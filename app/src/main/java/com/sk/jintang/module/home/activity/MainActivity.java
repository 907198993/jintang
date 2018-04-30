package com.sk.jintang.module.home.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.StatusBarUtils;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyRadioButton;

import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.broadcast.MyOperationBro;
import com.sk.jintang.module.category.fragment.CategoryFragment;
import com.sk.jintang.module.community.fragment.SheQuFragment;
import com.sk.jintang.module.home.event.MoreCategoryEvent;
import com.sk.jintang.module.home.event.SelectGoodsCategoryEvent;
import com.sk.jintang.module.home.fragment.HomeFragment;
import com.sk.jintang.module.home.network.ApiRequest;
import com.sk.jintang.module.home.network.response.AdverImgObj;
import com.sk.jintang.module.home.network.response.AppVersionObj;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.my.bean.AppInfo;
import com.sk.jintang.module.my.event.SelectSheQuEvent;
import com.sk.jintang.module.my.fragment.MyFragment;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.shoppingcart.fragment.ShoppingCartFragment;
import com.sk.jintang.service.MyAPPDownloadService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.Subscriber;

public class MainActivity extends BaseActivity {

    @BindView(R.id.status_bar)
    View status_bar;


    HomeFragment homeFragment;
    CategoryFragment categoryFragment;
//    GoodsClassFragment goodsClassFragment;
    SheQuFragment sheQuFragment;
    ShoppingCartFragment shoppingCartFragment;
    MyFragment myFragment;

    @BindView(R.id.layout_main_content)
    FrameLayout layout_main_content;
    @BindView(R.id.rb_home_shouye)
    MyRadioButton rb_home_shouye;
    @BindView(R.id.rb_home_orderclass)
    MyRadioButton rb_home_orderclass;
    @BindView(R.id.rb_home_shequ)
    MyRadioButton rb_home_shequ;
    @BindView(R.id.rb_home_shoppingcart)
    MyRadioButton rb_home_shoppingcart;
    @BindView(R.id.rb_home_my)
    MyRadioButton rb_home_my;


    private MyRadioButton selectButton;

