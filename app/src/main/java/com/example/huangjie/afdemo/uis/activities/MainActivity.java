package com.example.huangjie.afdemo.uis.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.example.huangjie.afdemo.R;
import net.arvin.afbaselibrary.entities.TabEntity;
import com.example.huangjie.afdemo.uis.fragments.TabAFragment;
import com.example.huangjie.afdemo.uis.fragments.TabBFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import net.arvin.afbaselibrary.uis.activities.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_main)
    public FrameLayout fl_main;
    @BindView(R.id.ctl_main)
    public CommonTabLayout ctl_main;

    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private int[] mIconUnselectIds = {
            R.drawable.tab_home_unselect, R.drawable.tab_speech_unselect,
            R.drawable.tab_contact_unselect, R.drawable.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.drawable.tab_home_select, R.drawable.tab_speech_select,
            R.drawable.tab_contact_select, R.drawable.tab_more_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mFragments.add(new TabAFragment());
        mFragments.add(new TabBFragment());
        mFragments.add(new TabAFragment());
        mFragments.add(new TabBFragment());
        ctl_main.setTabData(mTabEntities,this,R.id.fl_main,mFragments);
        //mTabLayout_2.setMsgMargin(0, -5, 5);
        ctl_main.setMsgMargin(1, -10, 10);
        ctl_main.setMsgMargin(2, -10, 10);
        ctl_main.setMsgMargin(3, -10, 10);
        initData();
    }

    private void initData() {

        ctl_main.showDot(0);
        ctl_main.showMsg(1, 5);
        ctl_main.showMsg(2, 55);
        ctl_main.showMsg(3, 100);
    }
}
