package com.sk.jintang.module.orderclass.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.module.orderclass.adapter.GoodsDetailImgAdapter;
import com.sk.jintang.module.orderclass.network.response.GoodsDetailXianShiObj;

import butterknife.BindView;

/**
 * Created by administartor on 2017/9/14.
 */

public class GoodsDetailXianShiFragment extends BaseFragment {
    @BindView(R.id.rv_goods_evaluate)
    RecyclerView rv_goods_evaluate;

    GoodsDetailImgAdapter imgAdapter;

    @Override
    protected int getContentView() {
        return R.layout.frag_goods_detail;
    }
    public static GoodsDetailXianShiFragment newInstance(GoodsDetailXianShiObj obj) {
        Bundle args = new Bundle();
        args.putSerializable("obj",obj);
        GoodsDetailXianShiFragment fragment = new GoodsDetailXianShiFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        imgAdapter=new GoodsDetailImgAdapter(mContext,R.layout._item_);
        GoodsDetailXianShiObj obj = (GoodsDetailXianShiObj) getArguments().getSerializable("obj");
        imgAdapter.setList(obj.getGoods_details());
        rv_goods_evaluate.setNestedScrollingEnabled(false);
        rv_goods_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_evaluate.setAdapter(imgAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
