package com.zxw.giftbook.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.DialogSheetzAction;
import pri.zxw.library.tool.dialogTools.DropDownBoxTool;
import pri.zxw.library.view.TitleBar;

/**
 * 功能  组成员编辑
 * Createdy 张相伟
 * 2016/11/6.
 */

public class GroupMemberEditAct extends MyBaseActivity {
    boolean isSubmit=false;
    AppServerTool mServicesTool;
    TitleBar titleBar;
    /**关联人所在组*/
    String affiliatedGroup;
    GroupmemberEntity entity;
    public static final String GET_ADD_URL="apiGroupmemberCtrl.do?doUpdate";
    public static final int GET_GROUP_MEMBER_CODE=4444;
    /**到添加关联人界面*/
    public static final int ADD_AFFILIATED_PERSON=3333;

    TreeMap<String,String>  groupmembers=new TreeMap<>();
    TreeMap<String,String> sidekickerGroups=new TreeMap<>();
    TextView
            /**关联人*/
            affiliated_personTv,affiliated_personAddTv,
    /**组类型*/
            typeTv,typeAddTv,importTv;
    EditText nameEdit;
    EditText phoneEdit;
    /**当前选择的组*/
    String typeId,typeName;
    Button submitBtn;
    boolean isAddAffiliated=false;
    /**
     * 是否获取组成员中
     */
    boolean isGetGroupMembering=false;
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_ADD_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                int ret=messageHandlerTool.handler(msg,GroupMemberEditAct.this);
                if(ret==1)
                {
                    ToastShowTool.myToastShort(GroupMemberEditAct.this,"修改成功！");
                   setResult(1);
                    finish();
                }else
                {
                    ToastShowTool.myToastShort(GroupMemberEditAct.this,"修改失败！");
                }
                isSubmit=false;
            }else if(msg.what==GET_DATA_CODE) {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                String data = messageHandlerTool.handlerData(msg, GroupMemberEditAct.this);
                if (data.length() > 0) {
                    try {
                        org.json.JSONObject jsonObj = new org.json.JSONObject(data);
                        JSONArray sidekickerGroupJsons=jsonObj.getJSONArray("sidekickerGroups");
                        for (int i=0;sidekickerGroupJsons!=null&&i<sidekickerGroupJsons.length();i++)
                        {
                            org.json.JSONObject obj=(org.json.JSONObject)sidekickerGroupJsons.get(i);
                            sidekickerGroups.put(obj.getString("id"),obj.getString("groupname"));
                        }

                    }catch (Exception e)
                    {
                    }
                }
            }
            else if(msg.what==GET_GROUP_MEMBER_CODE)
            {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                Type type=new TypeToken<List<GroupmemberEntity>>(){}.getType();
                List<GroupmemberEntity> data =(List<GroupmemberEntity>)
                        messageHandlerTool.handlerObject(msg,type, GroupMemberEditAct.this);
                if (data!=null&&data.size()> 0) {
                    try {
                        for (int i = 0; i<data.size(); i++) {
                            groupmembers.put(data.get(i).getId(),data.get(i).getGroupmember());
                        }
                        showGroupMember();
                    } catch (Exception e) {
                    }
                }else
                ToastShowTool.myToastShort(GroupMemberEditAct.this,"该组下没人！");
                isGetGroupMembering=false;
            }
            ProgressDialogTool.getInstance(GroupMemberEditAct.this).dismissDialog();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_group_member_add);
        entity=(GroupmemberEntity) getIntent().getSerializableExtra("entity");
        typeId=getIntent().getStringExtra("id");
        typeName=getIntent().getStringExtra("groupName");
        isAddAffiliated=getIntent().getBooleanExtra("isAddAffiliated",false);
        initView();
        initTool();
        initListener();
        getDropDownData();
    }

    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.act_group_member_add_title_bar);
        nameEdit=(EditText) findViewById(R.id.act_group_member_add_name_edit);
        phoneEdit=(EditText) findViewById(R.id.act_group_member_add_phone_edit);
        affiliated_personTv=(TextView) findViewById(R.id.act_group_member_add_affiliated_person_tv);
        affiliated_personAddTv=(TextView) findViewById(R.id.act_group_member_add_affiliated_person_add_tv);
        importTv=(TextView) findViewById(R.id.act_group_member_add_improt_tv);
        importTv.setVisibility(View.GONE);
        typeTv=(TextView) findViewById(R.id.act_group_member_add_type_tv);
        typeAddTv=(TextView) findViewById(R.id.act_group_member_add_type_add_tv);
        submitBtn=(Button) findViewById(R.id.act_group_member_add_btn);
        if(typeId!=null)
            typeTv.setText(typeName);
        if(isAddAffiliated)
        {
            affiliated_personTv.setVisibility(View.GONE);
            affiliated_personAddTv.setVisibility(View.GONE);
        }
        nameEdit.setText(entity.getGroupmember());
        nameEdit.setTag(entity.getGourpid());
        if(entity.getMemberphone()!=null&&entity.getMemberphone().trim().length()>0)
             phoneEdit.setText(entity.getMemberphone());
        if(entity.getAffiliatedpersonid()!=null&&entity.getAffiliatedpersonid().trim().length()>0)
             affiliated_personTv.setText(entity.getAffiliatedperson());
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
        affiliated_personTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroup();
            }
        });
        typeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showType();
            }
        });

        affiliated_personAddTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupMemberEditAct.this,GroupMemberEditAct.class);
                intent.putExtra("isAddAffiliated",true);
                startActivityForResult(intent,ADD_AFFILIATED_PERSON);
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
        if(nameEdit.getText().toString().trim().length()==0)
        {
            ToastShowTool.myToastShort(this,"请输入成员姓名！");
            return ;
        }
        if(typeId==null)
        {
            ToastShowTool.myToastShort(this,"请选择组！");
            return ;
        }
        Map<String,String > params= ComParamsAddTool.getParam();
        params.put("id",entity.getId());
        params.put("userid", FtpApplication.user.getId());
        params.put("gourpid", typeId);
        params.put("groupmember",nameEdit.getText().toString().trim());
        params.put("totalmoney", "0");
        if(affiliated_personTv.getTag()!=null) {
            params.put("affiliatedpersonid", affiliated_personTv.getTag().toString());
            params.put("affiliatedperson", affiliated_personTv.getText().toString());
        }
        if(phoneEdit.getText().toString().trim().length()>0)
        params.put("memberphone", phoneEdit.getText().toString());
        params.put("createBy", FtpApplication.user.getId());
        params.put("createName", FtpApplication.user.getUsername());
        if(isSubmit)
            return;
        isSubmit=true;
        mServicesTool.doPostAndalysisDataCall(GET_ADD_URL, params, GET_ADD_CODE, new IServicesCallback() {
            @Override
            public void onStart() {
                ProgressDialogTool.getInstance(GroupMemberEditAct.this).showDialog("修改....");
            }

            @Override
            public void onEnd() {
                ProgressDialogTool.getInstance(GroupMemberEditAct.this).dismissDialog();
                isSubmit=false;
            }
        });
    }

    public void getDropDownData()
    {
        ProgressDialogTool.getInstance(this).showDialog("加载中...");
        Map<String,String > params= ComParamsAddTool.getParam();
        params.put("userid", FtpApplication.user.getId());
        mServicesTool.doPostAndalysisData("apiAllTypeCtrl.do?getAll",params,GET_DATA_CODE);
    }
    public void showType()
    {
        DropDownBoxTool tool = new DropDownBoxTool();
        tool.showDialog("选择分组", sidekickerGroups, 1,
                this, typeTv, new DropDownBoxTool.Callback() {
                    @Override
                    public void complate(String key, String value) {
                        typeId=key;
                        typeName=value;
                    }
                });
    }


    public void showGroup()
    {
        if(isGetGroupMembering)
            return ;
        isGetGroupMembering=true;
        DropDownBoxTool tool = new DropDownBoxTool();
        tool.showDialog("选择关联人分组", sidekickerGroups, 1,
                this, affiliated_personTv, new DropDownBoxTool.Callback() {
                    @Override
                    public void complate(String key, String value) {
                        affiliated_personTv.setText("");
                        affiliatedGroup=key;
                        Map<String,String > params= ComParamsAddTool.getParam();
                        params.put("userid", FtpApplication.user.getId());
                        params.put("gourpid", affiliatedGroup);
                        ProgressDialogTool.getInstance(GroupMemberEditAct.this).showDialog("获取关联人");
                        mServicesTool.doPostAndalysisData("apiGroupmemberCtrl.do?datagrid",params,GET_GROUP_MEMBER_CODE);
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
        tool.showDialog("选择关联人", groupmembers, 1,
                this, affiliated_personTv, new DropDownBoxTool.Callback() {
                    @Override
                    public void complate(String key, String value) {
                    }
                });
    }

}
