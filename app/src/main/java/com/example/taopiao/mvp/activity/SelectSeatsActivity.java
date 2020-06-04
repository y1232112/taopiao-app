package com.example.taopiao.mvp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.adapter.GridSeatAdapter;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.SelectSeatsContract;
import com.example.taopiao.mvp.entity.SeatModel;
import com.example.taopiao.mvp.presenter.SelectSeatsPresenter;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.widget.SeatOverView;
import com.example.taopiao.widget.SeatRowView;
import com.example.taopiao.widget.SelectSeatView;
import com.uber.autodispose.AutoDisposeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringJoiner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SelectSeatsActivity extends AppCompatActivity implements SelectSeatsContract.view {
    private SelectSeatsContract.presenter presenter;
    Bitmap _sale, _sold, _selected;
    Bitmap sale, sold, selected;
    private double nStart;
    private Integer schedule_id;
    private Integer cinema_id;
    private Integer film_id;
    private Integer hall_id;
    private String hall_name;
    private Integer column_count;
    private Integer row_count;
    private SeatModel[][] hallSeats;
    private Map<String, String> hall;
    private GridSeatAdapter seatAdapter;
    private double origin_price;
    private double discout_price;
    private String discount;
    //位置信息
    private int seat_width;//座位宽
    private int seat_height;//作为高
    private int xSpacing;//水平间距
    private int ySpacing;//垂直间距
    //
    public String user_phone;
    private static Integer selectedNums=0;
    private List<Integer> selected_seatIds=new ArrayList<>();
    private String order_id;
    private double scaleTag=1;
   private String show_date;
    TextView hall_title;
//    @BindView(R.id.seat_view)
//    SelectSeatView seatView;
    @BindView(R.id.seat_over_view)
    SeatOverView seatOverView;
    @BindView(R.id.seat_row_view)
    SeatRowView seatRowView;
    @BindView(R.id.hv_seat)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.seat_grid)
    GridView seat_grid;
    @BindView(R.id.cummit_film_order)
    Button cummit_film_order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seats);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ButterKnife.bind(this);
        getUser();
        //

        //
        Intent intent = getIntent();
        schedule_id = Integer.parseInt(intent.getStringExtra("schedule_id"));
        String id2 = intent.getStringExtra("cinema_id");
        String id3 = intent.getStringExtra("hall_id");
        String id4 = intent.getStringExtra("film_id");
        discount = intent.getStringExtra("discount");

        String s3 = intent.getStringExtra("discount_price");
        String s4 = intent.getStringExtra("origin_price");
        hall_name = intent.getStringExtra("cinema_name");

        Log.d("价格-----：", s3 + " " + s4);
        origin_price = Double.parseDouble(s4);
        discout_price = Double.parseDouble(s3);

        cinema_id = Integer.parseInt(id2);
        film_id = Integer.parseInt(id4);
        hall_id = Integer.parseInt(id3);
        presenter = new SelectSeatsPresenter(this, this);
        presenter.getHall(cinema_id, hall_id);
        presenter.getSeatsInfo(cinema_id, hall_id,schedule_id);
