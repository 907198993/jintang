package com.sk.jintang;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.aspsine.multithreaddownload.DownloadConfiguration;
import com.aspsine.multithreaddownload.DownloadManager;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.github.retrofitutil.NetWorkManager;
//import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI;
import com.iflytek.cloud.SpeechUtility;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyApplication extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        // 安装tinker
        Beta.installTinker();

    }
    @Override
    public void onCreate() {

//        讯飞初始化
//        SpeechUtility.createUtility(this, "appid=" + Config.xunfei_app_id);
        super.onCreate();

        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(this, "95dce1c2f4", false);

        LeakCanary.install(this);
        ImagePipelineConfig frescoConfig = ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build();
        Fresco.initialize(this, frescoConfig);
        if(true&&BuildConfig.DEBUG){                           //http://192.168.0.19:20001/        //http://121.40.186.118:5108
         //   NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5108",BuildConfig.DEBUG).complete();

             NetWorkManager.getInstance(getApplicationContext(),"http://47.104.102.17:8001",BuildConfig.DEBUG).complete();
        }else{
          //  NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5008",BuildConfig.DEBUG).complete();
            NetWorkManager.getInstance(getApplicationContext(),"http://47.104.102.17:8001",BuildConfig.DEBUG).complete();
        }

//        if(true&&BuildConfig.DEBUG){                           //http://192.168.0.19:20001/        //http://121.40.186.118:5108
//            NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5108",BuildConfig.DEBUG).complete();
//
//           // NetWorkManager.getInstance(getApplicationContext(),"http://192.168.0.19:20001",BuildConfig.DEBUG).complete();
//        }else{
//           NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5008",BuildConfig.DEBUG).complete();
////            NetWorkManager.getInstance(getApplicationContext(),"http://192.168.0.19:20001",BuildConfig.DEBUG).complete();
//        }

        ZXingLibrary.initDisplayOpinion(this);
        SDKInitializer.initialize(getApplicationContext());

        JPushInterface.setDebugMode(BuildConfig.DEBUG); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        PlatformConfig.setWeixin(Config.weixing_id, Config.weixing_AppSecret);
        PlatformConfig.setQQZone(Config.qq_id, Config.qq_key);
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");

        UMShareAPI.get(this);
        huanXin();
        initDownloader();
    }

    private void initDownloader() {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(10);
        configuration.setThreadNum(3);
        DownloadManager.getInstance().init(getApplicationContext(), configuration);
    }
    private void huanXin() {
      EaseUI.getInstance().init(this, null);
    }
}
