package com.zxw.giftbook.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxw.giftbook.Activity.entitiy.ReceivingGiftEntity;
import com.zxw.giftbook.Activity.entitiy.ReceivingGiftsMoneyEntity;
import com.zxw.giftbook.Activity.menu.ReceivingGIftFragment;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.HomeJournalAccountAdapter;
import com.zxw.giftbook.adapter.ReceivingGiftItemMoneyAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseActivity;
import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.view.TitleBar;

/**
 * 功能 收礼事件，详细列表
 * Createdy 张相伟
 * 2017/6/4.
 */

public class ReceivingGiftItemMoneyListAct  extends MyPullToRefreshBaseActivity{

    TitleBar titleBar;
    AppServerTool mServicesTool;
    ReceivingGiftItemMoneyAdapter adapter;
    PullToRefreshListView listView;
    String id;
    String typeId="";
    String typeName="";
    String title;
    public static final String DEL_URL="apiReceivingGiftsMoneyController.do?doDel";
    public static final String GET_DATA_URL="apiReceivingGiftsMoneyController.do?datagrid";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<List<ReceivingGiftsMoneyEntity>>(){}.getType();
                MessageHandlerTool.MessageInfo msgInfo = messageHandlerTool.handler(msg,ReceivingGiftItemMoneyListAct.this,adapter,listView,type);
            }
            else if(msg.what==LOAD_CODE)
            {
                listView.setRefreshing(true);
            }
            else if(msg.what==DEL_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                int ret = messageHandlerTool.handler(msg,ReceivingGiftItemMoneyListAct.this);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_receiving_gift_item_money_list);
        id=getIntent().getStringExtra("id");
        typeId=getIntent().getStringExtra("typeId");
        typeName=getIntent().getStringExtra("typeName");
        title=getIntent().getStringExtra("title");
        initView();
        initTool();
        initListener();
        listLoad(mHandler);
    }



    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.a_receives_gift_item_money_title_bar);
        listView=(PullToRefreshListView)findViewById(R.id.a_receives_gift_item_money_lv);
        titleBar.setText(title);
    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
        adapter=new ReceivingGiftItemMoneyAdapter(this);
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
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReceivingGiftItemMoneyListAct.this, AddReceivingGiftIitemMoneyAct.class);
                intent.putExtra("parentId",id);
                intent.putExtra("typeId",typeId);
                intent.putExtra("typeName",typeName);
                startActivityForResult(intent,GET_ADD_CODE);
            }
        });
    }
    public void del(String id)
    {
        if(isSub)
            return ;
        Map<String,String> params= ComParamsAddTool.getParam();
        params.put("id",id);
        mServicesTool.doPostAndalysisDataCall(DEL_URL,params,DEL_CODE, new IServicesCallback() {
            @Override
            public void onStart() {
                ProgressDialogTool.getInstance(ReceivingGiftItemMoneyListAct.this).showDialog("删除中...");
                isSub=true;
            }

            @Override
            public void onEnd() {
                ProgressDialogTool.getInstance(ReceivingGiftItemMoneyListAct.this).dismissDialog();
                isSub=false;
            }
        });
    }

    @Override
    public void getWebData() {
        if(isSub)
            return ;
        Map<String,String> params= ComParamsAddTool.getPageParam(this);
        params.put("correlativeinvitation",id);
        params.put("create_by", FtpApplication.getInstance().getUser().getId());
        mServicesTool.doPostAndalysisDataCall(GET_DATA_URL, params, GET_DATA_CODE, new IServicesCallback() {
            @Override
            public void onStart() {
                isSub=true;
            }

            @Override
            public void onEnd() {
                isSub=false;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==GET_ADD_CODE&&resultCode==1)
        {
            listView.setRefreshing(true);
        }
    }
}
