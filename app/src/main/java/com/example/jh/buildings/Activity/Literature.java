package com.example.jh.buildings.Activity;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jh.buildings.Activity.MainActivity;
import com.example.jh.buildings.R;

public class Literature extends AppCompatActivity {

    private View imgBack;
    private Button button;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle = getIntent().getExtras();

                ImageView img_picture = (ImageView)findViewById(R.id.literature_picture);
                String picture = bundle.getString("pictureurl");
                //Log.i("qwer",picture);
                if (picture.equals("http://202.114.41.165:8080null")){img_picture.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nopicture));}
                else {
                    Glide.with(getApplicationContext()).load(picture).into(img_picture);
                }

                String name = bundle.getString("name");
                TextView tx_name = (TextView)findViewById(R.id.researchliterature_name);
                if (name.equals("null")){tx_name.setText("");}
                else {tx_name.setText(name);}

                String writer = bundle.getString("writer");
                TextView tx_writer = (TextView)findViewById(R.id.researchliterature_writer);
                if (writer.equals("null")){tx_writer.setText("");}
                else {tx_writer.setText(writer);}

                String type = bundle.getString("type");
                TextView tx_type = (TextView)findViewById(R.id.researchliterature_type);
                if (type.equals("null")){tx_type.setText("");}
                else {tx_type.setText(type);}

                String unit = bundle.getString("unit");
                TextView tx_unit = (TextView)findViewById(R.id.researchliterature_unit);
                if (unit.equals("null")){tx_unit.setText("");}
                else {tx_unit.setText(unit);}

                String provenance = bundle.getString("provenance");
                TextView tx_provenance = (TextView)findViewById(R.id.researchliterature_provenance);
                if (provenance.equals("null")){tx_provenance.setText("");}
                else {tx_provenance.setText(provenance);}

                String link = bundle.getString("link");
                TextView tx_link = (TextView)findViewById(R.id.researchliterature_link);
                if (link.equals("null")){tx_link.setText("");}
                else {
                    tx_link.setMovementMethod(LinkMovementMethod.getInstance());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        tx_link.setText(Html.fromHtml(link,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        tx_link.setText(Html.fromHtml(link));
                    }
                }
                //else {tx_link.setText(link);}

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.literature);

        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

        imgBack = (LinearLayout)findViewById(R.id.lit_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(Literature.this,MainActivity.class);
                startActivity(i);*/
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

        //实现文献的下载功能
        final Bundle bundle = getIntent().getExtras();
        final String downfile = bundle.getString("downfile");
        button = (Button)findViewById(R.id.literature_download);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://202.114.41.165:8080";
                if (!downfile.equals("null")){
                    String downfileurl = url + downfile;
                    Uri uri = Uri.parse(downfileurl);
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"此文献暂无可下载资源！",Toast.LENGTH_SHORT);
                }
            }
        });
    }

}
