package com.example.miomode;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class roomlist extends ListView {
    String[] lb=new String[]{"二手","租房","楼盘","中介"};
    private int page;
    private ArrayList<HashMap<String, Object>> data;
    private ArrayList<HashMap<String, Object>> datamz;
    private Object tiaozhuan;

    public roomlist(Context context) {
        super(context);
        initview();
    }

    public void setTiaozhuan(Object tiaozhuan) {
        this.tiaozhuan = tiaozhuan;
    }

    public roomlist(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();
    }

    void initview()
    {
        datamz=new ArrayList<>();
        TOOL.GETS("/prod-api/api/house/housing/list", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONArray jsonArray =new JSONObject(response.body().string()).getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("img",jsonArray.getJSONObject(i).getString("pic"));
                        hashMap.put("h1",jsonArray.getJSONObject(i).getString("sourceName"));
                        hashMap.put("h2",jsonArray.getJSONObject(i).getString("areaSize"));
                        hashMap.put("h3",jsonArray.getJSONObject(i).getString("price"));
                        hashMap.put("h4",jsonArray.getJSONObject(i).getString("description"));
                        hashMap.put("h5",jsonArray.getJSONObject(i).getString("tel"));
                        hashMap.put("type",jsonArray.getJSONObject(i).getString("houseType"));
                        datamz.add(hashMap);
                    }
                    getnews(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getnews(int i)
    {
        page = i;
        initdata();
    }

    void initdata3()
    {
        ArrayList<HashMap<String,Object>> data=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            data.add(datamz.get(5+i));
        }
        setAdapter(new Imgadapter(getContext(),data, R.layout.newslist,
                new String[]{"h1","h2","h3","h4"},
                new int[]{R.id.h1,R.id.h2,R.id.h3,R.id.h4}));

    }

    public void  onseach(String str)
    {
        data.clear();
        for (HashMap<String,Object> i: datamz) {
            if (((String) i.get("h1")).contains(str))
                data.add(i);
        }
        setAdapter(new Imgadapter(getContext(), data,R.layout.newslist,
                new String[]{"h1","h2","h3","h4"},
                new int[]{R.id.h0,R.id.h1,R.id.h2,R.id.h3}));
        Toast.makeText(getContext(), "搜索成功", Toast.LENGTH_SHORT).show();
    }
    //contains
    void initdata()
    {
        data = new ArrayList<>();
        for (HashMap<String,Object> i: datamz) {
            if (i.get("type").equals(lb[page]))
                data.add(i);
        }
        setAdapter(new Imgadapter(getContext(), data,R.layout.newslist,
                new String[]{"h1","h2","h3","h4"},
                new int[]{R.id.h1,R.id.h2,R.id.h3,R.id.h4}));

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), (Class<?>) tiaozhuan);
                intent.putExtra("h1", String.valueOf(data.get(position).get("h1")));
                intent.putExtra("h2", String.valueOf(data.get(position).get("h2")));
                intent.putExtra("h3", String.valueOf(data.get(position).get("h3")));
                intent.putExtra("h4", String.valueOf(data.get(position).get("h4")));
                intent.putExtra("h5", String.valueOf(data.get(position).get("h5")));
                intent.putExtra("type", String.valueOf(data.get(position).get("type")));
                        intent.putExtra("img", String.valueOf(data.get(position).get("img")));
                getContext().startActivity(intent);
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
//1 45