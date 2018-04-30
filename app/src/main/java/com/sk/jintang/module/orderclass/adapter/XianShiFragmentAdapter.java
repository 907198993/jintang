package com.sk.jintang.module.orderclass.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sk.jintang.module.orderclass.fragment.XianShiNewFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class XianShiFragmentAdapter extends FragmentStatePagerAdapter {
    List<XianShiNewFragment> list;

    public XianShiFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<XianShiNewFragment> list) {
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }
}
