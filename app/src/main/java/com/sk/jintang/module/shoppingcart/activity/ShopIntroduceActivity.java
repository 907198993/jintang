package com.sk.jintang.module.shoppingcart.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.customview.MyImageView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.shoppingcart.network.ApiRequest;
import com.sk.jintang.module.shoppingcart.network.response.ShopIntroduceObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/3/28.
 */
//商铺介绍
public class ShopIntroduceActivity extends BaseActivity {
    @BindView(R.id.civ_shop_img)
    CircleImageView civShopImg;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.ll_my_info)
    LinearLayout llMyInfo;
    @BindView(R.id.shop_address)
    TextView shopAddress;
    @BindView(R.id.phone_text)
    TextView phoneText;
    @BindView(R.id.shop_phone)
    TextView shopPhone;
    @BindView(R.id.iv_goods_detail_kefu)
    MyImageView ivGoodsDetailKefu;
    @BindView(R.id.shop_name_text)
    TextView shopNameText;
    @BindView(R.id.shop_user_name)
    TextView shopUserName;

    @Override
    protected int getContentView() {
        return R.layout.shop_introduction;
    }

    @Override
    protected void initView() {
        setAppTitle("店铺简介");
    }

    @Override
    protected void initData() {
        String storeId = getIntent().getStringExtra(com.sk.jintang.module.my.Constant.IParam.storeId);
        Map<String,String> map=new HashMap<String,String>();
        map.put("storeId",storeId);
        map.put("sign",  GetSign.getSign(map));
        ApiRequest.getShopIntroduce(map, new MyCallBack<ShopIntroduceObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(ShopIntroduceObj obj) {
                Glide.with(mContext).load(obj.getStoreHeadImg()).error(R.color.c_press).into(civShopImg);
                tvShopName.setText(obj.getStoreName());
                shopAddress.setText(obj.getStoreAddress());
                shopPhone.setText(obj.getStoreTel());
                shopUserName.setText(obj.getStoreUserName());
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}
