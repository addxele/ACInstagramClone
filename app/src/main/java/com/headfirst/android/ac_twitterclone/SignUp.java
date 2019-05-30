package com.headfirst.android.ac_twitterclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtPunchSpeed, edtPunchPower,
            edtKickSpeed, edtKickPower;
    private Button btnSave, btnGetAllData;
    private TextView txtGetData;
    private String allKickBoxer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        btnSave = findViewById(R.id.btnSave);
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);

        btnSave.setOnClickListener(SignUp.this);
        txtGetData.setOnClickListener(SignUp.this);
        btnGetAllData.setOnClickListener(SignUp.this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSave:
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
            break;
            case R.id.txtGetData:
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("7n1qaDJ7u8", (object, e) -> {
                    if (object != null && e == null) {
                        txtGetData.setText( object.get("name") + " - " + "" +
                                "\nPunch Power : " + object.get("punchPower") +
                                "\nPunch Speed : " + object.get("punchSpeed") +
                                "\nKick Power :" + object.get("kickPower") +
                                "\nKick Speed :" + object.get("kickSpeed"));
                    }
                });
                break;

            case R.id.btnGetAllData:
                allKickBoxer = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground((objects, e) -> {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (ParseObject kickBoxer : objects) {
                                allKickBoxer = allKickBoxer + kickBoxer.get("name") + "\n";
                            }
                            FancyToast.makeText(this, allKickBoxer + "", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        }else{
                            FancyToast.makeText(this, "Success", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        }
                    }
                });
                break;
        }

    }
}
