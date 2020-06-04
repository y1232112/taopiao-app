package com.example.taopiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class OrderSnackAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private ImageLoadingListener animateFirstListener;
    public OrderSnackAdapter(Context context,List<Map<String,String>> datas) {
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

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderSnackAdapter.ViewHolder holder=null;
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_order_snack, parent, false);
            holder = new OrderSnackAdapter.ViewHolder();
            holder.ids=(TextView)convertView.findViewById(R.id.ids) ;
            holder.ic= (ImageView) convertView.findViewById(R.id.os_ic);
            holder.phone=(TextView)convertView.findViewById(R.id.os_phone);
            holder.goodsNum=(TextView)convertView.findViewById(R.id.os_good_num);
            holder.content=(TextView)convertView.findViewById(R.id.os_content);

            convertView.setTag(holder);
        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (OrderSnackAdapter.ViewHolder) convertView.getTag();

        }
        String url= netUtils.server_root_path+map.get("phone_code");

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.avatar)
                .showImageForEmptyUri(R.drawable.avatar)
                .showImageOnFail(R.drawable.avatar)
                .cacheInMemory(true)
                .cacheOnDisc(false)
                .displayer(new RoundedBitmapDisplayer(100))
                .build();
        ImageLoader.getInstance().displayImage(url,holder.ic,options,animateFirstListener);
        String s= DateUtils.getDateToString(Long.parseLong(map.get("order_date")),"yyyy年MM月dd日");
        holder.ids.setText("用户ID/小食id/小食号:"+map.get("user_id")+"/"+map.get("order_id")+"/"+map.get("snack_id"));
        holder.phone.setText("用户号码;"+map.get("order_phone"));
        holder.goodsNum.setText("消费数量；"+map.get("goods_num"));
        holder.content.setText("明细;"+s+"-"+map.get("total_price")+"元"+"-"+map.get("pay_type"));
        return convertView;
    }

    private class ViewHolder{
        ImageView ic;
        TextView ids;
        TextView phone;
        TextView goodsNum;
        TextView content;
    }
}
