package com.example.jh.buildings.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
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
import java.util.Map;
import java.util.jar.Attributes;

public class Street extends Activity{
    private View imgBack;
    StringBuilder response;
    static String url = "http://202.114.41.165:8080";

    Map<String,String> Street_Name = new HashMap<>();
    Map<String,String> Street_District = new HashMap<>();
    Map<String,String> Street_Introduction = new HashMap<>();
    Map<String,String> Street_PictureUrl = new HashMap<>();

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();
                String buildid = bundle.getString("buildid");

                String name = Street_Name.get(buildid);
                TextView tx_name = (TextView)findViewById(R.id.street_name);
                //todo something wrong
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
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.street);

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
                        Street_Name.put(b.getString("BuildID"),b.getString("Name"));
                        Street_District.put(b.getString("BuildID"),b.getString("District"));
                        Street_Introduction.put(b.getString("BuildID"),b.getString("Introduction"));
                        Street_PictureUrl.put(b.getString("BuildID"),url + b.getString("PictureUrl"));
                    }
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

}
