package com.example.edu_s.infocarmadrid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;

import com.example.edu_s.infocarmadrid.CocheItem.Distintivo;

public class AddCocheActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String MENU_ITEM = "MenuItem";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private MenuItem menuItem;
    private FragmentManager fragmentManager;


    private static final String TAG = "Lab-UserInterface";


    private Date mDate;
    private EditText mNameText;
    private EditText mMatriculaText;
    private RadioGroup mDistintivo;
    private RadioButton mDefaultDistintivo;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_car);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            mNameText = (EditText) findViewById(R.id.nameView);
            mMatriculaText = (EditText) findViewById(R.id.matriculaView);
            mDefaultDistintivo = (RadioButton) findViewById(R.id.dist_Cero);
            mDistintivo = (RadioGroup) findViewById(R.id.distintivoView);

        drawer =  findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // OnClickListener for the Cancel Button,

            final Button cancelButton = (Button) findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    log("Entered cancelButton.OnClickListener.onClick()");

                    // - Implement onClick().
                    Intent data = new Intent();
                    setResult(RESULT_CANCELED, data);
                    finish();

                }
            });

            //OnClickListener for the Reset Button

            final Button resetButton = (Button) findViewById(R.id.resetButton);
            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    log("Entered resetButton.OnClickListener.onClick()");

                    // - Reset data fields to default values
                    mNameText.setText("");
                    mMatriculaText.setText("");
                    mDistintivo.check(mDefaultDistintivo.getId());

                }
            });

            // OnClickListener for the Submit Button
            // Implement onClick().

            final Button submitButton = (Button) findViewById(R.id.submitButton);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    log("Entered submitButton.OnClickListener.onClick()");


                    // -  Get Distintivo
                    Distintivo distintivo = getDistintivo();

                    // -  Name
                    String name = mNameText.getText().toString();

                    // - Matricula
                    String matricula = mMatriculaText.getText().toString();

                    // Package ToDoItem data into an Intent
                    Intent data = new Intent();
                    CocheItem.packageIntent(data, name, matricula, distintivo);

                    // - return data Intent and finish
                    setResult(RESULT_OK, data);
                    finish();



                }
            });
        }


        private Distintivo getDistintivo() {

            switch (mDistintivo.getCheckedRadioButtonId()) {
                case R.id.dist_Eco: {
                    return Distintivo.ECO;
                }
                case R.id.dist_Sd: {
                    return Distintivo.SD;
                }
                case R.id.dist_Verde: {
                    return Distintivo.VERDE;
                }
                case R.id.dist_Amarillo: {
                    return Distintivo.AMARILLO;
                }
                default: {
                    return Distintivo.CERO;
                }
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
        private void log(String msg) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, msg);
        }

}


