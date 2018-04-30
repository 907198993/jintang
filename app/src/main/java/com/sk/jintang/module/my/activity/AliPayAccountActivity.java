package com.sk.jintang.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.Loading;
import com.github.baseclass.view.MyDialog;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.AliAccountObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/11/18.
 */

public class AliPayAccountActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_ti_xian_ali)
    RecyclerView rv_ti_xian_ali;
    @BindView(R.id.tv_ali_account_edit)
    TextView tv_ali_account_edit;

    LoadMoreAdapter adapter;

    private boolean isDelete;
    private boolean accountIsEmpty=false;
    private String type;
    private String title;
    @Override
    protected int getContentView() {
        setAppRightImg(R.drawable.add_bank2);
        return R.layout.act_alipay_account;
    }

    @Override
    protected void initView() {
        type = getIntent().getStringExtra(Constant.IParam.type);
        if("1".equals(type)){
            title="支付宝";
        }else{
            title="微信";
        }
        setAppTitle("选择"+title);
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }
        });

        adapter=new LoadMoreAdapter<AliAccountObj>(mContext,R.layout.item_ali_account,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, AliAccountObj bean) {
                TextView tv_account_name = holder.getTextView(R.id.tv_account_name);
                tv_account_name.setText(title+"账户：");
                holder.setText(R.id.tv_ali_account,bean.getAlipay_number());
                ImageView iv_ali_default = holder.getImageView(R.id.iv_ali_default);
                if (bean.getIs_default()==1){
                    iv_ali_default.setImageResource(R.drawable.order17);
                }else{
                    iv_ali_default.setImageResource(R.drawable.order17_normal);
                }
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        AliAccountObj obj = (AliAccountObj) adapter.getList().get(position);
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.accountId,obj.getAlipay_id()+"");
                        intent.putExtra(Constant.IParam.account,((AliAccountObj) adapter.getList().get(position)).getAlipay_number());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });

                TextView tv_ali_account_delete = holder.getTextView(R.id.tv_ali_account_delete);
                if(isDelete){
                    tv_ali_account_delete.setVisibility(View.VISIBLE);
                    tv_ali_account_delete.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                            mDialog.setMessage("确认删除吗?");
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
                                    Loading.show(mContext);
                                    String accountId = bean.getAlipay_id()+"";
                                    Map<String,String> map=new HashMap<String,String>();
                                    map.put("alipay_id",accountId);
                                    map.put("type",type);
                                    map.put("sign", GetSign.getSign(map));
                                    ApiRequest.deleteAliAccount(map, new MyCallBack<BaseObj>(mContext) {
                                        @Override
                                        public void onSuccess(BaseObj obj) {
                                            ToastUtils.showToast(mContext,obj.getMsg());
                                            getList().remove(position);

                                            if(notEmpty(getList())&&getList().size()>0){
                                                tv_ali_account_edit.setVisibility(View.VISIBLE);
                                            }else{
                                                tv_ali_account_edit.setVisibility(View.GONE);
                                                accountIsEmpty=true;
                                            }
                                            notifyDataSetChanged();

                                        }
                                    });
                                }
                            });
                            mDialog.create().show();
                        }
                    });
                }else{
                    tv_ali_account_delete.setVisibility(View.GONE);
                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_ti_xian_ali.setLayoutManager(new LinearLayoutManager(mContext));
        rv_ti_xian_ali.addItemDecoration(getItemDivider());
        rv_ti_xian_ali.setAdapter(adapter);

    }
    @Override
    public void finish() {
        if(accountIsEmpty){
            setResult(Constant.RCode.deleteDefaultAccount);
        }
        super.finish();
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }
    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type",type);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAlipayAccount(map, new MyCallBack<List<AliAccountObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<AliAccountObj> list) {
                pageNum=2;
                adapter.setList(list,true);
            }
        });

    }

    @OnClick({R.id.app_right_iv,R.id.tv_ali_account_edit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.type,type);
                STActivityForResult(intent,AddAliPayAccountActivity.class,100);
            break;
            case R.id.tv_ali_account_edit:
                isDelete=!isDelete;
                if(isDelete){
                    tv_ali_account_edit.setText("完成");
                }else{
                    tv_ali_account_edit.setText("编辑");
                }
                adapter.notifyDataSetChanged();
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                getData();
                break;
            }
        }
    }

    @Override
    public void loadMore() {

    }
}
