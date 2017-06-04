package com.zxw.giftbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxw.giftbook.Activity.GroupMemberAct;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.Activity.entitiy.ReceivesInvitationEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;

/**
 *  收到请帖适配器
 * Created by Administrator on 2016/11/8.
 */

public class ReceivesInvitationAdapter extends MyBaseAdapter<ReceivesInvitationEntity> {
    List<ReceivesInvitationEntity> list;
    LayoutInflater inflater;
    Context mContext;
    public ReceivesInvitationAdapter(Context context)
    {
        this.inflater = LayoutInflater.from(context);
        list=new ArrayList<>();
        mContext=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    public ReceivesInvitationEntity getItem(int position)
    {
        return list.get(position);
    }
    @Override
    public void addDataAll(List<ReceivesInvitationEntity> infos) {
        list.addAll(infos);
        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReceivesInvitationView holder = null;
        if(convertView==null)
        {
            holder=new ReceivesInvitationView();
            convertView = inflater.inflate(R.layout.item_list_receives_invitation, null);
            holder.typeTv =
                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_type_tv);
            holder.addrTv =
                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_addr_tv);
            holder.nameTv =
                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_name_tv);
            holder.phoneTv =
                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_phone_tv);
            holder.dateTv =
                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_date_tv);
            holder.img =
                    (ImageView) convertView.findViewById(R.id.item_list_receives_invitation_img);
            convertView.setTag(holder);
        }else
            holder= (ReceivesInvitationView)convertView.getTag();
       final ReceivesInvitationEntity entity=list.get(position);
        holder.nameTv.setText(entity.getInvitername());
        holder.phoneTv.setText("["+entity.getInviterphone()+"]");
        Date date=new Date(entity.getFeastdate());
        if( DateCommon.gainCurrentDate().getYear()==date.getYear())
        {
            holder.dateTv.setText( DateCommon.formatDateTime( date,DateCommon.MM_DD_HH_MM_SS));
        }else
        {
            holder.dateTv.setText( DateCommon.formatDateTime( date,DateCommon.YYYY_MM_DD_HH_MM_SS));
        }
        holder.addrTv.setText(entity.getFeastaddress());
        holder.typeTv.setText(entity.getFeasttype());
        MyImgLoadTool.loadNetHeadImg(mContext, FtpApplication.getInstance().getUser().getPortrait(),
                holder.img,80,80,"ReceivesInvitationAdapter");
        return super.getView(position, convertView, parent);
    }

    @Override
    public void remove() {
        list.clear();
    }
    class ReceivesInvitationView
    {
        TextView nameTv,phoneTv,addrTv,dateTv,typeTv;
        ImageView img;
    }


}
