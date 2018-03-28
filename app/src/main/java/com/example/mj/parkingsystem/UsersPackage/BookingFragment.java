package com.example.mj.parkingsystem.UsersPackage;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mj.parkingsystem.GMailSender;
import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.content.Context.NOTIFICATION_SERVICE;


public class BookingFragment extends Fragment {


    EditText autoTime;
    EditText etPrice;
    EditText etTimeTaken;
    Button btBookArea;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    DatePickerDialog dialog = null;
    final Calendar myCalendar = Calendar.getInstance();
    String startTime;
    String endTime;
    String price;

    String duration;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking, container, false);


        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Bookings");
        final TextView ploteno = (TextView) view.findViewById(R.id.plote_no);

        autoTime = (EditText) view.findViewById(R.id.autoTime);
        etTimeTaken =(EditText)view.findViewById(R.id.etTimeTaken);
        etPrice = (EditText)view.findViewById(R.id.etPrice);
        btBookArea =(Button)view.findViewById(R.id.btBookArea);
        SimpleDateFormat dateF = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        final SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        String time = timeF.format(Calendar.getInstance().getTime());

        autoTime.setText(time);



        Bundle bundle = getArguments();
        final String plote_no = bundle.getString("plote_no");
        Log.d(TAG, "onCreateView: " + plote_no);
        ploteno.setText(plote_no);

       autoTime.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {

               if (event.getAction() == MotionEvent.ACTION_UP) {

                   Date calender = Calendar.getInstance().getTime();

                   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                   final String dateformate = simpleDateFormat.format(calender);
                   Calendar mcurrentTime = Calendar.getInstance();
                   int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                   int minute = mcurrentTime.get(Calendar.MINUTE);
                   final int date = mcurrentTime.get(Calendar.DATE);
                   final String startTime = getTimeStamp(dateformate, hour, minute);
                   Log.d(TAG, "onTimeSet: " + startTime + " Start Time");




                   TimePickerDialog mTimePicker;
                   mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                       @Override
                       public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                           autoTime.setText(selectedHour + ":" + selectedMinute);
                           String endTime = getTimeStamp(dateformate, selectedHour, selectedMinute);
                           Log.d(TAG, "onTimeSet: " + endTime + " Time Selected");



                           //set time Duration and price to edittexts of both
                           Long endLTime = Long.valueOf(endTime.trim().toString());
                           Long sTime = Long.parseLong(startTime.trim().toString());
                           String etime = timeF.format(endLTime);
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
                           float difff = (d2.getTime() - d1.getTime());
                           long diffMinutes = diff / (60 * 1000) % 60;
                           long diffHours = diff / (60 * 60 * 1000) % 24;
                     float costs = difff/(60*60*1000)% 100;
                           etTimeTaken.setText(diffHours+":"+diffMinutes);
                           Log.d(TAG, "onTimeSelected: "+costs);

                           etPrice.setText(String.valueOf("$"+costs));
                           String price = String.valueOf(costs);
                           String duration =String.valueOf(diff);
                            ValuesOfTime(startTime,endTime,price,duration);


                       }
                   }, hour, minute, true);
                   mTimePicker.setTitle("Select Time");
                   mTimePicker.show();
               }
                   return true;
               }

       });

       btBookArea.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final ParkingArea parkingArea = new ParkingArea();

               final FragmentManager fragmentManager = getFragmentManager();

               if (endTime!=null) {
                   String status = "active";

                   Booking booking = new Booking(user_id, plote_no, startTime, endTime, status, price, duration);
                   Log.d(TAG, "onTimeSet: " + booking.toString() + " booking object");
                   mDatabaseReference.child(plote_no).setValue(booking);

                   fragmentManager.beginTransaction().
                           replace(R.id.frame_layout, parkingArea, parkingArea.getTag()).
                           commit();
//                    createNotification(1,R.drawable.ic_notifications_black_24dp,
//                            "Thank You ","you booked  "+plote_no+" for "+duration+" costs $"+price);

                //   startNotification();

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

               }else {



                   fragmentManager.beginTransaction().
                           replace(R.id.frame_layout, parkingArea, parkingArea.getTag()).
                           commit();

                   Toast.makeText(getActivity(), "Your are not booking", Toast.LENGTH_SHORT).show();
               }

           }
       });

      /*  autoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        autoDate.setText(sdf.format(myCalendar.getTime()));
                    }
                };

                dialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });*/




        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Book");
    }

//
//    private void updateLabel() {
//        String myFormat = "MM/dd/yy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        autoDate.setText(sdf.format(myCalendar.getTime()));
//    }


    public static String getTimeStamp(String date, int hours, int min) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss.SSS");
            String mString = date + " " + hours + ":" + min + ":" + "00.000";
            Date parsedDate = dateFormat.parse(mString);
            long time = parsedDate.getTime();

            return String.valueOf(time);
        } catch (Exception e) {

            e.printStackTrace();
             return null;

        }
    }

    // this method take data from inner time selection dialog to onclick button to push to firebase
    public void ValuesOfTime(String startTime, String endTime, String price, String duration){
        this.startTime =startTime;
        this.endTime=endTime;
        this.price= price;
        this.duration=duration;


    }



    private void startNotification() {
        Log.i("NextActivity", "startNotification");

        // Sets an ID for the notification
        int mNotificationId = 001;

        // Build Notification , setOngoing keeps the notification always in status bar
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("Stop LDB")
                        .setContentText("Click to stop LDB")
                       ;

        // Create pending intent, mention the Activity which needs to be
        //triggered when user clicks on notification(StopScript.class in this case)


        final NotificationsFragment notificationsFragment= new NotificationsFragment();
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.frame_layout,notificationsFragment,notificationsFragment.getTag()).
                commit();


        Intent resultIntent = new Intent(getActivity(), MainActivity.class).
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        resultIntent.putExtra("1", "1");

        PendingIntent contentIntent =
                PendingIntent.getActivity(
                        getActivity(),
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(contentIntent);


        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);

        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());


    }
}