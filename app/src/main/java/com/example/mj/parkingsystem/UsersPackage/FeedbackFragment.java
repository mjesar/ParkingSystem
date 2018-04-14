package com.example.mj.parkingsystem.UsersPackage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mj.parkingsystem.Auth.Users;
import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {
    FirebaseAuth mAuth;
  //  List<Users> usersArrayList;
    List<FeedbacksPojo> feedbacksPojoList;
    RecyclerView recyclerView;
    RecordAdapter recordAdapter;
    DatabaseReference databaseReference;
    LinearLayoutManager linearLayoutManager;
    EditText et_feedback;
    Button bt_postfeedback;
     String username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.feedback_fragment, container, false);



        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Feedbacks");

        feedbacksPojoList = new ArrayList<FeedbacksPojo>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.rvFeedback);
        recyclerView.setLayoutManager(linearLayoutManager);
        recordAdapter = new RecordAdapter(getActivity(), feedbacksPojoList);
        recyclerView.setAdapter(recordAdapter);
       PopulateRecelerView();

        et_feedback=(EditText)view.findViewById(R.id.et_feedback);
        bt_postfeedback= (Button) view.findViewById(R.id.bt_postfeedback);

        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.child(user_id).getValue(Users.class);
                username= users.getName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bt_postfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String feedbacks= et_feedback.getText().toString();

                if (TextUtils.isEmpty(feedbacks)) {
                    Toast.makeText(getActivity(), "Provide Complete Info", Toast.LENGTH_SHORT).show();
                }else {


                   FeedbacksPojo feedbacksPojo= new FeedbacksPojo(feedbacks, user_id, username);
                    databaseReference.child(user_id).setValue(feedbacksPojo);

                }

            }
        });



        return view;
    }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            getActivity().setTitle("Profile");
        }

    public void PopulateRecelerView() {
        final String user_id = mAuth.getCurrentUser().getUid();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                if (dataSnapshot!= null) {

                    FeedbacksPojo feedbacksPojo = dataSnapshot.getValue(FeedbacksPojo.class);


                    feedbacksPojoList.add(feedbacksPojo);
                    Log.d(TAG, "onChildAdded: " + feedbacksPojo);

//                  Log.d(TAG, "onChildChangedStudents: "+feedbacksPojo.getFeedbacks());


                    recordAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                FeedbacksPojo feedbacksPojo= dataSnapshot.child(user_id).getValue(FeedbacksPojo.class);
                int index = getItemIndex(dataSnapshot.getKey());
                feedbacksPojoList.set(index, feedbacksPojo);
                recordAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                int index = getItemIndex(dataSnapshot.child(user_id).getKey());
                feedbacksPojoList.remove(index);
                recordAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public int getItemIndex(String key) {
        int index = -1;
        for (int i = 0; i < feedbacksPojoList.size(); i++) {
            if (feedbacksPojoList.get(i).getFeedbacks().equals(key)) {
                index = i;
                break;
            }
        }
        return index;

    }

}
