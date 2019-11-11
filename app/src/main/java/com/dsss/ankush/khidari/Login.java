package com.dsss.ankush.khidari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
   TextView register,forgot;
   EditText email,password;
   Button login;
   String e,p;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgot=(TextView)findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pd=new ProgressDialog(this);
        pd.setTitle("KHIDARI");
        pd.setCancelable(false);
        pd.setMessage("Please wait......");
        register=(TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragment mdf=new MyDialogFragment();
                mdf.show(getFragmentManager(),"");
            }
        });
        email=(EditText)findViewById(R.id.emaillogin);
        password=(EditText)findViewById(R.id.passwordlogin);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty())
                {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Password reset mail is sent",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Please ensure that user with this email exists and mail entered is correct",Toast.LENGTH_LONG).show();

                        }
                    });

                }

            }
        });
       login=(Button)findViewById(R.id.login);
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               e=email.getText().toString();
               p=password.getText().toString();
               if(true&&!e.isEmpty()&&!p.isEmpty())
               {
                   pd.show();
                   FirebaseAuth.getInstance().signInWithEmailAndPassword(e,p).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           DatabaseReference type= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Type").getRef();
                           type.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {
                                   String v=dataSnapshot.getValue().toString();
                                   if(v.equals("Player"))
                                   {
                                       startActivity(new Intent(getApplicationContext(),MainActivity_Player_Navigation.class));
                                        pd.dismiss();
                                        finish();

                                   }
                                   else
                                   {
                                       startActivity(new Intent(getApplicationContext(),MainActivityDrawerSponser.class));

                                       pd.dismiss();
                                       finish();
                                   }


                               }

                               @Override
                               public void onCancelled(DatabaseError databaseError) {

                               }
                           });

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                          pd.dismiss();
                          Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                       }
                   });
               }
               else
               {
                   Toast.makeText(getApplicationContext(),"Password should be atleast 8 character long and must conatin a char and numnber , Email must be valid",Toast.LENGTH_LONG).show();
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
}
