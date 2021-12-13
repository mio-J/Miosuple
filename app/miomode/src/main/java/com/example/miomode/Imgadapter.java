package com.example.miomode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Imgadapter extends SimpleAdapter {

    Context context;
    ArrayList<HashMap<String,Object>> data;
    public Imgadapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context=context;
        this.data= (ArrayList<HashMap<String, Object>>) data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= super.getView(position, convertView, parent);
        ImageView imageView=view.findViewById(R.id.image);
        Glide.with(context).load(TOOL.mainurl+data.get(position).get("img")).into(imageView);
        return view;
    }
}
