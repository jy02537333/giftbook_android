package com.zxw.giftbook.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxw.giftbook.Activity.entitiy.ReceivesInvitationEntity;
import com.zxw.giftbook.Activity.entitiy.VInvitationListAndGroupEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.ReceivesInvitationAdapter;
import com.zxw.giftbook.adapter.SendInvitationDetailListAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.view.TitleBar;

/**
 * 发送的请帖人员明细列表
 * Createdy 张相伟
 * 2017/5/20.
 */

public class SendInvitationListAct extends MyPullToRefreshBaseActivity {

    TitleBar titleBar;
    String parentId;
    AppServerTool mServicesTool;
    SendInvitationDetailListAdapter adapter;
    PullToRefreshListView listView;
    public static final String GET_DATA_URL="apiVInvitationListAndGroupController.do?datagrid";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<List<VInvitationListAndGroupEntity>>(){}.getType();
                MessageHandlerTool.MessageInfo msgInfo = messageHandlerTool.handler(msg,SendInvitationListAct.this,adapter,listView,type);
                String sum=  msgInfo.getRetMap().get("sumCount");
                if(sum!=null)
                {

                }
            }
            else if(msg.what==LOAD_CODE)
            {
                listView.setRefreshing(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_send_invitation_detail_list);
        parentId=getIntent().getStringExtra("parentid");
        initView();
        initTool();
        initListener();
        listLoad(mHandler);
    }

    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.a_send_invitation_detail_list_title_bar);
        listView=(PullToRefreshListView)findViewById(R.id.a_send_invitation_detail_list_lv);
//        Drawable top_edit=getResources().getDrawable(R.mipmap.top_edit);
//        top_edit.setBounds(0, 0, top_edit.getMinimumWidth(), top_edit.getMinimumHeight());
//        titleBar.setRightDrawable(top_edit,null,null,null);


    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
        adapter=new SendInvitationDetailListAdapter(this);
        listView.setAdapter(adapter);
        this.initListener(listView,adapter);

    }
    public void initListener()
    {
        titleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position=position-1;
//                VInvitationListAndGroupEntity invitationEntity= adapter.getItem(position);
//                Intent intent= new Intent(SendInvitationListAct.this,ReceivesInvitationDetailAct.class);
//                intent.putExtra("invitationId",invitationEntity.getId());
//                intent.putExtra("inviterId",invitationEntity.getInvitationlistEntityList().get(0).getId());
//                startActivity(intent);
            }
        });
//        titleBar.setRightClickListener(new TitleOnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(this, GiftMoneyAddNewAct.class);
//                startActivityForResult(intent,GET_ADD_CODE);
//            }
//        });
    }


    @Override
    public void getWebData() {
        Map<String,String> params= ComParamsAddTool.getPageParam(this);
        params.put("userid", FtpApplication.getInstance().getUser().getId());
        params.put("rows","1000");
        params.put("invitationid",parentId);
        mServicesTool.doPostAndalysisData(GET_DATA_URL,params,GET_DATA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==GET_ADD_CODE&&resultCode==1)
        {
            listView.setRefreshing(true);
        }
    }
}
