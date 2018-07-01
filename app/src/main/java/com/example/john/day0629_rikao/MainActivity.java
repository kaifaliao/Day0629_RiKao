package com.example.john.day0629_rikao;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dou361.ijkplayer.widget.IRenderView;
import com.dou361.ijkplayer.widget.IjkVideoView;
import com.dou361.ijkplayer.widget.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.video_views)
    IjkVideoView mVideoViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mVideoViews.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        mVideoViews.setVideoURI(Uri.parse("http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4"));
        mVideoViews.start();

    }
}
