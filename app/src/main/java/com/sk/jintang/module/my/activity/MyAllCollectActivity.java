package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.module.my.adapter.CollectFragmentAdapter;
import com.sk.jintang.module.my.event.GetOrderEvent;
import com.sk.jintang.module.my.fragment.MyCollectGoodsFragment;
import com.sk.jintang.module.my.fragment.MyCollectStoreFragment;
import com.sk.jintang.module.shoppingcart.adapter.MyViewPagerAdapter;
import com.sk.jintang.view.MyViewPager;

import java.util.List;

import butterknife.BindView;

//店铺和宝贝收藏

public class MyAllCollectActivity extends BaseActivity {

    @BindView(R.id.tl_my_order)
    TabLayout tl_all_order;
    @BindView(R.id.vp_my_order)
    MyViewPager vp_my_order;

    CollectFragmentAdapter adapter;
    List<Fragment> list;


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
        setAppTitle("我的收藏");
        return R.layout.act_my_collection;
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

        if (tl_all_order != null) {
            tl_all_order.setupWithViewPager(vp_my_order);
        }

        FragmentManager fm = getSupportFragmentManager();
        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(fm);
        pagerAdapter.addFragment(new MyCollectStoreFragment(),"商铺");
        pagerAdapter.addFragment(new MyCollectGoodsFragment(),"宝贝");
        vp_my_order.setAdapter(pagerAdapter);
        vp_my_order.setOffscreenPageLimit(2);

//        adapter = new CollectFragmentAdapter(getSupportFragmentManager());

//        allOrderFragment = AllOrderFragment.newInstance(Constant.type_0);
//        daiFuKuanOrderFragment =   AllOrderFragment.newInstance(Constant.type_1);
//        daiFaHuoOrderFragment =   AllOrderFragment.newInstance(Constant.type_2);
//        daiShouHuoOrderFragment =   AllOrderFragment.newInstance(Constant.type_3);
//        daiPingJiaOrderFragment =   AllOrderFragment.newInstance(Constant.type_4);
////        completeOrderFragment  =   AllOrderFragment.newInstance(Constant.type_5);
//
//        list = new ArrayList<>();
//        list.add(allOrderFragment);
//        list.add(daiFuKuanOrderFragment);
//        list.add(daiFaHuoOrderFragment);
//        list.add(daiShouHuoOrderFragment);
//        list.add(daiPingJiaOrderFragment);
////        list.add(completeOrderFragment  );
//
//        adapter.setList(list);
//        vp_my_order.setAdapter(adapter);
//        vp_my_order.setOffscreenPageLimit(list.size()-1);
//
//        tl_all_order.setupWithViewPager(vp_my_order);
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
    protected void initData() {

    }
    private void getData() {

    }

    @Override
    protected void onViewClick(View v) {

    }


}
