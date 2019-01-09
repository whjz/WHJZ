package com.example.jh.buildings.Fragment;

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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FragemntBuilding extends Fragment {

    //存储从building表中读取出的数据
    Map<String,String> Buildid = new LinkedHashMap<>();
    Map<String,String> Year = new LinkedHashMap<>();
    Map<String,String> District = new LinkedHashMap();
    Map<String,String> Introduction = new LinkedHashMap<>();
    Map<String,String> Street = new LinkedHashMap<>();
    Map<String,String> Styleid = new LinkedHashMap<>();
    Map<String,String> Use = new LinkedHashMap<>();
    Map<String,String> Value = new LinkedHashMap<>();
    Map<String,String> Picture = new LinkedHashMap<>();

    //存放BuildingListAdapter所需的数据
    List<BuildingListBean> itemBuildingList;
    //StringBuilder response;
    static String url = "http://202.114.41.165:8080";
    private BuildingListAdapter buildingListAdapter;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                buildingListAdapter=new BuildingListAdapter(getActivity(),itemBuildingList);
                listView.setAdapter(buildingListAdapter);
            }
        }

    };
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private ListView listView;
    private String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.buildings_list,null);
        requestUsingHttpURLConnection();
        listView = (ListView)view.findViewById(R.id.buildinglist);
        //为buildinglist设置监听，点击某一item后将相应数据传入对应的mainactivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG","enter 1");
                Iterator it = Buildid.entrySet().iterator();
                while (it.hasNext()){
                    position--;
                    Map.Entry entry = (Map.Entry)it.next();
                    if (position<0) {
                        name = (String) entry.getKey();
                        break;
                    }
                }

                String buildid = Buildid.get(name);
                String year = Year.get(buildid);
                String district = District.get(buildid);
                String introduction = Introduction.get(buildid);
                String street = Street.get(buildid);
                String styleid = Styleid.get(buildid);
                String use = Use.get(buildid);
                String value = Value.get(buildid);
                String picture = Picture.get(buildid);
                Bundle bundle = new Bundle();
                bundle.putString("buildid",buildid);
                bundle.putString("name",name);
                bundle.putString("year",year);
                bundle.putString("district",district);
                bundle.putString("introduction",introduction);
                bundle.putString("street",street);
                bundle.putString("styleid",styleid);
                bundle.putString("use",use);
                bundle.putString("value",value);
                bundle.putString("picture",picture);
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.putExtras(bundle);
                startActivity(i);
                //finish();
            }
        });

        //实现建筑列表界面的下拉刷新功能
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestUsingHttpURLConnection();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void requestUsingHttpURLConnection() {
        // 网络通信属于典型的耗时操作，开启新线程进行网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                BuildingListBean bean;
                HttpURLConnection connection = null;
                StringBuilder response = null;
                itemBuildingList=new ArrayList<>();
                try {
                    URL url = new URL("http://202.114.41.165:8080/WHJZProject/servlet/GetBuildingList"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    Log.i("TAG",connection.toString());
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    Log.i("TAG",response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray a = new JSONArray(response.toString());
                    for (int i = 0;i < a.length();i++) {
                        JSONObject b = a.getJSONObject(i);
                        bean = new BuildingListBean();
                        bean.setName(b.getString("Name"));
                        bean.setDistrict(b.getString("District"));
                        bean.setLevel(b.getString("Level"));
                        bean.setUrl(url + b.getString("Picture"));
                        itemBuildingList.add(bean);

                        Buildid.put(b.getString("Name"),b.getString("BuildID"));
                        Year.put(b.getString("BuildID"),b.getString("Year").toString());
                        District.put(b.getString("BuildID"),b.getString("District"));
                        Introduction.put(b.getString("BuildID"),b.getString("BuildIntroduction"));
                        Street.put(b.getString("BuildID"),b.getString("Street"));
                        Styleid.put(b.getString("BuildID"),b.getString("BuildStyleID"));
                        Use.put(b.getString("BuildID"),b.getString("CurrentUse"));
                        Value.put(b.getString("BuildID"),b.getString("HistoricalValue"));
                        Picture.put(b.getString("BuildID"),url + b.getString("Picture"));


                    }
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
                catch (JSONException e){
                    Log.i("TAG","json phase wrong");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void Jump2ListViewItemByID(String BuildID){
        Log.i("TAG","FragmentBuilding start judge");
        if (Year.containsKey(BuildID)){
            Set<String> set=Year.keySet();
            Object[] s= set.toArray();
            int position=0;
            for (int i=0;i<s.length;i++){
                if (BuildID.equals(s[i])){
                    position=i;
                    break;
                }
            }


            Log.i("TAG","FragmentBuilding before click");
            Log.i("TAG","FragmentBuilding position: "+position);
            // listView.getAdapter().getView(position, null, null).performClick();


            //            listView.performItemClick(
            //                                    listView.getAdapter().getView(1,null,null),
            //                                    1,
            //                                    listView.getAdapter().getItemId(1));
            //

            AdapterView.OnItemClickListener onItemClickListener = listView.getOnItemClickListener();
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(listView,View.inflate(getActivity(),R.layout.buildinglist_item,null),position,position);
            }
            //            Log.i("TAAA","FragmentBuilding before click");
            //             final int iposition=position;
            //             new Handler().post(new Runnable() {
            //                 @Override
            //                 public void run() {
            //                     listView.performItemClick(
            //                             listView.getAdapter().getView(iposition,null,null),
            //                             iposition,
            //                             listView.getAdapter().getItemId(iposition));
            //                 }
            //             });


        }
        else {
            // exception
            Log.i("TAG","exception");
            return;
        }
    }
}
