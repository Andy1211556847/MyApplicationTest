package com.hxl.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hxl.test.util.Path;
import com.hxl.test.widget.CustomView;
import com.hxl.test.widget.RoundFrameLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by huaxianlian on 2017/8/9
 */

public class TestActivity extends Activity{



    private File mYuvFile;

    private LinearLayout mLayoutRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_channel);
        mLayoutRoot = (LinearLayout) findViewById(R.id.layout_content);



        for(int i = 0;i<RoundFrameLayout.sModes.length;i++){

            RoundFrameLayout frameLayout = new RoundFrameLayout(this);
            LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(500,500);
            frameLayout.setLayoutParams(frameParams);

            ImageView imageView = new ImageView(this);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1,-1);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.mipmap.timg_1);

//            imageView.setXfermode(RoundFrameLayout.sModes[i]);

            frameLayout.addView(imageView);
            mLayoutRoot.addView(frameLayout);


            frameLayout.setXfermode(RoundFrameLayout.sModes[i]);
        }


        mYuvFile = new File(Path.getStorageRootPath() + "yuvData.txt");

        new Thread(){
            @Override
            public void run() {
                parserYUV();
            }
        }.start();



    }

    private void parserYUV(){
        try {

            int width = 640;
            int height = 480;

            int[] colors = new int[width * height];
            int index = 0;

            FileInputStream fos = new FileInputStream(mYuvFile);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();


            byte[] buff = new byte[1024];
            int len = 0;
            while((len = fos.read(buff)) != -1){
                bos.write(buff,0,len);
            }

            byte[] data = bos.toByteArray();

            

            colors = NV21ToRGB(data,width,height);

            final Bitmap bitmap = Bitmap.createBitmap(colors, width, height, Bitmap.Config.ARGB_8888);

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ImageView viewById = (ImageView) findViewById(R.id.imageView);
//                    viewById.setImageBitmap(bitmap);
//                }
//            });


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private static class RGB{
        public int r, g, b;
    }
    private static int COLOR_R = 0;
    private static int COLOR_G = 1;
    private static int COLOR_B = 2;

    //NV21是YUV420格式，排列是(Y), (VU)，是2 plane
    public static int[] NV21ToRGB(byte[] src, int width, int height){
        int numOfPixel = width * height;
        int positionOfV = numOfPixel;
        int[] rgb = new int[numOfPixel*3];


        int[] colors = new int[width * height];
        int colorIndex = 0;

        for(int i=0; i<height; i++){
            int startY = i*width;
            int step = i/2*width;
            int startV = positionOfV + step;
            for(int j = 0; j < width; j++){
                int Y = startY + j;
                int V = startV + j/2;
                int U = V + 1;
                int index = Y*3;
                RGB tmp = yuvTorgb(src[Y], src[U], src[V]);
//                RGB tmp = yuvTorgb(src[Y], (byte)0, (byte)0);
                rgb[index+COLOR_R] = tmp.r;
                rgb[index+ COLOR_G] = tmp.g;
                rgb[index+ COLOR_B] = tmp.b;


                if(colorIndex < colors.length){
                    colors[colorIndex] = Color.rgb(tmp.r,tmp.g,tmp.b);
                }

                colorIndex ++;
            }
        }
        return colors;
    }

    //NV12是YUV420格式，排列是(Y), (UV)，是2 plane
    public static int[] NV12ToRGB(byte[] src, int width, int height){
        int numOfPixel = width * height;
        int positionOfU = numOfPixel;
        int[] rgb = new int[numOfPixel*3];

        for(int i=0; i<height; i++){
            int startY = i*width;
            int step = i/2*width;
            int startU = positionOfU + step;
            for(int j = 0; j < width; j++){
                int Y = startY + j;
                int U = startU + j/2;
                int V = U + 1;
                int index = Y*3;
                RGB tmp = yuvTorgb(src[Y], src[U], src[V]);
                rgb[index+COLOR_R] = tmp.r;
                rgb[index+COLOR_G] = tmp.g;
                rgb[index+COLOR_B] = tmp.b;
            }
        }
        return rgb;
    }

    private static RGB yuvTorgb(byte Y, byte U, byte V){
        RGB rgb = new RGB();
        rgb.r = (int)((Y&0xff) + 1.732446 * ((U&0xff)-128));
        rgb.g = (int)((Y&0xff) - 0.698001 * ((U&0xff)-128) - 0.703125*((V&0xff)-128));
        rgb.b = (int)((Y&0xff) + 1.370705 * ((V&0xff)-128));

//        rgb.r   = ((Y << 8) + ((V << 8) + (V << 5) + (V << 2))) >> 8;
//        rgb.g = ((Y << 8) - ((U << 6) + (U << 5) + (U << 2)) - ((V << 7) + (V << 4) + (V << 2) + V)) >> 8;
//        rgb.b = ((Y << 8) + (U << 9) + (U << 3)) >> 8;


        return rgb;
    }
}
