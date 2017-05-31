package com.zxw.giftbook.Activity.login;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.view.TitleBar;

/**
 * Created by Administrator on 2017/5/30.
 */

public class AboutAct extends MyBaseActivity {
    TitleBar titleTv;
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_about);
        initView();
        initTool();
        initListener();
    }

    /** 初始化数据 */
    private void initTool() {
    }

    /** 初始化布局 */
    private void initView() {
        titleTv = (TitleBar) findViewById(R.id.a_about_title_bar);
        wv=(WebView) findViewById(R.id.a_about_wv);
        wv.loadUrl(NetworkConfig.api_url+"apiFeedbackCtrl.do?about");
    }

    private void initListener() {
        titleTv.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
