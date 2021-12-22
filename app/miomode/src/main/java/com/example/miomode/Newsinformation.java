package com.example.miomode;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

public class Newsinformation extends AppCompatActivity {

    private ImageView imageView;
    private TextView con;
    private Newslist newslist;
    private TextView textView5;
    private EditText editTextTextPersonName;
    private Button button;
    private LinearLayout pinglun;
    private Intent intent;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsinformation);
        initView();
        intent = getIntent();
        Glide.with(getApplicationContext()).load(TOOL.mainurl+ intent.getStringExtra("img")).into(imageView);
        con.setText(intent.getStringExtra("h1"));
        getSupportActionBar().setTitle(intent.getStringExtra("h0"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newslist.get3();
        pinglunset();
    }

    private void pinglunset() {
        TOOL.GET("/prod-api/api/park/press/comments/list?newsId="+intent.getStringExtra("id"), new TOOL.chuli() {
            @Override
            public void chuli(JSONArray jsonObject) throws JSONException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txt = new TextView(getApplicationContext());
                        txt.setText("当前评论"+jsonObject.length()+"条评论");
                        pinglun.addView(txt);

                        for (int i = 0; i < jsonObject.length(); i++) {
                            try {
                                TextView   textView = new TextView(getApplicationContext());
                                textView.setText(jsonObject.getJSONObject(i).getString("userName")+":"+jsonObject.getJSONObject(i).getString("content"));
                                pinglun.addView(textView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                });
            }
        });

    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        con = (TextView) findViewById(R.id.con);
        newslist = (Newslist) findViewById(R.id.newslist);
        textView5 = (TextView) findViewById(R.id.textView5);
        editTextTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        button = (Button) findViewById(R.id.button);
        pinglun = (LinearLayout) findViewById(R.id.pinglun);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)finish();
        return super.onOptionsItemSelected(item);
    }
}