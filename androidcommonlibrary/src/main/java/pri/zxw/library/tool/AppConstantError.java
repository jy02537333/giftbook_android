package pri.zxw.library.tool;

/**
 * 错误玛记录
 * @author Zxw
 *
 */
public class AppConstantError {
	/**
	 * 连接网络错误
	 */
   public static final Integer WEBSEVICE_WEB_ERROR=1000;
   /**
    * 连接网络异常
    */
   public static final Integer WEBSEVICE_SOAP_FAULT=2000;
   /**
    * 连接超时错误
    */
   public static final Integer WEB_TIMEOUT=15025;
   /**
    * 没有网络错误
    */
   public static final Integer NOT_NETWORK=999999;
   /**
    * 添加数据，重复主键错误
    */
   public static final Integer REGIST_PK_ERROR=2627;
    /**
     * 没有加载到数据
     */
    public static final Integer LOAD_NULL=2;
   	/**
	 * 代码错误，未知错误
	 */
	public static final Integer STSYEM_ERROR=9234234;
   /**
    * json转换错误
    */
   public static final Integer JSON_ERROR=37894;
   /**
    * 不再同一社区
    */
   public static final Integer NOT_COMMUNITY=555;
   
   /**
    * 已经是好友了
    */
   public static final Integer EXITSTS_NEIGHBORHOOD=66666;
   /**
    * 服务端json错误
    */
   public static final Integer NET_JSON_ERROR=9999;
   
   /**
    * 服务端sql错误
    */
   public static final Integer NET_SQL_ERROR=999;
   
}
