package com.sk.jintang.module.orderclass.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.activity.GoodsDetailActivity;
import com.sk.jintang.module.orderclass.network.response.GoodsDetailXianShiObj;
import com.sk.jintang.tools.DividerGridItemDecoration;

import butterknife.BindView;

/**
 * Created by administartor on 2017/9/14.
 */

public class GoodsTuiJianXianShiFragment extends BaseFragment {

    LoadMoreAdapter adapter;
    @BindView(R.id.rv_goods_detail_tuijian)
    RecyclerView rv_goods_detail_tuijian;
    @Override
    protected int getContentView() {
        return R.layout.frag_goods_tuijan;
    }
    public static GoodsTuiJianXianShiFragment newInstance(GoodsDetailXianShiObj obj) {
        Bundle args = new Bundle();
        args.putSerializable("obj",obj);
        GoodsTuiJianXianShiFragment fragment = new GoodsTuiJianXianShiFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {

    }
    public GoodsDetailXianShiObj getGoodsDetailObj() {
        return (GoodsDetailXianShiObj) getArguments().getSerializable("obj");
    }
    @Override
    protected void initData() {
        int screenWidth = PhoneUtils.getScreenWidth(mContext);
        adapter = new LoadMoreAdapter<GoodsDetailXianShiObj.TuijianListBean>(mContext, R.layout.item_goods, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, GoodsDetailXianShiObj.TuijianListBean bean) {
                TextView tv_goods_yuanjia = holder.getTextView(R.id.tv_goods_yuanjia);
                if(bean.getOriginal_price()<=0||bean.getOriginal_price()==bean.getPrice()){
                    tv_goods_yuanjia.setVisibility(View.INVISIBLE);
                }else{
                    tv_goods_yuanjia.setText("¥"+bean.getOriginal_price());
                    tv_goods_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    tv_goods_yuanjia.getPaint().setAntiAlias(true);
                    tv_goods_yuanjia.setVisibility(View.VISIBLE);
                }

                View tv_goods_baoyou = holder.getView(R.id.tv_goods_baoyou);
                tv_goods_baoyou.setVisibility(bean.getBaoyou()==1?View.VISIBLE:View.GONE);
                int imgWidth = (screenWidth - 2) / 2 - PhoneUtils.dip2px(mContext, 20);
                ImageView iv_goods_img = holder.getImageView(R.id.iv_goods_img);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = imgWidth;
                layoutParams.height = imgWidth;
                iv_goods_img.setLayoutParams(layoutParams);
                Glide.with(mContext).load(bean.getGoods_image()).error(R.color.c_press).into(iv_goods_img);

                ImageView iv_goods_share = holder.getImageView(R.id.iv_goods_share);
                iv_goods_share.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showFenXiang(bean.getGoods_id()+"");
                    }
                });
                holder.setText(R.id.tv_goods_name, bean.getGoods_name())
                        .setText(R.id.tv_goods_address, bean.getAddresss())
                        .setText(R.id.tv_goods_price, "" + bean.getPrice())
                        .setText(R.id.tv_goods_goumai, bean.getSales_volume() + "人购买");
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.goodsId,bean.getGoods_id()+"");
                        STActivity(intent,GoodsDetailActivity.class);
                    }
                });
            }
        };
        adapter.setList(getGoodsDetailObj().getTuijian_list());
        rv_goods_detail_tuijian.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_goods_detail_tuijian.setNestedScrollingEnabled(false);
        rv_goods_detail_tuijian.addItemDecoration(new DividerGridItemDecoration(mContext, 2));
        rv_goods_detail_tuijian.setAdapter(adapter);
    }

    @Override
    protected void onViewClick(View v) {

    }
}
