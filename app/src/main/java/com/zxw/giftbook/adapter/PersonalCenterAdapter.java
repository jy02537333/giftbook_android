package com.zxw.giftbook.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxw.giftbook.Activity.AffairEditAct;

import pri.zxw.library.entity.NameImgEntity;

import com.zxw.giftbook.Activity.ReceivesInvitationAct;
import com.zxw.giftbook.Activity.SendInvitationAct;
import com.zxw.giftbook.Activity.login.AboutAct;
import com.zxw.giftbook.Activity.login.LoginAct;
import com.zxw.giftbook.Activity.login.PersonalInfoAct;
import com.zxw.giftbook.Activity.login.RecommendAct;
import com.zxw.giftbook.Activity.login.UpdatePwdAct;
import com.zxw.giftbook.Activity.menu.PersonalCenterFragment;
import com.zxw.giftbook.Activity.myinfo.MyInfoEditAct;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.entity.User;
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
    public static final int COL_NUM=3;
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

    public PersonalCenterAdapter(PersonalCenterFragment context) {
        this.mContext = context;
        inflater= LayoutInflater.from(context.getActivity());
        comLists=new ArrayList<>();
        comLists.add(new NameImgEntity("个人中心",R.mipmap.personal_center,MyInfoEditAct.class));
        comLists.add(new NameImgEntity("收到的请帖",R.mipmap.receive_invitation,ReceivesInvitationAct.class));
        comLists.add(new NameImgEntity("发送的请帖",R.mipmap.invitation,SendInvitationAct.class));
        comLists.add(new NameImgEntity("推荐给好友",R.mipmap.recommend,RecommendAct.class));
        comLists.add(new NameImgEntity("修改密码",R.mipmap.edit_pwd,UpdatePwdAct.class));
        comLists.add(new NameImgEntity("关于",R.mipmap.about,AboutAct.class));
        comLists.add(new NameImgEntity("意见反馈",R.mipmap.feedback,AffairEditAct.class));
        comLists.add(new NameImgEntity("退出",R.mipmap.exit,User.class));
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
                if(comInfo.getClassName().equals(User.class)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext.getActivity());
                    builder.setMessage("确定要注销当前帐号吗?");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (null != FtpApplication.getInstance().getUser()) {
                                        Intent intent=new Intent(mContext.getActivity(), LoginAct.class);
                                        FtpApplication.getInstance().getUser().clearUser();
                                        mContext.startActivity(intent);
                                        mContext.getActivity().finish();
                                        FtpApplication.getInstance().clearActityNotServer();
                                    }
                                }
                            });
                    builder.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else
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
