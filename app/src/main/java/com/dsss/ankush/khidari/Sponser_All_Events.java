package com.dsss.ankush.khidari;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Sponser_All_Events extends AppCompatActivity {
    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser__all__events);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("  All Events");
        setSupportActionBar(toolbar);

        events=new ArrayList<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("All_Events");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    events.add(d.getValue(Event.class));
                }
                ListView l=(ListView)findViewById(R.id.sponser_all_events);
                l.setAdapter(new SponserAllEventListAdapter(getApplicationContext(),events));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
