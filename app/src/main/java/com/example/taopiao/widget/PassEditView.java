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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.example.taopiao.R;
import com.example.taopiao.mvp.activity.SnackBuyActivity;

import java.util.logging.Handler;

public class PassEditView extends View{
     private SnackBuyActivity context;
     private PopupWindow popupWindow;
     private PopupWindow popupWindow2;
     private Paint paint_pass;//画密码的画笔
     Bitmap ps_insert,dis_insert;
      private static final int psLength=6;//密码框个数6个
      private float width;
      private float height;
      private static int pSize=100; //密码框高宽一致
      private int lineSize=5;//线条大小
      private float startX;
      private float startY=200;
       private float endX;
       private float endY;
       //记录6个密码
    String[] MP=new String[]{"","","","","",""};
    //当前操作位置
    private static int pointer;
       //自定义键盘
       private static int kSize=250; //键盘单元格宽度
       private static int hSize=150;//键盘大暖阁高度
       private float startKY;
       private float startKX;
       private float endKY;
       private float endKX;
      //
      private float pToScreenY;
      private float pToScreenX;


    public PassEditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        pointer=0;
        ps_insert=BitmapFactory.decodeResource(getResources(),R.drawable.ic_my_ps);
        dis_insert=BitmapFactory.decodeResource(getResources(),R.drawable.ic_my_ps2);
        ps_insert=Bitmap.createScaledBitmap(ps_insert,100,100,true);
        dis_insert=Bitmap.createScaledBitmap(dis_insert,100,100,true);
        paint_pass=new Paint();
        paint_pass.setAntiAlias(false);

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

           int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            width = MeasureSpec.getSize(widthMeasureSpec);
//           height = MeasureSpec.getSize(heightMeasureSpec);
           //
          Paint paint=new Paint();
          paint.setColor(Color.BLACK);
          paint.setAntiAlias(false);
           startX=(width-pSize*6-lineSize*7)/2;
           endY=200+lineSize+pSize;
           endX=startX+6*lineSize+6*pSize;
           //
           startKX=(width-3*kSize-lineSize*4)/2;
           startKY=endY+300;
           endKX=startKX+3*kSize+3*lineSize;
           endKY=startKY+4*hSize+4*lineSize;
           pToScreenX=getX();
           pToScreenY=getY();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint1=new Paint();
        paint1.setColor(Color.BLACK);
      paint1.setAntiAlias(true);//设置抗锯齿
     paint1.setStyle(Paint.Style.STROKE);//实心
     paint1.setStrokeWidth(2);//线条粗细
        //画上边框
      canvas.drawLine(startX,startY,endX,startY,paint1);
      //画下边线
        canvas.drawLine(startX,endY,endX,endY,paint1);
        //画第一个竖线，最后一个竖线
        canvas.drawLine(startX,startY,startX,endY,paint1);
        canvas.drawLine(endX,startY,endX,endY,paint1);
         for (int i=1;i<6;i++){
              canvas.drawLine(startX+(pSize+lineSize)*i,startY,startX+(pSize+lineSize)*i,endY,paint1);
         }
                  //绘图
                  //绘制横线
                 for (int r=0;r<5;r++){
                    canvas.drawLine(startKX,startKY+r*hSize+lineSize*r,endKX,startKY+hSize*r+r*lineSize,paint1);
                 }
                 //绘制竖线
               for (int c=0;c<4;c++){
                    canvas.drawLine(startKX+c*kSize+c*lineSize,startKY,startKX+c*kSize+c*lineSize,endKY,paint1);
               }


               //绘制数字
             Paint paint2=new Paint();
              paint2.setAntiAlias(false);
              paint2.setColor(Color.BLACK);
              paint2.setStyle(Paint.Style.STROKE);
              paint2.setStrokeWidth(10);
              int p=0;
              for (int r1=0;r1<4;r1++){

                  for (int c1=0;c1<3;c1++){
                       RectF rectF=new RectF(startKX+c1*kSize+c1*lineSize+lineSize,startKY+lineSize*r1+r1*hSize+lineSize,
                                       startKX+c1*kSize+c1*lineSize+lineSize+kSize,startKY+lineSize*r1+r1*hSize+lineSize+hSize
                               ) ;
                        ++p;
                       float sx= startKX+c1*kSize+c1*lineSize+lineSize;
                       float sy= startKY+lineSize*r1+r1*hSize+lineSize;
                       float ex= startKX+c1*kSize+c1*lineSize+lineSize+kSize;
                       float ey=  startKY+lineSize*r1+r1*hSize+lineSize+hSize;
                       float t_sx=sx+100+25;
                       float t_sy=sy+50+25 ;
                       String s= String.valueOf(c1+r1*3+1);
                                 paint2.setTextSize(50);
                       if (p<10) {

                               canvas.drawPosText(s,new float[]{t_sx,t_sy},paint2);
                       }
                       if (p==10) {
                               canvas.drawPosText(".",new float[]{t_sx,t_sy},paint2);
                       }
                         if (p==11) {                                                               
                           canvas.drawPosText("0",new float[]{t_sx,t_sy},paint2);
                         }
                              if (p==12) {
                                canvas.drawPosText("x",new float[]{t_sx,t_sy},paint2);
                              }
                        
                  }

              }
//              **********************

