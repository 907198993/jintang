package com.sk.jintang.wxapi;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.sk.jintang.Config;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.module.home.activity.MainActivity;
import com.sk.jintang.module.my.activity.MyOrderListActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/10/12.
 */

public class PayResultActivity extends BaseActivity {
    @BindView(R.id.tv_pay_result)
    TextView tv_pay_result;
    @BindView(R.id.tv_pay_txt)
    TextView tv_pay_txt;

    private int errCode;
    private boolean isSuccess;
    private Intent intent;

    @Override
    protected int getContentView() {
        setAppTitle("支付结果");
        return R.layout.act_pay_success;
    }

    @Override
    protected void initView() {
        errCode=getIntent().getIntExtra("result",-1);
        if (errCode == 0) {// 支付成功
//                SPUtils.setPrefBoolean(mContext, Config.accountChongZhi,true);
            isSuccess=true;
            tv_pay_result.setText("买家已付款");
            tv_pay_txt.setText("您的包裹已整装待发");
        } else if (errCode == -2) {
            tv_pay_result.setText("买家已取消支付");
            tv_pay_txt.setText("您已取消支付");
        } else if (errCode == -1) {
            tv_pay_result.setText("买家支付失败");
            tv_pay_txt.setText("支付失败，请稍后再试");
        }
    }

    @Override
    protected void initData() {

    }
    @Override
    public void finish() {
        if(isSuccess){
            intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            STActivity(intent,MyOrderListActivity.class);
        }
        super.finish();
    }
    @OnClick({R.id.tv_pay_lookorder, R.id.tv_pay_back})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_lookorder:
                if(isSuccess){
                    finish();
                }else{
                    intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    STActivity(intent,MyOrderListActivity.class);
                    finish();
                }

                break;
            case R.id.tv_pay_back:
                intent = new Intent(Config.backHome);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                STActivity(intent,MainActivity.class);
                break;
        }
    }
}
