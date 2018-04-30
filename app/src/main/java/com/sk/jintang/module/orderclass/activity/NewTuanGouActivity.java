package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.TuanGouObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/2.
 */

public class NewTuanGouActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_new_tuan_gou)
    RecyclerView rv_new_tuan_gou;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("新品团购");
        setAppRightImg(R.drawable.share_title);
        return R.layout.act_new_tuan_gou;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<TuanGouObj>(mContext,R.layout.item_new_tuan_gou,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, TuanGouObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_tuan_gou);
                Glide.with(mContext).load(bean.getGoods_image()).error(R.color.c_press).into(imageView);
                holder.setText(R.id.tv_tuan_gou_title,bean.getGoods_name())
                        .setText(R.id.tv_tuan_gou_price,"¥"+bean.getGroup_price())
                        .setText(R.id.tv_tuan_gou_title,bean.getGoods_name())
                        .setText(R.id.tv_tuan_gou_num,bean.getGroup_num_people()+"人团");
                TextView textView = holder.getTextView(R.id.tv_tuan_gou_before_price);
                textView.setText("¥"+bean.getPrice());
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                textView.getPaint().setAntiAlias(true);

                TextView tv_tuan_gou_go = holder.getTextView(R.id.tv_tuan_gou_go);
                tv_tuan_gou_go.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {

                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.goodsId,bean.getGoods_id()+"");
                        STActivity(intent,GoodsDetailTuanGouActivity.class);
                    }
                });

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {

                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.goodsId,bean.getGoods_id()+"");
                        STActivity(intent,GoodsDetailTuanGouActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_new_tuan_gou.setLayoutManager(new LinearLayoutManager(mContext));
        rv_new_tuan_gou.addItemDecoration(getItemDivider());

        rv_new_tuan_gou.setAdapter(adapter);

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
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getTuanGouList(map, new MyCallBack<List<TuanGouObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<TuanGouObj> list) {
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
                showFenXiang("0");
            break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
