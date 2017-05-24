package com.zxw.giftbook.Activity.login;

import pri.zxw.library.listener.TitleOnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.view.TitleBar;

import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;

/**
 * 用户注册协议
 * @Author RA
 * @Blog http://blog.csdn.net/vipzjyno1
 */
public class UserRegisterAgreementAct extends MyBaseActivity  {
	private TitleBar titleTv;
	private boolean isCheck=false;
	private WebView wv;
	private AppServerTool mServicesTool;

	Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			try {
				if(msg.arg1==1)
				{

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_about);
		initView();
		initTool();
		initListener();
	}
	
	/** 初始化数据*/
	private void initTool() {
		mServicesTool=new AppServerTool(NetworkConfig.api_url,this,  mHandler);
	}
	
	/** 初始化布局*/
	private void initView() {
		titleTv=(TitleBar) findViewById(R.id.a_about_title_bar);
		 titleTv.setTitle("用户注册协议");
		 wv=(WebView)findViewById(R.id.a_about_wv);
		 wv.loadUrl(NetworkConfig.api_url+"Version/protocol");
	}
	private void initListener()
	{
		titleTv.setLeftClickListener(new TitleOnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	 
	}
	private class MOnClickListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			UserRegisterAgreementAct.this.finish();
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
