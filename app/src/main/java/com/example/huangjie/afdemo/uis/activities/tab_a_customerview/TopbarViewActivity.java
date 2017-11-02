package com.example.huangjie.afdemo.uis.activities.tab_a_customerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.huangjie.afdemo.R;
import com.example.huangjie.afdemo.uis.activities.tab_a_customerview.widget.SettingItemView;

import net.arvin.afbaselibrary.uis.activities.BaseActivity;
import net.arvin.afbaselibrary.uis.activities.BaseHeaderActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TopbarViewActivity extends BaseHeaderActivity {
    @BindView(R.id.setting_item_view)
    SettingItemView setting_item_view;

    @Override
    public int getContentView() {
        return R.layout.activity_topbar_view;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @OnClick(R.id.setting_item_view)
    public void onSetting_item_viewClicked() {
        setting_item_view.setCheck(!setting_item_view.isCheck());
    }

    @Override
    public String getTitleText() {
        return "TopbarView";
    }
}
