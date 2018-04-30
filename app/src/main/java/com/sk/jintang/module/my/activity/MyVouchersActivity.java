package com.sk.jintang.module.my.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.adapter.VouchersFragmentAdapter;
import com.sk.jintang.module.my.fragment.VouchersFragment;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.VouchersNumObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

//import com.sk.yangyu.module.my.event.VoucherEvent;
//import com.sk.yangyu.module.my.network.response.VouchersNumObj;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyVouchersActivity extends BaseActivity {
    @BindView(R.id.tl_my_vouchers)
    TabLayout tl_my_vouchers;

    @BindView(R.id.vp_my_vouchers)
    ViewPager vp_my_vouchers;

    VouchersFragmentAdapter adapter;
    List<Fragment> list;

    VouchersFragment weiShiYongFragment;
    VouchersFragment yiShiYongFragment;
    VouchersFragment yiGuoQiFragment;



    @Override
    protected int getContentView() {
        setAppTitle("我的抵用券");
        return R.layout.act_my_vouchers;
    }

    @Override
    protected void initView() {

        adapter = new VouchersFragmentAdapter(getSupportFragmentManager());

        weiShiYongFragment =VouchersFragment.newInstance(Constant.vouchersType_0);
        yiShiYongFragment = VouchersFragment.newInstance(Constant.vouchersType_1);
        yiGuoQiFragment = VouchersFragment.newInstance(Constant.vouchersType_2);
        if(Constant.IParam.select_voucher.equals(getIntent().getAction())){
            weiShiYongFragment.setSelectVoucher(true);
        }
        list = new ArrayList<>();
        list.add(weiShiYongFragment);
        list.add(yiShiYongFragment);
        list.add(yiGuoQiFragment );

        adapter.setList(list);
        vp_my_vouchers.setAdapter(adapter);
        vp_my_vouchers.setOffscreenPageLimit(list.size()-1);

        tl_my_vouchers.setupWithViewPager(vp_my_vouchers);

        /*getRxBusEvent(VoucherEvent.class, new MySubscriber<VoucherEvent>() {
            @Override
            public void onMyNext(VoucherEvent event) {
                Intent intent=new Intent();
                intent.putExtra(com.zhizhong.farmer.module.order.Constant.IParam.voucher,event.vouchersObj);
                setResult(RESULT_OK,intent);
                finish();
            }
        });*/


    }

    @Override
    protected void initData() {
        getVouchersNum();
    }

    private void getVouchersNum() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getVouchersNum(map, new MyCallBack<VouchersNumObj>(mContext) {
            @Override
            public void onSuccess(VouchersNumObj obj) {
                tl_my_vouchers.getTabAt(0).setText("未使用("+obj.getCount_wsy()+")");
                tl_my_vouchers.getTabAt(1).setText("已使用("+obj.getCount_ysy()+")");
                tl_my_vouchers.getTabAt(2).setText("已过期("+obj.getCount_ygq()+")");
            }
        });
    }

    @Override
    protected void onViewClick(View v) {

    }
}
