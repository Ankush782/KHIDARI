package com.dsss.ankush.khidari;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class SponserReg extends AppCompatActivity {
    EditText n,e,p,c,pan,pa;
    Sponser  sponser;
    EditText d;
    ProgressDialog pd;
    Button r;
    AppCompatActivity appCompatActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser_reg);
        pd=new ProgressDialog(this);
        appCompatActivity=this;
        pd.setTitle("KHIDARI");
        pd.setMessage("Please wait...... verifying");
         pd.setCancelable(false);
        sponser=new Sponser();
        n=(EditText)findViewById(R.id.names);
        e=(EditText)findViewById(R.id.emails);
        p=(EditText)findViewById(R.id.phones);
        d=(EditText) findViewById(R.id.dobs);
        c=(EditText)findViewById(R.id.company);
        pan=(EditText)findViewById(R.id.pans);
        pa=(EditText)findViewById(R.id.passwordlogins);
        r=(Button)findViewById(R.id.registerp);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(24)
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(SponserReg.this,0);
                datePickerDialog.updateDate(1990,1,1);

                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dd= Integer.toString(dayOfMonth)+"/"+Integer.toString(month)+"/"+Integer.toString(year);
                        d.setText(dd);
                    }
                });
                datePickerDialog.show();
            }
        });
        final mcallback m=new mcallback();
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(isverifypassword(pa.getText().toString())&&notEmpty())
              {
                  pd.show();
                  sponser.setName(n.getText().toString());
                  sponser.setEmail(e.getText().toString());
                  sponser.setMobile(p.getText().toString());
                  sponser.setDob(d.getText().toString());
                  sponser.setComapany(c.getText().toString());
                  sponser.setPan(pan.getText().toString());
                  sponser.setPassword(pa.getText().toString());
                  PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+p.getText().toString(),60, TimeUnit.SECONDS,appCompatActivity,m);


              }
              else
              {
                  Toast.makeText(getApplicationContext(),"Password should be atleast 8 character long and must conatin a char and numnber and mobile number must be valid,All feilds are required",Toast.LENGTH_LONG).show();

              }
            }
        });

    }
    boolean isverifypassword(String s)
    {
        if(s.length()<8)
        {
            return false;
        }
        boolean n=false;
        boolean c=false;
        for(char cc:s.toCharArray())
        {
            if(Character.isAlphabetic(cc))
                c=true;
            if(Character.isDigit(cc))
                n=true;


        }
        if(n&&c)
            return true;
        else
            return false;
    }
    boolean notEmpty()
    {
        if(e.getText().toString().isEmpty()||n.getText().toString().isEmpty()||p.getText().toString().isEmpty()||c.getText().toString().isEmpty()||pan.getText().toString().isEmpty()||pa.getText().toString().isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    class mcallback extends PhoneAuthProvider.OnVerificationStateChangedCallbacks
    {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(e.getText().toString(),pa.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Type").setValue("Sponser");
                    FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").setValue(sponser);
                    startActivity(new Intent(getApplicationContext(),MainActivityDrawerSponser.class));
                    pd.dismiss();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();

                }
            });


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }
    }
}
