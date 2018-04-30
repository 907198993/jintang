package com.sk.jintang.module.my.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.Config;
import com.sk.jintang.R;
import com.sk.jintang.module.home.activity.MainActivity;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.response.VouchersObj;

/**
 * Created by administartor on 2017/8/8.
 */

public class VouchersAdapter extends LoadMoreAdapter<VouchersObj> {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VouchersAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }

    @Override
    public void bindData(LoadMoreViewHolder holder, int i, VouchersObj bean) {
        holder.setText(R.id.tv_vouchers_available,String.format("满%s元可用",bean.getAvailable()+""))
        .setText(R.id.tv_vouchers_date,bean.getDeadline())
        .setText(R.id.tv_my_voucher_title,bean.getTitle())
        .setText(R.id.tv_vouchers_money,bean.getFace_value()+"");
        LinearLayout ll_vouchers_gb = (LinearLayout) holder.getView(R.id.ll_vouchers_gb);
        TextView tv_vouchers_yong = (TextView) holder.getView(R.id.tv_vouchers_yong);
        ImageView iv_vouchers_type = (ImageView) holder.getView(R.id.iv_vouchers_type);
        switch (type){
            case Constant.vouchersType_0:
                ll_vouchers_gb.setBackgroundResource(R.drawable.order11);
                tv_vouchers_yong.setVisibility(View.VISIBLE);
                iv_vouchers_type.setVisibility(View.GONE);
                break;
            case Constant.vouchersType_1:
                ll_vouchers_gb.setBackgroundResource(R.drawable.order10);
                tv_vouchers_yong.setVisibility(View.GONE);
                iv_vouchers_type.setImageResource(R.drawable.vouchers1);
                iv_vouchers_type.setVisibility(View.VISIBLE);

                break;
            case Constant.vouchersType_2:
                ll_vouchers_gb.setBackgroundResource(R.drawable.order10);
                tv_vouchers_yong.setVisibility(View.GONE);
                iv_vouchers_type.setImageResource(R.drawable.vouchers2);
                iv_vouchers_type.setVisibility(View.VISIBLE);

                break;
        }
        tv_vouchers_yong.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent=new Intent(Config.useVoucher);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setClass(mContext,MainActivity.class);
//                ActUtils.STActivity((Activity)mContext,intent, ConversationActivity.class);
                ((Activity)mContext).startActivity(intent);
            }
        });
    }

}
