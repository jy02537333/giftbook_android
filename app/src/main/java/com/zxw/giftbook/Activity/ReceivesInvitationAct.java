package com.zxw.giftbook.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import pri.zxw.library.refresh_tool.SwipeRecyclerView;
import com.zxw.giftbook.Activity.entitiy.MembergiftmoneyEntity;
import com.zxw.giftbook.Activity.entitiy.ReceivesInvitationEntity;
import com.zxw.giftbook.Activity.menu.HomeFragment;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.HomeJournalAccountAdapter;
import com.zxw.giftbook.adapter.ReceivesInvitationAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.DateMapUtil;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.base.MyPullToRefreshBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.dialogTools.DropDownBoxTool;
import pri.zxw.library.view.TitleBar;

/**
 * 收到的请帖列表
 * Createdy 张相伟
 * 2017/5/20.
 */

public class ReceivesInvitationAct extends MyPullToRefreshBaseActivity {
    TitleBar titleBar;
    AppServerTool mServicesTool;
    ReceivesInvitationAdapter adapter;
    SwipeRecyclerView listView;
    public static final String ADD_URL="apiInvitationController.do?doAdd";
    public static final String GET_DATA_URL="apiInvitationController.do?getList";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<List<ReceivesInvitationEntity>>(){}.getType();
                MessageHandlerTool.MessageInfo msgInfo = messageHandlerTool.handler(msg,ReceivesInvitationAct.this,adapter,type);
                setMessageInfo(msgInfo);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_receives_invitation);
        initView();
        initTool();
        initListener();
        listLoad(mHandler);
    }

    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.a_receives_invitation_title_bar);
        listView=(SwipeRecyclerView)findViewById(R.id.a_receives_invitation_lv);
//        Drawable top_edit=getResources().getDrawable(R.mipmap.top_edit);
//        top_edit.setBounds(0, 0, top_edit.getMinimumWidth(), top_edit.getMinimumHeight());
//        titleBar.setRightDrawable(top_edit,null,null,null);


    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
        adapter=new ReceivesInvitationAdapter(this);
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
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                position=position-1;
//                ReceivesInvitationEntity invitationEntity= adapter.getItem(position);
//                Intent intent= new Intent(ReceivesInvitationAct.this,ReceivesInvitationDetailAct.class);
//                intent.putExtra("invitationId",invitationEntity.getId());
//                intent.putExtra("inviterId",invitationEntity.getInvitationlistEntityList().get(0).getId());
//                startActivity(intent);
//            }
//        });


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
        params.put("phone", FtpApplication.getInstance().getUser().getUserphone());
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
