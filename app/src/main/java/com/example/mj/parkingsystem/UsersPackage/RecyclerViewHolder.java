package com.example.mj.parkingsystem.UsersPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mj.parkingsystem.R;

import java.util.List;

/**
 * Created by MJ on 2/11/2018.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = RecyclerViewHolder.class.getSimpleName();

        public TextView item_name;
        private List<FeedbacksPojo> feedbacksPojos;

        Context context;
//    private OnRecyclerItemClickListener onRecyclerItemClickListener;
        String taskTitle;


    public RecyclerViewHolder(final View itemView, final List<FeedbacksPojo> feedbacksPojos) {
            super(itemView);
            String taskTitle;
            this.feedbacksPojos = feedbacksPojos;
            context = itemView.getContext();

            item_name = (TextView) itemView.findViewById(R.id.item_name);





        }
}
