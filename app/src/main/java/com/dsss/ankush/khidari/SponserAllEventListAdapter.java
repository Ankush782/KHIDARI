package com.dsss.ankush.khidari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class SponserAllEventListAdapter extends BaseAdapter {
    Context c;

    public SponserAllEventListAdapter(Context c, ArrayList<Event> events) {
        this.c = c;
        this.events = events;
    }

    ArrayList<Event> events;

    public SponserAllEventListAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(c).inflate(R.layout.single_event_item,null);
        TextView name=v.findViewById(R.id.event_item_name);
        TextView sports=v.findViewById(R.id.event_item_sports);
        TextView city=v.findViewById(R.id.event_item_city);
        TextView date=v.findViewById(R.id.event_item_date);
        TextView time=v.findViewById(R.id.event_item_time);
        TextView author=v.findViewById(R.id.event_sponser);
        ImageView i=v.findViewById(R.id.sponsership_pic);

        Event e=events.get(position);
        author.setText(e.getAuthor());
        Glide.with(c).load(e.profile_pic).apply(RequestOptions.circleCropTransform()).into(i);
        name.setText("NAME : "+e.getName());
        sports.setText("SPORTS : "+e.getSports());
        city.setText("CITY : "+e.getCity());
        date.setText("DATE : "+e.getDate());
        time.setText("TIME : "+e.getTime());
        return v;
    }
}
