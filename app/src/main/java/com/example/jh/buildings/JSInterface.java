package com.example.jh.buildings;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.example.jh.buildings.Activity.MainPageActivity;

public class JSInterface {
    private Context context;

    public JSInterface(Context context) {
        this.context = context;
    }

    // Jump to the building-item according to the BuildID
    @JavascriptInterface
    public void Jump2ItemByID(String BuildID){
        ((MainPageActivity)context).JudgeFragmentJump2Item(BuildID);
        Log.i("TAG-chromium","chromium: "+BuildID);
    }
}
