package com.example.bigfamilyv20.Activities;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bigfamilyv20.R;

public class Login_Start_Activity extends AppCompatActivity {
    private static Button sign_up;
    private static TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__start_);
        login=(TextView)findViewById(R.id.LogInText1) ;
        sign_up=(Button)findViewById(R.id.SignUp);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),SignUp_First_Screen.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LogIn_Page.class);
                startActivity(intent);
            }
        });
    }
}
