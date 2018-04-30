package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.view.MyPopupwindow;
import com.github.customview.MyLinearLayout;
import com.github.customview.MyTextView;
import com.github.retrofitutil.NetworkMonitor;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.base.ResponseObj;
import com.sk.jintang.base.ServerException;
import com.sk.jintang.module.home.network.response.BrandObj;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.CityObj;
import com.sk.jintang.module.orderclass.network.response.GoodsSearchObj;
import com.sk.jintang.tools.DividerGridItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Subscriber;

/**
 * Created by administartor on 2017/10/10.
 */

public class SearchResultActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener {
    @BindView(R.id.rv_search_result)
    RecyclerView rv_search_result;

    @BindView(R.id.et_goods_min_price)
    EditText et_goods_min_price;
    @BindView(R.id.et_goods_max_price)
    EditText et_goods_max_price;
    @BindView(R.id.et_search_content)
    EditText et_search_content;
    @BindView(R.id.rv_goods_shaixuan_pp)
    RecyclerView rv_goods_shaixuan_pp;
    @BindView(R.id.rv_goods_shaixuan_city)
    RecyclerView rv_goods_shaixuan_city;

    @BindView(R.id.dl_order_class)
    DrawerLayout dl_order_class;
    @BindView(R.id.tv_goods_search_price)
    TextView tv_goods_search_price;

    @BindView(R.id.tv_goods_search_xl)
    TextView tv_goods_search_xl;
    @BindView(R.id.ll_search_order)
    LinearLayout ll_search_order;

    MyPopupwindow pricePopu,xiaoLiangPopu;

    LoadMoreAdapter adapter;

    private String searchStr;
    private int screenWidth;

    //品牌ID(0查全部)
    private int brand_id = 0;
    //最低价
    private int min_price = 0;
    //最高价
    private int max_price = 0;
    //地区
    private String address = "";


    private LoadMoreAdapter brandAdapter;
    private LoadMoreAdapter cityAdapter;

    private int tabSelectColor;
    private int tabNormalColor;
    private String salesVolumeOrder ="0";
    private String goodsPriceOrder ="0";

    @Override
    protected int getContentView() {
        setAppTitle("商品列表");
        setAppRightTitle("搜索");
        return R.layout.act_search_result;
    }

