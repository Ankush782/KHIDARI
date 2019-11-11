package com.dsss.ankush.khidari;

import android.app.DialogFragment;
import android.graphics.Color;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class SponsershipApplyDialog extends DialogFragment {
    Button yes,no;
    Player player;
    @Override
    public void setStyle(int style, int theme) {
        super.setStyle(style, theme);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                player=dataSnapshot.getValue(Player.class);
                //  super.onStart();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return inflater.inflate(R.layout.sponsershipspplydialog,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yes=(Button)view.findViewById(R.id.apply_yes);
        no=(Button)view.findViewById(R.id.apply_no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Applied_For_SponserShip").child(new Date().toString()).setValue(player).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(getView(), "Succesfully Applied", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                       // getDialog().dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(getView(), "Something went wrong Apply later", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                       //  getDialog().dismiss();
                    }
                });

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();

            }
        });

    }

    public SponsershipApplyDialog() {
    }

}
