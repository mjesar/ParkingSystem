package com.example.mj.parkingsystem.UsersPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class PrintBooked extends Fragment {


        TextView tv_date_booked;
        TextView tv_startingtime_book;
        TextView tv_endtime_booked;
        TextView tv_totalhours_booked;
        TextView tv_price_book;
        FirebaseAuth mAuth;
        DatabaseReference mDatabasereference;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_print_booked, container, false);

                tv_date_booked =(TextView)view.findViewById(R.id.tv_date_booked);
                tv_startingtime_book =(TextView)view.findViewById(R.id.tv_startingtime_book);
                tv_endtime_booked =(TextView)view.findViewById(R.id.tv_endtime_booked);
                tv_totalhours_booked =(TextView)view.findViewById(R.id.tv_totalhours_booked);
                tv_price_book =(TextView)view.findViewById(R.id.tv_price_book);


                mAuth = FirebaseAuth.getInstance();
                final String user_id = mAuth.getCurrentUser().getUid();
                mDatabasereference= FirebaseDatabase.getInstance().getReference("Bookings");
                mDatabasereference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                                Booking booking = dataSnapshot.child(user_id).getValue(Booking.class);
                                Calendar c = Calendar.getInstance(Locale.CHINA);
                                //Set time in milliseconds
                              /*  c.setTimeInMillis(Long.parseLong(booking.getSelectedDate()));
                                int mYear = c.get(Calendar.YEAR);
                                int mMonth = c.get(Calendar.MONTH);
                                int mDay = c.get(Calendar.DAY_OF_MONTH);
                                String date = mDay+"/"+mMonth+"/"+mYear;*/
                                tv_date_booked.setText(booking.getSelectedDate());

                                // start Time
                                c.setTimeInMillis(Long.parseLong(booking.getStartTime()));

                                int hr = c.get(Calendar.HOUR);
                                int min = c.get(Calendar.MINUTE);
                                String sTime = hr+":"+min;
                                tv_startingtime_book.setText(sTime);
                                // end time selected
                                c.setTimeInMillis(Long.parseLong(booking.getEndTime()));

                                int hrr = c.get(Calendar.HOUR);
                                int min1 = c.get(Calendar.MINUTE);
                                String eTime = hrr+":"+min1;
                                tv_endtime_booked.setText(eTime);

                                // duration and price

                                tv_totalhours_booked.setText(booking.getDuration());
                                tv_price_book.setText(booking.getPrice());


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                });
        return view;
        }


        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }


}
