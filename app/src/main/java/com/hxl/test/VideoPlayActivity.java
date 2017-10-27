package com.hxl.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import com.hxl.test.media.IJKVideoView;
import com.hxl.test.util.UIUtils;
import com.qiniu.qplayer.mediaEngine.PlayerParameter;
import com.qiniu.qplayer.mediaEngine.MediaPlayer;

import java.util.ArrayList;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by huaxianlian on 2017/8/23
 */

public class VideoPlayActivity extends Activity {

    IJKVideoView mVideoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);





        mVideoView = (IJKVideoView) findViewById(R.id.videoView);


//        mVideos.add("http://123.59.250.202/gcd.mp4");
//        mVideos.add("http://mus-oss.muscdn.com/reg02/2017/07/02/00/245712223036194816.mp4");
//        mVideos.add("http://musically.muscdn.com/reg02/2017/06/29/09/244762267827998720.mp4");
//        mVideos.add("http://musically.muscdn.com/reg02/2017/07/05/04/246872853734834176.mp4");
//        mVideos.add("http://musically.muscdn.com/reg02/2017/05/31/02/234148590598897664.mp4");
//

        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.open("http://mus-oss.muscdn.com/reg02/2017/07/02/00/245712223036194816.mp4", true);
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.stop();
            }
        });



    }
}


