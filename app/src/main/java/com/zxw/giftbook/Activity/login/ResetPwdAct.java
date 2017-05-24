package com.zxw.giftbook.Activity.login;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import pri.zxw.library.entity.User;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
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
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;

/**
 * 重置密码
 * @author 张相伟
 *
 */
public class ResetPwdAct extends MyBaseActivity {
	private TextView canelBtn;
	private EditText pwd1Edit;
	private EditText pwd2Edit;
	private Button nextBtn;
	private TitleBar titleTv;
	private String mobileStr;
	private AppServerTool mServicesTool;
	private final static int REGISTER_CODE = 5341;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				if (msg.arg1 == 1) {
					@SuppressWarnings("unchecked")
					Map<String, String> map = (Map<String, String>) msg.obj;
					Gson gson = new Gson();
					Type type = new TypeToken<User>() {
					}.getType();
					String status = map.get(JsonParse.STATUS);
					if (msg.arg1 == 1) {
						if (status.equals("0")) {
							User user = gson.fromJson(map.get(JsonParse.CONTEXT), type);
							if (user != null) {
								ToastShowTool.myToastShort(ResetPwdAct.this, "重置密码成功!");
								setResult(1);
								finish();
							}
						}
					} 
					
				} else 
					ToastShowTool.myToastShort(ResetPwdAct.this, "重置密码发生异常！");
				ProgressDialogTool.getInstance(ResetPwdAct.this).dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialogTool.getInstance(ResetPwdAct.this).dismissDialog();
			}
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_reset_pwd);
		mobileStr = getIntent().getExtras().getString(FindPwdAct.RESET_PWD_KEY);
		if (mobileStr == null || mobileStr.trim().length() == 0) {
			ToastShowTool.myToastShort(ResetPwdAct.this, "您的账号异常！");
			finish();
			return;
		}
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
		pwd1Edit = (EditText) findViewById(R.id.a_reset_pwd1_edit);
		pwd2Edit = (EditText) findViewById(R.id.a_reset_pwd2_edit);
		nextBtn = (Button) findViewById(R.id.a_reset_btn);
		titleTv = (TitleBar) findViewById(R.id.lay_title_bar);
		titleTv.setTitle("密码重置");
	}

	private void initListener() {
		canelBtn.setOnClickListener(new MOnClickListener());
		nextBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			
//				else if (!pwdOld.getText().toString().equals(user.getPassword())) {
//					ToastShowTool.myToastShort(UpdatePwdAct.this, "您输入的密码和原始密码不一样！");
//					return;
//				}
				 if (pwd1Edit.getText().toString().trim().length() == 0) {
					ToastShowTool.myToastShort(ResetPwdAct.this, "请输入新密码！");
					return;
				} else if (!pwd2Edit.getText().toString()
						.equals(pwd1Edit.getText().toString())) {
					ToastShowTool
							.myToastShort(ResetPwdAct.this, "两次输入的密码不一致！");
					return;
				}
				ProgressDialogTool.getInstance(ResetPwdAct.this).showDialog(
						"提交中...");
				Map<String, String> params = new HashMap<String, String>();
				params.put("mobile", mobileStr);
				params.put("password",pwd1Edit.getText().toString());
				params.put("confirm", pwd2Edit.getText().toString());


				mServicesTool.doPostAndalysisData("/UserInfo/FindPassword", params,REGISTER_CODE, 
						"EditPassword");
			}
		});
	}

	private class MOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			ResetPwdAct.this.finish();
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
