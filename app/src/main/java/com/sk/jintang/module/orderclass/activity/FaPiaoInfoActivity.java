package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.customview.MyEditText;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.module.orderclass.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/2.
 */

public class FaPiaoInfoActivity extends BaseActivity {
    @BindView(R.id.rg_fapiao_type)
    RadioGroup rg_fapiao_type;
    @BindView(R.id.rg_fapiao_head)
    RadioGroup rg_fapiao_head;
    @BindView(R.id.rb_fapiao_zhi)
    RadioButton rb_fapiao_zhi;
    @BindView(R.id.rb_fapiao_dian)
    RadioButton rb_fapiao_dian;
    @BindView(R.id.rb_fapiao_geren)
    RadioButton rb_fapiao_geren;
    @BindView(R.id.rb_fapiao_danwei)
    RadioButton rb_fapiao_danwei;
    @BindView(R.id.tv_fapiao_name)
    MyEditText tv_fapiao_name;
    @BindView(R.id.tv_fapiao_code)
    MyEditText tv_fapiao_code;
    @BindView(R.id.ll_fapiao_danwei)
    LinearLayout ll_fapiao_danwei;

    String faPiaoType="纸质";
    String faPiaoHead="个人";

    @Override
    protected int getContentView() {
        setAppTitle("发票信息");
        return R.layout.act_fa_piao_info;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String type = intent.getStringExtra(Constant.IParam.faPiaoType);
        String head = intent.getStringExtra(Constant.IParam.faPiaoHead);
        String name = intent.getStringExtra(Constant.IParam.faPiaoName);
        String code = intent.getStringExtra(Constant.IParam.faPiaoCode);

        if(!TextUtils.isEmpty(type)){
            if("纸质".equals(type)){
                rb_fapiao_zhi.setChecked(true);
            }else{
                rb_fapiao_dian.setChecked(true);
            }
            faPiaoType=type;
        }
        if(!TextUtils.isEmpty(head)){
            if("个人".equals(head)){
                tv_fapiao_code.setVisibility(View.GONE);
                rb_fapiao_geren.setChecked(true);
            }else{
                rb_fapiao_danwei.setChecked(true);
                tv_fapiao_code.setVisibility(View.VISIBLE);
            }
            faPiaoHead=head;
        }
        tv_fapiao_name.setText(name);
        tv_fapiao_code.setText(code);

        rg_fapiao_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_fapiao_zhi:
                        faPiaoType="纸质";
                    break;
                    case R.id.rb_fapiao_dian:
                        faPiaoType="电子";
                    break;
                }
            }
        });
        rg_fapiao_head.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_fapiao_geren:
                        faPiaoHead="个人";
                        tv_fapiao_code.setVisibility(View.GONE);
                        break;
                    case R.id.rb_fapiao_danwei:
                        faPiaoHead="单位";
                        tv_fapiao_code.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.tv_fapiao_commit})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fapiao_commit:
                Intent intent=new Intent();
                if(TextUtils.isEmpty(getSStr(tv_fapiao_name))){
                    showMsg("请输入抬头名称");
                    return;
                }
                if(faPiaoHead.equals("单位")){
                    if(TextUtils.isEmpty(getSStr(tv_fapiao_code))){
                        showMsg("请输入公司税号");
                        return;
                    }
                    intent.putExtra(Constant.IParam.faPiaoCode,getSStr(tv_fapiao_code));
                }
                intent.putExtra(Constant.IParam.faPiaoName,getSStr(tv_fapiao_name));
                intent.putExtra(Constant.IParam.faPiaoType,faPiaoType);
                intent.putExtra(Constant.IParam.faPiaoHead,faPiaoHead);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
