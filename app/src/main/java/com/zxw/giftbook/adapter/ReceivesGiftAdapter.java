package com.zxw.giftbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxw.giftbook.Activity.ReceivingGiftItemMoneyListAct;
import com.zxw.giftbook.Activity.entitiy.ReceivesInvitationEntity;
import com.zxw.giftbook.Activity.entitiy.ReceivingGiftEntity;
import com.zxw.giftbook.Activity.menu.ReceivingGIftAffairFragment;
import com.zxw.giftbook.Activity.menu.ReceivingGIftFragment;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pri.zxw.library.base.BaseEntity;
import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;

import static pri.zxw.library.base.MyPullToRefreshBaseFragment.ADD_CHILD_CODE;

/**
 *  收到礼金标题 适配器
 * Created by Administrator on 2016/11/8.
 */

public class ReceivesGiftAdapter extends MyBaseAdapter<ReceivingGiftEntity> {
    List<ReceivingGiftEntity> list;
    LayoutInflater inflater;
    ReceivingGIftFragment mContext;
    public ReceivesGiftAdapter(ReceivingGIftFragment context)
    {
        this.inflater = LayoutInflater.from(context.getContext());
        list=new ArrayList<>();
        mContext=context;
    }
    public ReceivingGiftEntity getItem(int position)
    {
        return list.get(position);
    }


    @Override
    public void removeItem(int position) {
        list.remove(position);
    }

    @Override
    public void addData(ReceivingGiftEntity info) {
        list.add(info);
    }

    @Override
    public void addDataAll(List infos) {
        list.addAll(infos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext.getContext()).inflate(R.layout.item_receiving_gift, parent, false);
        return new ReceivesGiftView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        ReceivesGiftView holder=(ReceivesGiftView)holder1;
        final ReceivingGiftEntity entity=list.get(position);
        holder.title.setText(entity.getTitle());
        holder.typeTv.setText(entity.getReceivestype());
        if(entity.getCreateDate()!=null )
        {
            long dateL=Long.parseLong(entity.getCreateDate());
            holder.dateTv.setText( DateCommon.formatDateTime(new Date( dateL),DateCommon.YYYY_P_MM_P_DD));
        }
        if(entity.getSumMoney()==null||entity.getSumMoney().equals(""))
            holder.moneyTv.setText("0元");
        else
            holder.moneyTv.setText(entity.getSumMoney()+"元");
        holder.numTv.setText(entity.getNum()+"人");
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
            ReceivingGiftEntity entity=getItem(getLayoutPosition());
            Intent intent=new Intent(mContext.getContext(), ReceivingGiftItemMoneyListAct.class);
            intent.putExtra("id",entity.getId());
            intent.putExtra("typeId",entity.getReceivestypeId());
            intent.putExtra("typeName",entity.getReceivestype());
            intent.putExtra("title",entity.getTitle());
            mContext.startActivityForResult(intent,ADD_CHILD_CODE);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
        public ReceivesGiftView(View convertView)
        {
            super(convertView);
            typeTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_type_tv);
            title =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_title_tv);
            dateTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_date_tv);
            moneyTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_money_tv);
            numTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_num_tv);
            rootView=convertView;
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }
        TextView title,moneyTv,numTv,dateTv,typeTv;
        View rootView;
    }


}
