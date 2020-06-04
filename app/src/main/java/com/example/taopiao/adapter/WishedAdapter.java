package com.example.taopiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taopiao.R;
import com.example.taopiao.utils.DateUtils;
import com.example.taopiao.utils.netUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;
import java.util.Map;

public class WishedAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private ImageLoadingListener animateFirstListener;
    public WishedAdapter(Context context,List<Map<String, String>> mDatas) {
        this.context = context;
        this.mInflater =LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WishedAdapter.ViewHolder holder=null;
        if (mDatas==null){

            return null;
        }else if (mDatas.size()==0){
            return null;
        }
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_wished,parent,false);
            holder = new WishedAdapter.ViewHolder();
            holder.img=(ImageView)convertView.findViewById(R.id.w_ic);
            holder.date=(TextView)convertView.findViewById(R.id.w_date) ;
            holder.title=(TextView)convertView.findViewById(R.id.w_title) ;
            holder.type=(TextView)convertView.findViewById(R.id.w_type) ;
            holder.score=(TextView)convertView.findViewById(R.id.w_avgscore) ;
            holder.num=(TextView)convertView.findViewById(R.id.w_nums) ;
            convertView.setTag(holder);

        }else {

            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (WishedAdapter.ViewHolder) convertView.getTag();

        }
        String s= DateUtils.getDateToString(Long.parseLong(map.get("wish_time")),"yyyy年MM月dd日");
        String url= netUtils.server_root_path+map.get("img");
        if (map.get("img")!=null&&!map.get("img").equals("")) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.avatar)
                    .showImageForEmptyUri(R.drawable.avatar)
                    .showImageOnFail(R.drawable.avatar)
                    .cacheInMemory(true)
                    .cacheOnDisc(false)
                    .displayer(new RoundedBitmapDisplayer(10))
                    .build();
            ImageLoader.getInstance().displayImage(url, holder.img, options, animateFirstListener);
        }
        holder.date.setText("记录时间："+s);
        holder.title.setText(map.get("film_name"));
        holder.type.setText(map.get("type"));
        holder.num.setText(map.get("wish_num")+"人想看");
        if (map.get("avgscore")!=null){
            holder.score.setText("综合评分："+map.get("avgscore"));
        }else {
            holder.score.setText("综合评分："+"还没有用户评分");
        }

        return convertView;

    }
    private class ViewHolder{
        ImageView img;
        TextView title;
        TextView date;
        TextView type;
        TextView score;
        TextView num;


    }
}
