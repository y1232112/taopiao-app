package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public interface SelectSeatsContract {

    interface view extends BaseView<CinemaContract.presenter> {
        void doScheduleSeats( List<Map<String,String>> maps);
        void getSeats( List<Map<String,String>> maps);
        void  setHall( List<Map<String,String>> maps);
        void doOrderLater( String message);
        void showDoItemResult(String message);
    }
    interface presenter extends BasePresenter {
        void getSeatsInfo(Integer cinema_id,Integer hall_id,Integer schedule_id);
        void getSchedule(Integer schedule_id);
        void getHall(Integer cinema_id,Integer hall_id);
        void addOrder(RequestBody requestBody);
        void addItem(RequestBody requestBody);
    }
}
