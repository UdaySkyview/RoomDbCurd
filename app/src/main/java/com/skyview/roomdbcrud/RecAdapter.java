package com.skyview.roomdbcrud;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skyview.roomdbcrud.roomdb.UserInfo;
import com.skyview.roomdbcrud.roomdb.UserViewModel;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {
    List<UserInfo> mylist;
    Application context;
    Context context1;
    Dialog dialog;

    public RecAdapter(Application context, List<UserInfo> mylist, Dialog dialog) {
        this.mylist = mylist;
        this.context = context;
        this.dialog = dialog;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecAdapter.MyViewHolder holder, int position) {

        UserInfo model1 = mylist.get(position);
        holder.textView.setText(model1.getName());
        holder.location.setText(model1.getLocation());
        holder.date.setText(model1.getDate());
        holder.length.setText(model1.getLength());
        holder.parking.setText(model1.getIsParkingAvailable());
        holder.difficulty.setText(model1.getDifficulty());
        holder.description.setText(model1.getDescription());
        context1 = holder.itemView.getContext();
        holder.deleteBtn.setOnClickListener(v -> {
            UserInfo model = new UserInfo(mylist.get(holder.getAdapterPosition()).getName(),
                    mylist.get(holder.getAdapterPosition()).getLocation(),
                    mylist.get(holder.getAdapterPosition()).getDate(),
                    mylist.get(holder.getAdapterPosition()).getIsParkingAvailable(),
                    mylist.get(holder.getAdapterPosition()).getLength(),
                    mylist.get(holder.getAdapterPosition()).getDifficulty(),
                    mylist.get(holder.getAdapterPosition()).getDescription());
            model.setId(mylist.get(holder.getAdapterPosition()).getId());
            UserViewModel viewModel = new UserViewModel(context);
            viewModel.delete(model);

        });
        holder.editButton.setOnClickListener(v -> showPOPUpForUpdate(holder.getAdapterPosition(), mylist));
    }


    @Override
    public int getItemCount() {
        if (mylist != null)
            return mylist.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView location;
        TextView date;
        TextView parking;
        TextView length;
        TextView difficulty;
        TextView description;
        ImageButton editButton, deleteBtn;

        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            parking = itemView.findViewById(R.id.parking);
            length = itemView.findViewById(R.id.lengthOfHike);
            difficulty = itemView.findViewById(R.id.difficulty);
            description = itemView.findViewById(R.id.description);
            deleteBtn = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }

    public void setContry(List<UserInfo> list) {
        mylist = list;

    }

    @SuppressLint("NonConstantResourceId")
    private void showPOPUpForUpdate(int position, List<UserInfo> myList) {
        dialog.show();
        EditText editText = dialog.findViewById(R.id.name);
        EditText location = dialog.findViewById(R.id.location);
        EditText date = dialog.findViewById(R.id.date);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGrp);
        EditText lengthOfHike = dialog.findViewById(R.id.lengthOfHike);
        Spinner difficulty = dialog.findViewById(R.id.difficulty);
        EditText description = dialog.findViewById(R.id.description);
        editText.setText(myList.get(position).getName().toString());
        location.setText(myList.get(position).getLocation());
        date.setText(myList.get(position).getDate());
        lengthOfHike.setText(myList.get(position).getLength());
        description.setText(myList.get(position).getDescription());
        switch (myList.get(position).getDifficulty()) {
            case "Easy":
                difficulty.setSelection(0);
                break;
            case "Medium":
                difficulty.setSelection(1);
                break;
            case "Hard":
                difficulty.setSelection(2);
                break;
        }
        if (myList.get(position).getIsParkingAvailable().equals("Yes")) {
            radioGroup.check(R.id.radioM);
        } else if (myList.get(position).getIsParkingAvailable().equals("No")) {
            radioGroup.check(R.id.radioF);
        }


        dialog.findViewById(R.id.saveBtn).setOnClickListener(v -> {
            String name = editText.getText().toString().trim();
            String locationVal = location.getText().toString().trim();
            String dateVal = date.getText().toString().trim();
            String lengthOfHikeVal = lengthOfHike.getText().toString().trim();
            String descriptionVal = description.getText().toString().trim();
            int id = radioGroup.getCheckedRadioButtonId();
            String isParkAvail;
            switch (id) {
                case R.id.radioM:
                    isParkAvail = "Yes";
                    break;
                case R.id.radioF:
                    isParkAvail = "No";
                    break;
                default:
                    isParkAvail = "";

            }
            String difficultyVal = difficulty.getSelectedItem().toString();
            if (isFieldValidated(context, name, locationVal, dateVal, lengthOfHikeVal)) {
                UserInfo model = new UserInfo(name, locationVal, dateVal, isParkAvail, lengthOfHikeVal, difficultyVal, descriptionVal);
                model.setId(mylist.get(position).getId());
                UserViewModel viewModel = new UserViewModel(context);
                viewModel.insert(model);
                dialog.dismiss();
                Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isFieldValidated(Context context, String name, String location, String date, String lengthOfHike) {
        if (name.isEmpty()) {
            Toast.makeText(context, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (location.isEmpty()) {
            Toast.makeText(context, "Please Enter Location", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (date.isEmpty()) {
            Toast.makeText(context, "Please Enter Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lengthOfHike.isEmpty()) {
            Toast.makeText(context, "Please Enter Length", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
