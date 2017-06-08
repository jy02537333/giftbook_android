package com.zxw.giftbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxw.giftbook.Activity.entitiy.ReceivesInvitationEntity;
import com.zxw.giftbook.Activity.entitiy.ReceivingGiftEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;

/**
 *  收到礼金标题 适配器
 * Created by Administrator on 2016/11/8.
 */

public class ReceivesGiftAdapter extends MyBaseAdapter<ReceivingGiftEntity> {
    List<ReceivingGiftEntity> list;
    LayoutInflater inflater;
    Context mContext;
    public ReceivesGiftAdapter(Context context)
    {
        this.inflater = LayoutInflater.from(context);
        list=new ArrayList<>();
        mContext=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    public ReceivingGiftEntity getItem(int position)
    {
        return list.get(position);
    }
    @Override
    public void addDataAll(List<ReceivingGiftEntity> infos) {
        list.addAll(infos);
        notifyDataSetChanged();

    }


    @Override
    public void removeItem(int position) {
        list.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReceivesGiftView holder = null;
        if(convertView==null)
        {
            holder=new ReceivesGiftView();
            convertView = inflater.inflate(R.layout.item_receiving_gift, null);
            holder.typeTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_type_tv);
            holder.title =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_title_tv);
            holder.dateTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_date_tv);
            holder.moneyTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_money_tv);
            holder.numTv =
                    (TextView) convertView.findViewById(R.id.item_receiving_igift_num_tv);
            convertView.setTag(holder);
        }else
            holder= (ReceivesGiftView)convertView.getTag();
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
        return super.getView(position, convertView, parent);
    }

    @Override
    public void remove() {
        list.clear();
    }
    class ReceivesGiftView
    {
        TextView title,moneyTv,numTv,dateTv,typeTv;
    }


}
