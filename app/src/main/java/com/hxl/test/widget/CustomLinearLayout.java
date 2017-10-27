package com.hxl.test.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 *
 * Android 事件分发总结
 * 由Activity接收到事件,首先是Down事件,Activity把Down事件传递给ViewGroup的dispatchToucHEvent() 方法,进行事件分发
 * 该方法首先调用自己的onInterceptTouchEvent()方法,判断当前是否需要拦截该事件,
 *
 * 1:拦截,后续子View将不再接收到事件,将改事件交给自己的onTouchEvent()
 * 方法,如果该方法返回false,则下次事件将不再传递过来,如果返回了true,则代表当前View要消耗次事件,以后的事件都会传递给这个View.
 *
 * 2:不拦截,则交给自己的下一级子View,调用这个子View的ViewGroup的dispatchToucHEvent() 方法,继续事件分发拦截重复步骤
 *
 *
 * 如果一个事件经过一层层的循环,如果一个子View的onTouchEvent() 方法都返回了false,这个事件再传递上一级View的onTouchEevent()
 * 方法,层层传递,知道传递到事件的源头,没有谁要消耗这个事件 则move事件将不再分发
 *
 * 如果父类把子View的事件拦截了,而子View又想消耗这个事件,此时子View 可以调用 在 dispatchTouchEvent() 方法调用 getParent().requestDisallowInterceptTouchEvent(true);
 * 请求父类不拦截,需要注意的事,要想子类调用这个方法,在Down事件的时候,父类不能拦截该子View的事件(move事件的可以拦截)
 *
 * setOnTouchListener 该方法类似于代理本地OnTouchEvent() 方法 如果该方法返回了false 则继续交给OnTouchEvent() ,如果返回了true,则不再交给OnTouchEvent()方法
 *
 *
 *
 * Created by huaxianlian on 2017/5/20
 */

public class CustomLinearLayout extends LinearLayout{
    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomLinearLayout dispatchTouchEvent = DOWN  ");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomLinearLayout dispatchTouchEvent = MOVE  ");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomLinearLayout dispatchTouchEvent = UP  ");
                break;
        }

        boolean result = super.dispatchTouchEvent(ev);

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomLinearLayout dispatchTouchEvent = DOWN  结果 = "+result);
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomLinearLayout dispatchTouchEvent = MOVE  结果 = "+result);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomLinearLayout dispatchTouchEvent = UP  结果 = "+result);
                break;
        }
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomLinearLayout onInterceptTouchEvent = DOWN  ");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomLinearLayout onInterceptTouchEvent = MOVE  ");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomLinearLayout onInterceptTouchEvent = UP  ");
                break;
        }
        boolean result = super.onInterceptTouchEvent(ev);

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomLinearLayout onInterceptTouchEvent = DOWN  结果 = "+result);
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomLinearLayout onInterceptTouchEvent = MOVE  结果 = "+result);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomLinearLayout onInterceptTouchEvent = UP  结果 = "+result);
                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomLinearLayout onTouchEvent = DOWN  ");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomLinearLayout onTouchEvent = MOVE  ");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomLinearLayout onTouchEvent = UP  ");
                break;
        }
        boolean result = super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomLinearLayout onTouchEvent = DOWN  结果 = "+result);
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomLinearLayout onTouchEvent = MOVE  结果 = "+result);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomLinearLayout onTouchEvent = UP  结果 = "+result);
                break;
        }
        return result;
    }
}
