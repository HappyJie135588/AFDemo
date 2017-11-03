package com.example.huangjie.afdemo.uis.activities.tab_b_media_record;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.example.huangjie.afdemo.R;
import com.example.huangjie.afdemo.entities.TabA_bVideoInfoEntity;
import com.example.huangjie.afdemo.uis.adapters.BaseAdapterWithHF;
import com.example.huangjie.afdemo.uis.adapters.TabA_bVideoListAdapter;
import com.example.huangjie.afdemo.utils.CommTools;

import net.arvin.afbaselibrary.uis.activities.BaseHeaderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LocalVideoListActivity extends BaseHeaderActivity implements BaseAdapterWithHF.OnItemClickListener<TabA_bVideoInfoEntity>, TabA_bVideoListAdapter.OnChildClickListener {
    public static int LOCAL_VIDEO_RESULT = 3;

    @BindView(R.id.rv_video)
    public RecyclerView mRecyclerView;
    public TabA_bVideoListAdapter mAdapter;

    public List<TabA_bVideoInfoEntity> infos = new ArrayList<>();

    private Intent lastIntent;

    @Override
    public int getContentView() {
        return R.layout.activity_local_video_list;
    }

    @Override
    public String getTitleText() {
        return "本地视频列表";
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        lastIntent = getIntent();
        mAdapter = new TabA_bVideoListAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnChildClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        initData();
    }

    private void initData() {
        String[] mediaColumns = new String[]{MediaStore.MediaColumns.DATA, BaseColumns._ID, MediaStore.MediaColumns.TITLE, MediaStore.MediaColumns.MIME_TYPE, MediaStore.Video.VideoColumns.DURATION, MediaStore.MediaColumns.SIZE};
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                TabA_bVideoInfoEntity info = new TabA_bVideoInfoEntity();
                info.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)));
                info.setMimeType(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)));
                info.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE)));
                info.setTime(CommTools.LongToHms(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION))));
                info.setSize(CommTools.LongToPoint(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, options);
                info.setBitmap(bitmap);
                infos.add(info);
            } while (cursor.moveToNext());
            mAdapter.refreshDatas(infos);
        }
    }

    @Override
    public void onItemClick(View view, int position, TabA_bVideoInfoEntity tabA_bVideoInfoEntity) {
        String filePath = tabA_bVideoInfoEntity.getFilePath();
        lastIntent.setData(Uri.parse(filePath));
        setResult(LOCAL_VIDEO_RESULT, lastIntent);
        finish();
    }

    @Override
    public void onChildClick(View view, int position, String bpath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        bpath = "file://" + bpath;
        intent.setDataAndType(Uri.parse(bpath), "video/*");
        startActivity(intent);
    }
}