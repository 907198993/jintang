package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.github.customview.MyEditText;
import com.google.gson.Gson;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.LoginObj;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/31.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_login_phone)
    MyEditText et_login_phone;
    @BindView(R.id.et_login_pwd)
    MyEditText et_login_pwd;

    @Override
    protected int getContentView() {
        setAppTitle("登录");
        setAppRightTitle("去注册");
        return R.layout.act_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick({R.id.app_right_tv,R.id.tv_login_forget_pwd,R.id.tv_login_commit, R.id.iv_login_qq, R.id.iv_login_wx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_right_tv:
                STActivity(RegisterActivity.class);
                break;
            case R.id.tv_login_commit:
                if(TextUtils.isEmpty(getSStr(et_login_phone))){
                    showToastS("请输入手机号");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_login_pwd))){
                    showToastS("请输入密码");
                    return;
                }
                login();

                break;
            case R.id.iv_login_qq:
                if (!UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.QQ)) {
                    showMsg("请安装QQ之后再试");
                    return;
                }
                UMShareConfig config2 = new UMShareConfig();
                config2.isNeedAuthOnGetUserInfo(true);
                UMShareAPI.get(this).setShareConfig(config2);
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        showLoading();
                        Log.i("========","onStart=");
                    }
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> m) {
//                        dismissLoading();
                        Log.i("========","onComplete="+new Gson().toJson(m).toString());
                        String uid = m.get("uid");
                        String name = m.get("name");
                        String profile_image_url = m.get("profile_image_url");
                        loginForApp("1",uid,name, profile_image_url);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        dismissLoading();

                        Log.i("========","onError=");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        dismissLoading();
                        Log.i("========","onCancel=");
                    }
                });
                break;
            case R.id.iv_login_wx:
                if (!UMShareAPI.get(mContext).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    showMsg("请安装微信之后再试");
                    return;
                }
                UMShareConfig config = new UMShareConfig();
                config.isNeedAuthOnGetUserInfo(true);
                UMShareAPI.get(this).setShareConfig(config);
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        showLoading();
                        Log.i("========","onStart=");
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> m) {
//                        dismissLoading();
                        Log.i("========","onComplete="+new Gson().toJson(m).toString());
                        String unionid = m.get("unionid");
                        String name = m.get("name");
                        String profile_image_url = m.get("profile_image_url");
                        loginForApp("2",unionid, name, profile_image_url);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        dismissLoading();
                        showMsg("微信登录失败");
                        Log.i("========","onError="+throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        dismissLoading();
                        Log.i("========","onCancel=");
                    }
                });
                break;

            case R.id.tv_login_forget_pwd:
                STActivity(ForgetPWDActivity.class);
                break;
        }
    }
    private void loginForApp(String platform,String unionid, String name, String profile_image_url) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("platform",platform);
        map.put("open",unionid);
        map.put("nickname",name);
        map.put("avatar",profile_image_url);
        map.put("RegistrationID",SPUtils.getPrefString(mContext,Config.jiguangRegistrationId,""));
        map.put("sign", GetSign.getSign(map));
        ApiRequest.platformLogin(map, new MyCallBack<LoginObj>(mContext) {
            @Override
            public void onSuccess(LoginObj obj) {
                loginResult(obj);
            }
        });

    }
    private void login() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("username",getSStr(et_login_phone));
        map.put("password",getSStr(et_login_pwd));
        map.put("RegistrationID",SPUtils.getPrefString(mContext,Config.jiguangRegistrationId,""));
        map.put("sign",GetSign.getSign(map));
        ApiRequest.userLogin(map, new MyCallBack<LoginObj>(mContext) {
            @Override
            public void onSuccess(LoginObj obj) {
                SPUtils.setPrefString(mContext,Config.phone,getSStr(et_login_phone));
                SPUtils.setPrefString(mContext,Config.hxuser,obj.getHxuser());
                SPUtils.setPrefString(mContext,Config.hxpwd,obj.getHxpwd());
                loginResult(obj);
            }
        });
    }

    private void loginResult(LoginObj obj) {
        SPUtils.setPrefString(mContext, Config.user_id,obj.getUser_id());
        SPUtils.setPrefString(mContext, Config.mobile,obj.getMobile());
        SPUtils.setPrefString(mContext, Config.sex,obj.getSex());
        SPUtils.setPrefString(mContext, Config.avatar,obj.getAvatar());
        SPUtils.setPrefString(mContext, Config.birthday,obj.getBirthday());
        SPUtils.setPrefString(mContext, Config.nick_name,obj.getNick_name());
        SPUtils.setPrefString(mContext, Config.user_name,obj.getUser_name());
        SPUtils.setPrefString(mContext, Config.amount,obj.getAmount()+"");
        SPUtils.setPrefInt(mContext, Config.count_wsy,obj.getCount_wsy());
        SPUtils.setPrefInt(mContext, Config.keeping_bean,obj.getKeeping_bean());
        SPUtils.setPrefInt(mContext, Config.news_is_check,obj.getNews_is_check());
        SPUtils.setPrefBoolean(mContext, Config.user_switch, obj.getMessage_sink()==1?true:false);
        //发送
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.Bro.operation));

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.i("=======","onActivityResult======");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
