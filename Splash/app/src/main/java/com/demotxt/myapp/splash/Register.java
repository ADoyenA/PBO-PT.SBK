package com.demotxt.myapp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Register extends AppCompatActivity {

    DatabaseHelper db;
    Button register,login;
    EditText edtEmail,edtPassword,edtCPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        edtEmail = (EditText) findViewById(R.id.email);
        edtPassword = (EditText) findViewById(R.id.pass);
        edtCPassword = (EditText) findViewById(R.id.cpass);
        register = (Button) findViewById(R.id.btSignUp);
        login = (Button) findViewById(R.id.btHaveAcc);

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
                if (edtEmail.equals("")||edtPassword.equals("")||edtCPassword.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    if (s2.equals(s3)){
                        Boolean chkemail = db.chkemail(s1);
                        if (chkemail==true){
                            Boolean insert = db.insert(s1,s2);
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

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
            return true;
        }else {
            return false;
        }
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
