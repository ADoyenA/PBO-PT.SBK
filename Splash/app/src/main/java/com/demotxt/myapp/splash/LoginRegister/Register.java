package com.demotxt.myapp.splash.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demotxt.myapp.splash.Database.Login.DatabaseHelper;
import com.demotxt.myapp.splash.R;

public class Register extends AppCompatActivity {

    DatabaseHelper db;
    Button register,login;
    EditText edtEmail,edtPassword,edtCPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.pass);
        edtCPassword = findViewById(R.id.cpass);
        register = findViewById(R.id.btSignUp);
        login = findViewById(R.id.btHaveAcc);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this,Login.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = edtEmail.getText().toString();
                String s2 = edtPassword.getText().toString();
                String s3 = edtCPassword.getText().toString();
                if (s1.equals("") || s2.equals("") || s3.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    if (s2.equals(s3)){
                        Boolean chkemail = db.chkemail(s1);
                        if (chkemail==true){
                            Boolean insert = db.insertUser(s1,s2);
                            if (insert==true){
                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Email Already exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(),"Password do not match",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
