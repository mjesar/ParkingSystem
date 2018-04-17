package com.example.mj.campussystem.admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mj.campussystem.Authentication.Users;
import com.example.mj.campussystem.R;

import java.util.List;

/**
 * Created by MJ on 2/11/2018.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = RecyclerViewHolder.class.getSimpleName();

        public TextView item_name;
        public ImageView item_delete;
        private List<Users> studentsList;

        Context context;
//    private OnRecyclerItemClickListener onRecyclerItemClickListener;
        String taskTitle;


    public RecyclerViewHolder(final View itemView, final List<Users> studentsList) {
            super(itemView);
            this.studentsList = studentsList;
            context = itemView.getContext();

            item_name = (TextView) itemView.findViewById(R.id.item_name);
            item_delete = (ImageButton)itemView.findViewById(R.id.item_delete);






        }
}
