package com.sk.jintang.module.my.activity;

import android.view.View;
import android.widget.TextView;

import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.module.my.Constant;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/15.
 */

public class GuiZeActivity extends BaseActivity {
    @BindView(R.id.tv_gui_ze)
    TextView tv_gui_ze;
    private String guiZeContent;

    @Override
    protected int getContentView() {

        return R.layout.act_gui_ze;
    }

    @Override
    protected void initView() {
        int guiZeType = getIntent().getIntExtra(Constant.IParam.guiZeType,-1);
        if(guiZeType==0){//签到
            setAppTitle("签到规则");
        }else if(guiZeType==1){//养生豆
            setAppTitle("养生豆规则");
        }
        guiZeContent = getIntent().getStringExtra(Constant.IParam.guiZeContent);
    }

    @Override
    protected void initData() {
        tv_gui_ze.setText(guiZeContent);
    }

    @Override
    protected void onViewClick(View v) {

    }
}
