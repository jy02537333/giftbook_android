package com.zxw.giftbook.Activity.my;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.entity.User;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.listener.TxtLengthRestrictTool;
import pri.zxw.library.tool.AlertTool;
import pri.zxw.library.tool.GetPictureTool;
import pri.zxw.library.tool.MyAlertDialog;
import pri.zxw.library.tool.StatisticalCharsTool;
import pri.zxw.library.view.CircleImageView;
import pri.zxw.library.view.TitleBar;
import pri.zxw.mysetting.MySettingInfo;
import pri.zxw.mysetting.MysettingAdapter;

public class ActivityEditUser extends MyBaseActivity {
    private Activity mActivity;
    User user;
    private TitleBar titleTv;
    private EditText descEdit,nickEdit,cityEdit;
    private Button logoutBtn;
    private CircleImageView userHead;
    private LinearLayout headLay;
    private ListView lv;
    private Boolean isUpdated = false;
    private int genderCheck = 0;
    private MySettingInfo nicknameInfo;
    private MySettingInfo categoryInfo;
    private String categoryId, localImg;
    private MySettingInfo cityInfo;
    private MySettingInfo genderInfo;
    private MysettingAdapter mysettingAdapter;
    boolean isEditing=false;
    /**
     * 原始昵称
     */
    private String nickname,cityStr;
    /**
     * 新昵称
     */
    private String newNickname,newCityStr;
    private final static int OPTIMIZE_USER_CODE = 5341;
    /**
     * 用户退出
     */
    public static final int LOGOUT_CODE = 5436;
    /**
     * 修改昵称
     */
    public static final int NICKNAME_CODE = 5491;
    /**
     * 修改头像
     */
    public static final int HEAD_IMG_CODE = 5631;
    /**
     * 修改性别
     */
    public static final int GENDER_CODE = 2631;
    public static final int CATEGORY_SKILL_CODE=8346;
    private GetPictureTool getPictureTool;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_edit_user);
        initView();
        initTool();
        initListener();
    }

    /**
     * 初始化数据
     */
    private void initTool() {
        getPictureTool = new GetPictureTool(mActivity);
        List<MySettingInfo> infos=new ArrayList<>();
        infos.add(createNickname());
        infos.add(createGender());
        mysettingAdapter=new MysettingAdapter(this,infos);
        lv.setAdapter(mysettingAdapter);
        setHeadImg();
        setUserView();

    }

    private void setUserView() {
         user = FtpApplication.getInstance().getUser();
        String nick = user.getUsername();
        if (nick == null || nick.trim().length() == 0) {
            nick = user.getUserphone();
        }
        nicknameInfo.setItemDesc(nick);
        if (user.getSex().equals("0")) {
            genderInfo.setItemDesc("保密");
            genderCheck = 0;
        } else if (user.getSex().equals("1")) {
            genderInfo.setItemDesc("男");
            genderCheck = 1;
        } else if (user.getSex().equals("2")) {
            genderInfo.setItemDesc("女");
            genderCheck = 2;
        }

        mysettingAdapter.notifyDataSetChanged();
    }

    public User getUser() {
        user.setSex(genderCheck);
        user.setUsername(nicknameInfo.getItemDesc());

        return user;
    }

    private void setHeadImg() {

            String imgUrl = user.getUserphone();
            if (imgUrl.equals("")) {
                userHead.setImageResource(R.mipmap.not_headimg);
            } else {
//                int w  = CommonFun.dip2px(mActivity,40);
//                IcedotImageListenerEx ls = new IcedotImageListenerEx(imgUrl,userHead);
//                userHead.setTag(imgUrl);
//                userHead.setImageBitmap(null);
//                _app._httpMgr.asyncGetHttpImage(ls);
            }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        headLay = (LinearLayout) findViewById(R.id.p_edit_user_head_lay);
        logoutBtn = (Button) findViewById(R.id.p_edit_logout_btn);
        userHead = (CircleImageView) findViewById(R.id.p_edit_user_head_img);
        lv=(ListView) findViewById(R.id.p_edit_user_lv);
        titleTv = (TitleBar) findViewById(R.id.lay_title_bar);
        titleTv.setTitle("个人信息");
//        right_text=(TextView) findViewById(R.id.right_text);
        titleTv.setRightText("完成");
//        right_text.setTextColor(getResources().getColor(R.color.selector_head_right_font) );
        descEdit=(EditText) findViewById(R.id.p_edit_user_desc_edit);
    }

    private void initListener() {
        titleTv.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        logoutBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertTool alertTool = new AlertTool(mActivity);
                alertTool.yesOrNo("您确认要退出吗？", new AlertTool.AlertToolCallBack() {
                    @Override
                    public void complete() {
//                        Map<String, String> param = new HashMap<String, String>();
//                        param.put("userid", user.getId()+"");
//                        param.put("DeviceToken", FtpApplication.getInstance().getUm_device_token());
//                        sendBroadcast(2);
                        setResult(LOGOUT_CODE);
                        finish();
                    }
                });
            }
        });
        headLay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getPictureTool.showPictureList("请选择图片！");
            }
        });
        descEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private class MOnClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CATEGORY_SKILL_CODE && resultCode == 1) {
            categoryId=data.getStringExtra("id");
            categoryInfo.setItemDesc(data.getStringExtra("name"));
            mysettingAdapter.notifyDataSetChanged();
        }else {
             localImg = getPictureTool.resultLocalPath(requestCode,
                    resultCode, data);
            if (localImg != null) {
            }
        }
    }
    MySettingInfo createNickname()
    {
        nicknameInfo=new MySettingInfo();
        nicknameInfo.setItemName("昵称");
        nicknameInfo.setNameWeight(1);
        nicknameInfo.setItemDesc(FtpApplication.getInstance().getUser().getUsername());
        nicknameInfo.setDescWeight(6);
        nicknameInfo.setItemClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNict();
            }
        });
        return nicknameInfo;
    }
    MySettingInfo createGender()
    {
        genderInfo=new MySettingInfo();
        genderInfo.setItemName("性别");
        genderInfo.setNameWeight(1);
        genderInfo.setItemDesc("保密");
        genderInfo.setDescWeight(6);
        genderInfo.setItemClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSex();
            }
        });
        return genderInfo;
    }
