package com.zxw.giftbook.Activity.login;

import java.lang.reflect.Type;
import java.util.Map;

import pri.zxw.library.entity.User;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.SharedpreferencesTool;
import pri.zxw.library.tool.ToastShowTool;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.view.TitleBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;

public class RegisterPwdAct extends MyBaseActivity {
	private EditText pwd1Edit;
	private EditText pwd2Edit;
	private Button  nextBtn;
	private TitleBar titleTv;
	private String accountString;
	private String verificationStr;
	private AppServerTool mServicesTool;
	private final static int REGISTER_CODE=5341;
	Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			try {
				User user = null;
				if(msg.arg1==1)
				{
				Map<String, String> map=(Map<String, String>)msg.obj;
				Gson gson = new Gson();
				Type type = new TypeToken<User>() {
				}.getType();
				 user = gson.fromJson(map.get(JsonParse.CONTEXT), type);
				if (user != null) {
						SharedpreferencesTool.addDataToPreferences
								(RegisterPwdAct.this,SharedpreferencesTool.USER_CONFIG_FILE,
										SharedpreferencesTool.USER_CONFIG_FILE_ACCOUNT, user.getLoginpassword());
						SharedpreferencesTool.addDataToPreferences
						(RegisterPwdAct.this,SharedpreferencesTool.USER_CONFIG_FILE,
								SharedpreferencesTool.USER_CONFIG_FILE_PASSWORD,user.getUserphone());
						FtpApplication.getInstance().setUser(user);
						 setResult(1);  
				         finish();  
				   }
				}
				else if(user == null)
					ToastShowTool.myToastShort
					   (RegisterPwdAct.this, "注册发生异常！");
				ProgressDialogTool.getInstance(RegisterPwdAct.this).dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialogTool.getInstance(RegisterPwdAct.this).dismissDialog();
			}
			
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(layout.act_com_register_pwd);
		accountString=getIntent().getStringExtra(RegisterAct.REG_ACCOUNT_KEY);
		verificationStr=getIntent().getStringExtra(RegisterAct.REG_VERIFICATION_CODO_KEY);
		if(accountString==null||accountString.trim().length()==0)
		{
			ToastShowTool.myToastShort(RegisterPwdAct.this, 
					"输入的手机号异常！");
			finish();
			return;
		}
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
		pwd1Edit=(EditText)findViewById(R.id.a_register_pwd1_edit);
		pwd2Edit=(EditText)findViewById(R.id.a_register_pwd2_edit);
		 nextBtn=(Button)findViewById(R.id.a_register_btn);
		 titleTv=(TitleBar) findViewById(R.id.lay_title_bar);
		 titleTv.setTitle("设置密码");
	}
	private void initListener()
	{
		titleTv.setLeftClickListener(new TitleOnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	  nextBtn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if(pwd1Edit.getText().toString().trim().length()==0)
			{ToastShowTool.myToastShort(RegisterPwdAct.this, "请输入密码！");
			return ;
			}
			else if(pwd1Edit.getText().toString().trim().length()<5)
			{ToastShowTool.myToastShort(RegisterPwdAct.this, "密码位数不能小于6！");
			return ;
			}
			else if(!pwd2Edit.getText().toString().equals(
				pwd1Edit.getText().toString())  )
			{
			ToastShowTool.myToastShort(
					RegisterPwdAct.this, "两次输入的密码不一致！");
			return ;
			}
//			Intent intent=new Intent(RegisterPwdAct.this,OptimizeUserAct.class);
//			intent.putExtra(RegisterAct.REG_ACCOUNT_KEY, accountString);
//			intent.putExtra(RegisterAct.REG_VERIFICATION_CODO_KEY,verificationStr);
//			intent.putExtra(RegisterAct.REG_PWD_KEY,pwd1Edit.getText().toString().trim());
//			startActivityForResult(intent, RegisterAct.REGISTER_CODE);
			
//			ProgressDialogTool.getInstance(RegisterPwdAct.this).showDialog("注册中...");
//			RequestParams parsms=new RequestParams();
//			parsms.add("mobile", accountString);
//			parsms.add("password", pwd1Edit.getText().toString());
//		 mServicesTool.doPostAndalysisData(
//				 "/UserInfo/Regedit",parsms,REGISTER_CODE,true);
		}
	});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
		if (requestCode == RegisterAct.REGISTER_CODE && resultCode == 1) {
			setResult(1);
			finish();
		}
		
	}
	private class MOnClickListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			RegisterPwdAct.this.finish();
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
