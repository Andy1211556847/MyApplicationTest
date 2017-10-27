package com.hxl.test.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by huaxianlian on 2017/9/19
 */

public class RoundFrameLayout extends FrameLayout {

    public static final int DIRECTION_LEFT_TOP = 0;
    public static final int DIRECTION_LEFT_BOTTOM = 1;
    public static final int DIRECTION_RIGHT_TOP = 2;
    public static final int DIRECTION_RIGHT_BOTTOM = 3;

    private Paint mPaint;
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

    public RoundFrameLayout(@NonNull Context context) {
        super(context);
        init(context,null,0);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    @TargetApi(21)
    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){

        setWillNotDraw(false);

        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(Color.MAGENTA);
        mPaint.setTextSize(20);

        mDst = new RectF();
    }

    Xfermode xfermode;

    @Override
    protected void dispatchDraw(Canvas canvas) {


        int saveLayer = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        super.dispatchDraw(canvas);



        mPaint.setXfermode(xfermode);

        Path path = new Path();
        path.setFillType(Path.FillType.WINDING);
        path.addCircle(getWidth()/2,getHeight()/2,getWidth() / 2, Path.Direction.CW);


        canvas.drawPath(path,mPaint);

        canvas.restoreToCount(saveLayer);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    public void setXfermode(Xfermode sMode) {
        xfermode = sMode;
    }
}
