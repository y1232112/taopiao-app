package com.example.taopiao.mvp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.mvp.contract.SnackBuyContract;
import com.example.taopiao.mvp.presenter.SnackBuyPresenter;
import com.example.taopiao.utils.BitmapUtils;
import com.example.taopiao.utils.QREncodingCodeUtils;
import com.example.taopiao.widget.PassEditView;
import com.uber.autodispose.AutoDisposeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.taopiao.mvp.activity.DoAvatarActivity.verifyStoragePermissions;

public class SnackBuyActivity extends AppCompatActivity implements SnackBuyContract.view {
    @BindView(R.id.i1_title)
    TextView i1;
    @BindView(R.id.i2_title)
    TextView i2;
    @BindView(R.id.i3_title)
    TextView i3;
    @BindView(R.id.i4_title)
    TextView i4;
    @BindView(R.id.i1_des)
    TextView i1des;
    @BindView(R.id.i2_des)
    TextView i2des;
    @BindView(R.id.i3_des)
    TextView i3des;
    @BindView(R.id.i4_des)
    TextView i4des;
    @BindView(R.id.dis)
    TextView dis;
    @BindView(R.id.disprice)
    TextView disprice;
    @BindView(R.id.oprice)
    TextView oprice;
    @BindView(R.id.click_count)
    ImageView click_count;
    @BindView(R.id.click_dis)
    ImageView click_dis;
    @BindView(R.id.show_nums)
    TextView show_nums;
    @BindView(R.id.show_tp)
    TextView show_tp;
    @BindView(R.id.root_snack_buy)
    FrameLayout rootView;

