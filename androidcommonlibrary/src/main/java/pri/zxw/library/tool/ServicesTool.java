package pri.zxw.library.tool;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pri.zxw.library.entity.FileByteEntity;
import pri.zxw.library.entity.FileEntity;
import pri.zxw.library.myinterface.IServicesCallback;


/**
 * @className 调用web的一些通用方法
 * @author 张相伟
 * @function 类功能
 * @createDate 2014-12-30
 * @version 1
 * @upadteMemter 2014-12-30
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public abstract class ServicesTool {
	private String baseUrl;
	private Handler mHandler;
	private Context mContext;
	OkHttpClient client = new OkHttpClient();
	/**
	 * 上传文件，name相同时，使用该属性
	 */
	private String fileName;
	/** 是否需要返回msg */
	private boolean isReturnMsg = false;
	/** 是否已经返回过msg */
	private boolean isAlreadyReturnMsg = false;
	private boolean isJsonArray = true;
	/**
	 * 线程id
	 */
	private String mThreadId;

	/**
	 * 上传文件，name相同时，使用该属性
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 上传文件，name相同时，使用该属性
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getmThreadId() {
		return mThreadId;
	}

	public void setmThreadId(String mThreadId) {
		this.mThreadId = mThreadId;
	}

	/**
	 * 设置延迟返回时间
	 */
	private long mDelayDate = 0;
	/**
	 * request请求开始时间
	 */
	private long startDate;

	public static final int REQUEST_CODE = 111;

	public void setIsJsonArray(boolean isJsonArray) {
		this.isJsonArray = isJsonArray;
	}

	public boolean getIsJsonArray() {
		return isJsonArray;
	}


	public ServicesTool(String base_Url, Context context, Handler handler) {
		baseUrl = base_Url;
		mHandler = handler;
		mContext = context;

	}

	public void doGetAndalysisData(Map<String, String> param) {
		doGetAndalysisData(baseUrl, param, REQUEST_CODE);
	}
	public void doGetAndalysisData(String subUrl, Map<String, String> param,
								   final int requestCode, boolean isJsonArray) {
		this.isJsonArray = isJsonArray;
		doGetAndalysisData(subUrl, param, requestCode);
	}

	public void doGetAndalysisData(String subUrl, Map<String, String> param,
								   final int requestCode, long delayDate) {
		mDelayDate = delayDate;
		startDate = System.currentTimeMillis();
		doGetAndalysisData(subUrl, param, requestCode);
	}

	public void doGetAndalysisData(String subUrl, Map<String, String> param,
								   final int requestCode, String threadId) {
		if (threadId != null && mThreadId != null && threadId.equals(mThreadId))
			return;
		this.mThreadId = threadId;
		doGetAndalysisData(subUrl, param, requestCode);
	}

	public void doGetAndalysisDataCall(String subUrl, Map<String, String> param,
								   final int requestCode,final IServicesCallback servicesCallback) {
		if (baseUrl == null || subUrl == null) {
			return;
		}
		if(servicesCallback!=null)
		{
			servicesCallback.onStart();
		}
		initProperty(subUrl, param, requestCode);
		OkHttpUtils.get()
				.url( baseUrl + subUrl)
				.params(param)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception error, int id) {
						failureHandler(call,error, requestCode, null);
						if(servicesCallback!=null)
						{
							servicesCallback.onEnd();
						}
					}
					@Override
					public void onResponse(String response, int id) {
						responseHandler(response, requestCode);
						if(servicesCallback!=null)
						{
							servicesCallback.onEnd();
						}
					}
				});

	}
	public void doGetAndalysisData(String subUrl, Map<String, String> param,
								   final int requestCode) {
		doGetAndalysisDataCall(subUrl,param,requestCode,null);
	}


	public void doPostAndalysisData(String subUrl, Map<String, String> param,
									final int requestCode, long delayDate) {
		mDelayDate = delayDate;
		startDate = System.currentTimeMillis();
		doPostAndalysisData(subUrl, param, requestCode);
	}

	public void doPostAndalysisData(String subUrl, Map<String, String> param,
									final int requestCode, String threadId) {
		if (threadId != null && mThreadId != null && threadId.equals(mThreadId))
			return;
		this.mThreadId = threadId;
		doPostAndalysisData(subUrl, param, requestCode);
	}

	public void doPostAndalysisDataCall(String subUrl, Map<String, String> param,
									final int requestCode,final IServicesCallback servicesCallback) {
		if (baseUrl == null || subUrl == null) {
			return;
		}
		initProperty(subUrl, param, requestCode);
		if(servicesCallback!=null)
			servicesCallback.onStart();
		OkHttpUtils.post()
				.url(baseUrl + subUrl)
				.params(param)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception error, int id) {
						failureHandler(call,error, requestCode, null);
						if(servicesCallback!=null)
							servicesCallback.onEnd();
					}
					@Override
					public void onResponse(String response, int id) {
						responseHandler(response, requestCode);
						if(servicesCallback!=null)
							servicesCallback.onEnd();
					}
				});
	}
	public void doPostAndalysisData(String subUrl, Map<String, String> param,
									final int requestCode) {
		doPostAndalysisDataCall(subUrl,param,requestCode,null);
	}

	/**
	 * post提交，带有返回key值
	 *
	 * @param subUrl
	 * @param param
	 * @param requestCode
	 * @param key
	 */
	public void doPostAndalysisKeyData(String subUrl,
									   Map<String, String> param, final int requestCode, final String key) {
		if (baseUrl == null || subUrl == null) {
			return;
		}
		initProperty(subUrl, param, requestCode);
		OkHttpUtils.post()
				.url(baseUrl + subUrl)
				.params(param)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception error, int id) {
						failureHandler(call,error, requestCode, key);
					}
					@Override
					public void onResponse(String response, int id) {
						responseHandler(response, requestCode,key);
					}
				});
	}

	public void doPostAndalysisData(Map<String, String> param) {
		doPostAndalysisData(baseUrl, param, REQUEST_CODE);
	}

	public void initProperty(String subUrl, Map<String, String> param,
			final int requestCode) {
		isAlreadyReturnMsg = false;
		isReturnMsg = true;
		setTime(requestCode);
		for (String key:param.keySet()) {
			if(param.get(key)==null)
				param.put(key,"");
		}
	}

	public Message getTimeOut(String responseBody, int requestCode) {
		if (responseBody.indexOf("socket time out") != -1) {
			return setTimeOutMsg(requestCode);
		} else
			return null;
	}

	private Message setTimeOutMsg(int requestCode) {
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put(JsonParse.STATUS, "failure");
		retMap.put(JsonParse.MSG, "连接超时！请使用一个信号好的网络！");
		retMap.put(JsonParse.CONTEXT, "null");
		Message msg = new Message();
		msg.what = requestCode;
		msg.arg1 = 0;
		msg.arg2 = AppConstantError.WEB_TIMEOUT;
		msg.obj = retMap;
		return msg;
	}

	public void responseHandler(String responseBody, final int requestCode) {
		responseHandler(responseBody, requestCode, null);

	}

	public void responseHandler(String responseBody, final int requestCode,
			String key) {
		if (!isReturnMsg)
			return;
		isAlreadyReturnMsg = true;
		responseHandlerNotReturn(responseBody, requestCode, key);
	}

	/**
	 * token验证回掉
	 */
	public abstract  void tokenVerifyCallback(Context context);

	/**
	 * 处理responseBody，不管理超时返回
	 *
	 * @param responseBody
	 * @param requestCode
	 */
	public void responseHandlerNotReturn(String responseBody,
			final int requestCode, String key) {
		try {
			if (mHandler == null)
				return;
			Message msg = new Message();
			msg.what = requestCode;
			msg.arg1 = 0;
			if (key != null) {
				Bundle data = new Bundle();
				data.putString("key", key);
				msg.setData(data);
			}
			if (notNetword(responseBody, msg, key)) {
				return;
			}
			// Log.v("result", "result=" + responseBody);
			if (responseBody.indexOf("http://404.safedog.cn") != -1) {
				msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
				mHandler.sendMessage(msg);
				mThreadId = null;
				return;
			}
			Map<String, String> map = null;
			try {
				map = JsonParse.parseReturnValue(responseBody);
				if (map != null && !map.isEmpty()) {
					if (map.get(JsonParse.STATUS).equals("1")) {
						msg.arg1 = 1;
					}
					if(map.get(JsonParse.STATUS).equals("5"))
					{
						tokenVerifyCallback(mContext);
					}
					if(map.containsKey(JsonParse.CONTEXT)&& map.get(JsonParse.CONTEXT).equals("null"))
						map.put(JsonParse.CONTEXT,"");
					if(map.containsKey(JsonParse.SUM_COUNT)&&map.get(JsonParse.SUM_COUNT).equals("null"))
						map.put(JsonParse.SUM_COUNT,"");
					msg.obj = map;
				} else
					msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
			} catch (Exception e) {
				e.printStackTrace();
			}
//			if (mDelayDate > 0
//					&& System.currentTimeMillis() - startDate <= mDelayDate)
//				try {
//					Thread.sleep(mDelayDate);
//					mDelayDate = 0;
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			mThreadId = null;
			mHandler.sendMessage(msg);
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * 判断是否没有网络
	 *
	 * @param responseBody
	 * @param msg
	 * @return
	 */
	private boolean notNetword(String responseBody, Message msg, String key) {

		if (responseBody.indexOf("can't resolve host") != -1
				|| responseBody.indexOf("Network is unreachable") != -1) {
			isAlreadyReturnMsg = true;
			msg.arg1 = 0;
			msg.arg2 = AppConstantError.NOT_NETWORK;
			msg = setKey(msg, key);
			mHandler.sendMessage(msg);
			mThreadId = null;
			return true;
		}
		return false;
	}

	private Message setKey(Message msg, String key) {
		if (key != null) {
			Bundle data = new Bundle();
			data.putString("Key", key);
			msg.setData(data);
		}
		return msg;
	}

	/**
	 * 找不到地址
	 *
	 * @param responseBody
	 * @param msg
	 * @return
	 */
	private boolean notFindUrl(String responseBody, Message msg, String key) {
		if (responseBody.indexOf("java.io.EOFException") != -1
				|| responseBody.indexOf("http://404.safedog.cn") != -1) {
			isAlreadyReturnMsg = true;
			msg.arg1 = 0;
			msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
			msg = setKey(msg, key);
			mHandler.sendMessage(msg);
			mThreadId = null;
			return true;
		}
		return false;
	}

	public void failureHandler(Call call, Exception error, int requestCode, String key) {
//		Log.e("VolleyError", error.getMessage());
		if (mHandler == null)
			return;
		if (!isReturnMsg)

			return;
		isAlreadyReturnMsg = true;
		Message msg = new Message();
		msg.what = requestCode;
		// error.toString() //TimeoutError
		if (error.toString().indexOf("TimeoutError") != -1) {
			msg = setTimeOutMsg(requestCode);
			mThreadId = null;
			mHandler.sendMessage(msg);
		} else if (error.getMessage() == null) {
			isAlreadyReturnMsg = true;
			msg.arg1 = 0;
			msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
			mHandler.sendMessage(msg);

		} else if (!notNetword(error.getMessage(), msg, key)) {
			if (!notFindUrl(error.getMessage(), msg, key)) {
				msg.arg1=0;
				msg.arg2=AppConstantError.WEBSEVICE_WEB_ERROR;
				mHandler.sendMessage(msg);
			}
		}
		mThreadId = null;
	}

	public void failureHandler(final int requestCode) {
		if (!isReturnMsg)
			return;
		isAlreadyReturnMsg = true;
		// 注意失败的处理
		Message msg = new Message();
		msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
		msg.what = requestCode;
		mThreadId = null;
		mHandler.sendMessage(msg);
	}



	public void setTime(final int requestCode) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <= 30; i++) {
					if (isAlreadyReturnMsg)
						return;
					try {
						Thread.sleep(1000);
						if (i == 15) {
							if (!isAlreadyReturnMsg) {
								isReturnMsg = false;
								Message msg = setTimeOutMsg(requestCode);
								mThreadId = null;
								if (mHandler == null)
									return;
								mHandler.sendMessage(msg);
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}



	public Object JsonArrayResponseHanlder(String responseBody,
			final int requestCode, Object parseResponse) {
		Message msg = new Message();
		msg.what = requestCode;
		// Log.v("result", "result=" + responseBody);

		try {

			if (responseBody.indexOf("[") == 0
					&& responseBody.lastIndexOf("]") == responseBody.length() - 1) {
				msg.arg1 = 1;
				msg.obj = responseBody;
			} else {
				msg.arg1 = 0;
				msg.arg2 = AppConstantError.WEBSEVICE_SOAP_FAULT;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mThreadId = null;
		mHandler.sendMessage(msg);
		return parseResponse;
	}


	public void addPutUploadFileRequest(final String subUrl,
										Map<String, String> params,
										String key,
										 Map<String, File> files,
										Map<String, FileByteEntity> paramsByte, final Object tag,
										final int requestCode1) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Connection", "keep-alive");
		headers.put("Accept", "*/*");
		headers.put("Cookie", "add cookies here");
		OkHttpUtils.post().files(key, files)
				.url(baseUrl + subUrl)
				.params(params)//
				.headers(headers)//
				.build()//
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception error, int id) {
						failureHandler(call, error, requestCode1, null);
					}

					@Override
					public void onResponse(String response, int id) {
						responseHandler(response, requestCode1);
					}
				});
	}



}
