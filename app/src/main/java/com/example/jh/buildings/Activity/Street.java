package com.example.jh.buildings.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jh.buildings.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.Attributes;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Street extends Activity{
    private View imgBack;
    StringBuilder response;
    static String url = "http://202.114.41.165:8080";
    private String TAG="TAG-Unit";
    private int MESSAGE_GET_STREET_REPRESENT_BUILDINGS_DATA=2;
    private int MESSAGE_GOTO_ACTIVITY_SHOW_REPRESENT_BUILDINGS=3;

    private LinearLayout ll_StreetRepresentBuilding1;
    private LinearLayout ll_StreetRepresentBuilding2;
    private LinearLayout ll_StreetRepresentBuilding;

    private String StreetID="";

    Map<String,String> Street_ID=new HashMap<>();
    Map<String,String> Street_Name = new HashMap<>();
    Map<String,String> Street_District = new HashMap<>();
    Map<String,String> Street_Introduction = new HashMap<>();
    Map<String,String> Street_PictureUrl = new HashMap<>();

    Map<String,String> StreetRepresentBuildings=new LinkedHashMap<>();

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();
                String buildid = bundle.getString("buildid");
                Log.i("TAG-Street","buildid: "+buildid);

                String name = Street_Name.get(buildid);
                StreetID=Street_ID.get(buildid);
                Log.i(TAG,"StreetID: "+StreetID);
                TextView tx_name = (TextView)findViewById(R.id.street_name);
                if (name=="null" || name==null){
                    tx_name.setText("");
                }
                else {tx_name.setText(name);}

                ImageView img_picture = (ImageView)findViewById(R.id.street_picture);
                String picture = Street_PictureUrl.get(buildid);
                //Log.i("qwer",picture);
                if (picture=="http://202.114.41.165:8080null"){img_picture.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nopicture));}
                else {
                    Glide.with(getApplicationContext()).load(picture).into(img_picture);
                }


                String District = Street_District.get(buildid);
                TextView tx_District = (TextView)findViewById(R.id.street_district);
                LinearLayout district00 = (LinearLayout)findViewById(R.id.district00);
                LinearLayout district0 = (LinearLayout)findViewById(R.id.district0);
                LinearLayout district1 = (LinearLayout)findViewById(R.id.district1);
                if (District.equals("null")){
                    district0.setVisibility(View.GONE);
                    district1.setVisibility(View.GONE);
                    district00.setVisibility(View.GONE);
                }
                else {
                    tx_District.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_District.setText(Html.fromHtml(District,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_District.setText(Html.fromHtml(District));
                    }
                }


                String Introduction = Street_Introduction.get(buildid);
                TextView tx_Introduction = (TextView)findViewById(R.id.street_Introduction);
                LinearLayout intro0 = (LinearLayout)findViewById(R.id.intro0);
                LinearLayout intro1 = (LinearLayout)findViewById(R.id.intro1);
                LinearLayout intro2 = (LinearLayout)findViewById(R.id.intro2);
                if (Introduction.equals("null")){
                    intro0.setVisibility(View.GONE);
                    intro1.setVisibility(View.GONE);
                    intro2.setVisibility(View.GONE);
                }
                else {
                    tx_Introduction.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_Introduction.setText(Html.fromHtml(Introduction,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_Introduction.setText(Html.fromHtml(Introduction));
                    }
                }
                initStreetRepresentBuildings();
            }
            else if (msg.what==MESSAGE_GET_STREET_REPRESENT_BUILDINGS_DATA){
                if (StreetRepresentBuildings.size()>0){
                    for (final String getKey:StreetRepresentBuildings.keySet()){
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText(StreetRepresentBuildings.get(getKey));
                        tv.setTextColor(Color.parseColor("#3399FF"));
                        tv.setTextSize(13);
                        TextPaint tp = tv.getPaint();
                        tp.setFakeBoldText(true);
                        layoutParams.setMargins(5,20,15,0);
                        ll_StreetRepresentBuilding.addView(tv,layoutParams);

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SearchBuildingByID(getKey);
                            }
                        });
                    }
                }
                else {
                    ll_StreetRepresentBuilding1.setVisibility(View.GONE);
                    ll_StreetRepresentBuilding2.setVisibility(View.GONE);
                }
            }
            else if(msg.what==MESSAGE_GOTO_ACTIVITY_SHOW_REPRESENT_BUILDINGS){
                Bundle bundle=msg.getData();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.street);

        initControls();
        requestUsingHttpURLConnectionGetStreet();

        imgBack = (LinearLayout)findViewById(R.id.street_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

    }

    public void initControls(){
        ll_StreetRepresentBuilding1=findViewById(R.id.ll_StreetRepresentBuilding1);
        ll_StreetRepresentBuilding2=findViewById(R.id.ll_StreetRepresentBuilding2);
        ll_StreetRepresentBuilding=findViewById(R.id.ll_StreetRepresentBuilding);
    }

    public void initStreetRepresentBuildings(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Todo check the url
                String url="http://202.114.41.165:8080/WHJZProject/servlet/GetStreetRepresents";
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response OkhttpResponse=client.newCall(request).execute();
                    String Data=OkhttpResponse.body().string();
                    Log.i(TAG, Data);
                    JSONObject Origin=new JSONObject(Data);

                    boolean Satatus=Origin.getBoolean("status");
                    Log.i(TAG,"Status: "+Satatus);
                    if (Satatus){
                        JSONArray Result=Origin.getJSONArray("result");
                        for (int i=0;i<Result.length();i++){
                            JSONObject SingleData=Result.getJSONObject(i);
                            String ID=SingleData.getString("StreetID");
                            if (ID.equals(StreetID)){
                                String BuildID=SingleData.getString("BuildID");
                                String BuildName=SingleData.getString("BuildName");
                                Log.i(TAG,"BuildID: "+BuildID+" BuildName: "+BuildName);
                                StreetRepresentBuildings.put(BuildID,BuildName);
                            }
                        }
                    }
                    Message message=new Message();
                    message.what=MESSAGE_GET_STREET_REPRESENT_BUILDINGS_DATA;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void requestUsingHttpURLConnectionGetStreet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetStreet"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray a = new JSONArray(response.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        Street_ID.put(b.getString("BuildID"),b.getString("StreetID"));
                        Street_Name.put(b.getString("BuildID"),b.getString("Name"));
                        Street_District.put(b.getString("BuildID"),b.getString("District"));
                        Street_Introduction.put(b.getString("BuildID"),b.getString("Introduction"));
                        Street_PictureUrl.put(b.getString("BuildID"),url + b.getString("PictureUrl"));
                    }
                    Log.i("TAG-Street","a: "+a);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void SearchBuildingByID(final String BuildID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://202.114.41.165:8080/WHJZProject/servlet/GetBuildingsByBuildID/"+BuildID;
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String Data=response.body().string();

                    JSONObject Origin=new JSONObject(Data);
                    JSONArray Result=Origin.getJSONArray("result");
                    JSONObject Content=Result.getJSONObject(0);

                    Bundle bundle=new Bundle();
                    bundle.putString("buildid",Content.getString("BuildID"));
                    bundle.putString("name",Content.getString("Name"));
                    bundle.putString("year",Content.getString("Year"));
                    bundle.putString("district",Content.getString("District"));
                    bundle.putString("introduction",Content.getString("BuildIntroduction"));
                    bundle.putString("street",Content.getString("Street"));
                    bundle.putString("styleid",Content.getString("BuildStyleID"));
                    bundle.putString("use",Content.getString("CurrentUse"));
                    bundle.putString("value",Content.getString("HistoricalValue"));
                    bundle.putString("picture","http://202.114.41.165:8080/"+Content.getString("Picture"));

                    Message msg = new Message();
                    msg.what = MESSAGE_GOTO_ACTIVITY_SHOW_REPRESENT_BUILDINGS;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
