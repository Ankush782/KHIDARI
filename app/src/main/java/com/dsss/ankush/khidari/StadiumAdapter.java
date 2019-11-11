package com.dsss.ankush.khidari;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class StadiumAdapter extends PagerAdapter {
    ArrayList<Stadium> stadiums;
    Context c;
    public StadiumAdapter(ArrayList<Stadium> s,Context c) {
        super();
        stadiums=s;
        this.c=c;
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // super.instantiateItem(container, position);
        View imageLayout = LayoutInflater.from(c).inflate(R.layout.single__stadium_item, container, false);
        ImageButton lay=imageLayout.findViewById(R.id.stadium_image);
        TextView t=imageLayout.findViewById(R.id.stadium_discription);
        TextView name=imageLayout.findViewById(R.id.stadium_name);
        Stadium s=stadiums.get(position);
        lay.setImageResource(R.drawable.mohali);
        t.setText(s.Discription);
        name.setText(s.name);

        container.addView(imageLayout, 0);

        return imageLayout;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       // super.destroyItem(container, position, object);
        ((ViewPager) container).removeView((View) object);
    }

    @Nullable
    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public int getCount() {
        return stadiums.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
          return view.equals(object);
    }
}
