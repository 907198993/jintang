package com.sk.jintang.module.home.activity;

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
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.activity.GoodsDetailActivity;
import com.sk.jintang.module.orderclass.activity.SelectCityActivity;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.GoodsSearchObj;
import com.sk.jintang.tools.DividerGridItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/10/10.
 */

public class GoodsForGongChangActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_search_result)
    RecyclerView rv_search_result;

    LoadMoreAdapter adapter;

    private String gongChangId;
    private int screenWidth;

    @BindView(R.id.app_right_city)
    TextView app_right_city;
    private String address="";
    @Override
    protected int getContentView() {
        setAppTitle("商品列表");
        setAppRightImg(R.drawable.share_title);
        return R.layout.act_gongchang_goodslist;
    }


    @Override
    protected void initView() {
        address = SPUtils.getPrefString(mContext, Config.city, "上海市");
        app_right_city.setVisibility(View.VISIBLE);
        app_right_city.setText(address);
        gongChangId = getIntent().getStringExtra(Constant.IParam.gongChangId);

        screenWidth = PhoneUtils.getScreenWidth(mContext);
        int imgWidth = (screenWidth - 2) / 2 - PhoneUtils.dip2px(mContext, 20);
        adapter=new LoadMoreAdapter<GoodsSearchObj>(mContext,R.layout.item_goods,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, GoodsSearchObj bean) {
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
        rv_search_result.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_search_result.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 5)));
        rv_search_result.setAdapter(adapter);


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

    private void getData(int page,boolean isLoad) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("city",address);
        map.put("factory_id",gongChangId);
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.goodsForGC(map, new MyCallBack<List<GoodsSearchObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<GoodsSearchObj> list) {
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

    @OnClick({R.id.app_right_iv,R.id.app_right_city})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:
                showFenXiang("0");
                break;
            case R.id.app_right_city:
                STActivityForResult(SelectCityActivity.class, 100);
                break;
        }
    }


    @Override
    public void loadMore() {
        getData(pageNum,true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    String city = data.getStringExtra(com.sk.jintang.module.orderclass.Constant.IParam.city);
                    app_right_city.setText(city);
                    showLoading();
                    address=city;
                    getData(1,false);
                    break;
            }
        }
    }
}
