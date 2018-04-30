package com.sk.jintang.module.community.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by administartor on 2017/9/4.
 */

public class ZhaoPinListActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_she_qu_list)
    RecyclerView rv_she_qu_list;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("招聘");
        return R.layout.act_she_qu_list;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_she_qu_list,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, Object bean) {

            }
        };

        adapter.setTestListSize(8);
        adapter.setOnLoadMoreListener(this);
        rv_she_qu_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_she_qu_list.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void loadMore() {

    }
}
