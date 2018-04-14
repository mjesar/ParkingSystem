package com.example.mj.parkingsystem.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AdminParkingArea extends Fragment implements View.OnClickListener{

    ImageButton bt_polte2;
    ImageButton bt_polte1;
    ImageButton bt_polte3;
    ImageButton bt_polte4;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_admin_parking_area,container,false);
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
        getActivity().setTitle("Parking Area");
    }

    @Override
    public void onClick(View view) {
        AdminBookingFragment adminBookingFragment = new AdminBookingFragment();
        final FragmentManager fragmentManager = getFragmentManager();
        final Bundle bundle = new Bundle();
        Date calender= Calendar.getInstance().getTime();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        final String dateformate = simpleDateFormat.format(calender);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        /*final String startTime= getTimeStamp(dateformate,hour,minute);
        Log.d(TAG, "onTimeSet: "+startTime+" Start Time");

        switch (view.getId()) {

            case R.id.bt_plote1:

                        bundle.putString("plote_no", "Plote_1");
                        adminBookingFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().
                                replace(R.id.frame_layout, adminBookingFragment, adminBookingFragment.getTag()).
                                commit();
                        Toast.makeText(getActivity(), "plote 1", Toast.LENGTH_SHORT).show();




                break;

            case R.id.bt_plote2:



                            bundle.putString("plote_no", "Plote_2");
                            adminBookingFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().
                                    replace(R.id.frame_layout,adminBookingFragment,adminBookingFragment.getTag()).
                                    commit();
                            Toast.makeText(getActivity(), "plote 2", Toast.LENGTH_SHORT).show();

                break;

            case R.id.bt_plote3:




                            bundle.putString("plote_no", "Plote_3");
                            adminBookingFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().
                                    replace(R.id.frame_layout,adminBookingFragment,adminBookingFragment.getTag()).
                                    commit();
                            Toast.makeText(getActivity(), "plote 3", Toast.LENGTH_SHORT).show();


                break;

            case R.id.bt_plote4:


                            bundle.putString("plote_no", "Plote_4");
                            adminBookingFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().
                                    replace(R.id.frame_layout,adminBookingFragment,adminBookingFragment.getTag()).
                                    commit();
                            Toast.makeText(getActivity(), "plote 4", Toast.LENGTH_SHORT).show();


                break;

            default:
                break;
        }*/
    }

}
