package pri.zxw.library.tool;

import java.util.Map;

import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.base.MyPullToRefreshBaseActivity;
import pri.zxw.library.base.MyPullToRefreshBaseInterface;

import android.app.Activity;
import android.os.Message;

/**
 * @className 网络返回的map处理
 * @author 张相伟
 * @function 类功能
 * @createDate 2015-2-3
 * @version 1
 * @upadteMemter 2015-2-3
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class NetReturnMapHandler {
	public static String getContext(Map<String, String> map,Activity act,
			MyPullToRefreshBaseInterface myPullToRefreshBaseInterface  )
	{
		
		if(map!=null&&map.containsKey("context"))
		{
			String contextStr=map.get("context");
			String statusStr=map.get("status");
			String msgStr=map.get("msg");
			if(contextStr!=null&&!contextStr.equals("null")&&contextStr.length()>0)
			{
				return contextStr;
			}
			else if((contextStr==null||contextStr.equals("null"))
					&&msgStr!=null&&!msgStr.equals("null"))
			{
				ToastShowTool.myToastShort(act, msgStr);
			}
			else {
				ToastShowTool.myToastShort(act, "网络异常！");
			}
		}else {
			ToastShowTool.myToastShort(act, "网络异常！");
		}
		if(myPullToRefreshBaseInterface!=null)
		myPullToRefreshBaseInterface.CurrPageMinus();
		return null;
	}
	public static String returnInt(Map<String, String> map,Activity act)
	{
		
		if(map!=null&&map.containsKey("context"))
		{
			String contextStr=map.get("context");
			String statusStr=map.get("status");
			String msgStr=map.get("msg");
			if(statusStr!=null&&!statusStr.equals("null"))
			{
				if(msgStr!=null&&!msgStr.equals("null")&&msgStr.length()>0)
				ToastShowTool.myToastShort(act, msgStr);
				if(statusStr.equals("success"))
					return "1";
				else {   
					return "0";
				}
			}else {
				ToastShowTool.myToastShort(act, "网络异常！");
			}
		}
		else {
			ToastShowTool.myToastShort(act, "网络异常！");
		}
		return null;
	}
	public static String returnInt(Message msg,Activity act)
	{
		return returnInt((Map<String, String>)msg.obj,act);
	}
	public static String getContext(Message msg,Activity act,MyPullToRefreshBaseInterface myPullToRefreshBaseInterface)
	{
		return getContext((Map<String, String>)msg.obj,act,myPullToRefreshBaseInterface );
	}
}
