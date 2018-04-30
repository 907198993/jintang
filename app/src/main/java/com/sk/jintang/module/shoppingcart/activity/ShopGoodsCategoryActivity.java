package com.sk.jintang.module.shoppingcart.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.module.shoppingcart.adapter.ShopGoodsCategoryAdapter;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.shoppingcart.network.ApiRequest;
import com.sk.jintang.module.shoppingcart.network.response.ShopGoodsTypeObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/23.
 */

public class ShopGoodsCategoryActivity extends BaseActivity {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private ShopGoodsCategoryAdapter shopGoodsCategoryAdapter;//品牌列表
    @Override
    protected int getContentView() {
        return R.layout.shop_goods_categoy;
    }

    @Override
    protected void initView() {
        setAppTitle("宝贝分类");
    }

    @Override
    protected void initData() {
        String storeId = getIntent().getStringExtra(com.sk.jintang.module.my.Constant.IParam.storeId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        shopGoodsCategoryAdapter=new ShopGoodsCategoryAdapter(mContext,0,storeId);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(shopGoodsCategoryAdapter);
        Map<String,String> map=new HashMap<String,String>();
        map.put("storeId",storeId);
        map.put("sign", GetSign.getSign(map));
        // https://github.com/zhongruiAndroid/RetrofitTool
        ApiRequest.getShopGoodsType(map, new MyCallBack<ShopGoodsTypeObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(ShopGoodsTypeObj obj) {
                shopGoodsCategoryAdapter.setList(obj.getTypeList(),true);
            }
        });
    }


    @Override
    protected void onViewClick(View v) {

    }

}
