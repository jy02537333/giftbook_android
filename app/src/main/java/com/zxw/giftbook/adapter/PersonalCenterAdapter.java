package com.zxw.giftbook.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxw.giftbook.Activity.AffairEditAct;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pri.zxw.library.base.BaseEntity;
import pri.zxw.library.entity.NameImgEntity;

import com.zxw.giftbook.Activity.MySendInvitationAct;
import com.zxw.giftbook.Activity.ReceivesInvitationAct;
import com.zxw.giftbook.Activity.SendInvitationAct;
import com.zxw.giftbook.Activity.login.AboutAct;
import com.zxw.giftbook.Activity.login.FeedbackAct;
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
public class PersonalCenterAdapter extends MyBaseAdapter<NameImgEntity> {
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
    public void addData(NameImgEntity info) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext.getContext()).inflate(R.layout.item_griv_name_img, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MViewHolder mHolder=(MViewHolder)holder;
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
    }

    @Override
    public int getItemCount() {
        if(comLists!=null)
            return comLists.size();
        return 0;
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
        comLists.add(new NameImgEntity("发送的请帖",R.mipmap.invitation,MySendInvitationAct.class));
        comLists.add(new NameImgEntity("推荐给好友",R.mipmap.recommend,RecommendAct.class));
        comLists.add(new NameImgEntity("修改密码",R.mipmap.edit_pwd,UpdatePwdAct.class));
        comLists.add(new NameImgEntity("关于",R.mipmap.about,AboutAct.class));
        comLists.add(new NameImgEntity("意见反馈",R.mipmap.feedback,FeedbackAct.class));
        comLists.add(new NameImgEntity("退出",R.mipmap.exit,User.class));
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

    void addView(FrameLayout frameLayout)
    {
        ImageView imageView=new ImageView(mContext.getActivity());
        ImgLoadMipmapTool.load(R.mipmap.add_icon,imageView);
        frameLayout.addView(imageView);
    }




    class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        public MViewHolder(View rootView) {
            super(rootView);
            ButterKnife.inject(rootView);
            mRootView = rootView;
            mRootView.setOnClickListener(this);
            mRootView.setOnLongClickListener(this);
        }
        @InjectView(R.id.item_griv_name_img_lay)
       RelativeLayout lay;
        @InjectView(R.id.item_griv_name_img_add_img)
        ImageView addImg;
        @InjectView(R.id.item_griv_name_img_framelay)
        RelativeLayout frameLayout;
        @InjectView(R.id.item_griv_name_img_name_tv)
       TextView nameTv;
        View mRootView;
    }

}
