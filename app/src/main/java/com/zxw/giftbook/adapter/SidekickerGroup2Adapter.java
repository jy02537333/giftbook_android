package com.zxw.giftbook.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxw.giftbook.Activity.GroupMemberAct;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.Activity.menu.SidekickerGroup2Fragment;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.base.MyBaseAdapter;

/**
 * 亲友团 ，组名称 适配器
 *
 */
public class SidekickerGroup2Adapter extends MyBaseAdapter<SidekickergroupEntity> {
    private SidekickerGroup2Fragment mContext;
    private List<SidekickergroupEntity> comLists;
    private LayoutInflater inflater = null;
    @Override
    public void addDataAll(List infos) {
        comLists.addAll(infos);
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(int position) {
        comLists.remove(position);
    }

    @Override
    public void remove() {
        comLists.clear();
    }

    public SidekickerGroup2Adapter(SidekickerGroup2Fragment context) {
        this.mContext = context;
        inflater= LayoutInflater.from(context.getContext());
        comLists=new ArrayList<>();
    }

    @Override
    public void addData(SidekickergroupEntity info) {
        comLists.add(info);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext.getContext()).inflate(R.layout.item_list_group_type2, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1,final int position) {
        MViewHolder mHolder=(MViewHolder)holder1;
        final SidekickergroupEntity comInfo =comLists.get(position);
        mHolder.nameTv.setText(comInfo.getGroupname());
        mHolder.numTv.setText("("+comInfo.getGroupmembersnum()+")");
        mHolder.operateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.operate(comInfo.getId(),comInfo.getGroupname(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(comLists!=null)
            return comLists.size();
        return 0;
    }

    @Override
    public SidekickergroupEntity getItem(int position) {
        return comLists.get(position);
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
            SidekickergroupEntity entity=getItem(getLayoutPosition());
            Intent intent=new Intent(mContext.getContext(), GroupMemberAct.class);
            intent.putExtra("id",entity.getId());
            intent.putExtra("groupName",entity.getGroupname());
            mContext.getContext().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
        public MViewHolder(View view) {
            super(view);
            lay = (LinearLayout) view.findViewById(R.id.item_list_group_type_lay);
            operateImg=(ImageView)view.findViewById(R.id.item_list_group_type_operate_img);
            nameTv = (TextView) view.findViewById(R.id.item_list_group_type_name_tv);
            numTv = (TextView) view.findViewById(R.id.item_list_group_type_num_tv);
            rootView=view;
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }
        LinearLayout lay;
        ImageView operateImg;
       TextView nameTv;
        TextView numTv;
        View rootView;
    }

}
