package com.example.jh.buildings.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.jh.buildings.Fragment.FragemntBuilding;
import com.example.jh.buildings.Fragment.FragmentMap;
import com.example.jh.buildings.R;

public class MainPageActivity extends Activity{

    private FrameLayout fl_container;
    private BottomNavigationView BottomNavigationViewer;

    private FragmentManager fragmentManager=getFragmentManager();
    private FragemntBuilding fragemntBuilding;
    private FragmentMap fragmentMap;

    private boolean ComeFromJSInterface=false;
    private String buildID="";

    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);
            Log.i("TAG","in handler");
            BottomNavigationViewer.setSelectedItemId(R.id.BottomNavigationMenu_Building);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        boolean net = isNetworkAvailable();
        if (net == false){
            Toast.makeText(MainPageActivity.this,"请打开网络连接",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
            startActivity(intent);
        }

        initControls();

        fragmentMap=new FragmentMap();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.add(R.id.fl_container,fragmentMap);
        ft.commit();
    }


    //检查网络是否连接
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void initControls(){
        fl_container=findViewById(R.id.fl_container);
        BottomNavigationViewer=findViewById(R.id.BottomNavigationViewer);

        BottomNavigationViewer.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.BottomNavigationMenu_Map:
                        FragmentTransaction ft1=fragmentManager.beginTransaction();
                        HideAllFragment(ft1);
                        if (fragmentMap==null){
                            fragmentMap=new FragmentMap();
                            ft1.add(R.id.fl_container,fragmentMap);
                        }
                        else {
                            ft1.show(fragmentMap);
                        }
                        ft1.commit();
                        return true;
                    case R.id.BottomNavigationMenu_Building:
                        FragmentTransaction ft2=fragmentManager.beginTransaction();
                        HideAllFragment(ft2);
                        if (fragemntBuilding==null){
                            fragemntBuilding=new FragemntBuilding();
                            ft2.add(R.id.fl_container,fragemntBuilding);
                        }
                        else {
                            ft2.show(fragemntBuilding);
                        }
                        ft2.commit();

                        // if (ComeFromJSInterface){
                        //     fragemntBuilding.Jump2ListViewItemByID(buildID);
                        //     ComeFromJSInterface=false;
                        // }
                         return true;
                }
                return false;
            }
        });
    }

    public void HideAllFragment(FragmentTransaction fragmentTransaction){
        if (fragemntBuilding!=null){
            fragmentTransaction.hide(fragemntBuilding);
        }
        if (fragmentMap!=null){
            fragmentTransaction.hide(fragmentMap);
        }
    }

    public void JudgeFragmentJump2Item(String BuildID){

        // Determine if the fragemntBuilding is created
        ComeFromJSInterface=true;
        buildID=BuildID;
        mhandler.obtainMessage();
        // BottomNavigationViewer.setSelectedItemId(R.id.BottomNavigationMenu_Building);
        // FragmentTransaction ft=fragmentManager.beginTransaction();
        // HideAllFragment(ft);
        // if (fragemntBuilding==null){
        //     fragemntBuilding=new FragemntBuilding();
        //     ft.add(R.id.fl_container,fragemntBuilding,"fragemntBuilding");
        // }
        // else {
        //     ft.show(fragemntBuilding);
        // }
        // ft.commit();

        // BottomNavigationViewer.getMenu().findItem(R.id.BottomNavigationMenu_Building).setChecked(true);
        Log.i("TAG","MainPageActivity create fragment");
        Log.i("TAG","BuildID: "+buildID);

        // The fragemntBuilding has been created,then jump to the item
        fragemntBuilding.Jump2ListViewItemByID(BuildID);
        Log.i("TAG","MainPageActivity Jump2ListViewItemByID");
    }
}
