package pri.zxw.library.tool;




/**
 * @author 龙流平(LongLiuPing)
 * @version 创建时间：2015年1月28日 上午11:30:14 package com.yunzhi.e_commerce.util;
 */
public class NetUtil {
	
	public static final String NETWORK_ERROR = "network_error";
	
	public static final String IP = "114.215.81.246";     
	public static final String PORT = "8080";         
	public static final String BASE_URL = "http://" + IP + ":" + PORT + "/GzHnCRMSystem/";
	/**
	 * 访问地址
	 * @return
	 */
	public static String getBaseUrl()  
	 {
		 return "http://" + IP + ":" + PORT + "/GzHnCRMSystem/";
	 }
	public NetUtil() {
	}

	// 用来统计接口的数量
	public class TYPE {
		public static final String login = "1";
		public static final String changePwd = "2";
	}

	public String getUrl(String type) {
		String url = null;
		if (type.equals(TYPE.login)) {
			url = "phoneLogin";
		} else if (type.equals(TYPE.changePwd)) {
			url = "phoneLogin/changePassword";
		}
		return url;
	}
	
}
