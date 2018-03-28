package com.example.mj.parkingsystem.UsersPackage;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mj.parkingsystem.Auth.Users;
import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by MJ on 3/27/2018.
 */


class BookingAdapter extends RecyclerView.Adapter {


    private List<Users> studentsList;
    private Context context;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseAuth mAuth;
    TextView item_apply;

    public BookingAdapter(FragmentActivity activity, List<Booking> bookingsArrayList) {

        this.studentsList = studentsList;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference("Booking");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Applied");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BookingViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifications, parent, false);
        viewHolder = new BookingViewHolder(layoutView, studentsList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       /* holder.item_name_job.setText(studentsList.get(position).getName());
        holder.item_apply.setText("");*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
