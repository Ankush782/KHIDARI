package com.dsss.ankush.khidari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

public class Show_Event_Activity extends AppCompatActivity {
    TextView name,sport,schedule,venue,prrizes,notes,fee,author;
    Button apply;
    Event e;

    ImageView i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__event_);

        i=(ImageView)findViewById(R.id.event_image);
        author=(TextView)findViewById(R.id.event_author_show);
        name= (TextView)findViewById(R.id.event_name_show);
        sport=(TextView)findViewById(R.id.event_Sport_show);
        schedule=(TextView)findViewById(R.id.event_time_show);
        venue=(TextView)findViewById(R.id.event_venue_show);
        prrizes=(TextView)findViewById(R.id.event_prize_show);
        notes=(TextView)findViewById(R.id.event__special_notes);
        fee=(TextView)findViewById(R.id.event_fee_show);
        e=(Event)getIntent().getSerializableExtra("event");
        name.setText(e.getName());
        sport.setText(e.getSports());
        Glide.with(getApplicationContext()).load(e.profile_pic).apply(RequestOptions.circleCropTransform()).into(i);
        author.setText("Organized By : "+e.getAuthor());
        schedule.setText("Date : "+e.getDate()+"\nTime : "+e.getTime());
        venue.setText("Address : "+e.getAddress()+"\nState : "+e.getState()+"\nCity : "+e.getCity());
        fee.setText(e.getFee());
        prrizes.setText("First prize : "+e.getIstprize()+"\n"+"First Runner Up : "+e.getIstrunnerup()+"\nSecond Runnner up : "+e.getSecondrunnerup());
        notes.setText(e.getNotes());
        apply=(Button)findViewById(R.id.event_apply_show);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PlayerRegistrationFormActivity.class);
                i.putExtra("event",e);
                startActivity(i);
            }
        });

    }
}
