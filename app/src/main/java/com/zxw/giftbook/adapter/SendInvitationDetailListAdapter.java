package com.zxw.giftbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxw.giftbook.Activity.SendInvitationAct;
import com.zxw.giftbook.Activity.SendInvitationListAct;
import com.zxw.giftbook.Activity.entitiy.InvitationlistEntity;
import com.zxw.giftbook.Activity.entitiy.VInvitationListAndGroupEntity;
import com.zxw.giftbook.Activity.entitiy.VSendInvitationEntity;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pri.zxw.library.base.BaseEntity;
import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.DateCommon;

/**
 * 我发送的请帖人员详情 适配器
 *
 */
public class SendInvitationDetailListAdapter extends MyBaseAdapter<VInvitationListAndGroupEntity> {
    private SendInvitationListAct mContext;
    private List<VInvitationListAndGroupEntity> comLists;
    private LayoutInflater inflater = null;
    @Override
    public void addDataAll(List infos) {
        comLists.addAll(infos);
        notifyDataSetChanged();
    }

    @Override
    public void remove() {
        comLists.clear();
    }

    public SendInvitationDetailListAdapter(SendInvitationListAct context) {
        this.mContext = context;
        inflater= LayoutInflater.from(context.getContext());
        comLists=new ArrayList<VInvitationListAndGroupEntity>();
    }
    @Override
    public VInvitationListAndGroupEntity getItem(int position) {
        return comLists.get(position);
    }

    @Override
    public void removeItem(int position) {
        comLists.remove(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public void addData(VInvitationListAndGroupEntity info) {
        comLists.add(info);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_receiving_gift, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        MViewHolder mHolder=(MViewHolder)holder1;
        final VInvitationListAndGroupEntity comInfo =comLists.get(position);
        mHolder.phoneTv.setText(comInfo.getInviteephone());
        mHolder.nameTv.setText(comInfo.getInviteename());
        mHolder.groupTv.setText(comInfo.getGroupname());
    }

    @Override
    public int getItemCount() {
        if(comLists!=null)
            return comLists.size();
        return 0;
    }





    class MViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
        public MViewHolder(View view) {
            super(view);
            lay = (LinearLayout) view.findViewById(R.id.item_send_invitation_detail_lay);
//            mHolder.operateImg=(ImageView)view.findViewById(R.id.item_send_invitation);
            phoneTv = (TextView) view.findViewById(R.id.item_send_invitation_detail_phone_tv);
            nameTv = (TextView) view.findViewById(R.id.item_send_invitation_detail_name_tv);
            groupTv = (TextView) view.findViewById(R.id.item_send_invitation_detail_group_tv);
            rootView=view;
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }
        LinearLayout lay;
       TextView phoneTv,nameTv,groupTv;
        View rootView;
    }

}
