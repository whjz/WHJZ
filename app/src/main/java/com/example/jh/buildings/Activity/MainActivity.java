package com.example.jh.buildings.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jh.buildings.Adapter.BuildingListAdapter;
import com.example.jh.buildings.R;
import com.example.jh.buildings.Activity.Street;
import com.example.jh.buildings.Activity.Style;
import com.example.jh.buildings.Activity.Unit;
import com.example.jh.buildings.HttpGetService.GetDesigner;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.sql.ResultSet;

public class MainActivity extends Activity{

    //从designer中读取出的数据
    Map<String,String> Name = new HashMap<>();
    Map<String,String> DesignerID = new HashMap<>();
    Map<String,String> Birthday = new HashMap<>();
    Map<String,String> NativePlace = new HashMap<>();
    Map<String,String> Sex = new HashMap<>();
    Map<String,String> Introduce = new HashMap<>();
    Map<String,String> OtherWorksIntroduction = new HashMap<>();
    Map<String,String> Picture = new HashMap<>();
    Map<String,String> School = new HashMap<>();
    Map<String,String> Achievement = new HashMap<>();

    //从buildingunit中读取出的数据
    Map<String,String> Unit_BuildUnitID = new HashMap<>();
    Map<String,String> Unit_Name = new HashMap<>();
    Map<String,String> Unit_Introduction = new HashMap<>();
    Map<String,String> Unit_Representativeworks = new HashMap<>();
    Map<String,String> Unit_FounderIntroduction = new HashMap<>();
    Map<String,String> Unit_PictureUrl = new HashMap<>();

    //从buildingstyle中读取出的数据
    Map<String,String> Style_BuildID = new HashMap<>();
    Map<String,String> Style_Name = new HashMap<>();
    Map<String,String> Style_RepresentativeBuildIntroduction = new HashMap<>();
    Map<String,String> Style_Style = new HashMap<>();
    Map<String,String> Style_Picture = new HashMap<>();
    Map<String,String> Style_CurrentUse = new HashMap<>();
    Map<String,String> Style_HistoricalValue = new HashMap<>();
    Map<String,String> Style_BuildStyleID = new HashMap<>();

    //从associatedperson中读取出的数据
    Map<String,String> AssociatedPerson_BuildID = new HashMap<>();
    Map<String,String> AssociatedPerson_AssociatePersonID = new HashMap<>();
    Map<String,String> AssociatedPerson_Name = new HashMap<>();
    Map<String,String> AssociatedPerson_Birthday = new HashMap<>();
    Map<String,String> AssociatedPerson_Sex = new HashMap<>();
    Map<String,String> AssociatedPerson_NativePlace = new HashMap<>();
    Map<String,String> AssociatedPerson_PersonalProfile = new HashMap<>();
    Map<String,String> AssociatedPerson_Picture = new HashMap<>();
    Map<String,String> AssociatedPerson_RelatedEvents = new HashMap<>();
    Map<String,String> AssociatedPerson_AssocaiteIntroduce = new HashMap<>();

    //从researchliterature中读取出的数据
    Map<String,String> ResearchLiterature_BuildID = new HashMap<>();
    Map<String,String> ResearchLiterature_LiteratureID = new HashMap<>();
    Map<String,String> ResearchLiterature_Writer = new HashMap<>();
    Map<String,String> ResearchLiterature_Unit = new HashMap<>();
    Map<String,String> ResearchLiterature_Name = new HashMap<>();
    Map<String,String> ResearchLiterature_Type = new HashMap<>();
    Map<String,String> ResearchLiterature_Provenance = new HashMap<>();
    Map<String,String> ResearchLiterature_Link = new HashMap<>();
    Map<String,String> ResearchLiterature_Downfile = new HashMap<>();
    Map<String,String> ResearchLiterature_PictureUrl = new HashMap<>();

    //从usechange中读取的数据
    Map<String,String> UseChange_BuildID = new HashMap<>();
    Map<String,String> UseChange_ChangeTime = new HashMap<>();
    Map<String,String> UseChange_ChangeOverview = new HashMap<>();
    Map<String,String> UseChange_UseUnit = new HashMap<>();

    //从vedio中读取的数据
    Map<String,String> Vedio_BuildID = new HashMap<>();
    Map<String,String> Vedio_ResourceUrl = new HashMap<>();
    Map<String,String> Vedio_Introduce = new HashMap<>();
    Map<String,String> Vedio_Name = new HashMap<>();


