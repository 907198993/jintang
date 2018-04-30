package com.sk.jintang.module.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
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
import com.sk.jintang.module.home.network.response.FactoryObj;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.orderclass.activity.SelectCityActivity;

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

public class GongChangActivity extends BaseActivity  {
    @BindView(R.id.rv_gong_chang)
    RecyclerView rv_gong_chang;
    @BindView(R.id.app_right_city)
    TextView app_right_city;
    private LoadMoreAdapter adapter;
    private String address="";
    @Override
    protected int getContentView() {
        setAppTitle("工厂直达");
        setAppRightImg(R.drawable.share_title);
        return R.layout.act_gong_chang;
    }

    @Override
    protected void initView() {
        address = SPUtils.getPrefString(mContext, Config.city, "上海市");
//        app_right_city.setVisibility(View.VISIBLE);
        app_right_city.setText(address);
        adapter=new LoadMoreAdapter<FactoryObj>(mContext,R.layout.item_gong_chang,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, FactoryObj bean) {
                holder.setText(R.id.tv_gc_xilie,bean.getXilie_name());
                RecyclerView rv_gc_list = (RecyclerView) holder.getView(R.id.rv_gc_list);

                LoadMoreAdapter childerAdapter=new LoadMoreAdapter<FactoryObj.FactoryListBean>(mContext,R.layout.item_home_gczd,pageSize) {
                    @Override
                    public void bindData(LoadMoreViewHolder childerHolder, int childerPosition, FactoryObj.FactoryListBean childerBean) {
                        LinearLayout ll_gc_view = (LinearLayout) childerHolder.getView(R.id.ll_gc_view);
                        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.width= PhoneUtils.getScreenWidth(mContext)/4;
                        layoutParams.height= PhoneUtils.getScreenWidth(mContext)/4;
                        ll_gc_view.setLayoutParams(layoutParams);
                        ImageView imageView = childerHolder.getImageView(R.id.iv_home_gc);
                        Glide.with(mContext).load(childerBean.getFactory_image()).error(R.color.c_press).into(imageView);
                        childerHolder.setText(R.id.tv_home_gc,childerBean.getFactory_name());
                        childerHolder.itemView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
                                    STActivity(LoginActivity.class);
                                    return;
                                }
                                Intent intent=new Intent();
                                intent.putExtra(Constant.IParam.gongChangId,childerBean.getFactory_id()+"");
                                ActUtils.STActivity((Activity) mContext,intent, GoodsForGongChangActivity.class);
                            }
                        });
                    }
                };

                childerAdapter.setList(bean.getFactory_list());

                rv_gc_list.setLayoutManager(new GridLayoutManager(mContext,4));
                rv_gc_list.setAdapter(childerAdapter);
            }
        };
        rv_gong_chang.setLayoutManager(new LinearLayoutManager(mContext));
        rv_gong_chang.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_gong_chang.setAdapter(adapter);

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

    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("province","");
        map.put("city","");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getFactoryList(map, new MyCallBack<List<FactoryObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<FactoryObj> list) {
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
