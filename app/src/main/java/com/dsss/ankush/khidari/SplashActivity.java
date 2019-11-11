package com.dsss.ankush.khidari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity implements Runnable{
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pd=new ProgressDialog(this);
        pd.setTitle("KHIDARI");
        pd.setMessage("Please wait......");
        Thread t=new Thread(this);
        t.start();

    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            if(FirebaseAuth.getInstance().getCurrentUser()==null) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
            else
            {
                DatabaseReference type= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Type").getRef();
                type.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v=dataSnapshot.getValue().toString();
                        if(v.equals("Player"))
                        {
                            startActivity(new Intent(getApplicationContext(),MainActivity_Player_Navigation.class));
                            pd.dismiss();

                        }
                        else
                        {
                            startActivity(new Intent(getApplicationContext(),MainActivityDrawerSponser.class));

                            pd.dismiss();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
