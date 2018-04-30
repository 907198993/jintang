package com.sk.jintang.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.github.rxjava.rxbus.RxUtils;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by administartor on 2017/9/5.
 */

public class ForgetPWDActivity extends BaseActivity {
    @BindView(R.id.et_forget_phone)
    MyEditText et_forget_phone;
    @BindView(R.id.et_forget_code)
    MyEditText et_forget_code;
    @BindView(R.id.tv_forget_getcode)
    MyTextView tv_forget_getcode;
    @BindView(R.id.et_forget_pwd)
    MyEditText et_forget_pwd;
    @BindView(R.id.et_forget_repwd)
    MyEditText et_forget_repwd;

    private String smsCode;
    @Override
    protected int getContentView() {
        setAppTitle("忘记密码");
        return R.layout.act_forget_pwd;
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

    @OnClick({R.id.tv_forget_getcode, R.id.tv_forget_commit})
    public void onClick(View view) {
        String phone=getSStr(et_forget_phone);
        switch (view.getId()) {
            case R.id.tv_forget_getcode:
                if(TextUtils.isEmpty(phone)){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(phone)){
                    showMsg("手机号不正确");
                    return;
                }
                getMsgCode();
                break;
            case R.id.tv_forget_commit:
                String code=getSStr(et_forget_code);
                if(TextUtils.isEmpty(phone)){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(phone )){
                    showMsg("手机号不正确");
                    return;
                }else if(TextUtils.isEmpty(smsCode)||TextUtils.isEmpty(code)||!code.equals(smsCode)){
                    showMsg("请输入正确验证码");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_forget_pwd))){
                    showMsg("密码不能为空");
                    return;
                }else if(!getSStr(et_forget_pwd).equals(getSStr(et_forget_repwd))){
                    showMsg("两次密码不一样");
                    return;
                }
                resetPwd(phone,getSStr(et_forget_pwd));
                break;
        }
    }
    private void resetPwd(String phone, String pwd) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("username",phone);
        map.put("newPassword",pwd);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.forgetPWD(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();
            }
        });

    }
    private void getMsgCode() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile",getSStr(et_forget_phone));
        map.put("rnd",getRnd());
        String sign = GetSign.getSign(map);
        map.put("sign", sign);
        showLoading();
        ApiRequest.getSMSCode(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                smsCode = obj.getSMSCode();
                countDown();
            }
        });
    }
    private void countDown() {
        tv_forget_getcode.setEnabled(false);
        final long count = 30;
        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(31)//计时次数
                .map(integer -> count - integer)
                .compose(RxUtils.appSchedulers())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        tv_forget_getcode.setEnabled(true);
                        tv_forget_getcode.setText("获取验证码");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv_forget_getcode.setText("剩下" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        addSubscription(subscribe);
    }
}
