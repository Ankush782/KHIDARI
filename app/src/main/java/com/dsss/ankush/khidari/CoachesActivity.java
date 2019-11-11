package com.dsss.ankush.khidari;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CoachesActivity extends AppCompatActivity {
   ArrayList<Coach> coaches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coaches=new ArrayList<>();
        Coach c=new Coach();
        c.name="William Anna";
        c.call="8283872642";
        c.email="kumarankush782@gmail.com";
                c.id=(R.drawable.man);
                c.desc="William Anna is a Coach of Cricket for last four years ,He have been coaching punjab cricket team since last last two years .He is from Ludhiana and have played cricket for India team also";
                for(int i=0;i<10;i++)
                {
                    coaches.add(c);
                }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(CoachesActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CoachesActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
            }
        setContentView(R.layout.activity_coaches);
        ListView l=(ListView)findViewById(R.id.coach_list);
        l.setAdapter(new MyCoachListAdapter(coaches,getApplicationContext()));

    }
}}
