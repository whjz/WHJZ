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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.jh.buildings.Activity.MainActivity;
import com.example.jh.buildings.R;
/**
 * @project_name Buildings
 * @author:Jh
 * @time: 2019/1/5 16:01
 * @version:V1.0
 */
public class UseChange extends Activity {

    private View imgBack;

    //获取从MainActivity传入的数据，并为此界面的组件绑定数据
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();

                String changetime = bundle.getString("changetime");
                TextView tx_changename = (TextView)findViewById(R.id.changetime);
                tx_changename.setText(changetime);

                String useunit = bundle.getString("useunit");
                TextView tx_useunit = (TextView)findViewById(R.id.useunit);
                LinearLayout useunit0 = (LinearLayout)findViewById(R.id.useunit0);
                LinearLayout useunit1 = (LinearLayout)findViewById(R.id.useunit1);
                if (useunit.equals("null")){
                    useunit0.setVisibility(View.GONE);
                    useunit1.setVisibility(View.GONE);
                }
                else {
                    tx_useunit.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_useunit.setText(Html.fromHtml(useunit,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_useunit.setText(Html.fromHtml(useunit));
                    }
                }

                String changeoverview = bundle.getString("changeoverview");
                TextView tx_changeoverview = (TextView)findViewById(R.id.changeoverview);
                LinearLayout changeoverview0 = (LinearLayout)findViewById(R.id.changeoverview0);
                LinearLayout changeoverview1 = (LinearLayout)findViewById(R.id.changeoverview1);
                LinearLayout changeoverview2 = (LinearLayout)findViewById(R.id.changeoverview2);
                if (changeoverview.equals("null")){
                    changeoverview0.setVisibility(View.GONE);
                    changeoverview1.setVisibility(View.GONE);
                    changeoverview2.setVisibility(View.GONE);
                }
                else {
                    tx_changeoverview.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_changeoverview.setText(Html.fromHtml(changeoverview,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_changeoverview.setText(Html.fromHtml(changeoverview));
                    }
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.usechange);

        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

        imgBack = (LinearLayout)findViewById(R.id.use_back);
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
