package com.gtan.demoapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.gtan.medaiplayer.GZMediaPLayer;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private SurfaceHolder videoHolder;
    private Button button;
    private GZMediaPLayer mediaPLayer;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path =getExternalFilesDir(Environment.DIRECTORY_MOVIES)+"/encrypt.mp4";

        mediaPLayer=new GZMediaPLayer(this);

        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView) findViewById(R.id.main_surface_view);
        videoHolder=surfaceView.getHolder();
        videoHolder.addCallback(this);
        button= (Button) findViewById(R.id.main_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPLayer.initChurchExoPlayer("http://test-medialib.singerdream.com/video/ed46cb7aa60d05db8cd6d7c22ff2ee23.mp4",
                        "2GGToKEf+SzFUXqSdo++3J5JnSvqn/QJtOuV9aDjoJQ=","WzJ4qgF2LXFDxVYLnfNc9A==");
                mediaPLayer.setVideoSurfaceHolder(videoHolder);
                mediaPLayer.setPlayWhenReady(true);
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (mediaPLayer!=null){
            mediaPLayer.setVideoSurfaceHolder(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mediaPLayer!=null){
            mediaPLayer.clearVideoSurfaceHolder(surfaceHolder);
        }
    }
}

