package com.hxl.test.media;

import android.net.Uri;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by huaxianlian on 2017/9/5
 */

public interface IRenderView {
    int SCALE_FIT_PARENT = 0;
    int SCALE_FILL_PARENT = 1;        // may clip
    int SCALE_WRAP_CONTENT = 2;
    int SCALE_MATCH_PARENT = 3;
    int SCALE_16_9_FIT_PARENT = 4;
    int SCALE_4_3_FIT_PARENT = 5;


    /**
     * 设置缩放方案 {@link IRenderView#SCALE_FIT_PARENT} ...
     * @param scale
     */
    void setDisplay(int scale);

    void setVideoSize(int width,int height);


}
