package com.zxw.giftbook.Activity.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import pri.zxw.library.entity.User;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxw.giftbook.MainAct;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.LoginUserInfoHandlerTool;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.view.TitleBar;


/**
 * 注册
 *
 * @Author RA
 * @Blog http://blog.csdn.net/vipzjyno1
 */
public class RegisterAct extends MyBaseActivity {
	private TitleBar titleBar;
	private TextView
//			canelBtn,
			registerTv;
	private EditText accountEdit,pwdEdit;
	private EditText codeEdit;
	private Button subBtn, getCodeBtn;
	private ImageView registerCheck;
	private boolean isCheck = false;
	private LinearLayout registerLay;
	private AppServerTool mServicesTool;
	@SuppressWarnings("rawtypes")
	private MessageHandlerTool handlerTool;
	private boolean isVerifaction=false;
	private boolean isClickVerifaction=false;
	private final static int VERIFICATION_CODE = 5341;
	public final static int REGISTER_CODE = 5734;
	private final static int TIMER_CODE = 2623;
	public static final String REG_ACCOUNT_KEY = "REG_ACCOUNT_KEY";
	public static final String REG_VERIFICATION_CODO_KEY = "REG_VERIFICATION_CODO_KEY";
	private final String SUB_URL="apiSysUserCtrl.do?doAdd";
	private Timer timer;
	private int timerInt = 30;
	private String verificCode="";
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				if (msg.what == VERIFICATION_CODE) {
					subBtn.setEnabled(true);
//					subBtn.setBackgroundColor(Color.rgb(207, 73, 72));
					subBtn.setBackgroundColor(getResources().getColor(R.color.com_color1));
					isVerifaction=true;
					if (msg.arg1 == 1) {
//{ when=-39s711ms what=5341 arg1=1 obj={data=0714, msg=成功, code=0} target=com. dxcm.news.RegisterAct$1 }

						@SuppressWarnings("unchecked")
						Map<String, String> map=(Map<String, String>)msg.obj;
						verificCode=map.get(JsonParse.CONTEXT);
					}else {
						handlerTool.requestResultPrompt(msg, RegisterAct.this,null);
						handlerTool.getIsNetworkError();
						timerCanel();
					}
				} else if (msg.what == TIMER_CODE) {
					String getCodeStr = null;
					if (timerInt <= 0) {
						timerCanel();
					} else {
						getCodeStr = timerInt + "";
						getCodeBtn.setText(getCodeStr);
					}

				}
				else if (msg.what == GET_ADD_CODE) {
					LoginUserInfoHandlerTool loginUserInfoHandlerTool=new LoginUserInfoHandlerTool(RegisterAct.this,mServicesTool);
					User user= loginUserInfoHandlerTool.loginedHandler(msg,pwdEdit.getText().toString());
					if(user!=null) {
						ToastShowTool.myToastShort(RegisterAct.this, "注册成功！");
						Intent intent = new Intent(RegisterAct.this, MainAct.class);
						startActivity(intent);
						finish();
					}else
						ToastShowTool.myToastShort(RegisterAct.this, loginUserInfoHandlerTool.getMsg());
				}
				ProgressDialogTool.getInstance(RegisterAct.this).dismissDialog();
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialogTool.getInstance(RegisterAct.this).dismissDialog();
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_register);
		initView();
		initTool();
		initListener();
	}

	/** 初始化数据 */
	@SuppressWarnings("rawtypes")
	private void initTool() {
		handlerTool=new MessageHandlerTool(false);
		mServicesTool = new AppServerTool(NetworkConfig.api_url,this, mHandler);
	}

	/** 初始化布局 */
	private void initView() {
		titleBar = (TitleBar) findViewById(R.id.a_register_title_bar);
		accountEdit = (EditText) findViewById(R.id.a_register_username_edit);
		pwdEdit = (EditText) findViewById(R.id.a_register_pwd_edit);
		codeEdit = (EditText) findViewById(R.id.a_register_barcode_edit);
		subBtn = (Button) findViewById(R.id.a_register_btn);
		registerTv = (TextView) findViewById(R.id.a_register_register);
		registerCheck = (ImageView) findViewById(R.id.a_register_check);
		registerLay = (LinearLayout) findViewById(R.id.a_register_layout1);
		getCodeBtn = (Button) findViewById(R.id.a_register_get_code);
		titleBar.setTitle("注册");

		SpannableString ss = new SpannableString("我已阅读并同意《新用户注册协议》");
		int buleColor=Color.rgb(112, 144, 186);
		int grayColor=Color.rgb(128, 128, 128);
		int grayLength="我已阅读并同意《".length();
		int buleLength="新用户注册协议".length();

//		ss.setSpan(new ForegroundColorSpan(grayColor),14
//				,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


		ss.setSpan(new chekcClick(),0,grayLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(   new UserRegisterClick(),grayLength,grayLength+buleLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(grayColor),0,
				grayLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(buleColor),grayLength,
				grayLength+buleLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		registerTv.setText(ss);
		registerTv.setMovementMethod(LinkMovementMethod.getInstance());

	}
	class UserRegisterClick  extends ClickableSpan implements OnClickListener
	{
		@Override
		public void onClick(View widget) {
			startActivity(new Intent(RegisterAct.this,UserRegisterAgreementAct.class));
		}
	}
	class chekcClick  extends ClickableSpan implements OnClickListener
	{
		@Override
		public void onClick(View widget) {
			setChecked();
		}
		@Override
		public void updateDrawState(TextPaint ds) {
			super.updateDrawState(ds);
			ds.setUnderlineText(false);//取消下划线
		}
	}

	private void timerCanel()
	{
		setGetCodeBtnEnabled(true);
		timer.cancel();
		getCodeBtn.setText("获取");
	}

	private void initListener() {
//		registerLay.setOnClickListener(new MonReadListener());
		registerCheck.setOnClickListener(new MonReadListener());
		subBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String account = getAccount();
				if (account == null) {
					return;
				} else if (codeEdit.getText().toString().trim().length() == 0) {
					ToastShowTool.myToastShort(RegisterAct.this, "请输入验证码！");
					return;
				}
				else if (pwdEdit.getText().toString().trim().length() == 0) {
					ToastShowTool.myToastShort(RegisterAct.this, "请输入密码！");
					return;
				}
				else if (pwdEdit.getText().toString().trim().length() <6) {
					ToastShowTool.myToastShort(RegisterAct.this, "密码不能少于6位！");
					return;
				}
//				if(!isClickVerifaction)
//				{
//					runGetVerifaction();
//					return ;
//				}
//				else if (!isCheck) {
//					ToastShowTool.myToastShort(RegisterAct.this, "请阅读用户手册！");
//					return;
//				}
//				else if(!isVerifaction||
//						!verificCode.equals(codeEdit.getText().toString().trim()))
//				{
//					ToastShowTool.myToastShort(RegisterAct.this, "验证码不正确！");
//					return;
//				}
				if(isSub)
					return;
				User userEntity=new User();
				userEntity.setLoginpassword(pwdEdit.getText().toString().trim());
				userEntity.setLoginname(accountEdit.getText().toString().trim());
				Map<String,String > param=new HashMap<String, String>();
				param.put("info",userEntity.toSignString(RegisterAct.this));
				param.put("code",codeEdit.getText().toString().trim());
				mServicesTool.setIsGoLogin(false);
				mServicesTool.doPostAndalysisDataCall(SUB_URL,param , GET_ADD_CODE, new IServicesCallback() {
					@Override
					public void onStart() {
						isSub=true;
						ProgressDialogTool.getInstance(RegisterAct.this).showDialog("注册...");
					}

					@Override
					public void onEnd() {
						ProgressDialogTool.getInstance(RegisterAct.this).dismissDialog();
						isSub=false;
					}
				});
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
			Map<String, String> param = new HashMap<String, String>();
			param.put("phone", account);
			mServicesTool.doPostAndalysisData(
					"apiSmsSendCtrl.do?sendSmsCode", param, VERIFICATION_CODE);
			setGetCodeBtnEnabled(false);
		}
	}

	private void setGetCodeBtnEnabled(Boolean isEnable) {
		if (!isEnable) {
			getCodeBtn.setText("30");
			getCodeBtn.setEnabled(false);
			getCodeBtn.setBackgroundColor(Color.GRAY);
			subBtn.setBackgroundColor(Color.GRAY);
			subBtn.setEnabled(false);
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
			subBtn.setEnabled(true);
			subBtn.setBackgroundColor(getResources().getColor(R.color.com_color1));
			getCodeBtn.setEnabled(true);
			getCodeBtn.setBackgroundColor(getResources().getColor(R.color.com_color1));
		}
	}

	private String getAccount() {
		String account = accountEdit.getText().toString().trim();
		if (account.length() == 0) {
			ToastShowTool.myToastShort(RegisterAct.this, "请输入手机号！");
			return null;
		} else if (!Pattern.matches("1[2-9]\\d{9}", account)) {
			ToastShowTool.myToastShort(RegisterAct.this, "请输入正确手机号！");
			return null;
		}
		return account;
	}

	private class MonReadListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			setChecked();
		}
	}
	private void setChecked()
	{
		if (!isCheck) {
			isCheck = true;
			registerCheck.setImageResource(R.mipmap.agree_3x);
		} else {
			isCheck = false;
			registerCheck.setImageResource(R.drawable.register_checked);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

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
