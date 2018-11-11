package com.example.edu_s.infocarmadrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MiscochesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String MENU_ITEM = "MenuItem";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private MenuItem menuItem;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscoches);
        toolbar = findViewById(R.id.toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AñadirCocheFragment  addcarFrag = new AñadirCocheFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, addcarFrag,"findThisFragment").addToBackStack(null).commit();
            }

        });

        drawer =  findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView =  findViewById(R.id.nav_view);
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        menuItem = item;
        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_car) {
            Intent myIntent = new Intent(this, MiscochesActivity.class);
            this.startActivity(myIntent);
        } else if (id == R.id.nav_news) {
            fragment = new NoticiasFragment();
        } else if (id == R.id.nav_check) {
            Intent myIntent = new Intent(this, comprobarcocheActivity.class);
            this.startActivity(myIntent);
        } else if (id == R.id.nav_alarm) {
            Intent myIntent = new Intent(this, recordatorioActivity.class);
            this.startActivity(myIntent);
        }

        //Con esto cargaremos los fragments en la vista content_layout
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,fragment).commit();
        }

        drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
