package com.zxw.giftbook.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zxw.giftbook.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import pri.zxw.library.base.MyPullToRefreshBaseActivity;
import pri.zxw.library.tool.WebGetDataTool;

/**
 * Created by sun on 2016/7/12.
 */
public class trackRecordActivity extends MyPullToRefreshBaseActivity {
    public static final int get_data_code=100;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){

            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.f_sidekicker_group);

    }
    void initView(){

    }
    void initTool(){

    }
    void initListner(){

    }

    @Override
    public void getWebData() {

    }


}