    private Double tprice=0.0;
    private Double price=0.0;
    private static Integer number=0;
    private String phone;
    private Integer user_id;
    private SnackBuyContract.presenter presenter;
    private Integer id;
    private String payType;
    private InputMethodManager inputMethodManager;
  public static SnackBuyActivity snackBuyActivity;
    private Integer snack_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_buy);

         Intent intent=getIntent();
        ButterKnife.bind(this);
        MyApplication app=MyApplication.getInstance();
        user_id=app.user_id;
         String s1=intent.getStringExtra("id");
         id=Integer.parseInt(s1);
        presenter=new SnackBuyPresenter(this,this);
        presenter.getUser();
        presenter.getSnack(id);
    }

  public SnackBuyActivity(){
    snackBuyActivity=this;
  }
  public static SnackBuyActivity getInstance(){
        return snackBuyActivity;
  }
    @OnClick({R.id.click_dis,R.id.click_count,R.id.pay_snack})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.click_count:
                 ++number;
                 tprice=number*price;
                 show_tp.setText(String.valueOf(tprice));
                 show_nums.setText(String.valueOf(number));
                break;
            case R.id.click_dis:

                 if (number>0){
                     --number;
                     tprice=number*price;
                     show_tp.setText(String.valueOf(tprice));
                     show_nums.setText(String.valueOf(number));
                 }

                break;
            case R.id.pay_snack:
                if (tprice>0){
                    showPipupWindow(rootView);
                }else {
                    Toast.makeText(SnackBuyActivity.this,"你还没有选择任何商品",Toast.LENGTH_SHORT);
                }
                break;
                default:
                    break;
        }
    }
    @Override
    public void setContent(List<Map<String, String>> maps) {
       if (maps!=null){
           if (maps.size()>0){
               Map<String,String> map=maps.get(0);
               price=Double.parseDouble(map.get("discount_price"));

               dis.setText(map.get("discount"));
               disprice.setText(map.get("discount_price"));
               if (!map.get("discount").equals("原价")){
                   oprice.setText(map.get("origin_price"));
                   oprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
               }

               i1.setText(map.get("item1"));
               i1des.setText(map.get("it_size1")+"/"+map.get("it_num1"));
               Log.d("--------------item----",map.get("it_num1"));
               if (map.get("item2")!=null&&!map.get("item2").equals("")){
                   i2.setText(map.get("item2"));
                   i2des.setText(map.get("it_size2")+"/"+map.get("it_num2"));
               }
               if (map.get("item3")!=null&&!map.get("item3").equals("")){
                   i3.setText(map.get("item3"));
                   i3des.setText(map.get("it_size3")+"/"+map.get("it_num3"));
               }
               if (map.get("item4")!=null&&!map.get("item4").equals("")){
                   i4.setText(map.get("item4"));
                   i4des.setText(map.get("it_size4")+"/"+map.get("it_num4"));
               }
           }
       }
    }

    @Override
    public void setUser(Map<String, String> maps) {
            if (maps!=null){
                if (maps.size()>0){
                    phone=maps.get("phone");
                }
            }
    }

    @Override
    public void laterDoOrder(String url) {
        Log.d("--url--",url);
        if (!url.equals("上传失败")){
            JSONObject object=new JSONObject();
            try {
                object.put("snack_id",id);
                object.put("user_id",user_id);
                object.put("order_phone",phone);
                object.put("goods_num",number);
                object.put("total_price",tprice);
                object.put("pay_type",payType);
                object.put("phone_code",url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String json=object.toString();
            RequestBody requestBody= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
            presenter.postSnackOrder(requestBody);
        }

    }

    @Override
    public void messageSnackOrder(String message) {
        Toast.makeText(SnackBuyActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
    //弹窗
    private void showPipupWindow(View view){
        //加载自定义布局
        View contentView= LayoutInflater.from(SnackBuyActivity.this).inflate(R.layout.popup_window_pay,null);
        View contentView2=LayoutInflater.from(SnackBuyActivity.this).inflate(R.layout.ps_edit,null);
        RelativeLayout lin1=contentView.findViewById(R.id.pay_zhifubao);
        RelativeLayout lin2=contentView.findViewById(R.id.pay_weixin);
        RelativeLayout lin3=contentView.findViewById(R.id.pay_union);
        CheckBox box1=(CheckBox) lin1.findViewById(R.id.select_zhifubao);
        CheckBox box2=(CheckBox)lin2.findViewById(R.id.select_weixin);
        CheckBox box3=(CheckBox)lin3.findViewById(R.id.select_union);
        ImageView gobac_ps=contentView2.findViewById(R.id.go_back_ps);

        ImageView nopay_return=contentView.findViewById(R.id.nopay_return);
        final PopupWindow popupWindow=new PopupWindow(contentView,RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        final PopupWindow popupWindow2=new PopupWindow(contentView2,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        PassEditView passEditView=contentView2.findViewById(R.id.my_editps);
        passEditView.setPopupWindow(popupWindow,popupWindow2);
        nopay_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        gobac_ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupWindow2.dismiss();
            }
        });

        lin1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                box1.setChecked(true);
                box2.setChecked(false);
                box3.setChecked(false);
                payType="支付宝";
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow2.setBackgroundDrawable(getResources().getDrawable(R.drawable.tranparent_boder_square));
//        popupWindow.setHeight(90);
//        popupWindow.setWidth(90);
                        popupWindow2.showAtLocation(view,Gravity.BOTTOM,0,0);

                    }
                },200);
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box1.setChecked(false);
                box2.setChecked(true);
                box3.setChecked(false);
                payType="微信";
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow2.setBackgroundDrawable(getResources().getDrawable(R.drawable.tranparent_boder_square));
//        popupWindow.setHeight(90);
//        popupWindow.setWidth(90);
                        popupWindow2.showAtLocation(view,Gravity.BOTTOM,0,0);
                    }
                },200);
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box1.setChecked(false);
                box2.setChecked(false);
                box3.setChecked(true);
                payType="银联";
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow2.setBackgroundDrawable(getResources().getDrawable(R.drawable.tranparent_boder_square));
//        popupWindow.setHeight(90);
//        popupWindow.setWidth(90);
                        popupWindow2.showAtLocation(view,Gravity.BOTTOM,0,0);
                    }
                },200);
            }
        });

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.tranparent_boder_square));
//        popupWindow.setHeight(90);
//        popupWindow.setWidth(90);
        popupWindow.showAtLocation(view,Gravity.BOTTOM,0,0);


    }

    public void showNormalDialog(PopupWindow popupWindow){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SnackBuyActivity.this);
        normalDialog.setIcon(R.drawable.ic_lautcher);
        normalDialog.setTitle("请确认你的订单");
        normalDialog.setMessage("你确定要提交订单吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        popupWindow.dismiss();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                          popupWindow.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }
  public void doOrderSnacks(){
      String content="od-小食-"+id+"-"+user_id+"-num:"+number+"-tprice-"+tprice+"-paytype-"+payType+"-phone:"+phone;
      Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.avatar);
      Bitmap bitmap1=QREncodingCodeUtils.receiveQRBitmap(content,400,400,"UTF-8",
              "H","1", Color.BLACK,Color.WHITE,bitmap,0.2F,null);
      JSONObject ob1=new JSONObject();
      MyApplication app=MyApplication.getInstance();
      try {
          ob1.put("uer_id",app.user_id);
          ob1.put("type","image");
          //封装两个body
          RequestBody des=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),ob1.toString());
//          verifyStoragePermissions(SnackBuyActivity.this);
          File file= BitmapUtils.compressImage(bitmap1);
          MultipartBody.Builder builder=new MultipartBody.Builder()
                  .setType(MultipartBody.FORM)
                  .addFormDataPart("desc","这是图片");

          RequestBody requestFile =
                  RequestBody.create(MediaType.parse("image/png"), file);
          MultipartBody.Part mfile =
                  MultipartBody.Part.createFormData("file", file.getName(), requestFile);

          presenter.uploadCode(mfile);
      } catch (JSONException e) {
          e.printStackTrace();
      }
  }

}
