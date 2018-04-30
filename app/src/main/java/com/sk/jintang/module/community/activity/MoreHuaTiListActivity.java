package com.sk.jintang.module.community.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.Constant;
import com.sk.jintang.module.community.network.ApiRequest;
import com.sk.jintang.module.community.network.response.MoreHuaTiObj;
import com.sk.jintang.module.my.activity.LoginActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/4.
 */

public class MoreHuaTiListActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_hua_ti)
    RecyclerView rv_hua_ti;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("话题");
        setAppRightImg(R.drawable.shequ5_small);
        return R.layout.act_hua_ti;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<MoreHuaTiObj>(mContext,R.layout.item_hua_ti,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, MoreHuaTiObj bean) {
                holder.setText(R.id.tv_more_huati_title,bean.getTopic_name())
                .setText(R.id.tv_more_huati_date,bean.getAdd_time());
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if (TextUtils.isEmpty(getUserId())) {
                            STActivity(LoginActivity.class);
                        }else{
                            Intent intent=new Intent();
                            intent.putExtra(Constant.IParam.topicId,bean.getTopic_id()+"");
                            intent.putExtra(Constant.IParam.topicTitle,bean.getTopic_name());
                            STActivity(intent,HuaTiDetailActivity.class);
                        }

                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_hua_ti.setLayoutManager(new LinearLayoutManager(mContext));
        rv_hua_ti.addItemDecoration(getItemDivider());
        rv_hua_ti.setAdapter(adapter);
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
    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getMoreHuaTi(map, new MyCallBack<List<MoreHuaTiObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<MoreHuaTiObj> list) {
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

    @OnClick({R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivityForResult(FaHuaTiActivity.class,100);
            break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    showLoading();
                    getData(1,false);
                break;
            }
        }
    }
}
