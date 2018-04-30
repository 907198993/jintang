package com.sk.jintang.module.my.activity;

import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyEditText;
import com.github.customview.MyRadioButton;
import com.sk.jintang.BuildConfig;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.event.PayEvent;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.MakeAccountOrderObj;
import com.sk.jintang.module.orderclass.bean.OrderBean;
import com.sk.jintang.tools.AndroidUtils;
import com.sk.jintang.tools.alipay.AliPay;
import com.sk.jintang.tools.alipay.OrderInfoUtil2_0;
import com.sk.jintang.tools.alipay.PayResult;
import com.sk.jintang.wxapi.WXPay;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by administartor on 2017/9/7.
 */

public class AccountChongZhiActivity extends BaseActivity {
    @BindView(R.id.tv_account_balance)
    TextView tv_account_balance;
    @BindView(R.id.et_account_chongzhi_money)
    MyEditText et_account_chongzhi_money;
    private boolean isPay;

    @Override
    protected int getContentView() {
        setAppTitle("账户充值");
        return R.layout.act_account_chong_zhi;
    }

    @Override
    public void finish() {
        if(isPay){
            setResult(RESULT_OK);
        }
        super.finish();
    }

    @Override
    protected void initView() {
        et_account_chongzhi_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int selectionStart = et_account_chongzhi_money.getSelectionStart();
                int selectionEnd = et_account_chongzhi_money.getSelectionEnd();
                if (!isOnlyPointNumber(et_account_chongzhi_money.getText().toString())){
                    s.delete(selectionStart - 1, selectionEnd);
                }
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(PayEvent.class, new MySubscriber() {
            @Override
            public void onMyNext(Object o) {
                et_account_chongzhi_money.setText(null);
                getData();
            }
        });
    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAccountBalance(map, new MyCallBack<BaseObj>(mContext, pl_load) {
            @Override
            public void onSuccess(BaseObj obj) {
                tv_account_balance.setText("¥"+obj.getAccount_balance());
            }
        });
    }
    @OnClick({R.id.tv_account_chongzhi_chongzhi})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_account_chongzhi_chongzhi:
                chongZhi();

            break;
        }
    }

    private void chongZhi() {
        String money = getSStr(et_account_chongzhi_money);
        if(TextUtils.isEmpty(money)){
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
        }
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage("是否确定充值?");
        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                isPay =true;
                startPay(resultMoney);
            }
        });
        mDialog.create().show();
    }

    private void startPay(double resultMoney) {
        showLoading();
        /////////////////////////////////////////首页跳转，类似于商品搜索界面
        Map<String,String>map=new HashMap<String,String>();
        map.put("userid",getUserId());
        map.put("money",resultMoney+"");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.accountChongZhi(map, new MyCallBack<MakeAccountOrderObj>(mContext) {
            @Override
            public void onSuccess(MakeAccountOrderObj obj) {
                selectPay(obj.getOrder_no(),obj.getMoney());
            }
        });

    }
    private void selectPay(String orderNo,double totalPrice) {
        BottomSheetDialog payDialog = new BottomSheetDialog(mContext);
        payDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        payDialog.setCancelable(false);
        payDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(payDialog.isShowing()&&keyCode== KeyEvent.KEYCODE_BACK){
                    payDialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        View payView = LayoutInflater.from(mContext).inflate(R.layout.popu_select_pay, null);
        ImageView iv_pay_cancle = payView.findViewById(R.id.iv_pay_cancle);
        iv_pay_cancle.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                payDialog.dismiss();
            }
        });
        TextView tv_pay_total = payView.findViewById(R.id.tv_pay_total);
        tv_pay_total.setText("¥" + totalPrice);
        RadioGroup rg_select_pay = payView.findViewById(R.id.rg_select_pay);
        TextView tv_pay_commit = payView.findViewById(R.id.tv_pay_commit);
        MyRadioButton rb_pay_online = payView.findViewById(R.id.rb_pay_online);
        rb_pay_online.setVisibility(View.GONE);
        View v_line = payView.findViewById(R.id.v_line);
        v_line.setVisibility(View.GONE);
        tv_pay_commit.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                payDialog.dismiss();
                int checkedRadioButtonId = rg_select_pay.getCheckedRadioButtonId();
                Log.i("===",orderNo+"===账户充值=="+totalPrice);
                switch (checkedRadioButtonId) {
                    case R.id.rb_pay_zhifubao:
                        zhiFuBaoPay(orderNo,totalPrice);
                        break;
                    case R.id.rb_pay_weixin:
                        OrderBean orderBean =new OrderBean();
                        orderBean.body="爱尚养御账户充值";
                        orderBean.nonceStr=getRnd();
                        orderBean.out_trade_no=orderNo;
                        if(BuildConfig.DEBUG){
                            orderBean.totalFee= AndroidUtils.mul(1,1);
                        }else{
                            orderBean.totalFee= AndroidUtils.mul(totalPrice,100);
                        }
                        orderBean.IP= AndroidUtils.getIP(mContext);
                        weiXinPay(orderBean);
                        break;
                    case R.id.rb_pay_online:
//                        onLinePay();
                        break;
                }
            }
        });
        payDialog.setContentView(payView);
        payDialog.show();
    }
    private void zhiFuBaoPay(String orderNo,double totalPrice) {
        double total;
        if(BuildConfig.DEBUG){
            total=0.01;
        }else{
            total =  totalPrice;
        }
//        double total =  totalPrice;
//        double total=0.01;
        AliPay bean = new AliPay();
        bean.setTotal_amount(total );
        bean.setOut_trade_no(orderNo);
        bean.setSubject(orderNo + "账户充值");
        bean.setBody("爱尚养御账户充值");
        String notifyUrl = SPUtils.getPrefString(mContext, Config.payType_ZFB, null);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(bean, notifyUrl);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String sign = OrderInfoUtil2_0.getSign(params, Config.zhifubao_rsa2, true);
        final String orderInfo = orderParam + "&" + sign;
        RXStart(new IOCallBack<Map>() {
            @Override
            public void call(Subscriber<? super Map> subscriber) {
                PayTask payTask = new PayTask(mContext);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Log.i("msp=====", result.toString());
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
            @Override
            public void onMyNext(Map map) {
                PayResult payResult = new PayResult(map);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                Log.i("==========", "1==========" + resultInfo);
                String resultStatus = payResult.getResultStatus();
                Log.i("==========", "2==========" + resultStatus);
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    showMsg("支付成功");
                    et_account_chongzhi_money.setText(null);
                    getData();
                } else if (TextUtils.equals(resultStatus, "6001")) {
                    showMsg("支付取消");
                } else {
                    showMsg("支付失败");
                }
            }
        });
    }
    private void weiXinPay(OrderBean orderBean) {
        SPUtils.setPrefBoolean(mContext, Config.accountChongZhi,true);
        new WXPay(mContext).pay(orderBean);
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
}
