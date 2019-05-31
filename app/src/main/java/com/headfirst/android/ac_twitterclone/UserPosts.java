package com.headfirst.android.ac_twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

public class UserPosts extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        linearLayout =  findViewById(R.id.linearLayout);

        Intent receivedIntentObject = getIntent();
        final String receivedUserName = receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(this, receivedUserName, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

        setTitle(receivedUserName + "'s posts");
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username", receivedUserName);
        parseQuery.orderByDescending("createdAt");

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        parseQuery.findInBackground((objects,e) -> {
            if (objects.size() > 0 && e == null) {
                for (ParseObject post : objects) {
                    TextView postDes = new TextView(UserPosts.this);
                    postDes.setText(post.get("image_des") + "");
                    ParseFile postPicture = (ParseFile) post.get("picture");
                    postPicture.getDataInBackground((data, ex) ->{
                        if (data != null && ex == null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            ImageView postImage = new ImageView(UserPosts.this);
                            LinearLayout.LayoutParams imageView_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            imageView_params.setMargins(5, 5, 5, 5);
                            postImage.setLayoutParams(imageView_params);
                            postImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            postImage.setImageBitmap(bitmap);

                            LinearLayout.LayoutParams des_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            des_param.setMargins(5,5,5,15);
                            postDes.setLayoutParams(des_param);
                            postDes.setGravity(Gravity.CENTER);
                            postDes.setBackgroundColor(Color.RED);
                            postDes.setTextColor(Color.WHITE);
                            postDes.setTextSize(30f);

                            linearLayout.addView(postImage);
                            linearLayout.addView(postDes);
                        }
                    });
                }
            }else{
                FancyToast.makeText(UserPosts.this, receivedUserName + " doesn't have any post yet"
                        , FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                finish();
            }
            dialog.dismiss();
        });
    }
}
