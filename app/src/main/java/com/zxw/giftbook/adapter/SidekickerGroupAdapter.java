package com.zxw.giftbook.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxw.giftbook.Activity.GroupMemberAct;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.Activity.menu.SidekickerGroup2Fragment;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.listener.TxtLengthRestrictTool;
import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;
import pri.zxw.library.tool.MyAlertDialog;

/**
 * 亲友团 ，组名称 适配器
 *
 */
public class SidekickerGroupAdapter extends MyBaseAdapter<SidekickergroupEntity> {
    private SidekickerGroup2Fragment mContext;
    private List<SidekickergroupEntity> comLists;
    private LayoutInflater inflater = null;
    /**grid列数*/
    public static final int COL_NUM=4;
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

    public SidekickerGroupAdapter(SidekickerGroup2Fragment context) {
        this.mContext = context;
        inflater= LayoutInflater.from(context.getContext());
        comLists=new ArrayList<SidekickergroupEntity>();
    }

    @Override
    public void addData(SidekickergroupEntity info) {
        comLists.add(info);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext.getContext()).inflate(R.layout.item_receiving_gift, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MViewHolder mHolder=(MViewHolder)holder;
        final SidekickergroupEntity comInfo =comLists.get(position);

        if(comInfo.getId()==null||comInfo.getId().equals(""))
        {
            if(comInfo.getAddType()==0)
            {
                mHolder.addImg.setVisibility(View.INVISIBLE);
                mHolder.nameTv.setVisibility(View.INVISIBLE);
                mHolder.numTv.setVisibility(View.INVISIBLE);
            }else {
                mHolder.frameLayout.setVisibility(View.VISIBLE);
                mHolder.addImg.setVisibility(View.VISIBLE);
                mHolder.nameTv.setVisibility(View.GONE);
                mHolder.nameTv.setText("");
                mHolder.numTv.setText(comInfo.getGroupname());
                mHolder.lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (comInfo.getAddType() == 1)
                            showAddView();
                        else
                        {
//                            mContext.addMember(comInfo.getId(), comInfo.getGroupname());
                        }
                    }
                });
            }
        }else {
            mHolder.addImg.setVisibility(View.GONE);
            mHolder.nameTv.setText(comInfo.getGroupname());
            mHolder.numTv.setText(comInfo.getGroupmembersnum()+"");
            mHolder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext.getActivity(), GroupMemberAct.class);
                    intent.putExtra("id",comInfo.getId());
                    intent.putExtra("groupName",comInfo.getGroupname());
                    mContext.startActivity(intent);
                }
            });
        }
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
    public SidekickergroupEntity getItem(int position) {
        return comLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    void addView(FrameLayout frameLayout)
    {
        ImageView imageView=new ImageView(mContext.getContext());
        ImgLoadMipmapTool.load(R.mipmap.add_icon,imageView);
        frameLayout.addView(imageView);
    }


    /**
     * 添加分类
     */
    private void showAddView() {
        final MyAlertDialog.MyBuilder dialog1 = new MyAlertDialog.MyBuilder(
                mContext.getActivity());
        dialog1.setTitle("添加分类");
        dialog1.setAutoDismiss(false, true);
        LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getActivity().getSystemService(mContext.getActivity().LAYOUT_INFLATER_SERVICE);
        View dialog_view = mLayoutInflater.inflate(
                R.layout.tool_alert_edit_text, null);
        dialog1.setContentView(dialog_view);
        final EditText editText = (EditText) dialog_view
                .findViewById(R.id.nickname_edit_aty_editor);
        editText.setTextColor(mContext.getResources().getColor(R.color.font_com_content_black_color));
        editText.setHintTextColor(mContext.getResources().getColor(R.color.com_hint_font_gray_color));
        editText.setHint("输入分类");
        editText.addTextChangedListener(new TxtLengthRestrictTool(editText, 20));
        editText.setSelection(editText.getText().length());
        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String str = editText.getText().toString().trim();
                mContext.addGroup(str);
                    dialog1.dismiss();
            }
        });
        dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog1.dismiss();
            }
        });
        dialog1.create().show();
    }


    class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
        public MViewHolder(View view) {
            super(view);
            lay = (RelativeLayout) view.findViewById(R.id.item_griv_group_type_lay);
            frameLayout = (RelativeLayout) view.findViewById(R.id.item_griv_group_type_framelay);
            addImg=(ImageView)view.findViewById(R.id.item_griv_group_type_add_img);
            nameTv = (TextView) view.findViewById(R.id.item_griv_group_type_name_tv);
            numTv = (TextView) view.findViewById(R.id.item_griv_group_type_num_tv);
            rootView=view;
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }
       RelativeLayout lay;
        ImageView addImg;
        RelativeLayout frameLayout;
       TextView nameTv;
        TextView numTv;
        View rootView;
    }

}
