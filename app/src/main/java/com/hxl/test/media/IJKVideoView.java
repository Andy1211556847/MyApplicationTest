package com.hxl.test.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.constraint.solver.LinearSystem;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;
import tv.danmaku.ijk.media.player.misc.IMediaDataSource;

/**
 * Created by huaxianlian on 2017/9/5
 */

public class IJKVideoView extends SurfaceView implements SurfaceHolder.Callback,IRenderView{

    private String TAG = "IJKVideoView";


    static {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    private IMediaPlayer.OnBufferingUpdateListener mMediaOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

        }
    };


    private IMediaPlayer.OnPreparedListener mMediaOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            if(mAutoPlay){
                start();
            }
        }
    };

    private IMediaPlayer.OnCompletionListener mMediaOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer iMediaPlayer) {

        }
    };


    private IMediaPlayer.OnSeekCompleteListener mMediaOnSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer iMediaPlayer) {

        }
    };

    private IMediaPlayer.OnVideoSizeChangedListener mMediaOnVideoSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
            if(width > 0 && height > 0){
                mMeasureHelper.setVideoSize(width,height);
                requestLayout();
            }
        }
    };


    private IMediaPlayer.OnErrorListener mMediaOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
            return false;
        }
    };

    private IMediaPlayer.OnInfoListener mMediaOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int i1) {

            switch (what) {
                case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    Log.d(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");

                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    Log.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");

                    if(!mAutoPlay){
                        pause();
                    }

                    break;

                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Log.d(TAG, "MEDIA_INFO_BUFFERING_START:");

                    break;

                case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Log.d(TAG, "MEDIA_INFO_BUFFERING_END:");


                    break;

                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                    Log.d(TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: ");
                    break;

                case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                    Log.d(TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                    break;

                case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    Log.d(TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                    break;

                case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                    Log.d(TAG, "MEDIA_INFO_METADATA_UPDATE:");
                    break;

                case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                    Log.d(TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    break;

                case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                    Log.d(TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    break;

                case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:


                    break;
                case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    Log.d(TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");

                    break;
            }


            if (mOnInfoListener != null) {
                mOnInfoListener.onInfo(iMediaPlayer, what, i1);
            }


            return true;
        }
    };


    private IMediaPlayer.OnTimedTextListener mMediaOnTimedTextListener = new IMediaPlayer.OnTimedTextListener() {
        @Override
        public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {

        }
    };




    private MeasureHelper mMeasureHelper;
    private IjkMediaPlayer mMediaPlayer;

    private IMediaPlayer.OnCompletionListener mOnCompletionListener;
    private IMediaPlayer.OnPreparedListener mOnPreparedListener;
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private IMediaPlayer.OnErrorListener mOnErrorListener;
    private IMediaPlayer.OnInfoListener mOnInfoListener;



    private Uri mUri;
    private boolean mAutoPlay;
    private HashMap<String,String> mHeards;




    public IJKVideoView(Context context) {
        super(context);
        init(context,null,0,0);
    }

    public IJKVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0,0);
    }

    public IJKVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr,0);
    }

    @TargetApi(21)
    public IJKVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs,defStyleAttr,defStyleRes);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){

        mMeasureHelper = new MeasureHelper(this);


        getHolder().addCallback(this);
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_NORMAL);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(mMeasureHelper.getMeasuredWidth(), mMeasureHelper.getMeasuredHeight());

    }





    private void initMediaPlay(){

        try {
            boolean usingMediaCodec = true;

            if (usingMediaCodec) {

                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);

                boolean autoRotate = true;
                if (autoRotate) {
                    mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
                }
                else {
                    mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
                }

                boolean handleResolutionChange = true;
                if (handleResolutionChange) {
                    mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
                }
                else {
                    mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 0);
                }
            }

            else {
                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
            }

            //true 声音与画面有点小问题
            boolean usingOpenSLES = false;
            if (usingOpenSLES) {
                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);
            }

            else {
                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
            }

//            String pixelFormat = mSettings.getPixelFormat();
//
//            if (TextUtils.isEmpty(pixelFormat)) {
//                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
//            } else {
//                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", pixelFormat);
//            }


            mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
            mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

            mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);

            mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);

            AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            am.requestAudioFocus(null,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);


            mMediaPlayer.setOnPreparedListener(mMediaOnPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mMediaOnVideoSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mMediaOnCompletionListener);
            mMediaPlayer.setOnErrorListener(mMediaOnErrorListener);
            mMediaPlayer.setOnInfoListener(mMediaOnInfoListener);
            mMediaPlayer.setOnBufferingUpdateListener(mMediaOnBufferingUpdateListener);
            mMediaPlayer.setOnSeekCompleteListener(mMediaOnSeekCompleteListener);
            mMediaPlayer.setOnTimedTextListener(mMediaOnTimedTextListener);



            bindSurfaceHolder();
            mMediaPlayer.setScreenOnWhilePlaying(true);

        } catch (Exception e) {
            e.printStackTrace();

            if(mOnErrorListener != null){
                mOnErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN,0);
            }
        }

    }

    private void bindSurfaceHolder(){
        if(mMediaPlayer != null){
            mMediaPlayer.setDisplay(getHolder());
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // 对外方法
    ///////////////////////////////////////////////////////////////////////////


    public void open(String path,boolean autoPlay){

        open(Uri.parse(path),autoPlay);
    }


    public void open(Uri uri,boolean autoPlay){

        try {

            if (uri == null) {
                return;
            }

            release();

            mMediaPlayer = new IjkMediaPlayer();

            initMediaPlay();

            mUri = uri;
            mAutoPlay = autoPlay;

            mMediaPlayer.setDataSource(getContext(), mUri);
            mMediaPlayer.prepareAsync();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void pause(){

        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
    }


    public void start(){
        if(mMediaPlayer != null){
            mMediaPlayer.start();
        }
    }

    public void stop(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
        }
    }


    public void release(){
        if(mMediaPlayer != null){
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;


            AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(null);

        }
    }



    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener listener){
        mOnPreparedListener = listener;
    }

    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener listener){
        mOnCompletionListener = listener;
    }

    public void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener listener){
        mOnBufferingUpdateListener = listener;
    }

    public void setOnErrorListener(IMediaPlayer.OnErrorListener listener){
        mOnErrorListener = listener;
    }

    public void setOnInfoListener(IMediaPlayer.OnInfoListener listener){
        mOnInfoListener = listener;
    }


    ///////////////////////////////////////////////////////////////////////////
    // SurfaceHolder Callback
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bindSurfaceHolder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        release();
    }


    ///////////////////////////////////////////////////////////////////////////
    // IRenderView
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setDisplay(int scale) {
        mMeasureHelper.setAspectRatio(scale);
        requestLayout();
    }

    @Override
    public void setVideoSize(int width, int height) {
        if (width > 0 && height > 0) {
            mMeasureHelper.setVideoSize(width, height);
            getHolder().setFixedSize(width, height);
            requestLayout();
        }
    }

}
