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

import com.example.mj.parkingsystem.GMailSender;
import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ParkingArea extends Fragment implements View.OnClickListener {

    ImageButton bt_polte2;
    ImageButton bt_polte1;
    ImageButton bt_polte3;
    ImageButton bt_polte4;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    Long st;
    Long ed;
    Long sdate;
    Long pTotal;
    Long dTotal;

     ArrayList<Booking> bookingArrayList;
    String selectedDate;
    String endTime1;
    String startTime1;
    String price;
    String duration;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_parking_area,container,false);
        bt_polte1 = (ImageButton)view.findViewById(R.id.bt_plote1);
         bt_polte2 =(ImageButton)view.findViewById(R.id.bt_plote2);
        bt_polte3 =(ImageButton)view.findViewById(R.id.bt_plote3);


        bt_polte4 =(ImageButton)view.findViewById(R.id.bt_plote4);

        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();

        bookingArrayList= new ArrayList<Booking>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Bookings");


        Bundle bundle = getArguments();
        selectedDate = bundle.getString("DateSelected");
        endTime1 = bundle.getString("EndTime");
        startTime1 = bundle.getString("StartTime");
        price= bundle.getString("Price");
        duration= bundle.getString("Duration");
        Log.d(TAG, "onCreateView: " + selectedDate+"  "+endTime1+"   "+startTime1);
        st= Long.valueOf(startTime1);
        ed = Long.valueOf(endTime1);


        bt_polte1.setOnClickListener(this);
        bt_polte2.setOnClickListener(this);
        bt_polte3.setOnClickListener(this);
        bt_polte4.setOnClickListener(this);

        checkBookingtime();




        return view;
   }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }


    @Override
    public void onClick(View view) {
        final PrintBooked printBooked= new PrintBooked();
        final FragmentManager fragmentManager = getFragmentManager();
        final String user_id = mAuth.getCurrentUser().getUid();
        Booking booking1;
        switch (view.getId()) {


            case R.id.bt_plote1:



                Log.e(TAG, "onDataChange: false");
                fragmentManager.beginTransaction().
                        replace(R.id.frame_layout, printBooked, printBooked.getTag()).
                        commit();
                booking1 = new Booking(user_id, "Plote_1", startTime1, endTime1, "Active", price, duration, selectedDate);
                mDatabaseReference.child(user_id).setValue(booking1);


                break;

            case R.id.bt_plote2:

                            fragmentManager.beginTransaction().
                                    replace(R.id.frame_layout,printBooked,printBooked.getTag()).
                                    commit();
               booking1= new Booking(user_id, "Plote_2", startTime1, endTime1, "Active", price, duration, selectedDate);
                mDatabaseReference.child(user_id).setValue(booking1);
                break;

            case R.id.bt_plote3:


                fragmentManager.beginTransaction().
                        replace(R.id.frame_layout,printBooked,printBooked.getTag()).
                        commit();
                booking1= new Booking(user_id, "Plote_3", startTime1, endTime1, "Active", price, duration, selectedDate);
                mDatabaseReference.child(user_id).setValue(booking1);


                break;

            case R.id.bt_plote4:


                fragmentManager.beginTransaction().
                        replace(R.id.frame_layout,printBooked,printBooked.getTag()).
                        commit();
                booking1= new Booking(user_id, "Plote_4", startTime1, endTime1, "Active", price, duration, selectedDate);
                mDatabaseReference.child(user_id).setValue(booking1);

                break;

            default:
                break;
        }
    }

    public void sendAutoMail(){

        new Thread(new Runnable() {


            @Override
            public void run() {
                try {

                    Object pw = "Password";
                    System.out.println("String: " + pw);

                    pw = "Password".toCharArray();
                    System.out.println("Array: " + pw);

                    GMailSender sender = new GMailSender("mohammadalijaisar@gmail.com",
                        "");
                    sender.sendMail("Hello from JavaMail", "Body from JavaMail",
                        "sylvain.saurel@gmail.com", "mohammadalijaisar@gmail.com");
            } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
            }
        }

    }).start();
}




        public void checkBookingtime(){

            mDatabaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Log.e(TAG, "onChildAdded: datasnapshot "+dataSnapshot);
                    Booking booking = dataSnapshot.getValue(Booking.class);
                    Log.e(TAG, "onChildAdded: booking "+booking );
                    bookingArrayList.add(booking);
                    Log.e(TAG, "onChildAdded: array " +bookingArrayList.get(0));

                    if (bookingArrayList.size()!=0){

                        Log.e(TAG, "onChildAdded: "+bookingArrayList.size() );
                        for (int i = 0; i < bookingArrayList.size(); i++) {
                            Long sTime = Long.valueOf(bookingArrayList.get(i).getStartTime());
                            Long eTime = Long.valueOf(bookingArrayList.get(i).getEndTime());
                            String ploteno = bookingArrayList.get(i).getPlote_no();
                            Log.e(TAG, "onClick:" + " PloteNo " + ploteno);

                            // check if this plote is book
                            if ((ploteno.equals( "Plote_1") && st < sTime && ed > eTime || ploteno.equals("Plote_1" )&&st < eTime && ed > eTime)) {
                                bt_polte1.setClickable(false);
                                Toast.makeText(getActivity(), "Plote1 ", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onDataChange: true");
                                bt_polte1.setBackgroundResource(R.mipmap.car);
                                Log.e(TAG, "onCreateView: " + bookingArrayList.size());

                            } else if (ploteno.equals("Plote_2" )&& st < sTime && ed > eTime || ploteno.equals("Plote_2" ) && st < eTime && ed > eTime) {
                                bt_polte2.setClickable(false);
                                Toast.makeText(getActivity(), "Plote 2 ", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onDataChange: true");
                                bt_polte2.setBackgroundResource(R.mipmap.car);
                                Log.e(TAG, "onCreateView: " + bookingArrayList.size());
                            } else if (ploteno.equals( "Plote_3") && st < sTime && ed > eTime || ploteno.equals("Plote_3" ) && st < eTime && ed > eTime) {
                                bt_polte3.setClickable(false);
                                Toast.makeText(getActivity(), "Plote 3", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onDataChange: true");
                                bt_polte3.setBackgroundResource(R.mipmap.car);
                                Log.e(TAG, "onCreateView: " + bookingArrayList.size());
                            } else if (ploteno.equals("Plote_4")&& st < sTime && ed > eTime ||ploteno.equals("Plote_4" )&& st < eTime && ed > eTime) {
                                bt_polte4.setClickable(false);
                                Toast.makeText(getActivity(), "Plote 4", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onDataChange: true");
                                bt_polte4.setBackgroundResource(R.mipmap.car);
                                Log.e(TAG, "onCreateView: " + bookingArrayList.size());
                            }


                        }
                    }
                }



                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





        }

}
