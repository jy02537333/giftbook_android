package com.zxw.giftbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxw.giftbook.Activity.ReceivesInvitationDetailAct;
import com.zxw.giftbook.Activity.entitiy.ReceivesInvitationEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
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
    public ReceivesInvitationEntity getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public void addData(ReceivesInvitationEntity info) {

    }
    @Override
    public void addDataAll(List infos) {
        list.addAll(infos);
        notifyDataSetChanged();

    }

    @Override
    public void removeItem(int position) {
        list.remove(position);
    }

    @Override
    public void remove() {
        list.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_receives_invitation, parent, false);
        return new ReceivesInvitationView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        ReceivesInvitationView holder=(ReceivesInvitationView)holder1;
        ReceivesInvitationEntity entity=list.get(position);
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ReceivesInvitationView extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
//        MyItemClickListener itemClickListener;
//        MyItemLongClickListener longClickListener;
        @Override
        public void onClick(View v) {
            ReceivesInvitationEntity invitationEntity=list.get(getLayoutPosition());
            Intent intent= new Intent(mContext,ReceivesInvitationDetailAct.class);
            intent.putExtra("invitationId",invitationEntity.getId());
            intent.putExtra("inviterId",invitationEntity.getInvitationlistEntityList().get(0).getId());
            mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        public ReceivesInvitationView(View rootView)
        {
            super(rootView);
            ButterKnife.inject(rootView);
            mRootView=rootView;
            mRootView.setOnClickListener(this);
            mRootView.setOnLongClickListener(this);
//            holder.typeTv =
//                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_type_tv);
//            holder.addrTv =
//                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_addr_tv);
//            holder.nameTv =
//                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_name_tv);
//            holder.phoneTv =
//                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_phone_tv);
//            holder.dateTv =
//                    (TextView) convertView.findViewById(R.id.item_list_receives_invitation_date_tv);
//            holder.img =
//                    (ImageView) convertView.findViewById(R.id.item_list_receives_invitation_img);
        }
        @InjectView(R.id.item_list_receives_invitation_name_tv)
        TextView nameTv;
        @InjectView(R.id.item_list_receives_invitation_phone_tv)
        TextView phoneTv;
        @InjectView(R.id.item_list_receives_invitation_addr_tv)
        TextView addrTv;
        @InjectView(R.id.item_list_receives_invitation_date_tv)
        TextView dateTv;
        @InjectView(R.id.item_list_receives_invitation_type_tv)
        TextView typeTv;
        @InjectView(R.id.item_list_receives_invitation_img)
        ImageView img;
        View mRootView;
    }


}
