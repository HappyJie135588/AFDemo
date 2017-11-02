package com.example.huangjie.afdemo.uis.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huangjie.afdemo.R;
import com.example.huangjie.afdemo.uis.fragments.taba.TabA_aFragment;
import com.example.huangjie.afdemo.uis.fragments.taba.TabA_bFragment;
import com.flyco.tablayout.SlidingTabLayout;

import net.arvin.afbaselibrary.uis.fragments.BaseFragment;
import net.arvin.afbaselibrary.uis.fragments.BaseHeaderFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabAFragment extends BaseHeaderFragment {

    @BindView(R.id.stl_taba)
    public SlidingTabLayout sts_taba;
    @BindView(R.id.vp_taba)
    public ViewPager vp_taba;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "自定义", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源"
    };
    private TabAPagerAdapter mAdapter;

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_a;
    }

    @Override
    public boolean isShowBackView() {
        return false;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mFragments.add(new TabA_aFragment());
        mFragments.add(new TabA_bFragment());
        mFragments.add(new TabA_aFragment());
        mFragments.add(new TabA_bFragment());
        mFragments.add(new TabA_aFragment());
        mFragments.add(new TabA_bFragment());
        mFragments.add(new TabA_aFragment());
        mAdapter = new TabAPagerAdapter(getAFContext().getSupportFragmentManager());
        vp_taba.setAdapter(mAdapter);
        sts_taba.setViewPager(vp_taba);


    }

    @Override
    public String getTitleText() {
        return "TabA";
    }

    class TabAPagerAdapter extends FragmentPagerAdapter {

        public TabAPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
