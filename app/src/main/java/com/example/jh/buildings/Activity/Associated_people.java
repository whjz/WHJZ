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
import com.example.jh.buildings.Activity.MainActivity;
import com.example.jh.buildings.R;

public class Associated_people extends Activity{
    private View imgBack;

    //获取从MainActivity传入的数据，并为此界面的组件绑定数据
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();

                String name = bundle.getString("name");
                TextView tx_name = (TextView)findViewById(R.id.associated_name);
                if (name.equals("null")){name = "";}
                else {tx_name.setText(name);}

                ImageView img_picture = (ImageView)findViewById(R.id.associatedperson_picture);
                String picture = bundle.getString("picture");
                if (picture.equals("http://202.114.41.165:8080null")){img_picture.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nopicture));}
                else {
                    Glide.with(getApplicationContext()).load(picture).into(img_picture);
                }
                //引入Glide来实现网络图片的加载

                String birthday = bundle.getString("birthday");
                TextView tx_birthday = (TextView)findViewById(R.id.associatedperson_birthday);
                if (birthday.equals("null")){tx_birthday.setText("");}
                else {tx_birthday.setText(birthday);}

                String sex = bundle.getString("sex");
                TextView tx_sex = (TextView)findViewById(R.id.associatedperson_sex);
                if (sex.equals("null")){tx_sex.setText("");}
                else {tx_sex.setText(sex);}

                String nativeplace = bundle.getString("nativeplace");
                TextView tx_nativeplace = (TextView)findViewById(R.id.associatedperson_nativeplace);
                if (nativeplace.equals("null")){tx_nativeplace.setText("");}
                else {tx_nativeplace.setText(nativeplace);}

                String personalprofile = bundle.getString("personalprofile");
                TextView tx_personalprofile = (TextView)findViewById(R.id.associatedperson_personalprofile);
                LinearLayout intro0 = (LinearLayout)findViewById(R.id.intro0);
                LinearLayout intro1 = (LinearLayout)findViewById(R.id.intro1);
                LinearLayout intro2 = (LinearLayout)findViewById(R.id.intro2);
                if (personalprofile.equals("null")){
                    intro0.setVisibility(View.GONE);
                    intro1.setVisibility(View.GONE);
                    intro2.setVisibility(View.GONE);
                }
                else {
                    tx_personalprofile.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_personalprofile.setText(Html.fromHtml(personalprofile,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_personalprofile.setText(Html.fromHtml(personalprofile));
                    }
                }

                String relatedevents = bundle.getString("relatedevents");
                TextView tx_relatedevents = (TextView)findViewById(R.id.associatedperson_relatedevents);
                LinearLayout Associated_event0 = (LinearLayout)findViewById(R.id.Associated_event0);
                LinearLayout Associated_event1 = (LinearLayout)findViewById(R.id.Associated_event1);
                LinearLayout Associated_event2 = (LinearLayout)findViewById(R.id.Associated_event2);
                if (relatedevents.equals("null")){
                    Associated_event0.setVisibility(View.GONE);
                    Associated_event1.setVisibility(View.GONE);
                    Associated_event2.setVisibility(View.GONE);
                }
                else {
                    tx_relatedevents.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_relatedevents.setText(Html.fromHtml(relatedevents,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_relatedevents.setText(Html.fromHtml(relatedevents));
                    }
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.associated_people);

        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

        imgBack = (LinearLayout)findViewById(R.id.people_back);
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

}
