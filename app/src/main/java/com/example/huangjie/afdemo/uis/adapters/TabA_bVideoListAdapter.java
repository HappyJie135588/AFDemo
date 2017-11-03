package com.example.huangjie.afdemo.uis.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huangjie.afdemo.R;
import com.example.huangjie.afdemo.entities.TabA_aEntity;
import com.example.huangjie.afdemo.entities.TabA_bVideoInfoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HuangJie on 2017/11/2.
 */

public class TabA_bVideoListAdapter extends BaseAdapterWithHF<TabA_bVideoInfoEntity> {
    public TabA_bVideoListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getFooterCount() {
        return 0;
    }

    @Override
    public int getHeaderCount() {
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_video_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseAdapterWithHF.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        TabA_bVideoInfoEntity item = getItemData(position);
        holder.iv_video_img.setImageBitmap(item.getBitmap());
        holder.tv_video_title.setText(item.getTitle());
        holder.tv_video_size.setText(item.getSize());
        holder.tv_video_time.setText(item.getTime());

//        holder.iv_video_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                String bpath = "file://" + item.getFilePath();
//                intent.setDataAndType(Uri.parse(bpath), "video/*");
//                context.startActivity(intent);
//            }
//        });

    }

    public interface OnChildClickListener {
        void onChildClick(View view, int position, String bpath);
    }

    private OnChildClickListener onChildClickListener;

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public class ViewHolder extends BaseAdapterWithHF.ViewHolder {
        @BindView(R.id.iv_video_img)
        ImageView iv_video_img;
        @BindView(R.id.tv_video_title)
        TextView tv_video_title;
        @BindView(R.id.tv_video_size)
        TextView tv_video_size;
        @BindView(R.id.tv_video_time)
        TextView tv_video_time;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            iv_video_img.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_video_img) {
                int positon = getAdapterPosition();
                onChildClickListener.onChildClick(v, positon, getItemData(positon).getFilePath());
            } else {
                super.onClick(v);
            }
        }
    }
}
