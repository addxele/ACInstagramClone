package com.headfirst.android.ac_twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtLoginName, edtLoginPassword;
    private Button btnLogin2, btnSignUp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In");

        edtLoginName = findViewById(R.id.edtLoginName);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnLogin2 = findViewById(R.id.btnLogin2);
        btnSignUp2 = findViewById(R.id.btnSignUp2);

        btnSignUp2.setOnClickListener(LoginActivity.this);
        btnLogin2.setOnClickListener(LoginActivity.this);
        if (ParseUser.getCurrentUser() != null) {
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
           case R.id.btnLogin2:
               if (edtLoginPassword.getText().toString().equals("")
                       || edtLoginName.getText().toString().equals("")) {
                   FancyToast.makeText(this, "User name and password is required", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

               }else {
                   ParseUser appUser = new ParseUser();
                   appUser.logInInBackground(edtLoginName.getText().toString(), edtLoginPassword.getText().toString(), (user, e) -> {
                       if (user != null && e == null) {
                           FancyToast.makeText(this, user.getUsername() + " is logged in successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                           transitionToSocialMediaActivity();
                       } else {
                           FancyToast.makeText(this, e.getMessage() + "", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                       }
                   });
               }
                break;
            case R.id.btnSignUp2:
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                break;
        }
    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
