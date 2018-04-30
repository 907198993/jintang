package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
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

public class TiXianActivity extends BaseActivity {
    @BindView(R.id.et_tx_account_money)
    TextView et_tx_account_money;
    @BindView(R.id.tv_tx_commit)
    TextView tv_tx_commit;
    @BindView(R.id.tv_ti_xian_fangshi)
    TextView tv_ti_xian_fangshi;
    @BindView(R.id.tv_ti_xian_max)
    TextView tv_ti_xian_max;
    @BindView(R.id.tv_tixian_title)
    TextView tv_tixian_title;
    @BindView(R.id.et_tx_money)
    EditText et_tx_money;

    private int tiXianType=0;
    private String accountId;
    private String type="1";
    private double account_balance;

    @Override
    protected int getContentView() {
        return R.layout.act_ti_xian;
    }

    @Override
    protected void initView() {
        tiXianType= getIntent().getIntExtra(Constant.IParam.tiXianType,0);
        if(tiXianType==0){
            setAppTitle("账户提现");
            tv_tixian_title.setText("账户余额");
        }else{
            setAppTitle("佣金提现");
            tv_tixian_title.setText("佣金余额");
        }

        et_tx_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int selectionStart = et_tx_money.getSelectionStart();
                int selectionEnd = et_tx_money.getSelectionEnd();
                if (!isOnlyPointNumber(et_tx_money.getText().toString())){
                    s.delete(selectionStart - 1, selectionEnd);
                }
            }
        });
    }
    public boolean isOnlyPointNumber(String number) {//保留两位小数正则
        if(number.indexOf(".")==-1){
            return true;
        }else if(number.indexOf(".")==0){
            number="0"+number;
        }
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
    @Override
    protected void initData() {
        showProgress();
        getMyBalance();
//        getDefaultBank();
    }

    private void getDefaultBank() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getDefaultBank(map, new MyCallBack<List<DefaultBankObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<DefaultBankObj> list) {
                /*if(notEmpty(list)){
                    DefaultBankObj bankObj = list.get(0);
                    accountId=bankObj.getId()+"";
                    tv_tx_accout.setText(bankObj.getBank_name());
                    Glide.with(mContext).load(bankObj.getBank_image()).error(R.color.c_press).into(iv_tx_bank);
                    iv_tx_bank.setVisibility(View.VISIBLE);
                }else{
                    iv_tx_bank.setVisibility(View.GONE);
                    tv_tx_accout.setText(null);
                }*/
            }
        });
    }

    private void getMyBalance() {
        if(tiXianType==0){//账户获取余额
            accountGetMyBalance();
        }else{//佣金获取余额
            yongJinGetMyBalance();
        }

    }

    private void yongJinGetMyBalance() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getFenXiao(map, new MyCallBack<FenXiaoObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(FenXiaoObj obj) {
                  account_balance = obj.getCommission();
//                et_yongjin_tx_money.setText("" + account_balance);
                et_tx_account_money.setText("¥"+ account_balance);
                tv_ti_xian_max.setText(account_balance +"元");
            }
        });
    }

    private void accountGetMyBalance() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getBalance(map, new MyCallBack<BaseObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(BaseObj obj) {
                  account_balance = obj.getAccount_balance();
                et_tx_account_money.setText("¥"+ account_balance);
                tv_ti_xian_max.setText(account_balance +"元");
            }
        });
    }

    @OnClick({ R.id.tv_tx_commit,R.id.ll_ti_xian_fangshi})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ll_ti_xian_fangshi:
                showTiXianFangShi();
                break;

            case R.id.tv_tx_commit:
                String money = getSStr(et_tx_money);
                if(TextUtils.isEmpty(accountId)){
                    showMsg("请选择提现方式");
                    return;
                }else if(TextUtils.isEmpty(money)){
                    showMsg("请输入金额");
                    return;
                }else if(money.length()==1&&money.indexOf(".")==0){
                    showMsg("请输入金额");
                    return;
                }
                if(money.indexOf(".")==0){
                    money="0"+money;
                }
                if(money.indexOf(".")==money.length()-1){
                    money=money.replace(".","");
                }
                double resultMoney = Double.parseDouble(money);
                if(resultMoney<=0){
                    showMsg("请输入金额");
                    return;
                }else if(resultMoney> account_balance){
                    showMsg("最多提现"+ account_balance);
                    return;
                }
                TiXian(resultMoney);
                break;
        }
    }

    private void showTiXianFangShi() {
        BottomSheetDialog sheetDialog=new BottomSheetDialog(mContext);
        sheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View view = getLayoutInflater().inflate(R.layout.popu_ti_xian_fangshi, null);
        View rb_ti_xian_ali = view.findViewById(R.id.rb_ti_xian_ali);
        View rb_ti_xian_wx = view.findViewById(R.id.rb_ti_xian_wx);
        rb_ti_xian_ali.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                sheetDialog.dismiss();
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.type,"1");
                STActivityForResult(intent,AliPayAccountActivity.class,200);
            }
        });
        rb_ti_xian_wx.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                sheetDialog.dismiss();
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.type,"2");
                STActivityForResult(intent,AliPayAccountActivity.class,203);
            }
        });
        View rb_ti_xian_bank = view.findViewById(R.id.rb_ti_xian_bank);
        rb_ti_xian_bank.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                sheetDialog.dismiss();
                STActivityForResult(AccountListActivity.class,300);
            }
        });
        sheetDialog.setContentView(view);
        sheetDialog.show();
    }

    private void TiXian(double money) {
        if(tiXianType==0){//账户提现
            accountTiXian(money);
        }else{//佣金提现
            yongJinTiXian(money);
        }
    }

    private void yongJinTiXian(double money) {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("type",type);
        map.put("account_id", accountId);
        map.put("amount", money + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.yongJinTiXian(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                STActivityForResult(TiXianSuccessActivity.class,400);
            }
        });
    }

    private void accountTiXian(double money) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type",type);
        map.put("account_id",accountId);
        map.put("amount",money+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.TiXian(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                STActivityForResult(TiXianSuccessActivity.class,400);
            }
        });
    }

    private void getAllMoney() {
        showLoading();
       /* addSubscription(ApiRequest.getAllMoney(getUserId(),getSign()).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                et_tx_money.setText(obj.getAllmoney()+"");
            }
        }));*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            String account;
            switch (requestCode){
                case 200://支付宝提现
                    type="1";
                    account = (String) data.getSerializableExtra(Constant.IParam.account);
                    accountId= (String) data.getSerializableExtra(Constant.IParam.accountId);
                    tv_ti_xian_fangshi.setText("支付宝"+account);
                break;
                case 203://微信提现
                    type="3";
                    account = (String) data.getSerializableExtra(Constant.IParam.account);
                    accountId= (String) data.getSerializableExtra(Constant.IParam.accountId);
                    tv_ti_xian_fangshi.setText("微信"+account);
                break;
                case 300://银行卡提现
                    type="2";
                    account = (String) data.getSerializableExtra(Constant.IParam.account);
                    accountId= (String) data.getSerializableExtra(Constant.IParam.accountId);
                    tv_ti_xian_fangshi.setText(account);
                break;
                case 400:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }else if(resultCode==Constant.RCode.deleteDefaultAccount){
            tv_ti_xian_fangshi.setText("请选择提现方式");
            accountId=null;
        }else if(resultCode==Constant.RCode.tiXianSuccess){
            getMyBalance();
            et_tx_money.setText(null);
            setResult(RESULT_OK);
        }
    }
}
