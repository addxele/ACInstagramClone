package com.headfirst.android.ac_twitterclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUserName, edtPassword, edtUserLogin, edtPasswordLogin;
    private Button btnSignUpUser, btnLoginUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserLogin = findViewById(R.id.edtUserLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnSignUpUser = findViewById(R.id.btnSignUpUser);
        btnLoginUser = findViewById(R.id.btnLoginUser);

        btnSignUpUser.setOnClickListener(SignUpLoginActivity.this);
        btnLoginUser.setOnClickListener(SignUpLoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSignUpUser:
                ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUserName.getText().toString());
                appUser.setPassword(edtPassword.getText().toString());

                appUser.signUpInBackground(e -> {
                    if (e == null) {
                        FancyToast.makeText(this, appUser.get("username") + " is signed up successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        FancyToast.makeText(this, e.getMessage() + "", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                });
                break;
            case R.id.btnLoginUser:
                ParseUser.logInInBackground(edtUserLogin.getText().toString(), edtPasswordLogin.getText().toString(), (user, e) -> {
                    if (user != null && e == null) {
                        FancyToast.makeText(this, user.get("username") + " is login successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                    } else {
                        FancyToast.makeText(this, e.getMessage() + "", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                });

                break;
        }
    }
}
