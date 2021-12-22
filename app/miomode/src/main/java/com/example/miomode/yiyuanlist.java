package com.example.miomode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

class yiyuanlist extends ListView {

    private ArrayList<HashMap<String, Object>> data;
    private Object tiaozhuan;
    public yiyuanlist(Context context) {
        super(context);
        init();
    }

    public void setTiaozhuan(Object tiaozhuan) {
        this.tiaozhuan = tiaozhuan;
    }

    public yiyuanlist(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST));
    }




    void init()
    {
        data = new ArrayList<>();
        TOOL.GET("/prod-api/api/hospital/hospital/list", new TOOL.chuli() {
            @Override
            public void chuli(JSONArray jsonObject) throws JSONException {
                for (int i = 0; i < jsonObject.length(); i++) 
                {
                    String hc="";
                    for (int j = 0; j < jsonObject.getJSONObject(i).getInt("level"); j++) {
                        hc+="⭐";
                    }
                    HashMap<String, Object> hashMap=new HashMap<>();
                    hashMap.put("h0",jsonObject.getJSONObject(i).getString("hospitalName")+"\n"+hc);
                    hashMap.put("h1",jsonObject.getJSONObject(i).getString("hospitalName"));
                    hashMap.put("h2",jsonObject.getJSONObject(i).getString("brief"));
                    hashMap.put("img",jsonObject.getJSONObject(i).getString("imgUrl"));
                    data.add(hashMap);
                }
                if (getContext()!=null)
                    ((Activity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setAdapter(new Imgadapter(getContext(),data, R.layout.yiyuanitem,
                                    new String[]{"h0"},new int[]{R.id.h1}));
                            setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent=new Intent(getContext(), (Class<?>) tiaozhuan);
                                    intent.putExtra("h0", String.valueOf(data.get(position).get("h1")));
                                    intent.putExtra("h1", String.valueOf(data.get(position).get("h2")));
                                    getContext().startActivity(intent);
                                }
                            });
                        }
                    });
            }
        });
    }
}
