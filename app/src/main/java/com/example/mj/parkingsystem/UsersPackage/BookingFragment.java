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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

    EditText etEndDate;
    EditText etEndTime;
    EditText etPrice;
    EditText etTimeTaken;
    EditText etStartTime;
    Button btBookArea;
    Button bt_select_area;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    DatePickerDialog dialog = null;
    final Calendar myCalendar = Calendar.getInstance();

    //   GLOBAL VARIABLES TO USE
    String startTime;
    String endTime;
    String price;
    String d;
    String selectedDate;
    int y;
    String h;
    int mi;

    String duration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking, container, false);


        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Bookings");
        final TextView ploteno = (TextView) view.findViewById(R.id.plote_no);
        etStartTime =(EditText)view.findViewById(R.id.etStartTime);
        etEndDate = (EditText) view.findViewById(R.id.etEndDate);
        etEndTime = (EditText) view.findViewById(R.id.etEndTime);
        etTimeTaken = (EditText) view.findViewById(R.id.etTimeTaken);
        etPrice = (EditText) view.findViewById(R.id.etPrice);
        bt_select_area = (Button) view.findViewById(R.id.bt_select_area);
        SimpleDateFormat dateF = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        final SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
         String date = dateF.format(Calendar.getInstance().getTime());
        String time = timeF.format(Calendar.getInstance().getTime());

        etStartTime.setText(time);
        etEndTime.setText(time);
        etEndDate.setText(date);


        String plote_no = null;
        try {
            Bundle bundle = getArguments();
            plote_no = bundle.getString("plote_no");
            Log.d(TAG, "onCreateView: " + plote_no);
            ploteno.setText(plote_no);
        } catch (Exception e) {
            e.printStackTrace();
        }

        etEndTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    Date calender = Calendar.getInstance().getTime();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
                    final String dateformate = simpleDateFormat.format(calender);
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR);
                    int minute = mcurrentTime.get(Calendar.MINUTE);



                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            etEndTime.setText(selectedHour + ":" + selectedMinute);
                           endTime = getTimeStamp(selectedDate, selectedHour, selectedMinute);
                            Log.d(TAG, "onTimeSet: " + endTime + " Time Selected");

                            Log.d(TAG, "onClick: "+endTime);

                      /*     SimpleDateFormat simpledateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss.SSS");
                           Date calender = Calendar.getInstance().getTime();
                           String str_date="13-09-2011 "+selectedHour+":"+selectedMinute+"00.000";
                           DateFormat formatter ;
                           Date date ;
                           formatter = new SimpleDateFormat("dd-MM-yyyy");

                           try {
                               date = (Date)formatter.parse(str_date);


                           final String dateformate = simpledateFormat.format(date);
                           String DateTime = getTimeStamp(dateformate,selectedHour ,selectedMinute);
                           Toast.makeText(getActivity(), DateTime, Toast.LENGTH_SHORT).show();
                           Log.d(TAG, "onClick: "+DateTime);

                           } catch (ParseException e) {
                               e.printStackTrace();
                           }*/
                           //set time Duration and price to edittexts of both
                           Long endLTime = Long.valueOf(endTime.trim().toString());
                           Long sTime = Long.parseLong(startTime.trim().toString());
                           String etime = timeF.format(endLTime);
                           String stime = timeF.format(sTime);
                           Date d1 = null;
                           Date d2 = null;


                           //  Get Difference between two times
                           try {
                               d1 = timeF.parse(stime);
                               d2 = timeF.parse(etime);
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }
                            Log.d(TAG, "onTimeSet: "+etime+stime);

                            //in milliseconds
                          long diff = d2.getTime() - d1.getTime();
                           float difff = (d2.getTime() - d1.getTime());
                           long diffMinutes = diff / (60 * 1000) % 60;
                           long diffHours = diff / (60 * 60 * 1000) % 24;
                          float costs = difff/(60*60*1000)% 100;
                           etTimeTaken.setText(diffHours+":"+diffMinutes);
                           Log.d(TAG, "onTimeSelected: "+diffHours);

                           etPrice.setText(String.valueOf("$"+costs));
                            price = String.valueOf("$"+costs);
                           duration =String.valueOf(diffHours+":"+diffMinutes);
                           ValuesOfTime(startTime,endTime,price,duration);
//


                        }
                    }, hour, minute, false);
                    mTimePicker.setTitle("Select End Time");
                    mTimePicker.show();
                }
                return true;
            }

        });

        etStartTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Date calender = Calendar.getInstance().getTime();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss.SSS");
                    final String dateformate = simpleDateFormat.format(calender);
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            etStartTime.setText(selectedHour + ":" + selectedMinute);

                           startTime = getTimeStamp(selectedDate, selectedHour, selectedMinute);
                           Log.d(TAG, "onTimeSet: " + selectedDate+ " Time Selected");
                            Log.d(TAG, "onClick: " + startTime);
                        }
                    }, hour, minute, false);
                    mTimePicker.setTitle("Select Starting Time");
                    mTimePicker.show();
                }
                    return true;

            }
        });






        etEndDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {


                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar mcurrentTime = Calendar.getInstance();

                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String myFormat = "dd-MM-yyyy";
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                            monthOfYear=monthOfYear+1;
                            if (monthOfYear> 10)
                                selectedDate = dayOfMonth+ "-" + monthOfYear+ "-" + year;
                            else {
                                selectedDate = dayOfMonth + "-" + "0" + monthOfYear+ "-" + year;
                            }

                            Log.d(TAG, "onDateSet: "+selectedDate);

                            etEndDate.setText(sdf.format(myCalendar.getTime()));
                        }
                    };

                    dialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                }
                return true;
            }

        });
        final Bundle bundle = new Bundle();

        bt_select_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (endTime!=null&& startTime!=null&&selectedDate!=null) {

                   final ParkingArea parkingArea = new ParkingArea();
                   bundle.putString("DateSelected", selectedDate);
                   bundle.putString("StartTime", startTime);
                   bundle.putString("EndTime", endTime);
                   bundle.putString("Price",price);
                   bundle.putString("Duration",duration);
                   parkingArea.setArguments(bundle);

                   Toast.makeText(getActivity(), "plote 1", Toast.LENGTH_SHORT).show();
                   final FragmentManager fragmentManager = getFragmentManager();
                   fragmentManager.beginTransaction().
                           replace(R.id.frame_layout, parkingArea, parkingArea.getTag()).
                           commit();
                   Log.d(TAG, "onClick: " + startTime + " end time " + endTime + " date " + selectedDate);
               }else {
                   Toast.makeText(getActivity(), "Please select a date and time first", Toast.LENGTH_SHORT).show();
               }

            }
        });


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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
            String mString = date + " " + hours + ":" + min + ":" + "00.000";
            Date parsedDate = dateFormat.parse(mString);
            long time = parsedDate.getTime();
            return String.valueOf(time);
        } catch (Exception e) {
        }
        return null;
    }

    // this method take data from inner time selection dialog to onclick button to push to firebase
    public void ValuesOfTime(String startTime, String endTime, String price, String
            duration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.duration = duration;


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
                        .setContentText("Click to stop LDB");

        // Create pending intent, mention the Activity which needs to be
        //triggered when user clicks on notification(StopScript.class in this case)


        final NotificationsFragment notificationsFragment = new NotificationsFragment();
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.frame_layout, notificationsFragment, notificationsFragment.getTag()).
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