//    MySettingInfo createCity()
//    {
//        cityInfo=new MySettingInfo();
//        cityInfo.setItemName("城市");
//        cityInfo.setNameWeight(1);
//        cityInfo.setItemDesc(MyApp.getInstance()._myInfo._city);
//        cityInfo.setDescWeight(6);
//        cityInfo.setItemClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateCity();
//            }
//        });
//        return cityInfo;
//    }


    /**
     * 修改昵称
     */
    private void updateNict() {
        final MyAlertDialog.MyBuilder dialog1 = new MyAlertDialog.MyBuilder(
                mActivity);
        dialog1.setTitle("编辑昵称");
        dialog1.setAutoDismiss(false, true);
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialog_view = mLayoutInflater.inflate(
                R.layout.tool_alert_edit_text, null);
        dialog1.setContentView(dialog_view);
        nickEdit = (EditText) dialog_view
                .findViewById(R.id.nickname_edit_aty_editor);
        nickEdit.setTextColor(getResources().getColor(R.color.black));
        nickEdit.setText(nicknameInfo.getItemDesc());
        nickEdit.addTextChangedListener(new TxtLengthRestrictTool(nickEdit, 20));
        nickEdit.setSelection(nickEdit.getText().length());
        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                newNickname = nickEdit.getText().toString().trim();
                    if (!isNickNameNull()) {
                        nicknameInfo.setItemDesc(newNickname);
                        mysettingAdapter.notifyDataSetChanged();
                        dialog1.dismiss();
                } else
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


    /**
     * 城市
     */
    private void updateCity() {
        final MyAlertDialog.MyBuilder dialog1 = new MyAlertDialog.MyBuilder(
                mActivity);
        dialog1.setTitle("编辑城市");
        dialog1.setAutoDismiss(false, true);
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialog_view = mLayoutInflater.inflate(
                R.layout.tool_alert_edit_text, null);
        dialog1.setContentView(dialog_view);
        cityEdit = (EditText) dialog_view
                .findViewById(R.id.nickname_edit_aty_editor);
        cityEdit.setHint("请在此输入城市");
        cityEdit.setTextColor(getResources().getColor(R.color.black));
        cityEdit.setText(cityInfo.getItemDesc());
        cityEdit.addTextChangedListener(new TxtLengthRestrictTool(cityEdit, 20));
        cityEdit.setSelection(cityEdit.getText().length());
        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                newCityStr = cityEdit.getText().toString().trim();
                cityInfo.setItemDesc(newCityStr);
                        dialog1.dismiss();
                mysettingAdapter.notifyDataSetChanged();
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


    private void updateSex() {
        final Builder dialog1 = new Builder(mActivity);
        dialog1.setTitle("请选择性别！");
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialog_view = mLayoutInflater.inflate(R.layout.radio_man_or_woman,
                null);
        dialog1.setView(dialog_view);
        RadioGroup sex_pick_group = (RadioGroup) dialog_view
                .findViewById(R.id.sex_pick_group);
        sex_pick_group
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup parent,
                                                 int checkedId) {
                        if (checkedId == R.id.sex_pick_from_secrecy) {
                            genderCheck = 0;
                            genderInfo.setItemDesc("保密");
                        } else if (checkedId == R.id.sex_pick_from_man) {
                            genderCheck = 1;
                            genderInfo.setItemDesc("男");
                        } else if (checkedId == R.id.sex_pick_from_woman) {
                            genderCheck = 2;
                            genderInfo.setItemDesc("女");
                        }
                        mysettingAdapter.notifyDataSetChanged();
                    }
                });

        dialog1.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        dialog1.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        if (genderCheck==0) {
            RadioButton sex_radio = (RadioButton) dialog_view
                    .findViewById(R.id.sex_pick_from_secrecy);
            sex_radio.setChecked(true);
        } else if (genderCheck==1) {
            RadioButton sex_radio = (RadioButton) dialog_view
                    .findViewById(R.id.sex_pick_from_man);
            sex_radio.setChecked(true);
        } else if (genderCheck==2) {
            RadioButton sex_radio = (RadioButton) dialog_view
                    .findViewById(R.id.sex_pick_from_woman);
            sex_radio.setChecked(true);
        }
        dialog1.create().show();
    }

    /**
     * 是否没有通过验证
     *
     * @return
     */
    private boolean isNickNameNull() {
        if (newNickname.equals("")) {
            Toast.makeText(mActivity, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return true;
        } else if (StatisticalCharsTool.getSemiangleCount(newNickname) > 20) {
            Toast.makeText(mActivity, "您输入昵称太长，请重新输入!", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        mActivity = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isUpdated) {
            setResult(1);
            finish();
        } else
            super.onBackPressed();
    }

}
