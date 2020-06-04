package com.example.taopiao.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.adapter.ReplyAdapter;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
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
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

import static com.example.taopiao.application.MyApplication.app;

public class ReplyActivty extends AppCompatActivity {
    private Context context;
   private int comment_from_uid;
    private int comment_id;
    private int toId;
    private int user_id=0;
   private String mContent="";
   private String c_score ;
   private String c_content;
   private String c_date;
   private String c_nick;
   private String c_avatar;
   private int replyType=1;
   private int film_id=0;
   private ReplyAdapter replyAdapter;
    private ImageLoadingListener animateFirstListener;
    private InputMethodManager inputMethodManager;
    private ListView listView;
    private static int replyNums=0;
    private static int zan_nums=0;
    private static int my_zan=0;

    @BindView(R.id.comment_avatar_01)
    ImageView avatar;
    @BindView(R.id.comment_nick_01)
    TextView nick;
    @BindView(R.id.comment_score_01)
    TextView score;
    @BindView(R.id.comment_content_01)
    TextView content;
    @BindView(R.id.comment_date_01)
    TextView date;
    @BindView(R.id.ic_zan_01)
    ImageView zan;
    @BindView(R.id.ic_reply_01)
    ImageView ic_r;
    @BindView(R.id.my_reply_content)
    EditText mReply;
    @BindView(R.id.cummit_reply)
    Button cummitReply;
    @BindView(R.id.reply_list)
    ListView replyListView;
    @BindView(R.id.comment_zan_01)
    TextView zanShow;
    @BindView(R.id.comment_reply_01)
    TextView replyShow;
    @BindView(R.id._right_warp)
    RelativeLayout topComment;
    @BindView(R.id.bg01)
    ImageView go_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
//     View view= LayoutInflater.from(context).inflate(R.layout.reply_item,null);

        ButterKnife.bind(this);
        inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       inputMethodManager.hideSoftInputFromWindow(mReply.getWindowToken(), 0);
//        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Intent intent=getIntent();
        comment_id= intent.getIntExtra("comment_id",0);
        //初始化数据
        MyApplication app=MyApplication.getInstance();
        user_id=app.user_id;
        intData(comment_id,user_id);

        c_nick=intent.getStringExtra("nick_name");
        c_score=intent.getStringExtra("score");
        c_content=intent.getStringExtra("content");
        c_date=intent.getStringExtra("comment_date");
        c_avatar=intent.getStringExtra("avatar");
        film_id=intent.getIntExtra("film_id",0);
        replyNums=Integer.parseInt(intent.getStringExtra("reply_nums")) ;
        zan_nums= Integer.parseInt(intent.getStringExtra("zan_nums"));
        zanShow.setText(intent.getStringExtra("zan_nums"));
        comment_from_uid=Integer.parseInt(intent.getStringExtra("from_uid"));
        replyShow.setText(intent.getStringExtra("reply_nums"));
        if (intent.getStringExtra("my_zan")!=null){
            my_zan= Integer.parseInt(intent.getStringExtra("my_zan"));
            if (my_zan==1){
                zan.setImageResource(R.drawable.ic_red_zan);
            }
        }

        Log.d(" extratal***************",comment_id+c_nick+c_score+content+c_date);
        nick.setText(c_nick);
        score.setText(showScore(c_score));
        content.setText(c_content);
        String s= DateUtils.getDateToString(Long.parseLong(c_date),"yyyy年MM月dd日");
        date.setText(s);

        if (c_avatar==null){
            avatar.setImageResource(R.drawable.avatar);
        }else {
            String url= netUtils.server_root_path+c_avatar;

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.avatar)
                    .showImageForEmptyUri(R.drawable.avatar)
                    .showImageOnFail(R.drawable.avatar)
                    .cacheInMemory(true)
                    .cacheOnDisc(false)
                    .displayer(new RoundedBitmapDisplayer(100))
                    .build();

