package com.sk.jintang.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/18.
 */

public class AddAliPayAccountActivity extends BaseActivity {


    @BindView(R.id.et_add_ali_account)
    EditText et_add_ali_account;
    @BindView(R.id.et_add_ali_name)
    EditText et_add_ali_name;
    @BindView(R.id.tv_add_account_name)
    TextView tv_add_account_name;

    private String type;
    private String title;
    @Override
    protected int getContentView() {
        return R.layout.act_add_alipay_account;
    }
    @Override
    protected void initView() {
        type = getIntent().getStringExtra(Constant.IParam.type);
        if("1".equals(type)){
            title="支付宝";
        }else{
            title="微信";
        }
        tv_add_account_name.setText(title+"账号");
        et_add_ali_account.setHint("请输入"+title+"账号");
        et_add_ali_name.setHint("请输入"+title+"姓名");
        setAppTitle("添加"+title);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_add_ali_account_commit})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_ali_account_commit:
                if(TextUtils.isEmpty(getSStr(et_add_ali_account))){
                    showMsg("请输入"+title+"账号");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_add_ali_name))){
                    showMsg("请输入"+title+"姓名");
                    return;
                }
                addAccount();
                break;
        }
    }

    private void addAccount() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("realname", getSStr(et_add_ali_name));
        map.put("type", type);
        map.put("alipay_number",getSStr(et_add_ali_account));
        map.put("sign", GetSign.getSign(map));
        ApiRequest.addAlipayAccount(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });

    }


}
