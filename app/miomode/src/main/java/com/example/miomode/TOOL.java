package com.example.miomode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
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
    static public String mainurl="http://124.93.196.45:10091";
    static public Object[] usermsg=new String[]{"mio","","","","",""};
    static public String Tokin="";
    static public ArrayList<HashMap<String,Object>> Alldata=new ArrayList<>();

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
 public  interface chuli
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

    public interface post
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

    static public void PUT( Context context,String url, String canshu, post post)
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
public interface logingv
{
    void logingv();
}
public static void Loging(Context context,String username,String password,logingv logingv)
{
    POST("/prod-api/api/login", context, String.format("{\"username\":\"%s\",\"password\":\"%s\"}",username,password ), new post() {
        @Override
        public void post(JSONObject jsonObject) throws JSONException {
            Tokin=jsonObject.getString("token");
            GETS("/prod-api/api/common/user/getInfo", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject msg=new JSONObject(response.body().string()).getJSONObject("user");
                        usermsg[0]=msg.getString("userName");
                        usermsg[1]=msg.getString("nickName");
                        usermsg[2]=msg.getString("phonenumber");
                        usermsg[3]=msg.getString("sex");
                        usermsg[4]=msg.getString("idCard");
                        logingv.logingv();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if ((Activity)context!=null)
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "账户或密码错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                }
            });
        }
    });
}

public static void getnewsdata(chuli chuli)
{
    TOOL.GET("/prod-api/press/press/list", new TOOL.chuli() {
        @Override
        public void chuli(JSONArray jsonArray) throws JSONException {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("img", jsonArray.getJSONObject(i).getString("cover"));
                hashMap.put("h0", jsonArray.getJSONObject(i).getString("title"));
                hashMap.put("h1", Html.fromHtml( jsonArray.getJSONObject(i).getString("content")));
                hashMap.put("h2", jsonArray.getJSONObject(i).getString("likeNum") + "点赞");
                hashMap.put("h3", jsonArray.getJSONObject(i).getString("readNum") + "阅读");
                hashMap.put("h4","日期："+ jsonArray.getJSONObject(i).getString("publishDate"));
                hashMap.put("id", jsonArray.getJSONObject(i).getString("id"));
                hashMap.put("type", jsonArray.getJSONObject(i).getString("type"));
                Alldata.add(hashMap);
                chuli.chuli(null);
            }

        }
    });
}

public static void Updatamsg(Context context,String nickName,String phonenumber,String sex)
{
    String canshu= String.format("{\"nickName\": \"%s\",\"phonenumber\": \"%s\",\"sex\": \"%s\"}",
            nickName,phonenumber,sex);
    TOOL.PUT(context,"/prod-api/api/common/user", canshu, new TOOL.post() {
        @Override
        public void post(JSONObject jsonObject) throws JSONException {
            Log.d("zjw", "post: "+jsonObject);
          if (context!=null)
              ((Activity)context).
             runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "信息修改成功！", Toast.LENGTH_SHORT).show();
                    ((Activity)context).finish();
                }
            });
        }
    });
}


    public static void Updatapassword(Context context,String oldpassword,String newpassword)
    {
        String canshu= String.format("{\"newPassword\": \"%s\",\"oldPassword\": \"%s\"}",newpassword,oldpassword);
        TOOL.PUT(context, "/prod-api/api/common/user/resetPwd", canshu, new TOOL.post() {
            @Override
            public void post(JSONObject jsonObject) throws JSONException {
                Log.d("zjw", "post: "+jsonObject);
                if (context!=null)
                    ((Activity)context).
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "修改密码成功！", Toast.LENGTH_SHORT).show();
                        ((Activity)context).finish();
                    }
                });
            }
        });
    }


    public static void Submsg(Context context,String msg)
    {
        String cnashu = String.format("{\"content\": \"%s\",\"title\": \"反馈意见\"}",msg);
        TOOL.POST("/prod-api/api/common/feedback",context, cnashu, new TOOL.post() {
            @Override
            public void post(JSONObject jsonObject) throws JSONException {
                if (context!=null)
                    ((Activity)context).
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "反馈成功！", Toast.LENGTH_SHORT).show();
                        ((Activity)context).finish();
                    }
                });
            }
        });
    }


}