            ImageLoader.getInstance().displayImage(url,avatar,options,animateFirstListener);

        }


    }
    @OnClick({R.id.comment_zan_01,R.id.ic_reply_01,R.id.cummit_reply,R.id.ic_zan_01,R.id._right_warp})
    public void OnClick(View view){

        switch (view.getId()){

            case R.id.ic_reply_01:
//                    弹出键盘
                      if (comment_from_uid==user_id){
                          Toast.makeText(ReplyActivty.this,"你不能给自己回复",Toast.LENGTH_LONG).show();
                      }else {
                          mReply.requestFocus();
                          inputMethodManager.showSoftInput(mReply,0);
                          this.toId=0;
                          this.replyType=1;
                          mReply.setHint("写回复");
                      }


                break;
            case  R.id.comment_zan_01:
                if (user_id!=0){

                }else {
                    Toast.makeText(ReplyActivty.this,"你还没有登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cummit_reply:

                       addReply(mReply.getText().toString().trim());
                    break;
            case R.id.ic_zan_01:
              cummitZan();
                 break;
            case R.id._right_warp:
                if (comment_from_uid==user_id){
                    Toast.makeText(ReplyActivty.this,"你不能给自己回复",Toast.LENGTH_LONG).show();
                }else {
                    mReply.requestFocus();
                    inputMethodManager.showSoftInput(mReply,0);
                    this.toId=0;
                    this.replyType=1;
                    mReply.setHint("写回复");
                }
                break;
            case R.id.bg01:
                finish();
                default:
                    break;
        }
    }
    public String showScore(String sc){
        if (sc.equals("0")){
            return "未评分";
        }else {
            return "给作品打了 "+sc+" 分";
        }
    }
    public void intData(Integer id,Integer user_id){
       getReply(id,user_id);

    }
    public void getReply(Integer id,Integer user_id){
        BaseRequest.getInstance().apiServices.getReply(id,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String,String>>>>) new BaseObserver<List<Map<String,String>>>(context) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseEntity<List<Map<String,String>>> entity) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<Map<String,String>>> entity) {
                        super.onNext(entity);
                        Log.d("**********getReply***********",entity.getParams().toString());
                        if (entity.getParams()!=null){
                            if (entity.getParams().size()>0){
                                replyAdapter=new ReplyAdapter(ReplyActivty.this,entity.getParams());
                                Log.d("**********getReply***********", String.valueOf(replyAdapter.getItem(0)));
                                //数据回调给activity

                                replyListView.setAdapter(replyAdapter);
                                //设置点击监听
                                replyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                                        Log.d("******************","item");
                                        if (user_id==replyAdapter.getToId(position)){
                                            Toast.makeText(ReplyActivty.this,"你不能给自己回复",Toast.LENGTH_LONG).show();
                                        }else {
                                            replyType=2;
                                            String n=replyAdapter.getNick(position);
                                            int t_id=replyAdapter.getToId(position);
                                            mReply.setFocusable(true);
                                            mReply.requestFocus();
                                            inputMethodManager.showSoftInput(mReply,0);
                                            getKeyBoard(replyType,n,t_id);
                                        }




                                    }
                                });
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
                    public void onCodeError(BaseEntity<List<Map<String,String>>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }
    public void addReply(String content){
        String s=mReply.getText().toString().trim();
        if (!s.equals("")){

                JSONObject object=new JSONObject();

                try {
                    object.put("comment_id",comment_id);
                    object.put("reply_type",replyType);
                    object.put("content",content);
                    object.put("from_uid",this.user_id);
                    if (replyType==2){
                        object.put("to_uid",toId);
                    }
                    String json=object.toString();
                    RequestBody requestBody=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
                    BaseRequest.getInstance().apiServices.addReply(requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe( new BaseObserver<Map<String,String>>(context){
                                //观察者数据的接收器
                                static final String TAG="--注册--：";
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(BaseEntity<Map<String,String>> entity) {
                                    super.onNext(entity);
                                    Toast.makeText(ReplyActivty.this,"回复成功！",Toast.LENGTH_LONG).show();
                                    getReply(comment_id,user_id);
                                }

                                @Override
                                public void onSuccess(BaseEntity<Map<String,String>> entity) {
                                    if(entity.isSuccess()){

                                    }else {

                                    }
                                }

                                @Override
                                public void onCodeError(BaseEntity<Map<String,String>> entity) {
                                    Toast.makeText(ReplyActivty.this,"回复失败！",Toast.LENGTH_LONG).show();

                                    Log.d(TAG+"ErrCode--",Integer.toString(entity.getCode()));
                                }

                                @Override
                                public void onFailure(Throwable e, boolean network) throws Exception {

                                }
                            });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


        }else {
            Toast.makeText(ReplyActivty.this,"你还没有输入任何内容",Toast.LENGTH_LONG).show();
        }
    }
    public void getKeyBoard(int type,String to_u_nick,int toId){
        //弹出键盘

        Log.d("适配器内部点击事件————-调用activity键盘", String.valueOf(this.replyType)+String.valueOf(toId));
        if (type==2){
            this.toId=toId;
            mReply.requestFocus();
            inputMethodManager.showSoftInput(mReply,0);
            this.replyType=2;
            mReply.setHint("回复:"+to_u_nick);
//           mReply.clearFocus();
        }else if (type==1){

        }

    }
    public void cummitZan() {
        Log.d("****cummit zan*******", comment_id + "filmid:" + film_id);
        JSONObject object = new JSONObject();

        MyApplication app = MyApplication.getInstance();
        if (app.user_id != 0) {


            try {
                object.put("type", 1);
                object.put("type_id", film_id);
                object.put("u_id", app.user_id);
                int o_id;
                if (my_zan == 0) {
                    object.put("status", 1);
                    //评论id
                    object.put("o_id", comment_id);
                } else {

                        object.put("status", 0);
                    object.put("o_id", comment_id);

                }
       Log.d("______________step________________","----");
                String json1 = object.toString();
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
                                Toast.makeText(ReplyActivty.this, entity.getMessage(), Toast.LENGTH_LONG).show();
                                updateRootCommentData();

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

        }else Toast.makeText(ReplyActivty.this,"你还没有登录",Toast.LENGTH_LONG).show();
    }

    public void updateRootCommentData(){
        JSONObject object = new JSONObject();
        try {
            object.put("type", 1);
            object.put("o_id", comment_id);
            object.put("u_id", this.user_id);
            String json = object.toString();
            RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
            BaseRequest.getInstance().getApiServices().selectZandReply(requestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((Observer<BaseEntity<List<Map<String,String>>>>) new BaseObserver<List<Map<String,String>>>(context) {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(BaseEntity<List<Map<String,String>>> entity) {


                        }

                        @Override
                        public void onNext(BaseEntity<List<Map<String,String>>> entity) {
                            super.onNext(entity);
                            Log.d("onSuccess",entity.getParams().toString());
                            List<Map<String,String>> maps=entity.getParams();
                            Map<String,String> map=maps.get(0);
                            replyNums=Integer.parseInt(map.get("reply_nums")) ;
                            zan_nums= Integer.parseInt(map.get("zan_nums"));
                            my_zan= Integer.parseInt(map.get("my_zan"));
                            zanShow.setText(map.get("zan_nums"));
                            Log.d("赞的+++跟新", String.valueOf(zan_nums));
                            if (my_zan==1){
                                zan.setImageResource(R.drawable.ic_red_zan);
                            }
                            if (my_zan==0){
                                zan.setImageResource(R.drawable.ic_zan);
                            }
                            replyShow.setText(map.get("reply_nums"));

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
                        public void onCodeError(BaseEntity<List<Map<String,String>>> entity) {

                        }

                        @Override
                        public void onFailure(Throwable e, boolean network) throws Exception {

                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public int getUserId(){
        return user_id;
    }
    public int getFilmId(){return film_id;}
    public int getCommentId(){return comment_id;}
}
