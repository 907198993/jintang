package com.sk.jintang.module.category.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.jintang.R;
import com.sk.jintang.module.category.network.response.CategoryObj;


public class CategoryTwoAdapter extends BaseRecyclerAdapter<CategoryObj.TypeListBean> {


    private CategoryTwoAdapter.OnItem2ClickListener onItem2ClickListener;

    public CategoryTwoAdapter(Context ctx, int layoutId) {
        super(ctx, layoutId);
    }



    @Override
    public void bindData(RecyclerViewHolder recyclerViewHolder, int i, CategoryObj.TypeListBean typeListBean) {
        TextView tv_home_gc = recyclerViewHolder.getTextView(R.id.tv_home_gc);
        tv_home_gc.setText(typeListBean.getGoods_type_name());
        ImageView imageView = recyclerViewHolder.getImageView(R.id.iv_home_gc);
        Glide.with(mContext).load(typeListBean.getGoods_type_img()).error(R.color.c_press).into(imageView);
        recyclerViewHolder.itemView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (onItem2ClickListener != null){
//                    RxBus.getInstance().post(new MoreCategoryEvent(typeListBean.getGoods_type_id(),typeListBean.getGoods_type_name()));
                    onItem2ClickListener.onItem2ClickListener(recyclerViewHolder.getAdapterPosition(),typeListBean,i);
                }
            }
        });

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_home_gczd, parent, false));
        return holder;
    }


    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public interface OnItem2ClickListener{
        void onItem2ClickListener(int pos, CategoryObj.TypeListBean data, int i);
    }

    public void setOnItem2ClickListener(CategoryTwoAdapter.OnItem2ClickListener onItemClickListener){
        this.onItem2ClickListener = onItemClickListener;
    }

}
