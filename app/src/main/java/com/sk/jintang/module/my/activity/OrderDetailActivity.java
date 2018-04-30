package com.sk.jintang.module.my.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.view.Loading;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyTextView;
import com.sk.jintang.BuildConfig;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.event.GetOrderEvent;
import com.sk.jintang.module.my.event.GetOrderNumEvent;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.OrderDetailObj;
import com.sk.jintang.module.orderclass.activity.GoodsDetailActivity;
import com.sk.jintang.module.orderclass.activity.GoodsDetailTuanGouActivity;
import com.sk.jintang.module.orderclass.activity.GoodsDetailXianShiActivity;
import com.sk.jintang.module.orderclass.bean.OrderBean;
import com.sk.jintang.tools.AndroidUtils;
import com.sk.jintang.tools.alipay.AliPay;
import com.sk.jintang.tools.alipay.OrderInfoUtil2_0;
import com.sk.jintang.tools.alipay.PayResult;
import com.sk.jintang.wxapi.WXPay;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by administartor on 2017/9/7.
 */

public class OrderDetailActivity extends BaseActivity {
    String orderNo;
    @BindView(R.id.tv_order_detail_status)
    TextView tv_order_detail_status;
    @BindView(R.id.tv_order_detail_wuliu)
    TextView tv_order_detail_wuliu;
    @BindView(R.id.tv_order_detail_wuliu_date)
    TextView tv_order_detail_wuliu_date;
    @BindView(R.id.tv_order_detail_name)
    TextView tv_order_detail_name;
    @BindView(R.id.tv_order_detail_phone)
    TextView tv_order_detail_phone;
    @BindView(R.id.tv_order_detail_address)
    TextView tv_order_detail_address;
    @BindView(R.id.rv_order_detail)
    RecyclerView rv_order_detail;
    @BindView(R.id.tv_order_detail_total)
    TextView tv_order_detail_total;
    @BindView(R.id.tv_order_detail_yunfei)
    TextView tv_order_detail_yunfei;
    @BindView(R.id.tv_order_detail_vouchers)
    TextView tv_order_detail_vouchers;
    @BindView(R.id.tv_order_detail_ysd_money)
    TextView tv_order_detail_ysd_money;
    @BindView(R.id.tv_order_detail_num)
    TextView tv_order_detail_num;
    @BindView(R.id.tv_order_detail_heji)
    TextView tv_order_detail_heji;
    @BindView(R.id.tv_order_detail_no)
    TextView tv_order_detail_no;
    @BindView(R.id.tv_order_detail_date)
    TextView tv_order_detail_date;
    @BindView(R.id.tv_order_detail_zffs)
    TextView tv_order_detail_zffs;
    @BindView(R.id.tv_order_detail_psfs)
    TextView tv_order_detail_psfs;
    @BindView(R.id.tv_order_detail_fplx)
    TextView tv_order_detail_fplx;

    @BindView(R.id.ll_order_detail_fukuan_date)
    LinearLayout ll_order_detail_fukuan_date;
    @BindView(R.id.ll_order_detail_xiadan_date)
    LinearLayout ll_order_detail_xiadan_date;
    @BindView(R.id.ll_order_detail_cancel_date)
    LinearLayout ll_order_detail_cancel_date;

    @BindView(R.id.tv_order_detail_xiadan_date)
    TextView tv_order_detail_xiadan_date;
    @BindView(R.id.tv_order_detail_cancel_date)
    TextView tv_order_detail_cancel_date;

    @BindView(R.id.ll_order_detail_bottom)
    LinearLayout ll_order_detail_bottom;
    @BindView(R.id.ll_order_detail_fahuo)
    LinearLayout ll_order_detail_fahuo;
    @BindView(R.id.tv_order_detail_qxdd)
    MyTextView tv_order_detail_qxdd;
    @BindView(R.id.tv_order_detail_qzf)
    MyTextView tv_order_detail_qzf;
    @BindView(R.id.tv_order_detail_sqtk)
    MyTextView tv_order_detail_sqtk;
    @BindView(R.id.tv_order_detail_qrsh)
    MyTextView tv_order_detail_qrsh;
    @BindView(R.id.tv_order_detail_qpj)
    MyTextView tv_order_detail_qpj;
    @BindView(R.id.tv_order_detail_delete)
    MyTextView tv_order_detail_delete;
    private BaseRecyclerAdapter adapter;
    private OrderDetailObj orderDetailObj;
    private int orderStatus;
    private String action;

    @Override
    protected int getContentView() {
        setAppTitle("订单详情");
        return R.layout.act_order_detail;
    }

