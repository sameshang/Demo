package com.gtan.demoapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gtan.mediaplayer.GZMediaPlayer;
import com.gtan.mediaplayer.OnCompletionListener;
import com.gtan.mediaplayer.OnErrorListener;
import com.gtan.mediaplayer.OnLoadingListener;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "MainActivity";

    private Context context;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    private SeekBar seekBar;
    private TextView mCurrentTime;
    private TextView mEndTime;
    private ProgressBar loadingBar;

    private Handler mHandler;

    private GZMediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.main_surface_view);
        SurfaceHolder videoHolder = surfaceView.getHolder();
        videoHolder.addCallback(this);

        //初始化播放器
        mediaPlayer = new GZMediaPlayer(this);
        //设置要用作媒体视频部分的接收器的页面
        mediaPlayer.setVideoSurfaceHolder(videoHolder);
        //设置监听,播放过程中,视频资源是否正在加载
        mediaPlayer.setOnLoadingListener(new OnLoadingListener() {
            @Override
            public void onLoadingChanged(GZMediaPlayer mp, boolean isLoading) {
                loadingBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    mHandler.removeMessages(CHANGE_PROGRESS);
                } else {
                    mHandler.sendEmptyMessage(CHANGE_PROGRESS);
                }
            }
        });
        //设置监听,到达媒体源的末尾时调用此方法
        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(GZMediaPlayer mp) {
                Log.e(TAG, "onCompletion: ");
            }
        });
        //设置监听,播放过程出现错误
        mediaPlayer.setOnErrorListener(new OnErrorListener() {
            @Override
            public void onError(GZMediaPlayer mp, Exception e) {
                Log.e(TAG, "onError: e\t" + e.getMessage());
            }
        });
        //准备播放资源,异步操作(歌者盟提供的资源id)
        mediaPlayer.prepareAsync(27482);
        // 1. 设置为 true 当播放器资源加载完成后自动播放,
        // 2. 当播放器播放过程中,调用此方法可以开始(true)/暂停(false)播放
        mediaPlayer.setPlayWhenReady(true);

        seekBar = (SeekBar) findViewById(R.id.main_seek);
        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(mSeekListener);

        mCurrentTime = (TextView) findViewById(R.id.main_current_position_time);
        mEndTime = (TextView) findViewById(R.id.main_end_time);
        loadingBar = (ProgressBar) findViewById(R.id.main_loading);

        //播放按钮
        Button playButton = (Button) findViewById(R.id.main_button_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.setPlayWhenReady(true);
            }
        });

        //暂停按钮
        Button pauseButton = (Button) findViewById(R.id.main_button_pause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.setPlayWhenReady(false);
            }
        });

        //重播按钮
        Button rePlay = (Button) findViewById(R.id.main_button_replay);
        rePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(0);
                mediaPlayer.setPlayWhenReady(true);
            }
        });

        //增强音量
        Button volumeIncrease = (Button) findViewById(R.id.main_volume_increase);
        volumeIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float volume = mediaPlayer.getVolume();
                if (volume < 1) {
                    //设置音量大小 范围 0 至 1
                    mediaPlayer.setVolume(volume + 0.1f);
                } else {
                    Toast.makeText(context, "已是最大啦", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //降低音量
        Button volumeDecline = (Button) findViewById(R.id.main_volume_decline);
        volumeDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float volume = mediaPlayer.getVolume();
                if (volume > 0) {
                    mediaPlayer.setVolume(volume - 0.1f);
                } else {
                    Toast.makeText(context, "已是最小啦", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //静音
        Button volumeSilence = (Button) findViewById(R.id.main_volume_silence);
        volumeSilence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.setVolume(0);
            }
        });

        mHandler = new MessageHandler(mediaPlayer);

    }

    private boolean mDragging;
    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStartTrackingTouch(SeekBar bar) {
            mDragging = true;

        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (mediaPlayer == null) {
                return;
            }
            if (!fromuser) {
                return;
            }
            //获取播放时长,单位毫秒
            long duration = mediaPlayer.getDuration();
            long newposition = (duration * progress) / 1000L;
            mCurrentTime.setText(stringForTime((int) newposition));
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            mDragging = false;
            //获取播放时长,单位毫秒
            long duration = mediaPlayer.getDuration();
            long newPosition = (duration * bar.getProgress()) / 1000L;
            //播放器指定的时间位置,,单位毫秒
            mediaPlayer.seekTo((int) newPosition);
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (mediaPlayer != null) {
            mediaPlayer.setVideoSurfaceHolder(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mediaPlayer != null) {
            mediaPlayer.clearVideoSurfaceHolder(surfaceHolder);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //播放器暂停
        mediaPlayer.setPlayWhenReady(false);
        mHandler.removeMessages(CHANGE_PROGRESS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放播放器资源
        mediaPlayer.release();
    }

    private static final int CHANGE_PROGRESS = 2;

    private class MessageHandler extends Handler {
        private final WeakReference<GZMediaPlayer> player;

        MessageHandler(GZMediaPlayer player) {
            this.player = new WeakReference<>(player);
        }

        @Override
        public void handleMessage(Message msg) {
            GZMediaPlayer player = this.player.get();
            if (player == null) {
                return;
            }
            switch (msg.what) {
                case CHANGE_PROGRESS:
                    //播放器状态,播放中(true)/暂停(false)
                    if (player.isPlaying() && !mDragging) {
                        //获取播放的当前位置,单位毫秒
                        long position = player.getPosition();
                        //获取播放时长,单位毫秒
                        long duration = player.getDuration();
                        if (duration > 0) {
                            long pos = 1000L * position / duration;
                            seekBar.setProgress((int) pos);
                        }
                        long bufferedPosition = player.getBufferedPosition();
                        long bufferedPos = 1000L * bufferedPosition / duration;
                        seekBar.setSecondaryProgress((int) bufferedPos);
                        mEndTime.setText(stringForTime((int) duration));
                        mCurrentTime.setText(stringForTime((int) position));
                        msg = obtainMessage(CHANGE_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (position % 1000));
                    }
                    break;
            }
        }
    }

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

}

