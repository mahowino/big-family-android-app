package com.example.bigfamilyv20.Activities;

import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.bigfamilyv20.Entities.User;
import com.example.bigfamilyv20.R;
import com.example.bigfamilyv20.Utils.InternetChecker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
private static ConstraintLayout constraintLayout;
private static FirebaseUser user;
private static FirebaseAuth mAuth=FirebaseAuth.getInstance();
private static FirebaseFirestore firebaseFirestore;
/*
    @Override
    protected void onStart() {
        user=mAuth.getCurrentUser();
        if (user!=null){
            Intent home=new Intent(MainActivity.this, MainPage.class);
            startActivity(home);
            finish();
        }
        super.onStart();
    }
    */

    @Override
    protected void onStart() {
        //get the existing active user
        user=mAuth.getCurrentUser();

        if(user!=null){
            //user already logged in
        String phone=user.getPhoneNumber();
            firebaseFirestore = FirebaseFirestore.getInstance();
            final CollectionReference reference1 = firebaseFirestore.collection("Users");
           //get user info

            reference1
                    .whereEqualTo("phoneNumber",phone)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        User newUser=new User();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            //populate User Data Onto User Object

                            String docId=snapshot.getId();
                            newUser.setId(docId);
                            newUser.setFirstName(snapshot.get("FirstName").toString());
                            newUser.setLastName(snapshot.get("LastName").toString());
                            newUser.setPhoneNumber(snapshot.get("phoneNumber").toString());


                        }
                        //open MainPage
                        Intent intent=new Intent(getApplicationContext(), MainPage.class);
                        startActivity(intent);
                        finish();
                }
            }});
            }
        else {
            //test this out
            //logic is if user !=null then
            Intent intent=new Intent(MainActivity.this,Login_Start_Activity.class);
            startActivity(intent);
            finish();
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = (ConstraintLayout) findViewById(R.id.SplashscreenBackground);
        Thread thread =new Thread(){
           @Override
           public void run() {
               try {
                   sleep(3000);

               }
               catch (Exception e){

               }
           }
        };

            thread.start();


        Thread checkInternetConnection =new Thread(){
            @Override
            public void run() {
                super.run();
             boolean internetchecker= (boolean) InternetChecker.internetIsConnected();
             while (internetchecker==true){

             }
             if(internetchecker==false){
                 Intent intent=new Intent(MainActivity.this,noInternet.class);
                 startActivity(intent);
                 finish();
             }
            }
        };
        checkInternetConnection.start();
        }
    }

