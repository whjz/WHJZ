package com.example.jh.buildings.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jh.buildings.JavaBean.BuildingListBean;
import com.example.jh.buildings.R;

import java.util.List;

public class BuildingListAdapter extends BaseAdapter {

    private List<BuildingListBean> bList;
    private LayoutInflater mfla;
    private Context mcontext;

    public BuildingListAdapter(Context context,List<BuildingListBean> list){
        bList = list;
        this.mcontext = context;
        mfla = LayoutInflater.from(context);
    }


    public int getCount() {
        return bList.size();
    }

    @Override
    //指定的索引对应的数据项
    public Object getItem(int position) {
        return bList.get(position);
    }

    @Override
    //指定的索引对应的数据项ID
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mfla.inflate(R.layout.buildinglist_item,null);
            viewHolder.buildingname = (TextView)convertView.findViewById(R.id.item_name);
            viewHolder.buildingdistrict = (TextView)convertView.findViewById(R.id.item_district);
            viewHolder.buildinglevel = (TextView)convertView.findViewById(R.id.item_level);
            viewHolder.buildingpicture = (ImageView)convertView.findViewById(R.id.item_picture);
            convertView.setTag(viewHolder);
        }

        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.buildingname.setText(bList.get(position).getName());
        viewHolder.buildingdistrict.setText(bList.get(position).getDistrict());
        viewHolder.buildinglevel.setText(bList.get(position).getLevel());
        if (bList.get(position).getUrl().equals("http://202.114.41.165:8080null")){
            viewHolder.buildingpicture.setImageResource(R.drawable.nopicture);
        }
        else {
            Glide.with(mcontext).load(bList.get(position).getUrl()).into(viewHolder.buildingpicture);
        }
        return convertView;
    }

    public class ViewHolder{
        public ImageView buildingpicture;
        public TextView buildingname;
        public TextView buildingdistrict;
        public TextView buildinglevel;
    }
}
