package pri.zxw.library.tool;//package pri.zxw.library.tool;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.http.Header;
//
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//
///**
// * @className 调用web的一些通用方法
// * @author 张相伟
// * @function 类功能
// * @createDate 2014-12-30
// * @version 1
// * @upadteMemter 2014-12-30
// * @ChangedBy 张相伟
// * @ChangedContent 修改内容
// * @param <T>
// */
//public class CopyOfServicesTool11 {
//	private String baseUrl;
//	private Handler mHandler;
//	/**是否需要返回msg*/
//	private  boolean isReturnMsg=false;
//	/**是否已经返回过msg*/
//	private  boolean isAlreadyReturnMsg=false;
//	private  boolean isJsonArray=true;
//	/**
//	 * 线程id
//	 */
//	private String mThreadId;
//	
//	public String getmThreadId() {
//		return mThreadId;
//	}
//	public void setmThreadId(String mThreadId) {
//		this.mThreadId = mThreadId;
//	}
//	/**
//	 * 设置延迟返回时间
//	 */
//	private long mDelayDate=0;
//	/**
//	 * request请求开始时间
//	 */
//	private long startDate;
//	
//	public static   final int REQUEST_CODE = 111;  
//	public void setIsJsonArray(boolean isJsonArray)
//	{
//		this.isJsonArray=isJsonArray;
//	}
//	public boolean getIsJsonArray()
//	{
//		return isJsonArray;
//	}
//	public CopyOfServicesTool11(String base_Url, String subUrl, Handler handler) {
//		baseUrl = base_Url;
//		mHandler = handler;
//	}
//
//	public CopyOfServicesTool11(String base_Url, Handler handler) {
//		baseUrl = base_Url;
//		mHandler = handler;
//		
//	}
//
//	public void doGetAndalysisData(RequestParams param) {
//		doGetAndalysisData(baseUrl, param, REQUEST_CODE);
//	}
//	public void initProperty(String subUrl, RequestParams param, final int requestCode)
//	{
//		isAlreadyReturnMsg=false;
//		isReturnMsg=true;
//		setTime(requestCode);
//	}
//	public Message  getTimeOut(String responseBody,int requestCode)
//	{						
//		if(responseBody.indexOf("socket time out")!=-1)
//		{
//			return setTimeOutMsg(requestCode);
//		}
//		else 
//			return null;
//	}
//	private Message setTimeOutMsg(int requestCode)
//	{
//		Map<String, String> retMap=new HashMap<String, String>();
//		retMap.put(JsonParse.STATUS, "failure");
//		retMap.put(JsonParse.MSG, "连接超时！请使用一个信号好的网络！");
//		retMap.put(JsonParse.CONTEXT, "null");
//		Message msg=new Message();
//		msg.what=requestCode;
//		msg.arg1=0;
//		msg.arg2=AppConstantError.WEB_TIMEOUT;
//		msg.obj=retMap;
//		return msg;
//	}
//
//	public void responseHandler(String responseBody,final int requestCode)
//	{
//		if(!isReturnMsg)
//			return ;
//		isAlreadyReturnMsg=true;
//		
//		//if(isJsonArray)
//		//	return JsonArrayResponseHanlder( responseBody, requestCode,parseResponse);
//		Message msg = new Message();
//		msg.what = requestCode;
//		msg.arg1 = 0;
//		if(notNetword(responseBody, msg))
//		{
//			return ;
//		}
//		Log.v("result", "result=" + responseBody);
//		if( responseBody.indexOf("http://404.safedog.cn")!=-1)
//		{
//			msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
//			mHandler.sendMessage(msg);
//			mThreadId=null;
//			return ;
//		}
//		Map<String, String> map = null;   
//		try {
//			map = JsonParse.parseReturnValue(responseBody);
//			if (map != null && !map.isEmpty()) {
//				if (map.get(JsonParse.STATUS).equals("0") || 
//						(map.get(JsonParse.CONTEXT)!=null&&!map.get(JsonParse.CONTEXT).equals("null"))) {
//					msg.arg1 = 1;
//				}
//				msg.obj = map;
//			} else
//				msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (mDelayDate>0&&System.currentTimeMillis() - startDate <= mDelayDate)
//			try {
//				Thread.sleep(mDelayDate);
//				mDelayDate=0;
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		mThreadId=null;
//		mHandler.sendMessage(msg);
//		Thread.currentThread().interrupt();
//	}
//	/**
//	 * 判断是否没有网络 
//	 * @param responseBody
//	 * @param msg
//	 * @return
//	 */
//	private boolean notNetword(String responseBody,Message msg)
//	{
//		
//		if( responseBody.indexOf("can't resolve host")!=-1)
//		{
//			msg.arg1 = 0;
//			msg.arg2 = AppConstantError.NOT_NETWORK;
//			mHandler.sendMessage(msg);
//			mThreadId=null;
//			return true;
//		}
//		return false;
//	}
//	public void failureHandler(final int requestCode)
//	{
//		if(!isReturnMsg)
//			return ;
//		isAlreadyReturnMsg=true;
//		// 注意失败的处理
//		Message msg = new Message();
//		msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
//		msg.what=requestCode;
//		mThreadId=null;
//		mHandler.sendMessage(msg);
//	}
//	public void doGetAndalysisData(String subUrl, RequestParams param, final int requestCode,boolean isJsonArray) {
//		this.isJsonArray=isJsonArray;
//		doGetAndalysisData( subUrl,  param, requestCode);
//	}
//	public void doGetAndalysisData(String subUrl, RequestParams param, final int requestCode,long delayDate) {
//		mDelayDate=delayDate;
//		startDate=System.currentTimeMillis();
//		doGetAndalysisData( subUrl,  param, requestCode);
//	}
//	public void doGetAndalysisData(String subUrl, RequestParams param, final int requestCode,String threadId)
//	{
//		if(threadId!=null&&mThreadId!=null&&threadId.equals(mThreadId))
//			return ;
//		this.mThreadId=threadId;
//		doGetAndalysisData( subUrl,  param, requestCode);
//	}
//	public void doGetAndalysisData(String subUrl, RequestParams param, final int requestCode) {
//		
//		if (baseUrl == null || subUrl == null ) {
//			return;
//		}
//		initProperty( subUrl,  param,   requestCode);
//		ClientConnect.get(baseUrl, subUrl, param, new TextHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode, Header[] headers,
//					String responseString) {
//					if(requestCode==4321)
//						Log.d("新闻加载错误结果字符", responseString);
//					 responseHandler(responseString,requestCode);
//			}
//			
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					String responseString, Throwable throwable) {
//				failureHandler(statusCode);
//			}
//		});
//	}
//	public void setTime(final int requestCode)
//	{
//		Thread thread=new Thread(new Runnable() {
//			@Override
//			public void run() {
//				for (int i = 0; i <= 30; i++) {
//					if(isAlreadyReturnMsg)
//						return ;
//					try {
//						Thread.sleep(1000);
//						if(i==15)
//						{
//							if(!isAlreadyReturnMsg)
//							{
//								isReturnMsg=false;   
//								Message msg=setTimeOutMsg(requestCode);
//								mThreadId=null;
//								mHandler.sendMessage(msg);
//							}
//						}
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}  
//		});
//		thread.start();
//	}
//	public void doPostAndalysisData(String subUrl, RequestParams param, final int requestCode,boolean isJsonArray) {
//		this.isJsonArray=isJsonArray;
//		doPostAndalysisData( subUrl,  param, requestCode);
//	}
//	public void doPostAndalysisData(String subUrl, RequestParams param, final int requestCode,long delayDate) {
//		mDelayDate=delayDate;
//		startDate=System.currentTimeMillis();
//		doPostAndalysisData( subUrl,  param, requestCode);
//	}
//	public void doPostAndalysisData(String subUrl, RequestParams param, final int requestCode,String threadId)
//	{
//		if(threadId!=null&&mThreadId!=null&&threadId.equals(mThreadId))
//			return ;
//		this.mThreadId=threadId;
//		doPostAndalysisData( subUrl,  param, requestCode);
//	}
//	public void doPostAndalysisData(String subUrl, RequestParams param, final int requestCode) {
//		if (baseUrl == null || subUrl == null ) {
//			return;
//		}
//		initProperty( subUrl,  param,   requestCode);
//		ClientConnect.post(baseUrl, subUrl, param, new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode, Header[] headers,
//					String responseString) {
//					if(requestCode==4321)
//						Log.d("新闻加载错误结果字符", responseString);
//					 responseHandler(responseString,requestCode);
//			}
//			
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					String responseString, Throwable throwable) {
//				failureHandler(statusCode);
//			}
//		});   
//	}
//	public void doPostAndalysisData(RequestParams param) {
//		doPostAndalysisData(baseUrl, param, REQUEST_CODE);
//	}
//	public Object JsonArrayResponseHanlder(String responseBody,final int requestCode,
//			Object parseResponse)
//	{
//		Message msg = new Message();
//		msg.what = requestCode;
//		Log.v("result", "result=" + responseBody);
//		
//		try {
//		
//			if (responseBody.indexOf("[")==0
//					&&responseBody.lastIndexOf("]")==responseBody.length()-1) {
//					msg.arg1 = 1;
//				msg.obj = responseBody;
//			} else
//			{
//				msg.arg1 = 0;
//				msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		mThreadId=null;
//		mHandler.sendMessage(msg);
//		return parseResponse;
//	}
//}
