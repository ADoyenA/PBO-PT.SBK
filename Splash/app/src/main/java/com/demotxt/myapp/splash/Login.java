package com.demotxt.myapp.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class Login extends AppCompatActivity {

    Button btLogin;
    EditText edtEmail,edtPassword;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);
        edtEmail = (EditText) findViewById(R.id.emailinput);
        edtPassword = (EditText) findViewById(R.id.passwordinput);
        btLogin = (Button) findViewById(R.id.btSignIn);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                Boolean Chkemailpass = db.emailpassword(email,password);
                if (Chkemailpass==true) {
                    if (Chkemailpass != null) {
                        Intent i = new Intent(Login.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();

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

