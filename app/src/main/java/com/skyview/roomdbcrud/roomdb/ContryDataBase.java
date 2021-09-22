package com.skyview.roomdbcrud.roomdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ContryModel.class}, version = 1)
public abstract class ContryDataBase extends RoomDatabase {
    static ContryDataBase dataBase;

    public abstract Dao Dao();

    public static synchronized ContryDataBase getDataBaseInstance(Context context) {
        if (dataBase == null) {
            dataBase = Room.databaseBuilder(context.getApplicationContext(), ContryDataBase.class, "contry_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return dataBase;
    }

    // below line is to create a callback for our room database.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // this method is called when database is created
            // and below line is to populate our data.
            new PolulateDBAsynkTask(dataBase).execute();
        }
    };

    private static class PolulateDBAsynkTask extends AsyncTask<Void, Void, Void> {

        ContryDataBase contryDataBase;

        public PolulateDBAsynkTask(ContryDataBase contryDataBase) {
            Dao dao = dataBase.Dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