    @Override
    protected void initView() {
        orderNo = getIntent().getStringExtra(Constant.IParam.orderNo);
        action = getIntent().getAction();
        if(TextUtils.isEmpty(action)){
            tv_order_detail_delete.setVisibility(View.GONE);
        }else{
            tv_order_detail_delete.setVisibility(View.VISIBLE);
        }
        rv_order_detail.setLayoutManager(new LinearLayoutManager(mContext));
        rv_order_detail.addItemDecoration(getItemDivider());
        rv_order_detail.setNestedScrollingEnabled(false);


        adapter = new BaseRecyclerAdapter<OrderDetailObj.GoodsListBean>(mContext, R.layout.item_order_detail) {
            @Override
            public void bindData(RecyclerViewHolder viewHolder, int i, OrderDetailObj.GoodsListBean item) {
                ImageView imageView = viewHolder.getImageView(R.id.iv_order_goods);
                Glide.with(mContext).load(item.getGoods_images()).error(R.color.c_press).into(imageView);
                viewHolder.setText(R.id.tv_order_goods_name, item.getGoods_name())
                        .setText(R.id.tv_order_goods_guige, item.getGoods_specification())
                        .setText(R.id.tv_order_goods_price, "¥" + item.getGoods_unitprice())
                        .setText(R.id.tv_order_goods_num, "x" + item.getGoods_number());

                viewHolder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        //0商品不存在 1普通商品 2限时抢购 3团购),staus商品状态(0商品不存在或者活动已结束 1商品存在活动没结束
                        Intent intent=new Intent();
                        intent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.goodsId,item.getGoods_id()+"");
                        if(item.getCode()==0||item.getCode()==1){
                            STActivity(intent,GoodsDetailActivity.class);
                        }else if(item.getCode()==2){
                            intent.setAction(Config.IParam.xianShiQiangGou);
                            STActivity(intent,GoodsDetailXianShiActivity.class);
                        }else if(item.getCode()==3){
                            STActivity(intent,GoodsDetailTuanGouActivity.class);
                        }
                    }
                });
            }
        };
        rv_order_detail.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("order_no", orderNo);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getOrderDetail(map, new MyCallBack<OrderDetailObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(OrderDetailObj obj) {
                orderDetailObj = obj;
                adapter.setList(obj.getGoods_list(), true);
                if (notEmpty(obj.getCourier_list())) {
                    tv_order_detail_wuliu.setText(obj.getCourier_list().get(0).getContext());
                    tv_order_detail_wuliu_date.setText(obj.getCourier_list().get(0).getTime());
                }
                tv_order_detail_xiadan_date.setText(obj.getCreate_add_time());
                tv_order_detail_cancel_date.setText(obj.getCancel_order_time());
                tv_order_detail_name.setText(obj.getAddress_recipient());
                tv_order_detail_phone.setText(obj.getAddress_phone());
                tv_order_detail_address.setText(obj.getShipping_address());

                tv_order_detail_total.setText("¥" + obj.getGoods_total_amount());
                tv_order_detail_yunfei.setText("¥" + obj.getFreight());
                tv_order_detail_vouchers.setText("-¥" + obj.getYouhui_money());
                tv_order_detail_ysd_money.setText("-¥" + obj.getKeeping_bean());
                tv_order_detail_num.setText("共" + obj.getGoods_list_count() + "件商品");
                tv_order_detail_heji.setText("  合计:¥" + obj.getCombined());

                tv_order_detail_no.setText(obj.getOrder_no());
                tv_order_detail_date.setText(obj.getPayment_add_time());
                tv_order_detail_zffs.setText(obj.getPay_id());
                tv_order_detail_psfs.setText(obj.getCourier_name());
                tv_order_detail_fplx.setText(obj.getInvoice_type());

                orderStatus = obj.getOrder_status();
                switch (orderStatus) {
                    case 1:
                        tv_order_detail_status.setText("待付款");
                        ll_order_detail_fukuan_date.setVisibility(View.GONE);
//                        ll_order_detail_fahuo.setVisibility(View.GONE);
                        ll_order_detail_cancel_date.setVisibility(View.GONE);


                        tv_order_detail_qxdd.setVisibility(View.VISIBLE);
                        tv_order_detail_qzf.setVisibility(View.VISIBLE);
                        tv_order_detail_sqtk.setVisibility(View.GONE);
                        tv_order_detail_qrsh.setVisibility(View.GONE);
                        tv_order_detail_qpj.setVisibility(View.GONE);
                        break;
                    case 2:
                        tv_order_detail_status.setText("待发货");
                        ll_order_detail_fukuan_date.setVisibility(View.VISIBLE);
                        ll_order_detail_cancel_date.setVisibility(View.GONE);
//                        ll_order_detail_fahuo.setVisibility(View.GONE);

                        tv_order_detail_qzf.setVisibility(View.GONE);
                        if(obj.getCode()==0){
                            tv_order_detail_sqtk.setVisibility(View.VISIBLE);
                            tv_order_detail_qxdd.setVisibility(View.GONE);
                        }else{
                            tv_order_detail_sqtk.setVisibility(View.GONE);
                            tv_order_detail_qxdd.setVisibility(View.VISIBLE);
                        }
                        tv_order_detail_qrsh.setVisibility(View.GONE);
                        tv_order_detail_qpj.setVisibility(View.GONE);
                        break;
                    case 3:
                        tv_order_detail_status.setText("待收货");
                        ll_order_detail_fukuan_date.setVisibility(View.VISIBLE);
                        ll_order_detail_cancel_date.setVisibility(View.GONE);
                        ll_order_detail_fahuo.setVisibility(View.VISIBLE);

                        tv_order_detail_qxdd.setVisibility(View.GONE);
                        tv_order_detail_qzf.setVisibility(View.GONE);
                        if(obj.getCode()==0){
                            tv_order_detail_sqtk.setVisibility(View.VISIBLE);
                        }else{
                            tv_order_detail_sqtk.setVisibility(View.GONE);
                        }
                        tv_order_detail_qrsh.setVisibility(View.VISIBLE);
                        tv_order_detail_qpj.setVisibility(View.GONE);
                        break;
                    case 4:
                        tv_order_detail_status.setText("待评价");
                        ll_order_detail_fukuan_date.setVisibility(View.VISIBLE);
                        ll_order_detail_cancel_date.setVisibility(View.GONE);
                        ll_order_detail_fahuo.setVisibility(View.VISIBLE);

                        tv_order_detail_qxdd.setVisibility(View.GONE);
                        tv_order_detail_qzf.setVisibility(View.GONE);
                        if(obj.getCode()==0){
                            tv_order_detail_sqtk.setVisibility(View.VISIBLE);
                        }else{
                            tv_order_detail_sqtk.setVisibility(View.GONE);
                        }
                        tv_order_detail_qrsh.setVisibility(View.GONE);
                        tv_order_detail_qpj.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        tv_order_detail_status.setText("已完成");
                        ll_order_detail_fukuan_date.setVisibility(View.VISIBLE);
                        ll_order_detail_cancel_date.setVisibility(View.GONE);
                        ll_order_detail_fahuo.setVisibility(View.VISIBLE);

                        tv_order_detail_qxdd.setVisibility(View.GONE);
                        tv_order_detail_qzf.setVisibility(View.GONE);
                        tv_order_detail_sqtk.setVisibility(View.GONE);
                        tv_order_detail_qrsh.setVisibility(View.GONE);
                        tv_order_detail_qpj.setVisibility(View.GONE);
                        ll_order_detail_bottom.setVisibility(View.GONE);
                        break;
                    case 6:
                        tv_order_detail_status.setText("已取消");
                        ll_order_detail_fukuan_date.setVisibility(View.VISIBLE);
                        ll_order_detail_cancel_date.setVisibility(View.VISIBLE);
//                        ll_order_detail_fahuo.setVisibility(View.GONE);

                        tv_order_detail_qxdd.setVisibility(View.GONE);
                        tv_order_detail_qzf.setVisibility(View.GONE);
                        tv_order_detail_sqtk.setVisibility(View.GONE);
                        tv_order_detail_qrsh.setVisibility(View.GONE);
                        tv_order_detail_qpj.setVisibility(View.GONE);
                        ll_order_detail_bottom.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

    }

    @OnClick({R.id.tv_order_detail_delete,R.id.ll_order_detail_wuliu,R.id.tv_order_detail_qxdd, R.id.tv_order_detail_qzf, R.id.tv_order_detail_sqtk, R.id.tv_order_detail_qrsh, R.id.tv_order_detail_qpj})
    public void onViewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_order_detail_delete:
                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认删除订单?");
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
                        deleteOrder(orderNo);
                    }
                });
                mDialog.create().show();
                break;
            case R.id.ll_order_detail_wuliu:
                intent=new Intent();
                intent.putExtra(Constant.IParam.orderNo,orderNo);
                STActivity(intent,WuLiuActivity.class);
                break;
            case R.id.tv_order_detail_qxdd:
                mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("确认取消订单吗?");
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
                        cancelOrder(orderNo);
                    }
                });
                mDialog.create().show();
                break;
            case R.id.tv_order_detail_qzf:
                //验证订单是否正常，防止活动过期
                showLoading();
                yanZhengOrder(orderDetailObj.getOrder_no(),orderDetailObj.getCombined());
                break;
            case R.id.tv_order_detail_sqtk:
                intent=new Intent();
                intent.putExtra(Constant.IParam.orderNo,orderNo);
                STActivityForResult(intent, TuiKuanActivity.class,100);
                break;
            case R.id.tv_order_detail_qrsh:
                mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认收货?");
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
                        sureOrder(orderNo);
                    }
                });
                mDialog.create().show();
                break;
            case R.id.tv_order_detail_qpj:
                intent=new Intent();
                intent.putExtra(Constant.IParam.orderNo,orderNo);
                STActivityForResult(intent, FaBiaoEvaluateActivity.class,201);
                break;
        }
    }

    private void deleteOrder(String order_no) {
        Loading.show(mContext);
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",SPUtils.getPrefString(mContext, Config.user_id,null));
        map.put("order_no",order_no);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.deleteOrder(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                RxBus.getInstance().post(new GetOrderEvent());
                RxBus.getInstance().post(new GetOrderNumEvent());
                ToastUtils.showToast(mContext,obj.getMsg());
                finish();
            }
        });
    }

    private void yanZhengOrder(String order_no, double combined) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",order_no);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.yanZhengOrder(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                selectPay(order_no,combined);
            }
        });
    }

    private void sureOrder(String order_no) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id", SPUtils.getPrefString(mContext, Config.user_id,null));
        map.put("order_no",order_no);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.sureOrder(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                RxBus.getInstance().post(new GetOrderEvent());
                ToastUtils.showToast(mContext,obj.getMsg());
                finish();
            }
        });

    }

    private void cancelOrder(String order_no) {
        Loading.show(mContext);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id", SPUtils.getPrefString(mContext, Config.user_id,null));
        map.put("order_no",order_no);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.cancelOrder(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj) {
                RxBus.getInstance().post(new GetOrderEvent());
                ToastUtils.showToast(mContext,obj.getMsg());
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    setResult(RESULT_OK);
                    finish();
                break;
                case 201:
                    setResult(RESULT_OK);
                    finish();
                break;
            }
        }
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
        tv_pay_commit.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                payDialog.dismiss();
                int checkedRadioButtonId = rg_select_pay.getCheckedRadioButtonId();
                switch (checkedRadioButtonId) {
                    case R.id.rb_pay_zhifubao:
                        zhiFuBaoPay(orderNo,totalPrice);
                        break;
                    case R.id.rb_pay_weixin:
                        OrderBean orderBean =new OrderBean();
                        orderBean.body="爱尚养御订单";
                        orderBean.nonceStr=getRnd();
                        orderBean.out_trade_no=orderNo;
//                        orderBean.totalFee= AndroidUtils.mul(1,1);
                        orderBean.totalFee= AndroidUtils.mul(totalPrice,100);
                        orderBean.IP= AndroidUtils.getIP(mContext);
                        weiXinPay(orderBean);
                        break;
                    case R.id.rb_pay_online:
                        onLinePay(orderNo,totalPrice);
                        break;
                }
            }


        });
        payDialog.setContentView(payView);
        payDialog.show();
    }
    private void onLinePay(String orderNo, double totalPrice) {
        Loading.show(mContext);
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",SPUtils.getPrefString(mContext,Config.user_id,null));
        map.put("order_no",orderNo);
        map.put("money",totalPrice+"");
        map.put("sign",GetSign.getSign(map));
        com.sk.jintang.module.orderclass.network.ApiRequest.yuEPay(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj) {
                getData();
                RxBus.getInstance().post(new GetOrderEvent());
                ToastUtils.showToast(mContext,obj.getMsg());
            }
        });

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
        bean.setSubject(orderNo + "订单交易");
        bean.setBody("爱尚养御订单");
        String notifyUrl = SPUtils.getPrefString(mContext, Config.payType_ZFB, null);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(bean, notifyUrl);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String sign = OrderInfoUtil2_0.getSign(params, Config.zhifubao_rsa2, true);
        final String orderInfo = orderParam + "&" + sign;
        Loading.show(mContext);
        Observable.create(new Observable.OnSubscribe<Map>() {
            @Override
            public void call(Subscriber<? super Map> subscriber) {
                PayTask payTask = new PayTask((Activity) mContext);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Log.i("msp=====", result.toString());
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new MySubscriber<Map>() {
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
                    getData();
                    RxBus.getInstance().post(new GetOrderEvent());
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.equals(resultStatus, "6001")) {
                    Loading.dismissLoading();
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(mContext, "支付取消", Toast.LENGTH_SHORT).show();
                } else {
                    Loading.dismissLoading();
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Loading.dismissLoading();
            }
        });


    }
    private void weiXinPay(OrderBean orderBean) {
        new WXPay(mContext).pay(orderBean);
    }
}
