package com.dsss.ankush.khidari;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Stadium_Activity extends AppCompatActivity {
    ArrayList<Stadium> s=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium_);
        Stadium ss=new Stadium();
        ss.id= R.drawable.mohali;
        ss.name="Mahender Singh Stadium,Mohali";
        ss.Discription="The Punjab Cricket Association is the governing body for the development and organization of the game of cricket in the State of Punjab and Union Territory of Chandigarh. PCA has a long history because the state of Punjab had undergone political and geographical changes during the last 60 years most of the area of joint Punjab having gone to Pakistan. The PCA functions from present day Punjab and Union Territory of Chandigarh. \n";
        for(int i=0;i<10;i++)
        {
            s.add(ss);
        }
        ViewPager  viewPager=(ViewPager)findViewById(R.id.stadium_view_pager);
        viewPager.setAdapter(new StadiumAdapter(s,getApplicationContext()));



    }
}
