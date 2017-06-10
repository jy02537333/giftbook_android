package com.zxw.giftbook.adapter;

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

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.DateCommon;

/**
 * 我发送的请帖人员详情 适配器
 *
 */
public class SendInvitationDetailListAdapter extends MyBaseAdapter {
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
    public int getCount() {
        if(comLists!=null)
            return comLists.size();
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_send_invitation_detail_list, null);
            mHolder = new ViewHolder();
            mHolder.lay = (LinearLayout) view.findViewById(R.id.item_send_invitation_detail_lay);
//            mHolder.operateImg=(ImageView)view.findViewById(R.id.item_send_invitation);
            mHolder.phoneTv = (TextView) view.findViewById(R.id.item_send_invitation_detail_phone_tv);
            mHolder.nameTv = (TextView) view.findViewById(R.id.item_send_invitation_detail_name_tv);
            mHolder.groupTv = (TextView) view.findViewById(R.id.item_send_invitation_detail_group_tv);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }
       final VInvitationListAndGroupEntity comInfo =comLists.get(position);
        mHolder.phoneTv.setText("("+comInfo.getInviteephone()+")");
        mHolder.nameTv.setText("("+comInfo.getInviteename()+")");
        mHolder.groupTv.setText("("+comInfo.getGroupname()+")");
        return view;
    }




    class ViewHolder {
        LinearLayout lay;
       TextView phoneTv,nameTv,groupTv;
    }

}
