package com.example.taopiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taopiao.R;
import com.example.taopiao.mvp.activity.SnackBuyActivity;
import com.example.taopiao.utils.netUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;
import java.util.Map;

public class SnackAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private ImageLoadingListener animateFirstListener;
    public SnackAdapter(Context context,List<Map<String, String>> mDatas) {
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
        Map<String,String> map=mDatas.get(position);
        return map;
    }

    @Override
    public long getItemId(int position) {
        Map<String,String> map=mDatas.get(position);
        return Long.parseLong(map.get("snack_id"));
    }

    public int getRealId(int position) {
        Map<String,String> map=mDatas.get(position);
        return Integer.parseInt(map.get("snack_id"));
    }
    public int getId(int position) {
        Map<String,String> map=mDatas.get(position);
        return Integer.parseInt(map.get("id"));
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SnackAdapter.ViewHolder holder=null;
        if (mDatas==null){

            return null;
        }else if (mDatas.size()==0){
            return null;
        }
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_snack,parent,false);
            holder = new SnackAdapter.ViewHolder();
            holder.img=(ImageView)convertView.findViewById(R.id.ic_snack);
            holder.title= (TextView) convertView.findViewById(R.id.snack_title);
            holder.oPrice=(TextView) convertView.findViewById(R.id.snack_origin_price);
            holder.dPrice=(TextView) convertView.findViewById(R.id.snack_discount_price);
            holder.discount=(TextView) convertView.findViewById(R.id.snack_discount);
            holder.container=(RelativeLayout)convertView.findViewById(R.id.container_root_item_snack) ;
            convertView.setTag(holder);

        }else {

            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (SnackAdapter.ViewHolder) convertView.getTag();

        }
        String t;
       if (map.get("item2").equals("")){
           t=map.get("item1");
           holder.title.setText(t);
       }else if (!map.get("item2").equals("")&&map.get("item3").equals("")){
           t=map.get("item1")+"+"+map.get("item2");
           holder.title.setText(t);
       }else if (!map.get("item2").equals("")&&!map.get("item3").equals("")&&map.get("item4").equals("")){
           t=map.get("item1")+"+"+map.get("item2")+"+"+map.get("item3");
           holder.title.setText(t);
       }else if (!map.get("item4").equals("")){
           t=map.get("item1")+"+"+map.get("item2")+"+"+map.get("item3")+"+"+map.get("item4");
           holder.title.setText(t);
       }
       holder.dPrice.setText(map.get("discount_price")+"元");
       holder.discount.setText(map.get("discount"));
       if (!map.get("discount").equals("原价")){
           holder.oPrice.setText(map.get("origin_price")+"元");
           holder.oPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
       }
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
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SnackBuyActivity.class);
                intent.putExtra("id",map.get("id"));
                Log.d("-------id----",map.get("id"));

                context.startActivity(intent);
            }
        });
        return convertView;
    }
    private class ViewHolder{
        ImageView img;
        TextView title;
        TextView oPrice;
        TextView dPrice;
        TextView discount;
       RelativeLayout container;

    }
}