    StringBuilder response_designer;
    StringBuilder response_unit;
    StringBuilder response_style;
    StringBuilder response_associatedperson;
    StringBuilder response_researchliterature;
    StringBuilder response_usechange;
    StringBuilder response_vedio;


    static String url = "http://202.114.41.165:8080";


    //为activity_main界面组件绑定数据，并为下级界面传递数据
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Bundle bundle1 = getIntent().getExtras();
                Boolean Sign =  Name.containsKey(bundle1.getString("buildid"));
                if (Sign == true){
                    String s = Name.get(bundle1.getString("buildid"));
                    TextView txde = (TextView)findViewById(R.id.designer);
                    txde.setText(s);
                    String buildid = bundle1.getString("buildid");
                    final Bundle bundle2 = new Bundle();
                    bundle2.putString("name", Name.get(buildid));
                    bundle2.putString("birthday", Birthday.get(buildid));
                    bundle2.putString("nativeplace", NativePlace.get(buildid));
                    bundle2.putString("sex", Sex.get(buildid));
                    bundle2.putString("introduce", Introduce.get(buildid));
                    bundle2.putString("otherworksintroduction", OtherWorksIntroduction.get(buildid));
                    bundle2.putString("picture", Picture.get(buildid));
                    bundle2.putString("school", School.get(buildid));
                    bundle2.putString("achievement", Achievement.get(buildid));
                    final Intent i = new Intent(MainActivity.this, Designer.class);
                    i.putExtras(bundle2);
                    TextView text3 = (TextView) findViewById(R.id.designer);
                    if (s != null) {
                        text3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(MainActivity.this, Designer.class);
                                i.putExtras(bundle2);
                                startActivity(i);
                            }
                        });
                    }
                }
                else {
                    LinearLayout Designer = (LinearLayout)findViewById(R.id.Designer);
                    Designer.setVisibility(View.GONE);
                }
            }

            if (msg.what == 2){
                Bundle bundle1 = getIntent().getExtras();
                if (Unit_Name.containsKey(bundle1.getString("buildid"))) {

                    String s = Unit_Name.get(bundle1.getString("buildid"));
                    TextView tx = (TextView) findViewById(R.id.unit);
                    tx.setText(s);
                    //为Unit界面传递数据
                    String buildid = bundle1.getString("buildid");
                    final Bundle bundle2 = new Bundle();
                    bundle2.putString("name",Unit_Name.get(buildid));
                    bundle2.putString("introduction", Unit_Introduction.get(buildid));
                    bundle2.putString("representativeworks", Unit_Representativeworks.get(buildid));
                    bundle2.putString("founderintroduction", Unit_FounderIntroduction.get(buildid));
                    bundle2.putString("pictureurl", Unit_PictureUrl.get(buildid));
                    final Intent i = new Intent(MainActivity.this, Unit.class);
                    i.putExtras(bundle2);
                    TextView text3 = (TextView) findViewById(R.id.unit);

                    text3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MainActivity.this, Unit.class);
                            i.putExtras(bundle2);
                            startActivity(i);
                        }
                    });
                }

                else {
                    LinearLayout Construction_unit = (LinearLayout)findViewById(R.id.Construction_unit);
                    Construction_unit.setVisibility(View.GONE);
                }


            }

            if (msg.what == 3){
                Bundle bundle3 = getIntent().getExtras();

                int count = 0;

                for(String getKey:Style_BuildID.keySet()){
                    if(Style_BuildID.get(getKey).equals(bundle3.getString("buildid"))){
                        count++;
                        LinearLayout layout = (LinearLayout)findViewById(R.id.Construction_style);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText(Style_Name.get(getKey));
                        tv.setTextColor(Color.parseColor("#3399FF"));
                        tv.setTextSize(13);
                        TextPaint tp = tv.getPaint();
                        tp.setFakeBoldText(true);
                        layoutParams.setMargins(5,0,15,0);
                        layout.addView(tv,layoutParams);

                        Bundle bundle = new Bundle();
                        bundle.putString("name",Style_Name.get(getKey));
                        bundle.putString("buildstyleid",Style_BuildStyleID.get(getKey));
                        bundle.putString("picture",Style_Picture.get(getKey));
                        bundle.putString("style",Style_Style.get(getKey));
                        bundle.putString("currentuse",Style_CurrentUse.get(getKey));
                        bundle.putString("historicalvalue",Style_HistoricalValue.get(getKey));
                        bundle.putString("representativebuildintroduction",Style_RepresentativeBuildIntroduction.get(getKey));
                        final Intent i = new Intent(MainActivity.this,Style.class);
                        i.putExtras(bundle);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(i);
                            }
                        });
                    }
                }

                if (count == 0){
                    LinearLayout Construction_style = (LinearLayout)findViewById(R.id.Construction_style);
                    Construction_style.setVisibility(View.GONE);
                }

