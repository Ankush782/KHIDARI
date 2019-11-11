package com.dsss.ankush.khidari;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Sponser_Edit_Activity extends AppCompatActivity {
    java.util.ArrayList<Event> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser__edit_);
         android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_edit_event
         );
        toolbar.setTitle("  Select Event");
        setSupportActionBar(toolbar);
         events=new java.util.ArrayList<>();
        com.google.firebase.database.DatabaseReference databaseReference= com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child(com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events");
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                events.clear();
                for(com.google.firebase.database.DataSnapshot d:dataSnapshot.getChildren())
                {
                    events.add(d.getValue(Event.class));
                }
                android.widget.ListView l=(android.widget.ListView)findViewById(R.id.sponser_edit_event_list);
                l.setAdapter(new Edit_Event_Adapter(getApplicationContext(),events));
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {

            }
        });
    }
}
