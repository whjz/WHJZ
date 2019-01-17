package com.example.jh.buildings.Activity;

import android.app.Activity;
import android.content.Context;
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
import com.example.jh.buildings.Activity.MainActivity;
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

public class Designer extends Activity{
    private View imgBack;
    private LinearLayout ll_DesignerRepresentBuilding1;
    private LinearLayout ll_DesignerRepresentBuilding2;
    private LinearLayout ll_DesignerRepresentBuilding;

    private String DesignerID="";
    // <BuildID,BuildName>
    private Map<String,String> RepresentBuildings=new LinkedHashMap<>();

    private String TAG="TAG-Designer";
    private int MESSAGE_GET_DESIGNER_REPRESENT_BUILDINGS_DATA=2;
    private int MESSAGE_GOTO_ACTIVITY_SHOW_REPRESENT_BUILDINGS=3;

    //为设计师界面中的组件绑定数据
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();

                String name = bundle.getString("name");
                DesignerID=bundle.getString("DesignerID");
                Log.i(TAG,"DesignerID: "+DesignerID);
                TextView tx_name = (TextView)findViewById(R.id.designer_name);
                if (name.equals("null")) {tx_name.setText("");}
                else {tx_name.setText(name);}
                //绑定文字
                String birthday = bundle.getString("birthday");
                TextView tx_birthday = (TextView)findViewById(R.id.designer_birthday);
                if (birthday.equals("null")){ tx_birthday.setText("");}
                else {tx_birthday.setText(birthday);}

                //其他代表作品简介
//                String otherworksintroduction = bundle.getString("otherworksintroduction");
//                TextView tx_otherworksintroduction = (TextView )findViewById(R.id.designer_other);
//                LinearLayout works_intro0 = (LinearLayout)findViewById(R.id.works_intro0);
//                LinearLayout works_intro1 = (LinearLayout)findViewById(R.id.works_intro1);
//                LinearLayout works_intro2 = (LinearLayout)findViewById(R.id.works_intro2);
//                if (otherworksintroduction.equals("null")){
//                    works_intro0.setVisibility(View.GONE);
//                    works_intro1.setVisibility(View.GONE);
//                    works_intro2.setVisibility(View.GONE);
//                }
//                else {
//                    tx_otherworksintroduction.setMovementMethod(LinkMovementMethod.getInstance());
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                        tx_otherworksintroduction.setText(Html.fromHtml(otherworksintroduction,Html.FROM_HTML_MODE_COMPACT));
//                    }
//                    else{
//                        tx_otherworksintroduction.setText(Html.fromHtml(otherworksintroduction));
//                    }}

                String sex = bundle.getString("sex");
                TextView tx_sex = (TextView)findViewById(R.id.designer_sex);
                if (sex.equals("null")){tx_sex.setText("");}
                else {tx_sex.setText(sex);}

                String school = bundle.getString("school");
                TextView tx_school = (TextView)findViewById(R.id.designer_school);
                if (school.equals("null")){tx_school.setText("");}
                else {tx_school.setText(school);}

                String nativeplace = bundle.getString("nativeplace");
                TextView tx_nativeplace = (TextView)findViewById(R.id.designer_nativeplace);
                if (nativeplace.equals("null")){tx_nativeplace.setText("");}
                else {tx_nativeplace.setText(nativeplace);}

