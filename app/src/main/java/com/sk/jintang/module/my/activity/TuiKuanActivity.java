package com.sk.jintang.module.my.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.IOCallBack;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseDividerListItem;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.request.UploadImgItem;
import com.sk.jintang.module.my.network.response.TuiKuanMoneyObj;
import com.sk.jintang.module.my.network.response.TuiKuanReasonObj;
import com.sk.jintang.tools.BitmapUtils;
import com.sk.jintang.tools.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import top.zibin.luban.Luban;

/**
 * Created by administartor on 2017/9/6.
 */

public class TuiKuanActivity extends BaseActivity {
    @BindView(R.id.tv_tuikuan_type)
    TextView tv_tuikuan_type;
    @BindView(R.id.tv_tuikuan_reason)
    TextView tv_tuikuan_reason;
    @BindView(R.id.tv_tuikuan_money)
    TextView tv_tuikuan_money;
    @BindView(R.id.et_tuikuan_content)
    EditText et_tuikuan_content;
    @BindView(R.id.tv_tuikuan_tishi)
    TextView tv_tuikuan_tishi;
    @BindView(R.id.rv_tuikuan)
    RecyclerView rv_tuikuan;
    List<TuiKuanReasonObj> tuiKuanReasonList;
    private String orderNo;
    private BottomSheetDialog dialog;
    private double tuiKuanMoney;
    String imgUrl1="";
    String imgUrl2="";
    String imgUrl3="";
    BaseRecyclerAdapter adapter;
    List<String>imgList=new ArrayList<>();

    int selectImgIndex=0;

    private BottomSheetDialog selectPhotoDialog;
    @Override
    protected int getContentView() {
        setAppTitle("申请退款");
        return R.layout.act_tui_kuan;
    }

