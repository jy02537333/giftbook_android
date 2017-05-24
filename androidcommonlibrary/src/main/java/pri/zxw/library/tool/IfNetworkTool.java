package pri.zxw.library.tool;



import pri.zxw.library.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;



/**
 * 判断是否有网络
 * @author Zxw
 *
 */
public class IfNetworkTool {
	
	/**
	 * 判断是否有网络,不能在异步方法中掉用
	 * @param context
	 * @return
	 */
	public static boolean isNetwork(Context context)
	{
		return isNetwork(context,true);
	}
	/**
	 * 判断是否有网络
	 * @param context
	 * @param isShowToast 是否显示toast提示
	 * @return
	 */
	public static boolean isNetwork(Context context,boolean isShowToast)
	{
		if(isNetworkAvailable(context))
		{
			return true;
		}else{
			if(isShowToast)
			Toast.makeText(context,context.getResources().getString(R.string.not_connected_to_the_internet) , Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	/**
	 * 
	 * 校验网络-如果没有网络就弹出设置,并返回true.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 上午9:03:56
	 */
	public static boolean validateInternet(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			openWirelessSet(context);
			return false;
		} else {
			NetworkInfo[] info = manager.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		openWirelessSet(context);
		return false;
	}
	/**
	 * 开发无线设置
	 * @param context
	 */
	public static void openWirelessSet(final Context context) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(R.string.prompt).setMessage(context.getString(R.string.check_connection)).setPositiveButton(R.string.menu_settings, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				context.startActivity(intent);
			}
		}).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		dialogBuilder.show();
	}
	
	/**
	 * 
	 * 校验网络-如果没有网络就返回true.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 上午9:05:15
	 */
	public static  boolean hasInternetConnected(Context context)  {
		ConnectivityManager manager =
				(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		if (manager != null) {
			NetworkInfo network = manager.getActiveNetworkInfo();
			if (network != null && network.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否联网
	 * 
	 * @param context
	 *            上下文对象
	 * @return true 联网，false 没有联网
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * wifi是否开启
	 * 
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}
}
