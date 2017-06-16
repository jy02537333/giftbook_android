/**  
* @Title: MyBaseActivity.java
* @Package com.zn.interact.base
* @Description: TODO(用一句话描述该文件做什么)
* @author A18ccms A18ccms_gmail_com  
* @date 2014-11-11 下午2:42:38
* @version V1.0  
*/ 
package pri.zxw.library.base;

 

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import pri.zxw.library.R;
import pri.zxw.library.entity.AppPropertyInfo;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.SystemBarTintManager;

/**
 * @ClassName: MyBaseActivity
 * @Description: 所有的activity都应该继承该类
 * @author Lix
 * @date 2014-11-11 下午2:42:38
 *
 */
public class MyBaseActivity extends Activity {
	protected SharedPreferences preferences;
	public static final int GET_DATA_CODE=9001;
	public static final int GET_ADD_CODE=9002;
	public static final int LOAD_CODE=9003;
	public static final int DEL_CODE=9004;
	public static final int EDIT_CODE=9005;
	public static final int ADD_CHILD_CODE=9006;
	public static final int GO_ADD=9101;
	public boolean isSub=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		AppPropertyInfo.activityList.add(this);
		initSystemBar(this);
	}


	public static void initSystemBar(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(activity, true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(activity);
		tintManager.setStatusBarTintEnabled(true);
		// 使用颜色资源
		tintManager.setStatusBarTintResource(R.color.com_color1);
	}


	@TargetApi(19)
	private static void setTranslucentStatus(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	@Override
	protected void onPause() {
		super.onPause();
//		UMMessageAndStatisticsTool.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
//		UMMessageAndStatisticsTool.onResume(this);
	}
	@Override
	protected void onDestroy() {
		AppPropertyInfo.activityList.remove(this);
		ProgressDialogTool.getInstance(this).dismissDialog();
		super.onDestroy();
	}
}
