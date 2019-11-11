package com.dsss.ankush.khidari;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PlayerRegistrationFormActivity extends AppCompatActivity {
    EditText name,age,phone,state,city,street,phone_h,email;
    Spinner level,volun;
    Button apply;
    ApplicationForm applicationForm;
    Event e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_registration_form);
        applicationForm=new ApplicationForm();
        e=(Event)getIntent().getSerializableExtra("event");
        final String levels[]={"Beginner","Average","Highly Skilled"};
        final String vol[]={"Yes","No"};
        email=(EditText)findViewById(R.id.applicant_email);
        name=(EditText)findViewById(R.id.applicant_name);
        age=(EditText)findViewById(R.id.applicant_age);
        phone=(EditText)findViewById(R.id.applicant_phone);
        phone_h=(EditText)findViewById(R.id.applicant_phone_h);
        state=(EditText)findViewById(R.id.event_state_a);
        city=(EditText)findViewById(R.id.event_city_a);
        street=(EditText)findViewById(R.id.event_address_a);
        level=(Spinner)findViewById(R.id.level);
        volun=(Spinner)findViewById(R.id.volunteer);
        ArrayAdapter<String> adp=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,levels);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level.setAdapter(adp);
        ArrayAdapter<String> adps=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,vol);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        volun.setAdapter(adps);level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applicationForm.setLevel(levels[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                applicationForm.setLevel(levels[0]);

            }
        });
        volun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applicationForm.setVolunteer(vol[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                applicationForm.setVolunteer(vol[0]);

            }
        });
        findViewById(R.id.registerpp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View x;
                if(notEmpty())
                {
                    if(Integer.parseInt(e.getFee())>0)
                    {
                        Intent i=new Intent(getApplicationContext(),MakePaymentActivity.class);
                        i.putExtra("fee",e.fee);
                        i.putExtra("form",applicationForm);
                        i.putExtra("email",email.getText());
                        i.putExtra("event",e);
                        startActivity(i);

                    }
                    else
                    {
                        applicationForm.name=name.getText().toString();
                        applicationForm.email=email.getText().toString();
                        applicationForm.phone=phone.getText().toString();
                        applicationForm.address=street.getText().toString();
                        applicationForm.state=state.getText().toString();
                        applicationForm.city=city.getText().toString();
                        applicationForm.age=age.getText().toString();
                        FirebaseDatabase.getInstance().getReference().child("All_Events").child(e.name).child("Participants").child(phone.getText().toString()).setValue(applicationForm).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Snackbar.make(,)
                                FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(e.name).setValue(applicationForm);
                                finish();
                                final AlertDialog.Builder builder=new AlertDialog.Builder(PlayerRegistrationFormActivity.this);
                                builder.setMessage("Congratulation! Succesfully Registred");
                                builder.setTitle("Success");
                                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        builder.create().show();
                                        finish();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                final AlertDialog.Builder builder=new AlertDialog.Builder(PlayerRegistrationFormActivity.this);
                                builder.setMessage("Something went wrong ,Try latter");
                                builder.setTitle("Failure");
                                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });

                            }
                        });

                    }
                }

            }
        });

    }
    boolean notEmpty()
    {
        if(TextUtils.isEmpty(name.getText())||TextUtils.isEmpty(email.getText())||TextUtils.isEmpty(phone_h.getText())||TextUtils.isEmpty(phone.getText())||TextUtils.isEmpty(email.getText())||TextUtils.isEmpty(state.getText())||TextUtils.isEmpty(city.getText())||TextUtils.isEmpty(street.getText())||TextUtils.isEmpty(age.getText())||Integer.parseInt(age.getText().toString())<0)
        {
            Toast.makeText(getApplicationContext(),"All feilds are required and age must be possitive",Toast.LENGTH_LONG).show();
            return  false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        applicationForm.name=name.getText().toString();
        applicationForm.email=email.getText().toString();
        applicationForm.phone=phone.getText().toString();
        applicationForm.address=street.getText().toString();
        applicationForm.state=state.getText().toString();
        applicationForm.city=city.getText().toString();
        applicationForm.age=age.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("All_Events").child(e.name).child("Participants").child(email.getText().toString()).setValue(applicationForm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Snackbar.make(,)
                FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(e.name).setValue(applicationForm);
                finish();
                final AlertDialog.Builder builder=new AlertDialog.Builder(PlayerRegistrationFormActivity.this);
                builder.setMessage("Congratulation! Succesfully Registred");
                builder.setTitle("Success");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        builder.create().show();
                        finish();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(PlayerRegistrationFormActivity.this);
                builder.setMessage("Something went wrong ,Try latter");
                builder.setTitle("Failure");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

            }
        });
    }
}

