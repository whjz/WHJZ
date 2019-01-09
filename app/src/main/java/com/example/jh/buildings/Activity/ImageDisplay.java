package com.example.jh.buildings.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import android.view.GestureDetector;
import android.view.MotionEvent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jh.buildings.Activity.MainActivity;
import com.example.jh.buildings.Adapter.BuildingListAdapter;
import com.example.jh.buildings.JavaBean.BuildingListBean;
import com.example.jh.buildings.R;
import com.example.jh.buildings.Adapter.PictureListAdapter;
import com.example.jh.buildings.JavaBean.PictureListBean;

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
import com.example.jh.buildings.R;

public class ImageDisplay extends Activity{

    //从picture中读取数据
    Map<String,String> Picture_Source = new HashMap<>();
    Map<String,String> Picture_Url = new HashMap<>();
    Map<String,String> Picture_Name = new HashMap<>();
    Map<String,String> Picture_Introduce = new HashMap<>();

    StringBuilder response_picture;
    List<PictureListBean> itemPictureList;
    private String buildid;
    private Context context;
    //StringBuilder response;
    static String url = "http://202.114.41.165:8080";
    private PictureListAdapter pictureListAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                pictureListAdapter=new PictureListAdapter(context,itemPictureList);
                listView.setAdapter(pictureListAdapter);

            }
        }
    };
    private View imgBack;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.imagelist);

        context = this;
        listView = (ListView) findViewById(R.id.picturelist);
        Bundle bundle = getIntent().getExtras();
        buildid = bundle.getString("buildid");
        String imagedisplay_name = bundle.getString("imagedisplay_name");
        TextView textView = (TextView)findViewById(R.id.imagedisplay_name);
        textView.setText(imagedisplay_name);
        requestUsingHttpURLConnectionGetPicture();
        imgBack = (LinearLayout)findViewById(R.id.pic_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });
    }
        public void requestUsingHttpURLConnectionGetPicture(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PictureListBean bean;
                    HttpURLConnection connection = null;
                    StringBuilder response = null;
                    itemPictureList = new ArrayList<>();
                    try {
                        URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetPicture"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                        connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                        connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                        connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                        connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                        InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        response_picture = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null){
                            response_picture.append(line);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray a = new JSONArray(response_picture.toString());
                        for (int i = 0;i < a.length();i++) {
                            JSONObject b = a.getJSONObject(i);
                            Picture_Introduce.put(b.getString("BuildID"),b.getString("Introduce"));
                            Picture_Name.put(b.getString("BuildID"),b.getString("Name"));
                            Picture_Source.put(b.getString("BuildID"),b.getString("Source"));
                            Picture_Url.put(b.getString("BuildID"),url + b.getString("Url"));
                            if (b.getString("BuildID").equals(buildid)){
                                bean = new PictureListBean();
                                bean.setName(b.getString("Name"));
                                bean.setIntroduce(b.getString("Introduce"));
                                bean.setSource(b.getString("Source"));
                                bean.setUrl(url + b.getString("Url"));
                                itemPictureList.add(bean);
                            }
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
//        //定义手势检测器实例
//        mDetector = new GestureDetector(this, new gestureListener());
//        VF_itemPics = (ViewFlipper) findViewById(R.id.ViewFlip_itempics);
//        //将此组件上的触摸事件交由GestureDetector处理
//        VF_itemPics.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return mDetector.onTouchEvent(motionEvent);
//            }
//        });
//        VF_itemPics.setClickable(true);
//        VF_itemPics.setLongClickable(true);
//
//

//    }
//
//    //重写GestureDetector中的手势处理方法
//    private class gestureListener implements GestureDetector.OnGestureListener {
//        public boolean onDown(MotionEvent e) {
//            // TODO Auto-generated method stub
//            return false;
//        }
//
//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            // TODO Auto-generated method stub
//            return false;
//        }
//
//        @Override
//        public void onShowPress(MotionEvent e) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            // TODO Auto-generated method stub
//            return false;
//        }
//
//        @Override
//        public void onLongPress(MotionEvent e) {
//            // TODO Auto-generated method stub
//        }
//
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//                               float velocityY) {
//            // TODO Auto-generated method stub
//            if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
//                VF_itemPics.showNext();
//                return true;
//            } else if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
//
//                VF_itemPics.showPrevious();
//                return true;
//            }
//
//            return false;
//        }

}






