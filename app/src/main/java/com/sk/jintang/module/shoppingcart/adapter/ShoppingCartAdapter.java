package com.sk.jintang.module.shoppingcart.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.utils.ActUtils;
import com.github.baseclass.view.Loading;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyCheckBox;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.activity.GoodsDetailActivity;
import com.sk.jintang.module.shoppingcart.event.TotalPriceEvent;
import com.sk.jintang.module.shoppingcart.network.ApiRequest;
import com.sk.jintang.module.shoppingcart.network.response.ShoppingCartObj;
import com.sk.jintang.tools.AndroidUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by administartor on 2017/9/18.
 */

public class ShoppingCartAdapter extends BaseRecyclerAdapter<ShoppingCartObj> {
    private boolean isEdit;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public ShoppingCartAdapter(Context ctx, int layoutId) {
        super(ctx, layoutId);
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, ShoppingCartObj bean) {

        ImageView imageView = holder.getImageView(R.id.iv_shopingcart);
        TextView tv_shopingcart_name = holder.getTextView(R.id.tv_shopingcart_name);
        TextView tv_shopingcart_guige = holder.getTextView(R.id.tv_shopingcart_guige);
        imageView.setOnClickListener(getListenner(bean));
        tv_shopingcart_name.setOnClickListener(getListenner(bean));
        tv_shopingcart_guige.setOnClickListener(getListenner(bean));

        Glide.with(mContext).load(bean.getGoods_image()).error(R.color.c_press).into(imageView);

        MyCheckBox cb_shopingcart = (MyCheckBox) holder.getView(R.id.cb_shopingcart);
        if(bean.isSelect()){
            cb_shopingcart.setChecked(true);
        }else{
            cb_shopingcart.setChecked(false);
        }

        /*final int num=bean.getNumber();
        double price=bean.getPrice();
        BigDecimal bdNum=new BigDecimal(num);
        BigDecimal bdPrice=new BigDecimal(price);
        BigDecimal total = bdNum.multiply(bdPrice);*/
        TextView tv_shopingcart_price = holder.getTextView(R.id.tv_shopingcart_price);
        tv_shopingcart_price.setText("¥"+mList.get(position).getTotalPrice());
        holder.setText(R.id.tv_shopingcart_name,bean.getGoods_name())
                .setText(R.id.tv_shopingcart_guige,bean.getSpecification())
                .setText(R.id.tv_shopingcart_unitprice,bean.getPrice()+"")
                .setText(R.id.tv_shopingcart_guige_second,bean.getSpecification())
                .setText(R.id.tv_shopingcart_num,bean.getNumber()+"")
                .setText(R.id.tv_shopingcart_num2,bean.getNumber()+"");

        cb_shopingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.get(position).setSelect(!mList.get(position).isSelect());

                jiSuanTotalPrice();

            }
        });

        LinearLayout ll_shopping_cart = (LinearLayout) holder.getView(R.id.ll_shopping_cart);
        LinearLayout ll_shopping_cart_edit = (LinearLayout) holder.getView(R.id.ll_shopping_cart_edit);
        if(isEdit){
            ll_shopping_cart_edit.setVisibility(View.VISIBLE);
            ll_shopping_cart.setVisibility(View.INVISIBLE);
        }else{
            ll_shopping_cart.setVisibility(View.VISIBLE);
            ll_shopping_cart_edit.setVisibility(View.INVISIBLE);
        }

        ImageView iv_shopingcart_jian = holder.getImageView(R.id.iv_shopingcart_jian);
        ImageView iv_shopingcart_jia = holder.getImageView(R.id.iv_shopingcart_jia);
        ImageView iv_shopingcart_delete = holder.getImageView(R.id.iv_shopingcart_delete);
        ImageView iv_shopingcart_jian2 = holder.getImageView(R.id.iv_shopingcart_jian2);
        ImageView iv_shopingcart_jia2 = holder.getImageView(R.id.iv_shopingcart_jia2);

        iv_shopingcart_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jian(bean, position, tv_shopingcart_price, holder);
            }
        });
        iv_shopingcart_jian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jian(bean, position, tv_shopingcart_price, holder);
            }
        });
        iv_shopingcart_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jia(bean, position, tv_shopingcart_price, holder);
            }
        });
        iv_shopingcart_jia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jia(bean, position, tv_shopingcart_price, holder);
            }
        });
        iv_shopingcart_delete.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("确认删除当前商品吗?");
                mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteGoods(position,bean);
                    }
                });
                mDialog.create().show();
            }
        });

    }

    @NonNull
    private MyOnClickListener getListenner(final ShoppingCartObj bean) {
        return new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {

                Intent intent = new Intent();
                intent.putExtra(Constant.IParam.goodsId, bean.getGoods_id() + "");
                ActUtils.STActivity((Activity) mContext,intent, GoodsDetailActivity.class);
            }
        };
    }

    private void jia(ShoppingCartObj bean, int position, TextView tv_shopingcart_price, RecyclerViewHolder holder) {
        if(bean.getNumber()<bean.getStock()){
            mList.get(position).setNumber(bean.getNumber()+1);
            tv_shopingcart_price.setText("¥"+mList.get(position).getTotalPrice());

            holder.setText(R.id.tv_shopingcart_num,bean.getNumber()+"")
                    .setText(R.id.tv_shopingcart_num2,bean.getNumber()+"");
            jiSuanTotalPrice();
            updateGoodsNum(mList.get(position).getId(),mList.get(position).getNumber());
        }else{
            ToastUtils.showToast(mContext,"当前商品库存为:"+bean.getStock());
        }
    }

    private void jian(ShoppingCartObj bean, int position, TextView tv_shopingcart_price, RecyclerViewHolder holder) {
        if(bean.getNumber()>1){
            mList.get(position).setNumber(bean.getNumber()-1);
            tv_shopingcart_price.setText("¥"+mList.get(position).getTotalPrice());
            holder.setText(R.id.tv_shopingcart_num,bean.getNumber()+"")
                .setText(R.id.tv_shopingcart_num2,bean.getNumber()+"");
            jiSuanTotalPrice();
            updateGoodsNum(mList.get(position).getId(),mList.get(position).getNumber());
        }
    }

    private void updateGoodsNum(int id,int num) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",SPUtils.getPrefString(mContext,Config.user_id,null));
        map.put("sc_id",id+"");
        map.put("number",num+"");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.updateShoppingCartNum(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {

            }
        });

    }

    private void jiSuanTotalPrice() {
        double total=0;
        boolean isSelectAll=true;
        if(mList.size()==0){
            isSelectAll=false;
        }
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).isSelect()){
                total=AndroidUtils.jiaFa(total,mList.get(i).getTotalPrice());
            }else{
                isSelectAll=false;
            }
        }
        RxBus.getInstance().post(new TotalPriceEvent("¥"+total,isSelectAll));
    }



    private void deleteGoods(int position, ShoppingCartObj bean) {
        Loading.show(mContext);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id", SPUtils.getPrefString(mContext, Config.user_id,null));
        map.put("sc_id",bean.getId()+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.deleteShoppingCart(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                boolean select = mList.get(position).isSelect();
                ToastUtils.showToast(mContext,obj.getMsg());
                mList.remove(position);
                notifyDataSetChanged();

                jiSuanTotalPrice();

            }
        });

    }


    public void setSelectAll(boolean selectAll) {
        if(selectAll){
            for (int i = 0;  i <mList.size() ;  i++) {
                if(!mList.get(i).isSelect()){
                    mList.get(i).setSelect(true);
                }
            }
            jiSuanTotalPrice();
        }else{
            for (int i = 0;  i <mList.size() ;  i++) {
                if(mList.get(i).isSelect()){
                    mList.get(i).setSelect(false);
                }
            }
        }
        notifyDataSetChanged();
    }
}
