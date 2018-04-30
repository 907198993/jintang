package com.sk.jintang.module.my.activity;

import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyCheckBox;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.BankObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/2.
 */

public class AddBankCardActivity extends BaseActivity {
    @BindView(R.id.et_bank_username)
    MyEditText et_bank_username;
    @BindView(R.id.et_bank_card)
    MyEditText et_bank_card;
    @BindView(R.id.et_bank_zhihang)
    MyEditText et_bank_zhihang;
    @BindView(R.id.tv_bank_img)
    ImageView tv_bank_img;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.ll_select_bank)
    LinearLayout ll_select_bank;
    @BindView(R.id.cb_bank_agree)
    MyCheckBox cb_bank_agree;
    @BindView(R.id.tv_add_bank_commit)
    MyTextView tv_add_bank_commit;

    private BottomSheetDialog bankDialog;
    private LoadMoreAdapter adapter;
    private String bankId;
    private String agreement;
    private BottomSheetDialog xieYiDialog;



    @Override
    protected int getContentView() {
        setAppTitle("添加银行卡");
        return R.layout.act_add_bank_card;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getXieYi(false);
    }

    @OnClick({R.id.ll_select_bank, R.id.tv_add_bank_commit,R.id.tv_tx_xieyi})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tx_xieyi:
                if(TextUtils.isEmpty(agreement)){
                    showLoading();
                    getXieYi(true);
                }else{
                    showXieYi(agreement);
                }
                break;
            case R.id.ll_select_bank:
                getBankList();
                break;
            case R.id.tv_add_bank_commit:
                String userName = getSStr(et_bank_username);
                String cardNum = getSStr(et_bank_card);
                if(TextUtils.isEmpty(userName)){
                    showMsg("请输入持卡人");
                    return;
                }else if(TextUtils.isEmpty(cardNum)){
                    showMsg("请输入卡号");
                    return;
                }else if(cardNum.trim().length()<15||cardNum.trim().length()>19){
                    showMsg("请输入正确卡号");
                    return;
                }else if(TextUtils.isEmpty(bankId)){
                    showMsg("请选择银行");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_bank_zhihang))){
                    showMsg("请输入开户行");
                    return;
                }
                addBank(userName,cardNum);
                break;
        }
    }

    private void showXieYi(String content) {
        /*View xieYi = LayoutInflater.from(mContext).inflate(R.layout.popu_tx_xieyi, null);
        xieYiDialog = new BottomSheetDialog(mContext);
        xieYiDialog.setCancelable(false);
        xieYiDialog.setContentView(xieYi);
        xieYiDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(xieYiDialog.isShowing()&&keyCode== KeyEvent.KEYCODE_BACK){
                    xieYiDialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        xieYiDialog.show();
        xieYi.findViewById(R.id.tv_xy_queding).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                xieYiDialog.dismiss();
            }
        });
        TextView xieYiContent = (TextView) xieYi.findViewById(R.id.tv_tx_xieyi_content);
        xieYiContent.setText(content);*/

    }
    private void getXieYi(boolean manual){
        String rnd=getRnd();
        /*addSubscription(ApiRequest.getTXXieYi(rnd,getSign("rnd",rnd)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                agreement = obj.getAgreement();
                if(manual){
                    showXieYi(agreement);
                }
            }
        }));*/
    }
    private void getBankList() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getBankList(map, new MyCallBack<List<BankObj>>(mContext) {
            @Override
            public void onSuccess(List<BankObj> list) {
                showBankDialog(list);
            }
        });

    }

    private void showBankDialog(List<BankObj> list) {
        if (bankDialog == null) {
            adapter=new LoadMoreAdapter<BankObj>(mContext,R.layout.item_select_bank,0) {
                @Override
                public void bindData(LoadMoreViewHolder holder, int position, BankObj bean) {
                    holder.setText(R.id.tv_item_bank_name,bean.getBank_name());
                    ImageView imageView = holder.getImageView(R.id.iv_item_bank_img);
                    Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);
                    LinearLayout ll_dialog_bank = (LinearLayout) holder.getView(R.id.ll_dialog_bank);
                    ll_dialog_bank.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            bankDialog.dismiss();
                            bankId=bean.getBank_id()+"";
                            bean.getImage_url();
                            tv_bank_img.setVisibility(View.VISIBLE);
                            Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(tv_bank_img);
                            tv_bank_name.setText(bean.getBank_name());
                        }
                    });

                }
            };
            adapter.setList(list);
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_bank,null);
            RecyclerView rv_bank = (RecyclerView) sexView.findViewById(R.id.rv_bank);
            rv_bank.setLayoutManager(new LinearLayoutManager(mContext));
            rv_bank.setAdapter(adapter);
            sexView.findViewById(R.id.tv_bank_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    bankDialog.dismiss();
                }
            });

            bankDialog = new BottomSheetDialog(mContext);
            bankDialog.setCanceledOnTouchOutside(true);
            bankDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            bankDialog.setContentView(sexView);
        }
        bankDialog.show();
    }

    private void addBank(String userName, String cardNum) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("realname",userName);
        map.put("bank_card_num",cardNum);
        map.put("opening_bank",getSStr(et_bank_zhihang));
        map.put("bank_id",bankId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.addAccount(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
