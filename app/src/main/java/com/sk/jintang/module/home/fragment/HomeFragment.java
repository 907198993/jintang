package com.sk.jintang.module.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
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
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.home.activity.NewScanActivity;
import com.sk.jintang.module.home.adapter.HomeGCZDAdapter;
import com.sk.jintang.module.home.adapter.HomeHuoDongAdapter;
import com.sk.jintang.module.home.adapter.HomeOtherHuoDongAdapter;
import com.sk.jintang.module.home.adapter.HomePPAdapter;
import com.sk.jintang.module.home.adapter.HomeShopAdatpter;
import com.sk.jintang.module.home.network.ApiRequest;
import com.sk.jintang.module.home.network.response.HomeBannerObj;
import com.sk.jintang.module.home.network.response.HomeButtomObj;
import com.sk.jintang.module.home.network.response.HomeGoodsObj;
import com.sk.jintang.module.home.network.response.HomeHuoDongObj;
import com.sk.jintang.module.home.network.response.SpecialRoadObj;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.my.activity.QianDaoActivity;
import com.sk.jintang.module.my.activity.YaoQingActivity;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.activity.GetVouchersActivity;
import com.sk.jintang.module.orderclass.activity.GoodsDetailActivity;
import com.sk.jintang.module.orderclass.activity.GoodsDetailTuanGouActivity;
import com.sk.jintang.module.orderclass.activity.GoodsDetailXianShiActivity;
import com.sk.jintang.module.orderclass.activity.HourDaoActivity;
import com.sk.jintang.module.orderclass.activity.JiuJinFaHuoActivity;
import com.sk.jintang.module.orderclass.activity.SearchGoods2Activity;
import com.sk.jintang.module.orderclass.activity.SelectCityActivity;
import com.sk.jintang.module.orderclass.activity.WeiShangDaiFaActivity;
import com.sk.jintang.module.orderclass.activity.XianShiQiangActivity;
import com.sk.jintang.tools.DividerGridItemDecoration;
import com.sk.jintang.tools.GlideLoader;
import com.sk.jintang.tools.ImageSizeUtils;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static android.app.Activity.RESULT_OK;

/**
 * Created by administartor on 2017/8/4.
 */

public class HomeFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{

//    @BindView(R.id.rv_home_huodong_other)
//    RecyclerView rv_home_huodong_other;

   // 限时抢购右边
    @BindView(R.id.rv_home_huodong)
    RecyclerView rv_home_huodong;

    // 特色推荐
    @BindView(R.id.rv_home)
    RecyclerView rv_home;

    //NestedScrollView
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    //品牌
    @BindView(R.id.rv_home_pp)
    RecyclerView rv_home_pp;
    //入口
    @BindView(R.id.rv_home_shop_category)
    RecyclerView rv_home_shop_category;

    @BindView(R.id.rv_home_gczd)
    RecyclerView rv_home_gczd;

    @BindView(R.id.bn_home)
    Banner bn_home;

    @BindView(R.id.tv_home_city)
    TextView tv_home_city;

    @BindView(R.id.tv_home_xsqg)
    TextView tv_home_xsqg;

    @BindView(R.id.iv_home_xsqg)
    ImageView iv_home_xsqg;

    @BindView(R.id.iv_home_xsd)
    ImageView iv_home_xsd;

    @BindView(R.id.tv_home_xsd)
    TextView tv_home_xsd;

    @BindView(R.id.iv_home_jjfh)
    ImageView iv_home_jjfh;

    @BindView(R.id.tv_home_jjfh)
    TextView tv_home_jjfh;

    @BindView(R.id.iv_home_wsdf)
    ImageView iv_home_wsdf;

    @BindView(R.id.tv_home_wsdf)
    TextView tv_home_wsdf;

    @BindView(R.id.iv_home_zpqz)
    ImageView iv_home_zpqz;

    @BindView(R.id.tv_home_zpqz)
    TextView tv_home_zpqz;

    @BindView(R.id.tv_home_toutiao)
    MarqueeView tv_home_toutiao;

    @BindView(R.id.ll_home_xsqg)
    LinearLayout ll_home_xsqg;

    private List<String> bannerList;
    @BindView(R.id.iv_home_qiandao)
    ImageView iv_home_qiandao;
    @BindView(R.id.iv_home_fenxiang)
    ImageView iv_home_fenxiang;
    @BindView(R.id.iv_home_lingqian)
    ImageView iv_home_lingqian;

