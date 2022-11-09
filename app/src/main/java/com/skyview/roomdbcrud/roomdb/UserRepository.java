package com.skyview.roomdbcrud.roomdb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private final Dao dao;
    private final LiveData<List<UserInfo>> listLiveData;

    public UserRepository(Application application) {
        UserDB dataBase = UserDB.getDataBaseInstance(application.getApplicationContext());
        dao = dataBase.Dao();
        listLiveData = dao.getAllUserInfo();
    }

    public void insert(UserInfo userInfo) {
        new InsertUser(dao).execute(userInfo);
    }

    public void delete(UserInfo userInfo) {
        new DeleteContry(dao).execute(userInfo);
    }

    public void deleteAll() {
        new DeleteAllContry(dao).execute();
    }

    public void update(UserInfo userInfo) {
        new UpdateContry(dao).execute(userInfo);
    }

    public LiveData<List<UserInfo>> getAllUsers() {
        return listLiveData;
    }

    private static class InsertUser extends AsyncTask<UserInfo, Void, Void> {
        public InsertUser(Dao dao) {
            this.dao = dao;
        }

        private final Dao dao;

        @Override
        protected Void doInBackground(UserInfo... userInfos) {
            dao.insert(userInfos[0]);
            return null;
        }
    }

    private static class UpdateContry extends AsyncTask<UserInfo, Void, Void> {
        public UpdateContry(Dao dao) {
            this.dao = dao;
        }

        private Dao dao;

        @Override
        protected Void doInBackground(UserInfo... userInfos) {
            dao.update(userInfos[0]);
            return null;
        }
    }

    private static class DeleteContry extends AsyncTask<UserInfo, Void, Void> {

        private Dao dao;

        public DeleteContry(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserInfo... userInfos) {
            dao.delete(userInfos[0]);
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
