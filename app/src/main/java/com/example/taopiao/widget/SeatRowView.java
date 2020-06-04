package com.example.taopiao.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

public class SeatRowView extends View {
    private static final String TAG="Select seat row view------";
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
    private int left=30;
    private int seatSize=50;//座位大小（高度=宽度）最小50
    boolean firstScale = true;//是否第一次缩放
    private int row;//行数
    private int column;//列数
    private int verSpacing=63;//竖直间距
    private int horSpacing=10;//水平间距

    private int overViewHeight;//概览图高度
    private int overViewWidth;//概览图宽度
    private float startX=betweenSpacing;
    private float startY=50;
    private float endX=0;
    private float endY=800;
    public SeatRowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint1=new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setAntiAlias(true);//设置抗锯齿
        paint1.setStyle(Paint.Style.STROKE);//实心
        paint1.setStrokeWidth(3);//线条粗细
        paint1.setTextSize(24);
//        canvas.drawText(String.valueOf(1),10,100,paint1);
        Log.d(TAG, "onDraw: rowview"+width+"/"+height);
        Log.d(TAG, "onDraw: row--"+row);
        for (int i=0;i<row;i++){

            int v=i+1;
            Log.d(TAG, "onDraw: v--"+startY+(seatSize+verSpacing)*(i)+seatSize);
            canvas.drawText(String.valueOf(v),45,startY+(seatSize+verSpacing)*(i),paint1);
        }
        super.onDraw(canvas);
    }
    public void initData(int row, int column){
        this.row=row;
        this.column=column;
        invalidate();
    }

}
