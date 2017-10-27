package com.hxl.test.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

/**
 * 雷达自定义控件改造
 * Created by on 2016/12/15.
 */
public class RadarView extends View {
    private Paint mPaint = new Paint();
    //最大半径
    private float mMaxRadius;
    //圆环个数
    private int mCircleCount = 7;
    //中心圆的半径
    private float mCenterCircleRadius;
    //颜色
    private int mColor = Color.YELLOW;

    private float mAlphaSpecing = 0;
    private float mRaduisSpecing = 0;

    // 是否运行
    private boolean mIsStarting = true;
    private List<Float> mAlphaColors = new ArrayList<>();
    private List<Float> mRadius = new ArrayList<>();


    //是否默认启动
    private boolean mDefaultStart = false;

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public RadarView(Context context) {
        super(context);
    }


    private void initAttrs(Context context, AttributeSet attrs) {
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarView);
//        if (typedArray != null) {
//            float radius = typedArray.getDimension(R.styleable.RadarView_centerRaduis, 0);
//            int circleCount = typedArray.getInteger(R.styleable.RadarView_circleCount, 7);
//            mIsStarting = typedArray.getBoolean(R.styleable.RadarView_start, true);
//            mCenterCircleRadius = radius;
//            mCircleCount = circleCount;
//            mColor = typedArray.getColor(R.styleable.RadarView_circleColor, Color.YELLOW);
//
//            mDefaultStart = mIsStarting;
//
//
//            mPaint.setColor(mColor);
//        }
    }


    private void init() {
        mRadius.clear();
        mAlphaColors.clear();

        mPaint.setColor(mColor);

        mMaxRadius = Math.min(getWidth(), getHeight()) / 2;

        if (mCenterCircleRadius == 0) {
            mCenterCircleRadius = mMaxRadius / (mCircleCount - 1) * 2;
        }

        float alphaSpacing = getFirstColor() / mCircleCount;
        float radiusSpacing = (mMaxRadius - mCenterCircleRadius) / mCircleCount;

        mAlphaSpecing = alphaSpacing / 30;
        mRaduisSpecing = radiusSpacing / 30;

        if(mDefaultStart) {
            for (int i = 0;i<mCircleCount;i++){
                mRadius.add(mMaxRadius - radiusSpacing*i);
                mAlphaColors.add(i * alphaSpacing);
            }
        }else {
            //解决过渡性不好的问题,初始化只添加一个圆环
            mRadius.add(mCenterCircleRadius);
            mAlphaColors.add(200f);
        }
    }


    private float getFirstColor(){
        return 200;
    }
    /***
     * 设置中心圆的半径
     *
     * @param radius
     */
    public void setCenterircleRadius(int radius) {
        this.mCenterCircleRadius = radius;
        init();
    }

    /**
     * 设置圆环的数量
     */
    public void setCircleCount(int count) {
        mCircleCount = count;
        init();
    }

    /**
     * 设置雷达的颜色
     *
     * @param color
     */
    public void setRadarColor(int color) {
        mColor = color;
        mPaint.setColor(color);
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setAlpha(255);
        //绘制中心圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mCenterCircleRadius, mPaint);
        if (mDefaultStart) {
            if (mRadius.size() == 0) {
                init();
            }
        }else {
            if (mIsStarting && mRadius.size() == 0) {
                init();
            }
        }
        drawCircle(canvas);
        if (mIsStarting) {
            //判断第一次启动
            if (mRadius.size() < mCircleCount) {
                float radiusSpacing = (mMaxRadius - mCenterCircleRadius) / mCircleCount;
                if (mRadius.get(mRadius.size() - 1) >= radiusSpacing + mCenterCircleRadius) {
                    mAlphaColors.add(getFirstColor());
                    mRadius.add(mCenterCircleRadius);
                }
            }else {

                boolean isAdd = false;
                float spacing = (mMaxRadius - mCenterCircleRadius) / mCircleCount;
                if ((mRadius.get(0) >= mMaxRadius + spacing || mAlphaColors.get(0) <= 0)) {
                    mAlphaColors.add(getFirstColor());
                    mRadius.add(mCenterCircleRadius);
                    isAdd = true;
                }
                // 同心圆数量达到10个，删除最外层圆
                if (isAdd) {
                    mRadius.remove(0);
                    mAlphaColors.remove(0);
                }
            }
        } else {

        }
        // 刷新界面
        invalidate();
    }

    private void drawCircle(Canvas canvas) {
        // 依次绘制 同心圆
        for (int i = 0; i < mAlphaColors.size(); i++) {
            float alpha = mAlphaColors.get(i);
            // 圆半径
            float radius = mRadius.get(i);
            mPaint.setAlpha((int) alpha);
            // 这个半径决定你想要多大的扩散面积
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);

            // 同心圆扩散,透明度每次 -
            if (alpha > 0) {
                if (alpha - mAlphaSpecing < 0) {
                    mAlphaColors.set(i, 0f);
                } else {
                    mAlphaColors.set(i, alpha - mAlphaSpecing);
                }
            }
            mRadius.set(i, radius + mRaduisSpecing);
        }
    }

    // 执行动画
    public void start() {
        if (mIsStarting) {
            return;
        }
        mIsStarting = true;
        init();
        invalidate();
    }

    // 停止动画
    public void stop() {
        mIsStarting = false;
    }
    // 判断是都在不在执行
    public boolean isStarting() {
        return mIsStarting;
    }
}
