package com.sk.jintang.module.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.RxBus;
import com.sk.jintang.R;
import com.sk.jintang.module.home.event.MoreCategoryEvent;
import com.sk.jintang.module.home.network.response.HomeButtomObj;

/**
 * Created by administartor on 2017/9/12.
 */

public class HomeOtherHuoDongAdapter extends BaseRecyclerAdapter<HomeButtomObj.GoodsTypeListBean> {

    public HomeOtherHuoDongAdapter(Context mContext, int layoutId) {
        super(mContext, layoutId);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_home_huodong_other, parent, false));
        return holder;

    }
    @Override
    public void bindData(RecyclerViewHolder holder, int i, HomeButtomObj.GoodsTypeListBean bean) {
        TextView textView = holder.getTextView(R.id.tv_home_huodong_other);
        ImageView imageView = holder.getImageView(R.id.iv_home_huodong_other);
        if(i==mList.size()){
            textView.setText("更多");
            imageView.setImageResource(R.drawable.home24);
            holder.itemView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    RxBus.getInstance().post(new MoreCategoryEvent("0","全部"));
                }
            });
//            Glide.with(mContext).load(R.drawable.home24).error(R.drawable.home24).into(imageView);
        }else{
            Glide.with(mContext).load(bean.getGoods_type_img()).error(R.color.background_f2).into(imageView);
            textView.setText(bean.getGoods_type_name());
            holder.itemView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    RxBus.getInstance().post(new MoreCategoryEvent(bean.getGoods_type_id(),bean.getGoods_type_name()));
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mList==null?0:mList.size()+1;
    }
}
