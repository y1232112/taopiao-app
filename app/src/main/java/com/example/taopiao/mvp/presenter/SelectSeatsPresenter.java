package com.example.taopiao.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.taopiao.adapter.ScheduleAdapter;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.SelectCinemaContract;
import com.example.taopiao.mvp.contract.SelectSeatsContract;
import com.example.taopiao.network.retrofit.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SelectSeatsPresenter implements SelectSeatsContract.presenter {


    private Context context;
    private SelectSeatsContract.view view;

    public SelectSeatsPresenter(Context context, SelectSeatsContract.view view){
        this.context = context;
        this.view = view;

    }

    @Override
    public void getSeatsInfo(Integer cinema_id, Integer hall_id,Integer schedule_id) {
        JSONObject object=new JSONObject();
        try {
            object.put("cinema_id",cinema_id);
            object.put("hall_id",hall_id);
            object.put("schedule_id",schedule_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json=object.toString();
        RequestBody requestBody= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        BaseRequest.getInstance().apiServices.getSeats(requestBody)
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
                                view.getSeats(maps);

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

    @Override
    public void getSchedule(Integer schedule_id) {
        BaseRequest.getInstance().apiServices.getScheduleById(schedule_id)
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
                                view.doScheduleSeats(maps);

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

    @Override
    public void getHall(Integer cinema_id, Integer hall_id) {
        BaseRequest.getInstance().apiServices.getHall(cinema_id,hall_id)
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
                                view.setHall(maps);

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

    @Override
    public void addOrder(RequestBody requestBody) {
        BaseRequest.getInstance().apiServices.cummitFilmOrder(requestBody)
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
                        view.doOrderLater(entity.getMessage());
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

    @Override
    public void addItem(RequestBody requestBody) {
        BaseRequest.getInstance().apiServices.addItem(requestBody)
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
                        view.showDoItemResult(entity.getMessage());
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
}
