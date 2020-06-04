package com.example.taopiao.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taopiao.MainActivity;
import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.activity.DoAvatarActivity;
import com.example.taopiao.mvp.activity.LoginActivity;
import com.example.taopiao.mvp.activity.MyLookedActivity;
import com.example.taopiao.mvp.activity.MyOderFilmActivity;
import com.example.taopiao.mvp.activity.MyOrderSnackActivity;
import com.example.taopiao.mvp.activity.MyWishActivity;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.utils.netUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineFragment extends Fragment {
    private static final String TAG="--MineFragment--";
    @BindView(R.id.mine_to_login)
    Button toLoginBtn;
    @BindView(R.id.my_nick)
    TextView myNick;
    @BindView(R.id.my_avatar)
    ImageView mAvatar;
    @BindView(R.id.mine_to_wish)
    TextView mywishfilm;
    @BindView(R.id.mine_to_looked)
    TextView mylookedfilm;
    @BindView(R.id._left)
    RelativeLayout filmticket;
    @BindView(R.id._right)
    RelativeLayout snack;
    private Map<String,String> my_info;
    private ImageLoadingListener animateFirstListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_mine,container,false);
//    在fragment中使用并绑定Butterknife
        ButterKnife.bind(this,view);
        MyApplication app=MyApplication.getInstance();
        int user_id=app.user_id;
        BaseRequest.getInstance().getApiServices().userInfo(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new BaseObserver<Map<String,String>>(getContext()){
                    //观察者数据的

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String,String>> entity) {
                        super.onNext(entity);
                        Log.d("onNext()方法--freagment=====",entity.toString());
                              my_info=entity.getParams();
                        myNick.setText(my_info.get("nickName"));

//                        Log.d("onNext**************",my_info.get("nickName"));
                              if (my_info.get("avatar")!=null){

                        String url= netUtils.server_root_path+my_info.get("avatar");

                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .showStubImage(R.drawable.avatar)
                                .showImageForEmptyUri(R.drawable.avatar)
                                .showImageOnFail(R.drawable.avatar)
                                .cacheInMemory(true)
                                .cacheOnDisc(false)
                                .displayer(new RoundedBitmapDisplayer(500))
                                .build();
                        ImageLoader.getInstance().displayImage(url,mAvatar,options,animateFirstListener);
                    }
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
       return view;
    }

    @OnClick({R.id.mine_to_login,R.id.my_avatar,R.id.mine_to_looked,R.id.mine_to_wish,R.id._right,R.id._left})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.mine_to_login:

           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   startActivity(new Intent(getActivity(), LoginActivity.class));
               }
           },100);
           break;
            case R.id.my_avatar:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), DoAvatarActivity.class));
                    }
                },100);
                break;
            case R.id.mine_to_wish:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), MyWishActivity.class));
                    }
                },100);
                break;
            case R.id.mine_to_looked:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), MyLookedActivity.class));
                    }
                },100);
                break;
            case R.id._left:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), MyOderFilmActivity.class));
                    }
                },100);
                break;
            case R.id._right:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), MyOrderSnackActivity.class));
                    }
                },100);
                break;
                default:
                    break;
        }
    }
}
