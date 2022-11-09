package com.skyview.roomdbcrud;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skyview.roomdbcrud.roomdb.UserInfo;
import com.skyview.roomdbcrud.roomdb.UserViewModel;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder>{
    List<UserInfo> mylist;
    Application context;
    Context context1;
    Dialog dialog;
    public RecAdapter(Application context, List<UserInfo> mylist, Dialog dialog) {
        this.mylist=mylist;
        this.context = context;
        this.dialog=dialog;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecAdapter.MyViewHolder holder, int position) {

            UserInfo model1=mylist.get(position);
            holder.textView.setText(model1.getName());
            context1=holder.itemView.getContext();
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserInfo model=new UserInfo(mylist.get(holder.getAdapterPosition()).getName());
                    model.setId(mylist.get(holder.getAdapterPosition()).getId());
                    UserViewModel viewModel=new UserViewModel(context);
                    viewModel.delete(model);

                }
            });
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPOPUpforUpdate(holder.getAdapterPosition(),mylist);
                }
            });
    }


    @Override
    public int getItemCount() {
        if(mylist!=null)
        return mylist.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageButton editButton,deleteBtn;
        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textview);
            deleteBtn=itemView.findViewById(R.id.deleteButton);
            editButton=itemView.findViewById(R.id.editButton);
        }
    }

    public void setContry(List<UserInfo> list){
        mylist=list;

    }
    private void showPOPUpforUpdate(int position,List<UserInfo> mylist1) {
        dialog.show();
        EditText editText=dialog.findViewById(R.id.name);
        editText.setText(mylist1.get(position).getName().toString());
        dialog.findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String s=editText.getText().toString().trim();
              if(!s.isEmpty()){
                  UserInfo model=new UserInfo(s);
                  model.setId(mylist.get(position).getId());
                  UserViewModel viewModel=new UserViewModel(context);
                  viewModel.insert(model);
                  dialog.dismiss();
                  Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show();
              }
            }
        });

    }
}