    private void searchGoods() {
        if (TextUtils.isEmpty(et_search_content.getText().toString())) {
            showMsg("请输入搜索内容");
        } else {
            PhoneUtils.hiddenKeyBoard(mContext,et_search_content);
            searchStr=et_search_content.getText().toString();
            getData(1,false);
        }
    }
    @Override
    protected void initView() {
        tabNormalColor= ContextCompat.getColor(mContext, R.color.green);
        tabSelectColor= Color.parseColor("#e0e0e0");
        searchStr = getIntent().getStringExtra(Constant.IParam.searchStr);
        et_search_content.setText(searchStr);
        et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchGoods();
                }
                return false;
            }
        });
        /*if(PhoneUtils.keyBoardIsOpen(mContext)){
            PhoneUtils.hiddenKeyBoard(mContext,rv_search_result);
        }*/
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
        map.put("search_term",searchStr);
        map.put("user_id",getUserId()==null?"0":getUserId());
        map.put("sales_volume", salesVolumeOrder + "");//销量排序(0默认 1大→小 2小→大)
        map.put("price", goodsPriceOrder + "");//价格排序(0默认 1大→小 2小→大)
        map.put("brand_id", brand_id + "");//品牌ID(0查全部)
        map.put("min_price", min_price + "");
        map.put("max_price", max_price + "");
        map.put("address", address);
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.goodsSearch(map, new MyCallBack<List<GoodsSearchObj>>(mContext,pl_load,pcfl) {
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
    private void setDataForSX(ResponseObj<List<BrandObj>> brand, ResponseObj<List<CityObj>> cityForGoods) {
        rv_goods_shaixuan_pp.setLayoutManager(new GridLayoutManager(mContext,3));
        if(brandAdapter==null){
            rv_goods_shaixuan_pp.addItemDecoration(new DividerGridItemDecoration(mContext,PhoneUtils.dip2px(mContext,10),R.color.white));
        }
        brandAdapter = new LoadMoreAdapter<BrandObj>(mContext, R.layout.item_goods_shaixuan_pp,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, BrandObj bean) {
                MyTextView tv_goods_shaixuan_pp = (MyTextView) holder.getView(R.id.tv_goods_shaixuan_pp);
                tv_goods_shaixuan_pp.setText(bean.getBrand_name());
                tv_goods_shaixuan_pp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(brand_id !=bean.getBrand_id()){
                            brand_id =bean.getBrand_id();
                        }else{
                            brand_id =0;
                        }
                        notifyDataSetChanged();
                    }
                });
                if(brand_id ==bean.getBrand_id()){
                    tv_goods_shaixuan_pp.setSolidColor(tabSelectColor);
                    tv_goods_shaixuan_pp.complete();
                }else{
                    tv_goods_shaixuan_pp.setSolidColor(tabNormalColor);
                    tv_goods_shaixuan_pp.complete();
                }
            }
        };
        brandAdapter.setList(brand.getResponse());
        rv_goods_shaixuan_pp.setAdapter(brandAdapter);



        rv_goods_shaixuan_city.setLayoutManager(new GridLayoutManager(mContext,3));
        if(cityAdapter==null){
            rv_goods_shaixuan_city.addItemDecoration(new DividerGridItemDecoration(mContext,PhoneUtils.dip2px(mContext,10),R.color.white));
        }
        cityAdapter = new LoadMoreAdapter<CityObj>(mContext, R.layout.item_goods_shaixuan_city,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, CityObj bean) {
                MyLinearLayout ll_goods_shaixuan_city = (MyLinearLayout) holder.getView(R.id.ll_goods_shaixuan_city);
                MyTextView tv_goods_shaixuan_city = (MyTextView) holder.getView(R.id.tv_goods_shaixuan_city);
                tv_goods_shaixuan_city.setText(bean.getCity());
                ll_goods_shaixuan_city.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!address.equals(bean.getCity())){
                            address =bean.getCity();
                        }else{
                            address ="";
                        }
                        notifyDataSetChanged();
                    }
                });
                if(address.equals(bean.getCity())){
                    ll_goods_shaixuan_city.setSolidColor(tabSelectColor);
                    ll_goods_shaixuan_city.complete();
                }else{
                    ll_goods_shaixuan_city.setSolidColor(tabNormalColor);
                    ll_goods_shaixuan_city.complete();
                }
            }
        };
        cityAdapter.setList(cityForGoods.getResponse());
        rv_goods_shaixuan_city.setAdapter(cityAdapter);

    }
    @OnClick({R.id.app_right_tv,R.id.tv_goods_shaixuan_reset,R.id.tv_goods_shaixuan_complete,R.id.tv_order_class_sx,R.id.tv_goods_search_price,R.id.tv_goods_search_xl})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_tv:
                searchGoods();
                break;
            case R.id.tv_goods_search_price:
                showPricePopu();
                break;
            case R.id.tv_goods_search_xl:
                showXiaoLiangPopu();
                break;
            case R.id.tv_goods_shaixuan_reset:
                address="";
                brand_id=0;
                brandAdapter.notifyDataSetChanged();
                cityAdapter.notifyDataSetChanged();
                min_price=0;
                max_price=0;
                et_goods_min_price.setText(null);
                et_goods_max_price.setText(null);
                break;
            case R.id.tv_goods_shaixuan_complete:
                if(TextUtils.isEmpty(getSStr(et_goods_min_price))){
                    min_price=0;
                }else{
                    min_price=Integer.parseInt(getSStr(et_goods_min_price));
                }
                if(TextUtils.isEmpty(getSStr(et_goods_max_price))){
                    max_price=0;
                }else{
                    max_price=Integer.parseInt(getSStr(et_goods_max_price));
                }
                if(min_price>max_price){
                    showMsg("最低价格不能大于最高价格");
                    return;
                }
                dl_order_class.closeDrawer(Gravity.RIGHT);
                showLoading();
                getData(1,false);
                break;
            case R.id.tv_order_class_sx:
                showLoading();
                if (!NetworkMonitor.isConnected(mContext)) {
                    showMsg(Config.noNetWork);
                    return;
                }
                RXStart(new IOCallBack<Map>() {
                    @Override
                    public void call(Subscriber<? super Map> subscriber) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("rnd", getRnd());
                        map.put("sign", GetSign.getSign(map));
                        ResponseObj<List<BrandObj>> brand = ApiRequest.getBrandList(map);

                        Map<String, String> mapParam = new HashMap<String, String>();
                        mapParam.put("rnd", getRnd());
                        mapParam.put("sign", GetSign.getSign(mapParam));
                        ResponseObj<List<CityObj>> cityForGoods = ApiRequest.getCityForGoodsList(mapParam);

                        if(brand.getErrCode()==1){
                            subscriber.onError(new ServerException(brand.getErrMsg()));
                        }
                        if(cityForGoods.getErrCode()==1){
                            subscriber.onError(new ServerException(cityForGoods.getErrMsg()));
                        }
                        Map<Integer, Object> listMap = new HashMap<Integer, Object>();
                        listMap.put(0, brand);
                        listMap.put(1, cityForGoods);

                        subscriber.onNext(listMap);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onMyNext(Map map) {
                        setDataForSX((ResponseObj<List<BrandObj>>)map.get(0),(ResponseObj<List<CityObj>>)map.get(1));
                        dl_order_class.openDrawer(Gravity.RIGHT);
                    }

                    @Override
                    public void onMyCompleted() {
                        super.onMyCompleted();
                        dismissLoading();
                    }
                    @Override
                    public void onMyError(Throwable e) {
                        super.onMyError(e);
                        dismissLoading();
                        if(e instanceof ServerException){
                            showMsg(e.getMessage());
                        }else{
                            showMsg("连接失败");
                        }
                    }
                });
                break;
        }
    }


    private void showPricePopu() {
        if(pricePopu==null){
            View priceOrderView = getLayoutInflater().inflate(R.layout.popu_price_order, null);

            TextView tv_search_price_order = priceOrderView.findViewById(R.id.tv_search_price_order);
            tv_search_price_order.setOnClickListener(getOrderListener(0,0,tv_search_price_order,"价格排序"));

            TextView tv_search_price_order_asc = priceOrderView.findViewById(R.id.tv_search_price_order_asc);
            tv_search_price_order_asc.setOnClickListener(getOrderListener(0,2,tv_search_price_order_asc,"价格从低到高"));

            TextView tv_search_price_order_desc = priceOrderView.findViewById(R.id.tv_search_price_order_desc);
            tv_search_price_order_desc.setOnClickListener(getOrderListener(0,1,tv_search_price_order_desc,"价格从高到低"));

            pricePopu=new MyPopupwindow(mContext,priceOrderView,PhoneUtils.getScreenWidth(mContext)/3,-1);
        }
        pricePopu.showAsDropDown(ll_search_order);
    }
    private void showXiaoLiangPopu() {
        if(xiaoLiangPopu==null){
            View xiaoLiangOrderView = getLayoutInflater().inflate(R.layout.popu_xiaoliang_order, null);

            TextView tv_search_xl_order = xiaoLiangOrderView.findViewById(R.id.tv_search_xl_order);
            tv_search_xl_order.setOnClickListener(getOrderListener(1,0,tv_search_xl_order,"销量排序"));

            TextView tv_search_xl_order_asc = xiaoLiangOrderView.findViewById(R.id.tv_search_xl_order_asc);
            tv_search_xl_order_asc.setOnClickListener(getOrderListener(1,2,tv_search_xl_order_asc,"销量从低到高"));

            TextView tv_search_xl_order_desc = xiaoLiangOrderView.findViewById(R.id.tv_search_xl_order_desc);
            tv_search_xl_order_desc.setOnClickListener(getOrderListener(1,1,tv_search_xl_order_desc,"销量从高到低"));

            xiaoLiangPopu=new MyPopupwindow(mContext,xiaoLiangOrderView,PhoneUtils.getScreenWidth(mContext)/3,-1);
        }
        xiaoLiangPopu.showAsDropDown(ll_search_order,PhoneUtils.getScreenWidth(mContext)/3,0);
    }

    @Override
    public void onBackPressed() {
        if(dl_order_class.isDrawerOpen(Gravity.RIGHT)){
            dl_order_class.closeDrawer(Gravity.RIGHT);
        }else{
            super.onBackPressed();
        }
    }

    @NonNull
    private MyOnClickListener getOrderListener(int flag,int index,TextView textView,String text) {
        return new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if(flag==0){//价格
                    goodsPriceOrder=index+"";
                    salesVolumeOrder="0";
                    pricePopu.dismiss();
                    tv_goods_search_price.setText(text);
                    tv_goods_search_xl.setText("销量排序");
                }else{//销量
                    salesVolumeOrder=index+"";
                    goodsPriceOrder="0";
                    xiaoLiangPopu.dismiss();
                    tv_goods_search_price.setText("价格排序");
                    tv_goods_search_xl.setText(text);
                }
//                textView.setText(text);
                showLoading();
                getData(1,false);
            }
        };
    }


    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
