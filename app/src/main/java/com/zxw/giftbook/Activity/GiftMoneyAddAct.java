package com.zxw.giftbook.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.entitiy.GifttypeEntity;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.Activity.entitiy.VGroupAndMemberEntity;
import com.zxw.giftbook.Activity.menu.SidekickerGroup2Fragment;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.GroupMemberSelectAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.myinterface.IDataMapUtilCallback;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.DataMapUtil;
import com.zxw.giftbook.utils.contact.ClearEditText;
import com.zxw.giftbook.view.SearchEditText;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.entity.ComInfo;
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
 * 礼金记录添加  使用中
 * Created by Administrator on 2016/11/8.
 */

public class GiftMoneyAddAct extends MyBaseActivity {
    boolean isSubmit=false;
    AppServerTool mServicesTool;
    TitleBar titleBar;
    public static final String ADD_URL="apiMembergiftmoneyCtrl.do?doAdd";
    public static final String GM_URL="apiGroupmemberCtrl.do?getFullMember";
    public static final int GM_CODE=9715;
    GroupMemberSelectAdapter groupMemberSelectAdapter;
    TreeMap<String,String> giftTypeMap=new TreeMap<>();
    TreeMap<String,String> sidekickerGroups=new TreeMap<>();
    TreeMap<String,String>  groupmembers=new TreeMap<>();
    TreeMap<String,Integer> membersNum=new TreeMap<>();
    List<VGroupAndMemberEntity> groupMemberList=new ArrayList<>();
    TextView
    /**组类型*/
    typeTv;
    String memberId;
    TextView nameTv;
    EditText moneyEdit;
    TextView searchMemberTv;
    TextView addGiftTypeTv;
    Button submitBtn;
    String typeId,typeName;
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what== MyPullToRefreshBaseFragment.GET_ADD_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                int ret=messageHandlerTool.handler(msg,GiftMoneyAddAct.this,"添加失败");
                if(ret==1)
                {
                    if(memberId==null||memberId.length()==0)
                    {
                        DataMapUtil.getAllTypeData(new GiftMoneyAddAct(),false,true, null );
                    }
                    ToastShowTool.myToastShort(GiftMoneyAddAct.this,"添加成功！");
                    setResult(1);
                    finish();
                }else if(msg.arg1<10)
                {
                    ToastShowTool.myToastShort(GiftMoneyAddAct.this,"添加失败！");
                }
                isSubmit=false;
            }else if(msg.what==MyPullToRefreshBaseFragment.GET_DATA_CODE) {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                String data = messageHandlerTool.handlerData(msg, GiftMoneyAddAct.this);
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
            else if(msg.what==GM_CODE) {//获取数据用户亲友信息
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                String data = messageHandlerTool.handlerData(msg, GiftMoneyAddAct.this);
                if (data.length() > 0) {
                    try {
                        Gson gson=new Gson();
                        Type type=new TypeToken<List<VGroupAndMemberEntity> >(){}.getType();
                        groupMemberList=gson.fromJson(data,type);
                        initAdapter();
                    }catch (Exception e)
                    {
                        e.getMessage();
                    }
                }
            }
            else if(msg.what==MyPullToRefreshBaseFragment.ADD_CHILD_CODE){
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<GifttypeEntity>(){}.getType();
                GifttypeEntity obj=(GifttypeEntity)messageHandlerTool.handlerObject(msg,type,GiftMoneyAddAct.this);
                if(obj!=null)
                {
                    giftTypeMap.put(obj.getId(),obj.getTypename() );
                    typeTv.setText(obj.getTypename());
                    typeTv.setTag(obj.getId());
                    typeId=obj.getId();
                    ToastShowTool.myToastShort(GiftMoneyAddAct.this,"添加成功！");
                    Intent intent = new Intent();
                    intent.setAction(SidekickerGroup2Fragment.SidekickerGroupBroadcast.SIDEKICKERGROUP_UPDATE);
                    sendBroadcast(intent);
                }else
                {
                    ToastShowTool.myToastShort(GiftMoneyAddAct.this,"添加失败！");
                }
            }
            else if(msg.what==GET_GROUP_MEMBER_CODE)
            {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                Type type=new TypeToken<List<GroupmemberEntity>>(){}.getType();
                List<GroupmemberEntity> data =(List<GroupmemberEntity>)
                        messageHandlerTool.handlerObject(msg,type, GiftMoneyAddAct.this);
                if (data!=null&&data.size()> 0) {
                    try {
                        for (int i = 0; i<data.size(); i++) {
                            groupmembers.put(data.get(i).getId(),data.get(i).getGroupmember());
                        }
                        showGroupMember();
                    } catch (Exception e) {
                    }
                }else
                    ToastShowTool.myToastShort(GiftMoneyAddAct.this,"该组下未有亲友！");
                isGetGroupMembering=false;
            }
            ProgressDialogTool.getInstance(GiftMoneyAddAct.this).dismissDialog();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_gift_money_add_old);
        initView();
        initTool();
        initListener();
        getDropDownData();
//        findFullMember();
    }

    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.act_gift_money_add_title_bar);
        nameTv=(TextView) findViewById(R.id.act_gift_money_add_name_edit);
//        nameLv=(ListView) findViewById(R.id.act_gift_money_add_name_lv);
        searchMemberTv=(TextView) findViewById(R.id.act_gift_money_add_member_add_tv);
        moneyEdit=(EditText) findViewById(R.id.act_gift_money_add_money_edit);
        addGiftTypeTv=(TextView) findViewById(R.id.act_gift_money_add_type_add_tv);
        typeTv=(TextView) findViewById(R.id.act_gift_money_add_type_tv);
        submitBtn=(Button)findViewById(R.id.act_gift_money_add_submit_btn);
        if(typeId!=null)
            typeTv.setText(typeName);
    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
         groupMemberSelectAdapter =new GroupMemberSelectAdapter(this);

    }
    void initAdapter()
    {
        groupMemberSelectAdapter.addData(groupMemberList);
        groupMemberSelectAdapter.getFilter().filter("");
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
                Intent intent=new Intent(GiftMoneyAddAct.this,MemberSearchAct.class);
                startActivityForResult(intent,1111);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        searchMemberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroup();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1111&&resultCode==1)
        {
            memberId=data.getStringExtra("giftMoneyUserId");
            nameTv.setText(data.getStringExtra("giftMoneyUserName"));
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
        params.put("gourpmemberid", memberId);
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
        ProgressDialogTool.getInstance(GiftMoneyAddAct.this).showDialog("加载中...");
        mServicesTool.doPostAndalysisData(ADD_URL,params,MyPullToRefreshBaseFragment.GET_ADD_CODE);
    }


    public void getDropDownData()
    {
        Map<String, SidekickergroupEntity> obj= DataMapUtil.getAllTypeData(this, new IDataMapUtilCallback() {
            @Override
            public void onSuccess(boolean isSuccess) {
                if(isSuccess)
                {
                    Map<String, SidekickergroupEntity> obj1= DataMapUtil.getAllTypeData(GiftMoneyAddAct.this, null);
                    initData(obj1);
                }
            }

            @Override
            public void onFailure(boolean isFailure) {
                ToastShowTool.myToastShort(GiftMoneyAddAct.this,"加载异常！");
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
                            ToastShowTool.myToastShort(GiftMoneyAddAct.this,"该组下未有亲友！");
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
