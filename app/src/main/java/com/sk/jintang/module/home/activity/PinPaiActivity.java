package com.sk.jintang.module.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.utils.ActUtils;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.home.Constant;
import com.sk.jintang.module.home.network.ApiRequest;
import com.sk.jintang.module.home.network.response.BrandObj;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.tools.DividerGridItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/31.
 */

public class PinPaiActivity extends BaseActivity {
    @BindView(R.id.rv_pin_pai)
    RecyclerView rv_pin_pai;
    private LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("品牌");
        setAppRightImg(R.drawable.share_title);
        return R.layout.act_pin_pai;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<BrandObj>(mContext,R.layout.item_home_pp,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, BrandObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_home_pp);
                Glide.with(mContext).load(bean.getBrand_img()).error(R.color.white).into(imageView);

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
                            STActivity(LoginActivity.class);
                            return;
                        }
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.pinPaiId,bean.getBrand_id()+"");
                        ActUtils.STActivity((Activity) mContext,intent, GoodsForPinPaiActivity.class);
                    }
                });
            }
        };
        rv_pin_pai.setLayoutManager(new GridLayoutManager(mContext,3));
        rv_pin_pai.addItemDecoration(new DividerGridItemDecoration(mContext,2));
        rv_pin_pai.setAdapter(adapter);
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
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getBrandList(map, new MyCallBack<List<BrandObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<BrandObj> list) {
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
}
