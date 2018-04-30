package com.sk.jintang.module.my.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.activity.MyAllCollectActivity;
import com.sk.jintang.module.my.activity.MyBalanceActivity;
import com.sk.jintang.module.my.activity.MyDataActivity;
import com.sk.jintang.module.my.activity.MyEvaluateActivity;
import com.sk.jintang.module.my.activity.MyFenXiaoActivity;
import com.sk.jintang.module.my.activity.MyMessageActivity;
import com.sk.jintang.module.my.activity.MyOrderListActivity;
import com.sk.jintang.module.my.activity.MySheQuActivity;
import com.sk.jintang.module.my.activity.MyVouchersActivity;
import com.sk.jintang.module.my.activity.QianDaoActivity;
import com.sk.jintang.module.my.activity.SetingActivity;
import com.sk.jintang.module.my.activity.TuiHuoListActivity;
import com.sk.jintang.module.my.activity.YangShengDouActivity;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.UserInfoObj;
import com.sk.jintang.module.orderclass.activity.AddressListActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by administartor on 2017/8/4.
 */

public class MyFragment extends BaseFragment {


    @BindView(R.id.civ_my_img)
    CircleImageView civ_my_img;
    @BindView(R.id.tv_my_name)
    TextView tv_my_name;
    @BindView(R.id.iv_my_amount)
    TextView iv_my_amount;
    @BindView(R.id.iv_my_vouchers)
    TextView iv_my_vouchers;
    @BindView(R.id.iv_my_ysd)
    TextView iv_my_ysd;
    @BindView(R.id.tv_my_point)
    TextView tv_my_point;

    @Override
    protected int getContentView() {
        return R.layout.frag_my;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.user_id,null))){
            return;
        }
        getData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.user_id,null))){
                return;
            }
            getData();
        }
    }

    private void getData() {
        if(TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.user_id,null))){
            return;
        }
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getUserInfo(map, new MyCallBack<UserInfoObj>(mContext) {
            @Override
            public void onSuccess(UserInfoObj obj) {
                SPUtils.setPrefString(mContext, Config.mobile,obj.getMobile());
                SPUtils.setPrefString(mContext, Config.sex,obj.getSex());
                SPUtils.setPrefString(mContext, Config.avatar,obj.getAvatar());
                SPUtils.setPrefString(mContext, Config.birthday,obj.getBirthday());
                SPUtils.setPrefString(mContext, Config.nick_name,obj.getNick_name());
                SPUtils.setPrefString(mContext, Config.user_name,obj.getUser_name());
                SPUtils.setPrefString(mContext, Config.amount,obj.getAmount()+"");
                SPUtils.setPrefInt(mContext, Config.count_wsy,obj.getCount_wsy());
                SPUtils.setPrefInt(mContext, Config.keeping_bean,obj.getKeeping_bean());
                SPUtils.setPrefInt(mContext, Config.news_is_check,obj.getNews_is_check());
                getInfo();
            }
        });
    }

    private void getInfo() {
        String nick_name = SPUtils.getPrefString(mContext, Config.nick_name, null);
        String sex = SPUtils.getPrefString(mContext, Config.sex, null);
        String avatar = SPUtils.getPrefString(mContext, Config.avatar, null);
        String amount = SPUtils.getPrefString(mContext, Config.amount, null);
        int keeping_bean = SPUtils.getPrefInt(mContext, Config.keeping_bean, 0);
        int count_wsy = SPUtils.getPrefInt(mContext, Config.count_wsy, 0);
        int isShowPoint = SPUtils.getPrefInt(mContext, Config.news_is_check, 0);
        if(isShowPoint==1){
            tv_my_point.setVisibility(View.VISIBLE);
        }else{
            tv_my_point.setVisibility(View.INVISIBLE);
        }
        tv_my_name.setText(nick_name);
        if("男".equals(sex)){
            tv_my_name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.boy1,0);
        }else if("女".equals(sex)){
            tv_my_name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.girl,0);
        }else{
            tv_my_name.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
        if (avatar != null) {
            Glide.with(mContext).load(avatar).error(R.drawable.people).into(civ_my_img);
        }
        iv_my_amount.setText("¥"+amount);
        iv_my_vouchers.setText(count_wsy+"");
        iv_my_ysd.setText(keeping_bean+"");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_my_address,R.id.ll_my_account,R.id.ll_my_yangshengdou,R.id.ll_my_info,R.id.ll_my_vouchers,R.id.iv_my_seting, R.id.iv_my_msg, R.id.tv_my_qiandao, R.id.tv_my_order, R.id.tv_my_shequ, R.id.tv_my_evaluate, R.id.tv_my_fenxiao, R.id.tv_my_tuihuo, R.id.tv_my_collect, R.id.tv_my_zixun})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_my_account:
                STActivity(MyBalanceActivity.class);
                break;
            case R.id.ll_my_yangshengdou:
                STActivity(YangShengDouActivity.class);
                break;
            case R.id.ll_my_info:
                STActivity(MyDataActivity.class);
                break;
            case R.id.ll_my_vouchers:
                STActivity(MyVouchersActivity.class);
                break;
            case R.id.iv_my_seting:
                STActivity(SetingActivity.class);
                break;
            case R.id.iv_my_msg:
                STActivity(MyMessageActivity.class);
                break;
            case R.id.tv_my_qiandao:
                STActivity(QianDaoActivity.class);
                break;
            case R.id.tv_my_order:
                STActivity(MyOrderListActivity.class);
                break;
            case R.id.tv_my_address:
                STActivity(AddressListActivity.class);
                break;
            case R.id.tv_my_shequ:
                STActivity(MySheQuActivity.class);
                break;
            case R.id.tv_my_evaluate:
                STActivity(MyEvaluateActivity.class);
                break;
            case R.id.tv_my_fenxiao:
                STActivity(MyFenXiaoActivity.class);
                break;
            case R.id.tv_my_tuihuo:
                STActivity(TuiHuoListActivity.class);
                break;
            case R.id.tv_my_collect:
                STActivity(MyAllCollectActivity.class);
                break;
            case R.id.tv_my_zixun:
                goHX();
                break;
        }
    }


}
