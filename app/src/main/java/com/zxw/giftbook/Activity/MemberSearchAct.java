package com.zxw.giftbook.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.entitiy.GifttypeEntity;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.Activity.entitiy.VGroupAndMemberEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.GroupMemberSelectAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.myinterface.IDataMapUtilCallback;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.DataMapUtil;
import com.zxw.giftbook.view.SearchEditText;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.base.MyPullToRefreshBaseFragment;
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

public class MemberSearchAct extends MyBaseActivity {
    TitleBar titleBar;
    ListView listView;
    SearchEditText nameEdit;
    TextView nameTv;
    String giftMoneyUserId;
    List<VGroupAndMemberEntity> groupMemberList=new ArrayList<>();
    GroupMemberSelectAdapter groupMemberSelectAdapter;
    AppServerTool mServicesTool;
    public static final String GM_URL="apiGroupmemberCtrl.do?getFullMember";
    public static final int GM_CODE=9715;
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GM_CODE) {//获取数据用户亲友信息
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                String data = messageHandlerTool.handlerData(msg, MemberSearchAct.this);
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
            ProgressDialogTool.getInstance(MemberSearchAct.this).dismissDialog();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_member_srearch);
                initView();
        initTool();
        initListener();
        findFullMember();
    }

    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.act_search_member_title);
        listView=(ListView)findViewById(R.id.act_search_member_lv);
        nameEdit=(SearchEditText)findViewById(R.id.act_search_member_set);
        nameTv=(TextView) findViewById(R.id.act_search_member_tv);
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
        listView.setAdapter(groupMemberSelectAdapter);
        listView.setTextFilterEnabled(true);
    }

    /**
     * 查询好友信息
     */
    public void findFullMember()
    {
        Map<String,String > params= ComParamsAddTool.getParam();
        params.put("userid", FtpApplication.user.getId());
        mServicesTool.doPostAndalysisData(GM_URL,params,GM_CODE);
    }

    public void initListener()
    {
        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("giftMoneyUserId","");
                intent.putExtra("giftMoneyUserName",nameEdit.getText().toString());
                setResult(1,intent);
                finish();
            }
        });
        // 设置搜索文本监听
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                groupMemberSelectAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                giftMoneyUserId=groupMemberSelectAdapter.getItem(position).getId();
                Intent intent=new Intent();
                intent.putExtra("giftMoneyUserId",giftMoneyUserId);
                intent.putExtra("giftMoneyUserName",groupMemberSelectAdapter.getItem(position).getGroupmember());
                setResult(1,intent);
                finish();
            }
        });
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
    }
}
