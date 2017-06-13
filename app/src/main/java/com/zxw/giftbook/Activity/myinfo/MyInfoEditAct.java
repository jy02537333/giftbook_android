package com.zxw.giftbook.Activity.myinfo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zxw.giftbook.Activity.login.LoginAct;
import com.zxw.giftbook.Activity.menu.PersonalCenterFragment;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.LoginUserInfoHandlerTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.utils.choosepic.ImgScrollGridTool;
import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.entity.ComIdNameInfo;
import pri.zxw.library.entity.FileEntity;
import pri.zxw.library.entity.KVStringVString;
import pri.zxw.library.entity.User;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.Common;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.CustomDialog;
import pri.zxw.library.tool.img_compression.ImageCompressUtils;
import pri.zxw.library.view.TitleBar;

/**
 * Created by Administrator on 2017/6/2.
 */

public class MyInfoEditAct extends MyBaseActivity {

    List<ComIdNameInfo> idList;
    String ids;
    TitleBar titleBar;
    ImgScrollGridTool noScrollGridTool;
    ImageView img;
    TextView sexTv,nameTv,accountTv;
    EditText nameEdit;
    RadioGroup sexRadio;
    RadioButton rbo1,rbo2;
    AppServerTool serverTool;
    LinearLayout lay1,lay2,nameLay1,nameLay2;
    int type=0;
//    private Dialog dialog;
    String oneImgPath="";
    public static final int SUBMIT_CODE=1537;
    public static final int GET_TOKEN_CODE=5537;
    private static final int UPDATE_IMG_END_CODE=0x333;
    private static final int COMPRESS_MULTI_CODE=2968;
    private static final int COMPRESS_ONE_CODE=2962;
    private String token;
    private ArrayList<KVStringVString> upimg_key_list = new ArrayList<>();
    private int mRequest=0;
    /**s是否单选*/
    boolean isOne=false;
    boolean isSubPic=false;
    /**1=编辑，2=保存*/
    int operateType=1;
    MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
    UploadManager uploadManager = new UploadManager();
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_TOKEN_CODE)
            {
                token= messageHandlerTool.handlerData(msg,MyInfoEditAct.this);
                if(token!=null&&token.length()>0) {
                    if(ImgScrollGridTool.mSelectPath.size()==0)
                        compressedOneFiles();
                    else
                        compressedMultiFiles();
                }
                else
                {
                    ProgressDialogTool.getInstance(MyInfoEditAct.this).dismissDialog();
                }
            }else if(msg.what==UPDATE_IMG_END_CODE)
            {
                submit();
            }else if(msg.what==SUBMIT_CODE)
            {
                int ret=   messageHandlerTool.handler(msg,MyInfoEditAct.this);
                if(ret==1)
                {
                    operateType=1;
                    changeEditState();
                    LoginUserInfoHandlerTool loginUserInfoHandlerTool=new LoginUserInfoHandlerTool(MyInfoEditAct.this,
                            serverTool);
                     loginUserInfoHandlerTool.loginedHandler(msg,FtpApplication.getInstance().getUser().getLoginpassword());
                    ToastShowTool.myToastShort(MyInfoEditAct.this,"修改成功！");
                    setViewValue();
                    Intent intent = new Intent();
                    intent.setAction(PersonalCenterFragment.FragmentMyBroadcast.FRAGMENTMYBROADCAST_UPDATE);
                    sendBroadcast(intent);
                }
            }
            else if(msg.what==COMPRESS_ONE_CODE)
            {
                for (int i = 0; i < compress.size(); i++) {
                    getUpimg(compress.get(i).fileName, ImgScrollGridTool.REQUEST_MULTI_IMAGE);
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_my_info_edit);
        initView();
        initListener();
        initTool();
        setViewValue();
    }
    void initView()
    {
        titleBar=(TitleBar) this.findViewById(R.id.a_my_info_edit_title_bar);
        img=(ImageView) this.findViewById(R.id.a_my_info_edit_img);
        accountTv=(TextView) this.findViewById(R.id.a_my_info_edit_account_tv);
        sexRadio=(RadioGroup) this.findViewById(R.id.a_my_info_edit_sex_rbog);
        rbo1=(RadioButton) this.findViewById(R.id.a_my_info_edit_sex_rbo1);
        rbo2=(RadioButton) this.findViewById(R.id.a_my_info_edit_sex_rbo2);
        sexRadio=(RadioGroup) this.findViewById(R.id.a_my_info_edit_sex_rbog);
        nameTv=(TextView) this.findViewById(R.id.a_my_info_edit_name_tv);
        nameEdit=(EditText) this.findViewById(R.id.a_my_info_edit_name_edit);
        sexTv=(TextView) this.findViewById(R.id.a_my_info_edit_sex_tv);
        lay1=(LinearLayout) this.findViewById(R.id.a_my_info_edit_sex_lay1);
        lay2=(LinearLayout) this.findViewById(R.id.a_my_info_edit_sex_lay2);
        nameLay1=(LinearLayout) this.findViewById(R.id.a_my_info_edit_name_lay1);
        nameLay2=(LinearLayout) this.findViewById(R.id.a_my_info_edit_name_lay2);
    }
    void setViewValue()
    {
        if(FtpApplication.getInstance().getUser().isLogin(this)) {
            User user=FtpApplication.getInstance().getUser();
            MyImgLoadTool.loadNetHeadThumbnailImg(this, user.getPortrait(),img,80,80,"");
            nameTv.setText(user.getUsername());
            nameEdit.setHint(user.getUsername());
            accountTv.setText(user.getLoginname());
            if(user.getSex()!=null&&user.getSex()==1)
            {
                sexTv.setText("男");
                rbo1.setSelected(true);
                rbo1.setChecked(true);
            }
            else if(user.getSex()!=null&&user.getSex()==2)
                {
                    sexTv.setText("女");
                    rbo2.setSelected(true);
                    rbo2.setChecked(true);
                }
            else
                sexTv.setText("未知");
        }
        else
        {
//            Intent intent=new Intent(this, LoginAct.class);
//            startActivityForResult(intent,911);
        }
    }
    void initTool()
    {
        serverTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
        noScrollGridTool=new ImgScrollGridTool(this);
    }
    void initListener()
    {
        sexTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sexRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.a_my_info_edit_sex_rbo1)
                    type=1;//男
                else
                    type=2;//女

            }
        });

        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                if(operateType==1)
                {
                    operateType=2;
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            noScrollGridTool.pickImage(true);
                        }
                    });
                }else
                {
                    operateType=1;
                    getQiniuToken();
                }
                changeEditState();
            }
        });
        titleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    void changeEditState()
    {
        if(operateType==2)
        {
            titleBar.setRightText("保存");
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.VISIBLE);
            nameLay1.setVisibility(View.GONE);
            nameLay2.setVisibility(View.VISIBLE);
        }else
        {
            titleBar.setRightText("编辑");
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.GONE);
            nameLay1.setVisibility(View.VISIBLE);
            nameLay2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == ImgScrollGridTool.REQUEST_STORAGE_READ_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                noScrollGridTool.pickImage(isOne);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(ImgScrollGridTool.REQUEST_ONE_IMAGE==requestCode&&resultCode==RESULT_OK)
        {
            if(data!=null) {
                ArrayList<String> path = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                oneImgPath=path.get(0);
                MyImgLoadTool.loadLocalRoundImg(MyInfoEditAct.this, new File(oneImgPath),
                        R.mipmap.default_bg,img,80,80,"");
//                ImgLoadMipmapTool.load(oneImgPath, img);
            }
        }
    }
    void submit()
    {
        if(type==0)
        {
            ToastShowTool.myToastShort(this,"请选择性别！");
            return ;
        }
        if(nameEdit.getText().toString().trim().length()==1)
        {
            ToastShowTool.myToastShort(this,"请输入正确姓名！");
            return ;
        }
        Map<String,String> param= ComParamsAddTool.getParam();
        param.put("info", FtpApplication.getInstance().getUser().toSignString(this));
        param.put("id", FtpApplication.getInstance().getUser().getId());
        if(nameEdit.getText().toString().trim().length()>0)
        param.put("username", nameEdit.getText().toString().trim());
        param.put("sex",type+"");
        if(upimg_key_list.size()>0) {
            param.put("portrait", upimg_key_list.get(0).value );
        }
        serverTool.doPostAndalysisDataCall("apiSysUserCtrl.do?doUpdate",param,SUBMIT_CODE,new IServicesCallback(){
            @Override
            public void onStart() {
                if(!isSubPic)
                {
                    ProgressDialogTool.getInstance(MyInfoEditAct.this).showDialog("提交中...");
                }
                isSub=true;
            }
            @Override
            public void onEnd() {
                isSub=false;
                isSubPic=false;
                ProgressDialogTool.getInstance(MyInfoEditAct.this).dismissDialog();
                clearImgList();
            }
        });

    }
    /***清理压缩后的图片和上传完成的图片*/
    void clearImgList()
    {
        compress.clear();
        upimg_key_list.clear();
    }
    public void getQiniuToken()
    {

        if(oneImgPath.trim().length()<5)
        {
            submit();
            return;
        }
        if(isSub)
            return;
        Map<String,String> param= ComParamsAddTool.getParam();
        serverTool.doGetAndalysisDataCall("apiQiNiuUptokenCtrl.do?uptokenMobile",param,GET_TOKEN_CODE,new IServicesCallback(){
            @Override
            public void onStart() {
                ProgressDialogTool.getInstance(MyInfoEditAct.this).showDialog("加载中...");
                ProgressDialogTool.getInstance(MyInfoEditAct.this).canelClick(new ProgressDialogTool.OnCanelClick() {
                    @Override
                    public void onComplate() {
                        isSub=false;
                        isSubPic=false;
                        clearImgList();
                    }
                });
                isSub=true;
                isSubPic=true;
            }
            @Override
            public void onEnd() {
            }
        });
    }
    List<FileEntity> compress=new ArrayList<>();
    private void compressedMultiFiles()
    {
        for (final String imgPath : ImgScrollGridTool.mSelectPath) {
            ImageCompressUtils.from(this)
                    .load(imgPath)
                    .execute(new ImageCompressUtils.OnCompressListener() {
                        @Override
                        public void onSuccess(File file) {
                            compress.add(new FileEntity(file, imgPath));
                            if (compress.size() == ImgScrollGridTool.mSelectPath.size()) {
                                mHandler.sendEmptyMessage(COMPRESS_MULTI_CODE);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    });
        }
    }
    private void compressedOneFiles()
    {
        ImageCompressUtils.from(this)
                .load(oneImgPath)
                .execute(new ImageCompressUtils.OnCompressListener() {
                    @Override
                    public void onSuccess(File file) {
                        compress.add(new FileEntity(file, oneImgPath));
                        mHandler.sendEmptyMessage(COMPRESS_ONE_CODE);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void getUpimg(final String imagePath,final int requestCode) {
        new Thread() {
            public void run() {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                String prefix=imagePath.substring(imagePath.lastIndexOf(".")+1);
                String fileName= DateCommon.getDateFileName()+ Common.getFixLenthString(3);

                String key="uplaod_head/"+FtpApplication.getInstance().getUser().getId()+"/"+fileName+"."+prefix;
                uploadManager.put(imagePath, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                Log.i("qiniu", key + ",\r\n " + info + ",\r\n "
                                        + res);
                                try {
                                    // 七牛返回的文件名
                                    String  upimg = res.getString("key");
//                                    if(requestCode==ImgScrollGridTool.REQUEST_ONE_IMAGE)
//                                    {
//                                        mRequest=requestCode;
//                                        mHandler.sendEmptyMessage(UPDATE_IMG_END_CODE);
//                                    }
                                    upimg_key_list.add(new KVStringVString(imagePath,upimg));//将七牛返回图片的文件名添加到list集合中
                                    //list集合中图片上传完成后，发送handler消息回主线程进行其他操作
                                    if (upimg_key_list.size() == compress.size()) {
                                        mRequest=requestCode;
                                        mHandler.sendEmptyMessage(UPDATE_IMG_END_CODE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, null);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
