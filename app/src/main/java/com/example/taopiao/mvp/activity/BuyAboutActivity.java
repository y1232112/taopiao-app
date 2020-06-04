package com.example.taopiao.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.mvp.contract.BuyAboutFilmContract;
import com.example.taopiao.mvp.presenter.buyAboutFilmPresenter;
import com.example.taopiao.utils.DateUtils;
import com.example.taopiao.utils.netUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.uber.autodispose.AutoDisposeConverter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyAboutActivity extends AppCompatActivity implements BuyAboutFilmContract.view {
    private Integer film_id;
    private BuyAboutFilmContract.presenter presenter;
    private ImageLoadingListener animateFirstListener;
    public static BuyAboutActivity buyAboutActivity;
    @BindView(R.id.bg_buy_about_go_back)
    ImageView buy_about_go_back;
    @BindView(R.id.ic_about_film)
    ImageView ic_film;
    @BindView(R.id.film_title_about)
    TextView title;
    @BindView(R.id.score_rating_about)
    RatingBar ratingBar;
    @BindView(R.id.show_score_about)
    TextView score;
    @BindView(R.id.film_score_nums_about)
    TextView score_nums;
   @BindView(R.id.film_type_about)
   TextView type;
   @BindView(R.id.product_length)
   TextView product_length;
   @BindView(R.id.date_about_info)
   TextView date;
   @BindView(R.id.brief_info_about)
   TextView brief;
   @BindView(R.id.about_wish_nums)
   TextView wish_nums;
   @BindView(R.id.about_looked_nums)
   TextView looked_nums;
   @BindView(R.id.to_schedule_about)
   Button buybtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_about_film);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        buyAboutActivity=this;
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String id=intent.getStringExtra("film_id");
        film_id=Integer.parseInt(id);
        presenter=new buyAboutFilmPresenter(this,this);
        presenter.buyAboutFilm(film_id);


    }
    @OnClick({R.id.bg_buy_about_go_back,R.id.to_schedule_about})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.bg_buy_about_go_back:
                finish();
                break;
            case R.id.to_schedule_about:
                Intent intent2=new Intent(BuyAboutActivity.this,SelectCinemaActivity.class);
                intent2.putExtra("film_id",film_id);
                startActivity(intent2);
            default:
                break;
        }
    }

    @Override
    public void showContent(List<Map<String, String>> maps) {
            if (maps.size()>0){
                Map<String,String> map=maps.get(0);
                title.setText(map.get("film_name"));
                if (map.get("avgscore")==null){

                }else {
                    ratingBar.setRating((float) Double.parseDouble(map.get("avgscore"))/2);
                }


                if (map.get("score_nums").equals("0")){
                    score.setText("还未评分");

                }else {
                    score.setText(map.get("avgscore")+"分");
                }
                score_nums.setText("("+map.get("score_nums")+"人评分)");
                type.setText(map.get("type"));
                product_length.setText(map.get("product_area")+"/"+map.get("film_length"));
                String s= DateUtils.getDateToString(Long.parseLong(map.get("public_date")),"yyyy年MM月dd日");
                date.setText(s+"/大陆上映");
                wish_nums.setText("有"+map.get("wish_nums")+"人想看");
                looked_nums.setText("有"+map.get("looked_nums")+"人看过");
                brief.setText(map.get("brief"));
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showStubImage(R.drawable.ic_lautcher)
                        .showImageForEmptyUri(R.drawable.ic_lautcher)
                        .showImageOnFail(R.drawable.ic_lautcher)
                        .cacheInMemory(true)
                        .cacheOnDisc(false)
                        .displayer(new RoundedBitmapDisplayer(20))
                        .build();
                String url= netUtils.server_root_path+map.get("img");
                ImageLoader.getInstance().displayImage(url,ic_film,options,animateFirstListener);
            }
    }
    public Integer getFilm_id(){
        return film_id;
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
}


