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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
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
import com.sk.jintang.module.home.activity.NewScanActivity;
import com.sk.jintang.module.home.event.SelectGoodsCategoryEvent;
import com.sk.jintang.module.home.network.response.BrandObj;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.CityObj;
import com.sk.jintang.module.orderclass.network.response.GoodsCategoryObj;
import com.sk.jintang.module.orderclass.network.response.GoodsListObj;
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
 * Created by administartor on 2017/8/4.
 */

public class GoodsClassActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener {
    @BindView(R.id.rv_order_class)
    RecyclerView rv_order_class;

    LoadMoreAdapter adapter;
    @BindView(R.id.dl_order_class)
    DrawerLayout dl_order_class;

    MyPopupwindow popupwindow;

    @BindView(R.id.tv_goods_tuijian)
    TextView tv_goods_tuijian;
    @BindView(R.id.et_goods_min_price)
    EditText et_goods_min_price;
    @BindView(R.id.et_goods_max_price)
    EditText et_goods_max_price;
    @BindView(R.id.ll_goods_order)
    LinearLayout ll_goods_order;
    @BindView(R.id.tv_goods_price)
    TextView tv_goods_price;
    @BindView(R.id.tv_goods_xl)
    TextView tv_goods_xl;
    @BindView(R.id.rv_goods_shaixuan_pp)
    RecyclerView rv_goods_shaixuan_pp;
    @BindView(R.id.rv_goods_shaixuan_city)
    RecyclerView rv_goods_shaixuan_city;
    @BindView(R.id.tv_goods_shaixuan_reset)
    TextView tv_goods_shaixuan_reset;
    @BindView(R.id.tv_goods_shaixuan_complete)
    TextView tv_goods_shaixuan_complete;
    @BindView(R.id.iv_goods_class_scan)
    ImageView iv_goods_class_scan;
    private TextView selectCategoryView;

    //类别ID(0查全部)
    private String type_id = "0";
    //(0查全部 1推荐 2热销)
    private int category = 0;
    //品牌ID(0查全部)
    private int brand_id = 0;
    //最低价
    private int min_price = 0;
    //最高价
    private int max_price = 0;
    //地区
    private String address = "";
    private int screenWidth;
    private List<GoodsCategoryObj> categoryObjList;
    private LoadMoreAdapter brandAdapter;
    private LoadMoreAdapter cityAdapter;
    private LoadMoreAdapter categoryAdapter;


    private int tabSelectColor;
    private int tabNormalColor;
    private String typeName;
    private String sorting_type="1";
    private String sorting_way="0";
    private String  typeId;
    @Override
    protected int getContentView() {
        return R.layout.frag_order_class;
    }

    @Override
    protected void initView() {
        typeId = getIntent().getStringExtra("typeId");
        tabNormalColor= ContextCompat.getColor(getApplicationContext(), R.color.green);
        tabSelectColor=Color.parseColor("#e0e0e0");
        screenWidth = PhoneUtils.getScreenWidth(mContext);

        int imgWidth = (screenWidth - 2) / 2 - PhoneUtils.dip2px(mContext, 20);
        adapter = new LoadMoreAdapter<GoodsListObj>(mContext, R.layout.item_goods, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, GoodsListObj bean) {
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
        rv_order_class.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_order_class.addItemDecoration(new DividerGridItemDecoration(mContext,2));
        //new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 5))
        rv_order_class.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1, false);
            }
        });

