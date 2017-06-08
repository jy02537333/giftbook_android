package com.zxw.giftbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zxw.giftbook.Activity.GroupMemberAct;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.base.MyBaseAdapter;

/**
 *  组成员名单
 * Created by Administrator on 2016/11/8.
 */

public class GroupMemberAdapter extends MyBaseAdapter<GroupmemberEntity> {
    List<GroupmemberEntity> list;
    LayoutInflater inflater;
    GroupMemberAct mContext;
    public GroupMemberAdapter(GroupMemberAct context)
    {
        this.inflater = LayoutInflater.from(context);
        list=new ArrayList<>();
        mContext=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    public GroupmemberEntity getItem(int position)
    {
        return list.get(position);
    }
    @Override
    public void addDataAll(List<GroupmemberEntity> infos) {
        list.addAll(infos);
        notifyDataSetChanged();

    }

    @Override
    public void removeItem(int position) {
        list.remove(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GroupMemberView holder = null;
        if(convertView==null)
        {
            holder=new GroupMemberView();
            convertView = inflater.inflate(R.layout.item_list_group_member_list, null);
            holder.affiliatedPersonTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_affiliated_person_tv);
            holder.giftMoneyTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_gift_money_tv);
            holder.nameTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_name_tv);
            holder.phoneTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_phone_tv);
            holder.phoneTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_phone_tv);
            holder.operateBtn =
                    (Button) convertView.findViewById(R.id.item_list_group_member_list_gift_operate_btn);
            convertView.setTag(holder);
        }else
            holder= (GroupMemberView)convertView.getTag();
       final GroupmemberEntity entity=list.get(position);
        holder.nameTv.setText(entity.getGroupmember());
        holder.giftMoneyTv.setText(entity.getTotalmoney()+"");
        holder.phoneTv.setText(entity.getMemberphone());
        if(entity.getAffiliatedperson()!=null&&entity.getAffiliatedperson().trim().length()>0) {
            holder.affiliatedPersonTv.setText(entity.getAffiliatedperson());
            holder.affiliatedPersonTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,GroupMemberAct.class);
                    intent.putExtra("id",entity.getAffiliatedpersonid()+"");
                    mContext.startActivity(intent);
                }
            });
        }else
        {
            holder.affiliatedPersonTv.setText("");
        }
        holder.operateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.operate(entity.getId(),entity.getGroupmember(),position);
            }
        });
        return super.getView(position, convertView, parent);
    }

    @Override
    public void remove() {
        list.clear();
    }
    class GroupMemberView
    {
        Button operateBtn;
        TextView nameTv;
        TextView phoneTv;
        /**关联人*/
        TextView affiliatedPersonTv;
        /**礼金总和*/
        TextView giftMoneyTv;
    }


}
