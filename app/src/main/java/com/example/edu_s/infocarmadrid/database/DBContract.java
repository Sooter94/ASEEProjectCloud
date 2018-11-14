package com.example.edu_s.infocarmadrid.database;

import android.provider.BaseColumns;

/**
 * Created by fgonzalez on 14/11/2018.
 */

public final class DBContract {

    private DBContract() {}

    public static class cocheItem implements BaseColumns {
        public static final String TABLE_NAME = "misCoches";
        public static final String COLUMN_NAME_MATRICULA = "matricula";
        public static final String COLUMN_NAME_NAME = "nombre";
        public static final String COLUMN_NAME_DISTINTIVO = "distintivo";
    }
}
