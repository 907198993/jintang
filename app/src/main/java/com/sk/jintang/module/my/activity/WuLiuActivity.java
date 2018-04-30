package com.sk.jintang.module.my.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyFrameLayout;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.WuLiuObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by administartor on 2017/9/7.
 */

public class WuLiuActivity extends BaseActivity {
    @BindView(R.id.rv_wu_liu)
    RecyclerView rv_wu_liu;

    @BindView(R.id.tv_wu_liu_kuaidi)
    TextView tv_wu_liu_kuaidi;

    @BindView(R.id.tv_wu_liu_no)
    TextView tv_wu_liu_no;

    @BindView(R.id.tv_wu_liu_phone)
    TextView tv_wu_liu_phone;

    private String orderNo;
    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("物流信息");
        return R.layout.act_wu_liu;
    }

    @Override
    protected void initView() {
        orderNo = getIntent().getStringExtra(Constant.IParam.orderNo);
        adapter=new LoadMoreAdapter<WuLiuObj.CourierListBean>(mContext,R.layout.item_wu_liu,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, WuLiuObj.CourierListBean bean) {
                holder.setText(R.id.tv_wu_liu_content,bean.getContext())
                        .setText(R.id.tv_wu_liu_date,bean.getTime());

                MyTextView tv_wu_liu_line = (MyTextView) holder.getView(R.id.tv_wu_liu_line);
                MyTextView tv_wu_liu_point = (MyTextView) holder.getView(R.id.tv_wu_liu_point);
                MyFrameLayout fl_wu_liu_first_point = (MyFrameLayout) holder.getView(R.id.fl_wu_liu_first_point);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                layoutParams.width=1;
                layoutParams.gravity= Gravity.CENTER_HORIZONTAL;
                if(position==0){
                    tv_wu_liu_point.setSolidColor(ContextCompat.getColor(mContext,R.color.green));
                    layoutParams.setMargins(0, PhoneUtils.dip2px(mContext,25),0,0);
                    fl_wu_liu_first_point.setVisibility(View.VISIBLE);
                    tv_wu_liu_point.setVisibility(View.GONE);
                }else{
                    tv_wu_liu_point.setSolidColor(ContextCompat.getColor(mContext,R.color.c_cccccc));
                    layoutParams.setMargins(0,0,0,0);
                    fl_wu_liu_first_point.setVisibility(View.GONE);
                    tv_wu_liu_point.setVisibility(View.VISIBLE);
                }
                tv_wu_liu_point.complete();
                tv_wu_liu_line.setLayoutParams(layoutParams);
//                tv_wu_liu_line.setSolidColor(ContextCompat.getColor(mContext,R.color.c_cccccc));
//                tv_wu_liu_line.complete();
            }
        };
        rv_wu_liu.setLayoutManager(new LinearLayoutManager(mContext));
        rv_wu_liu.setAdapter(adapter);
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
        ApiRequest.getOrderWuLiuDetail(map, new MyCallBack<WuLiuObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(WuLiuObj obj) {
                adapter.setList(obj.getCourier_list(),true);
                tv_wu_liu_no.setText(obj.getCourier_number());
                tv_wu_liu_kuaidi.setText(obj.getCourier_name());
                tv_wu_liu_phone.setText(obj.getCourier_phone());
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}
