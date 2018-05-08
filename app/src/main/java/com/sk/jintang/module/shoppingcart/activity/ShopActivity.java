package com.sk.jintang.module.shoppingcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.ToastUtils;
import com.github.customview.MyImageView;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.module.shoppingcart.adapter.MyViewPagerAdapter;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.ShopDetailObj;
import com.sk.jintang.module.orderclass.network.response.StatusObj;
import com.sk.jintang.module.shoppingcart.fragment.ShopTab1Fragment;
import com.sk.jintang.module.shoppingcart.fragment.ShopTab2Fragment;
import com.sk.jintang.module.shoppingcart.fragment.ShopTab3Fragment;
import com.sk.jintang.module.shoppingcart.network.response.StoreObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/21.
 */

public class ShopActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager myViewPageer;
    @BindView(R.id.shop_img)
    MyImageView shopImg;
    @BindView(R.id.shop_title)
    MyTextView shopTitle;
    @BindView(R.id.shop_address)
    MyTextView shopAddress;

    @BindView(R.id.tv_attention)
    MyTextView tvAttention;

    @BindView(R.id.iv_goods_detail_back)
    ImageView backImg;
    private List<Fragment> fragmentList = new ArrayList<>();


    @BindView(R.id.tablayout)
    TabLayout tab_layout;
    private String attention;//是否关注
    private  String storeId;
    private  String pinPaiId;
      Fragment ShopTab1Fragment,ShopTab2Fragment,ShopTab3Fragment;
    @Override
    protected int getContentView() {
        return R.layout.shop_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showProgress();
        storeId = getIntent().getStringExtra(com.sk.jintang.module.my.Constant.IParam.storeId);
        pinPaiId =  getIntent().getStringExtra(com.sk.jintang.module.home.Constant.IParam.pinPaiId);
        if(pinPaiId!=null){
          getStoreId(pinPaiId);
        }else {
            getData(storeId);
        }
        initUiAndData();
    }

    private void getStoreId(String brand_id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sign",  GetSign.getSign(map));
        map.put("brand_id", brand_id);
        ApiRequest.GetBrandStore(map, new MyCallBack<StoreObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(StoreObj obj) {
                getData(obj.getStoreID());
            }
        });

    }


    /**
     * 初始化话界面和数据
     */
    private void initUiAndData() {
        if (tab_layout != null) {
            tab_layout.setupWithViewPager(myViewPageer);
        }

        FragmentManager fm = getSupportFragmentManager();
        ShopTab1Fragment = new ShopTab1Fragment();
        ShopTab2Fragment = new ShopTab2Fragment();
        ShopTab3Fragment = new ShopTab3Fragment();

        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(fm);
        pagerAdapter.addFragment(ShopTab1Fragment,"首页");
        pagerAdapter.addFragment(ShopTab2Fragment,"全部宝贝");
        pagerAdapter.addFragment(ShopTab3Fragment,"新品");
        myViewPageer.setAdapter(pagerAdapter);
        myViewPageer.setOffscreenPageLimit(3);


        Bundle bundle = new Bundle();
        bundle.putString("storeId", storeId);
        ShopTab1Fragment.setArguments(bundle);

        Bundle bundle1 = new Bundle();
        bundle1.putString("storeId", storeId);
        ShopTab2Fragment.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString("storeId", storeId);
        ShopTab3Fragment.setArguments(bundle2);
    }

    private void getData(String storeId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sign",  "admin123");
        map.put("storeId", storeId);
        map.put("userId",getUsersId());
        ApiRequest.getShopsDetail(map, new MyCallBack<ShopDetailObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(ShopDetailObj obj) {
                attention = obj.getIsAttention();
                if(attention.equals("1")){
                    tvAttention.setText("已关注");
                }else{
                    tvAttention.setText("关注店铺");
                }
                shopTitle.setText(obj.getStoreName());
                shopAddress.setText(obj.getStoreAddress());
                Glide.with(mContext).load(obj.getStoreHeadImg()).error(R.drawable.home12).into(shopImg);
            }
        });
    }

    @OnClick({R.id.shop_goods_category_text,R.id.shop_introduce_text,R.id.tv_attention,R.id.iv_goods_detail_back,R.id.linear_image_category})
    protected void onViewClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_goods_detail_back:
                finish();
                break;
            case R.id.linear_image_category:
                intent=new Intent();
                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.storeId,storeId+"");
                STActivity(intent,ShopGoodsCategoryActivity.class);
                break;
            case R.id.shop_goods_category_text:
                intent=new Intent();
                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.storeId,storeId+"");
                STActivity(intent,ShopGoodsCategoryActivity.class);
                break;
            case R.id.shop_introduce_text:
                intent=new Intent();
                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.storeId,storeId+"");
                STActivity(intent,ShopIntroduceActivity.class);
                break;
            case R.id.tv_attention:
                if(attention.equals("0")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("sign",  "admin123");
                    map.put("storeId", storeId);
                    map.put("userId",getUsersId());
                    ApiRequest.getShopAttention(map, new MyCallBack<StatusObj>(mContext, pcfl, pl_load) {
                        @Override
                        public void onSuccess(StatusObj obj) {
                            if(obj.getSuccess()==1){
                                tvAttention.setText("已关注");
                                attention = "1";
                            }else{
                                ToastUtils.showToast(ShopActivity.this,"未成功关注");
                            }

                        }
                    });
                }else{
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("sign",  "admin123");
                    map.put("storeId", storeId);
                    map.put("userId",getUsersId());
                    ApiRequest.getShopAbolishAttention(map, new MyCallBack<StatusObj>(mContext, pcfl, pl_load) {
                        @Override
                        public void onSuccess(StatusObj obj) {
                            if(obj.getSuccess()==1){
                                tvAttention.setText("关注店铺");
                                attention = "0";
                                ToastUtils.showToast(ShopActivity.this,"取消关注成功");
                            }else{
                                ToastUtils.showToast(ShopActivity.this,"取消关注失败");
                            }
                        }
                    });
                }
                break;
        }
    }
}