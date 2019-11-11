package com.dsss.ankush.khidari;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

public class MainActivityDrawerSponser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Sponser sponser;
    ImageView profile_pic;
    TextView name,email;
    ArrayList<Player> playerArrayList;
    ArrayList<String> ids;
    Player demo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer_sponser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sponser A Shinning Star");
        setSupportActionBar(toolbar);
        demo=new Player("A","B","C","D","E","F","G","H","I","j");

        playerArrayList=new ArrayList<>();
      //  playerArrayList.add(demo);
        sponser=new Sponser();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        profile_pic=(ImageView)header.findViewById(R.id.dprfile);
        name=(TextView)header.findViewById(R.id.dname) ;
        email=(TextView)header.findViewById(R.id.demail);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sponser=dataSnapshot.getValue(Sponser.class);
              //  super.onStart();
                name.setText(sponser.getName());
              email.setText(sponser.getEmail());

                   Glide.with(getApplicationContext()).load(sponser.getProfile_pic()).apply(RequestOptions.circleCropTransform()).into(profile_pic);

           }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         ids=new ArrayList<>(50);
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference().child("Applied_For_SponserShip");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playerArrayList.clear();
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    playerArrayList.add(d.getValue(Player.class));
                }


                ListView l=(ListView)findViewById(R.id.sponsership_applied_student);
                SponserShipAppliedStudentAdapter sponserShipAppliedStudentAdapter=new SponserShipAppliedStudentAdapter(playerArrayList,getApplicationContext());
                l.setAdapter(sponserShipAppliedStudentAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AddEventActivity.class);
                i.putExtra("sponser",sponser);
                startActivity(i);

             }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_drawer_sponser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
       super.onStart();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sponser) {
           // Handle the camera action
      } else if (id == R.id.nav_editevent) {
            startActivity(new Intent(getApplicationContext(),Sponser_Edit_Activity.class));

       }
        else if (id == R.id.Book_a_stadium) {
            startActivity(new Intent(getApplicationContext(), Stadium_Activity.class));
        }
       else if (id == R.id.nav_all_events) {
            startActivity(new Intent(getApplicationContext(),Sponser_All_Events.class));

       } else if (id == R.id.nav_sponser_edit) {
            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            if(intent.resolveActivity(getPackageManager())!=null)
            {
                startActivityForResult(intent,1);
            }

       } else if (id == R.id.nav_share_sponser) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out this Sports  app at: https://play.google.com/store/apps/details?id=com.dsss.ankush.khidari");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nave_rate_sponser) {
            Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
            startActivity(rateIntent);

       }
       else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Please wait");
        pd.setMessage("Uploading......");

        if(requestCode==1&&resultCode==RESULT_OK)
        {
            pd.show();
            Uri photo=data.getData();
            FirebaseStorage.getInstance().getReference().child(new Date().getTime()+".jpg").putFile(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sponser.setProfile_pic(taskSnapshot.getDownloadUrl().toString());
                    FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").setValue(sponser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            updateProfilePic();
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(),"Succesfully Updated",Toast.LENGTH_LONG).show();

                        }
                    });

                }
            });

        }
    }
    void updateProfilePic()
    {
        Glide.with(getApplicationContext()).load(sponser.profile_pic).apply(RequestOptions.circleCropTransform()).into(profile_pic);
    }
}
