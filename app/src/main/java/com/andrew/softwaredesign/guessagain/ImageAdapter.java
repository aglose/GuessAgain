package com.andrew.softwaredesign.guessagain;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Andrew on 3/18/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    int count = 1;

    public ImageAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = inflater.inflate(R.layout.stack_categories, null);
            TextView textView = (TextView) grid.findViewById(R.id.categoryTextView);
            textView.setText(categories(context)[position]);
            textView.setTextColor(Color.WHITE);
        } else {
            grid = (View) convertView;
        }
        return grid;
    }



    private String[] categories(Context c) {
        return new String[]{
            c.getResources().getString(R.string.celebrities_Cat),
                    c.getResources().getString(R.string.movies_Cat),
                    c.getResources().getString(R.string.celebrities_Cat),
                    c.getResources().getString(R.string.celebrities_Cat),
                    c.getResources().getString(R.string.celebrities_Cat),
                    c.getResources().getString(R.string.celebrities_Cat)};
    }

}
