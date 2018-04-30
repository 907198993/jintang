package com.sk.jintang.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.github.customview.MyEditText;
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
 * Created by administartor on 2017/9/6.
 */

public class YiJianFanKuiActivity extends BaseActivity {
    @BindView(R.id.et_yjfk_content)
    EditText et_yjfk_content;
    @BindView(R.id.et_yjfk_phone)
    MyEditText et_yjfk_phone;

    @Override
    protected int getContentView() {
        setAppTitle("意见反馈");
        return R.layout.act_yi_jian_fan_kui;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_fan_kui_commit})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_fan_kui_commit:
                String content = getSStr(et_yjfk_content);
                String phone = getSStr(et_yjfk_phone);
                if(TextUtils.isEmpty(content)){
                    showMsg("请输入反馈内容");
                    return;
                }else if(TextUtils.isEmpty(phone)){
                    showMsg("请输入联系方式");
                    return;
                }else if(!GetSign.isMobile(phone)){
                    showMsg("联系方式格式不正确");
                    return;
                }
                commitYJFK(content,phone);
            break;
        }
    }

    private void commitYJFK(String content, String phone) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("content",content);
        map.put("contact_way",phone);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.YJFK(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                et_yjfk_content.setText(null);
                et_yjfk_phone.setText(null);
            }
        });
    }
}
