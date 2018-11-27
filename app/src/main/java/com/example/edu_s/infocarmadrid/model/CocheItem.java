package com.example.edu_s.infocarmadrid.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.Intent;

import com.example.edu_s.infocarmadrid.room.DistintivoConverter;

/**
 * Created by fgonzalez on 14/11/2018.
 */

@Entity(tableName = "coches")
public class CocheItem {

        @Ignore
        public static final String ITEM_SEP = System.getProperty("line.separator");

        public enum Distintivo {
            CERO, ECO, SD, AMARILLO, VERDE
        };

        @Ignore
        public final static String ID = "ID";
        @Ignore
        public final static String NAME = "name";
        @Ignore
        public final static String MATRICULA = "matricula";
        @Ignore
        public final static String DISTINTIVO = "distintivo";

        @PrimaryKey(autoGenerate = true)
        private long id;
        private String name = new String();
        private String matricula = new String();
        @TypeConverters(DistintivoConverter.class)
        private Distintivo distintivo = Distintivo.SD;

        @Ignore
        CocheItem(String name, String matricula, Distintivo distintivo) {
            this.name = name;
            this.matricula = matricula;
            this.distintivo = distintivo;
        }


        public CocheItem(long id, String name, String matricula, Distintivo distintivo) {
            this.id = id;
            this.name = name;
            this.matricula = matricula;
            this.distintivo = distintivo;
        }

        // Create a new CocheItem from data packaged in an Intent
        @Ignore
        public CocheItem(Intent intent) {
            id = intent.getLongExtra(CocheItem.ID,0);
            name = intent.getStringExtra(CocheItem.NAME);
            matricula =  intent.getStringExtra(CocheItem.MATRICULA);
            distintivo = Distintivo.valueOf(intent.getStringExtra(CocheItem.DISTINTIVO));
        }

        public long getId() { return id; }

        public void setId(long ID) { this.id = ID; }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMatricula() {
            return matricula;
        }

        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        public Distintivo getDistintivo() {
            return distintivo;
        }

        public void setDistintivo(Distintivo distintivo) {
            this.distintivo = distintivo;
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
            return id + ITEM_SEP + name + ITEM_SEP + matricula + ITEM_SEP + distintivo;
        }

        public String toLog() {
            return "ID: " + id + ITEM_SEP + "Name:" + name + ITEM_SEP + "Matricula:" + matricula
                    + ITEM_SEP + "Distintivo:" + distintivo;
        }

}
