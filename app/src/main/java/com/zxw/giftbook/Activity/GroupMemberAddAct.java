package com.zxw.giftbook.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pri.zxw.library.refresh_tool.SwipeRecyclerView;
import com.zxw.giftbook.Activity.contact.PhoneContactListSelectActivity;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.GroupMemberAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.myinterface.IDataMapUtilCallback;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.DataMapUtil;
import com.zxw.giftbook.view.ListViewEmptyView;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.entity.ComIdNameInfo;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.NetUtil;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.DialogSheetzAction;
import pri.zxw.library.tool.dialogTools.DropDownBoxTool;
import pri.zxw.library.view.TitleBar;

/**
 * 功能  组成员添加
 * Createdy 张相伟
 * 2016/11/6.
 */

public class GroupMemberAddAct extends MyBaseActivity {
    List<ComIdNameInfo> idList;
    String ids;
    boolean isSubmit=false;
    AppServerTool mServicesTool;
    TitleBar titleBar;
    /**关联人所在组*/
    String affiliatedGroup;
    public static final String GET_ADD_URL="apiGroupmemberCtrl.do?doAdd";
    public static final int GET_GROUP_MEMBER_CODE=4444;
    /**到添加关联人界面*/
    public static final int ADD_AFFILIATED_PERSON=3333;
    public final static int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1082;
    /**到导入界面*/
    public static final int ADD_IMPORT_CODE=6767;
    TreeMap<String,String>  groupmembers=new TreeMap<>();
    TreeMap<String,String> sidekickerGroups=new TreeMap<>();
    TextView
            /**关联人*/
            affiliated_personTv,affiliated_personAddTv,importTv,
    /**组类型*/
            typeTv,typeAddTv;
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
                int ret=messageHandlerTool.handler(msg,GroupMemberAddAct.this);
                if(ret==1)
                {
                    ToastShowTool.myToastShort(GroupMemberAddAct.this,"添加成功！");
                   setResult(1);
                    finish();
                }else
                {
                    ToastShowTool.myToastShort(GroupMemberAddAct.this,"添加失败！");
                }
                isSubmit=false;
            }
            else if(msg.what==GET_GROUP_MEMBER_CODE)
            {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                Type type=new TypeToken<List<GroupmemberEntity>>(){}.getType();
                List<GroupmemberEntity> data =(List<GroupmemberEntity>)
                        messageHandlerTool.handlerObject(msg,type, GroupMemberAddAct.this);
                if (data!=null&&data.size()> 0) {
                    try {
                        for (int i = 0; i<data.size(); i++) {
                            groupmembers.put(data.get(i).getId(),data.get(i).getGroupmember());
                        }
                        showGroupMember();
                    } catch (Exception e) {
                    }
                }else
                ToastShowTool.myToastShort(GroupMemberAddAct.this,"该组下没人！");
                isGetGroupMembering=false;
            }
            ProgressDialogTool.getInstance(GroupMemberAddAct.this).dismissDialog();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_group_member_add);
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
                Intent intent=new Intent(GroupMemberAddAct.this,GroupMemberAddAct.class);
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
        importTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requestPermissionsContact())
                {
                    return ;
                }
//                Intent intent=new Intent(GroupMemberAddAct.this,PhoneContactListSelectActivity.class);
//                intent.putExtra("isImport",true);
//                intent.putExtra("typeid",typeId);
//                intent.putExtra("typename",typeName);
//                startActivityForResult(intent,ADD_IMPORT_CODE);
            }
        });
    }
    public boolean requestPermissionsContact() {
        /**
         * 判断是否获取到相机权限
         */
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)) {//是否请求过该权限

            }else {//没有则请求获取权限，示例权限是：相机权限和定位权限，需要其他权限请更改或者替换
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }else {		//如果已经获取到了权限则直接进行下一步操作
            Intent intent=new Intent(GroupMemberAddAct.this,PhoneContactListSelectActivity.class);
            intent.putExtra("isImport",true);
            intent.putExtra("typeid",typeId);
            intent.putExtra("typename",typeName);
            startActivityForResult(intent,ADD_IMPORT_CODE);
        }


        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
//                ToastShowTool.myToastLong(this,"未授权访问联系人！");
//                return true;
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent=new Intent(GroupMemberAddAct.this,PhoneContactListSelectActivity.class);
                    intent.putExtra("isImport",true);
                    intent.putExtra("typeid",typeId);
                    intent.putExtra("typename",typeName);
                    startActivityForResult(intent,ADD_IMPORT_CODE);
                } else {
                    ToastShowTool.myToastLong(this,"未授权访问联系人！");
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
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
        params.put("userid", FtpApplication.user.getId());
        params.put("gourpid", typeId);
        params.put("gourpName",typeName );
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
        mServicesTool.doPostAndalysisData(GET_ADD_URL,params,GET_ADD_CODE);
    }

    public void getDropDownData()
    {
        Map<String, SidekickergroupEntity> obj= DataMapUtil.getAllTypeData(this,true,false, new IDataMapUtilCallback() {
            @Override
            public void onSuccess(boolean isSuccess) {
                if(isSuccess)
                {
                    Map<String, SidekickergroupEntity> obj1= DataMapUtil.getAllTypeData(GroupMemberAddAct.this, null);
                    initData(obj1);
                }
            }

            @Override
            public void onFailure(boolean isFailure) {
                ToastShowTool.myToastShort(GroupMemberAddAct.this,"加载异常！");
            }
        });
        initData(obj);
    }
    void initData( Map<String, SidekickergroupEntity> obj){
        if(obj!=null)
        {
            for (Map.Entry<String, SidekickergroupEntity> item:obj.entrySet()                 ) {
                sidekickerGroups.put(item.getKey(),item.getValue().getGroupname());//+"("+item.getValue().getGroupmembersnum()+")");
            }
        }
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
        tool.showDialog("选择关联人", groupmembers, 1,
                this, affiliated_personTv, new DropDownBoxTool.Callback() {
                    @Override
                    public void complate(String key, String value) {
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_IMPORT_CODE)
        {
            if (resultCode == 1) {
                setResult(1);
                finish();
            } else {

            }
        }
    }
}
