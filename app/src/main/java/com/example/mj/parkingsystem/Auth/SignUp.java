package com.example.mj.parkingsystem.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mj.parkingsystem.UsersPackage.MainActivity;
import com.example.mj.parkingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends Fragment {
    EditText et_name_r;
    EditText et_email_r;
    EditText et_password_r;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String catogery ="user";
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.activity_sign_up,container,false);
        TextView signup_tv =(TextView)view.findViewById(R.id.signup_tv);
        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.container);
        progressDialog = new ProgressDialog(getActivity());
        et_name_r = (EditText)view.findViewById(R.id.et_name_r);
        et_email_r = (EditText)view.findViewById(R.id.et_email_r);
        et_password_r = (EditText)view.findViewById(R.id.et_password_r);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        Button bt_signup =(Button)view.findViewById(R.id.bt_signup);

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
            }
        });


        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main2Activity) getActivity()).onMoveToLoginFragment(v);

            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = et_email_r.getText().toString().trim();
                String password = et_password_r.getText().toString().trim();
                final String name = et_name_r.getText().toString().trim();



                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                    progressDialog.show();


                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                String user_id = mAuth.getCurrentUser().getUid();
                                Users users = new Users(name, email, user_id,catogery);
                                databaseReference.child(user_id).setValue(users);
                                Log.d(getTag(), "onComplete: "+users.getCatogety().toString());

                                if (users.getCatogety().toString()=="user") {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }
                            }




                        }
                    });


                } else {
                    Toast.makeText(getActivity(), "Please fill all fields ", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

        });



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    public void RegisterUser(View view) {


    }
}