    private LocalBroadcastManager localBroadcastManager;
    private MyOperationBro myOperationBro;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(MoreCategoryEvent.class, new MySubscriber<MoreCategoryEvent>() {
            @Override
            public void onMyNext(MoreCategoryEvent event) {
                Toast.makeText(MainActivity.this,"ConversationActivity",Toast.LENGTH_SHORT).show();
                selectGoods();
                RxBus.getInstance().postSticky(new SelectGoodsCategoryEvent(event.typeId,event.typeName));
                selectButton.setChecked(true);
//                goodsClassFragment.setTypeId(event.typeId,event.typeName);
            }
        });
        getRxBusEvent(SelectSheQuEvent.class, new MySubscriber() {
            @Override
            public void onMyNext(Object o) {
                selectSheQu();
                selectButton.setChecked(true);
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if(intent==null){
            return;
        }else if(Config.backHome.equals(intent.getAction())){
            selectHome();
            selectButton.setChecked(true);
        }else if(Config.useVoucher.equals(intent.getAction())){
            selectGoods();
            selectButton.setChecked(true);
        }else if(Constant.IParam.goShoppingCart.equals(intent.getAction())){
            selectShoppingCart();
            selectButton.setChecked(true);
        }
    }
    @Override
    protected void initView() {
        // 推送
        String registrationID = JPushInterface.getRegistrationID(mContext);
        android.util.Log.i("registrationID","registrationID====="+registrationID);
        if(!TextUtils.isEmpty(registrationID)){
            SPUtils.setPrefString(mContext,Config.jiguangRegistrationId,registrationID);
        }

        boolean isUpdatePwd = SPUtils.getPrefBoolean(mContext, Config.isUpdatePWD, false);
        if(isUpdatePwd){
            SPUtils.removeKey(mContext,Config.isUpdatePWD);
            SPUtils.removeKey(mContext,Config.user_id);
        }
        setBroadcast();
        int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = statusBarHeight;
        status_bar.setLayoutParams(layoutParams);
        status_bar.setBackgroundColor(getResources().getColor(R.color.green));

        selectButton = rb_home_shouye;
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homeFragment).commitAllowingStateLoss();

    }
    private void setBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        myOperationBro = new MyOperationBro(new MyOperationBro.LoginBroInter() {
            @Override
            public void loginSuccess() {
                selectMy();
                selectButton.setChecked(true);

//                registerHuanXin();
            }

            @Override
            public void exitLogin() {
                selectHome();
                selectButton.setChecked(true);
                myFragment=null;
            }
        });
        //注册广播
        localBroadcastManager.registerReceiver(myOperationBro, new IntentFilter(Config.Bro.operation));
    }

//    private void registerHuanXin() {
//        RXStart(new IOCallBack<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                if(TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.phone,null))){
//                    return;
//                }
//                ChatClient.getInstance().createAccount(SPUtils.getPrefString(mContext,Config.phone,null), "123456", new Callback(){
//                    @Override
//                    public void onSuccess() {
//                    }
//                    @Override
//                    public void onError(int i, String s) {
//                        Log.i("===",i+"=onError=="+s);
//                    }
//                    @Override
//                    public void onProgress(int i, String s) {
//                        Log.i("===",i+"=onProgress=="+s);
//                    }
//                });
//            }
//
//            @Override
//            public void onMyNext(String s) {
//
//            }
//        });
//    }
    @Override
    protected void initData() {
        updateApp();
        //获取微信，支付宝支付的通知地址,支付方式1支付宝，2微信
        getZhiFuNotifyUrl("1");
        getZhiFuNotifyUrl("2");
        getAdverImg();
    }
    private void updateApp() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getAppVersion(map, new MyCallBack<AppVersionObj>(mContext) {
            @Override
            public void onSuccess(AppVersionObj obj) {
                if(obj.getAndroid_version()>getAppVersionCode()){
                    SPUtils.setPrefString(mContext,Config.appDownloadUrl,obj.getAndroid_vs_url());
                    SPUtils.setPrefBoolean(mContext,Config.appHasNewVersion,true);
                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                    mDialog.setMessage("检测到app有新版本是否更新?");
                    mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            AppInfo info = new AppInfo();
                            info.setUrl(obj.getAndroid_vs_url());
                            info.setHouZhui(".apk");
                            info.setFileName("yangyu");
                            info.setId(obj.getAndroid_version() + "");
                            downloadApp(info);
                        }
                    });
                    mDialog.create().show();
                }else{
                    SPUtils.setPrefBoolean(mContext,Config.appHasNewVersion,false);
                }
            }
        });
    }

    private void downloadApp(AppInfo info) {
        MyAPPDownloadService.intentDownload(mContext, info);
    }
    public int getAppVersionCode() {
        Context context=mContext;
        int versioncode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            String versionName = pi.versionName;
            versioncode = pi.versionCode;
            return versioncode;
        } catch (Exception e) {
            android.util.Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }
    private void getAdverImg() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getAdverImg(map, new MyCallBack<AdverImgObj>(mContext) {
            @Override
            public void onSuccess(AdverImgObj obj) {
                if(TextUtils.isEmpty(obj.getImage_url())){
                    SPUtils.removeKey(mContext,Config.imgPath);
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            saveImg(obj.getImage_url());
                        }
                    }).start();
                }
            }
            @Override
            public void onError(Throwable e) {
            }
        });
    }
    public void saveImg(String url){
        FutureTarget<File> future = Glide.with(mContext)
                .load(url)
                .downloadOnly(PhoneUtils.getScreenWidth(this),PhoneUtils.getScreenHeight(this));
        try {
            File cacheFile = future.get();
            String path = cacheFile.getAbsolutePath();
            SPUtils.setPrefString(mContext,Config.imgPath,path);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void getZhiFuNotifyUrl(String type) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("payment_type",type);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getPayNotifyUrl(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                if(obj.getPayment_type()==1){
                    SPUtils.setPrefString(mContext,Config.payType_ZFB,obj.getPayment_url());
                }else{
                    SPUtils.setPrefString(mContext,Config.payType_WX,obj.getPayment_url());
                }
            }
        });
    }
    @OnClick({R.id.rb_home_shouye, R.id.rb_home_orderclass, R.id.rb_home_shequ, R.id.rb_home_shoppingcart,R.id.rb_home_my})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home_shouye:
                selectHome();
                break;
            case R.id.rb_home_orderclass://去分类
                selectGoods();
                break;
            case R.id.rb_home_shequ:
                selectSheQu();
                break;
            case R.id.rb_home_shoppingcart:
                if (TextUtils.isEmpty(getUserId())) {
                    selectButton.setChecked(true);
                    STActivity(LoginActivity.class);
                    return;
                }
                selectShoppingCart();
                break;
            case R.id.rb_home_my:
                if (TextUtils.isEmpty(getUserId())) {
                    selectButton.setChecked(true);
                    STActivity(LoginActivity.class);
                    return;
                }
                selectMy();
                break;
        }
    }

    private void selectSheQu() {
        selectButton = rb_home_shequ;
        if (sheQuFragment == null) {
            sheQuFragment = new SheQuFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, sheQuFragment).commitAllowingStateLoss();
        } else {
            showFragment(sheQuFragment);
        }
        hideFragment(homeFragment);
        hideFragment(categoryFragment);
        hideFragment(shoppingCartFragment);
        hideFragment(myFragment);
    }

    private void selectShoppingCart() {
        selectButton = rb_home_shoppingcart;
        if (shoppingCartFragment == null) {
            shoppingCartFragment = new ShoppingCartFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, shoppingCartFragment).commitAllowingStateLoss();
        } else {
            showFragment(shoppingCartFragment);
        }
        hideFragment(homeFragment);
        hideFragment(categoryFragment);
        hideFragment(sheQuFragment);
        hideFragment(myFragment);
    }

    private void selectGoods() {
        selectButton = rb_home_orderclass;
        if (categoryFragment == null) {
            categoryFragment = new CategoryFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, categoryFragment).commitAllowingStateLoss();
        } else {
            showFragment(categoryFragment);
        }
        hideFragment(homeFragment);
        hideFragment(sheQuFragment);
        hideFragment(shoppingCartFragment);
        hideFragment(myFragment);
    }

    private void selectHome() {
        selectButton = rb_home_shouye;
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homeFragment).commitAllowingStateLoss();
        } else {
            showFragment(homeFragment);
        }
        hideFragment(categoryFragment);
        hideFragment(sheQuFragment);
        hideFragment(shoppingCartFragment);
        hideFragment(myFragment);
    }

    private void selectMy() {
        selectButton = rb_home_my;
        if (myFragment == null) {
            myFragment = new MyFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, myFragment).commitAllowingStateLoss();
        } else {
            showFragment(myFragment);
        }
        hideFragment(homeFragment);
        hideFragment(categoryFragment);
        hideFragment(sheQuFragment);
        hideFragment(shoppingCartFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //x 取消注册广播,防止内存泄漏
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(myOperationBro);
        }
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            showToastS("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    private boolean isHorMove=false;
    private float moveX;
    private float moveY;
    public int getScaledTouchSlop(){
        ViewConfiguration conf = ViewConfiguration.get(mContext);
        return conf.getScaledTouchSlop() * 2;
    }

    //判断刷新否
    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                moveX=motionEvent.getX();
                moveY=motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isHorMove){
                    if(isHorMove==false&&(Math.abs(moveY)-Math.abs(motionEvent.getY())>75||Math.abs(Math.abs(moveX)-Math.abs(motionEvent.getX()))<95)){
                        isHorMove=false;
                    }else if(Math.abs(moveX-motionEvent.getX())>=65&&moveY-motionEvent.getY()<=55){
                        isHorMove=true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isHorMove=false;
                break;
        }
        homeFragment.setRefresh(isHorMove);
        return super.dispatchTouchEvent(motionEvent);
    }
}
