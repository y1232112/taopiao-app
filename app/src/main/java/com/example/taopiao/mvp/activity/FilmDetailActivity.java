package com.example.taopiao.mvp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

import com.example.taopiao.R;
import com.example.taopiao.adapter.CommentAdapter;
import com.example.taopiao.adapter.MovieCrewAdapter;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.FilmDetailContract;
import com.example.taopiao.mvp.presenter.FilmDetailPresenter;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.utils.DateUtils;
import com.example.taopiao.utils.JsonUtils;
import com.example.taopiao.utils.netUtils;
import com.example.taopiao.widget.MyHorizontalScrollView;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.uber.autodispose.AutoDisposeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class FilmDetailActivity extends AppCompatActivity implements FilmDetailContract.view {
    private FilmDetailPresenter presenter;
    private int click_count=1;

    private MovieCrewAdapter movieCrewAdarpter;
    private int film_id;
    private  int user_id;
    private  int wishTag=0;
    private  int lookedTag=0;
   private static boolean isComent=false;
   private boolean isUp=false;
   private CommentAdapter commentAdapter;
   public static FilmDetailActivity filmDetailActivity;
    @BindView(R.id.movie_crew_h_scroll)
    MyHorizontalScrollView horizontalScrollView;
    @BindView(R.id.detail_film_title)
    TextView title;
    @BindView(R.id.detail_film_type)
    TextView type;
    @BindView(R.id.detail_film_actor)
    TextView actor;
    @BindView(R.id.detail_film_date)
    TextView date;
    @BindView(R.id.detail_film_other)
    TextView other;
    @BindView(R.id.film_detail_img)
    ImageView img;
    @BindView(R.id.detail_film_wish)
    TextView wish;
    @BindView(R.id.desc_title)
    TextView desc_title;
    @BindView(R.id.desc_expand)
    TextView desc_expand;
    @BindView(R.id.desc_content)
    TextView desc_content;
    @BindView(R.id.root_film_detail)
    LinearLayout r;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.wrap_wish)
    RelativeLayout r_wish;
    @BindView(R.id.movie_crew_L_out)
    LinearLayout movie_crew_out;
    @BindView(R.id.to_coment)
    TextView to_comment;
    @BindView(R.id.ic_ex)
    ImageView ex_ic;
    @BindView(R.id.ic_gone)
    ImageView gn_ic;
    @BindView(R.id.comment_list_view)
    ListView comment_list;
    @BindView(R.id.looked_btn)
    Button looked_btn;
    @BindView(R.id.wish_btn)
    Button wish_btn;
    private ImageLoadingListener animateFirstListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
       filmDetailActivity=this;
        ButterKnife.bind(this);
        MyApplication app=MyApplication.getInstance();
        user_id=app.user_id;

        Intent intent=getIntent();
         film_id=intent.getIntExtra("film_id",0);
        Log.d("传过来的错影片ID：", String.valueOf(film_id));
//        请求影片信息
//        初始化presenter
        presenter=new FilmDetailPresenter(this,this);
//        代理请求
        presenter.getFilm(film_id);
        presenter.getRoles(film_id);
        presenter.getUserFilm(film_id,user_id);
