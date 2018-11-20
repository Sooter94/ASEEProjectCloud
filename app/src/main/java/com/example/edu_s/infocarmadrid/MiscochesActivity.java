package com.example.edu_s.infocarmadrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.edu_s.infocarmadrid.database.cochesItemCRUD;

import java.util.List;

public class MiscochesActivity extends AppCompatActivity {

    // Add a ToDoItem Request Code
    private static final int ADD_CAR_ITEM_REQUEST = 0;

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
                Snackbar.make(MiscochesActivity.this.getCurrentFocus(), "Item " + item.getName() + " Clicked", Snackbar.LENGTH_LONG).show();
            }
        });


        // - Attach the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        cochesItemCRUD crud = cochesItemCRUD.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        log("Entered onActivityResult()");

        //  - Check result code and request code.
        // If user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter
        if (requestCode == ADD_CAR_ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                CocheItem item = new CocheItem(data);

                //insert into DB
                cochesItemCRUD crud = cochesItemCRUD.getInstance(this);
                long id = crud.insert(item);

                //update item ID
                item.setID(id);

                //insert into adapter list
                mAdapter.add(item);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        if (mAdapter.getItemCount() == 0)
            loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // ALTERNATIVE: Save all ToDoItems

    }

    @Override
    protected void onDestroy() {
        cochesItemCRUD crud = cochesItemCRUD.getInstance(this);
        crud.close();
        super.onDestroy();
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
                cochesItemCRUD crud = cochesItemCRUD.getInstance(this);
                crud.deleteAll();
                mAdapter.clear();
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

    // Load stored ToDoItems
    private void loadItems() {
        cochesItemCRUD crud = cochesItemCRUD.getInstance(this);
        List<CocheItem> items = crud.getAll();
        mAdapter.load(items);
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
