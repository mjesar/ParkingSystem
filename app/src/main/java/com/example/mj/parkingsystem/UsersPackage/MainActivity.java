package com.example.mj.parkingsystem.UsersPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mj.parkingsystem.Auth.Main2Activity;
import com.example.mj.parkingsystem.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /* FragmentManager fragmentManager= getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();*/

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new ParkingArea());
                    toolbar.setTitle("Parking Area");
                        return true;
                case R.id.navigation_feedBack:
                        loadFragment(new FeedbackFragment());
                        toolbar.setTitle("FeedBack");
                    return true;
                case R.id.navigation_notifications:
                    loadFragment(new NotificationsFragment());
                    toolbar.setTitle("Notifications");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= getSupportActionBar();
        toolbar.setTitle("Parking Area");
        loadFragment(new ParkingArea());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

     /*   Bundle b = getIntent().getExtras();
        if (b!=null){
            toolbar.setTitle("Notifications");

            loadFragment(new NotificationsFragment());

        }*/
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}