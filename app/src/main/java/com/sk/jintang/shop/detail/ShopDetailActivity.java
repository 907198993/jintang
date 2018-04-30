package com.sk.jintang.shop.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sk.jintang.R;
import com.sk.jintang.shop.BaseActivity;
import com.sk.jintang.shop.utils.ViewUtils;

/**
 * Created by k on 2017/10/30.
 */

public class ShopDetailActivity extends BaseActivity implements View.OnClickListener {

    private SimpleDraweeView iv_shop;




    @Override
    protected int getContentView() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initView() {
        iv_shop = (SimpleDraweeView) findViewById(R.id.iv_shop);
        ViewUtils.getBlurFresco(mContext, (SimpleDraweeView) findViewById(R.id.iv_shop_bg), "res:///" + R.drawable.icon_shop);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void again() {

    }
}


