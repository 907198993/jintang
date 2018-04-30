package com.sk.jintang.module.my.activity;

import android.content.Intent;
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
import com.sk.jintang.module.my.network.response.YongJinObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/4.
 */

public class MyYongJinActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener {
    @BindView(R.id.rv_my_yong_jin)
    RecyclerView rv_my_yong_jin;
    @BindView(R.id.tv_yongjin)
    TextView tv_yongjin;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的佣金");
        return R.layout.act_my_yong_jin;
    }

    @Override
    protected void initView() {
        adapter = new LoadMoreAdapter<YongJinObj.CommissionDetailListBean>(mContext, R.layout.item_yong_jin_mingxi, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, YongJinObj.CommissionDetailListBean bean) {
                holder.setText(R.id.tv_yongjin_content,bean.getRemark());

                TextView textView = holder.getTextView(R.id.tv_yongjin_price);
                if(bean.getValue()>0){
                    textView.setTextColor(getResources().getColor(R.color.green));
                    textView.setText("+"+bean.getValue()+"元");
                }else{
                    textView.setTextColor(getResources().getColor(R.color.red));
                    textView.setText(""+bean.getValue()+"元");
                }
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_my_yong_jin.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_yong_jin.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1, false);
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("pagesize", pageSize + "");
        map.put("page", page + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getYongJin(map, new MyCallBack<YongJinObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(YongJinObj obj) {
                tv_yongjin.setText("¥"+obj.getCommission());
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getCommission_detail_list(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getCommission_detail_list(),true);
                }
            }
        });


    }

    @OnClick({R.id.tv_yongjin_tx})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_yongjin_tx:
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.tiXianType,1);
                STActivityForResult(intent,TiXianActivity.class,1000);
//                STActivityForResult(YongJinTiXianActivity.class,1000);
            break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1000:
                    initData();
                    break;
            }
        }

    }
}
