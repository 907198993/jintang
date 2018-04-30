package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.androidtools.DateUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.GoodsQuestionObj;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2018/2/6.
 */

public class GoodsQuestionActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_goods_question)
    RecyclerView rv_goods_question;
    LoadMoreAdapter adapter;
    private String goodsId;

    @Override
    protected int getContentView() {
        setAppTitle("商品提问");
        setAppRightImg(R.drawable.add_bank2);
        return R.layout.act_goods_question;
    }

    @Override
    protected void initView() {
        goodsId = getIntent().getStringExtra(Constant.IParam.goodsId);
        adapter=new LoadMoreAdapter<GoodsQuestionObj>(mContext,R.layout.item_goods_question,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, GoodsQuestionObj bean) {
                holder.setText(R.id.tv_goods_question_content,bean.getQuestion_content())
                        .setText(R.id.tv_goods_question_time, DateUtils.dateToString(new Date(bean.getAdd_time()*1000)));
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.questionId,bean.getQuestion_id()+"");
                        intent.putExtra(Constant.IParam.questionContent,bean.getQuestion_content()+"");
                        STActivity(intent,QuestionDetailActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_goods_question.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_question.addItemDecoration(getItemDivider());
        rv_goods_question.setAdapter(adapter);


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

    private void getData(int page,boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("goods_id",goodsId);
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getQuestionList(map, new MyCallBack<List<GoodsQuestionObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<GoodsQuestionObj> list) {
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
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.goodsId,goodsId);
                STActivityForResult(intent,AddQuestionActivity.class,100);
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    getData(1,false);
                break;
            }
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
