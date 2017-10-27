package com.hxl.test;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.hxl.test.util.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;

/**
 * 视频录制
 * Created by Huaxl on 2016/11/2.
 */
public class MediaRecorderActivity extends Activity implements SurfaceHolder.Callback{

    private SurfaceView mSurfaceView;

    private Camera mCamera;
    private int mCameraId;


    private File mYuvFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        setContentView(R.layout.activity_media_recorders);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        try {
            String storageRootPath = Path.getStorageRootPath();

            File dir = new File(storageRootPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            mYuvFile = new File(storageRootPath + "yuvData.txt");
            if (!mYuvFile.exists()) {

                mYuvFile.createNewFile();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        int n = Camera.getNumberOfCameras();

        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < n; i++) {
            Camera.getCameraInfo(i, info);

            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                mCameraId = i;
                break;
            }
            else if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

                break;
            }
        }

        mSurfaceView.getHolder().addCallback(this);


    }

    //释放摄像头资源
    private void freeCameraResource() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();

            //重新加锁，因为之前为了MediaRecord 能录像解了锁
            mCamera.lock();

            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        freeCameraResource();
    }

    private void openCamera(){
        try {

            freeCameraResource();

            mCamera = Camera.open(mCameraId);


            Camera.Parameters parameters = mCamera.getParameters();
            //方向
            mCamera.setDisplayOrientation(90);

            //设置预览大小
            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
            parameters.setPreviewSize(640, 480);

            parameters.setPreviewFormat(ImageFormat.NV21);

            mCamera.setParameters(parameters);

            mCamera.setPreviewDisplay(mSurfaceView.getHolder());
            mCamera.startPreview();



            mCamera.setPreviewCallback(new Camera.PreviewCallback() {

                int count = 0;
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    if(count <= 9){
                        try {
                            FileOutputStream fos = new FileOutputStream(mYuvFile);

                            fos.write(data);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    count ++;
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        openCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
