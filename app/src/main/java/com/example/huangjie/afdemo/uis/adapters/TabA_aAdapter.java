package com.example.huangjie.afdemo.uis.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huangjie.afdemo.R;
import com.example.huangjie.afdemo.entities.TabA_aEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HuangJie on 2017/11/2.
 */

public class TabA_aAdapter extends BaseAdapterWithHF<TabA_aEntity> {
    public TabA_aAdapter(Context context) {
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
        View view = inflater.inflate(R.layout.item_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseAdapterWithHF.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        TabA_aEntity item = getItemData(position);
        holder.tv_item.setText(item.getName());

    }

    public class ViewHolder extends BaseAdapterWithHF.ViewHolder {
        @BindView(R.id.tv_item)
        TextView tv_item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
