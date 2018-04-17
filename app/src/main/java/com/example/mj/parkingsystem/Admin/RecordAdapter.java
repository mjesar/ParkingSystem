package com.example.mj.campussystem.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mj.campussystem.Authentication.Users;
import com.example.mj.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by MJ on 2/9/2018.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecyclerViewHolder>  {

    private static final String TAG = RecordAdapter.class.getSimpleName();
    FirebaseAuth mAuth;
    private List<Users> studentsList;
    private Context context;
    DatabaseReference databaseReference;

    public RecordAdapter(Context context, List<Users> studentsList) {
        this.studentsList = studentsList;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        viewHolder = new RecyclerViewHolder(layoutView, studentsList);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.item_name.setText(studentsList.get(position).getName());

        context = holder.item_name.getContext();

        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                alertDialog.setTitle("Delete");

                final Users users = studentsList.get(position);
                final String name = users.getName();
                final String user_id = users.getUser_id();

                alertDialog.setMessage("Are Sure You want to delete " + name);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Log.d(TAG, "onDataChssange: " + dataSnapshot.child(user_id).getRef().toString());
                                     dataSnapshot.child(user_id).getRef().removeValue();
                                 Toast.makeText(context, "User "+name+" is deleted", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.create();
                alertDialog.show();
            }

        });

    }
    @Override
    public int getItemCount() {
        return this.studentsList.size();
    }
}
