package com.dsss.ankush.khidari;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CancelTournamentActivity extends AppCompatActivity {
    ArrayList<Event> events;
    ArrayList<Event> eventss;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cancel_tournament);
        eventss=new ArrayList<>();
        events=new ArrayList<>();
        list=(ListView)findViewById(R.id.cancel_tournament_list);

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                eventss.clear();
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    events.add(d.getValue(Event.class));
                }
                FirebaseDatabase.getInstance().getReference().child("All_Events").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dd:dataSnapshot.getChildren())
                        {
                            Event ee=dd.getValue(Event.class);
                            for(Event x:events)
                            {
                                if(x.name.equals(ee.name))
                                {
                                    eventss.add(ee);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                list.setAdapter(new CancelEventListAdapter(getApplicationContext(),eventss));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
