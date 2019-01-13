package com.example.jh.buildings.Activity;

import android.app.Activity;

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

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * @project_name Buildings
 * @author:Jh
 * @time: 2019/1/11 11:58
 * @version:V1.0
 */
public class Vedio extends Activity {
    private View imgBack;

    //获取从MainActivity传入的数据，并为此界面的组件绑定数据
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();

//                String name = bundle.getString("name");
//                TextView tx_name = (TextView)findViewById(R.id.vedio_name);
//                tx_name.setText(name);

                String introduce = bundle.getString("introduce");
                TextView tx_introduce = (TextView)findViewById(R.id.vedio_intro);
                if (introduce.equals("null")){
                    tx_introduce.setText("");
                }
                else {
                    tx_introduce.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_introduce.setText(Html.fromHtml(introduce,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_introduce.setText(Html.fromHtml(introduce));
                    }
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vedio);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("resourceurl");
        String name = bundle.getString("name");
        JzvdStd jzvdStd = (JzvdStd) findViewById(R.id.videoplayer);
        //jzvdStd.setUp("http://202.114.41.165:8080/HistoryBuildings/Video/电视连续剧《陶铸》.mp4","吃饺子",Jzvd.SCREEN_WINDOW_NORMAL);
        jzvdStd.setUp(url,name,Jzvd.SCREEN_WINDOW_NORMAL);

        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

        imgBack = (LinearLayout)findViewById(R.id.vedio_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(Associated_people.this,MainActivity.class);
                startActivity(i);*/
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

}
