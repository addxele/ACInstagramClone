package com.headfirst.android.ac_twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignUpUserName, edtSignUpEmail, edtSignUpPassword;
    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");

        edtSignUpEmail = findViewById(R.id.edtSignUpEmailAddress);
        edtSignUpUserName = findViewById(R.id.edtSignUpUserName);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        edtSignUpPassword.setOnKeyListener(new  View.OnKeyListener(){
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUp);
                }
                return false;
            }
        });
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(SignUp.this);
        btnLogin.setOnClickListener(SignUp.this);
        if (ParseUser.getCurrentUser() != null) {
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSignUp:
                if (edtSignUpEmail.getText().toString().equals("")
                        || edtSignUpUserName.getText().toString().equals("")
                        || edtSignUpPassword.getText().toString().equals("")) {
                    FancyToast.makeText(this, "Email, Username, Password is required", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                } else {

                    final ParseUser appUser = new ParseUser();
                    appUser.setPassword(edtSignUpEmail.getText().toString());
                    appUser.setUsername(edtSignUpUserName.getText().toString());
                    appUser.setPassword(edtSignUpPassword.getText().toString());

                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + edtSignUpUserName.getText().toString());
                    progressDialog.show();
                    appUser.signUpInBackground(e -> {
                        if (e == null) {
                            FancyToast.makeText(this, appUser.getUsername() + " is signed up", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            transitionToSocialMediaActivity();
                        } else {
                            FancyToast.makeText(this, e.getMessage() + "", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                        progressDialog.dismiss();
                    });
                }
                    break;
                    case R.id.btnLogin:
                        Intent intent = new Intent(SignUp.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }

    }

    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}
