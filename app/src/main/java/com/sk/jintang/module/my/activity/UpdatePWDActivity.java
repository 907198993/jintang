package com.sk.jintang.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.github.customview.MyEditText;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/7.
 */

public class UpdatePWDActivity extends BaseActivity {
    @BindView(R.id.et_update_oldpwd)
    MyEditText et_update_oldpwd;
    @BindView(R.id.et_update_newpwd)
    MyEditText et_update_newpwd;
    @BindView(R.id.et_update_repwd)
    MyEditText et_update_repwd;

    @Override
    protected int getContentView() {
        setAppTitle("修改密码");
        return R.layout.act_update_pwd;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_updatepwd_commit})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_updatepwd_commit:
                String oldPWD=getSStr(et_update_oldpwd);
                String newPWD=getSStr(et_update_newpwd);
                String rePWD=getSStr(et_update_repwd);
                if(TextUtils.isEmpty(oldPWD)){
                    showMsg("原密码不能为空");
                    return;
                }else if(TextUtils.isEmpty(newPWD)){
                    showMsg("新密码不能为空");
                    return;
                }else if(!newPWD.equals(rePWD)){
                    showMsg("两次密码不一样");
                    return;
                }
                updatePwd(oldPWD,newPWD);
            break;
        }
    }

    private void updatePwd(String oldPWD,String newPWD) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("oldPassword",oldPWD);
        map.put("newPassword",newPWD);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.updatePWD(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());

                SPUtils.setPrefBoolean(mContext, Config.isUpdatePWD,true);

                finish();
            }
        });

    }
}
