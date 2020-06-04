package com.example.taopiao.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.taopiao.R;

import java.util.List;
import java.util.Map;

public class SeatOverView extends View {
    private static final String TAG="Select seat over view------";
    private List<Map<String,String>> mDatas;
    private Paint paint_seat;
    Bitmap _sale, _sold, _selected;
    Bitmap sale, sold, selected;
    private int lineSize=5;//边框线粗细
    private int betweenSpacing=20;//两端所留空隙
    private int widthMode;
    private int heightMode;
    private int width;
    private int height;
    private int seatSize;//座位大小（高度=宽度）最小50
    boolean firstScale = true;//是否第一次缩放
    private int row;//行数
    private int column;//列数
    private int verSpacing=5;//竖直间距
    private int horSpacing=5;//水平间距
    private String screenName = "";//荧幕名称
    private int overViewHeight;//概览图高度
    private int overViewWidth;//概览图宽度
    private float startX=betweenSpacing;
    private float startY=130;
    private float endX=1000;
    private float endY=800;

    public SeatOverView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        sale = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_seat_sale);
        sold = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_seat_sold);
        selected = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_seat_selected);
//        sale=Bitmap.createScaledBitmap(sale,seatSize,seatSize,false);
//        sold=Bitmap.createScaledBitmap(sold,seatSize,seatSize,false);
//        selected=Bitmap.createScaledBitmap(selected,seatSize,seatSize,false);
        Log.d(TAG, "onDraw: "+"高，宽："+width+"/"+height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        paint_seat=new Paint();
        paint_seat.setAntiAlias(false);
//        Log.d(TAG, "mesure: "+"高，宽："+width+"/"+height);
        setMeasuredDimension(width,800);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d(TAG, "onDraw: "+"高，宽："+width+"/"+height);
        if (row <= 0 || column == 0) {
            return;
        }
        Paint paint1=new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setAntiAlias(true);//设置抗锯齿
        paint1.setStyle(Paint.Style.STROKE);//实心
        paint1.setStrokeWidth(5);//线条粗细

        Paint paint2=new Paint();
        paint2.setColor(Color.RED);
        paint2.setAntiAlias(true);//设置抗锯齿
        paint2.setStyle(Paint.Style.STROKE);//实心
        paint2.setStrokeWidth(200);//线条粗细

        canvas.drawLine(0,0,width,0,paint2);
//        endY=betweenSpacing+((seatSize+5)*column);
//        endX=betweenSpacing+((seatSize+5)*row);
        canvas.drawLine(0,startY-30,endX,startY-30,paint1);
        canvas.drawLine(0,endY,endX,endY,paint1);
        canvas.drawLine(0,startY-30,0,endY,paint1);
        canvas.drawLine(endX,startY-30,endX,endY,paint1);
        for (int x=0;x<row;x++){
            for (int y=0;y<column;y++){
                Map<String,String> map=mDatas.get(x*column+y);
                if (map.get("active").equals("0")){

                }else {
                    if (map.get("active2").equals("0")){
                        canvas.drawBitmap(sale,startX+y*seatSize+5,startY+x*seatSize+5,paint_seat);
                    }else {
                        canvas.drawBitmap(sold,startX+y*seatSize+5,startY+x*seatSize+5,paint_seat);
                    }

                }

            }
        }

    }
    public void initData(int row, int column, List<Map<String,String>> maps){
        this.row=row;
        this.column=column;
        this.mDatas=maps;
        seatSize=600/row-5;
        sale=Bitmap.createScaledBitmap(sale,seatSize,seatSize,false);
        sold=Bitmap.createScaledBitmap(sold,seatSize,seatSize,false);
        selected=Bitmap.createScaledBitmap(selected,seatSize,seatSize,false);
//更新
       invalidate();
    }
}
