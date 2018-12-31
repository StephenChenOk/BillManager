package com.chen.fy.mytext.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.chen.fy.mytext.billsApp.MainActivity;
import com.chen.fy.mytext.fragment.DetailFragmentTest;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;

    private String data = MainActivity.date[0];

    private FragmentManager fm;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int i) {  //只在第一次初始化时调用
        Fragment fragment = null;
        fragment = MainActivity.fragments[i % MainActivity.fragments.length];
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        //得到缓存的fragment
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        //得到tag ❶
        String fragmentTag = fragment.getTag();
        if (MainActivity.fragmentsUpdateFlag[position % MainActivity.fragmentsUpdateFlag.length]) {
            Log.d("chen", "更新!!!\n");
            //如果这个fragment需要更新
            FragmentTransaction ft = fm.beginTransaction();
            //移除旧的fragment
            ft.remove(fragment);
            //换成新的fragment
            DetailFragmentTest detailFragmentTest = new DetailFragmentTest();
            fragment = detailFragmentTest;
            MainActivity.fragments[position % MainActivity.fragments.length] = detailFragmentTest;
            //添加新fragment时必须用前面获得的tag ❶
            ft.add(container.getId(), fragment, fragmentTag);
            ft.attach(fragment);
            ft.commit();
            //复位更新标志
            MainActivity.fragmentsUpdateFlag[position % MainActivity.fragmentsUpdateFlag.length] = false;
        }
        return fragment;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;  //返回PagerAdapter.POSITION_NONE保证调用notifyDataSetChanged刷新Fragment。
    }

}
