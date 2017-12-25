package com.zxw.giftbook.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.GroupMemberAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.PopShowDelAndEditOperateTool;
import com.zxw.giftbook.view.ListViewEmptyView;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.refresh_tool.SwipeRecyclerView;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.MyAlertDialog;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.view.TitleBar;

/**
 * 功能 组成员列表
 * Createdy 张相伟
 * 2016/11/7.
 */

public class GroupMemberAct extends MyPullToRefreshBaseActivity {
    GroupMemberAdapter mAdapter;
    AppServerTool mServicesTool;
    SwipeRecyclerView listView;
    TitleBar titleBar;
    ListViewEmptyView emptyView;
    String id,groupName;
    View rootView;
    int mPosition=-1;
    double tatolMoney=0,itemMoney=0;
    public static final String EDIT_URL="apiGroupmemberCtrl.do?doUpdate";
    public static final String DEL_URL="apiGroupmemberCtrl.do?doDel";
    public static final String GET_DATA_URL="apiGroupmemberCtrl.do?datagrid";

    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<List<GroupmemberEntity>>(){}.getType();
                Object msgInfo=   messageHandlerTool.handlerObject(msg,type,GroupMemberAct.this);
                // MessageHandlerTool.MessageInfo msgInfo1 =  messageHandlerTool.handler(msg,GroupMemberAct.this,mAdapter,listView,type);
                if(msgInfo!=null )  //&&msgInfo.getIsHashValue())
                {
                    List<GroupmemberEntity> list=( List<GroupmemberEntity>)msgInfo;
                    itemMoney=0;
                    for (GroupmemberEntity item:list)
                    {
                        double amount=0;
                        for(GroupmemberEntity childItem:item.getAffiliatedpersonList())
                        {
                            amount+=childItem.getTotalmoney();
                            itemMoney+=amount;
                        }
                        item.setAffiliatedpersonidAmount(amount);
                    }
                    if(mUpfalg)
                    {
                        mAdapter.remove();
                        tatolMoney=0;
                    }
                    tatolMoney=tatolMoney+itemMoney;
                    mAdapter.addDataAll(list);
                    mAdapter.notifyDataSetChanged();

                }
                onComplete();
                closePullUpToRefresh();

            }
            else if(msg.what==DEL_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                int ret=messageHandlerTool.handler(msg,GroupMemberAct.this);
                if(ret==1)
                {
                    mAdapter.removeItem(mPosition);
                    mAdapter.notifyDataSetChanged();
                }
            }
            else if(msg.what==LOAD_CODE)
                listView.setRefreshing(true);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_group_member_list);
        groupName= getIntent().getStringExtra("groupName");
        id= getIntent().getStringExtra("id");
        initView();
        initTool();
        initListener();
        listLoad(mHandler);
        closePullUpToRefresh();
    }
    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.act_group_member_list_title_bar);
        listView=(SwipeRecyclerView) findViewById(R.id.act_group_member_list_lv);
        emptyView=(ListViewEmptyView) findViewById(R.id.act_group_member_list_empty);
        titleBar.setText(groupName);
        rootView=titleBar.getRootView();
    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
        mAdapter=new GroupMemberAdapter(this);
        listView.setAdapter(mAdapter);
        listView.setEmptyView(emptyView);
        this.initListener(listView,mAdapter, SwipeRecyclerView.Mode.CLOSE_END);
    }
    public void initListener()
    {
        titleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                listView.setRefreshing(true);
            }
        });
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupMemberAct.this,GroupMemberAddAct.class);
                intent.putExtra("id",id);
                intent.putExtra("groupName",groupName);
                startActivityForResult(intent,GO_ADD);
            }
        });
    }



    @Override
    public void getWebData() {
        setRows(200);
        Map<String,String> params= ComParamsAddTool.getPageParam(this);
        params.put("userid", FtpApplication.user.getId());
        params.put("gourpid", id);
        mServicesTool.doPostAndalysisData(GET_DATA_URL,params,GET_DATA_CODE);
    }
    public void operate( String id,  String name,int poistion)
    {
        mPosition=poistion;
        PopShowDelAndEditOperateTool.show(this,id,name,  rootView,
                new PopShowDelAndEditOperateTool.DelAndEditCallback(){
                    @Override
                    public void onDelComplate(String id, String name) {
                        del(id,name);
                    }
                    @Override
                    public void onEditComplate(String id, String name) {
                        showEditView(id,name);
                    }
                });
    }
    void del(final String id,String name)
    {

        final MyAlertDialog.MyBuilder dialog1 = new MyAlertDialog.MyBuilder(this);
        dialog1.setTitle("删除成员："+name);
        dialog1.setAutoDismiss(false, true);
        LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        View dialog_view = mLayoutInflater.inflate(
                R.layout.tool_alert_edit_text, null);
        dialog1.setContentView(dialog_view);
        final EditText editText = (EditText) dialog_view
                .findViewById(R.id.nickname_edit_aty_editor);
        editText.setVisibility(View.GONE);
        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog1.dismiss();
                if(isSub)
                    return;
                isSub=true;
                Map<String,String> params= ComParamsAddTool.getParam();
                params.put("id", id);
                mServicesTool.doPostAndalysisDataCall(DEL_URL, params, DEL_CODE, new IServicesCallback() {
                    @Override
                    public void onStart() {
                        ProgressDialogTool.getInstance(GroupMemberAct.this).showDialog("删除....");
                        isSub=false;
                    }

                    @Override
                    public void onEnd() {
                        ProgressDialogTool.getInstance(GroupMemberAct.this).dismissDialog();
                        isSub=false;
                    }
                });

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
    void editSubmit(String id,String updateNameStr)
    {
        if(updateNameStr.trim().length()==0)
        {
            ToastShowTool.myToastShort(this,"");
            return ;
        }
        if(isSub)
            return;
        isSub=true;
        Map<String,String> params= ComParamsAddTool.getParam();
        params.put("id", id);
        params.put("groupname",updateNameStr);
        mServicesTool.doPostAndalysisDataCall(EDIT_URL, params, EDIT_CODE, new IServicesCallback() {
            @Override
            public void onStart() {
                ProgressDialogTool.getInstance(GroupMemberAct.this).showDialog("修改....");
                isSub=false;
            }

            @Override
            public void onEnd() {
                ProgressDialogTool.getInstance(GroupMemberAct.this).dismissDialog();
                isSub=false;
            }
        });
    }

    /**
     * 编辑亲友信息
     */
    private void showEditView(final String id, final String oldName) {
        GroupmemberEntity entity= mAdapter.getItem(mPosition);
        Intent intent=new Intent(this,GroupMemberEditAct.class);
        intent.putExtra("entity",entity);
        intent.putExtra("id",id);
        intent.putExtra("groupName",groupName);
        startActivityForResult(intent,EDIT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==1)
        {
            listView.setRefreshing(true);
        }
    }
}
