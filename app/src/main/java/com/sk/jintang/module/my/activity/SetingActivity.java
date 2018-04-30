package com.sk.jintang.module.my.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.baseclass.view.MyDialog;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.home.network.response.AppVersionObj;
import com.sk.jintang.module.my.bean.AppInfo;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.service.MyAPPDownloadService;
import com.suke.widget.SwitchButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/6.
 */

public class SetingActivity extends BaseActivity {
    @BindView(R.id.sb_seting_msg)
    SwitchButton sb_seting_msg;
    @BindView(R.id.tv_seting_version)
    TextView tv_seting_version;
    @BindView(R.id.tv_setting_new_version)
    TextView tv_setting_new_version;

    @Override
    protected int getContentView() {
        setAppTitle("设置");
        return R.layout.act_seting;
    }

    @Override
    protected void initView() {
        if(SPUtils.getPrefBoolean(mContext,Config.appHasNewVersion,false)){
            tv_setting_new_version.setVisibility(View.VISIBLE);
        }else{
            tv_setting_new_version.setVisibility(View.INVISIBLE);
        }


        tv_seting_version.setText(getVersion());
        boolean userSwitch = SPUtils.getPrefBoolean(mContext, Config.user_switch, true);
        sb_seting_msg.setChecked(userSwitch);
        sb_seting_msg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()== MotionEvent.ACTION_UP){
                    setSwitch();
                }
                return true;
            }
        });
    }

  

    public String getVersion () {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(),PackageManager.GET_CONFIGURATIONS);
            String version = info.versionName;
            return "V"+ version;
        } catch (Exception e) {
            e.printStackTrace();
            return "V1.0";
        }
    }
    @Override
    protected void initData() {

    }
    private void setSwitch() {
        boolean checked = sb_seting_msg.isChecked();
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("message_sink",!checked?"1":"0");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.setMsgTiXing(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                sb_seting_msg.setChecked(!checked);
                SPUtils.setPrefBoolean(mContext, Config.user_switch, !checked);
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                sb_seting_msg.setChecked(checked);
            }
        });
    }
    @OnClick({R.id.ll_seting_yjfk, R.id.tv_seting_exit,R.id.ll_setting_new_version})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_new_version:
                updateApp();
                break;
            case R.id.ll_seting_yjfk:
                STActivity(YiJianFanKuiActivity.class);
                break;
            case R.id.tv_seting_exit:
                mDialog = new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认退出登录?")
                        .setNegativeButton((dialog, which) -> dialog.dismiss())
                        .setPositiveButton((dialog, which) -> {
                            dialog.dismiss();
                            startExit();
                            exitLogin();

                        });
                mDialog.create().show();
                break;
        }
    }
    private void updateApp() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        com.sk.jintang.module.home.network.ApiRequest.getAppVersion(map, new MyCallBack<AppVersionObj>(mContext) {
            @Override
            public void onSuccess(AppVersionObj obj) {
                if(obj.getAndroid_version()>getAppVersionCode()){
                    SPUtils.setPrefString(mContext,Config.appDownloadUrl,obj.getAndroid_vs_url());
                    SPUtils.setPrefBoolean(mContext,Config.appHasNewVersion,true);
                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                    mDialog.setMessage("检测到app有新版本是否更新?");
                    mDialog.setNegativeButton((dialog, which) -> dialog.dismiss());
                    mDialog.setPositiveButton((dialog, which) -> {
                        dialog.dismiss();
                        AppInfo info=new AppInfo();
                        info.setUrl(obj.getAndroid_vs_url());
                        info.setHouZhui(".apk");
                        info.setFileName("yangyu");
                        info.setId(obj.getAndroid_version()+"");
                        downloadApp(info);
                    });
                    mDialog.create().show();
                }else{
                    showMsg("当前app已是最新版本");
                    tv_setting_new_version.setVisibility(View.INVISIBLE);
                    SPUtils.setPrefBoolean(mContext,Config.appHasNewVersion,false);
                }
            }
        });
    }
    public int getAppVersionCode() {
        Context context=mContext;
        int versioncode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            String versionName = pi.versionName;
            versioncode = pi.versionCode;
            return versioncode;
        } catch (Exception e) {
            android.util.Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }
    private void downloadApp(AppInfo info) {
        MyAPPDownloadService.intentDownload(mContext, info);
    }
    private void startExit() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.exitApp(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {

            }
        });

    }

    private void exitLogin() {
        SPUtils.removeKey(mContext, Config.user_id);
        Intent intent = new Intent(Config.Bro.operation);
        intent.putExtra(Config.Bro.flag, Config.Bro.exit_login);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        STActivity(LoginActivity.class);

        finish();
    }
}
