package com.skyview.roomdbcrud.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContryModel contryModel);

    @Update
    void update(ContryModel contryModel);

    @Delete
    void delete(ContryModel contryModel);

    @Query("SELECT * FROM contry_name")
    public LiveData<List<ContryModel>> getAllContry();

    @Query("DELETE FROM contry_name")
    void deleteAll();

}
