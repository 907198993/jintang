package com.sk.jintang.module.my.activity;

import android.Manifest;
import android.content.DialogInterface;
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
import android.text.Editable;
import android.text.TextWatcher;
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
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.view.MyDialog;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseDividerListItem;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.request.FaBiaoPingJiaBodyItem;
import com.sk.jintang.module.my.network.request.UploadImgItem;
import com.sk.jintang.module.my.network.response.FaBiaoPingJiaObj;
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

public class FaBiaoEvaluateActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_fa_biao_pingjia)
    RecyclerView rv_fa_biao_pingjia;

    LoadMoreAdapter adapter;
    private String orderNo;
    private int selectImgIndex, selectImgItemIndex;
    private BottomSheetDialog selectPhotoDialog;
    List<String>imgList=new ArrayList<>();
    @Override
    protected int getContentView() {
        setAppTitle("发表评价");
        return R.layout.act_fa_biao_evaluate;
    }
    private BaseDividerListItem dividerListItem;
    private BaseDividerListItem listItem;
    @Override
    protected void initView() {
        dividerListItem=new BaseDividerListItem(mContext,BaseDividerListItem.HORIZONTAL_LIST,PhoneUtils.dip2px(mContext,10),R.color.white);
        listItem=getItemDivider(PhoneUtils.dip2px(mContext,10));
        orderNo = getIntent().getStringExtra(Constant.IParam.orderNo);
        adapter=new LoadMoreAdapter<FaBiaoPingJiaObj>(mContext,R.layout.item_fa_biao_ping_jia,0) {
            @Override
            public LoadMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LoadMoreViewHolder holder = super.onCreateViewHolder(parent, viewType);
                EditText editText = holder.getEditText(R.id.et_fa_biao_content);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        getList().get(holder.getAdapterPosition()).setContent(editable.toString());
                    }
                });

                TextView tv_fa_biao_pingjia = holder.getTextView(R.id.tv_fa_biao_pingjia);
                MaterialRatingBar rb_fa_biao_pingjia = (MaterialRatingBar) holder.getView(R.id.rb_fa_biao_pingjia);
                rb_fa_biao_pingjia.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                    @Override
                    public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                            if(rating==1||rating==0){
                                tv_fa_biao_pingjia.setText("差");
                            }else if(rating==2||rating==3){
                                tv_fa_biao_pingjia.setText("一般");
                            }else{
                                tv_fa_biao_pingjia.setText("好");
                            }
                            mList.get(holder.getAdapterPosition()).setStars_num((int)rating);
                    }
                });


                return holder;
            }

            @Override
            public void bindData(LoadMoreViewHolder holder,final int position, FaBiaoPingJiaObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_fa_biao_pingjia);
                Glide.with(mContext).load(bean.getGoods_images()).error(R.color.c_press).into(imageView);

                TextView tv_fa_biao_pingjia = holder.getTextView(R.id.tv_fa_biao_pingjia);
                if(bean.getStars_num()<=1){
                    tv_fa_biao_pingjia.setText("差");
                }else if(bean.getStars_num()>1&&bean.getStars_num()<=3){
                    tv_fa_biao_pingjia.setText("一般");
                }else{
                    tv_fa_biao_pingjia.setText("好");
                }

                MaterialRatingBar rb_fa_biao_pingjia = (MaterialRatingBar) holder.getView(R.id.rb_fa_biao_pingjia);
                rb_fa_biao_pingjia.setRating(bean.getStars_num());


                EditText et_fa_biao_content = holder.getEditText(R.id.et_fa_biao_content);
                et_fa_biao_content.setText(getList().get(position).getContent());

                RecyclerView rv_fa_biao_addimg = (RecyclerView) holder.getView(R.id.rv_fa_biao_addimg);
                rv_fa_biao_addimg.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                rv_fa_biao_addimg.removeItemDecoration(dividerListItem);
                rv_fa_biao_addimg.addItemDecoration(dividerListItem);

                BaseRecyclerAdapter addImgAdapter=new BaseRecyclerAdapter<String>(mContext,R.layout.item_tuikuan_addimg) {
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
                        itemHolder.itemView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                if(itemPosition==mList.size()){
                                    selectImgItemIndex =-1;
                                    selectImgIndex =position;
                                }else{
                                    selectImgIndex =position;
                                    selectImgItemIndex =itemPosition;
                                }
                                addPhoto();
                            }
                        });
                        if(getItemViewType(itemPosition)==1){

                        }else{
                            ImageView imageView = itemHolder.getImageView(R.id.iv_tuikuan_img);
                            Glide.with(mContext).load(bean).error(R.color.c_press).into(imageView);
                        }
                    }
                    @Override
                    public int getItemViewType(int itemPosition) {
                        if(itemPosition==mList.size()&&mList.size()<3){
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
                addImgAdapter.setList(bean.getImage_list());
                rv_fa_biao_addimg.setAdapter(addImgAdapter);
            }
        };

        rv_fa_biao_pingjia.setLayoutManager(new LinearLayoutManager(mContext));
        rv_fa_biao_pingjia.removeItemDecoration(listItem);
        rv_fa_biao_pingjia.addItemDecoration(listItem);
        rv_fa_biao_pingjia.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    private void getData(int page,boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",orderNo);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getPingJiaImg(map, new MyCallBack<List<FaBiaoPingJiaObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<FaBiaoPingJiaObj> list) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(list,true);
                }else{
                    pageNum=2;
                    adapter.setList(list,true);
                }
            }
        });
    }
    @OnClick({R.id.tv_fbpj_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_fbpj_commit:
                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认发布评价?");
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
                        pingJiaCommit();
                    }
                });
                mDialog.create().show();
            break;
        }
    }

    private void pingJiaCommit() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",orderNo);
        map.put("sign",GetSign.getSign(map));
        FaBiaoPingJiaBodyItem item=new FaBiaoPingJiaBodyItem();
        item.setBody(adapter.getList());
        ApiRequest.pingJia(map,item, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
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
                        Log.i("=========",selectImgIndex+"========="+selectImgItemIndex);
                        if(selectImgItemIndex ==-1){
                            if(isEmpty(((FaBiaoPingJiaObj)adapter.getList().get(selectImgIndex)).getImage_list())){
                                //.setImage_list(list);
                                List<String> list=new ArrayList<String>();
                                list.add(obj.getImg());
                                ((FaBiaoPingJiaObj)adapter.getList().get(selectImgIndex)).setImage_list(list);
                            }else{
                                ((FaBiaoPingJiaObj)adapter.getList().get(selectImgIndex)).getImage_list().add(obj.getImg());
                            }
                        }else{
                            ((FaBiaoPingJiaObj)adapter.getList().get(selectImgIndex)).getImage_list().set(selectImgItemIndex,obj.getImg());
                        }
                        adapter.notifyDataSetChanged();
//                        adapter.setList(imgList,true);
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
