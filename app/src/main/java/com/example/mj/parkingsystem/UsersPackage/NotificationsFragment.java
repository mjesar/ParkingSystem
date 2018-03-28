package com.example.mj.parkingsystem.UsersPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment  extends Fragment {

    FirebaseAuth mAuth;
    List<Booking> bookingsArrayList;

    RecyclerView recyclerView;
    BookingAdapter bookingAdapter;
    DatabaseReference databaseReference;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_notifications_fragment,container,false);


        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Jobs");

        bookingsArrayList = new ArrayList<Booking>();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_notificcations);
        recyclerView.setLayoutManager(linearLayoutManager);

        bookingAdapter= new BookingAdapter (getActivity(), bookingsArrayList);

        recyclerView.setAdapter(bookingAdapter);
        PopulateRecelerView();

        return view;

    }

    private void PopulateRecelerView() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String user_id = mAuth.getCurrentUser().getUid();


                Booking booking= dataSnapshot.getValue(Booking.class);

                // users.setUser_id(dataSnapshot.getKey());
                if (booking.user_id.equals("job")){
                    bookingsArrayList.add(booking);

                    Log.d(getTag(), "onChildChangedStudents: "+booking.getStatus());


                    bookingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                Booking booking= dataSnapshot.getValue(Booking.class);
                int index = getItemIndex(dataSnapshot.getKey());
                bookingsArrayList.set(index, booking);
                bookingAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                int index = getItemIndex(dataSnapshot.getKey());
                bookingsArrayList.remove(index);
                bookingAdapter.notifyDataSetChanged();


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
        for (int i = 0; i < bookingsArrayList.size(); i++) {
            if (bookingsArrayList.get(i).getUser_id().equals(key)) {
                index = i;
                break;
            }
        }
        return index;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getActivity().setTitle("Profile");
}
}
