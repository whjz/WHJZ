package com.example.jh.buildings.Adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jh.buildings.JavaBean.PictureListBean;
import com.example.jh.buildings.R;

import java.util.List;

public class PictureListAdapter extends BaseAdapter {

    private List<PictureListBean> bList;
    private LayoutInflater mfla;
    private Context mcontext;

    public PictureListAdapter(Context context,List<PictureListBean> list){
        bList = list;
        this.mcontext = context;
        mfla = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bList.size();
    }

    @Override
    public Object getItem(int position) {
        return bList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mfla.inflate(R.layout.image_display_item,null);
            //viewHolder.pictureintroduce = (TextView)convertView.findViewById(R.id.image_intro);
            viewHolder.picturename = (TextView)convertView.findViewById(R.id.image_name);
            viewHolder.picturesource = (TextView)convertView.findViewById(R.id.image_source);
            viewHolder.pictureurl = (ImageView)convertView.findViewById(R.id.image_display);
            convertView.setTag(viewHolder);
        }

        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        String intro = bList.get(position).getIntroduce();
        if (intro.equals("null")){intro = "";}
        else {
            viewHolder.picturename.setMovementMethod(LinkMovementMethod.getInstance());
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                viewHolder.pictureintroduce.setText(Html.fromHtml(intro,Html.FROM_HTML_MODE_COMPACT));
            }
            else{
                viewHolder.picturename.setText(Html.fromHtml(intro));

            }
        }
        viewHolder.picturesource.setText("来源：" + bList.get(position).getSource());
        Glide.with(mcontext).load(bList.get(position).getUrl()).into(viewHolder.pictureurl);
        return convertView;
    }

    public class ViewHolder{
        public ImageView pictureurl;
        public TextView picturename;
        public TextView picturesource;
        public TextView pictureintroduce;
    }
}
