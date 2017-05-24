package pri.zxw.library.tool;


public class AppConstantIP {

	public static String SERVERS_URL="http://192.168.3.199";
	/**
	 * wcf命名空间
	 */
	public static final String SERVICE_WCF_NAMESPACE="http://tempuri.org/";
	/**
	 * wcf地址
	 */
	public static final String SERVICE_WCF_URL=SERVERS_URL+":8026/";
	
	/**
	 * 用户在线状态获取地址
	 */
	public static final String USER_ON_LINE_URL = SERVERS_URL+":9090/plugins/online/status";
	
	/**
	 * 上传图片路径
	 */
	public static final String UPLOAD_IMG_URL=SERVERS_URL+":8081/ImageServer/upServer";
	/**
	 * 图片获取地址
	 */
	public static final String FILES_OFUSER_HEAD_ICON = SERVERS_URL+":8081/ImageServer/files/";
	
	public static final int XMPP_SERVICE_PORT = 5222;
	
	public static final String XMPP_SERVICE_NAME = "192.168.3.199";
	
}
