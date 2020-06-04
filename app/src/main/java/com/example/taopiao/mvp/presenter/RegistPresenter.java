package com.example.taopiao.mvp.presenter;
import android.content.Context;
import android.util.Log;

import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.mvp.contract.RegistContract;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


public class RegistPresenter implements RegistContract.presenter {

    private Context context;
    private RegistContract.view view;

    public RegistPresenter(Context context, RegistContract.view view) {
        this.context = context;
        this.view = view;
    }

    /**
     *
     * @param phone
     * @param password
     */
    @Override
    public void userRegist(String username, String password,String sex,String phone) {
        Map<String,String> messageMap=new HashMap<>();

//       创建map集合，推入数据
        Map<String,String> map=new HashMap<String, String>();
        map.put("nickname",username);
        map.put("password",password);
        map.put("sex",sex);
        map.put("phone",phone);
//        创建JSONObject对象，传入map参数
       JSONObject jsonObject=new JSONObject(map);
//       将JSONObject对象转换为json
       String json=jsonObject.toString();
//       将json字符串转换为————请求体——————RequestBody
        RequestBody requestBody=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
//       进行http请求
        BaseRequest.getInstance().getApiServices().userRegist(requestBody)
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
                        Log.d("onNext()方法--",entity.toString());
                    }

                    @Override
                    public void onSuccess(BaseEntity<Map<String,String>> entity) {
                       if(entity.isSuccess()){
                           view.setContent("hello---->"+entity.getParams());
                       }else {
                           view.setContent("失败了--->"+entity.getMessage());
                       }
                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String,String>> entity) {

                        Log.d(TAG+"ErrCode--",Integer.toString(entity.getCode()));
                        view.setContent("---onCodeError-->"+Integer.toString(entity.getCode())+"="+entity.getParams());
                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {
                        view.setContent("---请求失败了----->"+e.getMessage());
                    }
                });
    }
}
