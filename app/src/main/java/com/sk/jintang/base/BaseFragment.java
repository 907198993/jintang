package com.sk.jintang.base;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.github.androidtools.ClickUtils;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.fragment.IBaseFragment;
import com.github.baseclass.rx.RxBus;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.chat.ChatActivity;
import com.sk.jintang.chat.ConversationActivity;
import com.sk.jintang.module.my.activity.MyAllCollectActivity;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.FenXiangObj;
import com.sk.jintang.view.ProgressLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Administrator on 2017/7/13.
 */

public abstract class BaseFragment extends IBaseFragment implements View.OnClickListener,ProgressLayout.OnAgainInter{
    protected int pageNum=2;
    protected int pageSize=20;

    private boolean isFirstLoadData=true;
    private boolean isPrepared;
    protected PtrClassicFrameLayout pcfl;
    /************************************************/
    protected abstract int getContentView();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void onViewClick(View v);
    protected void initRxBus(){};
    protected boolean isPause;
    protected void myReStart() {
    }
    protected Unbinder mUnBind;

    protected ProgressLayout pl_load;//进度框
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        mUnBind = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(null!=view.findViewById(R.id.pcfl_refresh)){
            pcfl = (PtrClassicFrameLayout) view.findViewById(R.id.pcfl_refresh);
            pcfl.setLastUpdateTimeRelateObject(this);
            pcfl.setOffsetXFlag(3);
            pcfl.setHorizontalMoveFlag(3);
            pcfl.setScaledTouchSlopFlag(1);
            pcfl.disableWhenHorizontalMove(true);
        }
        if(null!=view.findViewById(R.id.pl_load)){
            pl_load = (ProgressLayout) view.findViewById(R.id.pl_load);
            pl_load.setInter(this);
        }
        initView();
        initRxBus();
        isPrepared=true;
        setUserVisibleHint(true);
    }
    public void showProgress(){
        if (pl_load != null) {
            pl_load.showProgress();
        }
    }
    public void showContent(){
        if (pl_load != null) {
            pl_load.showContent();
        }
    }
    public void showErrorText(){
        if (pl_load != null) {
            pl_load.showErrorText();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        isPause =true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            isPause =true;
        }else{
            isPause =false;
            myReStart();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(isPause){
            isPause =false;
            myReStart();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFirstLoadData&&isPrepared&&getUserVisibleHint()&&isVisibleToUser){
            initData();
            isFirstLoadData=false;
        }
    }
    protected String getSStr(View view){
        if(view instanceof TextView){
            return ((TextView)view).getText().toString();
        } else if (view instanceof EditText) {
            return ((EditText)view).getText().toString();
        }else{
            return null;
        }
    }
    @Override
    public void onClick(View v) {
        if(!ClickUtils.isFastClick(v)){
            onViewClick(v);
        }
    }
    public void onDestroy() {
        super.onDestroy();
        mUnBind.unbind();
        RxBus.getInstance().removeAllStickyEvents();
    }

    @Override
    public void again() {
        initData();
    }
    public int getUserType(){
        return SPUtils.getPrefInt(mContext, Config.userType,-1);
    }
    protected String getUserId(){
        return SPUtils.getPrefString(mContext,Config.user_id,null);
    }
    protected String getUsersId(){
        return SPUtils.getPrefString(mContext,Config.user_id,"0");
    }
    protected String getSign(){
        return getSign("user_id",getUserId());
    }
    protected String getSign(String key,String value){
        return GetSign.getSign(key,value);
    }
    protected boolean isEmpty(List list){
        return list == null || list.size() == 0;
    }
    protected boolean notEmpty(List list){
        return !(list == null || list.size() == 0);
    }
    protected String getRnd(){
        Random random = new Random();
        int rnd = random.nextInt(9000) + 1000;
        return rnd+"";
    }
    protected BaseDividerListItem getItemDivider(){
        return new BaseDividerListItem(mContext,BaseDividerListItem.VERTICAL_LIST,2,R.color.background_f2);
    }
    protected BaseDividerListItem getItemDivider(int height){
        return new BaseDividerListItem(mContext,BaseDividerListItem.VERTICAL_LIST,height,R.color.background_f2);
    }
    protected BaseDividerListItem getItemDivider(int height,int color){
        return new BaseDividerListItem(mContext,BaseDividerListItem.VERTICAL_LIST,height,color);
    }


    protected void initWebViewForContent(WebView webview, String content) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        //自适应屏幕  
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webview.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        //设置Web视图
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                initWebTopView();
            }
        });

        webview.loadDataWithBaseURL(null, getNewContent(content), "text/html", "utf-8",null);
//        webview.loadUrl(url);
        // 设置WevView要显示的网页
//        webview.loadDataWithBaseURL(null, content, "text/html", "utf-8",null);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webview.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
    }
    protected void initWebViewForUrl(WebView webview,String url) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        //自适应屏幕  
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webview.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        //设置Web视图
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                initWebTopView();
            }
        });

//        webview.loadDataWithBaseURL(null, getNewContent(url), "text/html", "utf-8",null);
        webview.loadUrl(url);
        // 设置WevView要显示的网页
