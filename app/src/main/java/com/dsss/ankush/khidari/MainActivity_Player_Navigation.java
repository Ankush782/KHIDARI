package com.dsss.ankush.khidari;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.method.DateTimeKeyListener;
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

public class MainActivity_Player_Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
     Player player;
     ImageView profile_pic;
     TextView name,email;
    ArrayList<Event> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__player__navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Upcomming Events");
        setSupportActionBar(toolbar);
        player=new Player();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        profile_pic=(ImageView)header.findViewById(R.id.imageViewplayer);
        name=(TextView)header.findViewById(R.id.nameplaeyr) ;
        email=(TextView)header.findViewById(R.id.emailplayer);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                player=dataSnapshot.getValue(Player.class);
                //  super.onStart();
                name.setText(player.getName());
                email.setText(player.getEmail());
                if(player.profile_pic==""){

                    profile_pic.setImageResource(R.drawable.icon);
                    profile_pic.setBackgroundColor(Color.WHITE);
                }
                else
                {
                    Glide.with(getApplicationContext()).load(player.profile_pic).apply(RequestOptions.circleCropTransform()).into(profile_pic);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        events=new ArrayList<>();
        DatabaseReference databaseReferences= FirebaseDatabase.getInstance().getReference().child("All_Events");
        databaseReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    events.add(d.getValue(Event.class));
                }
                ListView l=(ListView)findViewById(R.id.player_all_events);
                l.setAdapter(new PlayerAllEventsListAdapter(getApplicationContext(),events));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




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
        getMenuInflater().inflate(R.menu.main_activity__player__navigation, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == 0) {
          // Handle the camera action
        }
       else if(id==R.id.nav_various_coaches)
       {
           startActivity(new Intent(getApplicationContext(),CoachesActivity.class));
       }
       else if(id==R.id.sports_stadium_of_punjab)
       {
           startActivity(new Intent(getApplicationContext(),Stadium_Activity.class));
       }
       else if(id==R.id.coachingcenter)
       {
           startActivity(new Intent(getApplicationContext(),Stadium_Activity.class));
       }
       else if (id == R.id.nav_profile) {
           Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
           intent.setType("image/*");
           if(intent.resolveActivity(getPackageManager())!=null)
           {
               startActivityForResult(intent,1);
           }

       }else if (id == R.id.nav_apply_dponsership) {
           SponsershipApplyDialog sponsershipApplyDialog=new SponsershipApplyDialog();
          // Bundle b=new Bundle();
          // sponsershipApplyDialog.setArguments(new Bundle().);

           sponsershipApplyDialog.show(getFragmentManager(),"");

       } else if (id == R.id.nav_share_player) {
           Intent sendIntent = new Intent();
           sendIntent.setAction(Intent.ACTION_SEND);
           sendIntent.putExtra(Intent.EXTRA_TEXT,
                   "Hey check out this Sports  app at: https://play.google.com/store/apps/details?id=com.dsss.ankush.khidari");
           sendIntent.setType("text/plain");
           startActivity(sendIntent);


       } else if (id == R.id.nav_rate_player) {
           Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
           startActivity(rateIntent);

       }
       else
       {
           android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
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

           android.app.AlertDialog alertDialog = alertDialogBuilder.create();
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
            Uri photo=data.getData();
            FirebaseStorage.getInstance().getReference().child(new Date().getTime()+".jpg").putFile(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    player.setProfile_pic(taskSnapshot.getDownloadUrl().toString());
                     FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").setValue(player).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        Glide.with(getApplicationContext()).load(player.profile_pic).apply(RequestOptions.circleCropTransform()).into(profile_pic);
    }
}
