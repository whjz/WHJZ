package com.example.jh.buildings.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Style extends AppCompatActivity {
    private String TAG="TAG-Style";

    private  View imgBack;
    private LinearLayout ll_RepresentBuilding;
    private LinearLayout ll_RepresentBuilding1;
    private LinearLayout ll_RepresentBuilding2;

    private String BuildStyleID="";
    // <BuildID,BuildName>
    private Map<String,String> Buildings=new LinkedHashMap<>();

    private int MESSAGE_INIT_CONTROLS=1;
    private int MESSAGE_SHOW_REPRESENT_BUILDINGS=2;
    private int MESSAGE_GOTO_ACTIVITY_SHOW_REPRESENT_BUILDINGS=3;

    //从buildingstyle中读取出的数据
    Map<String,String> Style_Name = new HashMap<>();
    Map<String,String> Style_Style = new HashMap<>();
    Map<String,String> Style_Picture = new HashMap<>();
    Map<String,String> Style_CurrentUse = new HashMap<>();
    Map<String,String> Style_HistoricalValue = new HashMap<>();
    Map<String,String> Style_BuildStyleID = new HashMap<>();

    StringBuilder response_style;
    static String url = "http://202.114.41.165:8080";

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == MESSAGE_INIT_CONTROLS){
                Bundle bundle = getIntent().getExtras();
                BuildStyleID=bundle.getString("buildstyleid");
                Log.i("TAG-Style","BuildStyleID: "+BuildStyleID);

                String name = bundle.getString("name");
                TextView tx_name = (TextView)findViewById(R.id.style_name);
                if (name.equals("null")){tx_name.setText("");}
                else {tx_name.setText(name);}


                ImageView img_picture = (ImageView)findViewById(R.id.style_picture);
                String picture = bundle.getString("picture");
                //Log.i("qwer",picture);
                if (picture.equals("http://202.114.41.165:8080null")){img_picture.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nopicture));}
                else {
                    Glide.with(getApplicationContext()).load(picture).into(img_picture);
                }

                String style = bundle.getString("style");
                TextView tx_style = (TextView)findViewById(R.id.style_style);
                LinearLayout Construction_style0 = (LinearLayout)findViewById(R.id.Construction_style0);
                LinearLayout Construction_style1 = (LinearLayout)findViewById(R.id.Construction_style1);
                LinearLayout Construction_style2 = (LinearLayout)findViewById(R.id.Construction_style2);
                if (style.equals("null")){
                    Construction_style0.setVisibility(View.GONE);
                    Construction_style1.setVisibility(View.GONE);
                    Construction_style2.setVisibility(View.GONE);
                }
                else {
                    tx_style.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_style.setText(Html.fromHtml(style,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_style.setText(Html.fromHtml(style));
                    }
                }


                String currentuse = bundle.getString("currentuse");
                TextView tx_currentuse = (TextView)findViewById(R.id.style_currentuse);
                LinearLayout use0 = (LinearLayout)findViewById(R.id.use0);
                LinearLayout use1 = (LinearLayout)findViewById(R.id.use1);
                LinearLayout use2 = (LinearLayout)findViewById(R.id.use2);
                if (currentuse.equals("null")){
                    use0.setVisibility(View.GONE);
                    use1.setVisibility(View.GONE);
                    use2.setVisibility(View.GONE);
                }
                else {
                    tx_currentuse.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_currentuse.setText(Html.fromHtml(currentuse,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_currentuse.setText(Html.fromHtml(currentuse));
                    }
                }

                String historicalvalue = bundle.getString("historicalvalue");
                TextView tx_historicalvalue = (TextView)findViewById(R.id.style_historicalvalue);
                LinearLayout value0 = (LinearLayout)findViewById(R.id.value0);
                LinearLayout value1 = (LinearLayout)findViewById(R.id.value1);
                LinearLayout value2 = (LinearLayout)findViewById(R.id.value2);
                if (historicalvalue.equals("null")){
                    value0.setVisibility(View.GONE);
                    value1.setVisibility(View.GONE);
                    value2.setVisibility(View.GONE);
                }
                else {
                    tx_historicalvalue.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_historicalvalue.setText(Html.fromHtml(historicalvalue,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_historicalvalue.setText(Html.fromHtml(historicalvalue));
                    }
                }

                String BuildStyleIntroduction = bundle.getString("BuildStyleIntroduction");
                TextView tx_BuildStyleIntroduction = (TextView)findViewById(R.id.BuildStyleIntroduction);
                LinearLayout BuildStyleIntroduction0 = (LinearLayout)findViewById(R.id.BuildStyleIntroduction0);
                LinearLayout BuildStyleIntroduction1 = (LinearLayout)findViewById(R.id.BuildStyleIntroduction1);
                LinearLayout BuildStyleIntroduction2 = (LinearLayout)findViewById(R.id.BuildStyleIntroduction2);
                if (BuildStyleIntroduction.equals("null")){
                    BuildStyleIntroduction0.setVisibility(View.GONE);
                    BuildStyleIntroduction1.setVisibility(View.GONE);
                    BuildStyleIntroduction2.setVisibility(View.GONE);
                }
                else {
                    tx_BuildStyleIntroduction.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_BuildStyleIntroduction.setText(Html.fromHtml(BuildStyleIntroduction,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_BuildStyleIntroduction.setText(Html.fromHtml(BuildStyleIntroduction));
                    }
                }

                initRepresentBuilding();
            }
            else if(msg.what==MESSAGE_SHOW_REPRESENT_BUILDINGS){
                if (Buildings.size()>0){
                    for (final String getKey:Buildings.keySet()){
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText(Buildings.get(getKey));
                        tv.setTextColor(Color.parseColor("#3399FF"));
                        tv.setTextSize(13);
                        TextPaint tp = tv.getPaint();
                        tp.setFakeBoldText(true);
                        layoutParams.setMargins(5,20,15,0);
                        ll_RepresentBuilding.addView(tv,layoutParams);

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SearchBuildingByID(getKey);
                            }
                        });
                    }
                }
                else {
                    ll_RepresentBuilding1.setVisibility(View.GONE);
                    ll_RepresentBuilding2.setVisibility(View.GONE);
                }
            }
            else if (msg.what==MESSAGE_GOTO_ACTIVITY_SHOW_REPRESENT_BUILDINGS){
                Bundle bundle=msg.getData();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }

            else if(msg.what == 4){
                Log.i("LLL", "wwww");
                Bundle bundle = getIntent().getExtras();
                String buildstyle = bundle.getString("buildstyleid");
                for (String getkey:Style_Name.keySet()){
                    if (getkey.equals(buildstyle)||getkey.equals("null")){
                        continue;
                    }
                    else {
                        LinearLayout layout = (LinearLayout)findViewById(R.id.other_style);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText(Style_Name.get(getkey));
                        tv.setTextColor(Color.parseColor("#3399FF"));
                        tv.setTextSize(13);
                        TextPaint tp = tv.getPaint();
                        tp.setFakeBoldText(true);
                        layoutParams.setMargins(5,20,15,0);
                        layout.addView(tv,layoutParams);


                        bundle.putString("name",Style_Name.get(getkey));
                        bundle.putString("buildstyleid",getkey);
                        bundle.putString("picture",Style_Picture.get(getkey));
                        bundle.putString("style",Style_Style.get(getkey));
                        bundle.putString("currentuse",Style_CurrentUse.get(getkey));
                        bundle.putString("historicalvalue",Style_HistoricalValue.get(getkey));
                        final Intent i = new Intent(Style.this,Style.class);
                        i.putExtras(bundle);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(i);
                            }
                        });
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.style);

        initControls();

        Message msg = new Message();
        msg.what = MESSAGE_INIT_CONTROLS;
        handler.sendMessage(msg);



        imgBack = (LinearLayout)findViewById(R.id.style_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

        TextView more_style_pic = (TextView)findViewById(R.id.more_style_btn);
        Bundle bundle = getIntent().getExtras();
        String buildstyleid = bundle.getString("buildstyleid");
        String name = bundle.getString("name");
        Bundle bundle1 = new Bundle();
        bundle1.putString("buildstyleid",buildstyleid);
        bundle1.putString("name",name);
        Intent i = new Intent(Style.this,BuildingStyleImageDisplay.class);
        i.putExtras(bundle1);
        more_style_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        //Log.i("LLL", "eee");
        requestUsingHttpURLConnectionGetBuildingStyle();
    }



    private void initControls(){
        ll_RepresentBuilding=findViewById(R.id.ll_RepresentBuilding);
        ll_RepresentBuilding1=findViewById(R.id.ll_RepresentBuilding1);
        ll_RepresentBuilding2=findViewById(R.id.ll_RepresentBuilding2);
    }

    private void initRepresentBuilding(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://202.114.41.165:8080/WHJZProject/servlet/GetBuildingStyleRepresents";
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String Data=response.body().string();
                    Log.i(TAG, Data);
                    try {
                        JSONArray Origin=new JSONArray(Data);
                        for (int i=0;i<Origin.length();i++){
                            JSONObject Single=Origin.getJSONObject(i);
                            String StyleID=Single.getString("BuildStyleID");
                            if (StyleID.equals(BuildStyleID)){
                                String BuildID=Single.getString("BuildID");
                                String BuildName=Single.getString("BuildName");
                                Buildings.put(BuildID,BuildName);
                            }
                        }

                        Message msg = new Message();
                        msg.what = MESSAGE_SHOW_REPRESENT_BUILDINGS;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
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
                    Log.i(TAG, String.valueOf(response));

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

    public void requestUsingHttpURLConnectionGetBuildingStyle(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetBuildingStyle"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response_style = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response_style.append(line);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray a = new JSONArray(response_style.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        Style_BuildStyleID.put(b.getString("id"),b.getString("BuildStyleID"));
                        Style_Name.put(b.getString("BuildStyleID"),b.getString("Name"));
                        Style_Style.put(b.getString("BuildStyleID"),b.getString("Style"));
                        Style_Picture.put(b.getString("BuildStyleID"),url + b.getString("Picture"));
                        Style_CurrentUse.put(b.getString("BuildStyleID"),b.getString("CurrentUse"));
                        Style_HistoricalValue.put(b.getString("BuildStyleID"),b.getString("HistoricalValue"));
                    }
                    Message msg = new Message();
                    msg.what = 4;
                    handler.sendMessage(msg);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
