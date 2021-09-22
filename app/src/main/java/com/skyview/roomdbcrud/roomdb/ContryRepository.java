package com.skyview.roomdbcrud.roomdb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContryRepository {
    private Dao dao;
    private LiveData<List<ContryModel>> allCource;

    public ContryRepository(Application application) {
        ContryDataBase dataBase = ContryDataBase.getDataBaseInstance(application.getApplicationContext());
        dao = dataBase.Dao();
        allCource = dao.getAllContry();
    }

    public void insert(ContryModel contryModel) {
        new InsertContry(dao).execute(contryModel);
    }

    public void delete(ContryModel contryModel) {
        new DeleteContry(dao).execute(contryModel);
    }

    public void deleteAll() {
        new DeleteAllContry(dao).execute();
    }

    public void update(ContryModel contryModel) {
        new UpdateContry(dao).execute(contryModel);
    }

    public LiveData<List<ContryModel>> getAllContry() {
        return allCource;
    }

    private static class InsertContry extends AsyncTask<ContryModel, Void, Void> {
        public InsertContry(Dao dao) {
            this.dao = dao;
        }

        private Dao dao;

        @Override
        protected Void doInBackground(ContryModel... contryModels) {
            dao.insert(contryModels[0]);
            return null;
        }
    }

    private static class UpdateContry extends AsyncTask<ContryModel, Void, Void> {
        public UpdateContry(Dao dao) {
            this.dao = dao;
        }

        private Dao dao;

        @Override
        protected Void doInBackground(ContryModel... contryModels) {
            dao.update(contryModels[0]);
            return null;
        }
    }

    private static class DeleteContry extends AsyncTask<ContryModel, Void, Void> {

        private Dao dao;

        public DeleteContry(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ContryModel... contryModels) {
            dao.delete(contryModels[0]);
            return null;
        }
    }

    private static class DeleteAllContry extends AsyncTask<Void, Void, Void> {
        public DeleteAllContry(Dao dao) {
            this.dao = dao;
        }

        private Dao dao;

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
}
