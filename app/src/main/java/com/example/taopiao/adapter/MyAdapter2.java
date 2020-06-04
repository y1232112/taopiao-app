package com.example.taopiao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taopiao.MainActivity;
import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.fragment.CinemaFragment;
import com.example.taopiao.mvp.fragment.HomeFragment;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.utils.DateUtils;
import com.example.taopiao.utils.netUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class MyAdapter2 extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context context;
    private List<Map<String,String>> mDatas;
    private HomeFragment homeFragment;
    private ImageLoadingListener animateFirstListener;

    public MyAdapter2(Context context, List datas, HomeFragment homeFragment) {
//        mInflater = LayoutInflater.from(context);
        mInflater = LayoutInflater.from(context);
        this.context=context;
        mDatas = datas;
        this.homeFragment=homeFragment;
    }
    public int getRealId(int position){
        Map<String,String> map=mDatas.get(position);
        return Integer.parseInt(map.get("film_id"));
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
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyAdapter2.ViewHolder holder = null;
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_1,parent,false);
            holder = new MyAdapter2.ViewHolder();
            holder.img=(ImageView)convertView.findViewById(R.id.tab_will_film_img_1);
            holder.title=(TextView)convertView.findViewById(R.id.tab_will_film_title);
            holder.wish_num=(TextView)convertView.findViewById(R.id.tab_will_film_wish);
            holder.type=(TextView)convertView.findViewById(R.id.tab_will_film_type);
            holder.actor=(TextView)convertView.findViewById(R.id.tab_will_film_actor);
            holder.public_date=(TextView)convertView.findViewById(R.id.tab_will_film_public_date);
            holder.button=(Button)convertView.findViewById((R.id.list_item_1_btn)) ;
            holder.button.setTag(map.get("film_id"));

            convertView.setTag(holder);
        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (MyAdapter2.ViewHolder) convertView.getTag();

        }
        String s= DateUtils.getDateToString(Long.parseLong(map.get("public_date")),"yyyy年MM月dd日");

        holder.title.setText(map.get("film_name"));
        holder.wish_num.setText(map.get("wish_nums"));
        holder.type.setText(map.get("type"));
        holder.actor.setText(map.get("actor"));
        holder.public_date.setText(s);
        if (map.get("wish_status")==null){
            holder.button.setText("想看");
            holder.button.setBackgroundResource(R.drawable.wish_button_02);
            holder.button.setTextColor(Color.WHITE);
        }else {
            if (map.get("wish_status").equals("0")){
                holder.button.setText("想看");
                holder.button.setBackgroundResource(R.drawable.wish_button_02);
                holder.button.setTextColor(Color.WHITE);
            }else {
                holder.button.setText("已想看");
                holder.button.setBackgroundResource(R.drawable.wish_button_01);
                holder.button.setTextColor(Color.BLACK);
            }
        }

//        处理想看功能
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                map2=getItem(position);
                Log.d("listview 内 adapter 内 的 button",String.valueOf(getRealId(position)));
                Button button1=v.findViewById(R.id.list_item_1_btn);
                Object data= button1.getTag();
//               Log.d("按钮在列表中的位置：",data.toString());
                String s1=button1.getText().toString().trim();
                Log.d("---",s1+"film_id"+map.get("film_id"));
//                封装请求体
                Map<String,String> messageMap=new HashMap<>();
                MyApplication app=MyApplication.getInstance();
                int u_id=app.user_id;

//        创建JSONObject对象，传入map参数
                JSONObject jsonObject=new JSONObject(map);
//       将JSONObject对象转换为json
                Log.d("你的iD是：", String.valueOf(u_id));
//       将json字符串转换为————请求体——————RequestBody
                try {
                    jsonObject.put("user_id",u_id);
//                    jsonObject.put("film_id",getRealId(position));
                    jsonObject.put("film_id",map.get("film_id"));
                    if (mDatas.get(position).get("wish_status")!=null){
                        jsonObject.put("wish_status",mDatas.get(position).get("wish_status"));
                        Log.d("*************",mDatas.get(position).get("wish_status"));
                    }else {
                        jsonObject.put("wish_status",0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json=jsonObject.toString();
//                封装请求体
                RequestBody requestBody=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);

                Log.d("---",s1);
                BaseRequest.getInstance().apiServices.postDoWish(requestBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<Map<String, String>>(context){

                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(BaseEntity<Map<String, String>> entity) {
                                super.onNext(entity);
                                Log.d("想看：-----",entity.getMessage());
                                if (entity.getMessage().equals("已想看")){
                                    Toast.makeText(MainActivity.mainActivity,"你已想看",Toast.LENGTH_SHORT).show();
                                    //更新数据

                                    homeFragment.upUi();
                                }else if(entity.getMessage().equals("已取消")){
                                    Toast.makeText(MainActivity.mainActivity,"你已取消",Toast.LENGTH_SHORT).show();
                                    //更新数据
                                    homeFragment.upUi();
                                }else {
                                    Toast.makeText(MainActivity.mainActivity,"操作失败请再次尝试",Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onSuccess(BaseEntity<Map<String, String>> entity) {


                            }

                            @Override
                            public void onCodeError(BaseEntity<Map<String, String>> entity) {

                            }

                            @Override
                            public void onFailure(Throwable e, boolean network) throws Exception {
                                Log.d("想看：---e--",e.toString());

                            }

                        });





            }
        });
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
