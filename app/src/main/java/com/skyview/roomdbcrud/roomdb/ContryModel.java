package com.skyview.roomdbcrud.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contry_name")
public class ContryModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "names")
    private String name;

    public ContryModel(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
