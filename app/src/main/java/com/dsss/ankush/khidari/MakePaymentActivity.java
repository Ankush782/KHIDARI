package com.dsss.ankush.khidari;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

import javax.xml.transform.Result;

public class MakePaymentActivity extends AppCompatActivity {
    TextView amount;
    Button pay,payc;
    ApplicationForm form;
    String email;
    Event e;
    final static  String Client_Id="AWAuTRUDzBohEECjcJzUcOynb8xnENhhN9i2sg5uPeBEo_5X4VSDwF4lW-JON24l2Pi89qBUn6koisUb";
    static  String env= PayPalConfiguration.ENVIRONMENT_SANDBOX;
     static PayPalConfiguration fig = new PayPalConfiguration()
            .environment(env)
            .clientId(Client_Id);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, fig);
        startService(intent);
        email=getIntent().getStringExtra("email");
        form=(ApplicationForm) getIntent().getSerializableExtra("form");
        e=(Event)getIntent().getSerializableExtra("event");
        amount=(TextView)findViewById(R.id.amount);
        pay=(Button)findViewById(R.id.pay);
        payc=(Button)findViewById(R.id.payc);
        payc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPayment thingToBuy=new PayPalPayment(new BigDecimal(40),"USD","Game Participation",PayPalPayment.PAYMENT_INTENT_SALE);
                Intent i=new Intent(MakePaymentActivity.this, PaymentActivity.class);
                i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,fig);

                i.putExtra(PaymentActivity.EXTRA_PAYMENT,thingToBuy);



                startActivityForResult(i,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Event e=(Event)getIntent().getSerializableExtra("event");
        if(resultCode== Activity.RESULT_OK)
        {
            if(requestCode==2) {
                FirebaseDatabase.getInstance().getReference().child("All_Events").child(e.name).child(email).setValue(form).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Snackbar.make(,)
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MakePaymentActivity.this);
                        builder.setMessage("Congratulation! Succesfully Registred");
                        builder.setTitle("Success");
                        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MakePaymentActivity.this);
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
            } }else if(resultCode==Activity.RESULT_CANCELED)
        {
            final AlertDialog.Builder builder=new AlertDialog.Builder(MakePaymentActivity.this);
            builder.setMessage("Something went wrong ,Try latter");
            builder.setTitle("Payment Failure");
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
    }
}
