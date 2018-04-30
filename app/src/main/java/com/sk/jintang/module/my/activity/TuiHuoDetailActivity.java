package com.sk.jintang.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.customview.FlowLayout;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.TuiHuoDetailObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by administartor on 2017/9/6.
 */

public class TuiHuoDetailActivity extends BaseActivity {

    @BindView(R.id.iv_tuihuo_detail)
    ImageView iv_tuihuo_detail;
    @BindView(R.id.tv_tuihuo_detail_status)
    TextView tv_tuihuo_detail_status;
    @BindView(R.id.tv_tuihuo_detail_time)
    TextView tv_tuihuo_detail_time;
    @BindView(R.id.tv_tuihuo_detail_content)
    TextView tv_tuihuo_detail_content;
    @BindView(R.id.tv_tuihuo_detail_project)
    TextView tv_tuihuo_detail_project;
    @BindView(R.id.tv_tuihuo_detail_price)
    TextView tv_tuihuo_detail_price;
    @BindView(R.id.tv_tuihuo_detail_reason)
    TextView tv_tuihuo_detail_reason;
    @BindView(R.id.fl_tuihuo_detail)
    FlowLayout fl_tuihuo_detail;
    private String orderNo;

    @Override
    protected int getContentView() {
        setAppTitle("我的退换货");
        return R.layout.act_tui_huo_detail;
    }

    @Override
    protected void initView() {
        orderNo = getIntent().getStringExtra(Constant.IParam.tuiHuoDetail);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("order_no",orderNo);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.tuiHuanHuoDetail(map, new MyCallBack<TuiHuoDetailObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(TuiHuoDetailObj obj) {
                fl_tuihuo_detail.removeAllViews();
                tv_tuihuo_detail_time.setText(obj.getRefund_add_time());
                tv_tuihuo_detail_content.setText(obj.getRefund_remark());
                tv_tuihuo_detail_project.setText(obj.getRefund_type());
                tv_tuihuo_detail_price.setText("¥"+obj.getRefund_amount());
                tv_tuihuo_detail_reason.setText(obj.getRefund_reason());

                /*(1退货中 2退货成功 3退货失败)*/
                int refundStatus = obj.getRefund_status();
                switch (refundStatus){
                    case 1:
                        tv_tuihuo_detail_status.setText("退款中");
                        iv_tuihuo_detail.setImageResource(R.drawable.tuihuo1);
                    break;
                    case 2:
                        tv_tuihuo_detail_status.setText("退款成功");
                        iv_tuihuo_detail.setImageResource(R.drawable.tuihuo3);
                    break;
                    case 3:
                        tv_tuihuo_detail_status.setText("退款失败");
                        iv_tuihuo_detail.setImageResource(R.drawable.tuihuo2);
                    break;
                }

                if(!TextUtils.isEmpty(obj.getRefund_voucher1())){
                    ImageView imageView1=new ImageView(mContext);
                    FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.width=PhoneUtils.dip2px(mContext,100);
                    layoutParams.height=PhoneUtils.dip2px(mContext,100);
                    imageView1.setLayoutParams(layoutParams);
                    imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    Glide.with(mContext).load(obj.getRefund_voucher1()).error(R.color.c_press).into(imageView1);
                    fl_tuihuo_detail.addView(imageView1);
                }
                if(!TextUtils.isEmpty(obj.getRefund_voucher2())){
                    ImageView imageView1=new ImageView(mContext);
                    FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.width=PhoneUtils.dip2px(mContext,100);
                    layoutParams.height=PhoneUtils.dip2px(mContext,100);
                    imageView1.setLayoutParams(layoutParams);
                    imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    Glide.with(mContext).load(obj.getRefund_voucher2()).error(R.color.c_press).into(imageView1);
                    fl_tuihuo_detail.addView(imageView1);
                }
                if(!TextUtils.isEmpty(obj.getRefund_voucher3())){
                    ImageView imageView1=new ImageView(mContext);
                    FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.width=PhoneUtils.dip2px(mContext,100);
                    layoutParams.height=PhoneUtils.dip2px(mContext,100);
                    imageView1.setLayoutParams(layoutParams);
                    imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    Glide.with(mContext).load(obj.getRefund_voucher3()).error(R.color.c_press).into(imageView1);
                    fl_tuihuo_detail.addView(imageView1);
                }
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}
