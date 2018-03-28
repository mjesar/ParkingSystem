package com.example.mj.parkingsystem.Admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mj.parkingsystem.R;
import com.example.mj.parkingsystem.UsersPackage.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;


public class AdminBookingFragment extends Fragment {
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    EditText et_start_time ;
    EditText et_end_time;
    EditText et_time_taken;
    EditText et_price;
    TextView tv_ploteno;
    TextView tv_username;
    Button bt_ok;
    Button bt_cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_admin_booking, container, false);


        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

        et_start_time =(EditText)view.findViewById(R.id.et_start_time);
        et_end_time=(EditText)view.findViewById(R.id.et_end_time);
        et_price =(EditText)view.findViewById(R.id.et_price);

        et_time_taken =(EditText)view.findViewById(R.id.et_total_time);
        tv_ploteno=(TextView)view.findViewById(R.id.plote_no);
        tv_username= (TextView)view.findViewById(R.id.tv_username);




        bt_ok=(Button)view.findViewById(R.id.bt_ok);
        bt_cancel= (Button)view.findViewById(R.id.bt_cancel);


        Bundle bundle = getArguments();
        final String plote_no = bundle.getString("plote_no");
        Log.d(TAG, "onCreateView: " + plote_no);
        tv_ploteno.setText(plote_no);

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Booking booking = dataSnapshot.child(plote_no).getValue(Booking.class);

                Long sTime = Long.valueOf(booking.getStartTime());
                Long endTime = Long.valueOf(booking.getEndTime());



                et_price.setText("$"+booking.getPrice());
                final SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());

                Date d1 = null;
                Date d2 = null;

                String etime = timeF.format(Long.valueOf(booking.getEndTime()));
                String stime = timeF.format(Long.valueOf(booking.getStartTime()));
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


                et_start_time.setText(stime);
                et_end_time.setText(etime);
                et_time_taken.setText(diffHours+":"+diffMinutes);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final AdminParkingArea adminParkingArea = new AdminParkingArea();
        final FragmentManager fragmentManager = getFragmentManager();
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragmentManager.beginTransaction().
                        replace(R.id.frame_layout,adminParkingArea,adminParkingArea.getTag()).
                        commit();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                alertDialog.setTitle("Cancel");
                alertDialog.setMessage("Are you sure you want to cancel this booking");
                alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Booking booking = new Booking();
                        booking.setStartTime("0000000");
                        booking.setEndTime("0000000");
                        booking.setPrice("");
                        booking.setStatus("cancel");
                        mDatabaseReference.child(plote_no).setValue(booking);
                        fragmentManager.beginTransaction().
                                replace(R.id.frame_layout,adminParkingArea,adminParkingArea.getTag()).
                                commit();


                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                alertDialog.create();
                alertDialog.show();



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


