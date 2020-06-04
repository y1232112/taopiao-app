package com.example.taopiao.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.taopiao.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectSeatView extends View {
    private static final String TAG="Select seat view------";
    private List<Map<String,String>> mDatas;
    private List<Integer> ids;
    private Paint paint_seat;
    Bitmap _sale, _sold, _selected;
    Bitmap sale, sold, selected;
    private int widthMode;
    private int heightMode;
    private int width;
    private int height;
    private int seatSize=100;//座位大小（高度=宽度）最小50
    boolean firstScale = true;//是否第一次缩放
    private int row;//行数
    private int column;//列数
    private int verSpacing=10;//竖直间距
    private int horSpacing=10;//水平间距
    private String screenName = "";//荧幕名称
    private int yinmuSize=10;//假设荧幕长度固定10个位置的长度
    private float startX=0;
    private float startY=70;
   private Matrix matrix;
    public SelectSeatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       matrix=new Matrix();
        sale = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_seat_sale);
        sold = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_seat_sold);
        selected = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_seat_selected);
        sale=Bitmap.createScaledBitmap(sale,seatSize,seatSize,false);
        sold=Bitmap.createScaledBitmap(sold,seatSize,seatSize,false);
        selected=Bitmap.createScaledBitmap(selected,seatSize,seatSize,false);
        Log.d(TAG, "onDraw: "+"高，宽："+width+"/"+height);
        ids=new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                setMeasuredDimension((seatSize+horSpacing)*column,(seatSize+verSpacing)*row+70);
        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        paint_seat=new Paint();
        paint_seat.setAntiAlias(false);
        Log.d(TAG, "mesure: "+"高，宽："+width+"/"+height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d(TAG, "onDraw: "+"高，宽："+width+"/"+height);
        if (row <= 0 || column == 0) {
            return;
        }
//        Log.d(TAG, "onDraw: maps--"+mDatas.toString());
        if (mDatas.size()>0){
            for (int x=0;x<row;x++){
                for (int y=0;y<column;y++){
                    Map<String,String> map=mDatas.get(x*column+y);
                    if (map.get("active").equals("0")){

                    }else {
                        int t=Integer.parseInt(map.get("active2"));
                        if (t==0){
                            if (ids.size()>0){
                                for (int i=0;i<ids.size();i++){

                                    if (ids.get(i)==Integer.parseInt(map.get("seat_id"))){
                                        canvas.drawBitmap(selected,startX+y*seatSize+y*horSpacing,startY+x*seatSize+x*verSpacing,paint_seat);
                                    }else {
                                        canvas.drawBitmap(sale,startX+y*seatSize+y*horSpacing,startY+x*seatSize+x*verSpacing,paint_seat);
                                    }
                                }

                            }else {
                                canvas.drawBitmap(sale,startX+y*seatSize+y*horSpacing,startY+x*seatSize+x*verSpacing,paint_seat);

                            }


                        }else if (t>0){
                            canvas.drawBitmap(sold,startX+y*seatSize+y*horSpacing,startY+x*seatSize+x*verSpacing,paint_seat);
                            invalidate();
                        }

                    }

                }
            }
        }

        Paint paint1=new Paint();
        paint1.setColor(Color.parseColor("#DCDCDC"));
        paint1.setAntiAlias(true);//设置抗锯齿
        paint1.setStyle(Paint.Style.STROKE);//实心
        paint1.setStrokeWidth(100);//线条粗细
        canvas.drawLine(getYinmuStartX(column),0,getYinmuEndX(column),0,paint1);

    }
    public void initData(int row, int column, List<Map<String,String>> maps){
        this.row=row;
        this.column=column;
        this.mDatas=maps;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                for (int r=0;r<row;r++){
                    for (int c=0;c<column;c++){
                        if (x>(startX+c*seatSize+c*horSpacing)&&x<(startX+c*seatSize+c*horSpacing)+seatSize
                                &&y>(startY+r*seatSize+r*verSpacing)&&y<(startY+r*seatSize+r*verSpacing)+seatSize){
                            int clicked=r*column+c;
                            Log.d(TAG, "onTouchEvent: 你点击了---"+clicked);
                            Map<String,String> map=mDatas.get(clicked);
                            if (!map.get("active").equals("0")){
                                int t=Integer.parseInt(map.get("active2"));
                                if (t==0){
                                    Log.d(TAG, "onTouchEvent: ids---"+ids.toString());
                                    List<Integer> temp=new ArrayList<>();
                                    if (ids.size()>0){
                                        int count=0;
                                        for (int h=0;h<ids.size();h++){
                                            if (ids.get(h)!=Integer.parseInt(map.get("seat_id"))){
                                                temp.add(ids.get(h));

                                            }else {
                                                count++;
                                            }
                                            if (count==0){
                                                temp.add(Integer.parseInt(map.get("seat_id")));
                                            }
                                        }
                                        ids=temp;


                                    }else if (ids.size()==0){
                                        ids.add(Integer.parseInt(map.get("seat_id")));
                                        Log.d(TAG, "onTouchEvent: list temp--"+temp.toString());
                                    }


                                }

                            }
                            break;
                        }

                    }
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
    public float getYinmuStartX(int column){
        if (column<=10){
            return 0;
        }else {
           return ((column-10)/2)*(seatSize+horSpacing);
        }
    }
    public float getYinmuEndX(int column){
        if (column<=10){
            return 0;
        }else {
           return ((column-10)/2+10)*(seatSize+horSpacing);
        }
    }


}
