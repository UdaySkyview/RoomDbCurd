package com.skyview.roomdbcrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skyview.roomdbcrud.roomdb.ContryModel;
import com.skyview.roomdbcrud.roomdb.ContryViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    List<ContryModel> allContry=new ArrayList<>();
    Dialog dialog;
    ContryViewModel viewModel;
    RecAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.addFloting);
        //initiallize dialog
        dialog=new Dialog(MainActivity.this);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialod_addcontry);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //insert one value
        viewModel=new ContryViewModel(getApplication());
        //allContry=new ContryViewModel(getApplication()).getAllContry().;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        initilizeValue();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPOPUp();
            }
        });
        new ItemTouchHelper(new SimpleCallback(ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "moving", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(MainActivity.this, "swaped", Toast.LENGTH_SHORT).show();
                ContryViewModel contryViewModel=new ContryViewModel(getApplication());
                ContryModel model=new ContryModel(allContry.get(viewHolder.getAdapterPosition()).getName());
                model.setId(allContry.get(viewHolder.getAdapterPosition()).getId());
                contryViewModel.delete(model);
            }
        }).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(new SimpleCallback(RIGHT, LEFT) {

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                ContryViewModel contryViewModel=new ContryViewModel(getApplication());
                ContryModel model=new ContryModel(allContry.get(viewHolder.getAdapterPosition()).getName());
                model.setId(allContry.get(viewHolder.getAdapterPosition()).getId());
                contryViewModel.delete(model);
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void initilizeValue() {
        Observer<List<ContryModel>> contryobserv=new Observer<List<ContryModel>>() {
            @Override
            public void onChanged(List<ContryModel> contryModels) {
                    allContry.clear();
                    allContry.addAll(contryModels);
                    if (adapter==null){
                        adapter=new RecAdapter(getApplication(),allContry,dialog);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        adapter.notifyDataSetChanged();
                    }
            }
        };
        viewModel=new ContryViewModel(getApplication());
        viewModel.getAllContry().observe(MainActivity.this, contryobserv);
    }

    private void showPOPUp() {
        dialog.show();
        EditText editText=dialog.findViewById(R.id.editText);
        editText.setText("");
        dialog.findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataIntoDb(editText.getText().toString());
                dialog.dismiss();
            }
        });
    }

    private void saveDataIntoDb(String toString) {
        if(!toString.isEmpty()){
            ContryModel model=new ContryModel(toString);
            ContryViewModel viewModel=new ContryViewModel(getApplication());
            viewModel.insert(model);
//            viewModel.getAllContry().observe(this, new Observer<List<ContryModel>>() {
//                @Override
//                public void onChanged(List<ContryModel> contryModels) {
//                    allContry=contryModels;
//                    RecAdapter adapter=new RecAdapter(getApplication(), allContry,dialog);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(getApplicationContext(), "Data Insert Successfully", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
        else{
            Toast.makeText(this, "Plz Write Contry Name then Insert", Toast.LENGTH_SHORT).show();
        }
    }

}