                //设置个人简介
                String introduce = bundle.getString("introduce");
                TextView tx_introduce = (TextView)findViewById(R.id.designer_intro);
                LinearLayout personal_intro_linearLayout0 = (LinearLayout)findViewById(R.id.personal_intro0);
                LinearLayout personal_intro_linearLayout1 = (LinearLayout)findViewById(R.id.personal_intro1);
                LinearLayout personal_intro_linearLayout2 = (LinearLayout)findViewById(R.id.personal_intro2);
                if (introduce.equals("null")){
                    personal_intro_linearLayout0.setVisibility(View.GONE);
                    personal_intro_linearLayout1.setVisibility(View.GONE);
                    personal_intro_linearLayout2.setVisibility(View.GONE);}
                else {
                    tx_introduce.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_introduce.setText(Html.fromHtml(introduce,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_introduce.setText(Html.fromHtml(introduce));
                    }
                }

                //个人成就
                String achievement = bundle.getString("achievement");
                TextView tx_achievement = (TextView)findViewById(R.id.designer_effort);
                LinearLayout Personal_achievements0 = (LinearLayout)findViewById(R.id.Personal_achievements0);
                LinearLayout Personal_achievements1 = (LinearLayout)findViewById(R.id.Personal_achievements1);
                LinearLayout Personal_achievements2 = (LinearLayout)findViewById(R.id.Personal_achievements2);
                if (achievement.equals("null")){
                    Personal_achievements0.setVisibility(View.GONE);
                    Personal_achievements1.setVisibility(View.GONE);
                    Personal_achievements2.setVisibility(View.GONE);
                }
                else {
                    tx_achievement.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_achievement.setText(Html.fromHtml(achievement,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_achievement.setText(Html.fromHtml(achievement));
                    }
                }

                //绑定图片
                String picture = bundle.getString("picture");
                ImageView pic = (ImageView)findViewById(R.id.img_designer);
                //Log.i("ccccc",picture);
                if (picture.equals("http://202.114.41.165:8080null")){pic.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nopicture));}
                else {
                    Glide.with(pic.getContext()).load(picture).into(pic);
                    //Log.i("eeeee","sss");
                }

                initDesignerRepresentBuildings();
            }
            else if (msg.what==MESSAGE_GET_DESIGNER_REPRESENT_BUILDINGS_DATA){
                if (RepresentBuildings.size()>0){
                    for (final String getKey:RepresentBuildings.keySet()){
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText(RepresentBuildings.get(getKey));
                        tv.setTextColor(Color.parseColor("#3399FF"));
                        tv.setTextSize(13);
                        TextPaint tp = tv.getPaint();
                        tp.setFakeBoldText(true);
                        layoutParams.setMargins(5,20,15,0);
                        ll_DesignerRepresentBuilding.addView(tv,layoutParams);

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SearchBuildingByID(getKey);
                            }
                        });
                    }
                }
                else {
                    ll_DesignerRepresentBuilding1.setVisibility(View.GONE);
                    ll_DesignerRepresentBuilding2.setVisibility(View.GONE);
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.designer);

        initControls();

        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

        imgBack = (LinearLayout)findViewById(R.id.designer_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(Designer.this,MainActivity.class);
                startActivity(i);*/
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

    }

    public void initControls(){
        ll_DesignerRepresentBuilding1=findViewById(R.id.ll_DesignerRepresentBuilding1);
        ll_DesignerRepresentBuilding2=findViewById(R.id.ll_DesignerRepresentBuilding2);
        ll_DesignerRepresentBuilding=findViewById(R.id.ll_DesignerRepresentBuilding);
    }

    public void initDesignerRepresentBuildings(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Todo check the url
                String url="http://202.114.41.165:8080/WHJZProject/servlet/GetDesignerRepresentsList";
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String Data=response.body().string();
                    Log.i(TAG, Data);
                    JSONObject Origin=new JSONObject(Data);

                    boolean Status=Origin.getBoolean("status");
                    Log.i(TAG,"Status: "+Status);
                    if (Status){
                        JSONArray Result=Origin.getJSONArray("result");
                        for (int i=0;i<Result.length();i++){
                            JSONObject SingleData=Result.getJSONObject(i);
                            String ID=SingleData.getString("DesignerID");
                            if (ID.equals(DesignerID)){
                                String BuildID=SingleData.getString("BuildID");
                                String BuildName=SingleData.getString("BuildName");
                                Log.i(TAG,"BuildID: "+BuildID+" BuildName: "+BuildName);
                                RepresentBuildings.put(BuildID,BuildName);
                            }
                        }
                    }

                    Message message=new Message();
                    message.what=MESSAGE_GET_DESIGNER_REPRESENT_BUILDINGS_DATA;
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

}
