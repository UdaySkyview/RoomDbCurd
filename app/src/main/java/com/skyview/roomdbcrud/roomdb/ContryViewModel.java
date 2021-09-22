package com.skyview.roomdbcrud.roomdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContryViewModel extends AndroidViewModel {
    private ContryRepository repository;
    private LiveData<List<ContryModel>> allContry;

    public ContryViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new ContryRepository(application);
        allContry = repository.getAllContry();
    }

    public void insert(ContryModel contryModel) {
        repository.insert(contryModel);
    }

    public void update(ContryModel contryModel) {
        repository.update(contryModel);
    }

    public void delete(ContryModel model) {
        repository.delete(model);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<ContryModel>> getAllContry() {
        return repository.getAllContry();
    }
}
