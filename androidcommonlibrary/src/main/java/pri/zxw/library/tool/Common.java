package pri.zxw.library.tool;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Common {

    /**
     * string装换int
     * @return
     */
    public static int strConvertInt(String numStr)
    {
        int num=0;
        try {
            num= Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return num;
    }


	/**
	 * 获取屏幕宽度
	 * @param act
	 * @return
	 */
	public static int getAppScreenWidth(Activity act){
		WindowManager manager = act.getWindowManager();
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		return  outMetrics.widthPixels;
	}
	
	
	/**
	 * 获取一个guid 长度35位
	 * 
	 * @param head
	 * @return
	 */
	public static String getGUID_Length35(String head) {
		int len = 0;
		if (head != null)
			len = head.trim().length();
		String str = head + UUID.randomUUID().toString().substring(len + 1);

		return str;
	}
	/**
	 * 判断是否连上wifi
	 * @param context
	 * @return
	 */
    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }
    
        return false ;
    }
    /** 
     * 判断某个界面是否在前台 
     * @param context 
     * @param className 
     *            某个界面名称 
     */  
    public static boolean isForeground(Context context, String className) {  
        if (context == null || TextUtils.isEmpty(className)) {  
            return false;  
        }  
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
        List<RunningTaskInfo> list = am.getRunningTasks(1);  
        if (list != null && list.size() > 0) {  
            ComponentName cpn = list.get(0).topActivity;  
            if (className.equals(cpn.getClassName())) {  
                return true;  
            }  
        }  
        return false;  
    }
    /*
 * 返回长度为【strLength】的随机数，在前面补0
 */
    public static String getFixLenthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }

}
