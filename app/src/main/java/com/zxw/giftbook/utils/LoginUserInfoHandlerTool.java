package com.zxw.giftbook.utils;

import android.app.Activity;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.FtpApplication;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import pri.zxw.library.db.JsonStrHistoryDao;
import pri.zxw.library.entity.User;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.ServicesTool;
import pri.zxw.library.tool.SharedpreferencesTool;

/**
 * 用户登录相关信息处理
 * 
 * @author 张相伟
 * 
 */
public class LoginUserInfoHandlerTool {
	private Activity mActivity;
	private ServicesTool mServicesTool;
	/**
	 * 是否在登陆中
	 */
	public static boolean isLoging=false;
	/**
	 * 用户的相关数据，是否分开获取
	 */
	private boolean mIsDistribution;
	private User user;
	JsonStrHistoryDao dao;
	public LoginUserInfoHandlerTool(Activity activity, ServicesTool servicesTool) {
		mActivity = activity;
		mServicesTool = servicesTool;
		dao=new JsonStrHistoryDao();
	}
	public LoginUserInfoHandlerTool(Activity activity, 
			ServicesTool servicesTool,boolean isDistribution) {
		mActivity = activity;
		mServicesTool = servicesTool;
		mIsDistribution=isDistribution;
		 dao=new JsonStrHistoryDao();
	}

	/**
	 * 用户登录后反馈一些用户信息
	 * 
	 * @param msg
	 */
	public User loginedHandler(Message msg,String pwd) {
		Gson gson=new Gson();
		try {
			Map<String, String> map = (Map<String, String>) msg.obj;
			if(map.get(JsonParse.STATUS).equals("1")) {
				JSONObject jsonArray = new JSONObject(map.get(JsonParse.CONTEXT));
				//JSONObject tokenJson=(JSONObject) jsonArray.get("data");
				String token = jsonArray.getString("token");
				String userJson = jsonArray.getString("obj");
				Type type = new TypeToken<User>() {
				}.getType();
				user = gson.fromJson(userJson, type);
				user.setLoginpassword(pwd);
				user.setToken(token);
				user.saveUser();
				FtpApplication.user = user;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return user;
	}



	/**
	 * 用户相关信息接收后，相关处理
	 * 
	 * @param msg
	 */
	public boolean messageHandler(Message msg) {
		boolean isSuccess = false;
		Map<String, String> map = null;
		switch (msg.what) {
		default:
			break;
		}
		return isSuccess;
	}
	/**
	 * 用户信息验证
	 */
	public void loginVerifity(int requestCode, android.os.Handler handler, Activity act, ServicesTool servicesTool){
		Map<String,String> params=new HashMap<>();
		if(FtpApplication.user!=null&&FtpApplication.user.isExists()&&!FtpApplication.user.isLogin(act)) {
			User user = FtpApplication.user.getUser();
			params.put("info", user.toSignString(act));
			params.put("loginname", user.getLoginname());
			params.put("login","");
			servicesTool.doPostAndalysisData("apiSysUserCtrl.do", params, requestCode);
		}
	}


}
