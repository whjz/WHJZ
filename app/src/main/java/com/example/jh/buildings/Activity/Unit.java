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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jh.buildings.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Unit extends AppCompatActivity {
    private View imgBack;
    private LinearLayout ll_UnitRepresentBuilding1;
    private LinearLayout ll_UnitRepresentBuilding2;
    private LinearLayout ll_UnitRepresentBuilding;

    private String UnitID="";
    private Map<String,String> UnitRepresentBuildings=new LinkedHashMap<>();

    private String TAG="TAG-Unit";
    private int MESSAGE_GET_UNIT_REPRESENT_BUILDINGS_DATA=2;
    private int MESSAGE_GOTO_ACTIVITY_SHOW_REPRESENT_BUILDINGS=3;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();

                String name = bundle.getString("name");
                UnitID=bundle.getString("BuildUnitID");
                Log.i(TAG,"UnitID: "+UnitID);
                TextView tx_name = (TextView)findViewById(R.id.unit_name);
                if (name.equals("null")){tx_name.setText("");}
                else {tx_name.setText(name);}

                ImageView img_picture = (ImageView)findViewById(R.id.unit_picture);
                String picture = bundle.getString("pictureurl");
                //Log.i("qwer",picture);
                if (picture.equals("http://202.114.41.165:8080null")){img_picture.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nopicture));}
                else {
                    Glide.with(getApplicationContext()).load(picture).into(img_picture);
                }


                String introduction = bundle.getString("introduction");
                TextView tx_introduction = (TextView)findViewById(R.id.unit_introduction);
                LinearLayout intro00 = (LinearLayout)findViewById(R.id.intro0);
                LinearLayout intro0 = (LinearLayout)findViewById(R.id.intro00);
                LinearLayout intro1 = (LinearLayout)findViewById(R.id.intro1);
                if (introduction.equals("null")){
                    intro0.setVisibility(View.GONE);
                    intro1.setVisibility(View.GONE);
                    intro00.setVisibility(View.GONE);
                }
                else {
                    tx_introduction.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_introduction.setText(Html.fromHtml(introduction,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_introduction.setText(Html.fromHtml(introduction));
                    }
                }

//                String representativeworks = bundle.getString("representativeworks");
//                TextView tx_representativeworks = (TextView)findViewById(R.id.unit_representativeworks);
//                LinearLayout works_intro0 = (LinearLayout)findViewById(R.id.works_intro0);
//                LinearLayout works_intro1 = (LinearLayout)findViewById(R.id.works_intro1);
//                LinearLayout works_intro2 = (LinearLayout)findViewById(R.id.works_intro2);
//                //Log.i("QWSA",introduction);
//                if (representativeworks.equals("null")){
//                    works_intro0.setVisibility(View.GONE);
//                    works_intro1.setVisibility(View.GONE);
//                    works_intro2.setVisibility(View.GONE);
//                }
//                else {
//                    tx_representativeworks.setMovementMethod(LinkMovementMethod.getInstance());
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                        tx_representativeworks.setText(Html.fromHtml(representativeworks,Html.FROM_HTML_MODE_COMPACT));
//                    }
//                    else{
//                        tx_representativeworks.setText(Html.fromHtml(representativeworks));
//                    }
//                }

                String founderintroduction = bundle.getString("founderintroduction");
                TextView tx_founderintroduction = (TextView)findViewById(R.id.unit_founderintroduction);
                LinearLayout Founder0 = (LinearLayout)findViewById(R.id.Founder0);
                LinearLayout Founder1 = (LinearLayout)findViewById(R.id.Founder1);
                LinearLayout Founder2 = (LinearLayout)findViewById(R.id.Founder2);
                if (founderintroduction.equals("null")){
                    Founder0.setVisibility(View.GONE);
                    Founder1.setVisibility(View.GONE);
                    Founder2.setVisibility(View.GONE);
                }
                else {
                    tx_founderintroduction.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_founderintroduction.setText(Html.fromHtml(founderintroduction,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_founderintroduction.setText(Html.fromHtml(founderintroduction));
                    }
                }
                initUnitRepresentBuildings();
            }
            else if(msg.what==MESSAGE_GET_UNIT_REPRESENT_BUILDINGS_DATA){
                if (UnitRepresentBuildings.size()>0) {
                    for (final String getKey : UnitRepresentBuildings.keySet()) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText(UnitRepresentBuildings.get(getKey));
                        tv.setTextColor(Color.parseColor("#3399FF"));
                        tv.setTextSize(13);
                        TextPaint tp = tv.getPaint();
                        tp.setFakeBoldText(true);
                        layoutParams.setMargins(5, 20, 15, 0);
                        ll_UnitRepresentBuilding.addView(tv, layoutParams);

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SearchBuildingByID(getKey);
                            }
                        });
                    }
                }
                else {
                    ll_UnitRepresentBuilding1.setVisibility(View.GONE);
                    ll_UnitRepresentBuilding2.setVisibility(View.GONE);
                }
            }
            else if (msg.what==MESSAGE_GOTO_ACTIVITY_SHOW_REPRESENT_BUILDINGS){
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
        setContentView(R.layout.unit);

        initControls();

        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

        imgBack = (LinearLayout)findViewById(R.id.unit_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });
    }

    public void initControls(){
        ll_UnitRepresentBuilding1=findViewById(R.id.ll_UnitRepresentBuilding1);
        ll_UnitRepresentBuilding2=findViewById(R.id.ll_UnitRepresentBuilding2);
        ll_UnitRepresentBuilding=findViewById(R.id.ll_UnitRepresentBuilding);
    }

    public void initUnitRepresentBuildings(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Todo check the url
                String url="http://202.114.41.165:8080/WHJZProject/servlet/GetBuildingUnitRepresents";
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String Data=response.body().string();
                    Log.i(TAG, Data);
                    JSONObject Origin=new JSONObject(Data);
                    boolean Satatus=Origin.getBoolean("status");
                    Log.i(TAG,"Status: "+Satatus);
                    if (Satatus){
                        JSONArray Result=Origin.getJSONArray("result");
                        for (int i=0;i<Result.length();i++){
                            JSONObject SingleData=Result.getJSONObject(i);
                            String ID=SingleData.getString("BuildUnitID");
                            Log.i(TAG,"BuildingUnitID: "+ID);
                            if (ID.equals(UnitID)){
                                String BuildID=SingleData.getString("BuildID");
                                String BuildName=SingleData.getString("BuildName");
                                Log.i(TAG,"BuildID: "+BuildID+" BuildName: "+BuildName);
                                UnitRepresentBuildings.put(BuildID,BuildName);
                            }
                        }
                    }
                    Message message=new Message();
                    message.what=MESSAGE_GET_UNIT_REPRESENT_BUILDINGS_DATA;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
