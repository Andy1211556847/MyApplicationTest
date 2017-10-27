package com.hxl.test.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by huaxianlian on 2017/5/20
 */

public class InnerLinearLayout extends LinearLayout{
    public InnerLinearLayout(Context context) {
        super(context);
    }

    public InnerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public InnerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("InnerLinearLayout dispatchTouchEvent = DOWN  ");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("InnerLinearLayout dispatchTouchEvent = MOVE  ");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("InnerLinearLayout dispatchTouchEvent = UP  ");
                break;
        }

        boolean result = super.dispatchTouchEvent(ev);

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("InnerLinearLayout dispatchTouchEvent = DOWN  结果 = "+result);
                break;
            case MotionEvent.ACTION_MOVE:
                result = false;
                System.out.println("InnerLinearLayout dispatchTouchEvent = MOVE  结果 = "+result);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("InnerLinearLayout dispatchTouchEvent = UP  结果 = "+result);
                break;
        }
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("InnerLinearLayout onInterceptTouchEvent = DOWN  ");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("InnerLinearLayout onInterceptTouchEvent = MOVE  ");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("InnerLinearLayout onInterceptTouchEvent = UP  ");
                break;
        }
        boolean result = false;

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                result = false;
                System.out.println("InnerLinearLayout onInterceptTouchEvent = DOWN  结果 = "+result);
                break;
            case MotionEvent.ACTION_MOVE:
//                result = false;
                System.out.println("InnerLinearLayout onInterceptTouchEvent = MOVE  结果 = "+result);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("InnerLinearLayout onInterceptTouchEvent = UP  结果 = "+result);
                break;
        }
        return result;
    }

    int count = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("InnerLinearLayout onTouchEvent = DOWN  ");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("InnerLinearLayout onTouchEvent = MOVE  ");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("InnerLinearLayout onTouchEvent = UP  ");
                break;
        }
        boolean result = true;

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                System.out.println("InnerLinearLayout onTouchEvent = DOWN  结果 = "+result);
                break;
            case MotionEvent.ACTION_MOVE:

                result = false;
                System.out.println("InnerLinearLayout onTouchEvent = MOVE  结果 = "+result);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("InnerLinearLayout onTouchEvent = UP  结果 = "+result);
                break;
        }
        return result;
    }
}
