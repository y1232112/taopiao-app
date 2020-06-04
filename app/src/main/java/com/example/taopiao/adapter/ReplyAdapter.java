package com.example.taopiao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
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

public class ReplyAdapter extends BaseAdapter {
    private ReplyActivty context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private static final int type=2;
    private static int replyNums=0;
    private static String currentNick;
    private InputMethodManager inputMethodManager;
    private ImageLoadingListener animateFirstListener;

    public ReplyAdapter(ReplyActivty context, List datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.context=context;
        countReplyNums();
    }
    //
    public int getToId(int position){
        Map<String,String> map=mDatas.get(position);

        return Integer.parseInt(map.get("from_uid"));
    }

    public int getTargetId(int position){
        Map<String,String> map=mDatas.get(position);
        if (map.get("to_uid")==null){
            return 0;
        }else return Integer.parseInt(map.get("to_uid"));
    }
    public int Type(){
        return type;
    }
    public String getNick(int position){
        Map<String,String> map=mDatas.get(position);
        return map.get("nick_name");
    }
    public String getTypeInData(int position){
        Map<String,String> map=mDatas.get(position);
        return map.get("reply_type");
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
        return Long.parseLong(map.get("from_uid"));
    }
     private void countReplyNums(){
        int num=0;
        if (mDatas.size()==1){
            this.replyNums= 1;
        }else {
            for (int i=0;i<mDatas.size();i++){
                if (mDatas.get(i).get("reply_type").equals("1")){
                    ++num;
                }
            }
            this.replyNums= num;
        }
     }
     public int getReplyNums(){
        return this.replyNums;
     }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReplyAdapter.ViewHolder holder=null;
        if (mDatas==null){

            return null;
        }else if (mDatas.size()==0){
            return null;
        }

        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.reply_item, parent, false);

            holder = new ReplyAdapter.ViewHolder();
            holder.favatar = (ImageView) convertView.findViewById(R.id.fromu_avatar);
            holder.fnick = (TextView) convertView.findViewById(R.id.fromu_nick);

            holder.fcontent = (TextView) convertView.findViewById(R.id.fromu_content);
            holder.fdate = (TextView) convertView.findViewById(R.id.fromu_date);
            holder.fzan = (TextView) convertView.findViewById(R.id.fromu_zan);
            holder.freply = (TextView) convertView.findViewById(R.id.fromu_reply);
            holder.fimg_zan = (ImageView) convertView.findViewById(R.id.ic_fu_zan);
            holder.fimg_reply = (ImageView) convertView.findViewById(R.id.ic_fu_reply);

            holder.tnick= (TextView) convertView.findViewById(R.id.to_u_nick);
            holder.tcontent = (TextView) convertView.findViewById(R.id.to_u_content);
            holder.layout=(RelativeLayout)convertView.findViewById(R.id.wrap_type_02_reply);
           holder.label=(TextView)convertView.findViewById(R.id.label);

        convertView.setTag(holder);
        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ReplyAdapter.ViewHolder) convertView.getTag();

        }
        holder.fimg_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户id
                  int mId=context.getUserId();
                  if (mId==getToId(position)){
                      Toast.makeText(context,"你不能给自己回复",Toast.LENGTH_LONG).show();
                  }else {
                      context.getKeyBoard(type,getNick(position),getToId(position));
                  }

            }
        });
        if (map.get("my_zan")!=null){
            if (map.get("my_zan").equals("0")){
                holder.fimg_zan.setImageResource(R.drawable.ic_zan);
            }else {
                holder.fimg_zan.setImageResource(R.drawable.ic_red_zan);
            }
        }else {
            holder.fimg_zan.setImageResource(R.drawable.ic_zan);
        }
        holder.fimg_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (map.get("my_zan")!=null){
                    Log.d("攒攒",map.get("my_zan"));


                }
                    JSONObject object = new JSONObject();

                    MyApplication app = MyApplication.getInstance();
                    if (app.user_id != 0) {

                        try {
                            object.put("type", type);
                            object.put("type_id", context.getFilmId());
                            object.put("u_id", app.user_id);

                           if (map.get("my_zan")==null){



                                Log.d("+++我赞为空？","???");
                               object.put("status", 1);
                               object.put("o_id", map.get("id"));


                           }else {
                               int my_zan;
                               my_zan = Integer.parseInt(map.get("my_zan").trim());
                               if (my_zan!=0){
                                   object.put("status", 0);
                                   //评论id
                                   object.put("o_id", map.get("id"));
                               }else {
                                   object.put("status", 1);
                                   //评论id
                                   object.put("o_id", map.get("id"));
                               }


                           }





                            Log.d("______________step________________","----");
                            String json1 = object.toString();
                            Log.d("  object :",json1);
                            RequestBody mrequestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json1);
                            BaseRequest.getInstance().getApiServices().doZan(mrequestBody)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseObserver<Map<String, String>>(context) {
                                        //观察者数据的接收器
                                        static final String TAG = "----点赞-----：";

                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(BaseEntity<Map<String, String>> entity) {
                                            super.onNext(entity);
                                            Log.d("onNext()方法--", entity.getMessage());
                                            context.getReply(context.getCommentId(),context.getUserId());
                                            Toast.makeText(context, entity.getMessage(), Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onSuccess(BaseEntity<Map<String, String>> entity) {
                                            if (entity.isSuccess()) {

                                            } else {

                                            }
                                        }

                                        @Override
                                        public void onCodeError(BaseEntity<Map<String, String>> entity) {

                                            Log.d(TAG + "ErrCode--", Integer.toString(entity.getCode()));
                                            Toast.makeText(context, entity.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onFailure(Throwable e, boolean network) throws Exception {
                                            Log.d("******do zan*****",e.toString());
                                        }
                                    });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else Toast.makeText(context,"你还没有登录",Toast.LENGTH_LONG).show();

            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView nick=(TextView)v.findViewById(R.id.to_u_nick);

                String s12=nick.getText().toString().trim();
                if (nick!=null){


                    Log.d("**********to  id   lay out********",s12+" "+type+" "+getTargetId(position));
                    if (getTargetId(position)==context.getUserId()){
                        Toast.makeText(context,"你不能给自己回复",Toast.LENGTH_LONG).show();
                    }else {
                        context.getKeyBoard(type,s12,getTargetId(position));
                    }

                }





            }
        });
        //设置数据
        String s= DateUtils.getDateToString(Long.parseLong(map.get("reply_date")),"yyyy年MM月dd日");
        holder.fnick.setText(map.get("nick_name"));
        holder.fdate.setText(s);
        holder.fcontent.setText(map.get("content"));
        if (map.get("my_zan")!=null){
            if (map.get("my_zan").equals("1")){
                holder.fimg_zan.setImageResource(R.drawable.ic_red_zan);

            }else {
                holder.fimg_zan.setImageResource(R.drawable.ic_zan);
            }

        }
        if (map.get("zan_nums")!=null){
            holder.fzan.setText(map.get("zan_nums"));
        }
        //设置头像
        if (map.get("avatar")==null){
            holder.favatar.setImageResource(R.drawable.avatar);
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
            ImageLoader.getInstance().displayImage(url,holder.favatar,options,animateFirstListener);
        }
        String to_uid=map.get("to_uid");
        if (map.get("to_uid")!=null){
                holder.layout.setVisibility(View.VISIBLE);
                for (int i=0;i<mDatas.size();i++){
                    Map<String,String> m2=mDatas.get(i);
                    if (m2.get("from_uid").equals(to_uid)){
                        holder.tnick.setText(m2.get("nick_name"));

                        holder.tcontent.setText(m2.get("content"));
                        Log.d("&&&&&&&&&&&&&&&",m2.get("nick_name")+m2.get("content")+m2.get("from_uid"));

                    }

            }

        }else {
            holder.layout.setVisibility(View.GONE);

        }



    return convertView;
    }
    private class ViewHolder{
        ImageView favatar;
        TextView fnick;
        TextView fcontent;
        TextView fdate;
        TextView fzan;
        TextView freply;
        ImageView fimg_zan;
        ImageView fimg_reply;
        TextView tnick;

        TextView tcontent;
        RelativeLayout layout;
        TextView label;
    }
}
