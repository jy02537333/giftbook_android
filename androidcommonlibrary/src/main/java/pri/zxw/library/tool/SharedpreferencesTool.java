package pri.zxw.library.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SharedpreferencesTool {
	
	public static final String SEARCH_HISTORY_FILE = "lost_find_searchHistory";
	public static  final String  USER_CONFIG_FILE="userConfig";
	public static  final String  USER_CONFIG_FILE_ID="userConfig_id";
	public static  final String  USER_CONFIG_FILE_ACCOUNT="userConfig_ACCOUNT";
	public static  final String  USER_CONFIG_FILE_NICkNAME="userConfig_nickname";
	public static  final String  USER_CONFIG_FILE_HEAD="userConfig_head";
	public static  final String  USER_CONFIG_FILE_GENDER="userConfig_gender";
	public static  final String  USER_CONFIG_FILE_PASSWORD="userConfig_PASSWORD";
	public static  final String  USER_CONFIG_FILE_OAUTH_TYPE="USER_CONFIG_FILE_OAUTH_TYPE";
	public static  final String  USER_CONFIG_FILE_OPENID="USER_CONFIG_FILE_OPENID";
	public static  final String  USER_NEWS_HISTORY="USER_NEWS_HISTORY";
	public static final String USER_SETTING_FILE = "UserSetting"; 
	/**
	 * app首次启动
	 */
	public static final String APP_FIRST_START = "APP_FIRST_START"; 
	/**
	 * 初次启动
	 */
	public static final String APP_FIRST_START_FILE="firstStart";
	
	public static void addDataToPreferences(Context context,String fileName,HashMap<String, String> data){
		SharedPreferences preferences =context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		Iterator<String> iterator =  data.keySet().iterator();
		while (iterator.hasNext()) {
			String key =iterator.next();
			String value=data.get(key);
			editor.putString(key, value);
		}
		editor.commit();
	}
	
	public static void addDataToPreferences(Context context,String fileName,String key, String value) {
		SharedPreferences preferences =context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static Map<String, String> getAllFromPreferences(Context context,String fileName) {
		SharedPreferences preferences =context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return (Map<String, String>) preferences.getAll();
	}

	public static HashMap<String, String> getDataFromPreferences(Context context,String fileName,String key){
		SharedPreferences preferences =context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		HashMap<String, String> data = new HashMap<String, String>();
		data.put(key, preferences.getString(key, ""));
		return data;

	}

}
