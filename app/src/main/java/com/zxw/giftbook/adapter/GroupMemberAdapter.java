package com.zxw.giftbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
    public GroupmemberEntity getItem(int position)
    {
        return list.get(position);
    }
    @Override
    public void addDataAll(List infos) {
        list.addAll(infos);
        notifyDataSetChanged();

    }

    @Override
    public void addData(GroupmemberEntity info) {
        list.add(info);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext.getContext()).inflate(R.layout.item_list_group_member_list, parent, false);
        return new GroupMemberView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1,final int position) {
        GroupMemberView holder=(GroupMemberView)holder1;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void removeItem(int position) {
        list.remove(position);
    }

    @Override
    public void remove() {
        list.clear();
    }
    class GroupMemberView extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
        public GroupMemberView(View convertView)
        {
            super(convertView);
            affiliatedPersonTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_affiliated_person_tv);
            giftMoneyTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_gift_money_tv);
            nameTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_name_tv);
            phoneTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_phone_tv);
            phoneTv =
                    (TextView) convertView.findViewById(R.id.item_list_group_member_list_phone_tv);
            operateBtn =
                    (Button) convertView.findViewById(R.id.item_list_group_member_list_gift_operate_btn);
            mRootView = convertView;
            mRootView.setOnClickListener(this);
            mRootView.setOnLongClickListener(this);
        }
        Button operateBtn;
        TextView nameTv;
        TextView phoneTv;
        /**关联人*/
        TextView affiliatedPersonTv;
        /**礼金总和*/
        TextView giftMoneyTv;
        View mRootView;
    }


}
