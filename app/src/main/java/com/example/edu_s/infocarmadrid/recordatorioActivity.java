package com.example.edu_s.infocarmadrid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class recordatorioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String MENU_ITEM = "MenuItem";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private MenuItem menuItem;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public static final int REQUEST_CODE=101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorio);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer =  findViewById(R.id.drawer_layout);
         toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void activateAlarm(){

         /*
        1st Param : Context
        2nd Param : Integer request code
        3rd Param : Wrapped Intent
        4th Intent: Flag
        */
        Intent intent=new Intent(this,AlarmBroadcastReceiver.class);
        PendingIntent.getBroadcast(this,REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 30);
        /*
        Alarm will be triggered once exactly at 5:30
        */
        alarmMgr.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), alarmIntent);
        /*
        1st Param : Type of the Alarm
        2nd Param : Time in milliseconds when the alarm will be triggered first
        3rd Param :Pending Intent
        Note that we have changed the type to RTC as we are using Wall clock time. Also device wont wake up
        when the alarm is triggered
        */
        
    }
    public void cancelAlarm(){
        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
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
