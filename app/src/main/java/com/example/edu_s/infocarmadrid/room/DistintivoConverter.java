package com.example.edu_s.infocarmadrid.room;

import android.arch.persistence.room.TypeConverter;

import com.example.edu_s.infocarmadrid.model.CocheItem;

/**
 * Created by fgonzalez on 21/11/2018.
 */


public class DistintivoConverter {

    @TypeConverter
    public static CocheItem.Distintivo toDistintivo(String distintivo) {
        return CocheItem.Distintivo.valueOf(distintivo);
    }

    @TypeConverter
    public static String toString(CocheItem.Distintivo distintivo) {
        return distintivo.name();
    }

}