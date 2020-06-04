package com.example.taopiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taopiao.R;
import com.example.taopiao.mvp.activity.SelectSeatsActivity;
import com.example.taopiao.utils.DateUtils;

import java.util.List;
import java.util.Map;

public class ScheduleAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    public ScheduleAdapter(Context context, List datas) {
//        mInflater = LayoutInflater.from(context);
        mInflater = LayoutInflater.from(context);
        this.context=context;
        mDatas = datas;

    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {

        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScheduleAdapter.ViewHolder holder = null;
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_schedule,parent,false);
            holder = new ScheduleAdapter.ViewHolder();
            holder.s_date=(TextView) convertView.findViewById(R.id.s_date);
            holder.s_tart=(TextView)convertView.findViewById(R.id.s_start);
            holder.s_no=(TextView)convertView.findViewById(R.id.s_no);
            holder.h_name=(TextView)convertView.findViewById(R.id.h_name);
            holder.language=(TextView)convertView.findViewById(R.id.s_language);
            holder.dis_price=(TextView)convertView.findViewById(R.id.dis_price);
            holder.dis_info=(TextView)convertView.findViewById(R.id.dis_info);
            holder.o_price=(TextView) convertView.findViewById((R.id.o_price)) ;
            holder.schedule_container=(RelativeLayout)convertView.findViewById(R.id.schedule_container);

            convertView.setTag(holder);
        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ScheduleAdapter.ViewHolder) convertView.getTag();

        }
//        String s= DateUtils.getDateToString(Long.parseLong(map.get("show_date")),"yyyy年MM月dd日");
            holder.s_date.setText(map.get("show_date"));
            holder.s_tart.setText(map.get("start_time"));
            holder.s_no.setText(map.get("hall_id"));
            holder.h_name.setText(map.get("hall_name"));
            holder.language.setText(map.get("language"));
            holder.dis_info.setText(map.get("discount"));

            if (!map.get("discount").equals("原价")){
                holder.dis_price.setText(map.get("discount_price")+"元/");
                holder.o_price.setText(map.get("origin_price")+"元");
                holder.o_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }else {
                holder.o_price.setText(map.get("origin_price")+"元");
            }
          holder.schedule_container.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(context,SelectSeatsActivity.class);
                  intent.putExtra("schedule_id",map.get("schedule_id"));
                  intent.putExtra("hall_name",map.get("hall_name"));
                  intent.putExtra("hall_id",map.get("hall_id"));
                  intent.putExtra("film_id",map.get("film_id"));
                  intent.putExtra("cinema_id",map.get("cinema_id"));
                  intent.putExtra("discount",map.get("discount"));
                  intent.putExtra("origin_price",map.get("origin_price"));
                  intent.putExtra("discount_price",map.get("discount_price"));

                   context.startActivity(intent);
              }
          });
        return convertView;
    }
    private class ViewHolder {

        TextView s_date;
        TextView s_tart;
        TextView s_no;
        TextView h_name;
        TextView language;
        TextView dis_info;
        TextView dis_price;
        TextView o_price;
        RelativeLayout schedule_container;

    }
}
