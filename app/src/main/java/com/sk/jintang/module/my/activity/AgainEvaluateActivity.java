package com.sk.jintang.module.my.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import com.github.baseclass.view.MyDialog;
import com.github.customview.FlowLayout;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseDividerListItem;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.request.AgainEvaluateItem;
import com.sk.jintang.module.my.network.request.UploadImgItem;
import com.sk.jintang.module.my.network.response.EvaluateDetailObj;
import com.sk.jintang.module.orderclass.activity.ImageDetailActivity;
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
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import rx.Subscriber;
import top.zibin.luban.Luban;

/**
 * Created by administartor on 2017/9/8.
 */

public class AgainEvaluateActivity extends BaseActivity {

    @BindView(R.id.et_evaluate_detail)
    EditText et_evaluate_detail;
    @BindView(R.id.iv_evaluate_detail)
    ImageView iv_evaluate_detail;
    @BindView(R.id.rb_evaluate_detail_star)
    MaterialRatingBar rb_evaluate_detail_star;
    @BindView(R.id.tv_evaluate_detail_star)
    TextView tv_evaluate_detail_star;
    @BindView(R.id.tv_evaluate_detail_content)
    TextView tv_evaluate_detail_content;
    @BindView(R.id.tv_evaluate_detail_commit)
    TextView tv_evaluate_detail_commit;
    @BindView(R.id.fl_evaluate_detail)
    FlowLayout fl_evaluate_detail;
    @BindView(R.id.rv_evaluate_detail_addimg)
    RecyclerView rv_evaluate_detail_addimg;

    private BaseDividerListItem dividerListItem;

    private int selectImgIndex;
    private BottomSheetDialog selectPhotoDialog;


    private String evaluateId;
    private boolean isLookEvaluate;
    private BaseRecyclerAdapter addImgAdapter;

    @Override
    protected int getContentView() {
        setAppTitle("发表评价");
        return R.layout.act_again_evaluate;
    }

    @Override
    protected void initView() {
        dividerListItem=new BaseDividerListItem(mContext,BaseDividerListItem.HORIZONTAL_LIST,PhoneUtils.dip2px(mContext,10),R.color.white);

        evaluateId = getIntent().getStringExtra(Constant.IParam.evaluateId);
        isLookEvaluate = getIntent().getBooleanExtra(Constant.IParam.lookEvaluate,false);
        if(isLookEvaluate){
            tv_evaluate_detail_commit.setVisibility(View.GONE);
            et_evaluate_detail.setEnabled(false);
        }
    }

