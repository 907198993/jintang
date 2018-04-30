package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.YangShengDouObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/6.
 */

public class YangShengDouActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_yang_sheng_dou)
    RecyclerView rv_yang_sheng_dou;
    @BindView(R.id.tv_ysd_num)
    TextView tv_ysd_num;
    @BindView(R.id.nsv)
    NestedScrollView nsv;

    private String beanRule="";
    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的养生豆");
        setAppRightTitle("规则");
        return R.layout.act_yang_sheng_dou;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<YangShengDouObj.KeepingBeanDetailListBean>(mContext,R.layout.item_yang_sheng_dou,pageSize,nsv) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, YangShengDouObj.KeepingBeanDetailListBean  bean) {
                holder.setText(R.id.tv_yangshengdou_date,bean.getAdd_time())
                .setText(R.id.tv_yangshengdou_type,bean.getRemark());
                TextView textView = holder.getTextView(R.id.tv_yangshengdou_amount);
                int value = bean.getValue();
                if(value>=0){
                    textView.setTextColor(getResources().getColor(R.color.green));
                    textView.setText("+"+value);
                }else{
                    textView.setTextColor(getResources().getColor(R.color.red));
                    textView.setText(""+value);
                }
            }
        };
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
        adapter.setOnLoadMoreListener(this);

        rv_yang_sheng_dou.setNestedScrollingEnabled(false);
        rv_yang_sheng_dou.setLayoutManager(new LinearLayoutManager(mContext));
        rv_yang_sheng_dou.addItemDecoration(getItemDivider());
        rv_yang_sheng_dou.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        showLoading();
        getData(1,false);
    }

    private void getData(int page,boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("pagesize",pageSize+"");
        map.put("page",page+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getYangShengDou(map, new MyCallBack<YangShengDouObj>(mContext,pcfl,pl_load) {


            @Override
            public void onSuccess(YangShengDouObj obj) {
                beanRule = obj.getKeeping_bean_rule();
                tv_ysd_num.setText(obj.getKeeping_bean()+"");
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getKeeping_bean_detail_list(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getKeeping_bean_detail_list(),true);
                }
            }
        });

    }

    @OnClick({R.id.app_right_tv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_tv:
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.guiZeType,Constant.IParam.guiZeType_1);
                intent.putExtra(Constant.IParam.guiZeContent,beanRule);
                STActivity(intent,GuiZeActivity.class);
            break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
