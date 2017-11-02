package com.example.huangjie.afdemo.uis.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongli on 2017/2/7 09:05
 * email: lc824767150@163.com
 */

public abstract class BaseAdapterWithHF<T> extends RecyclerView.Adapter<BaseAdapterWithHF.ViewHolder> {
    protected final static int TYPE_CONTENT = 0;
    protected final static int TYPE_HEADER = 1;
    protected final static int TYPE_FOOTER = 2;

    protected List<T> datas;
    protected LayoutInflater inflater;
    protected Context context;

    public BaseAdapterWithHF(Context context) {
        this.datas = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 向Adapter添加数据
     *
     * @param datas items
     */
    public void addDatas(List<? extends T> datas) {
        if (datas != null) {
            this.datas.addAll(datas);
            notifyDataSetChanged();
        } else {
            Log.e("BaseAdapterWithHF", "addDatas(List<T> datas) has null parameter");
        }
    }

    /**
     * 向Adapter添加数据
     *
     * @param data item
     */
    public void addData(T data) {
        if (data != null) {
            this.datas.add(data);
            notifyDataSetChanged();
        } else {
            Log.e("BaseAdapterWithHF", "addDatas(List<T> datas) has null parameter");
        }
    }

    /**
     * Adapter刷新数据
     *
     * @param datas items
     */
    public void refreshDatas(List<? extends T> datas) {
        if (datas != null) {
            this.datas.clear();
            addDatas(datas);
        } else {
            Log.e("BaseAdapterWithHF", "refreshDatas(List<T> datas) has null parameter");
        }
    }

    public void clearDatas() {
        this.datas.clear();
        notifyDataSetChanged();
    }

    /**
     * Adapter刷新数据
     *
     * @param data item数据
     */
    public void refreshData(T data) {
        if (data != null) {
            this.datas.clear();
            addData(data);
        } else {
            Log.e("BaseAdapterWithHF", "refreshDatas(List<T> datas) has null parameter");
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size() + getHeaderCount() + getFooterCount();
    }

    public abstract int getFooterCount();

    public abstract int getHeaderCount();

    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderCount()) {
            return TYPE_HEADER;
        }
        if (position >= getItemCount() - getFooterCount()) {
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
    }

    /**
     * 获取指定位置的数据
     *
     * @param position position
     * @return item
     */
    public T getItemData(int position) {
        if (position >= 0 && datas.size() > position - getHeaderCount()) {
            return datas.get(position - getHeaderCount());
        } else {
            return null;
        }
    }


    /**
     * 删除指定位置的数据
     *
     * @param position position
     */
    public void deleteItemData(int position) {
        deleteItemData(getItemData(position));
    }

    /**
     * 删除指定数据
     *
     * @param t item
     */
    public void deleteItemData(T t) {
        if (t != null) {
            datas.remove(t);
        }
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return datas;
    }

    public interface OnItemClickListener<K> {
        void onItemClick(View view, int position, K k);
    }

    public interface OnItemLongClickListener<K> {
        void onItemLongClick(View view, int position, K k);
    }

    private OnItemClickListener<T> onItemClickListener;
    private OnItemLongClickListener<T> onItemLongClickListener;

    public OnItemClickListener<T> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener<T> getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    //    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
//        this.onItemLongClickListener = onItemLongClickListener;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int positon = getAdapterPosition();
                onItemClickListener.onItemClick(v, positon, getItemData(positon));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                int positon = getAdapterPosition();
                onItemLongClickListener.onItemLongClick(v, positon, getItemData(positon));
            }
            return true;
        }
    }
}
