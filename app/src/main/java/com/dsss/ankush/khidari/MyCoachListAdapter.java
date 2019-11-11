package com.dsss.ankush.khidari;

import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MyCoachListAdapter extends BaseAdapter {
    ArrayList<Coach> coaches;Context c;
    MyCoachListAdapter(ArrayList<Coach> coaches, Context c)
    {
        this.coaches=coaches;
        this.c=c;
    }
    @Override
    public int getCount() {
        return coaches.size();
    }

    @Override
    public Object getItem(int position) {
        return coaches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(c).inflate(R.layout.single_coach_item,null);
        ImageButton ib=v.findViewById(R.id.coach_image);
        TextView name=v.findViewById(R.id.coach_name);
        TextView m=v.findViewById(R.id.multiline);
        Glide.with(c).load(coaches.get(position).id).apply(RequestOptions.circleCropTransform()).into(ib);
        name.setText(coaches.get(position).name);
        TextView desc=v.findViewById(R.id.multiline);
        desc.setText(coaches.get(position).desc);
        ImageButton b1,b2,b3;
        b1=v.findViewById(R.id.call);
        b2=v.findViewById(R.id.email);
        b3=v.findViewById(R.id.mess);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri="tel:"+coaches.get(position).call.trim();
                Intent i=new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse(uri));
                c.startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri="sms:"+coaches.get(position).call.trim();
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(uri));
                c.startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                c.startActivity(Intent.createChooser(i,coaches.get(position).email.trim()));
            }
        });
        return v;
    }
}
