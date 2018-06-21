package com.sk.jintang.module.shoppingcart.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.MyPopupwindow;
import com.github.customview.MyLinearLayout;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.activity.GoodsDetailActivity;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.GoodsListObj;
import com.sk.jintang.tools.DividerGridItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2018/4/6.
 * 商品宝贝分类详细列表
 */

public class ShopGoodsCategoryListActivity extends BaseActivity  implements LoadMoreAdapter.OnLoadMoreListener  {


    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_xl)
    TextView tvGoodsXl;
    @BindView(R.id.rv_goods_class)
    RecyclerView rvGoodsClass;
    @BindView(R.id.pcfl_refresh)
    PtrClassicFrameLayout pcflRefresh;
    @BindView(R.id.ll_goods_order)
    MyLinearLayout llGoodsOrder;
    private int screenWidth;
    LoadMoreAdapter adapter;
    private String sorting_type="0";
    private String sorting_way="0";
    private  String type_id;
    private  String storeId;
    @Override
    protected int getContentView() {
        setAppTitle(getIntent().getStringExtra(com.sk.jintang.module.my.Constant.IParam.typeName));
        return R.layout.shop_goods_categoy_list;
    }

    @Override
    protected void initView() {

        screenWidth = PhoneUtils.getScreenWidth(mContext);
        adapter = new LoadMoreAdapter<GoodsListObj>(mContext, R.layout.shop_goods_detail_item, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, GoodsListObj bean) {
                TextView tv_goods_name = holder.getTextView(R.id.tv_goods_name);//商品名称
                tv_goods_name.setText(bean.getGoods_name());
                TextView tv_goods_price = holder.getTextView(R.id.tv_goods_price);
                tv_goods_price.setText(bean.getPrice()+"");
                int imgWidth = (screenWidth - 2) / 2 ;
                ImageView iv_goods_img = holder.getImageView(R.id.iv_goods_img);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight=imgWidth;
                layoutParams.height=imgWidth;
                iv_goods_img.setLayoutParams(layoutParams);
                Glide.with(mContext).load(bean.getGoods_image()).error(R.color.c_press).into(iv_goods_img);
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
//                        if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
//                            STActivity(LoginActivity.class);
//                            return;
//                        }
                        Intent intent = new Intent();
                        intent.putExtra(Constant.IParam.goodsId, bean.getGoods_id() + "");
                        STActivity(intent, GoodsDetailActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rvGoodsClass.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvGoodsClass.addItemDecoration(new DividerGridItemDecoration(mContext,2));
        //new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 5))
        rvGoodsClass.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(type_id,1, false);
            }
        });
    }

    @Override
    protected void initData() {
        type_id = getIntent().getStringExtra(com.sk.jintang.module.my.Constant.IParam.typeId);
        storeId = getIntent().getStringExtra(com.sk.jintang.module.my.Constant.IParam.storeId);
        showProgress();
        getData(type_id,1, false);
    }

    @OnClick({R.id.tv_goods_price,R.id.tv_goods_xl})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_goods_price:
                showPricePopu();
                break;
            case R.id.tv_goods_xl:
                showXiaoLiangPopu();
                break;
        }
    }
    @NonNull
    private MyOnClickListener getOrderListener(int flag,int index,TextView textView,String text) {
        return new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if(flag==0){//价格
                    sorting_type ="1";
                    sorting_way =index+"";
                    pricePopu.dismiss();
                    tvGoodsPrice.setText(text);
                    tvGoodsXl.setText("销量");
                }else{//销量
                    sorting_type ="2";
                    sorting_way =index+"";
                    xiaoLiangPopu.dismiss();
                    tvGoodsPrice.setText("价格");
                    tvGoodsXl.setText(text);
                }
//                textView.setText(text);
                showLoading();
                getData(type_id,1,false);
            }
        };
    }
    MyPopupwindow pricePopu,xiaoLiangPopu;
    private void showPricePopu() {
        if(pricePopu==null){
            View priceOrderView = LayoutInflater.from(mContext).inflate(R.layout.popu_price_goods, null);

            TextView tv_search_price_order = priceOrderView.findViewById(R.id.tv_search_price_order);
            tv_search_price_order.setOnClickListener(getOrderListener(0,0,tv_search_price_order,"价格"));

            TextView tv_search_price_order_asc = priceOrderView.findViewById(R.id.tv_search_price_order_asc);
            tv_search_price_order_asc.setOnClickListener(getOrderListener(0,2,tv_search_price_order_asc,"价格↑"));

            TextView tv_search_price_order_desc = priceOrderView.findViewById(R.id.tv_search_price_order_desc);
            tv_search_price_order_desc.setOnClickListener(getOrderListener(0,1,tv_search_price_order_desc,"价格↓"));

            pricePopu=new MyPopupwindow(mContext,priceOrderView,PhoneUtils.getScreenWidth(mContext)/2,-1);
        }
        pricePopu.showAsDropDown(llGoodsOrder,0,0);
    }

    //    销量
    private void showXiaoLiangPopu() {
        if(xiaoLiangPopu==null){
            View xiaoLiangOrderView = LayoutInflater.from(mContext).inflate(R.layout.popu_xiaoliang_goods, null);

            TextView tv_search_xl_order = xiaoLiangOrderView.findViewById(R.id.tv_search_xl_order);
            tv_search_xl_order.setOnClickListener(getOrderListener(1,0,tv_search_xl_order,"销量"));

            TextView tv_search_xl_order_asc = xiaoLiangOrderView.findViewById(R.id.tv_search_xl_order_asc);
            tv_search_xl_order_asc.setOnClickListener(getOrderListener(1,2,tv_search_xl_order_asc,"销量↑"));

            TextView tv_search_xl_order_desc = xiaoLiangOrderView.findViewById(R.id.tv_search_xl_order_desc);
            tv_search_xl_order_desc.setOnClickListener(getOrderListener(1,1,tv_search_xl_order_desc,"销量↓"));

            xiaoLiangPopu=new MyPopupwindow(mContext,xiaoLiangOrderView,PhoneUtils.getScreenWidth(mContext)/2,-1);
        }
        xiaoLiangPopu.showAsDropDown(llGoodsOrder,PhoneUtils.getScreenWidth(mContext)/2*2,0);
    }

    private void getData(String type_id, int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type_id", type_id);
//        map.put("newGoods", newGoods + "");
        map.put("sorting_type", sorting_type);
        map.put("sorting_way", sorting_way);
        map.put("page", page + "");
        map.put("pagesize", 10 + "");
        map.put("storeId", storeId);
        map.put("newGoods", "0");//默认0
        map.put("sign",  GetSign.getSign(map));
        ApiRequest.getStoreGoodsList(map, new MyCallBack<List<GoodsListObj>>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(List<GoodsListObj> list) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(list, true);
                } else {
                    pageNum = 2;
                    adapter.setList(list, true);
                }
            }
        });

    }

    @Override
    public void loadMore() {

    }
}
