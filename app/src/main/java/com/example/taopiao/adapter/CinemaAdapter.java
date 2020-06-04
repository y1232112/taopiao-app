package com.example.taopiao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taopiao.R;

import java.util.List;
import java.util.Map;
import java.util.logging.XMLFormatter;

public class CinemaAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        Map<String,String> map=mDatas.get(position);
        return map;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CinemaAdapter(Context context,List<Map<String,String>> datas) {
        this.context = context;
          mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    public int getRealId(int position){
        Map<String,String> map=mDatas.get(position);
        int realId=Integer.parseInt(map.get("cinema_id"));
        return realId;
    }
    public  String getName(int position){
        Map<String,String> map=mDatas.get(position);
        return map.get("cinema_name");
    }
    public  String getAddress(int position){
        Map<String,String> map=mDatas.get(position);
        return map.get("address");
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      CinemaAdapter.ViewHolder holder=null;
        if (mDatas==null){

            return null;
        }else if (mDatas.size()==0){
            return null;
        }
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_cinema,parent,false);
            holder = new CinemaAdapter.ViewHolder();
            holder.title= (TextView) convertView.findViewById(R.id.cinema_title);
            holder.address= (TextView) convertView.findViewById(R.id.cinema_address);
            holder.serve_container=(LinearLayout)convertView.findViewById(R.id.container_cinema_serve);
            convertView.setTag(holder);
             if (map.get("snack")!=null){
                 int sn=Integer.parseInt(map.get("snack"));
                 if (sn>0){
                     addView(holder.serve_container,"小吃");
                 }
             }
            if (map.get("serve_01")!=null){
                addView(holder.serve_container,map.get("serve_01"));
            }
            if (map.get("serve_02")!=null){
                addView(holder.serve_container,map.get("serve_02"));
            }
            if (map.get("serve_03")!=null){
                addView(holder.serve_container,map.get("serve_03"));
            }
            if (map.get("4DX厅")!=null){
                addView(holder.serve_container,map.get("4DX厅"));
            }
            if (map.get("IMAX厅")!=null){
                addView(holder.serve_container,map.get("4DX厅"));
            }
            if (map.get("CGS中国巨幕厅")!=null){
                addView(holder.serve_container,map.get("CGS中国巨幕厅"));
            }
            if (map.get("杜比全景声厅")!=null){
                addView(holder.serve_container,map.get("杜比全景声厅"));
            }

            if (map.get("RealD厅")!=null){
                addView(holder.serve_container,map.get("RealD厅"));
            }
            if (map.get("LUXE巨幕厅")!=null){
                addView(holder.serve_container,map.get("LUXE巨幕厅"));
            }
            if (map.get("4D厅")!=null){
                addView(holder.serve_container,map.get("4D厅"));
            }
            if (map.get("巨幕厅")!=null){
                addView(holder.serve_container,map.get("巨幕厅"));
            }
        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (CinemaAdapter.ViewHolder) convertView.getTag();

        }
        //设置这数据显示
        Log.d("maps+++++++++++++++",map.toString()+map.get("cinema_name"));
        holder.title.setText(map.get("cinema_name"));
        holder.address.setText(map.get("county")+map.get("address"));


        return convertView;
    }
    private void addView( LinearLayout container,String string){
        //动态创建view
       TextView textView=new TextView(context);
       if (string.equals("改签")||string.equals("退")||string.equals("3D眼镜")||string.equals("小吃")){
           textView.setBackgroundResource(R.drawable.text_view_serve);
           textView.setTextColor(Color.parseColor("#FF9800"));
           //
           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                   LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           params.setMargins(5, 5, 5, 5);
           textView.setLayoutParams(params);
           textView.setPadding(5,5,5,5);

       }else {
           textView.setTextColor(Color.parseColor("#009688"));
           textView.setBackgroundResource(R.drawable.text_view_serve_01);
       }
       textView.setText(string);
       textView.setTextSize(9);
       container.addView(textView);
    }
    private class ViewHolder{
        TextView title;
        TextView address;
        LinearLayout serve_container;

    }
}
