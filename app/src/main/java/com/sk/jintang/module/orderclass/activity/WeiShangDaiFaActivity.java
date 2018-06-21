package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.graphics.Paint;
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
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.DifferentGoodsObj;
import com.sk.jintang.tools.DividerGridItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/1.
 */

public class WeiShangDaiFaActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_wei_shang_dai_fa)
    RecyclerView rv_wei_shang_dai_fa;

    LoadMoreAdapter adapter;
    private int goodsType;
    private int screenWidth;
    @Override
    protected int getContentView() {
//        setAppTitle("微商代发");
//        setAppTitle("家庭必备");
//        setAppTitle("免费试用");
//        setAppTitle("聚实惠");
//        setAppTitle("今日爆款");
        setAppRightImg(R.drawable.share_title);
        return R.layout.act_wei_shang_dai_fa;
    }

    @Override
    protected void initView() {
        goodsType =getIntent().getIntExtra(com.sk.jintang.module.home.Constant.IParam.goodsType,4);
        //4今日爆款 5聚实惠 6家庭必备 7免费试用 9微商代发
        switch (goodsType){
            case com.sk.jintang.module.home.Constant.IParam.goodsType_4:
                setAppTitle("今日爆款");
            break;
            case com.sk.jintang.module.home.Constant.IParam.goodsType_5:
                setAppTitle("聚实惠");
            break;
            case com.sk.jintang.module.home.Constant.IParam.goodsType_6:
                setAppTitle("家庭必备");
            break;
            case com.sk.jintang.module.home.Constant.IParam.goodsType_7:
                setAppTitle("免费试用");
            break;
            case com.sk.jintang.module.home.Constant.IParam.goodsType_9:
                setAppTitle("微商代发");
            break;
        }
        screenWidth = PhoneUtils.getScreenWidth(mContext);
        int imgWidth = (screenWidth - 2) / 2 - PhoneUtils.dip2px(mContext, 20);
        adapter=new LoadMoreAdapter<DifferentGoodsObj>(mContext,R.layout.item_goods,pageSize) {

            @Override
            public void bindData(LoadMoreViewHolder holder, int position, DifferentGoodsObj bean) {
                TextView tv_goods_yuanjia = holder.getTextView(R.id.tv_goods_yuanjia);
                if(bean.getOriginal_price()<=0||bean.getOriginal_price()==bean.getPrice()){
                    tv_goods_yuanjia.setVisibility(View.INVISIBLE);
                }else{
                    tv_goods_yuanjia.setText("¥"+bean.getOriginal_price());
                    tv_goods_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    tv_goods_yuanjia.getPaint().setAntiAlias(true);
                    tv_goods_yuanjia.setVisibility(View.VISIBLE);
                }

//                View tv_goods_baoyou = holder.getView(R.id.tv_goods_baoyou);
//                tv_goods_baoyou.setVisibility(bean.getBaoyou()==1?View.VISIBLE:View.GONE);
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

                        Intent intent = new Intent();
                        intent.putExtra(Constant.IParam.goodsId, bean.getGoods_id() + "");
                        STActivity(intent, GoodsDetailActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_wei_shang_dai_fa.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_wei_shang_dai_fa.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 5)));
        rv_wei_shang_dai_fa.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("type",goodsType+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getDifferentGoods(map, new MyCallBack<List<DifferentGoodsObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<DifferentGoodsObj> list) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(list,true);
                }else{
                    pageNum=2;
                    adapter.setList(list,true);
                }
            }
        });

    }

    @OnClick({R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:
                showFenXiang("0");
            break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
