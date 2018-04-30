package com.sk.jintang.module.orderclass.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.androidtools.DateUtils;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.QuestionDetailObj;

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

public class QuestionDetailActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener {

    @BindView(R.id.tv_question_detail_content)
    TextView tv_question_detail_content;
    @BindView(R.id.rv_question_detail_content_anwser)
    RecyclerView rv_question_detail_content_anwser;
    @BindView(R.id.et_question_detail)
    EditText et_question_detail;

    LoadMoreAdapter adapter;

    private String questionId;

    @Override
    protected int getContentView() {
        setAppTitle("问题详情");
        return R.layout.act_question_detail;
    }

    @Override
    protected void initView() {
        questionId = getIntent().getStringExtra(Constant.IParam.questionId);
        String questionContent = getIntent().getStringExtra(Constant.IParam.questionContent);
        tv_question_detail_content.setText(questionContent);
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1, false);
            }
        });
        adapter=new LoadMoreAdapter<QuestionDetailObj>(mContext,R.layout.item_goods_question,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, QuestionDetailObj bean) {
                holder.setText(R.id.tv_goods_question_content,bean.getAnswer_content())
                        .setText(R.id.tv_goods_question_time, DateUtils.dateToString(new Date(bean.getAdd_time()*1000)));
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_question_detail_content_anwser.setLayoutManager(new LinearLayoutManager(mContext));
        rv_question_detail_content_anwser.addItemDecoration(getItemDivider());
        rv_question_detail_content_anwser.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("question_id", questionId);
        map.put("page", page+"");
        map.put("pagesize", pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getQuestionDetail(map, new MyCallBack<List<QuestionDetailObj>>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(List<QuestionDetailObj> list) {
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

    @OnClick({R.id.tv_question_detail_huida})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_question_detail_huida:
                String content=getSStr(et_question_detail);
                if(TextUtils.isEmpty(content)){
                    showMsg("请输入内容");
                    return;
                }
                commit(content);
                break;
        }
    }

    private void commit(String content) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type","2");
        map.put("goods_question_id",questionId);
        map.put("content",content);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.huiDaQuestion(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj) {
                et_question_detail.setText(null);
                getData(1,false);
            }
        });

    }

    @Override
    public void loadMore() {
        getData(pageNum, true);
    }

}