//        cb_order_class_all.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    if (notEmpty(categoryObjList)) {
//                        showFenLei(categoryObjList);
//                    } else {
//                        getFenLei();
//                    }
//                }
//                return true;
//            }
//        });

        if(!TextUtils.isEmpty(typeName)){
//            cb_order_class_all.setText(typeName);
        }
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        RxBus.getInstance().toObservableSticky(SelectGoodsCategoryEvent.class).subscribe(new MySubscriber<SelectGoodsCategoryEvent>() {
            @Override
            public void onMyNext(SelectGoodsCategoryEvent event) {
                setTypeId(event.typeId,event.typeName);
                getData(1,false);
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
//        getDefaultFenLei();
        getData(1, false);
    }
    public void setTypeId(String id,String typeName){
        this.typeName =typeName;
        type_id=id;
    }
    public void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type_id", typeId + "");
        map.put("Category", category + "");
        map.put("brand_id", brand_id + "");
        map.put("min_price", min_price + "");
        map.put("max_price", max_price + "");
        map.put("sorting_type", sorting_type);
        map.put("sorting_way", sorting_way);
        map.put("address", address);
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        //4e78d62bb8837ffe8436fc2769e99210
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getGoodsList(map, new MyCallBack<List<GoodsListObj>>(mContext, pcfl, pl_load) {
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
    // 显示分类
    private void showFenLei(List<GoodsCategoryObj> list) {
            categoryAdapter = new LoadMoreAdapter<GoodsCategoryObj>(mContext, R.layout.item_goods_category, pageSize) {
                MyTextView selectCategory;
                @Override
                public void bindData(LoadMoreViewHolder holder, int position, GoodsCategoryObj bean) {
                    MyTextView tv_goods_category = (MyTextView) holder.getView(R.id.tv_goods_category);
                    tv_goods_category.setText(bean.getGoods_type_name());
                    if(bean.getGoods_type_id()== type_id){
                        selectCategory = tv_goods_category;
                        tv_goods_category.setSolidColor(tabSelectColor);
                        tv_goods_category.complete();
                    }else{
                        tv_goods_category.setSolidColor(tabNormalColor);
                        tv_goods_category.complete();
                    }
                    tv_goods_category.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            if (selectCategory == null) {
                                selectCategory = tv_goods_category;
                                selectCategory.setSolidColor(tabNormalColor);
                                selectCategory.complete();
//                                cb_order_class_all.setText(bean.getGoods_type_name());

                                getDataForCategory(bean.getGoods_type_id());
//                                cb_order_class_all.setChecked(true);
                            } else if (selectCategory != tv_goods_category) {

                                selectCategory.setSolidColor(tabNormalColor);
                                selectCategory.complete();

                                selectCategory = tv_goods_category;

                                selectCategory.setSolidColor(tabSelectColor);
                                selectCategory.complete();
//                                cb_order_class_all.setText(bean.getGoods_type_name());

                                getDataForCategory(bean.getGoods_type_id());
//                                cb_order_class_all.setChecked(true);
                            } else {
                            }
                            popupwindow.dismiss();
                        }

                        private void getDataForCategory(String typeId) {
                            type_id = typeId;
                            showLoading();
                            getData(1, false);
                        }
                    });
                }
            };
            categoryAdapter.setList(list);
            View fenLeiView = LayoutInflater.from(mContext).inflate(R.layout.popu_goods_class, null);
            RecyclerView rv_goods_category = fenLeiView.findViewById(R.id.rv_goods_category);

            rv_goods_category.setLayoutManager(new GridLayoutManager(mContext, 4));
            rv_goods_category.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 10), R.color.white));
            rv_goods_category.setAdapter(categoryAdapter);

            popupwindow = new MyPopupwindow(mContext, fenLeiView, PhoneUtils.getScreenWidth(mContext), -2);

//        popupwindow.showAsDropDown(cb_order_class_all);
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
                    tv_goods_price.setText(text);
                    tv_goods_xl.setText("销量");
                }else{//销量
                    sorting_type ="2";
                    sorting_way =index+"";
                    xiaoLiangPopu.dismiss();
                    tv_goods_price.setText("价格");
                    tv_goods_xl.setText(text);
                }
