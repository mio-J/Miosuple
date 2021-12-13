package com.example.miomode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//v16.14
public class TOOL {
    static public String mainurl="http://124.93.196.45:10001";
    static public Object[] usermsg=new String[]{"","","","","",""};
    static public String Tokin="";
    static public ArrayList<HashMap<String,Object>> Alldata;

    static public void GETS(String url, Callback callback)
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(mainurl+url)
                .method("GET", null)
                .addHeader("Authorization", Tokin)
                .build();
        Call call=client.newCall(request);
        call.enqueue(callback);
    }
    interface chuli
    {
        void chuli(JSONArray jsonObject) throws JSONException;
    }


    static public void GET(String url, chuli chuli)
    {
        GETS(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONArray jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string()).getJSONArray("rows");
                    chuli.chuli(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    interface post
    {
        void post(JSONObject jsonObject) throws JSONException;
    }

    static public void POST(String url,Context context,String canshu,post post)
    {
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,canshu);
        Request request = new Request.Builder()
                .url(mainurl+url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Tokin)
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zjw", "onFailure: "+e);
                if ((Activity)context!=null)
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "账户或密码错误", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if ((Activity)context!=null)
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    post.post(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("zjw", "onFailursssse: "+e);
                    if ((Activity)context!=null)
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "账户或密码错误", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                }

            }
        });
    }

    static public void PUT(String url,Context context,String canshu,post post)
    {
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,canshu);
        Request request = new Request.Builder()
                .url(mainurl+url)
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Tokin)
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if ((Activity)context!=null)
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "账户或密码错误", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if ((Activity)context!=null)
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    post.post(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    if ((Activity)context!=null)
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "账户或密码错误", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                }

            }
        });
    }



}
