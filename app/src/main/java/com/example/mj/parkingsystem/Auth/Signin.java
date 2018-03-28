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

import com.example.mj.parkingsystem.Admin.Adminnav;
import com.example.mj.parkingsystem.UsersPackage.MainActivity;
import com.example.mj.parkingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends Fragment{

    EditText et_email;
    EditText et_password;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.activity_signin,container,false);
        TextView login_tv =(TextView)view.findViewById(R.id.login_tv);
        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.container);

        progressDialog=new  ProgressDialog(getActivity());
        Button bt_signin= (Button)view.findViewById(R.id.bt_signin);
        et_email =(EditText)view.findViewById(R.id.et_email_s);
        et_password=(EditText)view.findViewById(R.id.et_password_s);
        mAuth =  FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        handleUsers();




        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main2Activity) getActivity()).onMoveToSignUpFragment(v);

            }
        });



        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Please provide email and password", Toast.LENGTH_SHORT).show();
                } else {


                    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                        progressDialog.show();
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {



                                if (task.isSuccessful()) {

                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(getTag(), "signInWithEmail:success");

                                    try {
                                        handleUsers();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(getTag(), "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                                progressDialog.dismiss();

                            }
                        });


                    }
                }
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }

    public void handleUsers(){


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try {
                    if (mAuth.getCurrentUser() != null /* users2.getUser_id().toString().equals(user_id )*/) {
                        final String user_id = mAuth.getCurrentUser().getUid();


                        progressDialog.show();



                        Users users = dataSnapshot.child(user_id).getValue(Users.class);
                        //  Log.d(TAG, "afsdffd: " + users.getUser_id()+" "+user_id);


                        if (users.getCatogety().toString().equals("user")) {

                            Log.d(getTag(), "onDataChangesad: " + "successfull opened company");

                            Intent intent1 = new Intent(getActivity(), MainActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                        }else if (users.getCatogety().toString().equals("admin")){
                            Log.d(getTag(), "onDataChangesad: " + "successfull opened company");

                            Intent intent1 = new Intent(getActivity(), Adminnav.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    final String user_id = mAuth.getCurrentUser().getUid();

                    Toast.makeText(getContext(), "Your account is deleted by Admin ", Toast.LENGTH_SHORT).show();
                    Log.d(getTag(), "onDataChadngesadaaa: " + user_id);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(getTag(), "User account deleted.");
                                    }
                                }
                            });
                    FirebaseAuth.getInstance().signOut();
                    progressDialog.dismiss();

                }

                progressDialog.dismiss();

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