    @BindView(R.id.tv_home_qiandao)
    TextView tv_home_qiandao;
    @BindView(R.id.tv_home_fenxiang)
    TextView tv_home_fenxiang;
    @BindView(R.id.tv_home_lingqian)
    TextView tv_home_lingqian;


    @BindView(R.id.iv_home_msg)
    ImageView iv_home_msg;


    HomePPAdapter huoDongAdapter;

    protected LoadMoreAdapter adapter;
    private HomeShopAdatpter homeShopAdapter;//商铺分类入口
    private HomeGCZDAdapter homeGCZDAdapter;//工程直达
    private HomePPAdapter homePPAdapter;//品牌列表
    private HomeHuoDongAdapter homeHuoDongAdapter;//今日爆款
//    private HomeOtherHuoDongAdapter homeOtherHuoDongAdapter;//按摩针灸


    private int screenWidth;
    private List<HomeBannerObj.ShufflingListBean> shufflingList;

    @Override
    protected int getContentView() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
       baiDuMap();
        tv_home_city.setText(SPUtils.getPrefString(mContext,Config.city,"成都市"));

        screenWidth = PhoneUtils.getScreenWidth(mContext);
      //  x　设置限时抢购的高度　
        int itemHeight = (screenWidth - PhoneUtils.dip2px(mContext, 106)) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height=itemHeight*2;

        ll_home_xsqg.setLayoutParams(layoutParams);

  //     加载更多
        adapter = new LoadMoreAdapter<HomeGoodsObj>(mContext, R.layout.item_goods, pageSize,nsv) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, HomeGoodsObj bean) {
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
                int imgWidth = (screenWidth - 2) / 2 - PhoneUtils.dip2px(mContext, 20);
                ImageView iv_goods_img = holder.getImageView(R.id.iv_goods_img);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight=imgWidth;
                layoutParams.height=imgWidth;
                iv_goods_img.setLayoutParams(layoutParams);
                Glide.with(mContext).load(bean.getGoods_image()).error(R.color.c_press).into(iv_goods_img);

                ImageView iv_goods_share = holder.getImageView(R.id.iv_goods_share);
                iv_goods_share.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showFenXiang(bean.getGoods_id()+"");
                    }
                });
                holder.setText(R.id.tv_goods_name,bean.getGoods_name())
                        .setText(R.id.tv_goods_address,bean.getAddresss())
                        .setText(R.id.tv_goods_price,""+bean.getPrice())
                        .setText(R.id.tv_goods_goumai,bean.getSales_volume()+"人购买");
                //item view 点击
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
        rv_home.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_home.setNestedScrollingEnabled(false);
        rv_home.setAdapter(adapter);
        rv_home.addItemDecoration(new DividerGridItemDecoration(mContext,2));

        //刷新
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getAllData();
            }
        });
        pcfl.disableWhenHorizontalMove(true);

        homeShopAdapter=new HomeShopAdatpter(mContext,0);
        homeGCZDAdapter=new HomeGCZDAdapter(mContext,0);
        homePPAdapter=new HomePPAdapter(mContext,0);
        homeHuoDongAdapter=new HomeHuoDongAdapter(mContext,0);
//        homeOtherHuoDongAdapter=new HomeOtherHuoDongAdapter(mContext,0);

       //商铺种类入口
        rv_home_shop_category.setAdapter(homeShopAdapter);
        rv_home_shop_category.setNestedScrollingEnabled(false);
        rv_home_shop_category.setLayoutManager(new GridLayoutManager(mContext,4));

        //工厂直达
        rv_home_gczd.setAdapter(homeGCZDAdapter);
        rv_home_gczd.setNestedScrollingEnabled(false);
        rv_home_gczd.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_home_gczd.addItemDecoration(new DividerGridItemDecoration(mContext,PhoneUtils.dip2px(mContext,1)));
       //品牌
        rv_home_pp.setAdapter(homePPAdapter);
        rv_home_pp.setNestedScrollingEnabled(false);
        rv_home_pp.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_home_pp.addItemDecoration(new DividerGridItemDecoration(mContext,PhoneUtils.dip2px(mContext,1)));

        rv_home_huodong.setAdapter(homeHuoDongAdapter);
        rv_home_huodong.setNestedScrollingEnabled(false);
        rv_home_huodong.setLayoutManager(new GridLayoutManager(mContext,3));
        rv_home_huodong.addItemDecoration(new DividerGridItemDecoration(mContext,PhoneUtils.dip2px(mContext,2)));

