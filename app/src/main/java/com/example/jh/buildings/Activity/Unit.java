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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jh.buildings.R;

public class Unit extends AppCompatActivity {
    private View imgBack;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();

                String name = bundle.getString("name");
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
                LinearLayout intro00 = (LinearLayout)findViewById(R.id.intro00);
                LinearLayout intro0 = (LinearLayout)findViewById(R.id.intro0);
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

                String representativeworks = bundle.getString("representativeworks");
                TextView tx_representativeworks = (TextView)findViewById(R.id.unit_representativeworks);
                LinearLayout works_intro0 = (LinearLayout)findViewById(R.id.works_intro0);
                LinearLayout works_intro1 = (LinearLayout)findViewById(R.id.works_intro1);
                LinearLayout works_intro2 = (LinearLayout)findViewById(R.id.works_intro2);
                if (representativeworks.equals("null")){
                    works_intro0.setVisibility(View.GONE);
                    works_intro1.setVisibility(View.GONE);
                    works_intro2.setVisibility(View.GONE);
                }
                else {
                    tx_representativeworks.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_representativeworks.setText(Html.fromHtml(representativeworks,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_representativeworks.setText(Html.fromHtml(representativeworks));
                    }
                }

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
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit);

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

}
