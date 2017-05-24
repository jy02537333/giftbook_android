package com.zxw.giftbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.zxw.giftbook.Activity.login.LoginAct;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.LoginUserInfoHandlerTool;

import java.util.HashMap;
import java.util.Map;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ServicesTool;
import pri.zxw.library.tool.SharedpreferencesTool;

public class Welcome extends MyBaseActivity {
	private AlphaAnimation start_anima;
	ImageView img;
	private ServicesTool mServicesTool;
	private LoginUserInfoHandlerTool loginUserInfoHandlerTool;
	private final int GET_ALL_CATEGORY_CODE = 9835;
	public static final int GET_DEF_CATEGORY_CODE = 9836;
	public static final int USER_VALIDATE_CODE = 7345;

	View view;
	Handler mHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case USER_VALIDATE_CODE:
					loginUserInfoHandlerTool
					.loginedHandler(msg,FtpApplication.user.getLoginpassword());
					if(FtpApplication.user.isLogin(Welcome.this)){
						startActivity(new Intent(Welcome.this,MainAct.class));
					}
					break;
			    default:
						loginUserInfoHandlerTool.messageHandler(msg);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialogTool.getInstance(Welcome.this).dismissDialog();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.a_welcome, null);
		setContentView(view);
		mServicesTool = new AppServerTool(NetworkConfig.api_url,this, mHandler);
		loginUserInfoHandlerTool=new LoginUserInfoHandlerTool(
				Welcome.this, mServicesTool);
		initView();
		initData();
	}

	private void initData() {
		loginUserInfoHandlerTool.loginVerifity(USER_VALIDATE_CODE,mHandler,this,mServicesTool);
		start_anima = new AlphaAnimation(0.3f, 1.0f);
		start_anima.setDuration(2000);
		view.startAnimation(start_anima);
		start_anima.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				redirectTo();
			}
		});
		Log.i("getDeviceInfo",getDeviceInfo(this));
	}
	/**
	 * 首次启动
	 */  
	private boolean firstStartApp()
	{
		
		Map<String, String> map=SharedpreferencesTool.getDataFromPreferences
				(Welcome.this, SharedpreferencesTool.APP_FIRST_START, 
						SharedpreferencesTool.APP_FIRST_START);
		if(!map.containsKey(SharedpreferencesTool.APP_FIRST_START)||
				map.get(SharedpreferencesTool.APP_FIRST_START).equals(""))
		{
			HashMap<String, String> valMap=new HashMap<String, String>();
			valMap.put(SharedpreferencesTool.APP_FIRST_START, "1");
			SharedpreferencesTool.addDataToPreferences(Welcome.this, SharedpreferencesTool.APP_FIRST_START
					, valMap);
			startActivity(new Intent(Welcome.this,FirstStartAct.class));
			finish();
			return true;
		}
		return false;
	}

	private void initView() {
		img=(ImageView)findViewById(R.id.a_welcome_img);
		ImgLoadMipmapTool.load(R.mipmap.default_bg,img);
	}
	/**转到首页*/
	private void redirectTo() {
		if(FtpApplication.user==null||!FtpApplication.user.isLogin(this))
		{
			startActivity(new Intent(getApplicationContext(),
					LoginAct.class));
		}else {
			startActivity(new Intent(getApplicationContext(),
					MainAct.class));
		}
		finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}
	public static String getDeviceInfo(Context context) {
	    try{
	      org.json.JSONObject json = new org.json.JSONObject();
	      android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
	          .getSystemService(Context.TELEPHONY_SERVICE);

	      String device_id = tm.getDeviceId();

	      android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

	      String mac = wifi.getConnectionInfo().getMacAddress();
	      json.put("mac", mac);

	      if( TextUtils.isEmpty(device_id) ){
	        device_id = mac;
	      }

	      if( TextUtils.isEmpty(device_id) ){
	        device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
	      }

	      json.put("device_id", device_id);

	      return json.toString();
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	  return null;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}
	@Override
	protected void onDestroy() {
		start_anima=null;
		mServicesTool=null;
		loginUserInfoHandlerTool=null;
		mHandler=null;
		img.setImageDrawable(null);
		super.onDestroy();
		System.gc();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	public void onBackPressed() {
		
	}
}
