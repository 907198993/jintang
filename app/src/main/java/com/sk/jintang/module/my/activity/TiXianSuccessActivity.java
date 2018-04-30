package com.sk.jintang.module.my.activity;

import android.view.View;

import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.module.my.Constant;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/18.
 */

public class TiXianSuccessActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        setAppTitle("账户提现");
        return R.layout.act_ti_xian_success;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_tixian_jilu,R.id.tv_tixian_back})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_tixian_jilu:
                setResult(RESULT_OK);
                finish();
            break;
            case R.id.tv_tixian_back:
                setResult(Constant.RCode.tiXianSuccess);
                finish();
            break;
        }
    }
}
