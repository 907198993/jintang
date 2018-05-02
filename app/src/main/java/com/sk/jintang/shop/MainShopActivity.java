package com.sk.jintang.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.florent37.viewanimator.ViewAnimator;
import com.sk.jintang.Config;
import com.sk.jintang.R;
import com.sk.jintang.base.*;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.SpecialShopDetailObj;
import com.sk.jintang.module.orderclass.network.request.ShoppingCartItem;
import com.sk.jintang.module.orderclass.network.response.ShopDetailObj;
import com.sk.jintang.module.orderclass.network.response.StatusObj;
import com.sk.jintang.module.shoppingcart.activity.SureGoodsActivity;
import com.sk.jintang.module.shoppingcart.network.response.ShoppingCartObj;
import com.sk.jintang.shop.Views.AddWidget;
import com.sk.jintang.shop.Views.ShopCarView;
import com.sk.jintang.shop.adapters.CarAdapter;
import com.sk.jintang.shop.behaviors.AppBarBehavior;
import com.sk.jintang.shop.fragments.FirstFragment;
import com.sk.jintang.shop.fragments.SecondFragment;
import com.sk.jintang.shop.network.response.ShopDataObj;
import com.sk.jintang.shop.utils.ViewUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainShopActivity extends BaseActivity implements AddWidget.OnAddClick ,View.OnClickListener{

    public static final String CAR_ACTION = "handleCar";
    public static final String CLEARCAR_ACTION = "clearCar";
    private CoordinatorLayout rootview;
    public BottomSheetBehavior behavior;
    public View scroll_container;
    private FirstFragment firstFragment;
    public static CarAdapter carAdapter;
    private ShopCarView shopCarView;
    private  TextView  tv_shop_name,tv_shop_summary,tv_shop_collect_text,car_limit;
    private ImageView tv_shop_collect; //收藏图标
    private ImageView  iv_shop;
    private SimpleDraweeView iv_shop_bg;

    private String storeId;//商铺id
    private String attention;//是否关注
    private  int startPs;//起送价格
    private  int shippingFree;//配送费

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initViews();
//        //注册广播
//
//
//
//    }

    @Override
    protected int getContentView() {
        return R.layout.act_main_go_shop;
    }

    @Override
    protected void initView() {
        storeId = getIntent().getStringExtra(Constant.IParam.storeId);
        rootview = (CoordinatorLayout) findViewById(R.id.rootview);
        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        tv_shop_summary = findViewById(R.id.tv_shop_summary);
        tv_shop_collect_text = findViewById(R.id.tv_shop_collect_text);
        tv_shop_collect = findViewById(R.id.tv_shop_collect);
        iv_shop_bg = findViewById(R.id.iv_shop_bg);
        tv_shop_collect.setOnClickListener(this);
        iv_shop  = findViewById(R.id.iv_shop);
        car_limit = findViewById(R.id.car_limit);
        car_limit.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViewpager();
        initShopCar();
        IntentFilter intentFilter = new IntentFilter(CAR_ACTION);
        intentFilter.addAction(CLEARCAR_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void initData() {
        storeId = getIntent().getStringExtra(com.sk.jintang.module.my.Constant.IParam.storeId);
        getData(storeId);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CAR_ACTION:
                    ShopDataObj.GoodsListBean goodsListBean = (ShopDataObj.GoodsListBean) intent.getSerializableExtra("foodbean");
                    ShopDataObj.GoodsListBean fb = goodsListBean;
                    int p = intent.getIntExtra("position", -1);
                    if (p >= 0 && p < firstFragment.getFoodAdapter().getItemCount()) {
                        fb = firstFragment.getFoodAdapter().getItem(p);
                        fb.setSelectCount(goodsListBean.getSelectCount());
                        firstFragment.getFoodAdapter().setData(p, fb);
                    } else {
                        for (int i = 0; i < firstFragment.getFoodAdapter().getItemCount(); i++) {
                            fb = firstFragment.getFoodAdapter().getItem(i);
                            if (fb.getGoodsId() == goodsListBean.getGoodsId()) {
                                fb.setSelectCount(goodsListBean.getSelectCount());
                                firstFragment.getFoodAdapter().setData(i, fb);
                                break;
                            }
                        }
                    }
                    dealCar(fb);
                    break;
                case CLEARCAR_ACTION:
                    clearCar();
                    break;
            }
            if (CAR_ACTION.equals(intent.getAction())) {

            }
        }
    };

    private void getData(String storeId) {
        showProgress();
        Map<String, String> map = new HashMap<String, String>();
        map.put("sign",  "admin123");
        map.put("storeId", storeId);
        map.put("userId", SPUtils.getPrefString(mContext, Config.user_id,"0"));
        com.sk.jintang.module.orderclass.network.ApiRequest.getSpecialShopDetail(map, new MyCallBack<SpecialShopDetailObj>(mContext, pl_load) {
            @Override
            public void onSuccess(SpecialShopDetailObj obj) {
                attention = obj.getIsAttention();
                if(attention.equals("1")){
                    tv_shop_collect.setBackground(getResources().getDrawable(R.drawable.star_1));
                    tv_shop_collect_text.setText("已关注");
                }else{
                    tv_shop_collect.setBackground(getResources().getDrawable(R.drawable.star_2));
                    tv_shop_collect_text.setText("关注店铺");
                }
                tv_shop_name.setText(obj.getStoreName());
                tv_shop_summary.setText(obj.getStoreAddress());
//                shopAddress.setText(obj.getStoreAddress());
                Glide.with(mContext).load(obj.getStoreHeadImg()).error(R.drawable.home12).into(iv_shop);
                ViewUtils.getBlurFresco(mContext, (SimpleDraweeView) findViewById(R.id.iv_shop_bg), obj.getStoreHeadImg());
                car_limit.setText("¥"+obj.getStartPs()+" 起送");
                startPs = obj.getStartPs();
                shippingFree = obj.getIsFreePs();

            }
        });
    }


    private void initShopCar() {
        behavior = BottomSheetBehavior.from(findViewById(R.id.car_container));
        shopCarView = (ShopCarView) findViewById(R.id.car_mainfl);
        View blackView = findViewById(R.id.blackview);
        shopCarView.setBehavior(behavior, blackView);
        RecyclerView carRecView = (RecyclerView) findViewById(R.id.car_recyclerview);
//		carRecView.setNestedScrollingEnabled(false);
        carRecView.setLayoutManager(new LinearLayoutManager(mContext));
        ((DefaultItemAnimator) carRecView.getItemAnimator()).setSupportsChangeAnimations(false);
        carAdapter = new CarAdapter(new ArrayList<ShopDataObj.GoodsListBean>(), this);
        carAdapter.bindToRecyclerView(carRecView);
    }

    private void initViewpager() {
        scroll_container = findViewById(R.id.scroll_container);
        ScrollIndicatorView mSv = (ScrollIndicatorView) findViewById(R.id.indicator);
        ColorBar colorBar = new ColorBar(mContext, ContextCompat.getColor(mContext, R.color.dicator_line_blue), 6,
                ScrollBar.Gravity.BOTTOM);
        colorBar.setWidth(ViewUtils.dip2px(mContext, 30));
        mSv.setScrollBar(colorBar);
        mSv.setSplitAuto(false);
        mSv.setOnTransitionListener(new OnTransitionTextListener().setColor(ContextCompat.getColor(mContext, R.color.dicator_line_blue),
                ContextCompat.getColor(mContext, R.color.black)));
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mSv, mViewPager);
        firstFragment = new FirstFragment();
        ViewpagerAdapter myAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        indicatorViewPager.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.car_limit:
            Intent intent;
            if(carAdapter.getData().size()==0){
             return;
            }else{
                if (SPUtils.getPrefString(mContext, Config.user_id,"0").equals("0")) {
                    STActivity(LoginActivity.class);
                    return;
                }
                ShoppingCartItem  item = new ShoppingCartItem();
                List<ShopDataObj.GoodsListBean> flist = carAdapter.getData();
                List<ShoppingCartItem.BodyBean> body = new ArrayList<>();
                ShoppingCartItem.BodyBean bodyBean;
                for (int i = 0; i < flist.size(); i++) {
                    bodyBean = new ShoppingCartItem.BodyBean();
                    bodyBean.setShopping_cart_id(0);
                    bodyBean.setGoods_id(flist.get(i).getGoodsId());
                    bodyBean.setNumber(flist.get(i).getSelectCount());
                    bodyBean.setSpecification_id(flist.get(i).getSpecificationList().get(0).getId());
                    body.add(bodyBean);
                }
                item.setBody(body);
                intent = new Intent();
                intent.putExtra(com.sk.jintang.module.shoppingcart.Constant.IParam.specialShoppingGoods, item);
                intent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.is_special_buy_now,true);
                STActivity(intent, SureGoodsActivity.class);
            }
            break;
        case R.id.tv_shop_collect:
            if (TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.user_id,null))) {
                STActivity(LoginActivity.class);
                return;
            }
            if(attention.equals("0")){
                Map<String, String> map = new HashMap<String, String>();
                map.put("sign",  "admin123");
                map.put("storeId", storeId);
                map.put("userId",SPUtils.getPrefString(mContext,Config.user_id,null));
                ApiRequest.getShopAttention(map, new MyCallBack<StatusObj>(mContext, pl_load) {
                    @Override
                    public void onSuccess(StatusObj obj) {
                        if(obj.getSuccess()==1){
                            tv_shop_collect.setBackground(getResources().getDrawable(R.drawable.star_1));
                            tv_shop_collect_text.setText("已关注");
                            attention = "1";
                        }else{
                            ToastUtils.showToast(mContext,"未成功关注");
                        }

                    }
                });
            }else{
                Map<String, String> map = new HashMap<String, String>();
                map.put("sign",  "admin123")  ;
                map.put("storeId", storeId);
                map.put("userId",SPUtils.getPrefString(mContext,Config.user_id,null));
                ApiRequest.getShopAbolishAttention(map, new MyCallBack<StatusObj>(mContext, pl_load) {
                    @Override
                    public void onSuccess(StatusObj obj) {
                        if(obj.getSuccess()==1){
                            tv_shop_collect.setBackground(getResources().getDrawable(R.drawable.star_2));
                            tv_shop_collect_text.setText("关注店铺");
                            attention = "0";
                            ToastUtils.showToast(mContext,"取消关注成功");
                        }else{
                            ToastUtils.showToast(mContext,"取消关注失败");
                        }



                    }
                });
            }
            break;
    }
    }

    private class ViewpagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private LayoutInflater inflater;
        private int padding;
        private String[] tabs = new String[]{"商品", "店铺"};

        ViewpagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(mContext);
            padding = ViewUtils.dip2px(mContext, 20);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            convertView = inflater.inflate(R.layout.item_textview, container, false);
            TextView textView = (TextView) convertView;
            textView.setText(tabs[position]); //名称
            textView.setPadding(padding, 0, padding, 0);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            switch (position) {
                case 0:
                    return firstFragment;
            }
            SecondFragment secondFragment =   new SecondFragment();
            Bundle bundle = new Bundle();
            bundle.putString("storeId", storeId);
            secondFragment.setArguments(bundle);
            return secondFragment;
        }
    }

    //返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddClick(View view, ShopDataObj.GoodsListBean fb) {
        dealCar(fb);
        ViewUtils.addTvAnim(view, shopCarView.carLoc, mContext, rootview);
    }


    @Override
    public void onSubClick(ShopDataObj.GoodsListBean fb) {
        dealCar(fb);
    }

    private void dealCar(ShopDataObj.GoodsListBean foodBean) {
        HashMap<String, Integer> typeSelect = new HashMap<>();//更新左侧类别badge用
        BigDecimal amount = new BigDecimal(0.0);
        int total = 0;
        boolean hasFood = false;
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            firstFragment.getFoodAdapter().notifyDataSetChanged();
        }
        List<ShopDataObj.GoodsListBean> flist = carAdapter.getData();
        int p = -1;
        for (int i = 0; i < flist.size(); i++) {
            ShopDataObj.GoodsListBean fb = flist.get(i);
            if (fb.getGoodsId() == foodBean.getGoodsId()) {
                fb = foodBean;
                hasFood = true;
                if (foodBean.getSelectCount() == 0) {
                    p = i;
                } else {
                    carAdapter.setData(i, foodBean);
                }
            }
            total += fb.getSelectCount();
            if (typeSelect.containsKey(fb.getTypeName())) {
                typeSelect.put(fb.getTypeName(), typeSelect.get(fb.getTypeName()) + fb.getSelectCount());
            } else {
                typeSelect.put(fb.getTypeName(), fb.getSelectCount());
            }
            amount = amount.add(fb.getPrice().multiply(BigDecimal.valueOf(fb.getSelectCount())));
        }
        if (p >= 0) {
            carAdapter.remove(p);
        } else if (!hasFood && foodBean.getSelectCount() > 0) {
            carAdapter.addData(foodBean);
            if (typeSelect.containsKey(foodBean.getTypeName())) {
                typeSelect.put(foodBean.getTypeName(), typeSelect.get(foodBean.getTypeName()) + foodBean.getSelectCount());
            } else {
                typeSelect.put(foodBean.getTypeName(), foodBean.getSelectCount());
            }
            amount = amount.add(foodBean.getPrice().multiply(BigDecimal.valueOf(foodBean.getSelectCount())));
            total += foodBean.getSelectCount();
        }
        shopCarView.showBadge(total);
        firstFragment.getTypeAdapter().updateBadge(typeSelect);
        shopCarView.updateAmount(amount,startPs,shippingFree);
    }

    public void expendCut(View view) {
        float cty = scroll_container.getTranslationY();
        if (!ViewUtils.isFastClick()) {
            ViewAnimator.animate(scroll_container)
                    .translationY(cty, cty == 0 ? AppBarBehavior.cutExpHeight : 0)
                    .decelerate()
                    .duration(100)
                    .start();
        }
    }

    public void clearCar(View view) {
        ViewUtils.showClearCar(mContext, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearCar();
            }
        });
    }

    private void clearCar() {
        List<ShopDataObj.GoodsListBean> flist = carAdapter.getData();
        for (int i = 0; i < flist.size(); i++) {
            ShopDataObj.GoodsListBean fb = flist.get(i);
            fb.setSelectCount(0);
        }
        carAdapter.setNewData(new ArrayList<ShopDataObj.GoodsListBean>());
        firstFragment.getFoodAdapter().notifyDataSetChanged();
        shopCarView.showBadge(0);
        firstFragment.getTypeAdapter().updateBadge(new HashMap<String, Integer>());
        shopCarView.updateAmount(new BigDecimal(0.0),startPs,shippingFree);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
