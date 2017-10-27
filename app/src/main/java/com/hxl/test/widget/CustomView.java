package com.hxl.test.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by huaxianlian on 2017/5/20
 */


public class CustomView extends View{
    public static final int DIRECTION_LEFT_TOP = 0;
    public static final int DIRECTION_LEFT_BOTTOM = 1;
    public static final int DIRECTION_RIGHT_TOP = 2;
    public static final int DIRECTION_RIGHT_BOTTOM = 3;

    private Paint mPaint;
    private Paint mClipPaint;
    private RectF mDst;

    private Path mPath;

    private int mDirection = DIRECTION_LEFT_TOP;

    public static final Xfermode[] sModes = {
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    };
    private int imageResource;

    public CustomView(Context context) {
        super(context);
        init(context,null,0);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    @TargetApi(21)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(Color.MAGENTA);


        mClipPaint = new Paint();
        mClipPaint.setDither(true);
        mClipPaint.setAntiAlias(true);
        mClipPaint.setColor(Color.LTGRAY);


        mDst = new RectF();
    }


    Xfermode mXfermode = sModes[5];

    public void setXfermode(Xfermode xfermode){
        mXfermode = xfermode;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int saveLayer = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);


        mPaint.setXfermode(mXfermode);

        //圆
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(getWidth() / 2.5f,getHeight() / 2.5f,getWidth() / 3,mPaint);

        //椭圆
        mPaint.setColor(Color.CYAN);
        canvas.drawOval(new RectF(0,getHeight() / 2.5f,getWidth() / 2.5f,getHeight()),mPaint);

        //矩形
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(getWidth() / 2.5f,getHeight() / 2.5f,getWidth(),getHeight(),mPaint);



        canvas.restoreToCount(saveLayer);



    }

    public void setScaleType(ImageView.ScaleType centerCrop) {

    }

    public void setImageResource(int timg_1) {

    }
}
