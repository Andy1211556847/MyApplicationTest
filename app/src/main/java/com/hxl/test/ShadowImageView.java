package com.hxl.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huaxianlian on 2017/7/12
 */

public class ShadowImageView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;

    private int mRadius = 1,mDx = 10,mDy = 10;

    public ShadowImageView(Context context) {
        this(context,null);
    }

    public ShadowImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShadowImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    @TargetApi(21)
    public ShadowImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        setLayerType( LAYER_TYPE_SOFTWARE , null);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.shipin_songli);
    }

    public void changeRadius() {
        mRadius+=3;
        postInvalidate();
    }
    public void changeRadius1() {
        mRadius-=3;
        postInvalidate();
    }

    public void changeDx() {
        mDx+=3;
        postInvalidate();
    }
    public void changeDx1() {
        mDx-=3;
        postInvalidate();
    }

    public void changeDy() {
        mDy+=3;
        postInvalidate();
    }

    public void changeDy1() {
        mDy-=3;
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShadowLayer(mRadius, mDx, mDy, Color.MAGENTA);

        canvas.drawText("启舰大SB",100,100,mPaint);

        canvas.drawCircle(200,200,50,mPaint);

        canvas.drawBitmap(mBitmap,null,new Rect(200,0,mBitmap.getWidth(),mBitmap.getHeight()),mPaint);



    }
}
