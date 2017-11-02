package com.example.huangjie.afdemo.uis.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.huangjie.afdemo.R;

import net.arvin.afbaselibrary.uis.fragments.BaseHeaderFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabBFragment extends BaseHeaderFragment {

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_b;
    }
    @Override
    public boolean isShowBackView() {
        return false;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public String getTitleText() {
        return "TabB";
    }
}
