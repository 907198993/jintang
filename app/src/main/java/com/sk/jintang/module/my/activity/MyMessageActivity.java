package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.MessageListObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/4.
 */

public class MyMessageActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_my_message)
    RecyclerView rv_my_message;

    LoadMoreAdapter adapter;
    @Override
    public void again() {
        initData();
    }

    @Override
    protected int getContentView() {
        setAppTitle("我的消息");
        return R.layout.act_my_message;
    }

    @Override
    protected void myReStart() {
        super.myReStart();
        getData(1,false);
    }
    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<MessageListObj>(mContext,R.layout.item_my_msg,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, MessageListObj bean) {
                holder.setText(R.id.tv_tgy_msg_title,bean.getTitle())
                        .setText(R.id.tv_tgy_msg_content,bean.getZhaiyao())
                        .setText(R.id.tv_tgy_msg_date,bean.getAdd_time());
                View tv_my_message_point = holder.getView(R.id.tv_my_message_point);
                if(bean.getNews_is_check()==1){
                    tv_my_message_point.setVisibility(View.VISIBLE);
                }else{
                    tv_my_message_point.setVisibility(View.INVISIBLE);
                }
            }
        };
        adapter.setOnLoadMoreListener(this);
        adapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                MessageListObj obj = (MessageListObj) adapter.getList().get(i);
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.msgId,obj.getId()+"");
                STActivity(intent,MessageDetailActivity.class);
            }
        });

        rv_my_message.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_message.setAdapter(adapter);
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
        map.put("user_id",getUserId());
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getMsgList(map, new MyCallBack<List<MessageListObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<MessageListObj> list) {
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

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
