package com.sk.jintang.module.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.sk.jintang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */

public class MapActivity  extends Activity implements BaiduMap.OnMapStatusChangeListener,OnGetGeoCoderResultListener,
        BDLocationListener,OnGetPoiSearchResultListener {
    private MapView map;
    private BaiduMap mBaiduMap;
    private GeoCoder geoCoder;
    private ListView lv_near_address,lv_search;
    protected static final String TAG = "ConversationActivity";
    private LocationClient mLocClient;
    private LatLng locationLatLng;
    private String city;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private EditText keyWorldsView;
    private LinearLayout ll_map;
    private LinearLayout ll_search;
    private ImageView iv_left;
    private boolean acStateIsMap=true;
    private PoiSearch mPoiSearch;
    List<LatLng> mPoints=new ArrayList<LatLng>();

    //----------------------------activity覆盖方法----------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_activity);
        initView();
//        setDeliveryScope();//如果需要设置配送的范围则加上此段代码
        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        map.onDestroy();
        if (geoCoder != null) {
            geoCoder.destroy();
        }
        mPoiSearch.destroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        map.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        map.onPause();
    }

    //----------------------------private方法----------------------------//

    private void initView() {
        //获取百度地图实例
        map = (MapView) findViewById(R.id.map);
        lv_near_address = (ListView) findViewById(R.id.lv_near_address);
        ll_map = (LinearLayout) findViewById(R.id.ll_map);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);
        lv_search = (ListView) findViewById(R.id.lv_search);
        keyWorldsView = (EditText) findViewById(R.id.et_search);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        mBaiduMap = map.getMap();
    }

    /**
     * 设置配送的范围
     */
//    private void setDeliveryScope() {
//        //配送范围的点自行设置
//        LatLng	latLng1=new LatLng(34.22898269265894,108.87636764022244);
//        LatLng	latLng2=new LatLng(34.225323612679404,108.87819186944901);
//        LatLng	latLng3=new LatLng(34.22578857521661,108.88394050365343);
//        LatLng	latLng4=new LatLng(34.2297155315396,108.88647470436645);
//        mPoints.add(latLng1);
//        mPoints.add(latLng2);
//        mPoints.add(latLng3);
//        mPoints.add(latLng4);
//        //构建用户绘制多边形的Option对象,绘制可配送区域
//        OverlayOptions polygonOption = new PolygonOptions()
//                .points(mPoints)
//                .stroke(new Stroke(5, 0xAA00FF00))
//                .fillColor(0xAAFFFF00);
//        //在地图上添加多边形Option，用于显示
//        mBaiduMap.addOverlay(polygonOption);
//    }

    private void initData() {

        //----------------------------设置地图状态改变----------------------------//
        MapStatus mapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        // 地图状态改变相关监听
        mBaiduMap.setOnMapStatusChangeListener(this);

        //----------------------------反地理编码GeoCoder---------------------------//
        // 创建GeoCoder实例对象
        geoCoder = GeoCoder.newInstance();
        // 设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(this);
        //----------------------------poi搜索模块设置，注册搜索事件监听---------------------------//
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        //----------------------------控件点击事件处理---------------------------//
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!acStateIsMap){
                    ll_map.setVisibility(View.VISIBLE);
                    ll_search.setVisibility(View.GONE);
                    acStateIsMap=true;
                }
            }
        });
        keyWorldsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acStateIsMap) {
                    ll_map.setVisibility(View.GONE);
                    ll_search.setVisibility(View.VISIBLE);
                    acStateIsMap = false;
                }
            }
        });
        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        keyWorldsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {}
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //当EditText控件中文字状态发生改变时调用
                if (cs.length() <= 0) {return;}
                mPoiSearch.searchInCity((new PoiCitySearchOption()).city("成都").keyword(cs.toString()));
            }
        });


        //---------------------------------------百度定位设置------------------------------------//
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位图层显示方式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
        mLocClient = new LocationClient(this);
        // 注册定位监听
        mLocClient.registerLocationListener(this);
        // 定位参数选项
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        // 设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
        // 可以用作地址信息的补充
        option.setIsNeedLocationDescribe(true);
        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 设置是否打开gps进行定位
        option.setOpenGps(true);
        // 设置 LocationClientOption
        mLocClient.setLocOption(option);
        // 开始定位
        mLocClient.start();
    }

    //-----------------------MapStatusChange监听回调方法----------------------//

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }


    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    /**
     *
     * 当用户拖动地图结束时，调用该方法
     */
    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        // 获取地图最后状态改变的中心点
        LatLng cenpt = mapStatus.target;
        Toast.makeText(getApplicationContext(), "最后停止点:" + cenpt.latitude+","+cenpt.longitude, Toast.LENGTH_LONG).show();
        //判断定位点是否在可配送范围内---如果需要设置配送的范围则加上此段代码
