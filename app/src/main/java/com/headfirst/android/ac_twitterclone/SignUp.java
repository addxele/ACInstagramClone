package com.headfirst.android.ac_twitterclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtPunchSpeed, edtPunchPower,
            edtKickSpeed, edtKickPower;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(SignUp.this);
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
    }

    @Override
    public void onClick(View v) {
        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.saveInBackground((e) -> {
                if (e == null) {
                    FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " was saved successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                } else {
                    FancyToast.makeText(SignUp.this, e.getMessage() + "", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage() + "", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }
    }

}
