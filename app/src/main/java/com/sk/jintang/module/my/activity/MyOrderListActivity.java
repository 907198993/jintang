package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.adapter.OrderFragmentAdapter;
import com.sk.jintang.module.my.event.GetOrderEvent;
import com.sk.jintang.module.my.event.GetOrderNumEvent;
import com.sk.jintang.module.my.fragment.AllOrderFragment;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.OrderNumObj;
import com.sk.jintang.view.MyViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class MyOrderListActivity extends BaseActivity {

    @BindView(R.id.tl_my_order)
    TabLayout tl_all_order;
    @BindView(R.id.vp_my_order)
    MyViewPager vp_my_order;

    OrderFragmentAdapter adapter;
    List<Fragment> list;

    AllOrderFragment allOrderFragment;
    AllOrderFragment daiFuKuanOrderFragment;
    AllOrderFragment daiFaHuoOrderFragment;
    AllOrderFragment daiShouHuoOrderFragment;
    AllOrderFragment daiPingJiaOrderFragment;
//    AllOrderFragment completeOrderFragment;
    private int type;

    /*  DaiWanShanOrderFragment daiFuKuanOrderFragment;
      DaiQueRenOrderFragment  daiFaHuoOrderFragment;
      DaiJieDanOrderFragment  daiShouHuoOrderFragment;
      YiJieDanOrderFragment  daiPingJiaOrderFragment;
      CompleteOrderFragment  completeOrderFragment;*/

/*
    @BindView(R.id.tv_my_order_num1)
    TextView tv_my_order_num1;

    @BindView(R.id.tv_my_order_num2)
    TextView tv_my_order_num2;

    @BindView(R.id.tv_my_order_num3)
    TextView tv_my_order_num3;

    @BindView(R.id.tv_my_order_num4)
    TextView tv_my_order_num4;

    @BindView(R.id.tv_my_order_num5)
    TextView tv_my_order_num5;*/

    @Override
    protected int getContentView() {
        setAppTitle("我的订单");
        return R.layout.act_my_order;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("====","====onNewIntent====initView");
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        RxBus.getInstance().getEvent(GetOrderEvent.class, new MySubscriber() {
            @Override
            public void onMyNext(Object o) {
                getData();
            }
        });
    }

    @Override
    protected void initView() {
        vp_my_order.setScroll(false);
        type = getIntent().getIntExtra(Constant.type, Constant.type_0);
        adapter = new OrderFragmentAdapter(getSupportFragmentManager());

        allOrderFragment = AllOrderFragment.newInstance(Constant.type_0);
        daiFuKuanOrderFragment =   AllOrderFragment.newInstance(Constant.type_1);
        daiFaHuoOrderFragment =   AllOrderFragment.newInstance(Constant.type_2);
        daiShouHuoOrderFragment =   AllOrderFragment.newInstance(Constant.type_3);
        daiPingJiaOrderFragment =   AllOrderFragment.newInstance(Constant.type_4);
//        completeOrderFragment  =   AllOrderFragment.newInstance(Constant.type_5);

        list = new ArrayList<>();
        list.add(allOrderFragment);
        list.add(daiFuKuanOrderFragment);
        list.add(daiFaHuoOrderFragment);
        list.add(daiShouHuoOrderFragment);
        list.add(daiPingJiaOrderFragment);
//        list.add(completeOrderFragment  );

        adapter.setList(list);
        vp_my_order.setAdapter(adapter);
        vp_my_order.setOffscreenPageLimit(list.size()-1);

        tl_all_order.setupWithViewPager(vp_my_order);
//        TabLayout.Tab tab = tl_all_order.newTab();
//        tab.setText("全部");
//        tl_all_order.setTag();

       /* for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tl_all_order.getTabAt(i);
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_order_tab, null);
            tab.setCustomView(inflate);
        }
*/
        Handler handler=new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                vp_my_order.setCurrentItem(type);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100://申请退款成功-刷新数据
                    RxBus.getInstance().post(new GetOrderEvent());
                    RxBus.getInstance().post(new GetOrderNumEvent());
                break;
                case 201://评价成功成功-刷新数据
                    RxBus.getInstance().post(new GetOrderEvent());
                    RxBus.getInstance().post(new GetOrderNumEvent());
                break;
                case 202://进入订单详情操作成功-刷新数据
                    RxBus.getInstance().post(new GetOrderEvent());
                    RxBus.getInstance().post(new GetOrderNumEvent());
                break;
            }
        }
    }

    @Override
    protected void initData() {
        getData();
    }
    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getOrderNum(map, new MyCallBack<OrderNumObj>(mContext) {
            @Override
            public void onSuccess(OrderNumObj obj) {
                tl_all_order.getTabAt(0).setText("全部\n("+obj.getCount()+")");
                tl_all_order.getTabAt(1).setText("待付款\n("+obj.getDfk_count()+")");
                tl_all_order.getTabAt(2).setText("待发货\n("+obj.getDfh_count()+")");
                tl_all_order.getTabAt(3).setText("待收货\n("+obj.getDsh_count()+")");
                tl_all_order.getTabAt(4).setText("待评价\n("+obj.getDpj_count()+")");

                /*tv_my_order_num1.setText(obj.getCount()+"");
                tv_my_order_num2.setText(obj.getDfk_count()+"");
                tv_my_order_num3.setText(obj.getDfh_count()+"");
                tv_my_order_num4.setText(obj.getDsh_count()+"");
                tv_my_order_num5.setText(obj.getDpj_count()+"");

                if (obj.getCount()==0){
                    tv_my_order_num1.setVisibility(View.GONE);
                }
                if (obj.getDfk_count()==0){
                    tv_my_order_num2.setVisibility(View.GONE);
                }
                if (obj.getDfh_count()==0){
                    tv_my_order_num3.setVisibility(View.GONE);
                }
                if (obj.getDsh_count()==0){
                    tv_my_order_num4.setVisibility(View.GONE);
                }
                if (obj.getDpj_count()==0){
                    tv_my_order_num5.setVisibility(View.GONE);
                }
*/
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }


}
