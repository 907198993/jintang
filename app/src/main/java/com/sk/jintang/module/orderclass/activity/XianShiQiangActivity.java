package com.sk.jintang.module.orderclass.activity;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.adapter.XianShiFragmentAdapter;
import com.sk.jintang.module.orderclass.fragment.XianShiNewFragment;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.XianShiTimeObj;
import com.sk.jintang.tools.DateUtils;
import com.sk.jintang.view.MyViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/2.
 */

public class XianShiQiangActivity extends BaseActivity {

    @BindView(R.id.tl_xianshi)
    TabLayout tl_xianshi;
    @BindView(R.id.vp_xianshi)
    MyViewPager vp_xianshi;
    @BindView(R.id.ll_xianshi_content)
    LinearLayout ll_xianshi_content;
    @BindView(R.id.tv_xianshi_nodata)
    TextView tv_xianshi_nodata;
    XianShiFragmentAdapter fragmentAdapter;
    XianShiNewFragment xianShiFragment;
    private Timer timer;
    private TimerTask task;
    private boolean initDataSuccess;
    private List<XianShiNewFragment> fragmentList=new ArrayList<>();
    LinearLayout selectView;

    @Override
    protected int getContentView() {
        setAppTitle("限时抢购");
        setAppRightImg(R.drawable.share_title);
        return R.layout.act_xian_shi_qiang_gou;
    }

    @Override
    protected void initView() {
        vp_xianshi.setScroll(false);
        fragmentAdapter=new XianShiFragmentAdapter(getSupportFragmentManager());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(task!=null){
            task.cancel();
        }
        if(timer!=null){
            timer.cancel();
        }
        task=null;
        timer=null;
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(initDataSuccess){
            if(xianShiFragment != null){
                int selectedTabPosition = tl_xianshi.getSelectedTabPosition();
                fragmentList.get(selectedTabPosition).getData(1,false);
            }
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.xianShiTime(map, new MyCallBack<List<XianShiTimeObj>>(mContext, pcfl, pl_load) {

            @Override
            public void onSuccess(List<XianShiTimeObj> list) {
                if(isEmpty(list)){
                    ll_xianshi_content.setVisibility(View.GONE);
                    tv_xianshi_nodata.setVisibility(View.VISIBLE);
                    return;
                }
                ll_xianshi_content.setVisibility(View.VISIBLE);
                tv_xianshi_nodata.setVisibility(View.GONE);
                for (int i = 0; i < list.size(); i++) {
                    XianShiTimeObj timeObj = list.get(i);
                    fragmentList.add(XianShiNewFragment.newInstance(timeObj.getPeriod_time_id(),timeObj.getBegin_time(),timeObj.getEnd_time()));
                    initDataSuccess=true;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date(timeObj.getBegin_time()));

                    View tabView = getTabView();
                    TextView tv_xianshi_time = tabView.findViewById(R.id.tv_xianshi_time);
                    TextView tv_xianshi_status =tabView.findViewById(R.id.tv_xianshi_status);
                    String hour=calendar.get(Calendar.HOUR_OF_DAY)<10?"0"+calendar.get(Calendar.HOUR_OF_DAY):calendar.get(Calendar.HOUR_OF_DAY)+"";
                    String mill=calendar.get(Calendar.MILLISECOND)<10?"0"+calendar.get(Calendar.MILLISECOND):calendar.get(Calendar.MILLISECOND)+"";
                    tv_xianshi_time.setText(hour+":"+mill);

                    if(new Date().getTime()>timeObj.getEnd_time()){
                        tv_xianshi_status.setText("已结束");
                    }else if(new Date().getTime()<timeObj.getBegin_time()){
                        tv_xianshi_status.setText("即将开抢");
                        daoJiShi(i,tv_xianshi_status,timeObj.getBegin_time(),timeObj.getEnd_time());
                    }else{
                        daoJiShi(i,tv_xianshi_status,timeObj.getBegin_time(),timeObj.getEnd_time());
                    }
                    if(i==0){
                        View ll_tab = tabView.findViewById(R.id.ll_tab);
                        ll_tab.setBackgroundResource(R.color.red);
                    }
                    TabLayout.Tab tab=tl_xianshi.newTab();
                    tab.setCustomView(tabView);
                    tl_xianshi.addTab(tab);
                }
                fragmentAdapter.setList(fragmentList);
                vp_xianshi.setAdapter(fragmentAdapter);
                vp_xianshi.setOffscreenPageLimit(fragmentList.size()-1);
                tl_xianshi.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        View customView = tab.getCustomView();
                        LinearLayout ll_tab = customView.findViewById(R.id.ll_tab);
                        ll_tab.setBackgroundResource(R.color.red);
                        vp_xianshi.setCurrentItem(tab.getPosition());
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        View customView = tab.getCustomView();
                        View ll_tab = customView.findViewById(R.id.ll_tab);
                        ll_tab.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray_99));
                    }
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
//                tl_xianshi.setupWithViewPager(vp_xianshi);

            }
        });
    }
    public View getTabView(){
        return getLayoutInflater().inflate(R.layout.item_xianshi,null);
    }
    private void daoJiShi(int position,TextView textView,long beginTime,long endTime) {
        timer = new Timer(true);
        task = new TimerTask() {
            public void run() {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(new Date().getTime()>=beginTime){
                            if (endTime < new Date().getTime()) {//是否结束
                                textView.setText("已结束");
                            } else {
                                textView.setText(DateUtils.getXCTime(new Date().getTime(),endTime));
                            }
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000);
    }


    @OnClick({R.id.app_right_iv})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.app_right_iv:
                showFenXiang("0");
                break;
        }
    }
}
