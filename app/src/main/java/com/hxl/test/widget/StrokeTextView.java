
package com.hxl.test.widget;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hxl.test.R;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class StrokeTextView extends TextView {

    private int colorSet[] = {0xFFF77D7D,0xFFF185C1,0xFFA685F1,0xFF7484EB,0xFF69C1B5,0xFF84DD88,0xFFE1D37D,0xFFECA576};
    private int mStrokeColor;
    private int mStrokeWidth;
    private boolean mShadow;
    private int mShadowRadius;

    public StrokeTextView(Context context) {
        this(context, (AttributeSet)null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mStrokeColor = 0;
        this.mStrokeWidth = 0;
        this.init(context, attrs);
    }

    public int getStrokeColor() {
        return this.mStrokeColor;
    }

    public void setStrokeColor(int color) {
        this.mStrokeColor = color;
        this.invalidate();
    }

    public void setStrokeColorResource( int resId) {
        this.mStrokeColor = this.getContext().getResources().getColor(resId);
        this.invalidate();
    }

    public int getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public void setShadow(boolean shadow){
        mShadow = shadow;
        this.invalidate();
    }
    public void setStrokeWidth(int width) {
        this.mStrokeWidth = width;
        this.invalidate();
    }
    public void setShadowRadius(int radius){
        this.mShadowRadius = radius;
        this.invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setStrokeWidthResource(int resId) {
        this.mStrokeColor = this.getContext().getResources().getDimensionPixelSize(resId);
        this.invalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        if(attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
            mStrokeWidth = ta.getDimensionPixelSize(R.styleable.StrokeTextView_strokeWidth,0);
            mStrokeColor = ta.getColor(R.styleable.StrokeTextView_strokeColor, Color.TRANSPARENT);
            mShadow = ta.getBoolean(R.styleable.StrokeTextView_shadow,true);
            mShadowRadius = ta.getDimensionPixelSize(R.styleable.StrokeTextView_shadowRadius,10);
            ta.recycle();
        }
        if(!isHardwareAccelerated()){
//            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }
    int width,height;
    protected void onDraw(Canvas canvas) {
        width = getWidth();
        height = getHeight();
        if(this.mStrokeWidth > 0) {
            TextPaint textPaint = this.getPaint();
            int oldColor = this.getCurrentTextColor();
            float oldStrockeWidth = textPaint.getStrokeWidth();
            Style oldStyle = textPaint.getStyle();

            boolean fakeBoldText = textPaint.isFakeBoldText();
            this.setTextColorUseReflection(this.mStrokeColor);
            textPaint.setStrokeWidth((float)this.mStrokeWidth);

            textPaint.setStyle(Style.STROKE);
            textPaint.setFakeBoldText(true);
            super.onDraw(canvas);
            if(mShadow){
                textPaint.setShadowLayer(mShadowRadius,0,0,mStrokeColor);
            }
            this.setTextColorUseReflection(oldColor);
            textPaint.setStrokeWidth(oldStrockeWidth);
            textPaint.setStyle(oldStyle);
            textPaint.setFakeBoldText(fakeBoldText);

            Paint paint = new Paint();
            paint.setStrokeCap(Paint.Cap.ROUND);//将画笔设置为圆角
            canvas.rotate(-90, getWidth() / 2, getHeight() / 2);
            paint.setStrokeWidth(20);
            paint.setStyle(Paint.Style.STROKE);
            paint.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2, colorSet, null));
            float doughnutWidth = Math.min(width, height) / 2 * 0.15f;
            RectF rectF = new RectF((width > height ? Math.abs(width - height) / 2 : 0) + doughnutWidth / 2, (height > width ? Math.abs(height - width) / 2 : 0) + doughnutWidth / 2, width - (width > height ? Math.abs(width - height) / 2 : 0) - doughnutWidth / 2, height - (height > width ? Math.abs(height - width) / 2 : 0) - doughnutWidth / 2);

            canvas.drawArc(rectF, 0, 180, false, paint);
        }

        super.onDraw(canvas);
    }

    private void setTextColorUseReflection(int color) {
        try {
            Field textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.setInt(this, color);
            textColorField.setAccessible(false);
        } catch (Exception var4) {
            ;
        }

        this.getPaint().setColor(color);
    }
}
