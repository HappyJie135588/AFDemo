package com.example.huangjie.afdemo.uis.activities.tab_a_customerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.huangjie.afdemo.R;

import net.arvin.afbaselibrary.uis.activities.BaseActivity;
import net.arvin.afbaselibrary.uis.activities.BaseHeaderActivity;


public class BezierActivity extends BaseHeaderActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_bezier_view;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public String getTitleText() {
        return "TestBezier";
    }
}
