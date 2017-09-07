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

## 3. GZMediaplayer 类简单介绍

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
    mediaPlayer.setVideoSurfaceView(SurfaceView surfaceView);
    
#### 3.2.2 SurfaceHolder

    /**
     * Sets the {@link SurfaceView} that holds the {@link Surface} onto which video will be
     * rendered. The player will track the lifecycle of the surface automatically.
     *
     * @param surfaceView The surfaceView .
     */
    mediaPlayer.setVideoSurfaceHolder(SurfaceHolder surfaceHolder);  
     
#### 3.2.3 TextureView

    /**
     * Sets the {@link TextureView} onto which video will be rendered. The player will track the
     * lifecycle of the surface automatically.
     *
     * @param textureView The texture view.
     */
    mediaPlayer.setVideoTextureView(TextureView textureView);
    
### 3.3 准备播放资源

异步操作,根情况选择

#### 3.3.1 歌者盟提供的id 未下载

    /**
     *
     * @param videoId 歌者盟提供的资源id.
     */
    mediaPlayer.prepareAsync(long videoId);
    
#### 3.3.2 歌者盟提供的id 根据歌者盟提供的Url已下载到本地

    /**
     * @param videoId  歌者盟提供的资源id
     *
     * @param filePath 本地文件路径
     */
    mediaPlayer.prepareAsync(long videoId, String filePath);

#### 3.3.3 非歌者盟提供的资源

    /**
     * @param filePath 资源路径
     */
    mediaPlayer.prepareAsync(String filePath);
    
### 3.4 暂停 播放

注意:当准备播放资源后(异步执行),调用 mediaPlayer.setPlayWhenReady(true) 当播放器资源加载完成后自动播放

    /**
     * @param playWhenReady 播放(true),暂停(false)
     */
     mediaPlayer.setPlayWhenReady(boolean playWhenReady);
     
### 3.5 音量控制
