package com.android.haule;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity implements View.OnClickListener{
    //uri of video
    String fileLocation = "https://....";
    FullScreenVideoView videoView;
    ImageView imgPip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init view
        videoView = findViewById(R.id.video_player);
        imgPip = findViewById(R.id.ic_icon_pip);
        //play video when start app
        playVideo();
        //set event onclick
        setEvent();
    }

    private void setEvent() {
        imgPip.setOnClickListener(this);
    }

    private void playVideo() {
        Uri vidFile = Uri.parse(fileLocation);
        videoView.setVideoURI(vidFile);
        videoView.setMediaController(new MediaController(this));
        videoView.start();
    }

    private void activePip(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            Rational aspectRatio = new Rational(width, height);
            PictureInPictureParams.Builder mPip = new PictureInPictureParams.Builder();
            mPip.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(mPip.build());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ic_icon_pip:
                activePip();
                break;
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if(isInPictureInPictureMode){
            imgPip.setVisibility(View.GONE);
        }else{
            imgPip.setVisibility(View.VISIBLE);
        }
    }
}
