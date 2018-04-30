package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

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
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.CityObj;
import com.sk.jintang.tools.SpaceItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/2.
 */

public class SelectCityActivity extends BaseActivity {
    @BindView(R.id.rv_hot_city)
    RecyclerView rv_hot_city;

    @BindView(R.id.rv_all_city)
    RecyclerView rv_all_city;

    @BindView(R.id.tv_city_select_province)
    TextView tv_city_select_province;

    @BindView(R.id.tv_current_city)
    TextView tv_current_city;

    LoadMoreAdapter hotCityAdapter,allCityAdapter,provinceAdapter;

    protected BottomSheetDialog provinceDialog;
    @Override
    protected int getContentView() {
        setAppTitle("选取地址");
        return R.layout.act_select_city;
    }

    @Override
    protected void initView() {
        tv_current_city.setText(SPUtils.getPrefString(mContext, Config.city,"定位失败"));

        rv_hot_city.setNestedScrollingEnabled(false);
        rv_all_city.setNestedScrollingEnabled(false);
        hotCityAdapter =new LoadMoreAdapter<CityObj>(mContext,R.layout.item_hot_city,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, CityObj bean) {
                holder.setText(R.id.tv_hotcity_name,bean.getTitle());
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.city,bean.getTitle());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        };
        rv_hot_city.setLayoutManager(new GridLayoutManager(mContext,3));
        rv_hot_city.addItemDecoration(new SpaceItemDecoration(10));
        rv_hot_city.setAdapter(hotCityAdapter);

        allCityAdapter=new LoadMoreAdapter<CityObj>(mContext,R.layout.item_all_city,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, CityObj bean) {
                holder.setText(R.id.tv_city_name,bean.getTitle());
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.city,bean.getTitle());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        };
        rv_all_city.setLayoutManager(new LinearLayoutManager(mContext));
        rv_all_city.setAdapter(allCityAdapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getHotCity();
//        getAllCity();
    }

    private void getHotCity() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getHotCity(map, new MyCallBack<List<CityObj>>(mContext,pl_load) {
            @Override
            public void onSuccess(List<CityObj> list) {
                hotCityAdapter.setList(list,true);
            }
        });
    }

    private void getAllCity() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAllCity(map, new MyCallBack<List<CityObj>>(mContext,pl_load) {
            @Override
            public void onSuccess(List<CityObj> list) {
                allCityAdapter.setList(list,true);
            }
        });
    }

    @OnClick({R.id.tv_city_select_province,R.id.ll_select_current_city})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ll_select_current_city:
                if("定位中".equals(getSStr(tv_current_city))||"定位失败".equals(getSStr(tv_current_city))){
                    showMsg("定位失败无法选择");
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.city,getSStr(tv_current_city));
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.tv_city_select_province:
                selectProvince();
                break;
        }
    }
    private void selectProvince() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getProvince(map, new MyCallBack<List<CityObj>>(mContext) {
            @Override
            public void onSuccess(List<CityObj> list) {
                showProvince(list);
            }
        });

    }
    private void showProvince(List<CityObj> list) {
        if (provinceDialog == null) {
            provinceAdapter=new LoadMoreAdapter<CityObj>(mContext,R.layout.item_select_province,0) {
                @Override
                public void bindData(LoadMoreViewHolder holder, int position, CityObj bean) {
                    TextView textView = holder.getTextView(R.id.tv_province_name);
                    textView.setText(bean.getTitle());
                    textView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            provinceDialog.dismiss();
                            tv_city_select_province.setText(bean.getTitle());
                            getCityForProvince(bean.getId()+"");
                        }
                    });
                }
            };
            provinceAdapter.setList(list);
            View province= LayoutInflater.from(mContext).inflate(R.layout.popu_province,null);
            TextView tv_province_cancle = (TextView) province.findViewById(R.id.tv_province_cancle);
            tv_province_cancle.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    provinceDialog.dismiss();
                }
            });
            RecyclerView rv_province = (RecyclerView) province.findViewById(R.id.rv_province);
            rv_province.setLayoutManager(new LinearLayoutManager(mContext));
            rv_province.setAdapter(provinceAdapter);

            provinceDialog=new BottomSheetDialog(mContext);
            provinceDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            provinceDialog.setCanceledOnTouchOutside(true);
            provinceDialog.setContentView(province);
        }else{

        }
        provinceDialog.show();
    }
    private void getCityForProvince(String parentId) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("parent_id",parentId);
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getCityForProvince(map, new MyCallBack<List<CityObj>>(mContext) {
            @Override
            public void onSuccess(List<CityObj> list) {
                allCityAdapter.setList(list,true);
            }
        });
    }
}
