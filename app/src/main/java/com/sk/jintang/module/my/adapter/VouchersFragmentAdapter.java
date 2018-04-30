package com.sk.jintang.module.my.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by administartor on 2017/8/8.
 */

public class VouchersFragmentAdapter extends FragmentStatePagerAdapter {
    String[]title={"未使用","已使用","已过期"};
    List<Fragment> list;

    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public VouchersFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
//        return super.getPageTitle(position);
    }
}
