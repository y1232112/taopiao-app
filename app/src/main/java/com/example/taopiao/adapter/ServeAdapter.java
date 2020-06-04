package com.example.taopiao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taopiao.R;

import java.util.List;
import java.util.Map;

public class ServeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String, String>> mDatas;

    public ServeAdapter(Context context, List<Map<String, String>> datas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        Map<String, String> map = mDatas.get(position);
        return map;
    }

    @Override
    public long getItemId(int position) {
        Map<String, String> map = mDatas.get(position);
        return Long.parseLong(map.get("cinema_id"));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServeAdapter.ViewHolder holder = null;
        if (mDatas == null) {

            return null;
        } else if (mDatas.size() == 0) {
            return null;
        }
        Map<String, String> map = mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_serve, parent, false);
            holder = new ServeAdapter.ViewHolder();
            holder.tag = (TextView) convertView.findViewById(R.id.serve_tag);
            holder.desc=(TextView) convertView.findViewById(R.id.serve_desc) ;

            convertView.setTag(holder);
        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ServeAdapter.ViewHolder) convertView.getTag();

        }

             holder.tag.setText(map.get("serve_type"));
             if (map.get("serve_type").equals("改签")||map.get("serve_type").equals("退")){
            holder.tag.setBackgroundResource(R.drawable.text_view_serve);
            holder.tag.setTextColor(Color.parseColor("#FF9800"));
            }else {
                 holder.tag.setBackgroundResource(R.drawable.text_view_serve_01);
                 holder.tag.setTextColor(Color.parseColor("#009688"));
             }
            if (map.get("description")!=null){
                holder.desc.setText(map.get("description"));
            }


            return convertView;

        }





    private class ViewHolder {
        TextView tag;
        TextView desc;


    }
}