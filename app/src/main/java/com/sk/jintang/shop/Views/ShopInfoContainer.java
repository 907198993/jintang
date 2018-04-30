package com.sk.jintang.shop.Views;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sk.jintang.R;
import com.sk.jintang.shop.utils.ViewUtils;

public class ShopInfoContainer extends RelativeLayout {

    public TextView shop_name, shop_sum, shop_type, shop_send;
    private ImageView shop_arrow, iv_pin;
    public ImageView iv_shop;
    LinearLayout linear_shop_collect;

    public ShopInfoContainer(Context context) {
        super(context);
    }

    public ShopInfoContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_shopinfo, this);
        shop_name = findViewById(R.id.tv_shop_name);
        shop_arrow = findViewById(R.id.tv_shop_collect);
        shop_sum = findViewById(R.id.tv_shop_summary);
        linear_shop_collect = findViewById(R.id.linear_shop_collect);
        ViewUtils.getBlurFresco(context, (SimpleDraweeView) findViewById(R.id.iv_shop_bg), "res:///" + R.drawable.icon_shop);
        iv_shop = findViewById(R.id.iv_shop);
//        ViewUtils.getFrescoController(context, iv_shop, "res:///" + R.drawable.icon_shop, 40, 40);
    }


    public void setWgAlpha(float alpha) {
        shop_sum.setAlpha(alpha);
        shop_arrow.setAlpha(alpha);
        shop_sum.setAlpha(alpha);
        iv_shop.setAlpha(alpha);
        linear_shop_collect.setAlpha(alpha);
    }
}
