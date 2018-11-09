package com.example.edu_s.infocarmadrid;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String MENU_ITEM = "MenuItem";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private MenuItem menuItem;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        menuItem = item;
        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_car) {               //Cada opcion del menu cargará su propio fragment para no sobrecargar con activities
            fragment = new MiscochesFragment();
        } else if (id == R.id.nav_news) {
            fragment = new NoticiasFragment();
        } else if (id == R.id.nav_check) {
            fragment = new ComprobarFragment();
        } else if (id == R.id.nav_alarm) {
            fragment = new RecordatoriosFragment();
        }

        //Con esto cargaremos los fragments en la vista content_layout
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout,fragment).commit();
        }

        drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Guardamos datos del bundle
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(MENU_ITEM, menuItem.getItemId());
        super.onSaveInstanceState(outState, outPersistentState);
    }
    //Restauramos datos del bundle para no perder los datos
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        onNavigationItemSelected(navigationView.getMenu().findItem(savedInstanceState.getInt(MENU_ITEM)));
        super.onRestoreInstanceState(savedInstanceState);
    }

}