    @Override
    protected void initView() {
        orderNo = getIntent().getStringExtra(Constant.IParam.orderNo);


        adapter=new BaseRecyclerAdapter<String>(mContext,R.layout.item_tuikuan_addimg) {
            @Override
            public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                RecyclerViewHolder holder;
                if(viewType==1){
                    holder = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_tuikuan_addimg, parent, false));
                }else{
                    holder = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_tuikuan_img, parent, false));
                }
                return holder;
            }
            @Override
            public void bindData(RecyclerViewHolder holder, int position, String bean) {
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if(position==mList.size()){
                            selectImgIndex=-1;
                        }else{
                            selectImgIndex=position;
                        }
                        addPhoto();
                    }
                });
                if(getItemViewType(position)==1){

                }else{
                    Log.i("---","---"+bean);
                    ImageView imageView = holder.getImageView(R.id.iv_tuikuan_img);
                    Glide.with(mContext).load(bean).error(R.color.c_press).into(imageView);
                }
            }
            @Override
            public int getItemViewType(int position) {
                if(position==mList.size()&&mList.size()<3){
                    return 1;
                }else{
                    return 0;
                }
            }
            @Override
            public int getItemCount() {
                if(mList==null){
                    return 0;
                }else if(mList.size()>=3){
                    return mList.size();
                }else{
                    return mList==null?0:mList.size()+1;
                }
            }
        };
        adapter.setList(imgList);
        rv_tuikuan.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        rv_tuikuan.addItemDecoration(new BaseDividerListItem(mContext,BaseDividerListItem.HORIZONTAL_LIST,PhoneUtils.dip2px(mContext,10),R.color.white));
        rv_tuikuan.setAdapter(adapter);
    }

    private void choosePhoto() {

    }

    private void addPhoto() {
        PhoneUtils.hiddenKeyBoard(mContext,et_tuikuan_content);
        showSelectPhotoDialog();
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
        getMoney();
    }

    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getTuiKuanReason(map, new MyCallBack<List<TuiKuanReasonObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<TuiKuanReasonObj> list) {
                tuiKuanReasonList=list;
            }
        });

    }

    private void getMoney() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("order_no",orderNo);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getTuiKuanMoney(map, new MyCallBack<TuiKuanMoneyObj>(mContext) {
            @Override
            public void onSuccess(TuiKuanMoneyObj obj) {
                tv_tuikuan_money.setText(obj.getMoney()+"元");
                tuiKuanMoney = obj.getMoney();
            }
        });
    }

    @OnClick({R.id.tv_tuikuan_reason, R.id.tv_tui_kuan_commit})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tuikuan_reason:
                PhoneUtils.hiddenKeyBoard(mContext,et_tuikuan_content);

                dialog = new BottomSheetDialog(mContext);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                View tuiKuanReasonView = LayoutInflater.from(mContext).inflate(R.layout.popu_tuikuan_reason, null);
                RecyclerView rv_tuikuan_reason = tuiKuanReasonView.findViewById(R.id.rv_tuikuan_reason);
                rv_tuikuan_reason.setLayoutManager(new LinearLayoutManager(mContext));
                rv_tuikuan_reason.addItemDecoration(getItemDivider());
                BaseRecyclerAdapter adapter=new BaseRecyclerAdapter<TuiKuanReasonObj>(mContext,R.layout.item_tuikuan_reason) {
                    @Override
                    public void bindData(RecyclerViewHolder holder, int i, TuiKuanReasonObj bean) {
                        holder.setText(R.id.tv_item_tuikuan_reason,bean.getRefund_reason());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tv_tuikuan_reason.setText(bean.getRefund_reason());
                                dialog.dismiss();
                            }
                        });
                    }
                };
                adapter.setList(tuiKuanReasonList);
                rv_tuikuan_reason.setAdapter(adapter);
                TextView tv_tuikuan_cancle = tuiKuanReasonView.findViewById(R.id.tv_tuikuan_cancle);
                tv_tuikuan_cancle.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(tuiKuanReasonView);
                dialog.show();
                break;
            case R.id.tv_tui_kuan_commit:
                if(TextUtils.isEmpty(getSStr(tv_tuikuan_reason))){
                    showMsg("请选择退款原因");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_tuikuan_content))){
                    showMsg("请输入退款说明");
                    return;
                }/*else if(imgList.size()<=0){
                    showMsg("请上传退款凭证");
                    return;
                }*/
                commitTuiKuan();
                break;
        }
    }

    private void commitTuiKuan() {
        for (int i = 0; i < imgList.size(); i++) {
            if(i==0){
                imgUrl1=imgList.get(i);
            }else if(i==1){
                imgUrl2=imgList.get(i);
            }else{
                imgUrl3=imgList.get(i);
            }
        }
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("order_no",orderNo);
        map.put("refund_type",getSStr(tv_tuikuan_type));
        map.put("refund_reason",getSStr(tv_tuikuan_reason));
        map.put("refund_amount",tuiKuanMoney+"");
        map.put("refund_instruction",getSStr(et_tuikuan_content));
        map.put("refund_voucher1",imgUrl1);
        map.put("refund_voucher2",imgUrl2);
        map.put("refund_voucher3",imgUrl3);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.tuiKuanCommit(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });
    }
    private void showSelectPhotoDialog() {
        if (selectPhotoDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_select_photo,null);
            sexView.findViewById(R.id.tv_select_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    selectPhoto();
                }
            });
            sexView.findViewById(R.id.tv_take_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    takePhoto();
                }
            });
            sexView.findViewById(R.id.tv_photo_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                }
            });
            selectPhotoDialog = new BottomSheetDialog(mContext);
            selectPhotoDialog.setCanceledOnTouchOutside(true);
            selectPhotoDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            selectPhotoDialog.setContentView(sexView);
        }
        selectPhotoDialog.show();
    }
    //选择相册
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3000);
    }
    private String path = Environment.getExternalStorageDirectory() +
            File.separator + Environment.DIRECTORY_DCIM + File.separator;
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }
    Uri photoUri;
    private String imgSaveName="";
    //拍照
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                String fileName = getPhotoFileName() + ".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imgSaveName = path + fileName;
                photoUri = Uri.fromFile(new File(imgSaveName));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, 2000);
            }
        }
    }
    private void uploadImg() {
        showLoading();
        Log.i("========","========"+imgSaveName);
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String newPath= ImageUtils.filePath;
                ImageUtils.makeFolder(newPath);
                FileInputStream fis = null;
                try {
                    List<File> files = Luban.with(mContext).load(imgSaveName).get();
                    String imgStr = BitmapUtils.bitmapToString2(files.get(0));
                    subscriber.onNext(imgStr);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                /*String newPath= ImageUtils.filePath;
                String name=ImageUtils.fileName;
                String smallBitmapPath = ImageUtils.getSmallBitmap(imgSaveName, newPath, name);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(smallBitmapPath);
                    Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                    String imgStr = BitmapUtils.bitmapToString(bitmap);
                    subscriber.onNext(imgStr);
                    subscriber.onCompleted();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }*/
            }
            @Override
            public void onMyNext(String baseImg) {
                UploadImgItem item=new UploadImgItem();
                item.setFile(baseImg);
                String rnd = getRnd();
                Map<String,String>map=new HashMap<String,String>();
                map.put("rnd",rnd);
                map.put("sign",GetSign.getSign(map));
                ApiRequest.uploadImg(map,item, new MyCallBack<BaseObj>(mContext) {
                    @Override
                    public void onSuccess(BaseObj obj) {
//                        imgUrl = obj.getImg();
//                        Glide.with(mContext).load(imgSaveName).error(R.drawable.people).into(civ_info_img);
                        if(selectImgIndex==-1){
                            imgList.add(obj.getImg());
                        }else{
                            imgList.set(selectImgIndex,obj.getImg());
                        }
                        if(imgList.size()>0){
                            tv_tuikuan_tishi.setVisibility(View.GONE);
                        }else{
                            tv_tuikuan_tishi.setVisibility(View.VISIBLE);
                        }
                        adapter.setList(imgList,true);
//                        updateInfoForImg();
                    }
                });
            }
            @Override
            public void onMyError(Throwable e) {
                super.onMyError(e);
                dismissLoading();
                showToastS("图片处理失败");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case 2000:
                uploadImg();
                break;
            case 3000:
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    imgSaveName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    uploadImg();
                }
                break;
        }
    }
}
