package com.zxw.giftbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxw.giftbook.Activity.entitiy.MembergiftmoneyEntity;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;

/**
 * 首页礼金记录适配器
 * Createdy 张相伟
 * 2016/11/1.
 */
public class HomeJournalAccountAdapter extends MyBaseAdapter<MembergiftmoneyEntity> {
    List<MembergiftmoneyEntity> list;
    LayoutInflater inflater;
    Context mContext;
    public HomeJournalAccountAdapter(Context context)
    {
        this.inflater = LayoutInflater.from(context);
        list=new ArrayList<>();
        mContext=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addDataAll(List<MembergiftmoneyEntity> infos) {
        if(list!=null)
            list.addAll(infos);
    }

    @Override
    public void remove() {
        if(list!=null)
            list.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null)
        {
            holder=new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_f_home_journal_account, null);
            holder.item_list_f_home_journal_account_alt_img =
                    (ImageView) convertView.findViewById(R.id.item_list_f_home_journal_account_alt_img);
            holder.item_list_f_home_journal_account_fee_tv =
                    (TextView) convertView.findViewById(R.id.item_list_f_home_journal_account_fee_tv);
            holder.item_list_f_home_journal_account_name_tv =
                    (TextView) convertView.findViewById(R.id.item_list_f_home_journal_account_name_tv);
            holder.item_list_f_home_journal_account_type_tv =
                    (TextView) convertView.findViewById(R.id.item_list_f_home_journal_account_type_tv);
            holder.item_list_f_home_journal_account_date_tv =
                    (TextView) convertView.findViewById(R.id.item_list_f_home_journal_account_date_tv);
            convertView.setTag(holder);
        }else
            holder= (ViewHolder)convertView.getTag();
        MembergiftmoneyEntity entity=list.get(position);
        if(entity.getIsexpenditure().equals("1"))
        {
            ImgLoadMipmapTool.load(R.mipmap.minus_alt ,holder.item_list_f_home_journal_account_alt_img);
        }else{
            ImgLoadMipmapTool.load(R.mipmap.plus_alt ,holder.item_list_f_home_journal_account_alt_img);
        }

       String date= DateCommon.dateToYYYY_P_MM_P_dd(entity.getCreateDate());
        holder.item_list_f_home_journal_account_date_tv.setText(date);
        holder.item_list_f_home_journal_account_fee_tv.setText(entity.getMoney()+"");
        holder.item_list_f_home_journal_account_name_tv.setText(entity.getGroupmember());
        holder.item_list_f_home_journal_account_type_tv.setText(entity.getExpendituretypename());

        return super.getView(position, convertView, parent);
    }



    class ViewHolder{
        ImageView item_list_f_home_journal_account_alt_img;
        TextView   item_list_f_home_journal_account_fee_tv;
        TextView  item_list_f_home_journal_account_name_tv;
        TextView  item_list_f_home_journal_account_type_tv;
        TextView  item_list_f_home_journal_account_date_tv;
    }
}
