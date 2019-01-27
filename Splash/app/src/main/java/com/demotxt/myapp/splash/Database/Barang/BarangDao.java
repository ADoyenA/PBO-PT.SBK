package com.demotxt.myapp.splash.Database.Barang;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BarangDao {


    @Query("SELECT * from barang ORDER BY nbarang ASC")
    LiveData<List<Barang>> getAlphabetizeBarangs();

    @Insert
    void insert(Barang barang);

    @Query("DELETE FROM barang")
    void  deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Barang barang);

    @Delete
    void deleteTask(Barang barang);

    @Query("SELECT * FROM barang WHERE id = :id")
    LiveData<Barang> loadTaskByIdb(int id);
}