//       seats_container.setWillNotDraw(false);
        sale = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ic_seat_sale);
        sold = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ic_seat_sold);
        selected = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ic_seat_selected);
        initSeats();
    }

    @OnClick({ R.id.cummit_film_order})
    public void OnClick(View view) {
        switch (view.getId()) {

            case R.id.cummit_film_order:
                 showNormalDialog();
                Log.d("你选择的ids是：",seatAdapter.getIds().toString()+seatAdapter.getName());
                default:
                    break;
        }
    }

    void initSeats(){

    }

    public List<Integer> getSelected_seatIds() {
        return selected_seatIds;
    }

    public void setSelected_seatIds(List<Integer> selected_seatIds) {
        this.selected_seatIds = selected_seatIds;
    }

    @Override
    public void doScheduleSeats(List<Map<String, String>> maps) {
          if (maps.size()>0){


          }
    }

    public SelectSeatsContract.presenter getPresenter() {
        return presenter;
    }

    public Double getOrigin_price() {
        return origin_price;
    }

    public Double getDiscout_price() {
        return discout_price;
    }

    public String getDiscount() {
        return discount;
    }
    void getUser(){
        MyApplication app=MyApplication.getInstance();
        int user_id=app.user_id;
        BaseRequest.getInstance().getApiServices().userInfo(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new BaseObserver<Map<String,String>>(this){
                    //观察者数据的

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String,String>> entity) {
                        super.onNext(entity);
                        Log.d("onNext()方法--freagment=====",entity.toString());
                        Map<String,String> map=entity.getParams();
                        user_phone=map.get("phone");
                    }
                    @Override
                    public void onSuccess(BaseEntity<Map<String,String>> entity) {
                        if(entity.isSuccess()){

                        }else {

                        }
                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String,String>> entity) {



                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }
    @Override
    public void getSeats(List<Map<String, String>> maps) {
              if (maps!=null){
                  if (maps.size()>0){
                      hallSeats=new SeatModel[maps.size()][maps.get(0).size()];
                       //
                    Log.d("影厅行数和列数：",column_count+" "+row_count);
//                    ViewGroup.LayoutParams params=seatView.getLayoutParams();
//                    params.width=column_count*110;
//                    params.height=row_count*110+70+100;
                      ViewGroup.LayoutParams params1=seatRowView.getLayoutParams();
                      params1.height=row_count*110+70;
//                    seatView.setLayoutParams(params);
//                      seatView.initData(row_count,column_count,maps);
                    seatRowView.setLayoutParams(params1);

                    seatOverView.initData(row_count,column_count,maps);
                    seatRowView.initData(row_count,column_count);

                        seatAdapter=new GridSeatAdapter(SelectSeatsActivity.this,maps);
                        seat_grid.setNumColumns(column_count);
                        seat_grid.setAdapter(seatAdapter);
                      ViewGroup.LayoutParams params=seat_grid.getLayoutParams();
                      params.width=column_count*110;
                      params.height=row_count*110+70+100;
                       seat_grid.setLayoutParams(params);
//                       seat_grid.setOnTouchListener(new View.OnTouchListener() {
//                           @Override
//                           public boolean onTouch(View v, MotionEvent event) {
//                               int pointerCount=event.getPointerCount();
//                               int n=event.getAction();
//                               if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN && 2 == pointerCount){
//                                 for (int i=0;i<pointerCount;i++){
//                                     float x=event.getX(i);
//                                     float y=event.getY(i);
//                                     Point point=new Point((int)x,(int)y);
//                                 }
//                                 int xleng=Math.abs((int)event.getX(0)-(int)event.getX(1));
//                                 int yleng=Math.abs((int)event.getY(0)-(int)event.getY(1));
//                                 nStart=Math.sqrt((float)xleng*xleng+(float)yleng*yleng);
//                               }else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP && 2 == pointerCount){
//                                   for (int i=0;i<pointerCount;i++){
//                                       float x=event.getX(i);
//                                       float y=event.getY(i);
//                                       Point point=new Point((int)x,(int)y);
//                                   }
//                                   int xleng=Math.abs((int)event.getX(0)-(int)event.getX(1));
//                                   int yleng=Math.abs((int)event.getY(0)-(int)event.getY(1));
//                                   double nEnd=Math.sqrt((float)xleng*xleng+(float)yleng*yleng);
//                                  scaleTag=nEnd/nStart;
//                                   if (nEnd/nStart<0.8){
//                                       scaleTag=0.8;
//                                   } else if (nEnd/nStart>2){
//                                       scaleTag=2;
//                                   }
//                                   if (nEnd>nStart){
//                                       //放大
//                                       //通过两个手指开始距离和结束距离，来判断放大缩小
//
//                                       ViewGroup.LayoutParams params=seat_grid.getLayoutParams();
//                                       params.width=column_count*100*(int)scaleTag;
//                                       seat_grid.setLayoutParams(params);
//                                       int wd=seat_grid.getColumnWidth();
//                                       seat_grid.setColumnWidth((int)(wd*scaleTag));
//                                       seatAdapter.setScale(scaleTag);
//                                        Toast.makeText(SelectSeatsActivity.this,"放大"+scaleTag,Toast.LENGTH_SHORT).show();
//                                   }else {
//                                       //缩小
//                                       seatAdapter.setScale(scaleTag);
//                                       int wd=seat_grid.getColumnWidth();
//                                       int lastWd=(int)(wd*scaleTag);
//                                       seat_grid.setColumnWidth(lastWd);
//                                       Toast.makeText(SelectSeatsActivity.this,"缩小"+scaleTag+"宽度："+lastWd,Toast.LENGTH_SHORT).show();
//                                   }
//                               }
//                               return false;
//                           }
//                       });


                  }
              }


    }

    @Override
    public void setHall(List<Map<String, String>> maps) {
        if (maps.size()>0){
            hall=maps.get(0);
            String s1=hall.get("column_count");
            String s2=hall.get("row_count");
            column_count=Integer.parseInt(s1);
            row_count=Integer.parseInt(s2);
//            hallSeats=new SeatModel[row_count][column_count];
        }

    }

    @Override
    public void doOrderLater(String message) {
        JSONObject object=new JSONObject();
        MyApplication app=MyApplication.getInstance();
      List<Integer> list=seatAdapter.getIds();
      for (int k=0;k<list.size();k++){
          try {


              object.put("user_id",app.user_id);
              object.put("order_id",order_id);
              object.put("seat_id",list.get(k));
              object.put("price",discout_price);


          } catch (JSONException e) {
              e.printStackTrace();
          }
          String json=object.toString();
          RequestBody requestBody= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
          presenter.addItem(requestBody);
      }

        Toast.makeText(SelectSeatsActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDoItemResult(String message) {
        presenter.getSeatsInfo(cinema_id, hall_id,schedule_id);
            if (message.equals("成功获得位置")){

            }
           Toast.makeText(SelectSeatsActivity.this,message,Toast.LENGTH_LONG).show();
    }


    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
    //图形绘制
    public void drawSeats(int row_count,int column_count){
        for (int i=0;i<row_count;i++){
            for (int j=0;j<column_count;j++){
                int x=j*seat_width+j*xSpacing;
                int y=i*seat_height+i*ySpacing;

            }
        }

    }

    public void showNormalDialog(){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SelectSeatsActivity.this);
        normalDialog.setIcon(R.drawable.ic_lautcher);
        normalDialog.setTitle("请确认你的订单"+seatAdapter.getName());
        normalDialog.setMessage("你确定要提交订单吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        List<Integer> li=seatAdapter.getIds();
                        if (li.size()>0){
                            JSONObject object=new JSONObject();
                            MyApplication app=MyApplication.getInstance();

                            try {
                                SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
                                Random random=new Random();
                                int num=random.nextInt(999999);
                                order_id=cinema_id+"-"+film_id+"-"+df.format(new Date())+num;
                                object.put("user_id",app.user_id);
                                object.put("order_id",order_id);
                                object.put("schedule_id",schedule_id);
                                object.put("order_phone",user_phone);
                                object.put("ticket_num",seatAdapter.selected_nums);
                                object.put("ticket_total_price",seatAdapter.selected_nums*discout_price);
                                object.put("status",0);
                                List<String> strings=seatAdapter.getName();
                                object.put("ids",seatAdapter.getIds());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String json=object.toString();
                            RequestBody requestBody= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
                            presenter.addOrder(requestBody);
                        }else {
                            Toast.makeText(SelectSeatsActivity.this,"你还没有选择座位",Toast.LENGTH_LONG).show();
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
}
