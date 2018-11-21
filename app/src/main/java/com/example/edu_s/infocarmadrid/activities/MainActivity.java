package com.example.edu_s.infocarmadrid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.edu_s.infocarmadrid.fragments.MainContentFragment;
import com.example.edu_s.infocarmadrid.R;

public class MainActivity extends AppCompatActivity {

    private MainContentFragment mainContentFragment = new MainContentFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set preferences default values
        //PreferenceManager.setDefaultValues( this , R.xml.preferences , false );

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .add(android.R.id.content, mainContentFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.nav_car) {
            // Abrir la activity con la lista de eventos
            Intent intent = new Intent( this , MiscochesActivity.class );
            startActivity( intent );
            return true;
        }
        if (id == R.id.nav_check) {
            // Abrir la activity con la lista de eventos
            Intent intent = new Intent( this , ConsultarDistintivoActivity.class );
            startActivity( intent );
            return true;
        }


    /*    if (id == R.id.action_settings) {

            // Display the settings fragment as the main content.
            getFragmentManager().beginTransaction()
                    .replace( android.R.id.content , new SettingsFragment())
                    .addToBackStack( null )
                    .commit();

            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }


}
