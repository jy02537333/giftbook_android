package com.zxw.giftbook.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.Activity.entitiy.MembergiftmoneyEntity;
import com.zxw.giftbook.Activity.menu.HomeFragment;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.GroupMemberAdapter;
import com.zxw.giftbook.adapter.HomeJournalAccountAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.MenuSettingViewInit;
import com.zxw.giftbook.view.ListViewEmptyView;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ServicesTool;
import pri.zxw.library.tool.WebGetDataTool;
import pri.zxw.library.view.TitleBar;

/**
 * 功能 组成员列表
 * Createdy 张相伟
 * 2016/11/7.
 */

public class GroupMemberAct extends MyPullToRefreshBaseActivity {
    GroupMemberAdapter mAdapter;
    AppServerTool mServicesTool;
    com.handmark.pulltorefresh.library.PullToRefreshListView listView;
    TitleBar titleBar;
    ListViewEmptyView emptyView;
    String id,groupName;
    double tatolMoney=0,itemMoney=0;
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
                listView.onRefreshComplete();
            }else if(msg.what==LOAD_CODE)
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
    }
    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.act_group_member_list_title_bar);
        listView=(com.handmark.pulltorefresh.library.PullToRefreshListView)findViewById(R.id.act_group_member_list_lv);
        emptyView=(ListViewEmptyView) findViewById(R.id.act_group_member_list_empty);
        titleBar.setText(groupName);
    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
        mAdapter=new GroupMemberAdapter(this);
        listView.setAdapter(mAdapter);
        listView.setEmptyView(emptyView);
        this.initListener(listView,mAdapter);
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
        Map<String,String> params= ComParamsAddTool.getPageParam(this);
        params.put("userid", FtpApplication.user.getId());
        params.put("gourpid", id);
        mServicesTool.doPostAndalysisData(GET_DATA_URL,params,GET_DATA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==GO_ADD&&resultCode==1)
        {
            listView.setRefreshing(true);
        }
    }
}
