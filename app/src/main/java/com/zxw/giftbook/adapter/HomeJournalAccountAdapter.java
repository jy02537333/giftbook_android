package com.zxw.giftbook.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.zxw.giftbook.Activity.entitiy.MembergiftmoneyEntity;
import com.zxw.giftbook.Activity.menu.HomeFragment;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pri.zxw.library.base.BaseFragment;
import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;

/**
 * 首页礼金记录适配器
 * Createdy 张相伟
 * 2016/11/1.
 */
public class HomeJournalAccountAdapter extends MyBaseAdapter<MembergiftmoneyEntity> {
    List<MembergiftmoneyEntity> list;
    LayoutInflater inflater;
    BaseFragment mContext;


    public HomeJournalAccountAdapter(BaseFragment context)
    {
        this.inflater = LayoutInflater.from(context.getActivity());
        list=new ArrayList<>();
        mContext=context;
    }

    @Override
    public void addData(MembergiftmoneyEntity info) {
        list.add(info);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext.getActivity()).inflate(R.layout.item_list_f_home_journal_account, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        MViewHolder holder=(MViewHolder)holder1;
        MembergiftmoneyEntity entity=list.get(position);
        if(entity.getIsexpenditure().equals("1"))
        {
            ImgLoadMipmapTool.load(R.mipmap.minus_alt ,holder.item_list_f_home_journal_account_alt_img);
        }else{
            ImgLoadMipmapTool.load(R.mipmap.plus_alt ,holder.item_list_f_home_journal_account_alt_img);
        }

        String date= DateCommon.dateToYYYY_P_MM_P_dd(entity.getCreateDate());
        holder.item_list_f_home_journal_account_date_tv.setText(date);
        holder.item_list_f_home_journal_account_fee_tv.setText(entity.getMoney()+"");
        holder.item_list_f_home_journal_account_name_tv.setText(entity.getGroupmember());
        holder.item_list_f_home_journal_account_type_tv.setText(entity.getExpendituretypename());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addDataAll(List infos) {
        if(list!=null)
            list.addAll(infos);
    }

    @Override
    public void removeItem(int position) {
        list.remove(position);
    }

    @Override
    public void remove() {
        if(list!=null)
            list.clear();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public MViewHolder(View rootView)
        {
            super( rootView);
            ButterKnife.inject(this, rootView);
            mRootView=rootView;
            mRootView.setOnClickListener(this);
            mRootView.setOnLongClickListener(this);
        }
        @InjectView(R.id.item_list_f_home_journal_account_alt_img)
        ImageView item_list_f_home_journal_account_alt_img;
        @InjectView(R.id.item_list_f_home_journal_account_fee_tv)
        TextView   item_list_f_home_journal_account_fee_tv;
        @InjectView(R.id.item_list_f_home_journal_account_name_tv)
        TextView  item_list_f_home_journal_account_name_tv;
        @InjectView(R.id.item_list_f_home_journal_account_type_tv)
        TextView  item_list_f_home_journal_account_type_tv;
        @InjectView(R.id.item_list_f_home_journal_account_date_tv)
        TextView  item_list_f_home_journal_account_date_tv;
        View mRootView;
        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}

