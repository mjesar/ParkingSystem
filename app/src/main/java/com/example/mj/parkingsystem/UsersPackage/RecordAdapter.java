package com.example.mj.parkingsystem.UsersPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by MJ on 2/9/2018.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecyclerViewHolder>  {

    private static final String TAG = RecordAdapter.class.getSimpleName();
    FirebaseAuth mAuth;
    private List<FeedbacksPojo> feedbacksPojos;
    private Context context;
    DatabaseReference databaseReference;

    public RecordAdapter(Context context, List<FeedbacksPojo> feedbacksPojos) {
        this.feedbacksPojos = feedbacksPojos;
        this.context = context;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_item, parent, false);
        viewHolder = new RecyclerViewHolder(layoutView, feedbacksPojos);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        Log.e(TAG, "onBindViewHolder: "+feedbacksPojos.get(position).getFeedbacks());
        holder.item_name.setText(feedbacksPojos.get(position).getFeedbacks());

        context = holder.item_name.getContext();


    }



    @Override
    public int getItemCount() {
        return this.feedbacksPojos.size();
    }
}
