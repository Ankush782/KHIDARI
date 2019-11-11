package com.dsss.ankush.khidari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class SponserShipAppliedStudentAdapter extends BaseAdapter {


    ArrayList<Player> playerArrayList=new ArrayList<>();
    Context c;

    public SponserShipAppliedStudentAdapter(ArrayList<Player> playerArrayList, Context c) {
        this.playerArrayList = playerArrayList;
        this.c = c;
    }

    @Override
    public int getCount() {
        return playerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return playerArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(c).inflate(R.layout.sponsership_applied_student_list_item,null);
        ImageView i=v.findViewById(R.id.sponsership_pic);
        TextView name=v.findViewById(R.id.sponsership_name);
        TextView games=v.findViewById(R.id.sponsership_games);
        TextView email=v.findViewById(R.id.sponsership_email);
        Player p=playerArrayList.get(position);

            Glide.with(c).load(p.profile_pic).apply(RequestOptions.circleCropTransform()).into(i);

        name.setText("PLayer name : "+p.name);
        games.setText("Sports : "+p.gameplayed);
        email.setText("Email : "+p.email);
        return v;
    }
}