//        if (!SpatialRelationUtil.isPolygonContainsPoint(mPoints, cenpt)){
//            Toast.makeText(getApplicationContext(),"该地址不在配送范围，请在黄色区域内选择",Toast.LENGTH_SHORT).show();
//        }
        //将中心点坐标转化为具体位置信息，当转化成功后调用onGetReverseGeoCodeResult()方法
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
    }
    //-----------------------地理编码监听回调方法----------------------//
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        //地址-经纬度

    }

    /**
     * 经纬度转化地址成功后调用
     */
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        final List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
        for(int i=0;i<poiInfos.size();i++){
            Log.i(TAG, "这里的值:" + poiInfos.get(i).name);
        }

        if (poiInfos != null && !"".equals(poiInfos)) {
            PoiListAdapter poiAdapter = new PoiListAdapter(MapActivity.this, poiInfos);
            lv_near_address.setAdapter(poiAdapter);
            lv_near_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = poiInfos.get(position).name.toString();
                    Toast.makeText(getApplicationContext(),name+"---->获取数据返回到添加地址页面"+poiInfos.get(position).location.latitude+"==="+poiInfos.get(position).location.longitude, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("address", name);
                    intent.putExtra("latitude", poiInfos.get(position).location.latitude);
                    intent.putExtra("longitude", poiInfos.get(position).location.longitude);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }
    }
    //-----------------------地图地位回调方法----------------------//
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        // 如果bdLocation为空或mapView销毁后不再处理新数据接收的位置
        if (bdLocation == null || mBaiduMap == null) {
            return;
        }

        // 定位数据
        MyLocationData data = new MyLocationData.Builder()
                // 定位精度bdLocation.getRadius()
                .accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(bdLocation.getDirection())
                // 经度
                .latitude(bdLocation.getLatitude())
                // 纬度
                .longitude(bdLocation.getLongitude())
                // 构建
                .build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(data);

        // 根据定位的地点，以动画方式更新地图状态
        LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        //判断定位点是否在可配送范围内---如果需要设置配送的范围则加上此段代码
//        if (!SpatialRelationUtil.isPolygonContainsPoint(mPoints, ll)){
//            Toast.makeText(getApplicationContext(),"该地址不在配送范围，请在可选区域内选择", Toast.LENGTH_SHORT).show();
//
//            //ll = SpatialRelationUtil.getNearestPointFromLine(mPoints, ll);//获取离配送范围最近的点坐标
//        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);
        mBaiduMap.animateMapStatus(msu);

        locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        // 获取城市，待会用于POISearch
        city = bdLocation.getCity();
        // 发起反地理编码请求(经纬度->地址信息)
        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
        // 设置反地理编码位置坐标
        reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
        geoCoder.reverseGeoCode(reverseGeoCodeOption);

        //只需要打开该页面时定位一次即可，定位成功后关闭定位功能
        mLocClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
    }
    //-----------------------Poi搜索回调方法----------------------//
    @Override
    public void onGetPoiResult(PoiResult result) {
        Log.i(TAG, "poiListener-----onGetPoiResult");
        //获取POI检索结果
        if (result == null || result.getAllPoi()==null) {
            Toast.makeText(getApplicationContext(),"暂时没有数据", Toast.LENGTH_SHORT).show();
            return;
        }

        for (PoiInfo info : result.getAllPoi()) {
            Log.i(TAG, "当前的搜索信息:" + info.name + " " + info.address );
        }

        final List<PoiInfo> poiInfos = result.getAllPoi();
        SearchListAdapter searchListAdapter = new SearchListAdapter(MapActivity.this, poiInfos);
        lv_search.setAdapter(searchListAdapter);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatLng latLng = poiInfos.get(position).location;
                String name = poiInfos.get(position).name;
                Intent intent = new Intent();
                intent.putExtra("address", name);
                intent.putExtra("latitude", latLng.latitude);
                intent.putExtra("longitude",latLng.longitude);
                setResult(RESULT_OK,intent);
                finish();

//                //判断定位点是否在可配送范围内---如果需要设置配送的范围则加上此段代码
//                if (!SpatialRelationUtil.isPolygonContainsPoint(mPoints, latLng)){
//                    Toast.makeText(getApplicationContext(),"该地址不在配送范围，请在黄色区域内选择", Toast.LENGTH_SHORT).show();
//                    ll_map.setVisibility(View.VISIBLE);
//                    ll_search.setVisibility(View.GONE);
//                    acStateIsMap=true;
//                    LatLng pt1 = SpatialRelationUtil.getNearestPointFromLine(mPoints, latLng);
//                    geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(pt1));
//                }
                Toast.makeText(getApplicationContext(),name+"---->获取数据返回到添加地址页面", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {}

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            if (!acStateIsMap){
                ll_map.setVisibility(View.VISIBLE);
                ll_search.setVisibility(View.GONE);
                acStateIsMap=true;
            }else {
                finish();
            }

        }
        return false;

    }
}