//                if (Style_Name.containsKey(bundle3.getString("buildid"))){
//
//                    String s = Style_Name.get(bundle3.getString("buildid"));
//                    Log.i("VVVV",s);
//                    TextView tx = (TextView)findViewById(R.id.Architectural_style);
//                    tx.setText(s);
//
//                    String buildid = bundle3.getString("buildid");
//                    Bundle bundle = new Bundle();
//                    bundle.putString("name",Style_Name.get(buildid));
//                    bundle.putString("picture",Style_Picture.get(buildid));
//                    bundle.putString("style",Style_Style.get(buildid));
//                    bundle.putString("currentuse",Style_CurrentUse.get(buildid));
//                    bundle.putString("historicalvalue",Style_HistoricalValue.get(buildid));
//                    bundle.putString("representativebuildintroduction",Style_RepresentativeBuildIntroduction.get(buildid));
//                    final Intent i = new Intent(MainActivity.this,Style.class);
//                    i.putExtras(bundle);
//                    tx.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(i);
//                        }
//                    });
//                }
//
//                else {
//                    LinearLayout Construction_style = (LinearLayout)findViewById(R.id.Construction_style);
//                    Construction_style.setVisibility(View.GONE);
//                }
//
//
            }

            if (msg.what == 4){
                Bundle bundle4 = getIntent().getExtras();
                //设置count来判断每个建筑是否包含关联人物(通过一个计数器来判断AssociatedPerson_BuildID集合中是否含有相应建筑对应的相关文献)
                int count=0;
                //由于AssociatedPerson表中的buildid不是主键，故选用ID作为key,同时判断数据库中是否存有buildid对应的AssociatedPerson的相关信息，如果有的话再为其绑定点击事件，并传递数据
                for(String getKey:AssociatedPerson_BuildID.keySet()){
                    if(AssociatedPerson_BuildID.get(getKey).equals(bundle4.getString("buildid"))){
                        count++;
                        LinearLayout layout = (LinearLayout)findViewById(R.id.Associated_layout);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText(AssociatedPerson_Name.get(getKey));
                        tv.setTextColor(Color.parseColor("#3399FF"));
                        layoutParams.setMargins(60,0,20,30);
                        layout.addView(tv,layoutParams);

                        Bundle bundle = new Bundle();
                        bundle.putString("name",AssociatedPerson_Name.get(getKey));
                        bundle.putString("picture",AssociatedPerson_Picture.get(getKey));
                        bundle.putString("birthday",AssociatedPerson_Birthday.get(getKey));
                        bundle.putString("sex",AssociatedPerson_Sex.get(getKey));
                        bundle.putString("nativeplace",AssociatedPerson_NativePlace.get(getKey));
                        bundle.putString("personalprofile",AssociatedPerson_PersonalProfile.get(getKey));
                        bundle.putString("relatedevents",AssociatedPerson_RelatedEvents.get(getKey));
                        final Intent i = new Intent(MainActivity.this,Associated_people.class);
                        i.putExtras(bundle);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(i);
                            }
                        });
                        }
                }

                if (count ==0){
                    LinearLayout Associated_person0 = (LinearLayout)findViewById(R.id.Associated_person0);
                    LinearLayout Associated_person1 = (LinearLayout)findViewById(R.id.Associated_person1);
                    LinearLayout Associated_layout = (LinearLayout)findViewById(R.id.Associated_layout);
                    Associated_person0.setVisibility(View.GONE);
                    Associated_person1.setVisibility(View.GONE);
                    Associated_layout.setVisibility(View.GONE);
                }
            }

            if (msg.what == 5){
                Bundle bundle5 = getIntent().getExtras();
                int count = 0;
                for(String getKey:ResearchLiterature_BuildID.keySet()){
                    if (ResearchLiterature_BuildID.get(getKey).equals(bundle5.getString("buildid"))){
                        count++;
                        LinearLayout layout = (LinearLayout)findViewById(R.id.Literature_layout);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText(ResearchLiterature_Name.get(getKey));
                        tv.setTextColor(Color.parseColor("#3399FF"));
                        layoutParams.setMargins(60,0,20,30);
                        layout.addView(tv,layoutParams);

                        Bundle bundle = new Bundle();
                        bundle.putString("name",ResearchLiterature_Name.get(getKey));
                        bundle.putString("writer",ResearchLiterature_Writer.get(getKey));
                        bundle.putString("type",ResearchLiterature_Type.get(getKey));
                        bundle.putString("unit",ResearchLiterature_Unit.get(getKey));
                        bundle.putString("provenance",ResearchLiterature_Provenance.get(getKey));
                        bundle.putString("downfile",ResearchLiterature_Downfile.get(getKey));
                        bundle.putString("link",ResearchLiterature_Link.get(getKey));
                        bundle.putString("pictureurl",ResearchLiterature_PictureUrl.get(getKey));
                        final  Intent i = new Intent(MainActivity.this,Literature.class);
                        i.putExtras(bundle);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(i);
                            }
                        });

                    }

                }

                if (count==0){
                    LinearLayout Related_literature0 = (LinearLayout)findViewById(R.id.Related_literature0);
                    LinearLayout Related_literature1 = (LinearLayout)findViewById(R.id.Related_literature1);
                    LinearLayout Literature_layout = (LinearLayout)findViewById(R.id.Literature_layout);
                    Related_literature0.setVisibility(View.GONE);
                    Related_literature1.setVisibility(View.GONE);
                    Literature_layout.setVisibility(View.GONE);
                }


            }

            if (msg.what == 6){
                Bundle bundle6 = getIntent().getExtras();
                int count = 0;
                for(String getKey:UseChange_BuildID.keySet()){
                    if (UseChange_BuildID.get(getKey).equals(bundle6.getString("buildid"))){
                        if (UseChange_ChangeTime.get(getKey).equals("null")){
                            continue;
                        }
                        else {
                            count++;
                            LinearLayout layout = (LinearLayout)findViewById(R.id.Use_change);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView tv = new TextView(getApplicationContext());
                            tv.setText(UseChange_ChangeTime.get(getKey));
                            tv.setTextColor(Color.parseColor("#3399FF"));
                            layoutParams.setMargins(60,0,20,30);
                            layout.addView(tv,layoutParams);

                            Bundle bundle = new Bundle();
                            bundle.putString("changetime",UseChange_ChangeTime.get(getKey));
                            bundle.putString("useunit",UseChange_UseUnit.get(getKey));
                            bundle.putString("changeoverview",UseChange_ChangeOverview.get(getKey));
                            final  Intent i = new Intent(MainActivity.this,UseChange.class);
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

                if (count==0){
                    LinearLayout Use_change0 = (LinearLayout)findViewById(R.id.Use_change0);
                    LinearLayout Use_change1 = (LinearLayout)findViewById(R.id.Use_change1);
                    LinearLayout Use_change = (LinearLayout)findViewById(R.id.Use_change);
                    Use_change0.setVisibility(View.GONE);
                    Use_change1.setVisibility(View.GONE);
                    Use_change.setVisibility(View.GONE);
                }


            }

            if (msg.what == 7){
                Bundle bundle7 = getIntent().getExtras();
                int count = 0;
                for(String getKey:Vedio_BuildID.keySet()){
                    if (Vedio_BuildID.get(getKey).equals(bundle7.getString("buildid"))){
                        if (Vedio_Name.get(getKey).equals("null")){
                            continue;
                        }
                        else {
                            count++;
                            LinearLayout layout = (LinearLayout)findViewById(R.id.Vedio);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView tv = new TextView(getApplicationContext());
                            tv.setText(Vedio_Name.get(getKey));
                            tv.setTextColor(Color.parseColor("#3399FF"));
                            layoutParams.setMargins(60,0,20,30);
                            layout.addView(tv,layoutParams);

//                            Bundle bundle = new Bundle();
//                            bundle.putString("introduce",Vedio_Introduce.get(getKey));
//                            bundle.putString("resourceurl",Vedio_ResourceUrl.get(getKey));
//                            final  Intent i = new Intent(MainActivity.this,UseChange.class);
//                            i.putExtras(bundle);
//                            tv.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    startActivity(i);
//
//                                }
//                            });

                        }

                    }

                }

                if (count==0){
                    LinearLayout Vedio = (LinearLayout)findViewById(R.id.Vedio);
                    LinearLayout Vedio0 = (LinearLayout)findViewById(R.id.Vedio0);
                    LinearLayout Vedio1 = (LinearLayout)findViewById(R.id.Vedio1);
                    Vedio.setVisibility(View.GONE);
                    Vedio0.setVisibility(View.GONE);
                    Vedio1.setVisibility(View.GONE);
                }


            }

            //为界面中的每个组件绑定数据
            //以下组件的数据可以从buildinglist直接获取
            if (msg.what == 8){
                Bundle bundle = getIntent().getExtras();
                ImageView pic = (ImageView)findViewById(R.id.build_picture);
                String url = bundle.getString("picture");
                if (url.equals("http://202.114.41.165:8080null")){
                    pic.setImageResource(R.drawable.nopicture);
                }
                else {
                    Glide.with(pic.getContext()).load(url).into(pic);
                }

                TextView name = (TextView)findViewById(R.id.txtView_ItemName);
                name.setText(bundle.getString("name"));

                TextView year = (TextView)findViewById(R.id.build_year);
                LinearLayout Construction_age = (LinearLayout)findViewById(R.id.Construction_age);
                if (bundle.getString("year").equals("null")) {
                    Construction_age.setVisibility(View.GONE);
                }
                else {
                    year.setText(bundle.getString("year"));
                }

                TextView district = (TextView)findViewById(R.id.build_district);
                LinearLayout Administrative_district = (LinearLayout)findViewById(R.id.Administrative_district);
                if (bundle.getString("district").equals("null")){
                    Administrative_district.setVisibility(View.GONE);
                }
                else {
                    district.setText(bundle.getString("district"));
                }

                //建筑物简介
                TextView introduction = (TextView)findViewById(R.id.building_intro);
                introduction.setMovementMethod(LinkMovementMethod.getInstance());
                String introduction_content = bundle.getString("introduction");
                LinearLayout intro0 = (LinearLayout)findViewById(R.id.intro0);
                LinearLayout intro1 = (LinearLayout)findViewById(R.id.intro1);
                LinearLayout intro2 = (LinearLayout)findViewById(R.id.intro2);
                if (introduction_content.equals("null")){
                    intro0.setVisibility(View.GONE);
                    intro1.setVisibility(View.GONE);
                    intro2.setVisibility(View.GONE);
                }
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    introduction.setText(Html.fromHtml(introduction_content,Html.FROM_HTML_MODE_COMPACT));
                }
                else{
                    introduction.setText(Html.fromHtml(introduction_content));
                }

                //当前用途
                TextView use = (TextView)findViewById(R.id.building_use);
                LinearLayout use0 = (LinearLayout)findViewById(R.id.use0);
                LinearLayout use1 = (LinearLayout)findViewById(R.id.use1);
                LinearLayout use2 = (LinearLayout)findViewById(R.id.use2);
                if (bundle.getString("use").equals("null")){
                    use0.setVisibility(View.GONE);
                    use1.setVisibility(View.GONE);
                    use2.setVisibility(View.GONE);
                }
                else {
                    use.setMovementMethod(LinkMovementMethod.getInstance());
                    String use_content = bundle.getString("use");
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        use.setText(Html.fromHtml(use_content,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        use.setText(Html.fromHtml(use_content));
                    }
                }

                //历史价值
                TextView value = (TextView)findViewById(R.id.build_val);
                LinearLayout value0 = (LinearLayout)findViewById(R.id.value0);
                LinearLayout value1 = (LinearLayout)findViewById(R.id.value1);
                LinearLayout value2 = (LinearLayout)findViewById(R.id.value2);
                Log.i("TAGGGG",bundle.getString("value"));
                if (bundle.getString("value").equals("null")){
                    value0.setVisibility(View.GONE);
                    value1.setVisibility(View.GONE);
                    value2.setVisibility(View.GONE);
                }
                else {
                    value.setMovementMethod(LinkMovementMethod.getInstance());
                    String value_content = bundle.getString("value");
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        value.setText(Html.fromHtml(value_content,Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        value.setText(Html.fromHtml(value_content));
                    }
                }

                TextView street = (TextView)findViewById(R.id.street);
                LinearLayout Street = (LinearLayout)findViewById(R.id.Street);
                if (bundle.getString("street").equals("null")){
                    Street.setVisibility(View.GONE);
                }
                else {
                    street.setText(bundle.getString("street"));
                    street.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle1 = getIntent().getExtras();
                            String buildid = bundle1.getString("buildid");
                            Intent i = new Intent(MainActivity.this, Street.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("buildid", buildid);
                            i.putExtras(bundle1);
                            startActivity(i);
                        }
                    });
                }
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Message msg = new Message();
        msg.what = 8;
        handler.sendMessage(msg);

        TextView btn1 = (TextView) findViewById(R.id.more_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                String pictureid = bundle.getString("buildid");
                Intent i = new Intent(MainActivity.this, ImageDisplay.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("buildid", pictureid);
                bundle2.putString("imagedisplay_name",bundle.getString("name"));
                i.putExtras(bundle2);
                startActivity(i);
            }
        });

        View img1 = (LinearLayout)findViewById(R.id.main_back);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

        //需要二次读取的数据

        //从designer中读取设计师的所有数据
        requestUsingHttpURLConnectionGetAllDesigners();
        //从buildingunit中读取建造单位的所有数据
        requestUsingHttpURLConnectionGetBuildingUnit();
        //从buildingstyle中读取建造风格的所有数据
        requestUsingHttpURLConnectionGetBuildingStyle();
        //从associatedperson中读取关联人物的所有数据
        requestUsingHttpURLConnectionGetAssociatePerson();
        //从researchliterature中读取相关文献的所有数据
        requestUsingHttpURLConnectionGetResearchLiterature();
        //从usechange中读取历史建筑用途变迁的所有数据
        requestUsingHttpURLConnectionGetUseChange();
        //从vedio中读取指定buildid的相关视频的所有数据
        Bundle bundle = getIntent().getExtras();
        String buildid = bundle.getString("buildid");
        requestUsingHttpURLConnectionGetVedio(buildid);
    }

    public void requestUsingHttpURLConnectionGetAllDesigners(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetAllDesigners"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response_designer = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response_designer.append(line);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray a = new JSONArray(response_designer.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        Name.put(b.getString("BuildID"),b.getString("Name"));
                        DesignerID.put(b.getString("BuildID"),b.getString("DesignerID"));
                        Birthday.put(b.getString("BuildID"),b.getString("Birthday"));
                        NativePlace.put(b.getString("BuildID"),b.getString("NativePlace"));
                        Sex.put(b.getString("BuildID"),b.getString("Sex"));
                        Introduce.put(b.getString("BuildID"),b.getString("Introduce"));
                        OtherWorksIntroduction.put(b.getString("BuildID"),b.getString("OtherWorksIntroduction"));
                        Picture.put(b.getString("BuildID"),url + b.getString("Picture"));
                        School.put(b.getString("BuildID"),b.getString("School"));
                        Achievement.put(b.getString("BuildID"),b.getString("Achievement"));
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
                        Style_BuildID.put(b.getString("id"),b.getString("BuildID"));
                        Style_BuildStyleID.put(b.getString("id"),b.getString("BuildStyleID"));
                        Style_Name.put(b.getString("id"),b.getString("Name"));
                        Style_RepresentativeBuildIntroduction.put(b.getString("id"),b.getString("RepresentativeBuildIntroduction"));
                        Style_Style.put(b.getString("id"),b.getString("Style"));
                        Style_Picture.put(b.getString("id"),url + b.getString("Picture"));
                        Style_CurrentUse.put(b.getString("id"),b.getString("CurrentUse"));
                        Style_HistoricalValue.put(b.getString("id"),b.getString("HistoricalValue"));
                    }
                    Message msg = new Message();
                    msg.what = 3;
                    handler.sendMessage(msg);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void requestUsingHttpURLConnectionGetBuildingUnit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetBuildingUnit"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response_unit = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response_unit.append(line);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray a = new JSONArray(response_unit.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        Unit_Name.put(b.getString("BuildID"),b.getString("Name"));
                        Unit_BuildUnitID.put(b.getString("BuildID"),b.getString("BuildUnitID"));
                        Unit_Introduction.put(b.getString("BuildID"),b.getString("Introduction"));
                        Unit_Representativeworks.put(b.getString("BuildID"),b.getString("Representativeworks"));
                        Unit_FounderIntroduction.put(b.getString("BuildID"),b.getString("FounderIntroduction"));
                        Unit_PictureUrl.put(b.getString("BuildID"),url + b.getString("PictureUrl"));
                    }
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void requestUsingHttpURLConnectionGetAssociatePerson(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetAssociatePerson"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response_associatedperson = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response_associatedperson.append(line);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray a = new JSONArray(response_associatedperson.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        AssociatedPerson_BuildID.put(b.getString("ID"),b.getString("BuildID"));
                        AssociatedPerson_AssociatePersonID.put(b.getString("ID"),b.getString("AssociatePersonID"));
                        AssociatedPerson_Name.put(b.getString("ID"),b.getString("Name"));
                        AssociatedPerson_Birthday.put(b.getString("ID"),b.getString("Birthday"));
                        AssociatedPerson_Sex.put(b.getString("ID"),b.getString("Sex"));
                        AssociatedPerson_NativePlace.put(b.getString("ID"),b.getString("NativePlace"));
                        AssociatedPerson_PersonalProfile.put(b.getString("ID"),b.getString("PersonalProfile"));
                        AssociatedPerson_Picture.put(b.getString("ID"),url + b.getString("Picture"));
                        AssociatedPerson_RelatedEvents.put(b.getString("ID"),b.getString("RelatedEvents"));
                        AssociatedPerson_AssocaiteIntroduce.put(b.getString("ID"),b.getString("AssocaiteIntroduce"));
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

    public void requestUsingHttpURLConnectionGetResearchLiterature(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetResearchLiterature"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response_researchliterature = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response_researchliterature.append(line);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray a = new JSONArray(response_researchliterature.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        ResearchLiterature_BuildID.put(b.getString("ID"),b.getString("BuildID"));
                        ResearchLiterature_LiteratureID.put(b.getString("ID"),b.getString("LiteratureID"));
                        ResearchLiterature_Writer.put(b.getString("ID"),b.getString("Writer"));
                        ResearchLiterature_Unit.put(b.getString("ID"),b.getString("Unit"));
                        ResearchLiterature_Name.put(b.getString("ID"),b.getString("Name"));
                        ResearchLiterature_Type.put(b.getString("ID"),b.getString("Type"));
                        ResearchLiterature_Provenance.put(b.getString("ID"),b.getString("Provenance"));
                        ResearchLiterature_Link.put(b.getString("ID"),b.getString("Link"));
                        ResearchLiterature_Downfile.put(b.getString("ID"),b.getString("Downfile"));
                        ResearchLiterature_PictureUrl.put(b.getString("ID"),url + b.getString("PictureUrl"));
                    }
                    Message msg = new Message();
                    msg.what = 5;
                    handler.sendMessage(msg);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void requestUsingHttpURLConnectionGetUseChange(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetUseChange"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response_usechange = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response_usechange.append(line);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray a = new JSONArray(response_usechange.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        UseChange_BuildID.put(b.getString("ID"),b.getString("BuildID"));
                        UseChange_ChangeTime.put(b.getString("ID"),b.getString("ChangeTime"));
                        UseChange_UseUnit.put(b.getString("ID"),b.getString("UseUnit"));
                        UseChange_ChangeOverview.put(b.getString("ID"),b.getString("ChangeOverview"));
                    }
                    Message msg = new Message();
                    msg.what = 6;
                    handler.sendMessage(msg);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void requestUsingHttpURLConnectionGetVedio(final String build){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetVideoByBuildID/"+build); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response_vedio = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response_vedio.append(line);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray a = new JSONArray(response_vedio.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        Vedio_BuildID.put(b.getString("ID"),b.getString("BuildID"));
                        Vedio_ResourceUrl.put(b.getString("ID"),url + b.getString("ResourceUrl"));
                        Vedio_Introduce.put(b.getString("ID"),b.getString("Introduce"));
                        Vedio_Name.put(b.getString("ID"),b.getString("Name"));
                    }
                    Message msg = new Message();
                    msg.what = 7;
                    handler.sendMessage(msg);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
