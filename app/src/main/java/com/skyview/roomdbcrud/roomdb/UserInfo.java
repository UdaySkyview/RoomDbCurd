package com.skyview.roomdbcrud.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_info")
public class UserInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "isParkingAvailable")
    private String isParkingAvailable;

    @ColumnInfo(name = "length")
    private String length;

    @ColumnInfo(name = "difficulty")
    private String difficulty;

    @ColumnInfo(name = "description")
    private String description;

    public UserInfo(String name, String location, String date, String isParkingAvailable, String length, String difficulty, String description) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.isParkingAvailable = isParkingAvailable;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsParkingAvailable() {
        return isParkingAvailable;
    }

    public void setIsParkingAvailable(String isParkingAvailable) {
        this.isParkingAvailable = isParkingAvailable;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
