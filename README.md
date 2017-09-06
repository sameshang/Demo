# Demo for GZMediaplayer

## 1. 功能介绍
该播放器仅用于播放歌者盟提供的资源文件
## 2. 集成前准备
### 2.1 下载SDK
<a href="https://github.com/sameshang/Demo/raw/master/gezhemeng_android_sdk_0.1.0.tar.gz">下载歌者盟SDK</a>
，其中包含了集成需要的库文件和文档。
### 2.2 导入SDK
歌者盟SDK以aar包形式提供，若合作方使用Eclipse作为开发工具请自行转换为jar包。

注意：歌者盟SDK最低支持API 16（Android 4.1）

##### 2.2.1 将下载包中aar\gzm_*.aar 复制到本地工程libs目录下 
##### 2.2.2 添加 v7 appcompat 库依赖
##### 2.2.3 在Gradle依赖中添加：

    repositories {
        flatDir {
            dirs 'libs'
        }
    }
    dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile(name: 'gzm_****', ext: 'aar')
    compile 'com.android.support:appcompat-v7:**.**'
    }
### 2.3 权限说明
申请网络权限

<uses-permission android:name="android.permission.INTERNET" />(必须)

## 3. GZMediaplayer 类简单介绍

详细使用方法请参考 demo和文档

    //初始化播放器
    GZMediaPlayer mediaPlayer =new GZMediaPlayer(android.content.Context context)  
    //设置要用作媒体视频部分的接收器的页面
    mediaPlayer.setVideoSurfaceHolder(SurfaceHolder surfaceHolder);
    //准备播放资源,异步操作(歌者盟提供的资源id)
    mediaPlayer.prepareAsync(long videoId);
    // 1. 设置为 true 当播放器资源加载完成后自动播放,
    // 2. 当播放器播放过程中,调用此方法可以开始(true)/暂停(false)播放
    mediaPlayer.setPlayWhenReady(boolean playWhenReady)
     //设置监听,播放过程中,视频资源是否正在加载
    mediaPlayer.setOnLoadingListener(new OnLoadingListener() {
        @Override
        public void onLoadingChanged(GZMediaPlayer mp, boolean isLoading) {
             Log.e(TAG, "onLoadingChanged: ");
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
