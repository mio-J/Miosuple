package com.example.miomode;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Newslist extends ListView {
    ArrayList<HashMap<String,Object>> data;

    public Newslist(Context context) {
        super(context);
    }

    public Newslist(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),Newsinformation.class);
                intent.putExtra("h1", String.valueOf(data.get(position).get("h1")));
                intent.putExtra("h2", String.valueOf(data.get(position).get("h2")));
                intent.putExtra("img", String.valueOf(data.get(position).get("img")));
                intent.putExtra("id", String.valueOf(data.get(position).get("id")));
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST));
    }

    public void setAlldata(ArrayList<HashMap<String, Object>> alldata) {
        TOOL.Alldata = alldata;
    }

    public void getnews(int page)
    {
        data=new ArrayList<>();
        String[] id=new String[]{"13","14","15","24","5"};
        for (int i = 0; i < TOOL.Alldata.size(); i++) {
            if (TOOL.Alldata.get(i).get("type").equals(id[page]))
                data.add(TOOL.Alldata.get(i));
        }
        setAdapter(new Imgadapter(getContext(),data,R.layout.newslist,
                new String[]{"h1","h2","h3","h4","h5"},new int[]{R.id.h1,R.id.h2,R.id.h3,R.id.h4,R.id.h5}));

    }

    public void get3()
    {
        data=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
                data.add(TOOL.Alldata.get(i));
        }
        setAdapter(new Imgadapter(getContext(),data,R.layout.newslist,
                new String[]{"h1","h2","h3","h4","h5"},new int[]{R.id.h1,R.id.h2,R.id.h3,R.id.h4,R.id.h5}));

    }
}
