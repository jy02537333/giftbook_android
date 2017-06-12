package com.zxw.giftbook.Activity.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.entitiy.InvitationlistEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.LoginUserInfoHandlerTool;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.entity.User;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.view.TitleBar;

/**
 * 重置密码
 * @author 张相伟
 *
 */
public class UpdatePwdAct extends MyBaseActivity {
	private EditText pwd1Edit,oldEdit;
	private EditText pwd2Edit;
	private Button nextBtn;
	private TitleBar titleTv;
	private String mobileStr;
	private AppServerTool mServicesTool;
	private final static int UPDATE_CODE = 5341;
	String submit_url="apiSysUserCtrl.do?editPwd";
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				if (msg.what == UPDATE_CODE) {
					@SuppressWarnings("unchecked")
					Map<String, String> map = (Map<String, String>) msg.obj;
					Gson gson = new Gson();
					Type type = new TypeToken<User>() {
					}.getType();
					String status = map.get(JsonParse.STATUS);
					if (msg.arg1 == 1) {
						if (status.equals("1")) {
							LoginUserInfoHandlerTool loginUserInfoHandlerTool=new
									LoginUserInfoHandlerTool(UpdatePwdAct.this,mServicesTool);
							User user = loginUserInfoHandlerTool.loginedHandler(msg,pwd1Edit.getText().toString());
							if (user != null) {
								ToastShowTool.myToastShort(UpdatePwdAct.this, "修改密码成功!");
								setResult(1);
								finish();
							}
						}
					} else
						ToastShowTool.myToastShort(UpdatePwdAct.this,map.get(JsonParse.MSG));
					
				} else 
					ToastShowTool.myToastShort(UpdatePwdAct.this,"网络异常！");
				ProgressDialogTool.getInstance(UpdatePwdAct.this).dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialogTool.getInstance(UpdatePwdAct.this).dismissDialog();
			}
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_update_pwd);
//		mobileStr = getIntent().getExtras().getString(FindPwdAct.RESET_PWD_KEY);
//		if (mobileStr == null || mobileStr.trim().length() == 0) {
//			ToastShowTool.myToastShort(UpdatePwdAct.this, "您的账号异常！");
//			finish();
//			return;
//		}
		initView();
		initTool();
		initListener();
	}

	/** 初始化数据 */
	private void initTool() {
		mServicesTool = new AppServerTool(NetworkConfig.api_url,this, mHandler);
	}

	/** 初始化布局 */
	private void initView() {
		pwd1Edit = (EditText) findViewById(R.id.a_update_pwd_edit);
		pwd2Edit = (EditText) findViewById(R.id.a_update_pwd_affirm_edit);
		oldEdit=(EditText)findViewById(R.id.a_update_pwd_old_edit) ;
		nextBtn = (Button) findViewById(R.id.a_update_pwd_btn);
		titleTv = (TitleBar) findViewById(R.id.lay_title_bar);
	}

	private void initListener() {
		titleTv.setLeftClickListener(new TitleOnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		nextBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			
//				else if (!pwdOld.getText().toString().equals(user.getPassword())) {
//					ToastShowTool.myToastShort(UpdatePwdAct.this, "您输入的密码和原始密码不一样！");
//					return;
//				}
				if (oldEdit.getText().toString().trim().length() == 0) {
					ToastShowTool.myToastShort(UpdatePwdAct.this, "请输入原密码！");
					return;
				} else
				if (pwd1Edit.getText().toString().trim().length() == 0) {
					ToastShowTool.myToastShort(UpdatePwdAct.this, "请输入新密码！");
					return;
				} else if (!pwd2Edit.getText().toString()
						.equals(pwd1Edit.getText().toString())) {
					ToastShowTool
							.myToastShort(UpdatePwdAct.this, "两次输入的密码不一致！");
					return;
				}
				ProgressDialogTool.getInstance(UpdatePwdAct.this).showDialog(
						"提交中...");
				Map<String, String> params = ComParamsAddTool.getParam();
				User user=new User();
				user=FtpApplication.getInstance().getUser().clone();
				user.setOldPwd(oldEdit.getText().toString());
				user.setLoginpassword(pwd1Edit.getText().toString());
//				params.put("mobile", mobileStr);
//				params.put("password", FtpApplication.getInstance().getUser().pwdEncryption(pwd1Edit.getText().toString()));
//				params.put("password", FtpApplication.getInstance().getUser().pwdEncryption(pwd1Edit.getText().toString()));
//				params.put("password",pwd1Edit.getText().toString());
				params.put("info", FtpApplication.getInstance().getUser().toUpdateSignString(UpdatePwdAct.this,user));
				mServicesTool.doPostAndalysisData(submit_url, params,UPDATE_CODE,
						"EditPassword");
			}
		});
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
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
