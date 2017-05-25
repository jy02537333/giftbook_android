package com.zxw.giftbook.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxw.giftbook.Activity.entitiy.ReceivesInvitationEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.ReceivesInvitationAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.base.MyPullToRefreshBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.view.TitleBar;

/**
 * 请帖邀请详情
 * Createdy 张相伟
 * 2017/5/20.
 */

public class ReceivesInvitationDetailAct extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_invitation_detail);
    }
    TitleBar titleBar;
    View view;
    AppServerTool mServicesTool;
    WebView wv;
    public static final String ADD_URL="apiInvitationController.do?doAdd";
    public static final String GET_DATA_URL="apiInvitationController.do?getList";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {
            }
            else if(msg.what==LOAD_CODE)
            {
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.a_receives_invitation);
        initView();
        initTool();
        initListener();
    }

    public void initView()
    {
        titleBar=(TitleBar) view.findViewById(R.id.a_invitation_detail_title_bar);
        wv=(WebView)view.findViewById(R.id.a_invitation_detail_wv);
       String invitationId= getIntent().getStringExtra("invitationId");
      String  inviterId=  getIntent().getStringExtra("inviterId");
        wv.loadUrl("apiInvitationController.do?=====&invitationId="+invitationId+"&inviterId="+inviterId);
//        Drawable top_edit=getResources().getDrawable(R.mipmap.top_edit);
//        top_edit.setBounds(0, 0, top_edit.getMinimumWidth(), top_edit.getMinimumHeight());
//        titleBar.setRightDrawable(top_edit,null,null,null);


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
    }


}
