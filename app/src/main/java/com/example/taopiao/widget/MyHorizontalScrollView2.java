package com.example.taopiao.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.taopiao.adapter.MovieCrewAdapter;
import com.example.taopiao.adapter.ScheduleAdapter;

import java.util.HashMap;
import java.util.Map;

public class MyHorizontalScrollView2 extends HorizontalScrollView implements View.OnClickListener {


    public interface CurrentImageChangeListener
    {
        void onCurrentImgChanged(int position, View viewIndicator);
    }


    public interface OnItemClickListener
    {
        void onClick(View view, int pos);
    }

    private MyHorizontalScrollView.CurrentImageChangeListener mListener;

    private MyHorizontalScrollView.OnItemClickListener mOnClickListener;

    private static final String TAG = "MyHorizontalScrollView2";

    /**
     * HorizontalListView中的LinearLayout
     */
    private LinearLayout mContainer;

    /**
     * 子元素的宽度
     */
    private int mChildWidth;
    /**
     * 子元素的高度
     */
    private int mChildHeight;
    /**
     * 当前最后一张图片的index
     */
    private int mCurrentIndex;
    /**
     * 当前第一张图片的下标
     */
    private int mFristIndex;
    /**
     * 当前第一个View
     */
    private View mFirstView;
    /**
     * 数据适配器
     */
    private ScheduleAdapter mAdapter;
    /**
     * 每屏幕最多显示的个数
     */
    private int mCountOneScreen;
    /**
     * 屏幕的宽度
     */
    private int mScreenWitdh;


    /**
     * 保存View与位置的键值对
     */
    private Map<View, Integer> mViewPos = new HashMap<View, Integer>();

    public MyHorizontalScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获得屏幕宽度
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWitdh = outMetrics.widthPixels;
    }
    //
    public void initDatas(ScheduleAdapter mAdapter) {
        this.mAdapter = mAdapter;
        mContainer = (LinearLayout) getChildAt(0);
        // 获得适配器中第一个View
        final View view = mAdapter.getView(0, null, mContainer);
        mContainer.addView(view);
        // 强制计算当前View的宽和高
        if (mChildWidth == 0 && mChildHeight == 0) {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            mChildHeight = view.getMeasuredHeight();
            mChildWidth = view.getMeasuredWidth();
            Log.e(TAG, view.getMeasuredWidth() + "," + view.getMeasuredHeight());
            mChildHeight = view.getMeasuredHeight();
            // 计算每次加载多少个View
            mCountOneScreen = mScreenWitdh / mChildWidth + 2;

            Log.e(TAG, "mCountOneScreen = " + mCountOneScreen
                    + " ,mChildWidth = " + mChildWidth);


        }
        //初始化第一屏幕的元素
        initFirstScreenChildren(mCountOneScreen);
    }
    public void initFirstScreenChildren(int mCountOneScreen)
    {
        mContainer = (LinearLayout) getChildAt(0);
        mContainer.removeAllViews();
        mViewPos.clear();
//
        int adapter_size=mAdapter.getCount();

        if (adapter_size!=0){
            if (adapter_size<=mCountOneScreen){
                for (int i = 0; i < adapter_size; i++){
                    View view = mAdapter.getView(i, null, mContainer);
                    view.setOnClickListener(this);
                    mContainer.addView(view);
                    mViewPos.put(view, i);
                    mCurrentIndex = i;
                }
            }else {
                for (int i = 0; i < mCountOneScreen; i++)
                {
                    View view = mAdapter.getView(i, null, mContainer);
                    view.setOnClickListener(this);
                    mContainer.addView(view);
                    mViewPos.put(view, i);
                    mCurrentIndex = i;
                }
            }
        }

        if (mListener != null)
        {
            notifyCurrentImgChanged();
        }

    }

    /**
     * 滑动时的回调
     */
    public void notifyCurrentImgChanged()
    {
        //先清除所有的背景色，点击时会设置为蓝色
        for (int i = 0; i < mContainer.getChildCount(); i++)
        {
            mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        mListener.onCurrentImgChanged(mFristIndex, mContainer.getChildAt(0));

    }

    @Override
    public void onClick(View v) {

    }
}