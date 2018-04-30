package com.sk.jintang.shop;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.baseclass.activity.IBaseActivity;
import com.sk.jintang.R;
import com.sk.jintang.view.ProgressLayout;


public abstract class BaseActivity extends IBaseActivity  implements ProgressLayout.OnAgainInter,View.OnClickListener{
	protected Context mContext = this;
	protected ProgressLayout pl_load;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getContentView()!=0){
			setContentView(getContentView());
		}
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("");
		setSupportActionBar(toolbar);
		mContext=this;
		if(null!=findViewById(R.id.pl_load)){
			pl_load = (ProgressLayout) findViewById(R.id.pl_load);
			pl_load.setInter(this);
		}
		initView();
		initData();
	}
	protected abstract int getContentView();
	protected abstract void initView();
	protected abstract void initData();

	public void close(View view) {
		finish();
	}

	public void goAccount(View view) {
	}

	@Override
	public void again() {
		initData();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	public void showProgress(){
		if (pl_load != null) {
			pl_load.showProgress();
		}
	}
	public void showContent(){
		if (pl_load != null) {
			pl_load.showContent();
		}
	}
	public void showErrorText(){
		if (pl_load != null) {
			pl_load.showErrorText();
		}
	}

}
