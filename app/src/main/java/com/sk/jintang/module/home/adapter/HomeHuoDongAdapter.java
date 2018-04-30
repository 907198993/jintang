package com.sk.jintang.module.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.utils.ActUtils;
import com.sk.jintang.Config;
import com.sk.jintang.R;
import com.sk.jintang.module.community.activity.YangShengXiaoZhiShiActivity;
import com.sk.jintang.module.home.network.response.HomeHuoDongObj;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.orderclass.activity.NewTuanGouActivity;
import com.sk.jintang.module.orderclass.activity.WeiShangDaiFaActivity;

/**
 * Created by administartor on 2017/9/12.
 */

public class HomeHuoDongAdapter extends BaseRecyclerAdapter<HomeHuoDongObj.ShufflingListBean> {

    private final int screenWidth;
    private final int itemHeight;

    public HomeHuoDongAdapter(Context mContext, int layoutId) {
        super(mContext, layoutId);

        screenWidth = PhoneUtils.getScreenWidth(mContext);
        itemHeight = (screenWidth - PhoneUtils.dip2px(mContext, 106)) / 3;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_home_huodong, parent, false));
        return holder;
    }

     @Override
    public void bindData(RecyclerViewHolder holder, int i, HomeHuoDongObj.ShufflingListBean bean) {
        holder.setText(R.id.tv_home_huodong,bean.getTitle());
        ImageView imageView = holder.getImageView(R.id.iv_home_huodong);
        Glide.with(mContext).load(bean.getImg_url()).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.PNG,90)).error(R.color.white).into(imageView);

        LinearLayout ll_home_huodong = (LinearLayout) holder.getView(R.id.ll_home_huodong);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width=itemHeight;
        layoutParams.height=itemHeight;
        ll_home_huodong.setLayoutParams(layoutParams);


            holder.itemView.setOnClickListener(new MyOnClickListener() {
                private Intent intent;
                @Override
                protected void onNoDoubleClick(View view) {
                    switch (i){
                        case 0:
                            if(TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id,null))){
                                ActUtils.STActivity((Activity) mContext,LoginActivity.class);
                                return;
                            }
                            intent = new Intent();
                            intent.putExtra(com.sk.jintang.module.home.Constant.IParam.goodsType,com.sk.jintang.module.home.Constant.IParam.goodsType_4);
                            ActUtils.STActivity((Activity) mContext, intent,WeiShangDaiFaActivity.class);
                        break;
                        case 1:
//                            Intent intent=new Intent();
//                            intent.putExtra(com.sk.yangyu.module.home.Constant.IParam.goodsType,com.sk.yangyu.module.home.Constant.IParam.goodsType_4);
                            ActUtils.STActivity((Activity) mContext,NewTuanGouActivity.class);

                        break;
                        case 2:
                            if(TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id,null))){
                                ActUtils.STActivity((Activity) mContext,LoginActivity.class);
                                return;
                            }
                             intent=new Intent();
                            intent.putExtra(com.sk.jintang.module.home.Constant.IParam.goodsType,com.sk.jintang.module.home.Constant.IParam.goodsType_5);
                            ActUtils.STActivity((Activity) mContext, intent,WeiShangDaiFaActivity.class);
                        break;
                        case 3:
                            if(TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id,null))){
                                ActUtils.STActivity((Activity) mContext,LoginActivity.class);
                                return;
                            }
                            intent=new Intent();
                            intent.putExtra(com.sk.jintang.module.home.Constant.IParam.goodsType,com.sk.jintang.module.home.Constant.IParam.goodsType_6);
                            ActUtils.STActivity((Activity) mContext, intent,WeiShangDaiFaActivity.class);
                        break;
                        case 4:
                            if(TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id,null))){
                                ActUtils.STActivity((Activity) mContext,LoginActivity.class);
                                return;
                            }
                             intent=new Intent();
                            intent.putExtra(com.sk.jintang.module.home.Constant.IParam.goodsType,com.sk.jintang.module.home.Constant.IParam.goodsType_7);
                            ActUtils.STActivity((Activity) mContext, intent,WeiShangDaiFaActivity.class);
                        break;
                        case 5:
                            intent=new Intent();
                            if(TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id,null))){
                                ActUtils.STActivity((Activity) mContext,LoginActivity.class);
                                return;
                            }
                            intent.putExtra(com.sk.jintang.module.home.Constant.IParam.goodsType,com.sk.jintang.module.home.Constant.IParam.goodsType_7);
                            ActUtils.STActivity((Activity) mContext, intent,YangShengXiaoZhiShiActivity.class);
                            break;
                    }

                }
            });

    }

}
