package com.dsss.ankush.khidari;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAllEventsListAdapter extends BaseAdapter{
    Context c;

    public PlayerAllEventsListAdapter(Context c, ArrayList<Event> events) {
        this.c = c;
        this.events = events;
    }

    ArrayList<Event> events;

    public PlayerAllEventsListAdapter() {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(c).inflate(R.layout.single_event_item,null);
        TextView name=v.findViewById(R.id.event_item_name);
        TextView sports=v.findViewById(R.id.event_item_sports);
        TextView city=v.findViewById(R.id.event_item_city);
        TextView date=v.findViewById(R.id.event_item_date);
        TextView time=v.findViewById(R.id.event_item_time);
        Event e=events.get(position);
        name.setText("NAME : "+e.getName());
        sports.setText("SPORTS : "+e.getSports());
        city.setText("CITY : "+e.getCity());
        date.setText("DATE : "+e.getDate());
        time.setText("TIME : "+e.getTime());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(c,Show_Event_Activity.class);

                i.putExtra("event",events.get(position));
                c.startActivity(i);
            }
        });
        return v;
    }
}
