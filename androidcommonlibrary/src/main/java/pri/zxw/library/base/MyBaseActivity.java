/**  
* @Title: MyBaseActivity.java
* @Package com.zn.interact.base
* @Description: TODO(用一句话描述该文件做什么)
* @author A18ccms A18ccms_gmail_com  
* @date 2014-11-11 下午2:42:38
* @version V1.0  
*/ 
package pri.zxw.library.base;

 

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import pri.zxw.library.entity.AppPropertyInfo;

/**
 * @ClassName: MyBaseActivity
 * @Description: 所有的activity都应该继承该类
 * @author Lix
 * @date 2014-11-11 下午2:42:38
 *
 */
public class MyBaseActivity extends Activity {
	protected SharedPreferences preferences;
	public static final int GET_DATA_CODE=1111;
	public static final int GET_ADD_CODE=2222;
	/**添加子级**/
	public static final int ADD_CHILD_CODE=9494;
	public static final int LOAD_CODE=3333;
	public static final int DEL_CODE=4444;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		AppPropertyInfo.activityList.add(this);
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
		super.onDestroy();
	}
}
