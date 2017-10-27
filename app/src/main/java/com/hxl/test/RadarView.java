package com.hxl.test;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

/**
 * 雷达自定义控件改造
 * Created by on 2016/12/15.
 */
public class RadarView extends View {

    private static final int POINT_NUM = 500;
    Paint mRedPaint;
    Paint mBluePaint;

    Paint mPaint;

    private ArrayList<PointF> mPoints = new ArrayList<>();

    public RadarView(Context context) {
        super(context);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(21)
    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){
        if(mRedPaint == null){
            mRedPaint = new Paint();
            mRedPaint.setColor(Color.RED);
            mRedPaint.setDither(true);
            mRedPaint.setAntiAlias(true);

            mBluePaint = new Paint();
            mBluePaint.setColor(Color.BLUE);
            mBluePaint.setDither(true);
            mBluePaint.setAntiAlias(true);

            mPaint = new Paint();
            mPaint.setColor(Color.MAGENTA);
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);

            mPaint.setStrokeWidth(4);
            mPaint.setStyle(Paint.Style.STROKE);


            //添加点
            mPoints.add(new PointF(0,50));
            mPoints.add(new PointF(50,50));
            mPoints.add(new PointF(100,50));
            mPoints.add(new PointF(150,50));
            mPoints.add(new PointF(200,50));
            mPoints.add(new PointF(300,50));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        init();
        //绘制中心圆
        int width = getWidth() ;
        int height = getHeight();

        int rectWidth = 100;
        int rectHeight = 100;


        Path path = new Path();

        int specing = 100;
        path.moveTo(specing,100);
        //二次贝塞尔曲线 参考点  结束点
//        path.quadTo(specing * 2.6f,300,specing * 4,200);

        //三次贝塞尔曲线
        path.cubicTo(specing * 2,300,specing * 3,60,specing * 4,200);

        canvas.drawPath(path,mPaint);



    }
    private ArrayList<PointF> buildBezierPoints() {
        ArrayList<PointF> points = new ArrayList<>();
        int order = mPoints.size() - 1;
        float delta = 1.0f / POINT_NUM;
        for (float t = 0; t <= 1; t += delta) {
            // Bezier点集
            points.add(new PointF(getBezierPointX(order, 0, t), getBezierPointY(order, 0, t)));
        }
        return points;
    }
    //用递归获取贝塞尔曲线点的x轴坐标
    private float getBezierPointX(int n, int position, float t) {
        if (n == 1) {
            return (1 - t) * mPoints.get(position).x + t * mPoints.get(position + 1).x;
        }
        return (1 - t) * getBezierPointX(n - 1, position, t) + t * getBezierPointX(n - 1, position + 1, t);
    }
    //用递归获取贝塞尔曲线点的y轴坐标
    private float getBezierPointY(int n, int position, float t) {
        if (n == 1) {
            return (1 - t) * mPoints.get(position).y + t * mPoints.get(position + 1).y;
        }
        return (1 - t) * getBezierPointY(n - 1, position, t) + t * getBezierPointY(n - 1, position + 1, t);
    }



    private void createPath(){
//        Path path = new Path();
//        for(int i = 0;i<mPoints.size(); i++){
//            Point point = mPoints.get(i);
//            if(i == 0){
//                path.moveTo(point.x,point.y);
//            }
//            else {
//                Point lastPoint = mPoints.get(i - 1);
//                path.cubicTo(lastPoint.x,lastPoint.y,);
//            }
//        }
    }
}
