package com.zxw.giftbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zxw.giftbook.Activity.ReceivingGiftItemMoneyListAct;
import com.zxw.giftbook.Activity.entitiy.ReceivingGiftEntity;
import com.zxw.giftbook.Activity.entitiy.ReceivingGiftsMoneyEntity;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.DateCommon;

/**
 *  收到礼金明细 适配器
 * Created by Administrator on 2016/11/8.
 */

public class ReceivingGiftItemMoneyAdapter extends MyBaseAdapter<ReceivingGiftsMoneyEntity> {
    List<ReceivingGiftsMoneyEntity> list;
    LayoutInflater inflater;
    ReceivingGiftItemMoneyListAct mContext;
    public ReceivingGiftItemMoneyAdapter(ReceivingGiftItemMoneyListAct context)
    {
        this.inflater = LayoutInflater.from(context);
        list=new ArrayList<>();
        mContext=context;
    }
    public ReceivingGiftsMoneyEntity getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public void removeItem(int position) {
        list.remove(position);
    }
    @Override
    public void addDataAll(List infos) {
        list.addAll(infos);
        notifyDataSetChanged();

    }

    @Override
    public void addData(ReceivingGiftsMoneyEntity info) {
        list.add(info);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_receiving_gift_item_money_list, parent, false);
        return new ReceivesGiftView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        ReceivesGiftView holder=(ReceivesGiftView)holder1;
        final ReceivingGiftsMoneyEntity entity=list.get(position);
        holder.nameTv.setText(entity.getGroupmember());
        holder.typeTv.setText(entity.getExpendituretypename());
//        if(entity.getCreateDate()!=null )
//        {
//            long dateL=Long.parseLong(entity.getCreateDate());
//            holder.dateTv.setText( DateCommon.formatDateTime(new Date( dateL),DateCommon.YYYY_P_MM_P_DD));
//        }
        if(entity.getMoney()==null||entity.getMoney().equals(""))
            holder.moneyTv.setText("0元");
        else
            holder.moneyTv.setText(entity.getMoney()+"元");
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.del(entity.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void remove() {
        list.clear();
    }
    class ReceivesGiftView extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
        public ReceivesGiftView(View convertView)
        {
            super(convertView);
            convertView = inflater.inflate(R.layout.item_receiving_gift_item_money_list, null);
            typeTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_gift_item_type_tv);
            nameTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_gift_item_money_name_tv);
            moneyTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_gift_item_money_tv);
            delBtn =
                    (Button) convertView.findViewById(R.id.item_receiving_gift_item_money_del_btn);
            mRootView=convertView;
            mRootView.setOnClickListener(this);
            mRootView.setOnLongClickListener(this);
        }
        TextView nameTv,moneyTv,typeTv;
        Button delBtn;
        View mRootView;
    }


}
