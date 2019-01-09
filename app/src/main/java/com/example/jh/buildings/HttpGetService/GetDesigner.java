package com.example.jh.buildings.HttpGetService;


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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDesigner {

    //public Map<String,String> BuildID;

    public Map<String,String> Name = new HashMap<String, String>();
    public Map<String,String> DesignerID = new HashMap<>();
    public Map<String,String> Birthday = new HashMap<>();
    public Map<String,String> NativePlace = new HashMap<>();
    public Map<String,String> Sex = new HashMap<>();
    public Map<String,String> Introduce = new HashMap<>();
    public Map<String,String> OtherWorksIntroduction = new HashMap<>();
    public Map<String,String> Picture = new HashMap<>();
    public Map<String,String> School = new HashMap<>();
    public Map<String,String> Achievement = new HashMap<>();
    public boolean finished=false;

    StringBuilder response;

    public void requestUsingHttpURLConnection(){

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
                    //Log.i("ta",a.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        Name.put(b.getString("BuildID"),b.getString("Name"));
                        DesignerID.put(b.getString("BuildID"),b.getString("DesignerID"));
                        Birthday.put(b.getString("BuildID"),b.getString("Birthday"));
                        NativePlace.put(b.getString("BuildID"),b.getString("NativePlace"));
                        Sex.put(b.getString("BuildID"),b.getString("Sex"));
                        Introduce.put(b.getString("BuildID"),b.getString("Introduce"));
                        OtherWorksIntroduction.put(b.getString("BuildID"),b.getString("OtherWorksIntroduction"));
                        Picture.put(b.getString("BuildID"),b.getString("Picture"));
                        School.put(b.getString("BuildID"),b.getString("School"));
                        Achievement.put(b.getString("BuildID"),b.getString("Achievement"));
                    }
                    finished=true;
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
