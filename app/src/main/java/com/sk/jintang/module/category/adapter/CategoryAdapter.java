package com.sk.jintang.module.category.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.jintang.R;
import com.sk.jintang.module.category.network.response.CategoryObj;


public class CategoryAdapter extends BaseRecyclerAdapter<CategoryObj.TypeListBean> {
    //先声明一个int成员变量
    private int thisPosition;
    //再定义一个int类型的返回值方法
    public int getthisPosition() {
        return thisPosition;
    }

    //其次定义一个方法用来绑定当前参数值的方法
    //此方法是在调用此适配器的地方调用的，此适配器内不会被调用到
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    private CategoryAdapter.OnItemClickListener onItemClickListener;

    public CategoryAdapter(Context ctx, int layoutId) {
        super(ctx, layoutId);
    }



    @Override
    public void bindData(RecyclerViewHolder recyclerViewHolder, int i, CategoryObj.TypeListBean typeListBean) {
        TextView tv_home_gc = recyclerViewHolder.getTextView(R.id.textView);
        tv_home_gc.setText(typeListBean.getGoods_type_name());
        if (i == getthisPosition()) {
            recyclerViewHolder.itemView.setBackgroundColor(Color.parseColor("#f6f6f6"));
            tv_home_gc.setTextColor(Color.parseColor("#2DDB97"));
        } else {
            recyclerViewHolder.itemView.setBackgroundColor(Color.WHITE);
            tv_home_gc.setTextColor(Color.parseColor("#000000"));
        }
        recyclerViewHolder.itemView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClickListener(recyclerViewHolder.getAdapterPosition(),typeListBean,i);
                }
            }
        });

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.template_single_text, parent, false));
        return holder;
    }


    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public interface OnItemClickListener{
        void onItemClickListener(int pos, CategoryObj.TypeListBean data, int i);
    }

    public void setOnItemClickListener(CategoryAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
