package com.example.edu_s.infocarmadrid.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.edu_s.infocarmadrid.activities.CocheItem;

/**
 * Created by fgonzalez on 20/11/2018.
 */

@Database( entities = { CocheItem.class } , version = 1 )
public abstract class CochesDatabase extends RoomDatabase {

    private static CochesDatabase instance;

    public static CochesDatabase getDatabase( Context context ){
        if( instance == null )
            instance = Room.databaseBuilder( context.getApplicationContext() ,
                    CochesDatabase.class , "infoMadrid.db" ).build();

        return instance;
    }

    public abstract CochesDao cochesDao();

}
