package com.example.taopiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taopiao.MainActivity;
import com.example.taopiao.R;
import com.example.taopiao.mvp.activity.BuyAboutActivity;
import com.example.taopiao.mvp.activity.SelectCinemaActivity;
import com.example.taopiao.mvp.fragment.CinemaFragment;
import com.example.taopiao.utils.DateUtils;
import com.example.taopiao.utils.netUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;
import java.util.Map;

public class HotFilmAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private ImageLoadingListener animateFirstListener;
    public HotFilmAdapter(Context context, List datas) {
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
        Map<String,String> map=mDatas.get(position);
        return Long.parseLong(map.get("film_id"));
    }

    public int getRealId(int position) {
        Map<String,String> map=mDatas.get(position);
        return Integer.parseInt(map.get("film_id"));
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotFilmAdapter.ViewHolder holder = null;
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_hot,parent,false);
            holder = new HotFilmAdapter.ViewHolder();
            holder.img=(ImageView)convertView.findViewById(R.id.tab_hot_film_img);
            holder.title=(TextView)convertView.findViewById(R.id.tab_hot_film_title);
            holder.wish_num=(TextView)convertView.findViewById(R.id.tab_hot_film_wish);
            holder.type=(TextView)convertView.findViewById(R.id.tab_hot_film_type);
            holder.actor=(TextView)convertView.findViewById(R.id.tab_hot_film_actor);
            holder.public_date=(TextView)convertView.findViewById(R.id.tab_hot_film_public_date);
            holder.button=(Button)convertView.findViewById((R.id.list_item_2_btn)) ;
            convertView.setTag(holder);
        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (HotFilmAdapter.ViewHolder) convertView.getTag();

        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BuyAboutActivity.class);
                intent.putExtra("film_id",map.get("film_id"));
                context.startActivity(intent);
            }
        });
        String s= DateUtils.getDateToString(Long.parseLong(map.get("public_date")),"yyyy年MM月dd日");

        holder.title.setText(map.get("film_name"));
        holder.wish_num.setText(map.get("wish_nums"));
        holder.type.setText(map.get("type"));
        holder.actor.setText(map.get("actor"));
        holder.public_date.setText(s);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_lautcher)
                .showImageForEmptyUri(R.drawable.ic_lautcher)
                .showImageOnFail(R.drawable.ic_lautcher)
                .cacheInMemory(true)
                .cacheOnDisc(false)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
        String url= netUtils.server_root_path+map.get("img");
        ImageLoader.getInstance().displayImage(url,holder.img,options,animateFirstListener);
        return convertView;
    }
    private class ViewHolder {
        ImageView img;
        TextView title;
        TextView wish_num;
        TextView type;
        TextView actor;
        TextView public_date;
        Button button;
    }
}
