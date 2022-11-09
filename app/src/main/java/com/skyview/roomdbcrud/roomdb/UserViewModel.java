package com.skyview.roomdbcrud.roomdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public UserViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new UserRepository(application);
        repository.getAllUsers();
    }

    public void insert(UserInfo userInfo) {
        repository.insert(userInfo);
    }

    public void update(UserInfo userInfo) {
        repository.update(userInfo);
    }

    public void delete(UserInfo model) {
        repository.delete(model);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<UserInfo>> getListLiveData() {
        return repository.getAllUsers();
    }
}
