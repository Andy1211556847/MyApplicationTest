package com.hxl.test;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.provider.Contacts;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hxl.test.util.UIUtils;

/**
 * Created by huaxianlian on 2017/8/4
 */

public class PathAnimaActivity extends Activity {

    FrameLayout mLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_anim);

//        startActivity(new Intent(PathAnimaActivity.this,TestActivity.class));


        mLayout = (FrameLayout) findViewById(R.id.layout_content);
        PathView pathView = new PathView(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1,-1);
        pathView.setLayoutParams(layoutParams);
        mLayout.addView(pathView,0);





        //定义一个path
        final Path path = new Path();
        path.moveTo(0,0);
        path.cubicTo(UIUtils.dip2px(30),UIUtils.dip2px(110),
                UIUtils.dip2px(100),UIUtils.dip2px(50),
                UIUtils.dip2px(120),UIUtils.dip2px(110));

        //绘制path路径
        pathView.setPath(path);

        final TextView test = (TextView) findViewById(R.id.txt);






        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(test,path);
            }
        });


    }

    //path动画
    private void start(final View view, Path path){
        final PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path,false);
        ValueAnimator animator = ValueAnimator.ofFloat(0,pathMeasure.getLength());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float length = (float) animation.getAnimatedValue();

                float[] point = new float[2];
                pathMeasure.getPosTan(length,point,null);

                view.setTranslationX(point[0]);
                view.setTranslationY(point[1]);
            }
        });

        animator.setDuration(1000);
        animator.start();
    }

    private class PathView extends View {

        Paint mPaint;

        Path mPath;
        public PathView(Context context) {
            super(context);

            mPaint = new Paint();
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);

            mPaint.setColor(Color.BLUE);
            mPaint.setStyle(Paint.Style.STROKE);

            mPaint.setStrokeWidth(5);
        }

        public void setPath(Path path){
            this.mPath = path;

            invalidate();
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if(mPath != null){
                canvas.drawPath(mPath,mPaint);
            }

        }
    }
}
