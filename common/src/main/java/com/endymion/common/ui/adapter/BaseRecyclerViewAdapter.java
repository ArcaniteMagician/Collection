package com.endymion.common.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用的RecyclerView的Adapter基类
 * Created by Jim on 2018/07/30.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mList;
    protected int layoutId;

    public BaseRecyclerViewAdapter(Context context, List<T> mList, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mList = mList;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(layoutId, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        convert(holder, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public abstract void convert(RecyclerViewHolder holder, T data);
}
