package com.example.edu_s.infocarmadrid;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

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
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final java.util.Calendar c = java.util.Calendar.getInstance();

    //Fecha
    final int mes = c.get(java.util.Calendar.MONTH);
    final int dia = c.get(java.util.Calendar.DAY_OF_MONTH);
    final int anio = c.get(java.util.Calendar.YEAR);

    //Hora
    final int hora = c.get(java.util.Calendar.HOUR_OF_DAY);
    final int minuto = c.get(java.util.Calendar.MINUTE);

    //Widgets
    EditText etFecha, etHora;
    ImageButton ibObtenerFecha, ibObtenerHora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recordatorio);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer =  findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        etFecha = findViewById(R.id.et_mostrar_fecha_picker);
        etHora = findViewById(R.id.et_mostrar_hora_picker);

        ibObtenerFecha = findViewById(R.id.ib_obtener_fecha);
        ibObtenerHora = findViewById(R.id.ib_obtener_hora);

        ibObtenerFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();

            }
        });
        ibObtenerHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHora();
            }
        });
    }


    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;

                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
        },anio, mes, dia);

        recogerFecha.show();

    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }

        }, hora, minuto, false);

        recogerHora.show();
    }



    public void activateAlarm(){

         /*
        1st Param : Context
        2nd Param : Integer request code
        3rd Param : Wrapped Intent
        4th Intent: Flag
        */
        Intent intent = new Intent(this,AlarmBroadcastReceiver.class);
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
        }  else if (id == R.id.nav_add) {
            Intent myIntent = new Intent(this, AddCocheActivity.class);
            this.startActivity(myIntent);
        } else if (id == R.id.nav_home) {
            Intent myIntent = new Intent(this, MainActivity.class);
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