//        webview.loadDataWithBaseURL(null, content, "text/html", "utf-8",null);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webview.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
    }


    protected static String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }


    public void goHX(int i,String HxUserId){ //商户环信id
        if(EMClient.getInstance().isLoggedInBefore()){
            //已经登录，可以直接进入会话界面
            OpenHuanXinChat(HxUserId);
        }else{
            showLoading();
            hxName=SPUtils.getPrefString(mContext,Config.hxuser,null);
            hxPwd=SPUtils.getPrefString(mContext,Config.hxpwd,null);
            loginHXSuccess(hxName,hxPwd,i,HxUserId);

        }
    }

    public void goHX(){
        if(EMClient.getInstance().isLoggedInBefore()){
            //已经登录，可以直接进入会话界面
            OpenHuanXinChatList();
        }else{
            showLoading();
            hxName=SPUtils.getPrefString(mContext,Config.hxuser,null);
            hxPwd=SPUtils.getPrefString(mContext,Config.hxpwd,null);
            loginHXSuccess(hxName,hxPwd,0,null);

        }
    }

    String hxName;
    String hxPwd;
    private void loginHXSuccess(String hxName,String pwd,int i,String HxUserId) {

        EMClient.getInstance().login(hxName, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                dismissLoading();
                if(i==0){
                    OpenHuanXinChatList();
                }else{
                    OpenHuanXinChat(HxUserId);
                }


            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String error) {
                dismissLoading();
            }
        });
    }
    private void OpenHuanXinChatList() {
        STActivity(ConversationActivity.class);
    }
    private void OpenHuanXinChat(String HxUserId) {
        startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, HxUserId));
    }

    /*****************************************************************第三方分享********************************************************************************/
    protected void fenXiang(SHARE_MEDIA shareMedia,String  fenXiangId) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("goods_id",fenXiangId);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.fenXiang(map, new MyCallBack<FenXiangObj>(mContext,true) {
            @Override
            public void onSuccess(FenXiangObj obj) {
                UMWeb web = new UMWeb(obj.getShare_link());
                UMImage image=new UMImage(mContext,R.drawable.app_default);
                web.setTitle(obj.getTitle());//标题
                web.setThumb(image);  //缩略图
                web.setDescription(obj.getContent());//描述
                new ShareAction(mContext)
                        .setPlatform(shareMedia)//传入平台
                        .withMedia(web)
//                      .withText(getSStr(tv_fenxiao_detail_code))//分享内容
                        .setCallback(getListener())
                        .share();
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissLoading();
            }
        });
    }
    protected UMShareListener getListener() {
        return new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                dismissLoading();
                Log.i("============","============onStart");
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Log.i("============","============onResult");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                showMsg(throwable.getMessage());
                Log.i("============","============onError");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Log.i("============","============onCancel");
            }
        };
    }
    BottomSheetDialog fenXiangDialog;
    public void showFenXiang(String fenXiangId){
        if (fenXiangDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_fen_xiang,null);
            /*iv_yaoqing_wx
iv_yaoqing_friend
iv_yaoqing_qq
iv_yaoqing_qzone
iv_yaoqing_sina*/
            sexView.findViewById(R.id.iv_yaoqing_wx).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                        showMsg("请安装微信之后再试");
                        return;
                    }
                    fenXiang(SHARE_MEDIA.WEIXIN,fenXiangId);
                    fenXiangDialog.dismiss();

                }
            });
            sexView.findViewById(R.id.iv_yaoqing_friend).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                        showMsg("请安装微信之后再试");
                        return;
                    }
                    fenXiang(SHARE_MEDIA.WEIXIN_CIRCLE,fenXiangId);
                    fenXiangDialog.dismiss();

                }
            });
            sexView.findViewById(R.id.iv_yaoqing_qq).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                        showMsg("请安装QQ之后再试");
                        return;
                    }
                    fenXiang(SHARE_MEDIA.QQ,fenXiangId);
                    fenXiangDialog.dismiss();
                }
            });
            sexView.findViewById(R.id.iv_yaoqing_qzone).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                        showMsg("请安装QQ之后再试");
                        return;
                    }
                    fenXiang(SHARE_MEDIA.QZONE,fenXiangId);
                    fenXiangDialog.dismiss();
                }
            });
           /* sexView.findViewById(R.id.iv_yaoqing_sina).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    showMsg("正在开发中");
                    fenXiangDialog.dismiss();
                }
            });*/
            sexView.findViewById(R.id.tv_fenxiang_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiangDialog.dismiss();
                }
            });
            fenXiangDialog=new BottomSheetDialog(mContext);
            fenXiangDialog.setCanceledOnTouchOutside(true);
            fenXiangDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            fenXiangDialog.setContentView(sexView);
        }
        fenXiangDialog.show();
    }
    public boolean keJian(View view){
        int screenWidth= PhoneUtils.getScreenWidth(mContext);
        int screenHeight=PhoneUtils.getScreenHeight(mContext);

        Rect rect=new Rect(0,0,screenWidth,screenHeight );
        int[] location = new int[2];
        view.getLocationInWindow(location);
        System.out.println(Arrays.toString(location));
        // Rect ivRect=new Rect(imageView.getLeft(),imageView.getTop(),imageView.getRight(),imageView.getBottom());
        if (view.getLocalVisibleRect(rect)) {/*rect.contains(ivRect)*/
            return true;
        } else {
            return false;
        }
    }
}
