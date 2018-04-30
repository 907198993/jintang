package com.sk.jintang.module.shoppingcart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyLinearLayout;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
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
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ShopTab3Fragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener {


    @BindView(R.id.ll_goods_order)
    MyLinearLayout llGoodsOrder;
    @BindView(R.id.rv_order_class)
    RecyclerView rvOrderClass;
    @BindView(R.id.pcfl_refresh)
    PtrClassicFrameLayout pcflRefresh;

    private int screenWidth;
    LoadMoreAdapter adapter;
    //类别ID(0查全部)
    private int type_id = 0;
    //是否是新品
    private int newGoods = 0;
    //当前页码
    private int page = 0;
    //每页数量
    private int pagesize = 10;

    private String sorting_type = "1";
    private String sorting_way = "0";
    private  String storeId;
    @Override
    protected int getContentView() {
        return R.layout.shop_tab2_fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle!=null){
            storeId = bundle.getString("storeId");
        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }
    @Override
    protected void initView() {
        llGoodsOrder.setVisibility(View.GONE);
        screenWidth = PhoneUtils.getScreenWidth(getActivity());
        adapter = new LoadMoreAdapter<GoodsListObj>(getActivity(), R.layout.shop_goods_detail_item, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, GoodsListObj bean) {
                TextView tv_goods_name = holder.getTextView(R.id.tv_goods_name);//商品名称
                tv_goods_name.setText(bean.getGoods_name());
                TextView tv_goods_price = holder.getTextView(R.id.tv_goods_price);
                tv_goods_price.setText(bean.getPrice() + "");
                int imgWidth = (screenWidth - 2) / 2;
                ImageView iv_goods_img = holder.getImageView(R.id.iv_goods_img);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = imgWidth;
                layoutParams.height = imgWidth;
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
        rvOrderClass.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvOrderClass.addItemDecoration(new DividerGridItemDecoration(getActivity(), 2));

        rvOrderClass.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1, false);
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    @Override
    protected void onViewClick(View v) {

    }


    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type_id", type_id + "");
        map.put("newGoods", newGoods + "");
        map.put("sorting_type", sorting_type);
        map.put("sorting_way", sorting_way);
        map.put("page", page + "");
        map.put("pagesize", 10 + "");
        map.put("rnd", "0");
        map.put("sign", "admin123");
        map.put("storeId", storeId);
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
        getData(pageNum, true);
    }

}
