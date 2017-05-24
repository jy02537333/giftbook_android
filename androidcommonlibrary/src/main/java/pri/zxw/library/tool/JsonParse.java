package pri.zxw.library.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author 龙流平(LongLiuPing)
 * @version 创建时间：2015年1月28日 下午3:03:13 package com.yunzhi.e_commerce.util;
 */
public class JsonParse {
	public static final String STATUS="result";
	public static final String MSG="msg";
	public static final String CONTEXT="data";
	public static final String SUM_COUNT="sumCount";
	public static Map<String, String> parseReturnValueForLogin(String data) throws Exception {
		if (!isJson(data)) {
			return null;
		}
		JSONObject jsonObj = new JSONObject(data);
		Map<String, String> map = new HashMap<String, String>();
		map.put(STATUS, jsonObj.getString(STATUS));
		map.put(MSG, jsonObj.getString(MSG));
		if(data.indexOf(CONTEXT)!=-1)
		map.put(CONTEXT, jsonObj.getString(CONTEXT));
		return map;
	}
	static boolean isJson(String data)
	{
		if(data == null)
		{
			return false;
		}
		int s = data.trim().indexOf("{");
		int e = data.trim().lastIndexOf("}");
		if(s==0 && e>=0)
		{
			return true;
		}else{
			return false;
		}
	}

	public static Map<String, String> parseForLoginContent(String data) throws Exception {
		if (!isJson(data)) {
			return null;
		}
		JSONObject jsonObj = new JSONObject(data);
		Map<String, String> map = new HashMap<String, String>();
		map.put("roles", jsonObj.getString("roles"));
		map.put("userId", jsonObj.getString("userId"));
		map.put("sessionCode", jsonObj.getString("sessionCode"));
		return map;
	}

	public static Map<String, String> parseReturnValueForChangePwd(String data) throws Exception {
		if (!isJson(data)) {
			return null;
		}
		JSONObject jsonObj = new JSONObject(data);
		Map<String, String> map = new HashMap<String, String>();
		map.put(STATUS, jsonObj.getString(STATUS));
		map.put(MSG, jsonObj.getString(MSG));
		return map;
	}

	public static Map<String, String> parseReturnValue(String data) throws Exception {
		if (!isJson(data)) {
			return null;
		}
		String str = data;
		
		
		JSONObject jsonObj = new JSONObject(data);
		Map<String, String> map = new HashMap<String, String>();
		if(data.indexOf(CONTEXT)!=-1)
			map.put(CONTEXT, jsonObj.getString(CONTEXT)); // 返回内容
		if(data.indexOf(STATUS)!=-1)
			map.put(STATUS, jsonObj.getString(STATUS)); // 状�?
		if(data.indexOf(MSG)!=-1)
			map.put(MSG, jsonObj.getString(MSG)); // 消息
		if(data.indexOf(SUM_COUNT)!=-1)
			map.put(SUM_COUNT, jsonObj.getString(SUM_COUNT)); // 消息
		return map;
	}

	public static List<Map<String, String>> parseAreaData(String data) throws Exception {
		List<Map<String, String>> all = new ArrayList<Map<String, String>>();
		JSONArray jsonArr = new JSONArray(data);
		for (int x = 0; x < jsonArr.length(); x++) {
			Map<String, String> map = new HashMap<String, String>();
			JSONObject jsonObj = jsonArr.getJSONObject(x);
			map.put("id", jsonObj.getString("id")); // 返回内容
			map.put("areaName", jsonObj.getString("areaName")); // 返回内容
			all.add(map);
		}
		return all;
	}

	public static List<Map<String, Object>> parseShopScore(JSONArray jsonArr) throws Exception {
		List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();
		if (jsonArr != null && !jsonArr.equals("")) {
			for (int x = 0; x < jsonArr.length(); x++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jsonObj = jsonArr.getJSONObject(x);

				map.put("userNickName", jsonObj.getString("userNickName"));
				map.put("ratingDate", jsonObj.getString("ratingDate"));
				map.put("scores", jsonObj.getString("scores"));
				map.put("backContent", jsonObj.getString("backContent"));
				map.put("contentDetail", jsonObj.getString("contentDetail"));
				all.add(map);
			}
		}
		return all;
	}
	
	
	public static Map<String, String> parseReturnValueForContent(String data)
			throws Exception {
		if (data == null) {
			return null;
		}
		JSONObject jsonObj = new JSONObject(data);
		Map<String, String> map = new HashMap<String, String>();
		map.put("PARTNER", jsonObj.getString("PARTNER"));
		map.put("SELLER", jsonObj.getString("SELLER"));
		map.put("RSA_PRIVATE", jsonObj.getString("RSA_PRIVATE"));
		return map;
	}

}
