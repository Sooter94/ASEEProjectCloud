package com.example.edu_s.infocarmadrid.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.edu_s.infocarmadrid.R;
import com.example.edu_s.infocarmadrid.model.CocheAdapter;
import com.example.edu_s.infocarmadrid.room.CochesDatabase;

import java.util.List;

public class MiscochesActivity extends AppCompatActivity {

    // Add a CARITEM Request Code
    private static final int ADD_CAR_ITEM_REQUEST = 0;
    private static final int DELETE_CAR_ITEM_REQUEST = 1;

    private static final String TAG = "Lab-UserInterface";

    // IDs for menu items
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_DUMP = Menu.FIRST + 1;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CocheAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscoches);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // - Attach Listener to FloatingActionButton. Implement onClick()
                Intent intent = new Intent(MiscochesActivity.this, AddCocheActivity.class);
                startActivityForResult(intent, ADD_CAR_ITEM_REQUEST);
            }
        });

        // - Get a reference to the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // - Set a Linear Layout Manager to the RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // - Create a new Adapter for the RecyclerView
        // specify an adapter (see also next example)
        mAdapter = new CocheAdapter(this, new CocheAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CocheItem item) {
                // Abrimos la vista del evento
                Intent i = new Intent( MiscochesActivity.this , AddCocheActivity.class );
                i.putExtra("item" , item.getId() );
                startActivityForResult(i , DELETE_CAR_ITEM_REQUEST );            }
        });

        // - Attach the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        log("Entered onActivityResult()");

        //  - Check result code and request code.
        // If user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter
            if (resultCode == RESULT_OK) {

                // Crea un CocheItem
                if (requestCode == ADD_CAR_ITEM_REQUEST) {
                    CocheItem cocheItem = new CocheItem(data);
                    new AsyncInsert().execute(cocheItem);
                }
                // Borra un CocheItem
                if (requestCode == DELETE_CAR_ITEM_REQUEST) {
                    Bundle datos = data.getExtras();

                    try {
                        long id = datos.getLong( "item" );
                        Log.d("Debug" , "El bundle contiene el id: " + id );
                        new AsyncDeleteCoche().execute( id );
                    } catch ( Exception e ){
                        Log.d("Debug" , "El bundle no contiene datos" );
                    }
                }

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter.getItemCount() == 0)
            loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE:
                //cochesItemCRUD crud = cochesItemCRUD.getInstance(this);
                //crud.deleteAll();
                //mAdapter.clear();
                new AsyncDeleteAll().execute();
                return true;
            case MENU_DUMP:
                dump();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dump() {

        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            String data = ((CocheItem) mAdapter.getItem(i)).toLog();
            log("Item " + i + ": " + data.replace(CocheItem.ITEM_SEP, ","));
        }

    }

    // Load stored CocheItems
    private void loadItems() {
        new AsyncLoad().execute();
    }


    class AsyncLoad extends AsyncTask<Void,Void,List<CocheItem>> {

        @Override
        protected List<CocheItem> doInBackground(Void... voids) {

            CochesDatabase cochesDatabase = CochesDatabase.getDatabase( MiscochesActivity.this );
            List<CocheItem> items = cochesDatabase.cochesDao().getAll();

            return items;
        }

        @Override
        protected void onPostExecute(List<CocheItem> items) {
            super.onPostExecute(items);

            mAdapter.load( items );
        }
    }

    class AsyncInsert extends AsyncTask<CocheItem,Void,CocheItem>{

        @Override
        protected CocheItem doInBackground(CocheItem... items) {

            CochesDatabase cochesDatabase = CochesDatabase.getDatabase( MiscochesActivity.this );
            long id = cochesDatabase.cochesDao().insert( items[0] );
            items[0].setId(id);

            return items[0];
        }

        @Override
        protected void onPostExecute(CocheItem item) {
            super.onPostExecute(item);

            mAdapter.add( item );
        }
    }

    class AsyncDeleteAll extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            CochesDatabase cochesDatabase = CochesDatabase.getDatabase( MiscochesActivity.this );
            cochesDatabase.cochesDao().deleteAll();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mAdapter.clear();
        }
    }

    class AsyncDeleteCoche extends AsyncTask<Long,Void,CocheItem>{

        @Override
        protected CocheItem doInBackground(Long... id) {

            CochesDatabase cochesDatabase = CochesDatabase.getDatabase( MiscochesActivity.this );
            CocheItem aux = cochesDatabase.cochesDao().getItem( id[0] );
            cochesDatabase.cochesDao().deleteItem( id[0] );

            return aux;
        }

        @Override
        protected void onPostExecute(CocheItem item) {
            super.onPostExecute(item);

            mAdapter.delete( item );
        }
    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}
