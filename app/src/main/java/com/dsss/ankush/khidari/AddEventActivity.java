package com.dsss.ankush.khidari;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class AddEventActivity extends AppCompatActivity {
    EditText name,sports,date,time,state,city,address,a,b,c,note,fee;
    Button add;
    Event e;
    ProgressDialog pd;
    String p_name;
    Sponser sponser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        pd=new ProgressDialog(this);
        pd.setTitle("Alert");
        pd.setMessage("Please wait while adding");
        pd.setCancelable(false);
        sponser=(Sponser)getIntent().getSerializableExtra("sponser");
        fee=(EditText)findViewById(R.id.event_fee);
        add=(Button)findViewById(R.id.add_event);
        name=(EditText)findViewById(R.id.name_event);
        sports=(EditText)findViewById(R.id.event_sports);
        date=(EditText)findViewById(R.id.event_date);
        time=(EditText)findViewById(R.id.event_time);
        state=(EditText)findViewById(R.id.event_state);
        city=(EditText)findViewById(R.id.event_city);
        address=(EditText)findViewById(R.id.event_address);
        note=(EditText)findViewById(R.id.event_note);
        a=(EditText)findViewById(R.id.event_a);
        b=(EditText)findViewById(R.id.event_b);
        c=(EditText)findViewById(R.id.event_c);
        date.setOnClickListener(new View.OnClickListener() {
            @TargetApi(24)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddEventActivity.this,0);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String d= Integer.toString(dayOfMonth)+"/"+Integer.toString(month)+"/"+Integer.toString(year);
                        date.setText(d);
                    }
                });
                datePickerDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
           // @TargetApi(24)
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String t=Integer.toString(hourOfDay)+"::"+Integer.toString(minute);
                        time.setText(t);

                    }
                }        ,Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),true);
                timePickerDialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verify())
                {
                    pd.show();
                     e=new Event();
                     e.setProfile_pic(sponser.profile_pic);
                     e.setAuthor(sponser.name);
                    e.name=name.getText().toString();
                    e.sports=sports.getText().toString();
                    e.date=date.getText().toString();
                    e.time=time.getText().toString();
                    e.state=state.getText().toString();
                    e.city=city.getText().toString();
                    e.address=address.getText().toString();
                    e.fee=fee.getText().toString();
                    e.istprize=a.getText().toString();
                    e.istrunnerup=b.getText().toString();
                    e.secondrunnerup=c.getText().toString();
                    e.notes=TextUtils.isEmpty(note.getText().toString())?"":note.getText().toString();
                    FirebaseDatabase.getInstance().getReference().child("All_Events").child(e.name).setValue(e).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(e.name).setValue(e).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Event Added Succesfully",Toast.LENGTH_LONG).show();
                                    finish();pd.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Something went wrong Try again",Toast.LENGTH_LONG).show();
                                        pd.dismiss();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Something went wrong Try again",Toast.LENGTH_LONG).show();
                                    pd.dismiss();

                                }
                            });
                        }
                    });
                }
            }
        });
        }
        boolean verify()
        {
            if(TextUtils.isEmpty(name.getText()))
            {
                name.setError("Event name is required");
                return false;
            }else if(TextUtils.isEmpty(sports.getText()))
            {
                sports.setError("Sport is required");
                return false;
            }else if(TextUtils.isEmpty(state.getText()))
            {
                state.setError("State is required");
                return false;
            }else if(TextUtils.isEmpty(city.getText()))
            {
                city.setError("City is required");
                return false;
            }else if(TextUtils.isEmpty(address.getText()))
            {
                address.setError("Address is required");
                return false;
            }else if(TextUtils.isEmpty(date.getText()))
            {
                date.setError("Date is required");
                return false;
            }else if(TextUtils.isEmpty(time.getText()))
            {
                time.setError("Time is required");
                return false;
            }else if(TextUtils.isEmpty(a.getText()))
            {
                a.setError("Ist prize is required");
                return false;
            }else if(TextUtils.isEmpty(b.getText()))
            {
                b.setError("Ist Runner Up prize is required");
                return false;
            }else if(TextUtils.isEmpty(c.getText()))
            {
                c.setError("Second Runner Up prize is required");
                return false;
            }else if(TextUtils.isEmpty(fee.getText()))
            {
                fee.setError("This feind cant be Empty");
                return false;
            }
            return  true;
        }
        }
