package com.example.mj.parkingsystem.UsersPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mj.parkingsystem.Auth.Users;
import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by MJ on 3/27/2018.
 */



class BookingViewHolder extends RecyclerView.ViewHolder {

    public TextView item_name_job;
    public TextView item_apply;
    private List<Users> studentsList;
    private FirebaseAuth myAuth;
    private DatabaseReference databaseReference;
    Context context;


    public BookingViewHolder(View layoutView, List<Users> studentsList) {

        super(layoutView);
        this.studentsList = studentsList;
        context = itemView.getContext();

        item_name_job = (TextView) itemView.findViewById(R.id.item_name_job);
        item_apply = (TextView) itemView.findViewById(R.id.item_delete);

    }
}
