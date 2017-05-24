package com.zxw.giftbook.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxw.giftbook.utils.AppServerTool;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.base.MyPullToRefreshBaseActivity;
import pri.zxw.library.entity.ComInfo;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.WebGetDataTool;

/**
 * Created by sun on 2016/7/12.
 */
public class poortwoActivity extends MyPullToRefreshBaseActivity
{
    AppServerTool mServicesTool;
    MessageHandlerTool mMessageHandlerTool;
    MyBaseAdapter mAdapter;
    PullToRefreshListView lv;
    private static final int get_data_code=5435;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==get_data_code)
            {
                Type type=new TypeToken<List<ComInfo>>(){}.getType();
                mMessageHandlerTool.handler(msg,poortwoActivity.this,mAdapter,lv,type);
            }

        }
    };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.act_working_condition);

    }
    void initView(){
        findViewById(R.id.add);
    }
    void initTool()
    {
        mMessageHandlerTool=new MessageHandlerTool();
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
    }
    void initListenere()
    {
        this.initListener(lv,mAdapter);
    }
    protected   void initTool(PullToRefreshBase pullToRefreshBase,
                                      WebGetDataTool wgdTool)
    {

    }
    /**
     * 获取数据的操作
     */
    public  void getWebData(){
        Map<String ,String > map=null;
         mServicesTool.doPostAndalysisData("api.jsp",map,get_data_code,2000);
    }

}
