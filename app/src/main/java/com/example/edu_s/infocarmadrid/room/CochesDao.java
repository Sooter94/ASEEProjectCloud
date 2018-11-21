package com.example.edu_s.infocarmadrid.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.edu_s.infocarmadrid.activities.CocheItem;

import java.util.List;

/**
 * Created by fgonzalez on 20/11/2018.
 */

@Dao
public interface CochesDao {

    @Query("SELECT * FROM coches")
    public List<CocheItem> getAll();

    @Insert
    public long insert(CocheItem item);

    @Query("DELETE FROM coches")
    public void deleteAll();

    @Query("SELECT * FROM coches WHERE id = :id")
    public CocheItem getItem( long id );

    @Query("DELETE FROM coches WHERE id = :id")
    public void deleteItem( long id );

}
