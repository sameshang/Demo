package com.gtan.demoapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.gtan.mediaplayer.GZMediaPlayer;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private SurfaceHolder videoHolder;
    private Button button;
    private GZMediaPlayer mediaPlayer;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path =getExternalFilesDir(Environment.DIRECTORY_MOVIES)+"/encrypt.mp4";

        mediaPlayer=new GZMediaPlayer(this);

        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView) findViewById(R.id.main_surface_view);
        videoHolder=surfaceView.getHolder();
        videoHolder.addCallback(this);
        button= (Button) findViewById(R.id.main_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.prepareAsync(17922);
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (mediaPlayer!=null){
            mediaPlayer.setVideoSurfaceHolder(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mediaPlayer!=null){
            mediaPlayer.clearVideoSurfaceHolder(surfaceHolder);
        }
    }
}

