# Demo for GZMediaPlayer

## 1. 功能介绍
该播放器仅用于播放歌者盟提供的资源文件
## 2. 集成前准备
### 2.1 下载SDK
下载歌者盟SDK 
##### 2.1.1 下载库文件 
下载地址: https://git.singerdream.com/gitbucket/shangmw/DemoForGZMediaPlayer/raw/master/app/libs/GZMPlayer_1.0.0.aar
##### 2.1.2 下载文档
下载地址: https://git.singerdream.com/gitbucket/shangmw/DemoForGZMediaPlayer/raw/master/GZMPlayer_1.0.0_android_api.tar.gz
### 2.2 导入SDK
歌者盟SDK以aar包形式提供，若合作方使用Eclipse作为开发工具请自行转换为jar包。

注意：歌者盟SDK最低支持API 16（Android 4.1）

##### 2.2.1 将文件 GZMPlayer_\*\*\*.aar 复制到本地工程libs目录下 
##### 2.2.2 添加 v7 appcompat 库依赖
##### 2.2.3 在Gradle依赖中添加：

    repositories {
        flatDir {
            dirs 'libs'
        }
    }
    dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile(name: 'GZMPlayer__****', ext: 'aar')
    compile 'com.android.support:appcompat-v7:**.**'
    }
### 2.3 权限说明
申请网络权限

<uses-permission android:name="android.permission.INTERNET" />(必须)

## 3. GZMediaplayer 类简介

详细使用方法请参考 demo和文档

### 3.1 初始化播放器

       /**
       * 初始化播放器
       *
       * @param context the Context to use
       */
      GZMediaPlayer mediaPlayer =new GZMediaPlayer(android.content.Context context)
      
### 3.2 播放视频设置view

#### 3.2.1 SurfaceView

     /**
     * Sets the {@link SurfaceView} that holds the {@link Surface} onto which video will be
     * rendered. The player will track the lifecycle of the surface automatically.
     *
     * @param surfaceView The surfaceView .
     */
    public void setVideoSurfaceView(SurfaceView surfaceView);
    
#### 3.2.2 SurfaceHolder

    /**
     * Sets the {@link SurfaceView} that holds the {@link Surface} onto which video will be
     * rendered. The player will track the lifecycle of the surface automatically.
     *
     * @param surfaceView The surfaceView .
     */
    public void setVideoSurfaceHolder(SurfaceHolder surfaceHolder);  
     
#### 3.2.3 TextureView

    /**
     * Sets the {@link TextureView} onto which video will be rendered. The player will track the
     * lifecycle of the surface automatically.
     *
     * @param textureView The texture view.
     */
    public void setVideoTextureView(TextureView textureView);
    
### 3.3 准备播放资源

异步操作,根情况选择

#### 3.3.1 歌者盟提供的id 未下载

    /**
     *
     * @param videoId 歌者盟提供的资源id.
     */
    public void prepareAsync(long videoId);
    
#### 3.3.2 歌者盟提供的id 根据歌者盟提供的Url已下载到本地

    /**
     * @param videoId  歌者盟提供的资源id
     *
     * @param filePath 本地文件路径
     */
    public void prepareAsync(long videoId, String filePath);

#### 3.3.3 非歌者盟提供的资源

    /**
     * @param filePath 资源路径
     */
     
    public void prepareAsync(String filePath);
    
### 3.4 暂停/播放

注意:当准备播放资源后(异步执行),调用 mediaPlayer.setPlayWhenReady(true) 当播放器资源加载完成后自动播放

    /**
     * @param playWhenReady 播放(true),暂停(false)
     */
    public void setPlayWhenReady(boolean playWhenReady);
     
### 3.5 音量控制

#### 3.5.1 设置播放器音量

    /**
     * Sets the audio volume, with 0 being silence and 1 being unity gain.
     *
     * @param audioVolume The audio volume.
     */
    public void setVolume(float audioVolume);
    
#### 3.5.2 获得播放器音量

    /**
     * Returns the audio volume, with 0 being silence and 1 being unity gain.
     *
     * @throws if the internal player engine has not been initialized or has been released.
     */
    public float getVolume() throws IllegalStateException

### 3.6 定点播放

#### 3.6.1 获取播放器当前位置(单位毫秒)

    /**
     * Returns the playback position in the current window, in milliseconds.
     *
     * @throws if the internal player engine has not been initialized or has been released.
     */
    public long getPosition() throws IllegalStateException
    
#### 3.6.2 获取播放器总时长(单位毫秒)

    /**
     * Returns the playback position in the current window, in milliseconds.
     *
     * @throws if the internal player engine has not been initialized or has been released.
     */
    public long getPosition() throws IllegalStateException 
    
#### 3.6.3 获取播放器缓冲时长(单位毫秒)

    /**
     * Returns an estimate of the position in the current window up to which data is buffered, in
     * milliseconds.
     *
     * @throws if the internal player engine has not been initialized or has been released.
     */
    public long getBufferedPosition() throws IllegalStateException

#### 3.6.4 播放器跳转

    /**
     * Seeks to a position specified in milliseconds in the current window.
     *
     * @param positionMs The seek position in the current window, or {@link C#TIME_UNSET} to seek to
     *                   the window's default position.
     */
    public void seekTo(int positionMs);

### 3.7 播放器状态

#### 3.7.1 判断是否正在播放

    /**
     * @return Checks whether the GZMediaPlayer is playing.
     * @throws if the internal player engine has not been initialized or has been released.
     */
    public boolean isPlaying() throws IllegalStateException 
    
#### 3.7.2 获得播放器状态

    **
     * The player does not have any media to play.
     */
    public static final int STATE_IDLE = 1;
    /**
     * The player is not able to immediately play from its current position. This state typically
     * occurs when more data needs to be loaded.
     */
    public static final int STATE_BUFFERING = 2;
    /**
     * The player is able to immediately play from its current position. The player will be playing if
     * {@link #getPlayWhenReady()} is true, and paused otherwise.
     */
    public static final int STATE_READY = 3;
    /**
     * The player has finished playing the media.
     */
    public static final int STATE_ENDED = 4;
    

     /**
     * Returns the current state of the player.
     *
     * @throws if the internal player engine has not been initialized or has been released.
     */
     public int getPlaybackState() throws IllegalStateException 

### 3.8 释放播放器

    /**
     * Releases the player. This method must be called when the player is no longer required. The
     * player must not be used after calling this method.
     */
    public void release()
