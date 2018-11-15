package com.example.edu_s.infocarmadrid;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fgonzalez on 14/11/2018.
 */

public class CocheItem {

        public static final String ITEM_SEP = System.getProperty("line.separator");

        public enum Distintivo {
            CERO, ECO, SD, AMARILLO, VERDE
        };


        public final static String ID = "ID";
        public final static String NAME = "name";
        public final static String MATRICULA = "matricula";
        public final static String DISTINTIVO = "distintivo";

        private long mID;
        private String mName = new String();
        private String mMatricula = new String();
        private Distintivo mDistintivo = Distintivo.CERO;


        CocheItem(String name, String matricula, Distintivo distintivo) {
            this.mName = name;
            this.mMatricula = matricula;
            this.mDistintivo = distintivo;
        }

        public CocheItem(long ID, String name, String matricula, String distintivo) {
            this.mID = ID;
            this.mName = name;
            this.mMatricula = matricula;
            this.mDistintivo = Distintivo.CERO;
        }

        // Create a new ToDoItem from data packaged in an Intent
        CocheItem(Intent intent) {
            mID = intent.getLongExtra(CocheItem.ID,0);
            mName = intent.getStringExtra(CocheItem.NAME);
            mMatricula =  intent.getStringExtra(CocheItem.MATRICULA);
            mDistintivo = Distintivo.valueOf(intent.getStringExtra(CocheItem.DISTINTIVO));
        }

        public long getID() { return mID; }

        public void setID(long ID) { this.mID = ID; }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getMatricula() {
            return mMatricula;
        }

        public void setMatricula(String matricula) {
            mMatricula = matricula;
        }

        public Distintivo getDistintivo() {
            return mDistintivo;
        }

        public void setDistintivo(Distintivo distintivo) {
            mDistintivo = distintivo;
        }

        // Take a set of String data values and
        // package them for transport in an Intent

        public static void packageIntent(Intent intent, String name,
                                         String matricula, Distintivo distintivo) {

            intent.putExtra(CocheItem.NAME, name);
            intent.putExtra(CocheItem.MATRICULA, matricula);
            intent.putExtra(CocheItem.DISTINTIVO, distintivo.toString());
        }

        public String toString() {
            return mID + ITEM_SEP + mName + ITEM_SEP + mMatricula + ITEM_SEP + mDistintivo;
        }

        public String toLog() {
            return "ID: " + mID + ITEM_SEP + "Name:" + mName + ITEM_SEP + "Matricula:" + mMatricula
                    + ITEM_SEP + "Distintivo:" + mDistintivo;
        }

}
