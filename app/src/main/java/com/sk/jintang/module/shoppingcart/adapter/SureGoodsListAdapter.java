package com.sk.jintang.module.shoppingcart.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.jintang.R;
import com.sk.jintang.module.category.network.response.CategoryObj;
import com.sk.jintang.module.shoppingcart.network.response.SureOrderObj;


public class SureGoodsListAdapter extends BaseRecyclerAdapter<SureOrderObj.OrderGoodsListBean.GoodsListBean> {


    private SureGoodsListAdapter.OnItem2ClickListener onItem2ClickListener;

    public SureGoodsListAdapter(Context ctx, int layoutId) {
        super(ctx, layoutId);
    }



    @Override
    public void bindData(RecyclerViewHolder recyclerViewHolder, int i, SureOrderObj.OrderGoodsListBean.GoodsListBean typeListBean) {

          recyclerViewHolder.setText(R.id.tv_item_sure_order_goods_title,typeListBean.getGoods_name())
                            .setText(R.id.tv_item_sure_order_goods_guige,typeListBean.getSpecification())
                            .setText(R.id.tv_item_sure_order_goods_price,"¥"+typeListBean.getPrice())
                            .setText(R.id.tv_sure_order_goods_num,"X"+typeListBean.getNumber());

        ImageView imageView = recyclerViewHolder.getImageView(R.id.iv_item_sure_order);
        Glide.with(mContext).load(typeListBean.getGoods_image()).error(R.color.c_press).into(imageView);
        recyclerViewHolder.itemView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                if (onItem2ClickListener != null){
//                    RxBus.getInstance().post(new MoreCategoryEvent(typeListBean.getGoods_type_id(),typeListBean.getGoods_type_name()));
//                    onItem2ClickListener.onItem2ClickListener(recyclerViewHolder.getAdapterPosition(),typeListBean,i);
//                }
            }
        });

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_sure_goods_list2, parent, false));
        return holder;
    }


    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public interface OnItem2ClickListener{
        void onItem2ClickListener(int pos, CategoryObj.TypeListBean data, int i);
    }

    public void setOnItem2ClickListener(SureGoodsListAdapter.OnItem2ClickListener onItemClickListener){
        this.onItem2ClickListener = onItemClickListener;
    }

}
