package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.customview.MyEditText;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.AccountObj;
import com.sk.jintang.module.my.network.response.DefaultBankObj;
import com.sk.jintang.module.my.network.response.FenXiaoObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/4.
 */

public class YongJinTiXianActivity extends BaseActivity {

    @BindView(R.id.iv_yongjin_tx_bank)
    ImageView iv_yongjin_tx_bank;
    @BindView(R.id.tv_yongjin_tx_accout)
    TextView tv_yongjin_tx_accout;
    @BindView(R.id.ll_yongjin_tixian)
    LinearLayout ll_yongjin_tixian;
    @BindView(R.id.et_yongjin_tx_money)
    MyEditText et_yongjin_tx_money;
    private String accountId;

    private double account_balance;
    @Override
    protected int getContentView() {
        setAppTitle("提现");
        return R.layout.act_ti_xian_yong_jin;
    }

    @Override
    protected void initView() {
        et_yongjin_tx_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int selectionStart = et_yongjin_tx_money.getSelectionStart();
                int selectionEnd = et_yongjin_tx_money.getSelectionEnd();
                if (!isOnlyPointNumber(et_yongjin_tx_money.getText().toString())) {
                    s.delete(selectionStart - 1, selectionEnd);
                }
            }
        });
    }

    public boolean isOnlyPointNumber(String number) {//保留两位小数正则
        if (number.indexOf(".") == -1) {
            return true;
        } else if (number.indexOf(".") == 0) {
            number = "0" + number;
        }
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    @Override
    protected void initData() {
        showProgress();
        getAllMoney();
        getDefaultBank();
    }

    private void getDefaultBank() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getDefaultBank(map, new MyCallBack<List<DefaultBankObj>>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(List<DefaultBankObj> list) {
                if (notEmpty(list)) {
                    DefaultBankObj bankObj = list.get(0);
                    accountId = bankObj.getId() + "";
                    tv_yongjin_tx_accout.setText(bankObj.getBank_name());
                    Glide.with(mContext).load(bankObj.getBank_image()).error(R.color.c_press).into(iv_yongjin_tx_bank);
                    iv_yongjin_tx_bank.setVisibility(View.VISIBLE);
                } else {
                    iv_yongjin_tx_bank.setVisibility(View.GONE);
                    tv_yongjin_tx_accout.setText(null);
                }
            }
        });
    }

    private void getAllMoney() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getFenXiao(map, new MyCallBack<FenXiaoObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(FenXiaoObj obj) {
                account_balance = obj.getCommission();
//                et_yongjin_tx_money.setText("" + account_balance);
                et_yongjin_tx_money.setHint("最多可转出" + account_balance+"元");
            }
        });

    }
    @OnClick({R.id.ll_yongjin_tixian, R.id.tv_yongjin_tx_all, R.id.tv_yongjin_tx_commit})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_yongjin_tixian:
                STActivityForResult(AccountListActivity.class, 100);
                break;
            case R.id.tv_yongjin_tx_all:
                et_yongjin_tx_money.setText(account_balance+"");
                break;
            case R.id.tv_yongjin_tx_commit:
                String money = getSStr(et_yongjin_tx_money);
                if (TextUtils.isEmpty(getSStr(tv_yongjin_tx_accout))) {
                    showMsg("请选择到账账户");
                    return;
                } else if (TextUtils.isEmpty(money)) {
                    showMsg("请输入金额");
                    return;
                } else if (money.length() == 1 && money.indexOf(".") == 0) {
                    showMsg("请输入金额");
                    return;
                }
                if (money.indexOf(".") == 0) {
                    money = "0" + money;
                }
                if (money.indexOf(".") == money.length() - 1) {
                    money = money.replace(".", "");
                }
                double resultMoney = Double.parseDouble(money);
                if (resultMoney <= 0) {
                    showMsg("请输入金额");
                    return;
                }else if(resultMoney>account_balance){
                    showMsg("最多提现"+account_balance);
                    return;
                }
                TiXian(resultMoney);
                break;
        }
    }

    private void TiXian(double money) {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("account_id", accountId);
        map.put("amount", money + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.yongJinTiXian(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    AccountObj account = (AccountObj) data.getSerializableExtra(Constant.IParam.account);
                    accountId = account.getId() + "";
                    tv_yongjin_tx_accout.setText(account.getBank_card());
                    Glide.with(mContext).load(account.getBank_image()).error(R.color.c_press).into(iv_yongjin_tx_bank);
                    iv_yongjin_tx_bank.setVisibility(View.VISIBLE);
                    break;
            }
        } else if (resultCode == Constant.RCode.deleteDefaultAccount) {
            switch (requestCode) {
                case 100:
                    tv_yongjin_tx_accout.setText(null);
                    iv_yongjin_tx_bank.setImageDrawable(null);
                    iv_yongjin_tx_bank.setVisibility(View.GONE);
                    initData();
                    break;
            }
        }
    }


}
