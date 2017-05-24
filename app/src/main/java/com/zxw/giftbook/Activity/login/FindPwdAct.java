package com.zxw.giftbook.Activity.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ToastShowTool;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.view.TitleBar;


/**
 * 注册
 * 
 * @Author RA
 * @Blog http://blog.csdn.net/vipzjyno1
 */
public class FindPwdAct extends MyBaseActivity {
	private TitleBar  titleTv;
	private EditText accountEdit;
	private EditText codeEdit;
	private Button nextBtn, getCodeBtn;
	private AppServerTool mServicesTool;
	private boolean isVerifaction=false;
	private boolean isClickVerifaction=false;
	private final static int VERIFICATION_CODE = 5341;
	private final static int FIND_PWD=6258;
	private final static int TIMER_CODE = 2623;
	public static final String RESET_PWD_KEY = "RESET_PWD_KEY";
	public static final String REG_VERIFICATION_CODO_KEY = "REG_VERIFICATION_CODO_KEY";
	private Timer timer;
	private int timerInt = 30;
	private String verificCode="";
	@SuppressWarnings("rawtypes")
	private MessageHandlerTool handlerTool;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				if (msg.what == VERIFICATION_CODE) { 
					nextBtn.setEnabled(true);
//					nextBtn.setBackgroundColor(Color.rgb(207, 73, 72));
					nextBtn.setBackgroundColor(getResources().getColor(R.color.com_color1));
					isVerifaction=true;
					if (msg.arg1 == 1) {
//{ when=-39s711ms what=5341 arg1=1 obj={data=0714, msg=成功, code=0} target=com.dxcm.news.RegisterAct$1 }
						@SuppressWarnings("unchecked")
						Map<String, String> map=(Map<String, String>)msg.obj;
						verificCode=map.get(JsonParse.CONTEXT);
					}else {
						handlerTool.requestResultPrompt(msg, FindPwdAct.this,null);
						handlerTool.getIsNetworkError();
							timerCanel();
					}
				} else if (msg.what == TIMER_CODE) {
					String getCodeStr = null;
					if (timerInt <= 1) {   
						timerCanel();
					} else {
						getCodeStr = timerInt + "";
						getCodeBtn.setText(getCodeStr);
					}
				
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};
	private void timerCanel()
	{
		setGetCodeBtnEnabled(true);
		timer.cancel();
		getCodeBtn.setText("获取");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_find_pwd);
		initView();
		initTool();
		initListener();
	}

	/** 初始化数据 */
	@SuppressWarnings("rawtypes")
	private void initTool() {
		mServicesTool = new AppServerTool(NetworkConfig.api_url,this, mHandler);
		handlerTool=new MessageHandlerTool(false);
	}

	/** 初始化布局 */
	private void initView() {
		accountEdit = (EditText) findViewById(R.id.a_find_pwd_username_edit);
		codeEdit = (EditText) findViewById(R.id.a_find_pwd_barcode_edit);
		nextBtn = (Button) findViewById(R.id.a_find_pwd_btn);
		getCodeBtn = (Button) findViewById(R.id.a_find_pwd_get_code);
		titleTv = (TitleBar) findViewById(R.id.lay_title_bar);
		titleTv.setTitle("找回密码");
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
				String account = getAccount();
				if (account == null) {
					return;
				} else if (codeEdit.getText().toString().trim().length() == 0) {
					ToastShowTool.myToastShort(FindPwdAct.this, "请输入验证码！");
					return;
				} 
				if(!isClickVerifaction)
				{  
					runGetVerifaction();
					return ;
				}
				 else if(!isVerifaction||
						 !verificCode.equals(codeEdit.getText().toString().trim()))
				{
					ToastShowTool.myToastShort(FindPwdAct.this, "验证码不正确！");
					return;
				}
				Intent intent = new Intent(FindPwdAct.this,
						ResetPwdAct.class);
				intent.putExtra(RESET_PWD_KEY, account);
				intent.putExtra(REG_VERIFICATION_CODO_KEY, codeEdit.getText()
						.toString().trim());
				startActivityForResult(intent, FIND_PWD);
			}
		});
		getCodeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				runGetVerifaction();
			}
		});
	}
	/**
	 * 开始获取验证码
	 */
	private void runGetVerifaction()   
	{
		isClickVerifaction=true;
		String account = getAccount();
		if (account != null) {
			HashMap<String, String> params=new HashMap<String, String>();
			params.put("mobile", account);
			mServicesTool.doPostAndalysisData(
					"/UserInfo/FindPasswordCode",params, VERIFICATION_CODE,"getcode");
			setGetCodeBtnEnabled(false);
		}
	}

	private void setGetCodeBtnEnabled(Boolean isEnable) {   
		if (!isEnable) {
			getCodeBtn.setText("30");
			getCodeBtn.setEnabled(false);
			getCodeBtn.setBackgroundColor(Color.GRAY);
			nextBtn.setBackgroundColor(Color.GRAY);
			nextBtn.setEnabled(false);
			TimerTask task = new TimerTask() {
				public void run() {
					Message message = new Message();
					message.what = TIMER_CODE;
					timerInt--;
					mHandler.sendMessage(message);
				}
			};
			timer = new Timer(true);
			timerInt = 30;
			timer.schedule(task, 1000, 1000);
		} else {
			nextBtn.setEnabled(true);
			nextBtn.setBackgroundColor(getResources().getColor(R.color.com_color1));
			getCodeBtn.setEnabled(true);
			getCodeBtn.setBackgroundColor(getResources().getColor(R.color.com_color1));
		}
	}

	private String getAccount() {
		String account = accountEdit.getText().toString().trim();
		if (account.length() == 0) {
			ToastShowTool.myToastShort(FindPwdAct.this, "请输入手机号！");
			return null;
		} else if (!Pattern.matches("1[2-9]\\d{9}", account)) {
			ToastShowTool.myToastShort(FindPwdAct.this, "请输入正确手机号！");
			return null;
		}
		return account;
	}


	private class MOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			FindPwdAct.this.finish();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FIND_PWD && resultCode == 1) {
			setResult(1);
			finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
@Override
protected void onDestroy() {
	super.onDestroy();
}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
