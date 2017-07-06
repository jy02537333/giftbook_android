package com.zxw.giftbook.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.entitiy.GifttypeEntity;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.myinterface.IDataMapUtilCallback;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.DataMapUtil;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.listener.TxtLengthRestrictTool;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.MyAlertDialog;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.DialogSheetzAction;
import pri.zxw.library.tool.dialogTools.DropDownBoxTool;
import pri.zxw.library.view.TitleBar;

/**
 * 添加收礼信息,旧格式
 * Createdy 张相伟
 * 2017/6/4.
 */

public class AddReceivingGiftIitemMoneyOldAct extends MyBaseActivity {
    boolean isSubmit=false;
    AppServerTool mServicesTool;

    public static final String ADD_URL="apiMembergiftmoneyCtrl.do?doAdd";

    TreeMap<String,String> giftTypeMap=new TreeMap<>();
    TreeMap<String,String> sidekickerGroups=new TreeMap<>();
    TreeMap<String,String>  groupmembers=new TreeMap<>();
    TreeMap<String,Integer> membersNum=new TreeMap<>();

    @InjectView(R.id.a_receiving_gift_add_title_bar)
    TitleBar titleBar;
            /**组类型*/
    @InjectView(R.id.a_receiving_gift_add_type_tv)
    TextView        typeTv;
    @InjectView(R.id.a_receiving_gift_add_name_tv)
    TextView nameTv;
    @InjectView(R.id.a_receiving_gift_add_money_edit)
    EditText moneyEdit;
    @InjectView(R.id.a_receiving_gift_add_member_add_tv)
    TextView searchMemberTv;
    @InjectView(R.id.a_receiving_gift_add_type_add_tv)
    TextView addGiftTypeTv;
    @InjectView(R.id.a_receiving_gift_add_submit_btn)
    Button submitBtn;
    String typeId,typeName;
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what== MyPullToRefreshBaseFragment.GET_ADD_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                int ret=messageHandlerTool.handler(msg,AddReceivingGiftIitemMoneyOldAct.this,"添加失败");
                if(ret==1)
                {
                    ToastShowTool.myToastShort(AddReceivingGiftIitemMoneyOldAct.this,"添加成功！");
                    setResult(1);
                    finish();
                }else if(msg.arg1<10)
                {
                    ToastShowTool.myToastShort(AddReceivingGiftIitemMoneyOldAct.this,"添加失败！");
                }
                isSubmit=false;
            }else if(msg.what==MyPullToRefreshBaseFragment.GET_DATA_CODE) {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                String data = messageHandlerTool.handlerData(msg, AddReceivingGiftIitemMoneyOldAct.this);
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
                GifttypeEntity obj=(GifttypeEntity)messageHandlerTool.handlerObject(msg,type,AddReceivingGiftIitemMoneyOldAct.this);
                if(obj!=null)
                {
                    giftTypeMap.put(obj.getId(),obj.getTypename() );
                    typeTv.setText(obj.getTypename());
                    typeTv.setTag(obj.getId());
                    ToastShowTool.myToastShort(AddReceivingGiftIitemMoneyOldAct.this,"添加成功！");
                }else
                {
                    ToastShowTool.myToastShort(AddReceivingGiftIitemMoneyOldAct.this,"添加失败！");
                }
            }
            else if(msg.what==GET_GROUP_MEMBER_CODE)
            {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                Type type=new TypeToken<List<GroupmemberEntity>>(){}.getType();
                List<GroupmemberEntity> data =(List<GroupmemberEntity>)
                        messageHandlerTool.handlerObject(msg,type, AddReceivingGiftIitemMoneyOldAct.this);
                if (data!=null&&data.size()> 0) {
                    try {
                        for (int i = 0; i<data.size(); i++) {
                            groupmembers.put(data.get(i).getId(),data.get(i).getGroupmember());
                        }
                        showGroupMember();
                    } catch (Exception e) {
                    }
                }else
                    ToastShowTool.myToastShort(AddReceivingGiftIitemMoneyOldAct.this,"该组下未有亲友！");
                isGetGroupMembering=false;
            }
            ProgressDialogTool.getInstance(AddReceivingGiftIitemMoneyOldAct.this).dismissDialog();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_receiving_gift_add_old);
        ButterKnife.inject(this);
        initView();
        initTool();
        initListener();
        getDropDownData();
    }

    public void initView()
    {
        if(typeId!=null)
            typeTv.setText(typeName);
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
        typeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showType();
            }
        });
        addGiftTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddView();
            }
        });
        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroup();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
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
        params.put("isexpenditure","0");
        ProgressDialogTool.getInstance(AddReceivingGiftIitemMoneyOldAct.this).showDialog("加载中...");
        mServicesTool.doPostAndalysisData(ADD_URL,params,MyPullToRefreshBaseFragment.GET_ADD_CODE);
    }

    public void getDropDownData()
    {
        Map<String, SidekickergroupEntity> obj= DataMapUtil.getAllTypeData(this, new IDataMapUtilCallback() {
            @Override
            public void onSuccess(boolean isSuccess) {
                if(isSuccess)
                {
                    Map<String, SidekickergroupEntity> obj1= DataMapUtil.getAllTypeData(AddReceivingGiftIitemMoneyOldAct.this, null);
                    initData(obj1);
                }
            }

            @Override
            public void onFailure(boolean isFailure) {
                ToastShowTool.myToastShort(AddReceivingGiftIitemMoneyOldAct.this,"加载异常！");
            }
        });
        initData(obj);
    }
    void initData( Map<String, SidekickergroupEntity> obj){
        if(obj!=null)
        {
            for (Map.Entry<String, SidekickergroupEntity> item:obj.entrySet()                 ) {
                sidekickerGroups.put(item.getKey(),item.getValue().getGroupname()+"("+item.getValue().getGroupmembersnum()+")");
                membersNum.put(item.getKey(),item.getValue().getGroupmembersnum());
            }
            for (Map.Entry<String, GifttypeEntity> item:   DataMapUtil.getGiftTypeMap() .entrySet()             ) {
                giftTypeMap.put(item.getKey(),item.getValue().getTypename());
            }
        }
    }
    public void showType()
    {
        DropDownBoxTool tool = new DropDownBoxTool();
        tool.showDialog("选择礼金类型", giftTypeMap, 1,
                this, typeTv, new DropDownBoxTool.Callback() {
                    @Override
                    public void complate(String key, String value) {
                        typeId=key;
                        typeName=value;
                    }
                });
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
                        if(membersNum.get(key)==0)
                        {
                            ToastShowTool.myToastShort(AddReceivingGiftIitemMoneyOldAct.this,"该组下未有亲友！");
                            isGetGroupMembering=false;
                            return ;
                        }
                        for(GroupmemberEntity entity : DataMapUtil.getGroupMember().get(key).getGroupmemberList())
                        {
                            groupmembers.put(entity.getId(),entity.getGroupmember());
                        }
                        showGroupMember();
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
}
