package pri.zxw.library.tool;

import pri.zxw.library.R;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;


/**
 * @className Toast提示工具
 * @author 张相伟
 * @function 类功能
 * @createDate 2014-12-30
 * @version 1
 * @upadteMemter 2014-12-30
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class ToastShowTool {
	
	public static android.content.res.Resources getResources(Context act)
	{
		
		  return act.getResources();
	}
	
	public static String  getResourcesString(Context act,int stringRID)
	{
		  return getResources(act).getString(stringRID);
	}

	/**
	 * 是否有json或者sql错误
	 * @param act
	 * @param errorCode
	 * @return
	 */
	public static boolean showJsonOrSqlError(Context act,Integer errorCode)
	{
		if(errorCode!=null)
		{
			if(errorCode.equals(AppConstantError.NET_SQL_ERROR)||
					errorCode.equals(AppConstantError.NET_JSON_ERROR))
			{
				myToastLong(act, getResourcesString(act, R.string.net_fail));
				return true;
			}
		}
		return false;
	}
	
		/**
		 * 网络操作失败
		*/
	  public static   void  showNetFail(Context act)
	  {
			myToastLong(act, getResourcesString(act, R.string.net_fail));
	  }
	  /**
	 * 网络操作成功
	 */
	public static void  showNetSuccess(Context act)
	{
		myToastLong(act, getResourcesString(act, R.string.net_ok));
	}
	/**
	 * 主键重复错误
	 */
	public static void  showPKError(Context act)
	{
		myToastLong(act, getResourcesString(act, R.string.net_PK_error));
	}
	/**
	 * 网络错误提示
	 */
	public static void showNetError(Context act)
	{
		myToastLong(act, getResourcesString(act, R.string.net_error));
	}
	
	/**
	 * 最后一页数据提示
	 */
	public static void showLastPrompt(Context act)
	{
		myToastLong(act, getResourcesString(act, R.string.paging_last_show_content));
	}
	/**
	 * 空数据提示
	 */
	public static void showEmptyPrompt(Context act)
	{
		myToastLong(act, getResourcesString(act, R.string.paging_search_show_empty_content));
	}
	
	/**
	 * 显示长时间消息
	 * @param act
	 * @param content
	 */
	public static void myToastLong(Context act,String content)
	{
		Toast.makeText(act, content, Toast.LENGTH_LONG).show();
	}
	/**
	 * 显示短时间消息
	 * @param act
	 * @param content
	 */
	public static void myToastShort(Context act,String content)
	{
		Toast.makeText(act, content, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 连接超时
	 * @param act
	 */
	public static void myToastTimeout(Context act)
	{
		Toast.makeText(act, act.getResources().getString(R.string.not_connected_to_the_internet)
				, Toast.LENGTH_LONG).show();
	}
	/**
	 * 无网络错误提示
	 * @param act  
	 */
	public static void myToastNotNetwork(Context act)
	{
		Toast.makeText(act,act.getResources().getString(R.string.not_connected_to_the_internet)
				, Toast.LENGTH_LONG).show();
	}
	/**
	 * 请先连接网络！再进行操作
	 * @param act  
	 */
	public static void myToastNetworkOpare(Context act)
	{
		myToastShort(act, "请先连接网络！再进行操作！");
	}
}
