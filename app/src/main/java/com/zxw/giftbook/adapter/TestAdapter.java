package com.zxw.giftbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import pri.zxw.library.base.BaseEntity;
import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.entity.ComInfo;

/**
 * Created by lenovo on 2016-07-15.
 */
public class TestAdapter extends MyBaseAdapter<ComInfo> {

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public void addData(ComInfo info) {

    }

    @Override
    public void addDataAll(List<? extends BaseEntity> infos) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void removeItem(int position) {
    }

    @Override
    public void remove() {

    }
}
