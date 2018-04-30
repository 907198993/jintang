package com.sk.jintang.module.shoppingcart.fragment;

import android.content.Context;
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
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.activity.GoodsDetailActivity;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.ShopDetailObj;
import com.sk.jintang.tools.GlideLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ShopTab1Fragment extends BaseFragment  {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bn_goods_detail)
    Banner bn_goods_detail;
    private int screenWidth;
    private ArrayList<String>  bannerList;
    Context mContext;
    protected LoadMoreAdapter shopAdapter;
    List<ShopDetailObj.SlideImgListBean> obj;
    private List<ShopDetailObj.GoodsListBean> goodsListBeen;
    private  String storeId;

    @Override
    protected int getContentView() {
        return R.layout.shop_fragment;
    }

    @Override
    protected void initView() {

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
    protected void initData() {

        getData();
    }
    @Override
    protected void onViewClick(View v) {


    }
    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sign", "admin123");
        map.put("storeId", storeId);
        map.put("userId",getUsersId());
        ApiRequest.getShopsDetail(map, new MyCallBack<ShopDetailObj>(mContext) {
            @Override
            public void onSuccess(ShopDetailObj obj) {
                setBanner(obj.getSlideImgList());
                goodsListBeen = obj.getGoodsList();
                setList();
            }
        });

    }

    private void setBanner(List<ShopDetailObj.SlideImgListBean>  obj) {
        bannerList = new ArrayList<String>();
        for (int i = 0; i < obj.size(); i++) {
            bannerList.add(obj.get(i).getImg());
        }
        screenWidth = PhoneUtils.getScreenWidth(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height=screenWidth/2;
        layoutParams.width=screenWidth;
        bn_goods_detail.setLayoutParams(layoutParams);
        bn_goods_detail.setImages(bannerList);
        bn_goods_detail.setImageLoader(new GlideLoader());
        bn_goods_detail.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent=new Intent();
                intent.putStringArrayListExtra(Constant.IParam.imgList,bannerList);
                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.imgIndex,position);
//                图片详情
//                STActivity(intent,ImageDetailActivity.class);
            }
        });
        bn_goods_detail.start();
    }
    //商品列表
    public void setList(){
        screenWidth = PhoneUtils.getScreenWidth(getActivity());
        shopAdapter = new LoadMoreAdapter<ShopDetailObj.GoodsListBean>(getActivity(), R.layout.shop_goods_detail_item, 10) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, ShopDetailObj.GoodsListBean bean) {
                TextView tv_goods_name = holder.getTextView(R.id.tv_goods_name);//商品名称
                tv_goods_name.setText(bean.getGoodsName());
                TextView tv_goods_price = holder.getTextView(R.id.tv_goods_price);
                tv_goods_price.setText(bean.getGoodsPrice()+"");

                int imgWidth = (screenWidth - 2) / 2 ;
                ImageView iv_goods_img = holder.getImageView(R.id.iv_goods_img);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight=imgWidth;
                layoutParams.height=imgWidth;
                iv_goods_img.setLayoutParams(layoutParams);
                Glide.with(mContext).load(bean.getGoodsImg()).error(R.color.c_press).into(iv_goods_img);

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
//                        if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
////                            STActivity(LoginActivity.class);
//                            return;
//                        }
                        Intent intent = new Intent();
                        intent.putExtra(Constant.IParam.goodsId, bean.getGoods_id() + "");
                        STActivity(intent, GoodsDetailActivity.class);
                    }
                });
            }
        };
        shopAdapter.setList(goodsListBeen);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(shopAdapter);
    }
}
