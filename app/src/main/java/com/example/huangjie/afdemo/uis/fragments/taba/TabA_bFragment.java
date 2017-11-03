package com.example.huangjie.afdemo.uis.fragments.taba;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.VideoView;

import com.example.huangjie.afdemo.R;
import com.example.huangjie.afdemo.uis.activities.tab_b_media_record.LocalVideoListActivity;
import com.example.huangjie.afdemo.uis.activities.tab_b_media_record.MediaRecorderActivity;

import net.arvin.afbaselibrary.uis.fragments.BaseHeaderFragment;
import net.arvin.afbaselibrary.uis.helpers.IBaseContact;
import net.arvin.afbaselibrary.utils.AFLog;

import java.security.Permission;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.example.huangjie.afdemo.uis.activities.tab_b_media_record.LocalVideoListActivity.LOCAL_VIDEO_RESULT;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabA_bFragment extends BaseHeaderFragment {
    static final int REQUEST_VIDEO_CAPTURE = 1;
    @BindView(R.id.videoview)
    public VideoView mVideoView;

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_a_b;
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
        return "TabA_b";
    }

    @OnClick(R.id.btn_sysrecord)
    public void btn_sysrecord() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getAFContext().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @OnClick(R.id.btn_customerrecord)
    public void btn_customerrecord() {
        startActivity(MediaRecorderActivity.class);
    }

    @OnClick(R.id.btn_sysvideolist)
    public void btn_sysvideolist() {
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        //intent.setType("image/*");
        // intent.setType("audio/*"); //选择音频
        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）

        // intent.setType("video/*;image/*");//同时选择视频和图片

        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(intent, 3);
    }

    @OnClick(R.id.btn_customervideolist)
    public void btn_customervideolist() {
        requestPermission(new IBaseContact.IRequestPermissionCallback(){

            @Override
            public void agreeAll() {
                AFLog.d("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                startActivityForResult(LocalVideoListActivity.class, LOCAL_VIDEO_RESULT);
            }
        },"缺少读文件权限", Manifest.permission.READ_EXTERNAL_STORAGE);
    }



    @OnClick(R.id.btn_play)
    public void btn_play() {
        showToast("开始播发");
        mVideoView.start();//播放视频
        mVideoView.requestFocus();//播放窗口为当前窗口
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            AFLog.d("地址:" + videoUri.toString());
            showToast(videoUri.toString());
            mVideoView.setVideoURI(videoUri);
            mVideoView.start();//程序运行时自动开始播放视频
            mVideoView.requestFocus();//播放窗口为当前窗口
        } else if (requestCode == MediaRecorderActivity.REQUEST_CUSTOMER_VIDEO_CAPTURE) {
            Uri videoUri = data.getData();
            AFLog.d("地址:" + videoUri.toString());
            showToast(videoUri.toString());
            mVideoView.setVideoURI(videoUri);
            mVideoView.start();//程序运行时自动开始播放视频
            mVideoView.requestFocus();//播放窗口为当前窗口
        } else if (requestCode == LOCAL_VIDEO_RESULT) {
            Uri videoUri = data.getData();
            AFLog.d("地址:" + videoUri.toString());
            showToast(videoUri.toString());
            mVideoView.setVideoURI(videoUri);
            mVideoView.start();//程序运行时自动开始播放视频
            mVideoView.requestFocus();//播放窗口为当前窗口
        }
    }
}
