package com.sk.jintang.shop;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.sk.jintang.module.home.network.ApiRequest;
import com.sk.jintang.module.home.network.response.SpecialStoreObj;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.activity.AgainEvaluateActivity;
import com.sk.jintang.module.my.network.response.MyEvaluateObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/8.
 */

public class ShopListsActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_my_evaluate)
    RecyclerView rv_my_evaluate;

    LoadMoreAdapter adapter;
    private  String specialStoreId;
    @Override
    protected int getContentView() {
        setAppTitle( getIntent().getStringExtra(com.sk.jintang.module.home.Constant.IParam.specialStoreTitle));
        return R.layout.act_my_evaluate;
    }

    @Override
    protected void initView() {
        specialStoreId = getIntent().getStringExtra(com.sk.jintang.module.home.Constant.IParam.specialStoreId);
        adapter=new LoadMoreAdapter<SpecialStoreObj>(mContext,R.layout.item_special_store_list,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, SpecialStoreObj bean) {
                ImageView iv_my_evaluate_img = holder.getImageView(R.id.iv_store);

                Glide.with(mContext).load(bean.getStoreImg()).error(R.color.c_press).into(iv_my_evaluate_img);
                holder.setText(R.id.tv_store_name,bean.getStoreName());
               if(bean.getIsFreePs()==0){
                   holder.setText(R.id.tv_price_fee,"起送价"+bean.getStartPs()+"|"+"配送费还没");
               }else{
                   holder.setText(R.id.tv_store_name,bean.getStoreName())
                           .setText(R.id.tv_price_fee,"起送价"+bean.getStartPs()+"|"+"配送免费");
               }
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.storeId,bean.getStoreID()+"");
                        STActivity(intent,MainShopActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_my_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_evaluate.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,10)));
        rv_my_evaluate.setAdapter(adapter);

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
        map.put("pageIndex",page+"");
        map.put("pageSize",pageSize+"");
        map.put("storeTypeId",specialStoreId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getSpecialStore(map, new MyCallBack<List<SpecialStoreObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<SpecialStoreObj> list) {
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
    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }

}
