package com.sk.jintang.module.orderclass.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.jintang.R;

/**
 * Created by administartor on 2017/10/18.
 */

public class GoodsDetailImgAdapter extends BaseRecyclerAdapter<String> {
    public GoodsDetailImgAdapter(Context ctx, int layoutId) {
        super(ctx, layoutId);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView=new ImageView(mContext);
        imageView.setAdjustViewBounds(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        RecyclerViewHolder holder = new RecyclerViewHolder(this.mContext,imageView);
        return holder;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int i, String bean) {
        ImageView imageView = (ImageView) holder.itemView;
        Glide.with(mContext).load(bean).error(R.color.c_press).into(imageView);
    }
}
