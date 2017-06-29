package com.zxw.giftbook.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxw.giftbook.Activity.MySendInvitationAct;
import com.zxw.giftbook.Activity.SendInvitationListAct;
import com.zxw.giftbook.Activity.entitiy.VSendInvitationEntity;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.nereo.multi_image_selector.bean.Image;
import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.ImgLoad.ImgUrlUtil;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;

/**
 * 我发送的请帖 适配器
 *
 */
public class SendInvitationAdapter extends MyBaseAdapter<VSendInvitationEntity> {
    private MySendInvitationAct mContext;
    private List<VSendInvitationEntity> comLists;
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

//    public SendInvitationAdapter(SendInvitationAct context) {
//        this.mContext = context;
//        inflater= LayoutInflater.from(context.getContext());
//        comLists=new ArrayList<VSendInvitationEntity>();
//    }
    public SendInvitationAdapter(MySendInvitationAct context) {
        this.mContext = context;
        inflater= LayoutInflater.from(context.getContext());
        comLists=new ArrayList<VSendInvitationEntity>();
    }

    @Override
    public void addData(VSendInvitationEntity info) {
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
        final VSendInvitationEntity comInfo =comLists.get(position);
        long dateL=Long.parseLong(comInfo.getFeastdate());
        mHolder.dateTv.setText(DateCommon.formatDateTime(new Date( dateL),DateCommon.YYYY_P_MM_P_DD));
        mHolder.numTv.setText("邀请"+comInfo.getNum()+"人");
        mHolder.addrTv.setText(comInfo.getFeastaddress());
        mHolder.typeTv.setText(comInfo.getFeasttype());
        String imgUrl= ImgUrlUtil.getFullImgUrl(comInfo.getCoverimg());
        MyImgLoadTool.loadNetHeadThumbnailImg(mContext,imgUrl,mHolder.img,60,60,"");
    }

    @Override
    public int getItemCount() {
        if(comLists!=null)
            return comLists.size();
        return 0;
    }


    @Override
    public VSendInvitationEntity getItem(int position) {
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




    class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        @Override
        public void onClick(View v) {
            VSendInvitationEntity invitationEntity= getItem(getLayoutPosition());
            Intent intent= new Intent(mContext,SendInvitationListAct.class);
            intent.putExtra("parentId",invitationEntity.getId());
            mContext.startActivity(intent);

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
        public MViewHolder(View view) {
            super(view);
            lay = (LinearLayout) view.findViewById(R.id.item_send_invitation_lay);
//            mHolder.operateImg=(ImageView)view.findViewById(R.id.item_send_invitation);
            dateTv = (TextView) view.findViewById(R.id.item_send_invitation_date_tv);
            numTv = (TextView) view.findViewById(R.id.item_send_invitation_num_tv);
            addrTv = (TextView) view.findViewById(R.id.item_send_invitation_addr_tv);
            typeTv = (TextView) view.findViewById(R.id.item_send_invitation_type_tv);
            img = (ImageView) view.findViewById(R.id.item_send_invitation_img);
            rootView=view;
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }
        LinearLayout lay;
        ImageView img;
       TextView dateTv,typeTv,addrTv;
        TextView numTv;
        View rootView;
    }

}