//        rv_home_huodong_other.setAdapter(homeOtherHuoDongAdapter);
//        rv_home_huodong_other.setNestedScrollingEnabled(false);
//        rv_home_huodong_other.setLayoutManager(new GridLayoutManager(mContext,4));
//        rv_home_huodong.addItemDecoration(new DividerGridItemDecoration(mContext,PhoneUtils.dip2px(mContext,2)));
    }

    @Override
    protected void initData() {
        showProgress();
        getAllData();
    }
    public void setRefresh(boolean isHorMove) {
        pcfl.setEnabled(!isHorMove);
    }

    public void getAllData(){
        getGoodsList(1,false);
        getHomeImg();
        getHomeHuoDong();
        getHomeButtomData();
        getSpecialRoad();
    }
    private void getSpecialRoad(){
        Map<String,String>map=new HashMap<String,String>();
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getSpecialRoad(map, new MyCallBack<List<SpecialRoadObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<SpecialRoadObj> obj) {
                homeShopAdapter.setList(obj,true);
            }
        });
    }

    private void getHomeButtomData() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
     // https://github.com/zhongruiAndroid/RetrofitTool
        ApiRequest.getHomeButtomData(map, new MyCallBack<HomeButtomObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(HomeButtomObj obj) {
                homeGCZDAdapter.setList(obj.getFactory_list(),true);
                homePPAdapter.setList(obj.getBrand_list(),true);
//                homeOtherHuoDongAdapter.setList(obj.getGoods_type_list(),true);

            }
        });

    }

    private void getGoodsList(int page,boolean isLoad) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getHomeGoods(map, new MyCallBack<List<HomeGoodsObj>>(mContext,pcfl,pl_load) {

            @Override
            public void onSuccess(List<HomeGoodsObj> list) {
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

    private void getHomeHuoDong() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));

        ApiRequest.getHomeHuoDong(map, new MyCallBack<HomeHuoDongObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(HomeHuoDongObj obj) {
                homeHuoDongAdapter.setList(obj.getShuffling_list(),true);
                try{
                    List<HomeHuoDongObj.FlashsaleListBean> xsqgList = obj.getFlashsale_list();//限时抢购
                    if(notEmpty(xsqgList)){
                        tv_home_xsqg.setText(xsqgList.get(0).getTitle());
                        Glide.with(mContext).load(xsqgList.get(0).getImg_url()).error(R.color.c_press).into(iv_home_xsqg);
                    }

                    List<HomeHuoDongObj.ListBean> wuLiuList = obj.getList();//物流发货
                    if(notEmpty(wuLiuList)){
                        tv_home_xsd.setText(wuLiuList.get(0).getTitle());
                        Glide.with(mContext).load(wuLiuList.get(0).getImg_url()).error(R.drawable.home12).into(iv_home_xsd);

                        tv_home_jjfh.setText(wuLiuList.get(1).getTitle());
                        Glide.with(mContext).load(wuLiuList.get(1).getImg_url()).error(R.drawable.home13).into(iv_home_jjfh);

                        tv_home_wsdf.setText(wuLiuList.get(2).getTitle());
                        Glide.with(mContext).load(wuLiuList.get(2).getImg_url()).error(R.drawable.home14).into(iv_home_wsdf);

                        tv_home_zpqz.setText(wuLiuList.get(3).getTitle());
                        Glide.with(mContext).load(wuLiuList.get(3).getImg_url()).error(R.drawable.home15).into(iv_home_zpqz);

                    }

                    if(notEmpty(obj.getHeadline_list())){
                        List<String> info = new ArrayList<>();
                        for (int i = 0; i < obj.getHeadline_list().size(); i++) {
                            info.add(obj.getHeadline_list().get(i).getTitle());
                        }
                        tv_home_toutiao.startWithList(info);
                    }

                }catch (Exception e){
                    showMsg("获取图片数据异常");
                }
            }
        });

    }


    private void getHomeImg() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getHomePage(map, new MyCallBack<HomeBannerObj>(mContext) {
            @Override
            public void onSuccess(HomeBannerObj obj) {
                List<HomeBannerObj.ListBean> imgList = obj.getList();
                if (notEmpty(imgList)) {
                    tv_home_qiandao.setText(imgList.get(0).getTitle());
                    tv_home_fenxiang.setText(imgList.get(1).getTitle());
                    tv_home_lingqian.setText(imgList.get(2).getTitle());
                    Glide.with(mContext).load(imgList.get(0).getImg_url()).error(R.drawable.home1).into(iv_home_qiandao);
                    Glide.with(mContext).load(imgList.get(1).getImg_url()).error(R.drawable.home2).into(iv_home_fenxiang);
                    Glide.with(mContext).load(imgList.get(2).getImg_url()).error(R.drawable.home3).into(iv_home_lingqian);
                }
                shufflingList = obj.getShuffling_list();
                if (notEmpty(obj.getShuffling_list())) {

                    bannerList = new ArrayList<String>();
                    for (int i = 0; i < obj.getShuffling_list().size(); i++) {
                        bannerList.add(obj.getShuffling_list().get(i).getImg_url());
                    }
                    bn_home.setLayoutParams(ImageSizeUtils.getImageSizeLayoutParams(mContext));
                    bn_home.setImages(bannerList);
                    bn_home.setImageLoader(new GlideLoader());

                    bn_home.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            HomeBannerObj.ShufflingListBean item = shufflingList.get(position);
                            //(0商品不存在 1普通商品 2限时抢购 3团购),status商品状态(0商品不存在或者活动已结束 1商品存在活动没结束
                            Intent intent=new Intent();
                            intent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.goodsId,item.getGoods_id()+"");
                            if(item.getCode()==1&&item.getStatus()==1){
                                STActivity(intent,GoodsDetailActivity.class);
                            }else if(item.getCode()==2&&item.getStatus()==1){
                                intent.setAction(Config.IParam.xianShiQiangGou);
                                STActivity(intent,GoodsDetailXianShiActivity.class);
                            }else if(item.getCode()==3&&item.getStatus()==1){
                                STActivity(intent,GoodsDetailTuanGouActivity.class);
                            }
                        }
                    });
                    bn_home.start();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 300:
                    if(data!=null){
                        tv_home_city.setText(data.getStringExtra(Config.IParam.city));
                    }
                    break;
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (bn_home != null && bannerList != null) {
            bn_home.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bn_home != null && bannerList != null) {
            bn_home.startAutoPlay();
        }
    }

    @OnClick({R.id.tv_home_city,R.id.ll_home_fenxiang,R.id.iv_home_scan,R.id.ll_home_search,R.id.ll_home_get_vouchers,R.id.ll_home_qiandao,R.id.iv_home_msg,R.id.tv_home_toutiao,R.id.ll_home_xsqg,   R.id.ll_home_xsd, R.id.ll_home_jjfh, R.id.ll_home_wsdf, R.id.ll_home_zpqz})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home_city:
                STActivityForResult(SelectCityActivity.class,300);
                break;
            case R.id.ll_home_fenxiang:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivity(YaoQingActivity.class);
                break;
            case R.id.iv_home_scan://扫描
                STActivity(NewScanActivity.class);
