package com.skyview.roomdbcrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skyview.roomdbcrud.roomdb.UserInfo;
import com.skyview.roomdbcrud.roomdb.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    List<UserInfo> allContry = new ArrayList<>();
    Dialog dialog;
    UserViewModel viewModel;
    RecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDialog();
        initializeValue();
    }

    private void initDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialod_addcontry);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.addFloting);
        viewModel = new UserViewModel(getApplication());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void initializeValue() {
        @SuppressLint("NotifyDataSetChanged") Observer<List<UserInfo>> userObs = userInfoList -> {
            allContry.clear();
            allContry.addAll(userInfoList);
            if (adapter == null) {
                adapter = new RecAdapter(getApplication(), allContry, dialog);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        };
        viewModel = new UserViewModel(getApplication());
        viewModel.getListLiveData().observe(MainActivity.this, userObs);

        fab.setOnClickListener(v -> showPOPUp());
        new ItemTouchHelper(new SimpleCallback(ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "moving", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(MainActivity.this, "Swiping", Toast.LENGTH_SHORT).show();
                UserViewModel userViewModel = new UserViewModel(getApplication());
                UserInfo model = new UserInfo(allContry.get(viewHolder.getAdapterPosition()).getName(), allContry.get(viewHolder.getAdapterPosition()).getLocation(), allContry.get(viewHolder.getAdapterPosition()).getDate(), allContry.get(viewHolder.getAdapterPosition()).getIsParkingAvailable(), allContry.get(viewHolder.getAdapterPosition()).getLength(), allContry.get(viewHolder.getAdapterPosition()).getDifficulty(), allContry.get(viewHolder.getAdapterPosition()).getDescription());
                model.setId(allContry.get(viewHolder.getAdapterPosition()).getId());
                userViewModel.delete(model);
            }
        }).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(new SimpleCallback(RIGHT, LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                UserViewModel userViewModel = new UserViewModel(getApplication());
                UserInfo model = new UserInfo(allContry.get(viewHolder.getAdapterPosition()).getName(), allContry.get(viewHolder.getAdapterPosition()).getLocation(), allContry.get(viewHolder.getAdapterPosition()).getDate(), allContry.get(viewHolder.getAdapterPosition()).getIsParkingAvailable(), allContry.get(viewHolder.getAdapterPosition()).getLength(), allContry.get(viewHolder.getAdapterPosition()).getDifficulty(), allContry.get(viewHolder.getAdapterPosition()).getDescription());
                model.setId(allContry.get(viewHolder.getAdapterPosition()).getId());
                userViewModel.delete(model);
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void showPOPUp() {
        dialog.show();
        EditText name = dialog.findViewById(R.id.name);
        EditText location = dialog.findViewById(R.id.location);
        EditText date = dialog.findViewById(R.id.date);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGrp);
        EditText lengthOfHike = dialog.findViewById(R.id.lengthOfHike);
        Spinner difficulty = dialog.findViewById(R.id.difficulty);
        EditText description = dialog.findViewById(R.id.description);
        dialog.findViewById(R.id.saveBtn).setOnClickListener(v -> {
            if (isFieldValidated(
                    name.getText().toString(),
                    location.getText().toString(),
                    date.getText().toString(),
                    lengthOfHike.getText().toString()
            )) {
                saveDataIntoDb(
                        name.getText().toString(),
                        location.getText().toString(),
                        date.getText().toString(),
                        lengthOfHike.getText().toString(),
                        description.getText().toString(),
                        radioGroup.getCheckedRadioButtonId(),
                        difficulty.getSelectedItem().toString()
                );
                dialog.dismiss();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void saveDataIntoDb(String name, String location, String date, String length, String description, int checkedRadioButtonId, String difficulty) {
        String isParkingAvailable;
        switch (checkedRadioButtonId) {
            case R.id.radioM:
                isParkingAvailable = "Yes";
                break;
            case R.id.radioF:
                isParkingAvailable = "No";
                break;
            default:
                isParkingAvailable = "";
        }
        UserInfo model = new UserInfo(name, location, date, isParkingAvailable, length, difficulty, description);
        UserViewModel viewModel = new UserViewModel(getApplication());
        viewModel.insert(model);
    }

    private boolean isFieldValidated(String name, String location, String date, String lengthOfHike) {
        if (name.isEmpty()) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (location.isEmpty()) {
            Toast.makeText(this, "Please Enter Location", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (date.isEmpty()) {
            Toast.makeText(this, "Please Enter Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lengthOfHike.isEmpty()) {
            Toast.makeText(this, "Please Enter Length", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}