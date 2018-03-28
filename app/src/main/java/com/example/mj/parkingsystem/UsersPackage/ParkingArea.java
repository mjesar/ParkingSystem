package com.example.mj.parkingsystem.UsersPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.example.mj.parkingsystem.UsersPackage.BookingFragment.getTimeStamp;

public class ParkingArea extends Fragment implements View.OnClickListener {

    ImageButton bt_polte2;
    ImageButton bt_polte1;
    ImageButton bt_polte3;
    ImageButton bt_polte4;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_parking_area,container,false);
        bt_polte1 = (ImageButton)view.findViewById(R.id.bt_plote1);
         bt_polte2 =(ImageButton)view.findViewById(R.id.bt_plote2);
        bt_polte3 =(ImageButton)view.findViewById(R.id.bt_plote3);
        bt_polte1.setClickable(false);


        bt_polte4 =(ImageButton)view.findViewById(R.id.bt_plote4);

        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();





        bt_polte1.setOnClickListener(this);
        bt_polte2.setOnClickListener(this);
        bt_polte3.setOnClickListener(this);
        bt_polte4.setOnClickListener(this);

       /* eReminderDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    *//*      Your code   to get date and time    *//*
                        selectedmonth = selectedmonth + 1;
                        eReminderDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();

            }

        });

        */
     return view;
   }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }


    @Override
    public void onClick(View view) {
        final BookingFragment bookingFragment = new BookingFragment();
        final FragmentManager fragmentManager = getFragmentManager();
        final Bundle bundle = new Bundle();
        Date calender= Calendar.getInstance().getTime();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        final String dateformate = simpleDateFormat.format(calender);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        final String startTime= getTimeStamp(dateformate,hour,minute);
        Log.d(TAG, "onTimeSet: "+startTime+" Start Time");

        switch (view.getId()) {

            case R.id.bt_plote1:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Booking booking = dataSnapshot.child("Plote_1").getValue(Booking.class);
                        Log.d(TAG, "onDataChange: "+booking.getEndTime()+"  "+dataSnapshot.getValue().toString());



                        Long endTime = Long.valueOf(booking.getEndTime().trim().toString());
                        Long sTime = Long.parseLong(startTime.trim().toString());
                        Log.d(TAG, "onDataChange: "+endTime+" == "+sTime);



                        // check if this plote is booked
                        if (sTime<=endTime){
                            bt_polte1.setClickable(true);
                            final SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                            String etime = timeF.format(endTime);
                            String stime = timeF.format(sTime);
                            Date d1 = null;
                            Date d2 = null;


                            // Get Difference between two times
                            try {
                                d1 = timeF.parse(stime);
                                d2 = timeF.parse(etime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //in milliseconds
                                long diff = d2.getTime() - d1.getTime();
                                long diffMinutes = diff / (60 * 1000) % 60;
                                long diffHours = diff / (60 * 60 * 1000) % 24;
                                Log.d(TAG, "onDataChangsse: "+diffHours+" : "+diffMinutes );

                            //   bt_polte1.setBackgroundColor(Color.parseColor("580008F9"));

                            Toast.makeText(getActivity(), "Already booked for H"+diffHours+" : m"+diffMinutes, Toast.LENGTH_SHORT).show();
                        }else {

                            bundle.putString("plote_no", "Plote_1");
                            bookingFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().
                                    replace(R.id.frame_layout,bookingFragment,bookingFragment.getTag()).
                                    commit();
                            Toast.makeText(getActivity(), "plote 1", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                break;

            case R.id.bt_plote2:

                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Booking booking = dataSnapshot.child("Plote_2").getValue(Booking.class);



                        Long endTime = Long.valueOf(booking.getEndTime().trim().toString());
                        Long sTime = Long.parseLong(startTime.trim().toString());
                        Log.d(TAG, "onDataChange: "+endTime+" == "+sTime);



                        // check if this plote is booked
                        if (sTime<=endTime){
                            bt_polte1.setClickable(true);
                            final SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                            String etime = timeF.format(endTime);
                            String stime = timeF.format(sTime);
                            Date d1 = null;
                            Date d2 = null;


                            // Get Difference between two times
                            try {
                                d1 = timeF.parse(stime);
                                d2 = timeF.parse(etime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //in milliseconds
                            long diff = d2.getTime() - d1.getTime();
                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000) % 24;
                            Log.d(TAG, "onDataChangsse: "+diffHours+" : "+diffMinutes );

                            //   bt_polte1.setBackgroundColor(Color.parseColor("580008F9"));

                            Toast.makeText(getActivity(), "Already booked for H"+diffHours+" : m"+diffMinutes, Toast.LENGTH_SHORT).show();
                        }else {

                            bundle.putString("plote_no", "Plote_2");
                            bookingFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().
                                    replace(R.id.frame_layout,bookingFragment,bookingFragment.getTag()).
                                    commit();
                            Toast.makeText(getActivity(), "plote 2", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

            case R.id.bt_plote3:

                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Booking booking = dataSnapshot.child("Plote_3").getValue(Booking.class);



                        Long endTime = Long.valueOf(booking.getEndTime().trim().toString());
                        Long sTime = Long.parseLong(startTime.trim().toString());
                        Log.d(TAG, "onDataChange: "+endTime+" == "+sTime);



                        // check if this plote is booked
                        if (sTime<=endTime){
                            bt_polte1.setClickable(true);
                            final SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                            String etime = timeF.format(endTime);
                            String stime = timeF.format(sTime);
                            Date d1 = null;
                            Date d2 = null;


                            // Get Difference between two times
                            try {
                                d1 = timeF.parse(stime);
                                d2 = timeF.parse(etime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //in milliseconds
                            long diff = d2.getTime() - d1.getTime();
                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000) % 24;
                            Log.d(TAG, "onDataChangsse: "+diffHours+" : "+diffMinutes );

                            //   bt_polte1.setBackgroundColor(Color.parseColor("580008F9"));

                            Toast.makeText(getActivity(), "Already booked for H"+diffHours+" : m"+diffMinutes, Toast.LENGTH_SHORT).show();
                        }else {


                            bundle.putString("plote_no", "Plote_3");
                            bookingFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().
                                    replace(R.id.frame_layout,bookingFragment,bookingFragment.getTag()).
                                    commit();
                            Toast.makeText(getActivity(), "plote 3", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

            case R.id.bt_plote4:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Booking booking = dataSnapshot.child("Plote_4").getValue(Booking.class);
                        Log.d(TAG, "onDataChange: "+booking.getEndTime()+"  "+dataSnapshot.getValue().toString());



                        Long endTime = Long.valueOf(booking.getEndTime().trim().toString());
                        Long sTime = Long.parseLong(startTime.trim().toString());
                        Log.d(TAG, "onDataChange: "+endTime+" == "+sTime);



                        // check if this plote is booked
                        if (sTime<=endTime){
                            bt_polte1.setClickable(true);
                            final SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                            String etime = timeF.format(endTime);
                            String stime = timeF.format(sTime);
                            Date d1 = null;
                            Date d2 = null;


                            // Get Difference between two times
                            try {
                                d1 = timeF.parse(stime);
                                d2 = timeF.parse(etime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //in milliseconds
                            long diff = d2.getTime() - d1.getTime();
                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000) % 24;
                            Log.d(TAG, "onDataChangsse: "+diffHours+" : "+diffMinutes );

                            //   bt_polte1.setBackgroundColor(Color.parseColor("580008F9"));

                            Toast.makeText(getActivity(), "Already booked for H"+diffHours+" : m"+diffMinutes, Toast.LENGTH_SHORT).show();
                        }else {

                            bundle.putString("plote_no", "Plote_4");
                            bookingFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().
                                    replace(R.id.frame_layout,bookingFragment,bookingFragment.getTag()).
                                    commit();
                            Toast.makeText(getActivity(), "plote 4", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

            default:
                break;
        }
    }


}
