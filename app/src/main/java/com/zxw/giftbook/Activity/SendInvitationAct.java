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
import pri.zxw.library.refresh_tool.SwipeRecyclerView;
import com.zxw.giftbook.Activity.entitiy.ReceivesInvitationEntity;
import com.zxw.giftbook.Activity.entitiy.VSendInvitationEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.ReceivesInvitationAdapter;
import com.zxw.giftbook.adapter.SendInvitationAdapter;
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
 * 功能 我发送的请帖
 * Createdy 张相伟
 * 2017/6/2.
 */

public class SendInvitationAct extends MyPullToRefreshBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_send_invitation);
    }
    TitleBar titleBar;
    View view;
    AppServerTool mServicesTool;
    SendInvitationAdapter adapter;
    SwipeRecyclerView listView;
    public static final String GET_DATA_URL="apiVSendInvitationController.do?getList";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<List<VSendInvitationEntity>>(){}.getType();
                MessageHandlerTool.MessageInfo msgInfo = messageHandlerTool.handler(msg,SendInvitationAct.this,adapter,type);
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
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.a_send_invitation);
//        initView();
//        initTool();
//        initListener();
//        listLoad(mHandler);
    }

    public void initView()
    {
        titleBar=(TitleBar) view.findViewById(R.id.a_send_invitation_title_bar);
        listView=(SwipeRecyclerView)view.findViewById(R.id.a_send_invitation_lv);
        Drawable top_edit=getResources().getDrawable(R.mipmap.top_edit);
        top_edit.setBounds(0, 0, top_edit.getMinimumWidth(), top_edit.getMinimumHeight());
        titleBar.setRightDrawable(top_edit,null,null,null);


    }
    void initTool()
    {
//        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
//        adapter=new SendInvitationAdapter(this);
//        listView.setAdapter(adapter);
//        this.initListener(listView,adapter);

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
                Intent intent=new Intent(SendInvitationAct.this,AffairEditAct.class);
                startActivityForResult(intent,ADD_CHILD_CODE);
            }
        });
    }


    @Override
    public void getWebData() {
        Map<String,String> params= ComParamsAddTool.getPageParam(this);
        params.put("create_id", FtpApplication.getInstance().getUser().getId());
        mServicesTool.doPostAndalysisData(GET_DATA_URL,params,GET_DATA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ADD_CHILD_CODE&&resultCode==1)
        {
            listView.setRefreshing(true);
        }
    }
}
