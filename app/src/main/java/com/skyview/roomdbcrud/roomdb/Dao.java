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
    void insert(UserInfo userInfo);

    @Update
    void update(UserInfo userInfo);

    @Delete
    void delete(UserInfo userInfo);

    @Query("SELECT * FROM user_info")
    LiveData<List<UserInfo>> getAllUserInfo();

    @Query("DELETE FROM user_info")
    void deleteAll();

    @Query("SELECT * FROM user_info")
    List<UserInfo> getAllUserList();

}
