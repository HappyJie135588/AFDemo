package com.example.huangjie.afdemo.uis.activities.tab_a_customerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.huangjie.afdemo.R;
import com.example.huangjie.afdemo.uis.activities.tab_a_customerview.widget.TouchPullView;

import net.arvin.afbaselibrary.uis.activities.BaseActivity;
import net.arvin.afbaselibrary.uis.activities.BaseHeaderActivity;

import butterknife.BindView;


public class TouchPullViewActivity extends BaseHeaderActivity {
    private static final float TOUCH_MOVE_MAX_Y = 800;
    private float mTouchMoveStartY = 0;

    @BindView(R.id.fl_root)
    View fl_root;
    @BindView(R.id.touchPull)
    TouchPullView mTouchPullView;

    @Override
    public int getContentView() {
        return R.layout.activity_touch_pull_view;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        fl_root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //得到意图
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchMoveStartY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float y = event.getY();
                        if (y > mTouchMoveStartY) {
                            float moveSize = y - mTouchMoveStartY;
                            float progress = moveSize >= TOUCH_MOVE_MAX_Y ? 1 : moveSize / TOUCH_MOVE_MAX_Y;
                            mTouchPullView.setProgress(progress);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public String getTitleText() {
        return "下拉控件";
    }
}
