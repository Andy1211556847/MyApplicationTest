package com.hxl.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 *
 * 画廊ViewPager展示
 * Created by huaxianlian on 2017/8/4
 */

public class GalleryViewPagerActivity extends Activity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery_viewpager);

        //因为设置了android:clipChildren="false" 会导致viewpager控件外不能触摸,所以讲viewpager外层layout传递给viewpager
        findViewById(R.id.layout_pager).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });


        mViewPager = (ViewPager) findViewById(R.id.pagerView);
        //item之间的间距,这个距离应该是个正数,因为这里用到了缩放效果,所以可以使负数
        mViewPager.setPageMargin(- 20);
        //页面缓存数据
        mViewPager.setOffscreenPageLimit(3);


        mViewPager.setPageTransformer(true,new ScaleAlphaPageTransformer());
        mViewPager.setAdapter(new DataAdapter());
        mViewPager.setCurrentItem(3);

    }

    private class ScaleAlphaPageTransformer implements ViewPager.PageTransformer{

        private float MINALPHA = 0.7f;
        private float MINSCALE = 0.3f;
        private float MAXROTATIONY = 20;
        @Override
        public void transformPage(View page, float position) {

            page.setPivotX(page.getWidth() / 2);
            page.setPivotY(page.getHeight() / 2);

            //如果超出左右两边则设置忽略不处理,设置最小值
            if (position < -1 || position > 1) {
                page.setAlpha(1 - MINALPHA);
                page.setScaleX(1 - MINSCALE);
                page.setScaleY(1 - MINSCALE);

                page.setRotationY(position > 0 ? - MAXROTATIONY : MAXROTATIONY);
            }

            else {

                /*
                    这里加了三种效果 透明,缩放,与旋转,可根据自己需求添加
                 */

                /*
                 position:由左到右值的变化 0 到 -1
                 position:由右到左值的变化 0 到 1
                 所以变化系数 取绝对值就行
                 */

                float value = Math.abs(position);

                page.setAlpha(1 - MINALPHA * value);

                page.setScaleX(1 - MINSCALE * value);
                page.setScaleY(1 - MINSCALE * value);


                page.setRotationY(position >= 0 ? - MAXROTATIONY * value : MAXROTATIONY * value);
            }
        }
    }


    private class DataAdapter extends PagerAdapter {

        private ArrayList<String> mDatas = new ArrayList<>();

        DataAdapter(){
            for (int i = 0; i < 20 ;i++){
                mDatas.add("");
            }
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GalleryViewPagerActivity.this);
            ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
            imageView.setLayoutParams(layoutParams);


            if(position % 4 == 0){
                imageView.setImageResource(R.mipmap.timg);
            }
            else if(position % 3 == 0){
                imageView.setImageResource(R.mipmap.timg_1);
            }
            else if(position % 2 == 0){
                imageView.setImageResource(R.mipmap.timg_2);
            }
            else{
                imageView.setImageResource(R.mipmap.timg_3);
            }

            container.addView(imageView);

            imageView.setTag(""+position);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object != null) {
                container.removeView((View) object);
            }
        }
    }
}
