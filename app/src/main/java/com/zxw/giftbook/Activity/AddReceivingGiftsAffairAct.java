package com.zxw.giftbook.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;

import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.entitiy.ReceivesInvitationTitleEntity;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.SidekickerGroupAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.MenuSettingViewInit;

import java.lang.reflect.Type;
import java.util.Map;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.view.TitleBar;

/**
 * 功能 添加收礼事件
 * Createdy 张相伟
 * 2017/6/3.
 */

public class AddReceivingGiftsAffairAct extends MyBaseActivity {

    TitleBar titleBar;
    EditText titleEdit;
    Button subBtn;
    RadioGroup radioGroup;
    AppServerTool mServicesTool;
    MessageHandlerTool messageHandlerTool;
    int type=1;
    boolean isSub=false;
    public static final String ADD_URL="apiReceivesInvitationController.do?datagrid";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_ADD_CODE)
            {
                Type type=new TypeToken<ReceivesInvitationTitleEntity>(){}.getType();
                ReceivesInvitationTitleEntity entity=
                        (ReceivesInvitationTitleEntity)messageHandlerTool.handlerObject(msg,type,AddReceivingGiftsAffairAct.this);
                if(entity!=null)
                {
                    ToastShowTool.myToastShort(AddReceivingGiftsAffairAct.this,"添加成功！");
                    Intent intent=new Intent();
                    intent.putExtra("entity",entity);
                    setResult(1,intent);
                    finish();
                }
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.a_add_receiving_gifts_affair);
//        initView();
//        initTool();
//        initListener();
    }
    private void  initView()
    {
        titleBar=(TitleBar) findViewById(R.id.a_add_receiving_gifts_affair_title_bar);
        titleEdit=(EditText) findViewById(R.id.a_add_receiving_gifts_affair_title_edit);
        radioGroup=(RadioGroup) findViewById(R.id.a_add_receiving_gifts_affair_rbog);
        subBtn=(Button) findViewById(R.id.a_add_receiving_gifts_affair_btn);
    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
         messageHandlerTool=new MessageHandlerTool();
    }
    private void  initListener(){
        titleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.a_add_receiving_gifts_affair_rbo1)
                {
                    type=1;
                }else if(checkedId==R.id.a_add_receiving_gifts_affair_rbo2){
                    type=2;
                }else if(checkedId==R.id.a_add_receiving_gifts_affair_rbo3){
                    type=3;
                }else if(checkedId==R.id.a_add_receiving_gifts_affair_rbo4){
                    type=4;
                }
            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSub)
                    return ;
                isSub=true;
                Map<String ,String> params= ComParamsAddTool.getParam();
                String titleStr=titleEdit.getText().toString();
                if(ToastShowTool.emptyToast(AddReceivingGiftsAffairAct.this,"请输入名称！",titleEdit))
                   return;
                params.put("title",titleStr);
                params.put("receivestype",type+"");
                mServicesTool.doPostAndalysisDataCall(ADD_URL, params, GET_ADD_CODE, new IServicesCallback() {
                    @Override
                    public void onStart() {
                        ProgressDialogTool.getInstance(AddReceivingGiftsAffairAct.this).showDialog("提交中....");
                    }
                    @Override
                    public void onEnd() {
                        isSub=false;
                        ProgressDialogTool.getInstance(AddReceivingGiftsAffairAct.this).dismissDialog();
                    }
                });
            }
        });
    }

}
