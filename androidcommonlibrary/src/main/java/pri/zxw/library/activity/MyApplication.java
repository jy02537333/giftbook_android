/**
 * @Title: CateListAdapter.java
 * @Package com.yunzhi.e_commerce.adapter
 * @Description: TODO(用一句话描述该文件做什么)
 * @author A18ccms Lix
 * @date 2015-1-13 下午4:24:23
 * @version V1.0
 */
package pri.zxw.library.activity;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pri.zxw.library.entity.User;
import pri.zxw.library.tool.ClearActivityTool;
import pri.zxw.library.tool.SharedpreferencesTool;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;   
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {
	public static Context mContext = null;
	static MyApplication mApplication = null;
	private ExecutorService es;
	public   static User user;
	/**程序启动时，将app保存起来，如果总的工程重启登录了，可以对比两个用户是否相同*/
	public static User startUser;
	

	public void setUser(User user) {
		this.user=user;
//		try {
//			CheckNewMessageService.user=(User) user.clone();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			
		
	}

	public static String chatTarget = "";
	public static Boolean chatActivtiyOpenBool;

	public static MyApplication getInstance() {
		return mApplication;
	}
	private List<Activity> activityList = new LinkedList<Activity>();

	// -------------

	// -------------lix start------
	public int getAtyCount() {
		return activityList.size();
	}

	// -------------lix end------

	public void execRunnable(Runnable r) {
		es.execute(r);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		mContext = getApplicationContext();
		es = Executors.newFixedThreadPool(3);
		Thread.setDefaultUncaughtExceptionHandler(this);
		try {
			Context context = createPackageContext("com.szugyi.circlemenu", Context.CONTEXT_IGNORE_SECURITY|Context.CONTEXT_INCLUDE_CODE);
			SharedPreferences preferences = context.getSharedPreferences("userIDFile", Context.MODE_WORLD_READABLE);
			String userId = preferences.getString("userId", "");
			String userName = preferences.getString("account", "");
			String roles = preferences.getString("roles", "");
	
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	
		
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

	@Override
	// 建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消�?
	public void onTerminate() {
		super.onTerminate();
	}



	

	public void exit() {
		try {
			ClearActity();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ActivityManager am = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
			am.restartPackage(mContext.getPackageName());
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}


	}

	// TODO 清空activity
	public void ClearActity() {
		// XmppConnectionManager.getInstance().disconnect();
		// stopService(new Intent(this, PresenceService.class));
		clearActityNotServer();
	}

	/**
	 * 清空所有activity
	 */
	public void clearActityNotServer() {
		ClearActivityTool.clearActityNotServer(activityList);
	}

	/**
	 * 关闭其他activity，留下一个特定的activity
	 * 
	 */
	public void ClearOtherActityLeavingAct(Class<Activity> actClass) {
		ClearActivityTool.ClearOtherActityLeavingAct(activityList, actClass);
	}

	/**
	 * 关闭其他activity，留下一个特定的activity
	 * 
	 */
	public void ClearOtherActityLeavingAct() {
		ClearActivityTool.ClearOtherActityLeavingAct(activityList, null);
	}

	/**
	 * 在列表中，清除特定一个activity
	 * 
	 * @param act
	 */
	public void ClearActity(Activity act) {
		ClearActivityTool.ClearActity(activityList, act);
	}

	/**
	 * 添加activity
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		ClearActivityTool.addActivity(activityList, activity);
	}

	/**
	 * 出现异常时，返回到登�?
	 */
	public void exceptionBackLogin() {
		// MyApplication.user = null;
		// Intent intent7 = new Intent(this, LoginAty.class);
		// startActivity(intent7);
		clearActityNotServer();
	}

	

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		ex.printStackTrace();
//		Toast.makeText(this, getString(R.string.app_exception_handling), Toast.LENGTH_SHORT).show();
//		Intent intent = new Intent(getApplicationContext(), Main_start.class);
//		PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//		AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500, restartIntent);
		System.exit(0);
	}
}
