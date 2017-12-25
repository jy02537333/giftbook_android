package com.zxw.giftbook.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.zxw.giftbook.Activity.contact.ContactListSelectActivity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;

import org.json.JSONException;
import org.json.JSONObject;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.utils.choosepic.ImgScrollGridTool;
import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.entity.ComIdNameInfo;
import pri.zxw.library.entity.FileEntity;
import pri.zxw.library.entity.KVStringVString;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.Common;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.CustomDialog;
import pri.zxw.library.tool.img_compression.ImageCompressUtils;
import pri.zxw.library.view.TitleBar;

/**
 * 请帖事件编辑
 * Createdy 张相伟
 * 2017/3/27.
 */

public class AffairEditAct extends MyBaseActivity {

    List<ComIdNameInfo> idList;
    String ids;
    TitleBar titleBar;
    TextView a_affair_edit_cover_Tv,manTv,womanTv;
    GridView a_affair_edit_noScrollgridview;
    ImgScrollGridTool noScrollGridTool;
    LinearLayout rootLay;
    EditText a_affair_edit_tfDetails_edit,a_affair_edit_man_tv,a_affair_edit_woman_tv,invitationEdit
     ,a_affair_edit_feastaddress_edit,a_affair_edit_feast_hotel_edit;
    ImageView coverImg;
    RadioGroup typeRadio;
    TextView coverTv,a_affair_edit_date_tv,a_affair_edit_time_tv,selectTv;
    Button submitBtn;
    AppServerTool serverTool;
    int type=1;
    private Dialog dialog;
    String oneImgPath="";
    public static final int REQUEST_CONTACT_CODE=8934;
    public static final int SUBMIT_CODE=1537;
    public static final int GET_TOKEN_CODE=5537;
    private static final int UPDATE_ONE_IMG_END_CODE=0x546;
    private static final int UPDATE_IMG_END_CODE=0x333;
    private static final int COMPRESS_MULTI_CODE=2968;
    private static final int COMPRESS_ONE_CODE=2962;
    private String token;
    private ArrayList<KVStringVString> upimg_key_list = new ArrayList<>();
    private int mRequest=0;
    /**s是否单选*/
    boolean isOne=false;
    boolean isGetData=false;
    int mHour=0;
    int mMinute=0;
    String expendituredate;
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
    UploadManager uploadManager = new UploadManager();
    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            //修改日历控件的年，月，日
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            expendituredate=dateAndTime.toString();
            //将页面TextView的显示更新为最新时间
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd", Locale.CHINA);
            a_affair_edit_date_tv.setText(sdf.format(dateAndTime.getTime()));
        }
    };
    TimePickerDialog.OnTimeSetListener timeDialog = new TimePickerDialog.OnTimeSetListener() {
                //从这个方法中取得获得的时间
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay,
                                      int minute) {
                    a_affair_edit_time_tv.setText(hourOfDay+":"+minute);
                }
            };
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_TOKEN_CODE)
            {
                token= messageHandlerTool.handlerData(msg,AffairEditAct.this);
                if(token!=null&&token.length()>0) {
                    if(ImgScrollGridTool.mSelectPath.size()==0)
                        compressedOneFiles();
                    else
                        compressedMultiFiles();
                }
                else
                {
                    dialog.dismiss();
                }
            }else if(msg.what==UPDATE_IMG_END_CODE)
            {
//                if(mRequest==ImgScrollGridTool.REQUEST_MULTI_IMAGE){
//                    getUpimg(oneImgPath,ImgScrollGridTool.REQUEST_ONE_IMAGE);
//                } else if(mRequest==ImgScrollGridTool.REQUEST_ONE_IMAGE){
//                    submit();
//                 }
                submit();
            }else if(msg.what==SUBMIT_CODE)
            {
                int ret=   messageHandlerTool.handler(msg,AffairEditAct.this);
                if(ret==1)
                {
                    ToastShowTool.myToastShort(AffairEditAct.this,"发布请帖成功！");
                    setResult(1);
                    finish();
                }
            }
            else if(msg.what==COMPRESS_MULTI_CODE)
            {
                compressedOneFiles();
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
        setContentView(R.layout.act_affair_edit);
        initView();
        initListener();
        initTool();
        initTime();
    }
    void initView()
    {
        titleBar=(TitleBar)findViewById(R.id.a_affair_edit_title_bar);
        a_affair_edit_cover_Tv=(TextView)findViewById(R.id.a_affair_edit_cover_tv);
        a_affair_edit_noScrollgridview=(GridView)findViewById(R.id.a_affair_edit_noScrollgridview);
        rootLay=(LinearLayout) findViewById(R.id.a_affair_edit_root_lay);
        a_affair_edit_tfDetails_edit=(EditText) findViewById(R.id.a_affair_edit_tfDetails_edit);
        a_affair_edit_date_tv=(TextView) findViewById(R.id.a_affair_edit_date_tv);
        a_affair_edit_time_tv=(TextView) findViewById(R.id.a_affair_edit_time_tv);
        coverImg=(ImageView) findViewById(R.id.a_affair_edit_cover_img);
        selectTv=(TextView) findViewById(R.id.a_affair_edit_select_tv);
        submitBtn=(Button) findViewById(R.id.a_affair_edit_btn);
        typeRadio=(RadioGroup) findViewById(R.id.a_affair_edit_rbog);
        a_affair_edit_man_tv=(EditText) findViewById(R.id.a_affair_edit_man_edit);
        a_affair_edit_woman_tv=(EditText) findViewById(R.id.a_affair_edit_woman_edit);
        a_affair_edit_feast_hotel_edit=(EditText) findViewById(R.id.a_affair_edit_feast_hotel_edit);
        invitationEdit=(EditText) findViewById(R.id.a_affair_edit_invitation_edit);
        manTv=(TextView) findViewById(R.id.a_affair_edit_man_tv);
        womanTv=(TextView) findViewById(R.id.a_affair_edit_woman_tv);
//        a_affair_edit_inviter_tv.setText(FtpApplication.getInstance().getUser().getUsername());
//        a_affair_edit_tfDetails_edit.clearFocus();
//      a_affair_edit_tfDetails_edit.setSelected(false);
    }
    void initTool()
    {
        serverTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
        noScrollGridTool=new ImgScrollGridTool(this);
        noScrollGridTool.init(a_affair_edit_noScrollgridview, null, rootLay);
    }
    void initTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar nowDate= Calendar.getInstance();
        nowDate.add(Calendar.DATE,1);
        String time = format.format(nowDate.getTime());
        a_affair_edit_date_tv.setText(time);
    }
    void initListener()
    {
        typeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.a_affair_edit_wedding_rbo)
                {
                    type=1;//婚礼
                    manTv.setText("男方");
                    womanTv.setText("女方");
                    a_affair_edit_man_tv.setHint("男方姓名");
                    a_affair_edit_woman_tv.setHint("女方姓名");
                }
                 else
                {
                    type=2;//百日宴
                    manTv.setText("男孩");
                    womanTv.setText("女孩");
                    a_affair_edit_man_tv.setHint("男孩名字");
                    a_affair_edit_woman_tv.setHint("女孩名字");
                }

            }
        });
        a_affair_edit_cover_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noScrollGridTool.pickImage(true);
            }
        });
        coverImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noScrollGridTool.pickImage(true);
            }
        });
        a_affair_edit_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当点击DatePickerDialog控件的设置按钮时，调用该方法
                DatePickerDialog  dateDlg = new DatePickerDialog(AffairEditAct.this,
                        datePicker,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH)
                );
                dateDlg.show();
            }
        });
        a_affair_edit_time_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog  dateDlg = new TimePickerDialog(AffairEditAct.this,
                        timeDialog,18, 0, true
                );
                dateDlg.show();
            }
        });
        selectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffairEditAct.this, ContactListSelectActivity.class);
                if (ids != null && ids.trim().length() > 0)
                    intent.putExtra("ids", ids);
                startActivityForResult(intent, REQUEST_CONTACT_CODE);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQiniuToken();
            }
        });
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                getQiniuToken();
            }
        });
        titleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        if(REQUEST_CONTACT_CODE==requestCode)//请求联系人
        {
            if (resultCode == 1) {
                if (data != null)
                    ids = data.getStringExtra("ids");
                selectTv.setText("");
                if (ids == null || ids.trim().length() == 0) {

                } else {
                    String entitys = data.getStringExtra("entitys");
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ComIdNameInfo>>() {
                    }.getType();
                    idList = gson.fromJson(entitys, type);
                    String nameStr="";
                    for (int i=0;i<idList.size();i++)
                    {
                        if(i>7||i+1==idList.size())
                        {
                            nameStr=nameStr+idList.get(i).getName();
                            break;
                        }else
                            nameStr=nameStr+idList.get(i).getName()+",";
                    }
                    selectTv.setText(nameStr);
//                    createHeadImg();
                }
            } else {

            }
        }else
        if(ImgScrollGridTool.REQUEST_ONE_IMAGE==requestCode&&resultCode==RESULT_OK)
        {
            coverImg.setVisibility(View.VISIBLE);
            a_affair_edit_cover_Tv.setVisibility(View.GONE);
            if(data!=null) {
                ArrayList<String> path = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                oneImgPath=path.get(0);
                MyImgLoadTool.loadLocalImg(AffairEditAct.this, new File(oneImgPath),
                        R.mipmap.default_bg,coverImg,80,80,"");
//                ImgLoadMipmapTool.load(oneImgPath, coverImg);
            }
        }else if(ImgScrollGridTool.REQUEST_MULTI_IMAGE==requestCode&&resultCode==RESULT_OK)
             noScrollGridTool.imgActivityResult(resultCode,requestCode,data);
    }
    void submit()
    {
        String inviter=  invitationEdit.getText().toString();
        String address=a_affair_edit_tfDetails_edit.getText().toString();
        String feastHotel=a_affair_edit_feast_hotel_edit.getText().toString();
        Map<String,String> param= ComParamsAddTool.getParam();
        param.put("inviterid", FtpApplication.getInstance().getUser().getId());
        param.put("inviterphone", FtpApplication.getInstance().getUser().getUserphone());
        param.put("feasthotel",feastHotel);
        param.put("feastaddress",address);
        param.put("feastdate",a_affair_edit_date_tv.getText().toString()+" "+a_affair_edit_time_tv.getText().toString()+":00");
        param.put("feasttype",type+"");
        param.put("manname",a_affair_edit_man_tv.getText().toString()+"");
        param.put("womanname",a_affair_edit_woman_tv.getText().toString()+"");
        param.put("createName", inviter);
        String photoAlbum="";
        boolean isAddCoverImg=false;//是否已经加入到封面图
//        for (int i=0;i<  ImgScrollGridTool.mSelectPath.size();i++)
//        {
//            if(ImgScrollGridTool.mSelectPath.get(i).equals(oneImgPath))
//                isExists=true;
//        }
        if(upimg_key_list.size()>0) {
            for (int i = 0; i < upimg_key_list.size(); i++) {
                if (upimg_key_list.get(i).key.equals(oneImgPath) && !isAddCoverImg) {
                    param.put("coverimg", upimg_key_list.get(i).value);
                    isAddCoverImg = true;
                } else {
                    if(i+1==upimg_key_list.size())
                    {
                        photoAlbum = photoAlbum + upimg_key_list.get(i).value ;
                    }else
                        photoAlbum = photoAlbum + upimg_key_list.get(i).value + ",";
                }
            }
//            photoAlbum = photoAlbum.substring(0, photoAlbum.length() - 1);
            param.put("photoalbum", photoAlbum);
        }else
            param.put("photoalbum", "");
        StringBuilder stringBuilder=new StringBuilder("[");
        for (int i=0;i<idList.size();i++)
        {
            stringBuilder.append("{\"inviteeid\":\"").append(idList.get(i).getId()).append("\",")
                    .append("\"inviteename\":\"").append(idList.get(i).getName()).append("\",")
                    .append("\"inviteephone\":\"").append(idList.get(i).getPhone()).append("\"");
            if(i+1==idList.size())
                stringBuilder.append("}");
            else
                stringBuilder.append("},");
        }
        stringBuilder.append("]");
        param.put("invitationListJson",stringBuilder.toString());
        serverTool.doPostAndalysisDataCall("apiInvitationController.do?doAdd",param,SUBMIT_CODE,new IServicesCallback(){
            @Override
            public void onStart() {
//                dialog = CustomDialog.createLoadingDialog(AffairEditAct.this, "加载数据…");
//                dialog.setCancelable(false);
//                dialog.show();
//                isGetData=true;
            }
            @Override
            public void onEnd() {
                isGetData=false;
                dialog.dismiss();
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

        if(type==0)
        {
            ToastShowTool.myToastShort(AffairEditAct.this,"请选择邀请类型！");
            return ;
        }
        String inviter=  invitationEdit.getText().toString();
        if(inviter.trim().length()<2)
        {
            ToastShowTool.myToastShort(AffairEditAct.this,"请填写邀请人！");
            return ;
        }
        String address=a_affair_edit_tfDetails_edit.getText().toString();
        if(address.trim().length()<5)
        {
            ToastShowTool.myToastShort(AffairEditAct.this,"请填写完整的地址！");
            return;
        }
        String inviterStr=invitationEdit.getText().toString();
        if(inviterStr.trim().length()<1)
        {
            ToastShowTool.myToastShort(AffairEditAct.this,"请选择亲友！");
            return;
        }
        if(oneImgPath.trim().length()<5)
        {
            ToastShowTool.myToastShort(AffairEditAct.this,"请选择封面图！");
            return;
        }
        if(isGetData)
            return;
        Map<String,String> param= ComParamsAddTool.getParam();
        serverTool.doGetAndalysisDataCall("apiQiNiuUptokenCtrl.do?uptokenMobile",param,GET_TOKEN_CODE,new IServicesCallback(){
            @Override
            public void onStart() {
                dialog = CustomDialog.createLoadingDialog(AffairEditAct.this, "加载数据…");
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isGetData=false;
                        clearImgList();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
                isGetData=true;
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
                String fileName=DateCommon.getDateFileName()+Common.getFixLenthString(3);

                                String key="uplaod_affair/"+FtpApplication.getInstance().getUser().getId()+"/"+fileName+"."+prefix;
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
        if(ContactListSelectActivity.mContactList!=null)
              ContactListSelectActivity.mContactList.clear();
    }
}
