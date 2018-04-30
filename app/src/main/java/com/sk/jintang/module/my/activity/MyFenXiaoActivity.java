package com.sk.jintang.module.my.activity;

import android.view.View;
import android.widget.TextView;

import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.FenXiaoObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyFenXiaoActivity extends BaseActivity {


    @BindView(R.id.tv_fenxiao_yongjin)
    TextView tv_fenxiao_yongjin;
    @BindView(R.id.tv_fenxiao_xiaji)
    TextView tv_fenxiao_xiaji;

    @Override
    protected int getContentView() {
        setAppTitle("我的分销");
        return R.layout.act_my_fen_xiao;
    }
    @Override
    protected void initView() {
    }
    @Override
    protected void initData() {
        showProgress();
        getData();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getFenXiao(map, new MyCallBack<FenXiaoObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(FenXiaoObj obj) {
                tv_fenxiao_yongjin.setText("¥"+obj.getCommission());
                tv_fenxiao_xiaji.setText(obj.getLower_level()+"人");
            }
        });
    }
    @OnClick({R.id.ll_fenxiao_code, R.id.ll_fenxiao_xiaji, R.id.ll_fenxiao_yongjin,
              R.id.fl_fenxiao_xiaji, R.id.fl_fenxiao_yongjin})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.fl_fenxiao_xiaji:
            case R.id.ll_fenxiao_xiaji:
                STActivity(MyXiaJiActivity.class);
                break;
            case R.id.fl_fenxiao_yongjin:
            case R.id.ll_fenxiao_yongjin:
                STActivity(MyYongJinActivity.class);
                break;
            case R.id.ll_fenxiao_code:
                STActivity(YaoQingActivity.class);
                break;
        }
    }
}