      drawPs(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

         switch (event.getAction()){
             case MotionEvent.ACTION_MOVE:
             case MotionEvent.ACTION_DOWN:
                 float x=event.getX();
                 float y=event.getY();


             for (int i=0;i<4;i++){
                     for (int k=0;k<3;k++){
                         float sx= startKX+k*kSize+k*lineSize+lineSize;
                         float sy= startKY+lineSize*i+i*hSize+lineSize;
                         float ex= startKX+k*kSize+k*lineSize+lineSize+kSize;
                         float ey=  startKY+lineSize*i+i*hSize+lineSize+hSize;
                         int n=i*3+k+1;
                         if (x>sx&&x<ex&&y>sy&&y<ey){

                             Log.d("你点击的区域是", String.valueOf(n) + "操作位置是:" + pointer);

                             if (n<10&&pointer<6){
                                 MP[pointer]=String.valueOf(n);

                                 ++pointer;
                                invalidate();
                                cummit();
                             }else if(n==10&&pointer<6){
                                 MP[pointer]=".";
                                 ++pointer;
                                 invalidate();

                                 cummit();
                             }else if (n==11&&pointer<6){
                                 MP[pointer]="0";
                                 ++pointer;
                                 invalidate();
                                 cummit();
                             }else if (n==12&&pointer>0){
                                 --pointer;
                                 MP[pointer]="";
                                 invalidate();

                             }
                             cummit();
                             break;
                         }
                     }
                 }
//                 if (x>=startKX&&x<=endKX&&y>=startKY&&y<=endKY){
//                     return true;
//                 }

              break;

         }
        return super.onTouchEvent(event);
    }
    public void cummit(){
        String s="";
      for (int i=0;i<MP.length;i++){
          s=s+MP[i];
      }
      if (s.length()==6){
            popupWindow.dismiss();
            popupWindow2.dismiss();
          SnackBuyActivity.getInstance().doOrderSnacks();
      }
        Log.d("------提交----",s+"长度"+s.length());

    }
    public int getLength(){
        String s="";
        for (int i=0;i<MP.length;i++){
            s=s+MP[i];
        }
        return s.length();
    }
    public void drawPs(Canvas canvas){
                 Log.d("pointer:",String.valueOf(pointer));
                 switch (pointer){
                     case 0:
                         canvas.drawBitmap(dis_insert,startX,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+100,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+200,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+300,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+400,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+500,startY,paint_pass);
                         Log.d("-----0---","pointer"+pointer);
                         break;
                     case 1:
                         canvas.drawBitmap(ps_insert,startX,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+100,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+200,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+300,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+400,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+500,startY,paint_pass);

                         Log.d("-----1---","pointer"+pointer);
                         break;
                     case 2:
                         canvas.drawBitmap(ps_insert,startX,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+100,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+200,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+300,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+400,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+500,startY,paint_pass);
                         Log.d("-----2---","pointer"+pointer);
                         break;
                     case 3:
                         canvas.drawBitmap(ps_insert,startX,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+100,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+200,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+300,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+400,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+500,startY,paint_pass);
                         Log.d("-----2---","pointer"+pointer);
                         break;
                     case 4:
                         canvas.drawBitmap(ps_insert,startX,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+100,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+200,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+300,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+400,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+500,startY,paint_pass);
                         Log.d("-----3---","pointer"+pointer);
                         break;
                     case 5:
                         canvas.drawBitmap(ps_insert,startX,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+100,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+200,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+300,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+400,startY,paint_pass);
                         canvas.drawBitmap(dis_insert,startX+500,startY,paint_pass);
                         Log.d("-----4---","pointer"+pointer);
                         break;
                     case 6:
                         canvas.drawBitmap(ps_insert,startX,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+100,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+200,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+300,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+400,startY,paint_pass);
                         canvas.drawBitmap(ps_insert,startX+500,startY,paint_pass);
                         Log.d("-----5---","pointer"+pointer);
                         break;

                 }

    }

    public void setPopupWindow(PopupWindow popupWindow,PopupWindow popupWindow2) {
        this.popupWindow = popupWindow;
        this.popupWindow2=popupWindow2;

    }
}
