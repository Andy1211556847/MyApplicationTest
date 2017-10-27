package com.qiniu.qplayer.mediaEngine;

import android.view.Surface;
import android.view.SurfaceView;

/**
 * Created by huaxianlian on 2017/8/28
 */

public class MediaPlayer implements PlayerParameter {
    private static final String TAG = "QCLOGMediaPlayer";

    static {
        System.loadLibrary("QPlayer");
    }



    private long mNativeContext;
    private Surface mSurface;
    private OnPlayerEventListener mListener;
    public void setOnPlayerEventListener(OnPlayerEventListener listener){
        mListener = listener;
    }


    public long init(String apkPath, int nFlag) {
        mNativeContext = nativeInit(this, apkPath, nFlag);
        if(mNativeContext != 0){
            //初始化成功



        }
        return mNativeContext;
    }


    public void setView(SurfaceView sv) {
        if (sv != null) {
            mSurface = sv.getHolder().getSurface();
        } else {
            mSurface = null;
        }

        nativeSetView(mNativeContext, mSurface);
    }

    public int open(String strPath, int nFlag) {
        return nativeOpen(mNativeContext, strPath, nFlag);
    }

    public void play() {
        nativePlay(mNativeContext);
    }

    public void pause() {
        nativePause(mNativeContext);
    }

    public void stop() {
        nativeStop(mNativeContext);
    }

    public long getDuration() {
        return nativeGetDuration(mNativeContext);
    }

    public long getPos() {
        return nativeGetPos(mNativeContext);
    }

    public int setPos(long lPos) {
        return nativeSetPos(mNativeContext,lPos);
    }

    public int getParam(int nParamId, int nParam, Object objParam) {
        return nativeGetParam(mNativeContext, nParamId, nParam, objParam);
    }

    public int setParam(int nParamId, int nParam, Object objParam) {
        return nativeSetParam(mNativeContext, nParamId, nParam, objParam);
    }

    public void uninit() {
        nativeUninit(mNativeContext);
    }

    public int getVideoWidth() {
        return 0;
    }

    public int getVideoHeight() {
        return 0;
    }

    public void setVolume(int nVolume) {
        setParam(PARAM_PID_AUDIO_VOLUME, nVolume, null);
    }

    public int getStreamNum() {
        return 0;
    }

    public int setStreamPlay(int nStream) {
        return 0;
    }

    public int getStreamPlay() {
        return 0;
    }

    public int getStreamBitrate(int nStream) {
        return 0;
    }


    /**
     * 停止释放资源
     */
    public void release(){
        stop();
        setView(null);

        uninit();
    }


    public interface OnPlayerEventListener{

        /**
         * 事件监听
         * @param what
         * @param ext1
         * @param ext2
         * @param obj
         */
        void onEvent(int what, int ext1, int ext2, Object obj);

        /**
         * 声音帧数据
         * @param data
         * @param size
         * @param lTime
         */
        void onAudioData(byte[] data, int size, long lTime);

        /**
         * 视频帧数据
         * @param data
         * @param size
         * @param lTime
         * @param nFlag
         */
        void onVideoData(byte[] data, int size, long lTime,int nFlag);
    }



    ///////////////////////////////////////////////////////////////////////////
    // native方法调用与回调
    ///////////////////////////////////////////////////////////////////////////


    /**
     * 播放器事件
     * @param baselayer_ref  native初始化绑定的播放器对象
     * @param what
     * @param ext1
     * @param ext2
     * @param obj
     */
    private static void postEventFromNative(Object baselayer_ref, int what, int ext1, int ext2, Object obj) {

        if(what == QC_MSG_PLAY_COMPLETE){
            System.out.println("播放完成");
        }

        final MediaPlayer player = (MediaPlayer) baselayer_ref;

        if(player != null){
            if(what == QC_MSG_SNKV_NEW_FORMAT){
                player.setParam(PARAM_PID_EVENT_DONE,0,null);
            }


            if(player.mListener != null){
                player.mListener.onEvent(what,ext1,ext2,obj);
            }
        }

    }


    /**
     * 声音帧数据回调
     * @param baselayer_ref
     * @param data
     * @param size
     * @param lTime
     */
    private static void audioDataFromNative(Object baselayer_ref, byte[] data, int size, long lTime) {
        System.out.println("声音数据");
        MediaPlayer player = (MediaPlayer) baselayer_ref;

        if(player != null){
            if(player.mListener != null){
                player.mListener.onAudioData(data,size,lTime);
            }
        }

    }

    /**
     * 视频帧数据回调
     * @param baselayer_ref
     * @param data
     * @param size
     * @param lTime
     * @param nFlag
     */
    private static void videoDataFromNative(Object baselayer_ref, byte[] data, int size, long lTime, int nFlag) {
        System.out.println("视频数据");
        MediaPlayer player = (MediaPlayer) baselayer_ref;
        if(player != null){
            if(player.mListener != null){
                player.mListener.onVideoData(data,size,lTime,nFlag);
            }
        }

    }

    // the native functions
    private native long nativeInit(Object player, String apkPath, int nFlag);

    private native int nativeUninit(long nNativeContext);

    private native int nativeSetView(long nNativeContext, Object view);

    private native int nativeOpen(long nNativeContext, String strPath, int nFlag);

    private native int nativePlay(long nNativeContext);

    private native int nativePause(long nNativeContext);

    private native int nativeStop(long nNativeContext);

    private native long nativeGetPos(long nNativeContext);

    private native int nativeSetPos(long nNativeContext, long lPos);

    private native long nativeGetDuration(long nNativeContext);

    private native int nativeGetParam(long nNativeContext, int nParamId, int nParam, Object objParam);

    private native int nativeSetParam(long nNativeContext, int nParamId, int nParam, Object objParam);
}
