package com.example.taopiao.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.activity.CommentActivity;
import com.example.taopiao.mvp.activity.FilmDetailActivity;
import com.example.taopiao.mvp.activity.ReplyActivty;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.utils.DateUtils;
import com.example.taopiao.utils.netUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private int id;
    private ImageLoadingListener animateFirstListener;

    public CommentAdapter(FilmDetailActivity context, List datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    this.context=context;
      id=context.getFilmId();
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

        return Long.parseLong(map.get("id"));
    }
    public int getRealId(int position){
        Map<String,String> map=mDatas.get(position);
        return Integer.parseInt(map.get("id"));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      CommentAdapter.ViewHolder holder=null;
      if (mDatas==null){

          return null;
      }else if (mDatas.size()==0){
          return null;
      }
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_comment_list,parent,false);

            holder = new CommentAdapter.ViewHolder();
            holder.avatar=(ImageView)convertView.findViewById(R.id.comment_avatar);
            holder.nick=(TextView)convertView.findViewById(R.id.comment_nick);
            holder.score=(TextView)convertView.findViewById(R.id.comment_score);
            holder.content =(TextView)convertView.findViewById(R.id.comment_content);
            holder.date=(TextView)convertView.findViewById(R.id.comment_date);
            holder.zan=(TextView)convertView.findViewById(R.id.comment_zan);
            holder.reply=(TextView)convertView.findViewById(R.id.comment_reply);
            holder.img_zan=(ImageView)convertView.findViewById(R.id.ic_zan);
            holder.img_reply=(ImageView)convertView.findViewById(R.id.ic_reply);
            holder.img_more_option=(ImageView)convertView.findViewById(R.id.more_option);

            holder.img_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("**********commentAdarpter******","你点击了："+getItemId(position));
                    JSONObject object=new JSONObject();

                    MyApplication app=MyApplication.getInstance();

                    try {
                        object.put("type", 1);
                        object.put("type_id", id);
                        object.put("u_id", app.user_id);
                        int o_id;
                        if (map.get("my_zan")==null){
                            object.put("status", 1);
                            //评论id
                            object.put("o_id",map.get("id"));

                            Log.d("step1","________"+map.get("id"));
                        }else {

                            if (map.get("my_zan").equals("0")){
                                object.put("status", 1);
                            }else {
                                object.put("status", 0);
                            }

                            object.put("o_id",map.get("id"));
                            Log.d("step2","________"+map.get("my_zan"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                        String json = object.toString();
                        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                    BaseRequest.getInstance().getApiServices().doZan(requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe( new BaseObserver<Map<String,String>>(context){
                                //观察者数据的接收器
                                static final String TAG="----点赞-----：";
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(BaseEntity<Map<String,String>> entity) {
                                    super.onNext(entity);
                                    Log.d("onNext()方法--",entity.getMessage());
                                    Toast.makeText(context,entity.getMessage(),Toast.LENGTH_LONG).show();
                                  FilmDetailActivity.filmDetailActivity.initCommentInfo();
                                }

                                @Override
                                public void onSuccess(BaseEntity<Map<String,String>> entity) {
                                    if(entity.isSuccess()){

                                    }else {

                                    }
                                }

                                @Override
                                public void onCodeError(BaseEntity<Map<String,String>> entity) {

                                    Log.d(TAG+"ErrCode--",Integer.toString(entity.getCode()));
                                    Toast.makeText(context,entity.getMessage(),Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Throwable e, boolean network) throws Exception {

                                }
                            });
                }
            });
            holder.img_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
//                             适配器中跳转activity
                             Intent intent=new Intent(context, ReplyActivty.class);
                            Map<String,String> m= (Map<String, String>) getItem(position);
                            Log.d("测试————————",m.get("nick_name"));
                             intent.putExtra("comment_id",getRealId(position));
                            intent.putExtra("nick_name",m.get("nick_name"));
                            intent.putExtra("score",m.get("score"));
                            intent.putExtra("content",m.get("content"));
                            intent.putExtra("comment_date",m.get("comment_date"));
                             intent.putExtra("avatar",m.get("avatar"));
                             intent.putExtra("zan_nums",m.get("zan_nums"));
                             intent.putExtra("reply_nums",m.get("reply_nums"));
                             intent.putExtra("my_zan",m.get("my_zan"));
                             intent.putExtra("from_uid",m.get("from_uid"));
                             context.startActivity(intent);
                         }
                     },200);
                }
            });
            convertView.setTag(holder);
        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();

        }
//        设置数据
         String s1= map.get("avatar");
        if (s1==null){

        }

        holder.nick.setText(map.get("nick_name"));
        holder.score.setText(showScore(map.get("score")));
        holder.content.setText(map.get("content"));
        holder.zan.setText(map.get("zan_nums"));
        holder.reply.setText(map.get("reply_nums"));
        MyApplication app=MyApplication.getInstance();

       if (map.get("from_uid").equals(String.valueOf(app.user_id))){
           holder.img_more_option.setVisibility(View.VISIBLE);
           holder.img_more_option.setImageResource(R.drawable.ic_more_option);
           holder.img_more_option.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                 FilmDetailActivity.filmDetailActivity.showNormalDialog();
               }
           });
       }
        if (map.get("my_zan")!=null){
            if (map.get("my_zan").trim().equals("1")){

                holder.img_zan.setImageResource(R.drawable.ic_red_zan);
            }
        }


        if (map.get("avatar")==null){
            holder.avatar.setImageResource(R.drawable.avatar);
        }else {
            String url= netUtils.server_root_path+map.get("avatar");

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.avatar)
                    .showImageForEmptyUri(R.drawable.avatar)
                    .showImageOnFail(R.drawable.avatar)
                    .cacheInMemory(true)
                    .cacheOnDisc(false)
                    .displayer(new RoundedBitmapDisplayer(100))
                    .build();
            ImageLoader.getInstance().displayImage(url,holder.avatar,options,animateFirstListener);

        }
        String s= DateUtils.getDateToString(Long.parseLong(map.get("comment_date")),"yyyy年MM月dd日");
        holder.date.setText(s);
//        holder.zan.setText(map.get(""));
//        holder.reply.setText(map.get(""));
        return convertView;
    }
    public String showScore(String sc){
        if (sc.equals("0")){
            return "未评分";
        }else {
            return "给作品打了 "+sc+" 分";
        }
    }
    private class ViewHolder{
        ImageView avatar;
        TextView nick;
        TextView score;
        TextView content;
        TextView date;
        TextView zan;
        TextView reply;
        ImageView img_zan;
        ImageView img_reply;
       ImageView  img_more_option;
    }
}
