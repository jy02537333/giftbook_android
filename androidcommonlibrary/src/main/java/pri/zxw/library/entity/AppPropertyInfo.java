package pri.zxw.library.entity;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class AppPropertyInfo extends Application {
	public static List<Activity> activityList = new LinkedList<Activity>();
	public static User user;
	public static String EquipmentUid;
	protected static String collectsStr = "";
	/**
	 * 新闻评论点赞
	 */
	protected static String praisesStr = "";
	/**
	 * 新闻点赞
	 */
	protected static String newsPraiseStr="";
	/**
	 * 爆料点赞记录
	 */
	protected static String customNewsCollects = "";
	/**
	 * 爆料点赞记录
	 */
	protected static String customNewsPraises = "";
	/**
	 * 是否切换到消息
	 */
	public static boolean isTabInfoFragment=false;
	/**
	 * 爆料评论点赞记录
	 */
	protected static String customNewsCommentPraises = "";
	public static List<ComInfo> cominfos;
	/**
	 * 是否网络断开
	 */
	public static boolean isNetworkDisconnected;
	/**
	 * 阅读历史
	 */
	protected static String historyStr = "";
	/**
	 * 阅读爆料历史
	 */
	protected static String customNewsHistoryStr = "";
	protected static String um_device_token="";
	
	
	public static String getUmDeviceToken() {
		return um_device_token;
	}
	public static void setUmDeviceToken(String um_device_token) {
		AppPropertyInfo.um_device_token = um_device_token;
	}
	/**
	 * 爆料阅读历史
	 */
	public static String getCustomNewsHistoryStr() {
		return customNewsHistoryStr;
	}
	/**
	 * 爆料阅读历史
	 * @param _customNewsHistoryStr
	 */
	public static void setCustomNewsHistoryStr(String _customNewsHistoryStr) {
		customNewsHistoryStr = _customNewsHistoryStr;
	}
	/**
	 * 爆料阅读历史
	 * @param _customNewsHistoryStr
	 */
	public static void addCustomNewsHistoryStr(String _customNewsHistoryStr) {
		customNewsHistoryStr += ","+_customNewsHistoryStr+",";
	}
	/**
	 * 爆料收藏
	 * 
	 */
	public static String getCustomNewsCollects() {
		return customNewsCollects;
	}

	/**
	 * 爆料收藏
	 * 
	 * @param value
	 */
	public static void setCustomNewsCollects(String value) {
		customNewsCollects = value;
	}

	/**
	 * 爆料收藏
	 * 
	 * @param value
	 */
	public static void addCustomNewsCollects(String value) {
		if (value.indexOf(",") == -1)
			value = "," + value + ",";
		customNewsCollects += value;
	}

	/**
	 * 爆料点赞记录
	 */
	public static String getCustomNewsPraises() {
		return customNewsPraises;
	}

	/**
	 * 爆料点赞记录
	 */
	public static void setCustomNewsPraises(String value) {
		customNewsPraises = value;
	}

	/**
	 * 爆料点赞记录
	 */
	public static void addCustomNewsPraises(String value) {
		if (value.indexOf(",") == -1)
			value = "," + value + ",";
		customNewsPraises += value;
	}

	/**
	 * 爆料评论点赞记录
	 */
	public static String getCustomNewsCommentPraises() {
		return customNewsCommentPraises;
	}

	/**
	 * 爆料评论点赞记录
	 */
	public static void setCustomNewsCommentPraises(String value) {
		customNewsCommentPraises = value;
	}

	/**
	 * 爆料评论点赞记录add
	 */
	public static void addCustomNewsCommentPraises(String value) {
		if (value.indexOf(",") == -1)
			value = "," + value + ",";
		customNewsCommentPraises += value;
	}
/**
 * 新闻点赞
 * @return
 */
	public static String getNewsPraiseStr() {
		return newsPraiseStr;
	}
	/**
	 * 新闻点赞
	 * @return
	 */
	public static void setNewsPraiseStr(String _newsPraiseStr) {
		newsPraiseStr = _newsPraiseStr;
	}
	/**
	 * 新闻点赞
	 * @return
	 */
	public static void addNewsPraiseStr(String _newsPraiseStr) {
		newsPraiseStr +=  "," + _newsPraiseStr + ",";
	}
	/**
	 * 阅读历史
	 */
	public String getHistoryStr() {
		return historyStr;
	}

	/**
	 * 阅读历史
	 */
	public void setHistoryStr(String _historyStr) {
		historyStr = _historyStr;
	}
	/**
	 * 新闻收藏
	 */
	public String getCollectsStr() {
		return collectsStr;
	}
	/**
	 * 新闻收藏
	 */
	public static void setCollectsStr(String _collectsStr) {
		collectsStr = _collectsStr;
	}
	/**
	 * 新闻收藏
	 */
	public static void addCollectsStr(String value) {
		if (value.indexOf(",") == -1)
			value = "," + value + ",";
		collectsStr += value;
	}
	/**
	 * 新闻评论点赞
	 */
	public static String getPraisesStr() {
		return praisesStr;
	}
	/**
	 * 新闻评论点赞
	 */
	public static void setPraisesStr(String _praisesStr) {
		praisesStr = _praisesStr;
	}
	/**
	 * 新闻评论点赞
	 */
	public static void addPraisesStr(String value) {
		if (value.indexOf(",") == -1)
			value = "," + value + ",";
		praisesStr += value;
	}

	/**
	 * 是否网络断开
	 */
	public boolean isNetworkDisconnected() {
		return isNetworkDisconnected;
	}

	/**
	 * 是否网络断开
	 */
	public void setNetworkDisconnected(boolean _isNetworkDisconnected) {
		isNetworkDisconnected = _isNetworkDisconnected;
	}

	public User getUser() {
		if(user==null)
		{
			user=new User();
			return user.getUser();
		}
		return user;
	}

	public void setUser(User _user) {
		user = _user;
	}
	public static  void clearHistory() {
		collectsStr = "";
		praisesStr = "";
		customNewsCollects = "";
		customNewsPraises = "";
		customNewsCommentPraises = "";
		historyStr = "";
		customNewsHistoryStr = "";
		newsPraiseStr="";
	}
	

}
