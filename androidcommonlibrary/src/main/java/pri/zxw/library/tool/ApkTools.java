package pri.zxw.library.tool;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class ApkTools {
	public static boolean isAppInstalled(Context context, String uri) {
		PackageManager pm = context.getPackageManager();
		boolean installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}
	/**
	 * 关闭app
	 * @param context
	 * @param packageName
	 */
	public static void closeApp(Context context,String packageName)
	{
		try {
			ActivityManager manager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);   
//			manager.killBackgroundProcesses(packageName); 
			manager.restartPackage(packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void startApp(Activity act,String packingName,String startActivityName ) {
			Intent mIntent = new Intent();
			ComponentName comp = new ComponentName(packingName,
					startActivityName);
			mIntent.setComponent(comp);
			mIntent.setAction("android.intent.action.MAIN");
			mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			act.startActivity(mIntent);
	}
}
