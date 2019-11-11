package com.dsss.ankush.khidari;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CancelEventListAdapter extends BaseAdapter {
    Context c;
    Player p=new Player();
    Activity a;

    public CancelEventListAdapter(Context c, ArrayList<Event> events) {
        this.c = c;
        this.events = events;
        this.a=a;
    }

    ArrayList<Event> events;

    public CancelEventListAdapter() {
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

       final Event e=events.get(position);
        author.setText(e.getAuthor());
        Glide.with(c).load(e.profile_pic).apply(RequestOptions.circleCropTransform()).into(i);
        name.setText("NAME : "+e.getName());
        sports.setText("SPORTS : "+e.getSports());
        city.setText("CITY : "+e.getCity());
        date.setText("DATE : "+e.getDate());
        time.setText("TIME : "+e.getTime());
        //Player p=new Player();
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                p=dataSnapshot.getValue(Player.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       v.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(e.getName()).getRef().removeValue();
               FirebaseDatabase.getInstance().getReference().child("All_Events").child(e.getName()).child(p.getPhone()).getRef().removeValue();
               Toast.makeText(c,"Successfully deleted participation",Toast.LENGTH_LONG).show();



               }
       });

        return v;
    }
}
