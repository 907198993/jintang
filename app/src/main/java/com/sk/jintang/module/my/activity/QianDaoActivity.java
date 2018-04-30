package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.bean.QianDaoBean;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.QianDaoObj;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/5.
 */

public class QianDaoActivity extends BaseActivity {
    @BindView(R.id.rv_qiandao)
    RecyclerView rv_qiandao;
    @BindView(R.id.tv_qiandao_date)
    TextView tv_qiandao_date;
    @BindView(R.id.tv_qiandao_ysd)
    TextView tv_qiandao_ysd;
    @BindView(R.id.tv_qiandao_commit)
    TextView tv_qiandao_commit;

    private List<Integer> qianDaoList;
    BaseRecyclerAdapter adapter;
    List<QianDaoBean> list;
    private boolean isQianDao;//今天是否签到
    private String guiZe="";
    @Override
    protected int getContentView() {
        setAppTitle("签到");
        setAppRightTitle("规则");
        return R.layout.act_qian_dao;
    }

    @Override
    protected void initView() {
        Calendar calendar = Calendar.getInstance();
        tv_qiandao_date.setText(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月");

        int screenWidth = PhoneUtils.getScreenWidth(mContext);
        adapter=new BaseRecyclerAdapter<QianDaoBean>(mContext,R.layout.item_qian_dao) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, QianDaoBean bean) {
                FrameLayout fl_qiandao_date = (FrameLayout) holder.getView(R.id.fl_qiandao_date);
                ImageView iv_qiandao_img = (ImageView) holder.getView(R.id.iv_qiandao_img);
                TextView tv_qiandao_date = holder.getTextView(R.id.tv_qiandao_date);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.width=screenWidth/7;
                layoutParams.height=screenWidth/7*4/5;
                fl_qiandao_date.setLayoutParams(layoutParams);
                tv_qiandao_date.setText(bean.date+"");
                if(bean.type==1){
                    tv_qiandao_date.setTextColor(ContextCompat.getColor(mContext,R.color.gray_33));
                    if(notEmpty(qianDaoList)){
                        boolean contains = qianDaoList.contains(bean.date);
                        if(contains){
                            iv_qiandao_img.setVisibility(View.VISIBLE);
                        }else{
                            iv_qiandao_img.setVisibility(View.GONE);
                        }
                    }else{
                        iv_qiandao_img.setVisibility(View.GONE);
                    }
                }else{
                    tv_qiandao_date.setTextColor(ContextCompat.getColor(mContext,R.color.gray_99));
                    iv_qiandao_img.setVisibility(View.GONE);
                }
            }
        };
        rv_qiandao.setLayoutManager(new GridLayoutManager(mContext,7));
        rv_qiandao.setAdapter(adapter);
    }

    private void setDate() {
        list=new ArrayList<>();
        Calendar current = Calendar.getInstance();
        current.set(Calendar.DATE, 1);
        int firstWeek = current.get(Calendar.DAY_OF_WEEK) - 1;
        current.roll(Calendar.DATE, -1);
        int lastWeek = current.get(Calendar.DAY_OF_WEEK) - 1;


        Calendar before = Calendar.getInstance();
        before.set(Calendar.MONTH,Calendar.getInstance().get(Calendar.MONTH)-1);
        int beforeMonthLastDay = getMonthLastDay(Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.MONTH) - 1);
        int addBeforeDay=beforeMonthLastDay-firstWeek+1;
        for (int i = 0; i < firstWeek; i++) {
            QianDaoBean bean=new QianDaoBean();
            bean.type=0;
            bean.date=addBeforeDay;
            addBeforeDay++;
            beforeMonthLastDay--;
            list.add(bean);
        }
        for (int i = 1; i <= getCurrentMonthLastDay(); i++) {
            QianDaoBean bean=new QianDaoBean();
            bean.type=1;
            bean.date=i;
            list.add(bean);
        }
        int j=1;
        for (int i = lastWeek; i < 6; i++) {
            QianDaoBean bean=new QianDaoBean();
            bean.type=0;
            bean.date=j;
            j++;
            list.add(bean);
        }
        adapter.setList(list,true);
    }

    @Override
    protected void initData() {
        showProgress();

        getQianDaoData();
    }

    private void getQianDaoData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getQianDaoList(map, new MyCallBack<QianDaoObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(QianDaoObj obj) {
                guiZe=obj.getKeeping_bean_rule();
                tv_qiandao_ysd.setText("您已获得: "+obj.getKeeping_bean()+"养生豆");
                qianDaoList = obj.getList();
                isQianDao = obj.getList().contains(Calendar.getInstance().get(Calendar.DATE));
                if(isQianDao){
                    tv_qiandao_commit.setText("今日已签到");
                    tv_qiandao_commit.setEnabled(false);
                }else{
                    tv_qiandao_commit.setText("点击签到");
                    tv_qiandao_commit.setEnabled(true);
                }
                setDate();
            }
        });

    }

    public int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    public int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    @OnClick({R.id.tv_qiandao_commit,R.id.app_right_tv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_qiandao_commit:
                qianDao();
                break;
            case R.id.app_right_tv:
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.guiZeType,Constant.IParam.guiZeType_0);
                intent.putExtra(Constant.IParam.guiZeContent,guiZe);
                STActivity(intent,GuiZeActivity.class);
                break;
        }
    }

    private void qianDao() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.qianDao(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                getQianDaoData();
            }
        });

    }
}