//初始化数据
      initCommentInfo();
    }
       public void initCommentInfo(){
           JSONObject object=new JSONObject();
           try {
               object.put("t_id",film_id);
               object.put("type",1);
               object.put("user_id",user_id);
           } catch (JSONException e) {
               e.printStackTrace();
           }

           presenter.getComment(object.toString());
    }
     public int getFilmId(){
        return film_id;
     }
    @Override
    public void setFilm(List<Map<String, String>> maps) {

       Map<String,String> map=maps.get(0);
        String url= netUtils.server_root_path+map.get("img");
       String s1=map.get("film_name");
       String s2=map.get("type");
       String s3=map.get("actor");
       String s4= DateUtils.getDateToString(Long.parseLong(map.get("public_date")),"yyyy年MM月dd日");
        String s5=map.get("product_area");
       String s6=map.get("film_length");
       String s7=map.get("wish_nums");
//       Log.d("想看人数：",s7);
       String s8=map.get("brief");
       title.setText(s1);
       type.setText(s2);
       actor.setText(s3);
       date.setText(s4);
       other.setText(s5+"/"+s6);
       wish.setText(s7);
       desc_content.setText(s8);
       if (map.get("img")!=null&&!map.get("img").equals("")){
           DisplayImageOptions options = new DisplayImageOptions.Builder()
                   .showStubImage(R.drawable.ic_lautcher)
                   .showImageForEmptyUri(R.drawable.ic_lautcher)
                   .showImageOnFail(R.drawable.ic_lautcher)
                   .cacheInMemory(true)
                   .cacheOnDisc(false)
                   .displayer(new RoundedBitmapDisplayer(20))
                   .build();
           ImageLoader.getInstance().displayImage(url,img,options,animateFirstListener);

       }
        getPalette(returnBitMap(url));


    }


    private Bitmap getBitmap(ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }
    private void getPalette(Bitmap bitmap) {
        if (bitmap != null) {
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(@Nullable Palette palette) {
                    if (palette != null) {
                        Palette.Swatch mutedDark = palette.getDarkMutedSwatch();;//柔和的暗色


                        if (mutedDark != null) {
                            int v=mutedDark.getRgb();

//                            r.setBackgroundColor(v);
                            r.setBackgroundColor(setColorShallow(mutedDark.getRgb(), 0.3f));
                            double v1 = ColorUtils.calculateLuminance(v);
                            looked_btn.setBackgroundColor(setColorShallow(mutedDark.getRgb(), 0.5f));
                            wish_btn.setBackgroundColor(setColorShallow(mutedDark.getRgb(), 0.6f));
                            if (v1>=0.5){
                                //亮色
                                title.setTextColor(Color.parseColor("#FFFFFF"));
                                type.setTextColor(Color.parseColor("#FFFFFF"));
                                actor.setTextColor(Color.parseColor("#FFFFFF"));
                                date.setTextColor(Color.parseColor("#FFFFFF"));
                               other.setTextColor(Color.parseColor("#FFFFFF"));
                                wish.setTextColor(Color.parseColor("#FFFFFF"));
                                wish.setBackgroundColor(Color.parseColor("#DCDCDC"));
                                wish.setBackgroundColor(v);
                                desc_content.setBackgroundColor(v);
                            }else {
                                //暗色
                                title.setTextColor(Color.parseColor("#FFFFFF"));
                                type.setTextColor(Color.parseColor("#FFFFFF"));
                                actor.setTextColor(Color.parseColor("#FFFFFF"));
                                date.setTextColor(Color.parseColor("#FFFFFF"));
                                other.setTextColor(Color.parseColor("#FFFFFF"));
                                wish.setTextColor(Color.parseColor("#FFFFFF"));
                                r_wish.setBackgroundColor(v);
                                desc_content.setBackgroundColor(v);
                            }
                        }else {
                            r.setBackgroundColor(Color.DKGRAY);
                            title.setTextColor(Color.parseColor("#FFFFFF"));
                            type.setTextColor(Color.parseColor("#FFFFFF"));
                            actor.setTextColor(Color.parseColor("#FFFFFF"));
                            date.setTextColor(Color.parseColor("#FFFFFF"));
                            other.setTextColor(Color.parseColor("#FFFFFF"));
                            wish.setTextColor(Color.parseColor("#FFFFFF"));
                            r_wish.setBackgroundColor(Color.parseColor("#363636"));
                            desc_content.setBackgroundColor(Color.parseColor("#363636"));
                        }
                    }
                }
            });
        }
    }
    /**
     * 颜色加深算法
     */
    private int setColorBurn(int rgb, float val) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        r = (int) Math.floor(r * (1f - val));
        g = (int) Math.floor(g * (1f - val));
        b = (int) Math.floor(b * (1f - val));

        return Color.rgb(r, g, b);
    }

    /**
     * 颜色浅化算法
     */
    private int setColorShallow(int rgb, float val) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        r = (int) Math.floor(r * (1f + val));
        g = (int) Math.floor(g * (1f + val));
        b = (int) Math.floor(b * (1f + val));
        return Color.rgb(r, g, b);
    }
   @OnClick({R.id.desc_expand,R.id.to_coment,R.id.looked_btn,R.id.wish_btn})
   public void OnClick(View view) {
        switch (view.getId()){

            case R.id.desc_expand:



           String ex=desc_expand.getText().toString();
           if (ex.equals("展开")){
               desc_content.setEllipsize(null);
               desc_content.setSingleLine(false);
               desc_expand.setText("收起");
               gn_ic.setVisibility(View.VISIBLE);
               ex_ic.setVisibility(View.GONE);


           }else if(ex.equals("收起")){

               desc_content.setLines(3);
               desc_content.setEllipsize(TextUtils.TruncateAt.END);
               desc_expand.setText("展开");
               gn_ic.setVisibility(View.GONE);
               ex_ic.setVisibility(View.VISIBLE);
           }

             break;
            case R.id.to_coment:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(FilmDetailActivity.this,CommentActivity.class);
                        intent.putExtra("film_id",film_id);
                        startActivity(intent);
                    }
                },100);
                     break;
            case R.id.wish_btn:
                doWish();
                break;
            case R.id.looked_btn:
                Log.d("_______looked______", String.valueOf(lookedTag));

                 if (lookedTag==1){
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Intent intent=new Intent(FilmDetailActivity.this,CommentActivity.class);
                             intent.putExtra("film_id",film_id);
                             startActivity(intent);
                         }
                     },100);
                 }else if(lookedTag==0) {
                     JSONObject ob1=new JSONObject();
                     try {
                         ob1.put("film_id",film_id);
                         ob1.put("user_id",user_id);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }

                     RequestBody requestBody=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),ob1.toString());
                     presenter.doLooked(requestBody);
                 }
                break;

        }
   }

    @Override
    public void setRoles(List<Map<String, String>> maps) {
        if (maps.size()!=0){
            movieCrewAdarpter=new MovieCrewAdapter(FilmDetailActivity.this,maps);
            Log.d("maps++++++++++++:",maps.toString());
            horizontalScrollView.initDatas(movieCrewAdarpter);
            Log.d("maps++++++++++++:", String.valueOf(movieCrewAdarpter.getCount()));
        }


    }

    @Override
    public void setComment(List<Map<String, String>> maps) {
          Log.d("+++++++++maps:",maps.toString());
        Log.d("******************","0");
              if (maps==null){
                  isComent=false;
                  to_comment.setTextColor(Color.WHITE);
                  to_comment.setBackgroundResource(R.drawable.bg_style_to_coment);

                  to_comment.setText("参与讨论");

              }else if (maps!=null){
                  int size=maps.size();
                  Log.d("******************","1");
                  if (size==0) {
                      isComent=false;
                      to_comment.setTextColor(Color.WHITE);
                      to_comment.setBackgroundResource(R.drawable.bg_style_to_coment);

                      to_comment.setText("参与讨论");
                      isComent = false;
                  } else if (size>0){

                      commentAdapter=new CommentAdapter(this,maps);
                      comment_list.setAdapter(commentAdapter);
                      // 动态设置listview的高,必须在适配器实例化初始化后执行
                      ViewGroup.LayoutParams params=comment_list.getLayoutParams();
                      params.height=getHeight()+(comment_list.getDividerHeight()*(commentAdapter.getCount()-1));
                      comment_list.setLayoutParams(params);
                      comment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                          @Override
                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                              Log.d("+++++++++++++++++++++++", String.valueOf(id));
                              new Handler().postDelayed(new Runnable() {
                                  @Override
                                  public void run() {
                                      Intent intent=new Intent(FilmDetailActivity.this,ReplyActivty.class);
                                      intent.putExtra("comment_id",id);
                                      Map<String,String> m= (Map<String, String>) commentAdapter.getItem(position);
                                      Log.d("测试————————",m.get("nick_name"));
                                      intent.putExtra("comment_id",commentAdapter.getRealId(position));
                                      intent.putExtra("nick_name",m.get("nick_name"));
                                      intent.putExtra("score",m.get("score"));
                                      intent.putExtra("content",m.get("content"));
                                      intent.putExtra("comment_date",m.get("comment_date"));
                                      intent.putExtra("avatar",m.get("avatar"));
                                      intent.putExtra("zan_nums",m.get("zan_nums"));
                                      intent.putExtra("reply_nums",m.get("reply_nums"));
                                      intent.putExtra("my_zan",m.get("my_zan"));
                                      intent.putExtra("film_id",film_id);
                                      intent.putExtra("from_uid",m.get("from_uid"));
                                      startActivity(intent);
                                  }
                              },200);
                          }
                      });
                          for (int i=0;i<size;i++){
                              Map<String,String> m=maps.get(i);
                        Log.d("******************","2");
                              if (Integer.parseInt(m.get("from_uid"))==user_id){
                                  isComent=true;
                                  to_comment.setText("编辑讨论");
                                  to_comment.setTextColor(Color.RED);
                                  to_comment.setBackgroundResource(R.drawable.bg_style_to_comment_02);
                                  break;
                              }else {
                                  isComent=false;
                                  to_comment.setTextColor(Color.WHITE);
                                  to_comment.setBackgroundResource(R.drawable.bg_style_to_coment);

                                  to_comment.setText("参与讨论");

                              }
                          }
                      }

              }
    }

    @Override
    public void setUserFilm(List<Map<String, String>> maps) {
              if (maps.size()>0){
                    if (maps.get(0).get("wish_status").equals("1")){
                        wish_btn.setText("已想看");
                        this.wishTag=1;
                    }else {
                        wish_btn.setText("想看");
                        this.wishTag=0;
                    }
                    if (maps.get(0).get("looked").equals("1")){
                        looked_btn.setText("已看过去评分");
                        this.lookedTag=1;
                    }else {
                        looked_btn.setText("看过");
                        this.lookedTag=0;

                    }

              }
    }

    @Override
    public void showDoLooked(String message) {
              presenter.getUserFilm(film_id,user_id);
             Toast.makeText(FilmDetailActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDoWish(String message) {
          Toast.makeText(FilmDetailActivity.this,message,Toast.LENGTH_LONG).show();
        presenter.getUserFilm(film_id,user_id);
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public int getHeight() {
        int total = 0;
        for (int i = 0; i < commentAdapter.getCount(); i++) {
            View listItem = commentAdapter.getView(i, null, comment_list);
            listItem.measure(0, 0);
            total += listItem.getMeasuredHeight();
        }
        return total;
    }




    @Override
    public void onResume() {
        super.onResume();
//        用户从平林页面返回此页时调用
//        刷新页面数据这里主要刷新listview的评论去信息
        JSONObject object=new JSONObject();
        try {
            object.put("t_id",film_id);
            object.put("type",1);
            object.put("user_id",user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        presenter.getComment(object.toString());

    }
   //弹出对话框删除目标用户的评论
   public void showNormalDialog(){

       final AlertDialog.Builder normalDialog =
               new AlertDialog.Builder(FilmDetailActivity.this);
       normalDialog.setIcon(R.drawable.ic_lautcher);
       normalDialog.setTitle("确认你的选择");
       normalDialog.setMessage("你确定要删除自己的评论吗？");
       normalDialog.setPositiveButton("确定",
               new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       //...To-do
                       try {
                           deletMyComment();
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               });
       normalDialog.setNegativeButton("关闭",
               new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       //...To-do

                   }
               });
       // 显示
       normalDialog.show();
   }
//删除自己的评论
    public void deletMyComment() throws JSONException {
        JSONObject object=new JSONObject();
        object.put("film_id",film_id);
        object.put("user_id",user_id);
        object.put("type",1);
        String json = object.toString();
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getApiServices().deleteMyComment(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new BaseObserver<Map<String,String>>(filmDetailActivity){
                    //观察者数据的接收器
                    static final String TAG="----删除评论 ----：";
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String,String>> entity) {
                        super.onNext(entity);
                        Log.d("onNext()方法--",entity.getMessage());
                        Toast.makeText(filmDetailActivity,entity.getMessage(),Toast.LENGTH_LONG).show();
                        filmDetailActivity.initCommentInfo();
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

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }

  public void doWish(){
      //        创建JSONObject对象，传入map参数
      JSONObject jsonObject=new JSONObject();
//       将JSONObject对象转换为json

//       将json字符串转换为————请求体——————RequestBody
      try {
          jsonObject.put("user_id",user_id);
//
          jsonObject.put("film_id",film_id);

          jsonObject.put("wish_status",wishTag);


      } catch (JSONException e) {
          e.printStackTrace();
      }
      String json=jsonObject.toString();
//                封装请求体
      RequestBody requestBody=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
      presenter.doWish(requestBody);
  }

}
