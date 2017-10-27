package com.hxl.test.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by huaxianlian on 2017/5/23
 */

public class CustomListView extends ListView {
    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomListView dispatchTouchEvent = DOWN  ");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomListView dispatchTouchEvent = MOVE  ");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomListView dispatchTouchEvent = UP  ");
                break;
        }

        boolean result = super.dispatchTouchEvent(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomListView dispatchTouchEvent = DOWN  结果 = " + result);
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomListView dispatchTouchEvent = MOVE  结果 = " + result);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomListView dispatchTouchEvent = UP  结果 = " + result);
                break;
        }
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomListView onInterceptTouchEvent = DOWN  ");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomListView onInterceptTouchEvent = MOVE  ");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomListView onInterceptTouchEvent = UP  ");
                break;
        }
        boolean result = super.onInterceptTouchEvent(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomListView onInterceptTouchEvent = DOWN  结果 = " + result);
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomListView onInterceptTouchEvent = MOVE  结果 = " + result);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomListView onInterceptTouchEvent = UP  结果 = " + result);
                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomListView onTouchEvent = DOWN  ");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomListView onTouchEvent = MOVE  ");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomListView onTouchEvent = UP  ");
                break;
        }
        boolean result = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CustomListView onTouchEvent = DOWN  结果 = " + result);
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CustomListView onTouchEvent = MOVE  结果 = " + result);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CustomListView onTouchEvent = UP  结果 = " + result);
                break;
        }
        return result;
    }
}
