package com.sk.jintang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/22.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Myholder> {
    private Context mContext;
    private String[] strs = new String[100];
    public RecyclerAdapter(Context context) {
        this.mContext = context;
        //为测试给Recycler添加数据
        for (int i = 0; i < 100; i++) {
            strs[i] = i + "";
        }
    }
    //这里返回一个ViewHolder
    @Override
    public RecyclerAdapter.Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_recycler_item, null);
        Myholder myholder = new Myholder(view);
        return myholder;
    }
    //为ViewHolder中的布局绑定数据
    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        holder.textView.setText(strs[position]);
    }
    @Override
    public int getItemCount() {
        return strs.length;
    }
    static class Myholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView textView;
        public Myholder(View itemView) {
            super(itemView);
            //ButterKnife也可以用于ViewHoder中
            ButterKnife.bind(this, itemView);
        }
    }
}