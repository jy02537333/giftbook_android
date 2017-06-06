package com.zxw.giftbook.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.entitiy.GifttypeEntity;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.listener.TxtLengthRestrictTool;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.MyAlertDialog;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.DialogSheetzAction;
import pri.zxw.library.tool.dialogTools.DropDownBoxTool;
import pri.zxw.library.view.TitleBar;

/**
 * 礼金记录添加
 * Created by Administrator on 2016/11/8.
 */

public class GiftMoneyAddNewAct extends MyBaseActivity {
    boolean isSubmit=false;
    AppServerTool mServicesTool;
    TitleBar titleBar;
    public static final String ADD_URL="apiMembergiftmoneyCtrl.do?doAdd";

    TreeMap<String,String> giftTypeMap=new TreeMap<>();
    TreeMap<String,String> sidekickerGroups=new TreeMap<>();
    TreeMap<String,String>  groupmembers=new TreeMap<>();
    TreeMap<String,Integer> membersNum=new TreeMap<>();

    TextView otherTv,birthdayTv,weddingTv,hundredthTv,dateTv,moveHouseTv;
    TextView nameTv;
    EditText moneyEdit;
    TextView searchMemberTv;
    Button submitBtn;
    String typeId,typeName;
    String expendituredate;
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
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
            SimpleDateFormat sdf = new SimpleDateFormat( "MM-dd", Locale.CHINA);
            dateTv.setText(sdf.format(dateAndTime.getTime()));
        }
    };
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what== MyPullToRefreshBaseFragment.GET_ADD_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                int ret=messageHandlerTool.handler(msg,GiftMoneyAddNewAct.this);
                if(ret==1)
                {
                    ToastShowTool.myToastShort(GiftMoneyAddNewAct.this,"添加成功！");
                    setResult(1);
                    finish();
                }else
                {
                    ToastShowTool.myToastShort(GiftMoneyAddNewAct.this,"添加失败！");
                }
                isSubmit=false;
            }else if(msg.what==MyPullToRefreshBaseFragment.GET_DATA_CODE) {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                String data = messageHandlerTool.handlerData(msg, GiftMoneyAddNewAct.this);
                if (data.length() > 0) {
                    try {
                        org.json.JSONObject jsonObj = new org.json.JSONObject(data);
                        JSONArray giftTypes=jsonObj.getJSONArray("gifttypes");
                        for (int i=0;giftTypes!=null&&i<giftTypes.length();i++)
                        {
                            org.json.JSONObject obj=(org.json.JSONObject)giftTypes.get(i);
                            giftTypeMap.put(obj.getString("id"),obj.getString("typename"));
                        }
                        JSONArray sidekickerGroupJsons=jsonObj.getJSONArray("sidekickerGroups");
                        for (int i=0;sidekickerGroupJsons!=null&&i<sidekickerGroupJsons.length();i++)
                        {
                            org.json.JSONObject obj=(org.json.JSONObject)sidekickerGroupJsons.get(i);
                            membersNum.put(obj.getString("id"),obj.getInt("groupmembersnum"));
                            sidekickerGroups.put(obj.getString("id"),obj.getString("groupname"));
                        }


                    }catch (Exception e)
                    {
                    }
                }
            }
            else if(msg.what==MyPullToRefreshBaseFragment.ADD_CHILD_CODE){
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<GifttypeEntity>(){}.getType();
                GifttypeEntity obj=(GifttypeEntity)messageHandlerTool.handlerObject(msg,type,GiftMoneyAddNewAct.this);
                if(obj!=null)
                {
                    giftTypeMap.put(obj.getId(),obj.getTypename() );
//                    typeTv.setText(obj.getTypename());
//                    typeTv.setTag(obj.getId());
                    ToastShowTool.myToastShort(GiftMoneyAddNewAct.this,"添加成功！");
                }else
                {
                    ToastShowTool.myToastShort(GiftMoneyAddNewAct.this,"添加失败！");
                }
            }
            else if(msg.what==GET_GROUP_MEMBER_CODE)
            {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                Type type=new TypeToken<List<GroupmemberEntity>>(){}.getType();
                List<GroupmemberEntity> data =(List<GroupmemberEntity>)
                        messageHandlerTool.handlerObject(msg,type, GiftMoneyAddNewAct.this);
                if (data!=null&&data.size()> 0) {
                    try {
                        for (int i = 0; i<data.size(); i++) {
                            groupmembers.put(data.get(i).getId(),data.get(i).getGroupmember());
                        }
                        showGroupMember();
                    } catch (Exception e) {
                    }
                }else
                    ToastShowTool.myToastShort(GiftMoneyAddNewAct.this,"该组下未有亲友！");
                isGetGroupMembering=false;
            }
            ProgressDialogTool.getInstance(GiftMoneyAddNewAct.this).dismissDialog();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_gift_money_add_new);
        initView();
        initTool();
        initListener();
        getDropDownData();
    }

    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.act_gift_money_add_title_bar);
        nameTv=(TextView) findViewById(R.id.act_gift_money_add_name_tv);
        searchMemberTv=(TextView) findViewById(R.id.act_gift_money_add_member_add_tv);
        moneyEdit=(EditText) findViewById(R.id.i_calc_value_edit);
        dateTv=(TextView) findViewById(R.id.act_gift_money_add_date_tv);
        weddingTv=(TextView) findViewById(R.id.a_gift_money_add_wedding_type_tv);
        birthdayTv=(TextView) findViewById(R.id.a_gift_money_add_birthday_type_tv);
        moveHouseTv=(TextView) findViewById(R.id.a_gift_money_add_move_house_type_tv);
        otherTv=(TextView) findViewById(R.id.a_gift_money_add_other_type_tv);
        hundredthTv=(TextView) findViewById(R.id.a_gift_money_add_hundredth_type_tv);
        submitBtn=(Button)findViewById(R.id.i_calc_confirm);
        SimpleDateFormat sdf = new SimpleDateFormat( "MM-dd", Locale.CHINA);
        dateTv.setText(sdf.format(dateAndTime.getTime()));
    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
    }
    public void initListener()
    {
        titleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroup();
            }
        });
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当点击DatePickerDialog控件的设置按钮时，调用该方法
                        DatePickerDialog  dateDlg = new DatePickerDialog(GiftMoneyAddNewAct.this,
                        datePicker,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH));
                dateDlg.show();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        weddingTv.setOnClickListener(new MyClick());
        birthdayTv.setOnClickListener(new MyClick());
        otherTv.setOnClickListener(new MyClick());
        hundredthTv.setOnClickListener(new MyClick());
        moveHouseTv.setOnClickListener(new MyClick());
    }
    public void setTvSelect(TextView tv)
    {
        Drawable drawable1=getResources().getDrawable(R.mipmap.wedding);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        weddingTv.setCompoundDrawables(null,drawable1,null,null);
        Drawable drawable2=getResources().getDrawable(R.mipmap.birthday);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        birthdayTv.setCompoundDrawables(null,drawable2,null,null);
        Drawable drawable3=getResources().getDrawable(R.mipmap.other);
        drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
        otherTv.setCompoundDrawables(null,drawable3,null,null);
        Drawable drawable4=getResources().getDrawable(R.mipmap.hundredth);
        drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
        hundredthTv.setCompoundDrawables(null,drawable4,null,null);
        Drawable drawable10=getResources().getDrawable(R.mipmap.hundredth);
        drawable10.setBounds(0, 0, drawable10.getMinimumWidth(), drawable10.getMinimumHeight());
        moveHouseTv.setCompoundDrawables(null,drawable10,null,null);
        if(tv.getId()==weddingTv.getId())
        {
            Drawable drawable5=getResources().getDrawable(R.mipmap.wedding_p);
            drawable5.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            typeId="1";
            typeName=tv.getText().toString();
            weddingTv.setCompoundDrawables(null,drawable5,null,null);
        }else     if(tv.getId()==birthdayTv.getId())
        {
            Drawable drawable5=getResources().getDrawable(R.mipmap.birthday_p);
        drawable5.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            typeId="3";
            typeName=tv.getText().toString();
            birthdayTv.setCompoundDrawables(null,drawable5,null,null);
        }else     if(tv.getId()==otherTv.getId())
        {
            Drawable drawable5=getResources().getDrawable(R.mipmap.other_p);
        drawable5.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            typeId="0";
            typeName=tv.getText().toString();
            otherTv.setCompoundDrawables(null,drawable5,null,null);
        }else     if(tv.getId()==hundredthTv.getId())
        {
            Drawable drawable5=getResources().getDrawable(R.mipmap.hundredth_p);
        drawable5.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            typeId="2";
            typeName=tv.getText().toString();
            hundredthTv.setCompoundDrawables(null,drawable5,null,null);
        }else     if(tv.getId()==moveHouseTv.getId())
        {
            Drawable drawable5=getResources().getDrawable(R.mipmap.move_house_p);
            drawable5.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            typeId="4";
            typeName=tv.getText().toString();
            moveHouseTv.setCompoundDrawables(null,drawable5,null,null);
        }
    }
    public void submit()
    {
        if(nameTv.getText().toString().trim().length()==0)
        {
            ToastShowTool.myToastShort(this,"请输入成员姓名！");
            return ;
        }
        if(moneyEdit.getText().toString().trim().length()==0)
        {
            ToastShowTool.myToastShort(this,"请输入礼金数量！");
            return ;
        }
        if(typeId==null)
        {
            ToastShowTool.myToastShort(this,"请选择礼金类型！");
            return ;
        }
        Map<String,String > params= ComParamsAddTool.getParam();
        params.put("gourpmemberid", nameTv.getTag().toString());
        params.put("groupmember", nameTv.getText().toString());
        params.put("expendituretype", typeId);
        params.put("expendituretypename", typeName);
        params.put("totalmoney", "0");
        params.put("createBy", FtpApplication.user.getId());
        params.put("createName", FtpApplication.user.getUsername());
        if(moneyEdit.getText().toString().trim().length()>0)
            params.put("money", moneyEdit.getText().toString());
        if(isSubmit)
            return;
        isSubmit=true;
        params.put("isexpenditure","1");
        ProgressDialogTool.getInstance(GiftMoneyAddNewAct.this).showDialog("加载中...");
        mServicesTool.doPostAndalysisData(ADD_URL,params,MyPullToRefreshBaseFragment.GET_ADD_CODE);
    }

    public void getDropDownData()
    {
        Map<String,String > params= ComParamsAddTool.getParam();
        params.put("userid", FtpApplication.user.getId());
        ProgressDialogTool.getInstance(GiftMoneyAddNewAct.this).showDialog("提交中...");
        mServicesTool.doPostAndalysisData("apiAllTypeCtrl.do?getAll",params,MyPullToRefreshBaseFragment.GET_DATA_CODE);
    }
    public void addGiftType(String name)
    {
        ProgressDialogTool.getInstance(this).showDialog("加载中...");
        Map<String,String > params= ComParamsAddTool.getParam();
        params.put("userid", FtpApplication.user.getId());
        params.put("typename", name);
        mServicesTool.doPostAndalysisData("apiGifttypeCtrl.do?doAdd",params,MyPullToRefreshBaseFragment.ADD_CHILD_CODE);
    }


    /**
     * 添加分类
     */
    private void showAddView() {
        final MyAlertDialog.MyBuilder dialog1 = new MyAlertDialog.MyBuilder(
                this);
        dialog1.setTitle("添加礼金类型");
        dialog1.setAutoDismiss(false, true);
        LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        View dialog_view = mLayoutInflater.inflate(
                R.layout.tool_alert_edit_text, null);
        dialog1.setContentView(dialog_view);
        final EditText editText = (EditText) dialog_view
                .findViewById(R.id.nickname_edit_aty_editor);
        editText.setTextColor(getResources().getColor(R.color.font_com_content_black_color));
        editText.setHint("输入礼金类型");
        editText.setHintTextColor(getResources().getColor(R.color.com_hint_font_gray_color));
        editText.addTextChangedListener(new TxtLengthRestrictTool(editText, 20));
        editText.setSelection(editText.getText().length());
        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String str = editText.getText().toString().trim();
                addGiftType(str);
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
    private boolean isGetGroupMembering=false;
    private static final int GET_GROUP_MEMBER_CODE=8635;
    public void showGroup()
    {
        if(isGetGroupMembering)
            return ;
        isGetGroupMembering=true;
        DropDownBoxTool tool = new DropDownBoxTool();
        tool.showDialog("选择分组", sidekickerGroups, 1,
                this, null, new DropDownBoxTool.Callback() {
                    @Override
                    public void complate(String key, String value) {
                        if(membersNum.get(key)<=0)
                        {
                            ToastShowTool.myToastShort(GiftMoneyAddNewAct.this,"该组下未有亲友！");
                            isGetGroupMembering=false;
                            return ;
                        }
                        Map<String, String> params = ComParamsAddTool.getParam();
                        params.put("userid", FtpApplication.user.getId());
                        params.put("gourpid", key);
                        ProgressDialogTool.getInstance(GiftMoneyAddNewAct.this).showDialog("获取收礼人");
                        mServicesTool.doPostAndalysisData("apiGroupmemberCtrl.do?datagrid", params, GET_GROUP_MEMBER_CODE);
                    }
                }, new DialogSheetzAction.CanelCallback() {
                    @Override
                    public void canelCallback() {
                        isGetGroupMembering=false;
                    }
                });
    }
    public void showGroupMember()
    {
        DropDownBoxTool tool = new DropDownBoxTool();
        tool.showDialog("选择收礼人", groupmembers, 1,
                this, nameTv, new DropDownBoxTool.Callback() {
                    @Override
                    public void complate(String key, String value) {
                    }
                });
    }
    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            setTvSelect((TextView) v);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==33)
            ToastShowTool.myToastShort(this,"1111");
    }
}
