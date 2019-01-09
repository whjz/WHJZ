package com.example.jh.buildings.Activity;

import android.content.Intent;
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
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jh.buildings.R;

public class Style extends AppCompatActivity {
    private  View imgBack;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();

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

                String representativebuildintroduction = bundle.getString("representativebuildintroduction");
                TextView tx_representativebuildintroduction = (TextView)findViewById(R.id.style_representativebuildintroduction);
                LinearLayout intro0 = (LinearLayout)findViewById(R.id.intro0);
                LinearLayout intro1 = (LinearLayout)findViewById(R.id.intro1);
                LinearLayout intro2 = (LinearLayout)findViewById(R.id.intro2);
                if (representativebuildintroduction.equals("null")){
                    intro0.setVisibility(View.GONE);
                    intro1.setVisibility(View.GONE);
                    intro2.setVisibility(View.GONE);
                }
                else {
                    tx_representativebuildintroduction.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_representativebuildintroduction.setText(Html.fromHtml(representativebuildintroduction,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_representativebuildintroduction.setText(Html.fromHtml(representativebuildintroduction));
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

        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

        imgBack = (LinearLayout)findViewById(R.id.style_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });
    }
}
