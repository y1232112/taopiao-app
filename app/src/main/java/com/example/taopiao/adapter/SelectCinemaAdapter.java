package com.example.taopiao.adapter;



import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taopiao.R;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.activity.FilmDetailActivity;
import com.example.taopiao.mvp.activity.SelectCinemaActivity;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.widget.MyHorizontalScrollView2;

import java.util.List;
import java.util.Map;
import java.util.logging.XMLFormatter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectCinemaAdapter extends BaseAdapter {
    private SelectCinemaActivity context;
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

    public SelectCinemaAdapter(SelectCinemaActivity context,List<Map<String,String>> datas) {
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
       SelectCinemaAdapter.ViewHolder holder=null;
        if (mDatas==null){

            return null;
        }else if (mDatas.size()==0){
            return null;
        }
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_cinema2,parent,false);

            holder = new SelectCinemaAdapter.ViewHolder();
            holder.listSchedule=(MyHorizontalScrollView2) convertView.findViewById(R.id.schedule_h_scroll);
            holder.title= (TextView) convertView.findViewById(R.id.cinema_title);
            holder.address= (TextView) convertView.findViewById(R.id.cinema_address);
            holder.serve_container=(LinearLayout)convertView.findViewById(R.id.container_cinema_serve);

            holder.show_tips_tasks=(TextView)convertView.findViewById(R.id.show_tips_task);
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
            holder = (SelectCinemaAdapter.ViewHolder) convertView.getTag();

        }
        //设置这数据显示
        Log.d("maps+++++++++++++++",map.toString()+map.get("cinema_name"));
        holder.title.setText(map.get("cinema_name"));
        holder.address.setText(map.get("county")+map.get("address"));
        String s1=map.get("cinema_id");
        holder.cinema_id=Integer.parseInt(s1);
        holder.film_id=context.getFilm_id();
        Integer cinema_id=Integer.parseInt(s1);
        Integer film_id=context.getFilm_id();
        holder.setSchedule();



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
    public class ViewHolder{
        TextView title;
        TextView address;
        LinearLayout serve_container;
        LinearLayout schedule_container;
        TextView show_tips_tasks;
        MyHorizontalScrollView2 listSchedule;
        Integer cinema_id;
        Integer film_id;
        private ScheduleAdapter scheduleAdapter;
        void setSchedule(){
            BaseRequest.getInstance().apiServices.aFilmSchedule(cinema_id,film_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((Observer<BaseEntity<List<Map<String, String>>>>) new BaseObserver<List<Map<String, String>>>(context) {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(BaseEntity<List<Map<String, String>>> entity) {

                        }

                        @Override
                        public void onNext(BaseEntity<List<Map<String, String>>> entity) {
                            super.onNext(entity);
                            Log.d("onNext", entity.getParams().toString());
                            if (entity.getParams()!=null){
                                if (entity.getParams().size()>0){
                                    List<Map<String,String>> maps=entity.getParams();
                                    scheduleAdapter=new ScheduleAdapter(context,maps);
                                    listSchedule.initDatas(scheduleAdapter);

                                }

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
                        public void onCodeError(BaseEntity<List<Map<String, String>>> entity) {

                        }

                        @Override
                        public void onFailure(Throwable e, boolean network) throws Exception {
                            Log.d("++++++++++++",e.toString());
                        }
                    });
        }
//        //这行异步任务获取影院关于这部影片的排片信息
//        private class receiveScheduleTasks extends AsyncTask<Integer[],Void,List<Map<String,String>>>{
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//               show_tips_tasks.setText("下载中.....");
//            }
//
//            @Override
//            protected List<Map<String, String>> doInBackground(Integer[]... integers) {
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(List<Map<String, String>> maps) {
//                super.onPostExecute(maps);
//                show_tips_tasks.setText("....下载完成");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        show_tips_tasks.setText("");
//                    }
//                },200);
//            }
//        }
    }

}
