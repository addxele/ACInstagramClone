package com.headfirst.android.ac_twitterclone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class SocialMediaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        setTitle("Social Media App");

        toolbar = findViewById(R.id.myToolbar);
        viewPager = findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabLayout = findViewById(R.id.tabLayout);

        setSupportActionBar(toolbar);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager, false);
    }
}
