package com.example.bigfamilyv20.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bigfamilyv20.Entities.User;
import com.example.bigfamilyv20.R;
import com.example.bigfamilyv20.Utils.Password;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LogIn_Page extends AppCompatActivity {
    private static EditText phone,pass;
    private static Button login;
    private static ProgressBar bar;
    private static FirebaseFirestore firebaseFirestore;
    private static CheckBox box;
    private static String docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in__page);
        login=(Button)findViewById(R.id.login_btn);
        phone=(EditText)findViewById(R.id.login_phone);
        pass=(EditText)findViewById(R.id.login_pass);
        bar=(ProgressBar)findViewById(R.id.bar_login);
        box=(CheckBox)findViewById(R.id.checkBox_SHOW_PASSWORRD);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(box.isChecked()){
                    pass.setTransformationMethod(null);
                }
                else{
                    pass.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bar.setVisibility(View.VISIBLE);
                final String phoner=phone.getText().toString().trim();
                final String password=pass.getText().toString().trim();

                if(!phoner.isEmpty() && !password.isEmpty()) {

                    if(phoner.startsWith("07")){
                        phoner.replace("07","+254");
                    }

                    if(phoner.startsWith("7")){
                        phoner.replace("7","+2547");
                    }

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    final CollectionReference reference1 = firebaseFirestore.collection("Users");
                    reference1
                            .whereEqualTo("phoneNumber", phoner)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                final User newUser=new User();
                                for (DocumentSnapshot snapshot : task.getResult()) {
                                    docId=snapshot.getId();
                                    newUser.setId(docId);
                                    newUser.setFirstName(snapshot.get("FirstName").toString());
                                    newUser.setLastName(snapshot.get("LastName").toString());
                                    newUser.setPhoneNumber(snapshot.get("phoneNumber").toString());

                                }

                            reference1.document(docId).collection("securityDetail").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for (DocumentSnapshot snapshot1:task.getResult()){
                                            String u_pass = snapshot1.get("U_PASS").toString();
                                            String u_salt = snapshot1.get("U_SALT").toString();
                                            if (Password.authenticatePassword(password, u_pass, u_salt) == true) {
                                               Intent confirmation=new Intent(getApplicationContext(),confirmation_message.class);
                                               confirmation.putExtra("UserPhone",phoner);
                                               confirmation.putExtra("login_path","logInExistingUser");
                                               startActivity(confirmation);

                                            } else {
                                                Toast.makeText(LogIn_Page.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }}
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LogIn_Page.this, "error getting user cred", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }}
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LogIn_Page.this, "error getting user", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            else {
                    Toast.makeText(LogIn_Page.this, "Fill In All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