//                STActivity(ScanActivity.class);
                break;
            case R.id.ll_home_get_vouchers:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivity(GetVouchersActivity.class);
                break;
            case R.id.iv_home_msg:
                goHX();
                /*if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivity(MyMessageActivity.class);*/
            break;
            case R.id.tv_home_toutiao:
            break;
            case R.id.ll_home_xsqg:
                STActivity(XianShiQiangActivity.class);
                break;
            case R.id.ll_home_xsd:
                STActivity(HourDaoActivity.class);
                break;
            case R.id.ll_home_jjfh://就近发货
                STActivity(JiuJinFaHuoActivity.class);
                break;
            case R.id.ll_home_wsdf:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra(com.sk.jintang.module.home.Constant.IParam.goodsType,com.sk.jintang.module.home.Constant.IParam.goodsType_9);
                ActUtils.STActivity((Activity) mContext, intent,WeiShangDaiFaActivity.class);
                break;
            case R.id.ll_home_zpqz:
                showMsg("正在开发");
                break;
            case R.id.ll_home_qiandao:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivity(QianDaoActivity.class);
                break;
            case R.id.ll_home_search:
                STActivity(SearchGoods2Activity.class);
                break;
        }
    }
    @Override
    public void loadMore() {
        getGoodsList(pageNum,true);
    }

   public MyLocationListenner myListener = new MyLocationListenner();

    private void baiDuMap() {
        // 定位初始化
        LocationClient mLocClient = new LocationClient(mContext);
       mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    private boolean isFirstLoc=true;
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null  )
                return;

//            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
            if (isFirstLoc) {
                /*MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();*/
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());

                Log.i("===",location.getAddrStr()+"=="+location.getCity()+"==="+location.getDistrict());
                String city=location.getCity();
                SPUtils.setPrefString(mContext, Config.city,city);
                tv_home_city.setText(city);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
