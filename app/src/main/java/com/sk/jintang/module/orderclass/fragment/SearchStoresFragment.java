package com.sk.jintang.module.orderclass.fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.view.MyDialog;
import com.github.customview.FlowLayout;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.my.activity.SearchStoresResultActivity;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.event.RefreshEvent;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.SearchRecordObj;
import com.sk.jintang.view.ProgressLayout;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.github.baseclass.rx.RxBusHelper.getRxBusEvent;

public class SearchStoresFragment extends BaseFragment {
    @BindView(R.id.iv_search_delete)
    ImageView ivSearchDelete;
    @BindView(R.id.fl_search_history)
    FlowLayout fl_search_history;
    @BindView(R.id.fl_search_hot)
    FlowLayout fl_search_hot;
    @BindView(R.id.pl_load)
    ProgressLayout plLoad;
    Unbinder unbinder;

    @Override
    protected int getContentView() {
        return R.layout.fragment_search_goods;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void myReStart() {
        super.myReStart();
        getData();
    }
    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(RefreshEvent.class, new MySubscriber<RefreshEvent>() {

            @Override
            public void onMyNext(RefreshEvent refreshEvent) {
                getData();
                Toast.makeText(getActivity(),"123",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId() == null ? "0" : getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getSearchRecord(map, new MyCallBack<SearchRecordObj>(mContext, pl_load) {
            @Override
            public void onSuccess(SearchRecordObj obj) {
                fl_search_history.removeAllViews();
                fl_search_hot.removeAllViews();
                if (notEmpty(obj.getRecently_list())) {
                    for (int i = 0; i < obj.getRecently_list().size(); i++) {
                        SearchRecordObj.RecentlyListBean bean = obj.getRecently_list().get(i);
                        MyTextView textView = new MyTextView(mContext);
                        textView.setText(bean.getSearch_term());
                        textView.setPadding(PhoneUtils.dip2px(mContext, 12), 0, PhoneUtils.dip2px(mContext, 12), 0);
                        textView.setGravity(Gravity.CENTER);
                        textView.setMinHeight(PhoneUtils.dip2px(mContext, 35));
                        textView.setSolidColor(Color.parseColor("#f5f5f5"));
                        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, PhoneUtils.dip2px(mContext, 10), PhoneUtils.dip2px(mContext, 10));
                        textView.setLayoutParams(layoutParams);
                        textView.setRadius(PhoneUtils.dip2px(mContext, 3));
                        textView.complete();
                        textView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
//                                PhoneUtils.hiddenKeyBoard(mContext, et_search_content);
                                Intent intent = new Intent();
                                intent.putExtra(Constant.IParam.searchStr, bean.getSearch_term());
                                STActivityForResult(intent, SearchStoresResultActivity.class, 100);
                            }
                        });
                        fl_search_history.addView(textView);
                    }
                }
                if (notEmpty(obj.getHottest_list())) {
                    for (int i = 0; i < obj.getHottest_list().size(); i++) {
                        SearchRecordObj.HottestListBean hottestListBean = obj.getHottest_list().get(i);
                        MyTextView textView = new MyTextView(mContext);
                        textView.setText(hottestListBean.getSearch_term());
                        textView.setPadding(PhoneUtils.dip2px(mContext, 12), 0, PhoneUtils.dip2px(mContext, 12), 0);
                        textView.setGravity(Gravity.CENTER);
                        textView.setMinHeight(PhoneUtils.dip2px(mContext, 35));
                        textView.setSolidColor(Color.parseColor("#f5f5f5"));
                        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, PhoneUtils.dip2px(mContext, 10), PhoneUtils.dip2px(mContext, 10));
                        textView.setLayoutParams(layoutParams);
                        textView.setRadius(PhoneUtils.dip2px(mContext, 3));
                        textView.complete();
                        textView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
//                                PhoneUtils.hiddenKeyBoard(mContext, et_search_content);
                                Intent intent = new Intent();
                                intent.putExtra(Constant.IParam.searchStr, hottestListBean.getSearch_term());
                                STActivityForResult(intent, SearchStoresResultActivity.class, 100);
                            }
                        });
                        fl_search_hot.addView(textView);
                    }
                }
            }
        });
    }
    @OnClick({R.id.iv_search_delete})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_delete:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                MyDialog.Builder mDialog = new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认删除历史记录?");
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
                        deleteRecord();
                    }
                });
                mDialog.create().show();
                break;
        }
    }
    private void deleteRecord() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.deleteSearchRecord(map, new MyCallBack<BaseObj>(mContext, true) {
            @Override
            public void onSuccess(BaseObj obj) {
                getData();
                RxBus.getInstance().post(new RefreshEvent());
            }
        });
    }
}
