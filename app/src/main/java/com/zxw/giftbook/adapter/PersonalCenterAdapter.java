package com.zxw.giftbook.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxw.giftbook.Activity.AffairEditAct;
import com.zxw.giftbook.Activity.GroupMemberAct;
import pri.zxw.library.entity.NameImgEntity;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.Activity.menu.PersonalCenterFragment;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;

/**
 * 个人中心
 *
 */
public class PersonalCenterAdapter extends MyBaseAdapter {
    private PersonalCenterFragment mContext;
    private List<NameImgEntity> comLists;
    private LayoutInflater inflater = null;
    /**grid列数*/
    public static final int COL_NUM=4;
    @Override
    public void addDataAll(List infos) {
        comLists.addAll(infos);
        notifyDataSetChanged();
    }

    @Override
    public void remove() {
        comLists.clear();
    }

    public PersonalCenterAdapter(PersonalCenterFragment context) {
        this.mContext = context;
        inflater= LayoutInflater.from(context.getActivity());
        comLists=new ArrayList<NameImgEntity>();
        comLists.add(new NameImgEntity("个人中心",R.mipmap.personal_center,AffairEditAct.class));
        comLists.add(new NameImgEntity("收到的请帖",R.mipmap.receive_invitation,AffairEditAct.class));
        comLists.add(new NameImgEntity("发送的请帖",R.mipmap.invitation,AffairEditAct.class));
        comLists.add(new NameImgEntity("推荐给好友",R.mipmap.recommend,AffairEditAct.class));
        comLists.add(new NameImgEntity("修改密码",R.mipmap.edit_pwd,AffairEditAct.class));
        comLists.add(new NameImgEntity("关于",R.mipmap.about,AffairEditAct.class));
        comLists.add(new NameImgEntity("意见反馈",R.mipmap.feedback,AffairEditAct.class));
        comLists.add(new NameImgEntity("退出",R.mipmap.exit,AffairEditAct.class));
    }

    @Override
    public int getCount() {
        if(comLists!=null)
            return comLists.size();
        return 0;
    }
    @Override
    public NameImgEntity getItem(int position) {
        return comLists.get(position);
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
            view = inflater.inflate(R.layout.item_griv_name_img, null);
            mHolder = new ViewHolder();
            mHolder.lay = (RelativeLayout) view.findViewById(R.id.item_griv_name_img_lay);
            mHolder.frameLayout = (RelativeLayout) view.findViewById(R.id.item_griv_name_img_framelay);
            mHolder.addImg=(ImageView)view.findViewById(R.id.item_griv_name_img_add_img);
            mHolder.nameTv = (TextView) view.findViewById(R.id.item_griv_name_img_name_tv);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }
       final NameImgEntity comInfo =comLists.get(position);
        ImgLoadMipmapTool.load(comInfo.getImgId(),mHolder.addImg);
        mHolder.nameTv.setText(comInfo.getName());
        mHolder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext.getActivity(), comInfo.getClassName()));
            }
        });
        /**用来计算的索引*/
        int calcPosition=position+1;
        if(calcPosition%COL_NUM==0)
        {
            mHolder.lay.setBackgroundResource(R.drawable.shape_btn_layout_right_not_bottom_border);
        }else
        {
            mHolder.lay.setBackgroundResource(R.drawable.shape_btn_layout_left_not_bottom_border);
        }
        if(comLists.size()-calcPosition<COL_NUM)
        {
            mHolder.lay.setBackgroundResource(R.drawable.shape_layout_left_border);
        }else if(comLists.size()-calcPosition==0)
        {
            mHolder.lay.setBackgroundResource(R.drawable.shape_layout_right_border);
        }
        return view;
    }
    void addView(FrameLayout frameLayout)
    {
        ImageView imageView=new ImageView(mContext.getActivity());
        ImgLoadMipmapTool.load(R.mipmap.add_icon,imageView);
        frameLayout.addView(imageView);
    }




    class ViewHolder {
       RelativeLayout lay;
        ImageView addImg;
        RelativeLayout frameLayout;
       TextView nameTv;
        TextView numTv;
    }

}
