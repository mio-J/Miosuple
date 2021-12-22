package com.example.miosuple;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miomode.Newslist;
import com.example.miomode.TOOL;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Newslist newslist2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}