//                textView.setText(text);
                showLoading();
                getData(1,false);
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

            pricePopu=new MyPopupwindow(mContext,priceOrderView,PhoneUtils.getScreenWidth(mContext)/4,-1);
        }
        pricePopu.showAsDropDown(ll_goods_order,PhoneUtils.getScreenWidth(mContext)/4*1,0);
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

            xiaoLiangPopu=new MyPopupwindow(mContext,xiaoLiangOrderView,PhoneUtils.getScreenWidth(mContext)/4,-1);
        }
        xiaoLiangPopu.showAsDropDown(ll_goods_order,PhoneUtils.getScreenWidth(mContext)/4*2,0);
    }
    @OnClick({R.id.tv_goods_price,
            R.id.tv_goods_xl,R.id.iv_goods_class_scan,R.id.ll_goods_class_search,R.id.iv_goods_msg,R.id.tv_order_class_sx, R.id.tv_goods_tuijian ,R.id.tv_goods_shaixuan_reset,R.id.tv_goods_shaixuan_complete})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_goods_price:
                showPricePopu();
                break;
            case R.id.tv_goods_xl:
                showXiaoLiangPopu();
                break;
            case R.id.iv_goods_class_scan:
                STActivity(NewScanActivity.class);
                break;
            case R.id.ll_goods_class_search:
                STActivity(SearchGoodsActivity.class);
                break;
            case R.id.iv_goods_msg:
                goHX();
                /*if (TextUtils.isEmpty(getUserId())){
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivity(MyMessageActivity.class);*/
                break;
            case R.id.tv_goods_shaixuan_reset: //筛选重置
                address="";
                brand_id=0;
                brandAdapter.notifyDataSetChanged();
                cityAdapter.notifyDataSetChanged();
                min_price=0;
                max_price=0;
                et_goods_min_price.setText(null);
                et_goods_max_price.setText(null);
                break;
            case R.id.tv_goods_shaixuan_complete://筛选完成
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
            case R.id.tv_order_class_sx:  //筛选按钮
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
                        map.put("sign", GetSign.getSign(map));//品牌列表
                        ResponseObj<List<BrandObj>> brand = ApiRequest.getBrandList(map);

                        Map<String, String> mapParam = new HashMap<String, String>();
                        mapParam.put("rnd", getRnd());
                        mapParam.put("sign", GetSign.getSign(mapParam));
                        //城市列表
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
            case R.id.tv_goods_tuijian:
                getDataForCategory(1, tv_goods_tuijian);
                break;
          /*  case R.id.tv_goods_hot:
//                getDataForCategory(2, tv_goods_hot);
                break;*/
        }
    }
                //向筛选填充数据
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

    private void getDataForCategory(int categoryId, TextView textView) {
        if (selectCategoryView == null) {
            category = categoryId;
            selectCategoryView = textView;
            selectCategoryView.setTextColor(tabNormalColor);
        } else if (selectCategoryView != textView) {
            category = categoryId;
            selectCategoryView.setTextColor(ContextCompat.getColor(mContext, R.color.gray_33));
            selectCategoryView = textView;
            selectCategoryView.setTextColor(tabNormalColor);
        } else {
            category = 0;
            selectCategoryView.setTextColor(ContextCompat.getColor(mContext, R.color.gray_33));
            selectCategoryView = null;
        }
        showLoading();
        getData(1, false);
    }

//    private void getFenLei() {
//        showLoading();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("rnd", getRnd());
//        map.put("sign", GetSign.getSign(map));
//        ApiRequest.getGoodsCategory(map, new MyCallBack<List<GoodsCategoryObj>>(mContext) {
//            @Override
//            public void onSuccess(List<GoodsCategoryObj> list) {
//                categoryObjList = list;
//                showFenLei(categoryObjList);
//            }
//        });
//
//    }
//    private void getDefaultFenLei() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("rnd", getRnd());
//        map.put("sign", GetSign.getSign(map));
//        ApiRequest.getGoodsCategory(map, new MyCallBack<List<GoodsCategoryObj>>(mContext) {
//            @Override
//            public void onSuccess(List<GoodsCategoryObj> list) {
//                categoryObjList = list;
//                if(notEmpty(list)&&TextUtils.isEmpty(typeName)){
////                    cb_order_class_all.setText(list.get(0).getGoods_type_name());
//                }else{
////                    cb_order_class_all.setText(typeName);
//                }
//                typeName="";
//            }
//        });
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    break;
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void loadMore() {
        Log.i("===","===商品loadMore");
        getData(pageNum, true);
    }
}
