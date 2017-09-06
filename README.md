# Demo for GZMediaplayer

## 1. 功能介绍
该播放器用于播放歌者盟加密后的视频文件
## 2. 集成前准备
### 2.1 下载SDK
下载歌者盟SDK并解压缩 <https://github.com/sameshang/Demo/raw/master/gezhemeng_android_sdk_0.1.0.tar.gz> ，其中包含了集成需要的库文件、文档。
### 2.2 导入SDK
歌者盟SDK以aar包形式提供，若合作方使用Eclipse作为开发工具请自行转换为jar包。

注意：歌者盟SDK最低支持API 16（Android 4.1）

将下载包中aars\gzm_*.aar 复制到本地工程libs目录下，在Gradle依赖中添加：

    repositories {
        flatDir {
            dirs 'libs'
        }
    }
    
    dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile(name: 'gzm_***', ext: 'aar')
    compile 'com.android.support:appcompat-v7:26.+'//v7 支持库
    }

    