    @Override
    protected void initData() {
        showProgress();
        addImg();
        getData();
    }


    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appraise_id", evaluateId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getEvaluateDetail(map, new MyCallBack<EvaluateDetailObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(EvaluateDetailObj obj) {
                Glide.with(mContext).load(obj.getGoods_img()).error(R.color.c_press).into(iv_evaluate_detail);
                rb_evaluate_detail_star.setNumStars(5);
                rb_evaluate_detail_star.setRating(obj.getNumber_stars());
                tv_evaluate_detail_star.setText(obj.getType());
                if(TextUtils.isEmpty(obj.getContent())){
                    tv_evaluate_detail_content.setText("暂无评价");
                }else{
                    tv_evaluate_detail_content.setText(obj.getContent());
                }
                fl_evaluate_detail.removeAllViews();
                if(notEmpty(obj.getImg_list())){
                    for (int i = 0; i < obj.getImg_list().size(); i++) {
                        ImageView imageView=new ImageView(mContext);
                        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.width= PhoneUtils.dip2px(mContext,100);
                        layoutParams.height=PhoneUtils.dip2px(mContext,100);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        Glide.with(mContext).load(obj.getImg_list().get(i)).error(R.color.c_press).into(imageView);
                        fl_evaluate_detail.addView(imageView);
                        int finalI = i;
                        imageView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                Intent intent=new Intent();
                                intent.putStringArrayListExtra(com.sk.jintang.module.orderclass.Constant.IParam.imgList, (ArrayList<String>) obj.getImg_list());
                                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.imgIndex, finalI);
                                STActivity(intent,ImageDetailActivity.class);
                            }
                        });

                    }
                }
                if(isLookEvaluate){
                    if(obj.getAfter_review()!=null){
                        et_evaluate_detail.setText(obj.getAfter_review().getContent());
                        if(notEmpty(obj.getAfter_review().getImg_list())){
                            List<String>list=new ArrayList<>();
                            for (int i = 0; i < obj.getAfter_review().getImg_list().size(); i++) {
                                list.add(obj.getAfter_review().getImg_list().get(i).getImg());
                            }
                            addImgAdapter.setList(list,true);
                        }
                    }
                }
            }
        });




    }

    public void addImg(){
        addImgAdapter = new BaseRecyclerAdapter<String>(mContext, R.layout.item_tuikuan_addimg) {
            @Override
            public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                RecyclerViewHolder itemHolder;
                if(viewType==1){
                    itemHolder = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_tuikuan_addimg, parent, false));
                }else{
                    itemHolder = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_tuikuan_img, parent, false));
                }
                return itemHolder;
            }
            @Override
            public void bindData(RecyclerViewHolder itemHolder, int itemPosition, String bean) {
                if(!isLookEvaluate){
                    itemHolder.itemView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            if(itemPosition==mList.size()){
                                selectImgIndex =-1;
                            }else{
                                selectImgIndex =itemPosition;
                            }
                            addPhoto();
                        }
                    });
                }

                if(getItemViewType(itemPosition)==1){

                }else{
                    ImageView imageView = itemHolder.getImageView(R.id.iv_tuikuan_img);
                    Glide.with(mContext).load(bean).error(R.color.c_press).into(imageView);
                    if(isLookEvaluate){
                        imageView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                Intent intent=new Intent();
                                intent.putStringArrayListExtra(com.sk.jintang.module.orderclass.Constant.IParam.imgList, (ArrayList<String>) addImgAdapter.getList());
                                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.imgIndex, itemPosition);
                                STActivity(intent,ImageDetailActivity.class);
                            }
                        });
                    }


                }
            }
            @Override
            public int getItemViewType(int itemPosition) {
                if(isLookEvaluate){
                    return 0;
                }
                if(itemPosition==mList.size()&&mList.size()<3){
                    return 1;
                }else{
                    return 0;
                }
            }
            @Override
            public int getItemCount() {
                if(isLookEvaluate){
                    return mList==null?0:mList.size();
                }
                if(mList==null){
                    return 0;
                }else if(mList.size()>=3){
                    return mList.size();
                }else{
                    return mList==null?0:mList.size()+1;
                }
            }
        };
        addImgAdapter.setList(new ArrayList());
        rv_evaluate_detail_addimg.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        rv_evaluate_detail_addimg.removeItemDecoration(dividerListItem);
        rv_evaluate_detail_addimg.addItemDecoration(dividerListItem);
        rv_evaluate_detail_addimg.setAdapter(addImgAdapter);
    }

    @OnClick({R.id.tv_evaluate_detail_commit})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_evaluate_detail_commit:
                if(TextUtils.isEmpty(getSStr(et_evaluate_detail))){
                    showMsg("请输入评价内容");
                    return;
                }
                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("确认发布追加评论吗?");
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
                        evaluateCommit();
                    }
                });
                mDialog.create().show();
            break;
        }
    }

    private void evaluateCommit() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("appraise_id",evaluateId);
        map.put("content",getSStr(et_evaluate_detail));
        map.put("sign",GetSign.getSign(map));
        AgainEvaluateItem item=new AgainEvaluateItem();
        List<AgainEvaluateItem.BodyBean> body=new ArrayList<>();
        if(notEmpty(addImgAdapter.getList())){
            for (int i = 0; i < addImgAdapter.getList().size(); i++) {
                AgainEvaluateItem.BodyBean bean=new AgainEvaluateItem.BodyBean();
                bean.setImage_url((String)addImgAdapter.getList().get(i));
                body.add(bean);
            }
        }
        item.setBody(body);
        ApiRequest.againEvaluate(map,item, new MyCallBack<BaseObj>(mContext){
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void addPhoto() {
        showSelectPhotoDialog();
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
//                photoUri = Uri.fromFile(new File(imgSaveName));
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                int currentapiVersion = Build.VERSION.SDK_INT;
                if (currentapiVersion < 24){
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgSaveName)));
                }else{  //解决Android 7.0 相机问题
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(),
                            getApplicationContext().getPackageName()+ ".provider", new File(imgSaveName)));
                }

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
                        if(selectImgIndex ==-1){
                            if(isEmpty(addImgAdapter.getList())){
                                List<String> list=new ArrayList<String>();
                                list.add(obj.getImg());
                                addImgAdapter.setList(list);
                            }else{
                                addImgAdapter.getList().add(obj.getImg());
                            }
                        }else{
                            addImgAdapter.getList().set(selectImgIndex,obj.getImg());
                        }
                        addImgAdapter.notifyDataSetChanged();
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
