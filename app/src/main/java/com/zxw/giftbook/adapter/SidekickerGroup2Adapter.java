package com.zxw.giftbook.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class SidekickerGroup2Adapter extends MyBaseAdapter {
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
    public int getCount() {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_group_type2, null);
            mHolder = new ViewHolder();
            mHolder.lay = (LinearLayout) view.findViewById(R.id.item_list_group_type_lay);
            mHolder.operateImg=(ImageView)view.findViewById(R.id.item_list_group_type_operate_img);
            mHolder.nameTv = (TextView) view.findViewById(R.id.item_list_group_type_name_tv);
            mHolder.numTv = (TextView) view.findViewById(R.id.item_list_group_type_num_tv);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }
        final SidekickergroupEntity comInfo =comLists.get(position);
        mHolder.nameTv.setText(comInfo.getGroupname());
        mHolder.numTv.setText("("+comInfo.getGroupmembersnum()+")");
        mHolder.operateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.operate(comInfo.getId(),comInfo.getGroupname(),position);
            }
        });
        return view;
    }




    class ViewHolder {
        LinearLayout lay;
        ImageView operateImg;
       TextView nameTv;
        TextView numTv;
    }

}
