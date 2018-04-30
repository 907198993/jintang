package com.sk.jintang.module.my.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.StoreCollectObj;

import com.sk.jintang.module.shoppingcart.activity.ShopActivity;
import com.sk.jintang.shop.MainShopActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MyCollectStoreFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{

    @BindView(R.id.rv_my_collect)
    RecyclerView rv_my_collect;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.act_search_store_result;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<StoreCollectObj>(mContext,R.layout.item_my_store_collect,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, StoreCollectObj bean) {
               /* TextView tv_my_collect_yuanjia = holder.getTextView(R.id.tv_my_collect_yuanjia);
                tv_my_collect_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tv_my_collect_yuanjia.getPaint().setAntiAlias(true);
*/
                holder.setText(R.id.tv_my_collect_name,bean.getStoreName());
                ImageView imageView = holder.getImageView(R.id.iv_my_collect);
                Glide.with(mContext).load(bean.getStoreImg()).error(R.color.c_press).into(imageView);

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        //0商品不存在 1普通商品 2限时抢购 3团购),staus商品状态(0商品不存在或者活动已结束 1商品存在活动没结束
                        Intent intent=new Intent();
                        intent.putExtra(com.sk.jintang.module.my.Constant.IParam.storeId,bean.getStoreID()+"");
                        if(bean.getStoreType()==0){
                            STActivity(intent,ShopActivity.class);
                        }else{
                            STActivity(intent,MainShopActivity.class);
                        }


                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_my_collect.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_collect.addItemDecoration(getItemDivider());
        rv_my_collect.setAdapter(adapter);

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
        map.put("userId",getUserId());
        map.put("pageIndex",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign","admin123");
        ApiRequest.getMyStoreCollectionList(map, new MyCallBack<List<StoreCollectObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<StoreCollectObj> list) {
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
