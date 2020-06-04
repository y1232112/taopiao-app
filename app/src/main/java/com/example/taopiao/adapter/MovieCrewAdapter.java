package com.example.taopiao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taopiao.R;
import com.example.taopiao.utils.netUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieCrewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;

    private ImageLoadingListener animateFirstListener;

    public MovieCrewAdapter(Context context, List<Map<String,String>> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;


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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieCrewAdapter.ViewHolder holder = null;
        Map<String, String> map;
        if (mDatas.size()!=0){
        map = mDatas.get(position);
        }else {
            map=new HashMap<>();
        }

        Log.d("adapter：",map.toString());
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_movie_crew, parent, false);
            holder = new ViewHolder();

            holder.img= (ImageView) convertView.findViewById(R.id.detail_movie_crew_img);
            holder.name=(TextView) convertView.findViewById(R.id.detail_movie_crew_name);
            holder.role=(TextView)convertView.findViewById(R.id.detail_role);
            convertView.setTag(holder);

        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (MovieCrewAdapter.ViewHolder) convertView.getTag();

        }

        holder.name.setText(map.get("movie_crew_name"));
        if (map.get("role").equals("")||map.get("role")==null){

        }else {
            holder.role.setText("饰："+map.get("role"));
        }

        String url= netUtils.server_root_path+map.get("img");
        if (url!=null){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_lautcher)
                .showImageForEmptyUri(R.drawable.ic_lautcher)
                .showImageOnFail(R.drawable.ic_lautcher)
                .cacheInMemory(true)
                .cacheOnDisc(false)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();


            ImageLoader.getInstance().displayImage(url,holder.img,options,animateFirstListener);
        }

        return convertView;
    }
    private class ViewHolder{
        ImageView img;
        TextView name;
        TextView role;

    }
}
