package com.example.jh.buildings.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.jh.buildings.JSInterface;
import com.example.jh.buildings.R;

public class FragmentMap extends Fragment {
    private WebView wv_map;
    private JSInterface jsInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_map,null);

        initControls(view);
        wv_map.loadUrl("file:////android_asset/whjz/whjz.html");
        WebSettings webSettings=wv_map.getSettings();
        webSettings.setJavaScriptEnabled(true);

        jsInterface=new JSInterface(getActivity());
        wv_map.addJavascriptInterface(jsInterface,"jsInterface");

        return view;
    }

    public void initControls(View view){
        wv_map=view.findViewById(R.id.wv_map);
    }
}
