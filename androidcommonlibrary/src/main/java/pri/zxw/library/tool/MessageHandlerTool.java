package pri.zxw.library.tool;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import pri.zxw.library.base.BaseApp;
import pri.zxw.library.base.BaseEntity;
import pri.zxw.library.base.MyBaseAdapter;
import pri.zxw.library.base.MyPullToRefreshBaseInterface;
import pri.zxw.library.db.JsonStrHistoryDao;
import pri.zxw.library.tool.AppConstantError;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.ToastShowTool;

import android.content.Context;
import android.content.Entity;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.handmark.pulltorefresh.library.DateCommon;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

/**
 * 处理 android.os.Message的返回值
 * 
 * @author 张相伟
 * 
 */
public class MessageHandlerTool {
	public static final int HASH_VALUE = 1;
	public static final int ERROR = -1;
	public static final int END = 2;
	public String jsonStr;
	/**
	 * 是否网络方面错误
	 */
	private boolean isNetworkError = false;
	/**
	 * 是否启动上拉刷新
	 */
	private boolean mIsEnableUpRefresh = true;
	/**
	 * 是否有网络错误
	 * @return
	 */
	public boolean getIsNetworkError()
	{
		return isNetworkError;
	}
	public MessageHandlerTool(boolean isEnableUpRefresh) {
		mIsEnableUpRefresh = isEnableUpRefresh;
	}

	public MessageHandlerTool() {
		
	}

	/**
	 *
	 * @param msg
	 * @param type
	 * @return type的对象
	 */
	public Object handlerObject(Message msg, Type type,Context context) {
		Object obj = null;
		try {
			if (msg.arg1 == 1) {
                @SuppressWarnings("unchecked")
                Map<String, String> map = (Map<String, String>) msg.obj;
                Gson gson = new Gson();
                if (map != null) {
                    String status = map.get(JsonParse.STATUS);
                        if (status.equals("1")) {
                            String data=map.get(JsonParse.CONTEXT);
							this.jsonStr=data;
                            Object retObj = gson.fromJson(data, type);
                            return retObj;
                        }
                }
            }else {//处理网络错误提示
                networkErrorShow(msg,context);
            }
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 *
	 * @param msg
	 */
	public String handlerData(Message msg,Context context) {
		if (msg.arg1 == 1) {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) msg.obj;
			Gson gson = new Gson();
			if (map != null) {
				String status = map.get(JsonParse.STATUS);
				if (msg.arg1 == 1) {
					if (status.equals("1")) {
						return map.get(JsonParse.CONTEXT);
					}
				}
			}
		}else {//处理网络错误提示
			networkErrorShow(msg,context);
		}
		return "";
	}


	/**
	 *
	 * @param msg
	 * @return type的对象
	 */
	public int handler(Message msg,Context context) {
		int obj = 0;

		if (msg.arg1 == 1) {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) msg.obj;
			Gson gson = new Gson();
			if (map != null) {
				String status = map.get(JsonParse.STATUS);
				if (msg.arg1 == 1) {
					if (status.equals("1")) {
						return 1;
					}
				}
			}
		}else {//处理网络错误提示
			networkErrorShow(msg,context);
		}
		return obj;
	}

	/**
	 * listview 刷新请求的返回处理，包括网络错误的提示
	 * @param msg
	 * @return type的对象
	 */
	@SuppressWarnings("rawtypes")
	public MessageInfo handler(Message msg,
			MyPullToRefreshBaseInterface contenxt, MyBaseAdapter adapter,
			PullToRefreshBase listView, Type type) {
		return handler(msg,contenxt,adapter,listView, type,null);
	}

	/**
	 * listview 刷新请求的返回处理，包括网络错误的提示
	 * @param msg
	 * @param key json实体的key
	 * @return type的对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MessageInfo handler(Message msg,
			MyPullToRefreshBaseInterface contenxt, MyBaseAdapter adapter,
			PullToRefreshBase listView, Type type,String key) {
		MessageInfo messageInfo = new MessageInfo();
		if (adapter == null)
			return messageInfo;
		try {
			List tList = null;
			if (msg.arg1 == 1) {
				Map<String, String> map = (Map<String, String>) msg.obj;
				messageInfo.retMap=map;
				Gson gson = new Gson();
				if (map != null) {
					String status = map.get(JsonParse.STATUS);
					if (status.equals("1")) {
						String data=map.get(JsonParse.CONTEXT);
						if(key!=null)
						{
							JSONObject jsonObj = new JSONObject(data);
							data=jsonObj.getString(key);
						}
						try {
							tList = gson.fromJson(data, type);
						} catch (JsonSyntaxException e) {
							JsonReader reader = new JsonReader(new StringReader(data));
							reader.setLenient(true);
							tList= gson.fromJson(reader, type);
						}
						if (tList != null && tList.size() > 0) {
							if (contenxt.getUpfalg()) {
								adapter.remove();
								if (mIsEnableUpRefresh)
									contenxt.enableUpRefresh();
							}
							adapter.addDataAll(tList);
							if (contenxt.getUpfalg())
								{
								adapter.notifyDataSetInvalidated();
								contenxt.enableUpRefresh();
								JsonStrHistoryDao dao=new JsonStrHistoryDao();
								dao.updateUserHistory(contenxt.getClass().getName()+"_time", 
										DateCommon.getCurrentDateStr());
								dao.updateUserHistory(contenxt.getClass().getName()+"_json", 
										map.get(JsonParse.CONTEXT));
								}
							else
								adapter.notifyDataSetChanged();
							messageInfo.isHashValue = true;
							messageInfo.list=tList;
						
							new Thread(new Runnable() {
								@Override
								public void run() {
									System.gc();
								}
							}).start();
						} else if (!contenxt.getUpfalg()) {
							listView.setMode(Mode.PULL_FROM_START);
							messageInfo.isHashValue = false;
							messageInfo.isEnd = true;
						}else if(contenxt.getUpfalg())
						{
							adapter.remove();
							adapter.notifyDataSetChanged();
							messageInfo.isHashValue = false;
							messageInfo.isEnd = true;
						}
					}
				}
				messageInfo.isSuccess = true;
			} else {//处理网络错误提示
				networkErrorShow(msg,contenxt.getContext());
				if (!contenxt.getUpfalg())
					contenxt.CurrPageMinus();
				if(msg.arg1==2&&contenxt.getUpfalg())
				{
						adapter.remove();
						adapter.notifyDataSetChanged();
						messageInfo.isHashValue = false;
						messageInfo.isEnd = true;
				}
			}
			listView.onRefreshComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		return messageInfo;
	}

	/**
	 * 请求结果提示
	 */
	public void requestResultPrompt(Message msg, Context context,String errorStr)
	{
		networkErrorShow(msg, context);
		if(!isNetworkError)
			errorShow(msg, context,errorStr);
	}
	
	/**
	 * 错误提示
	 * 
	 * @param msg
	 * @param context
	 */
	public void errorShow(Message msg, Context context) {
		errorShow(msg,context,null);
	}

	/**
	 * 错误提示
	 * 
	 * @param msg
	 * @param context
	 */
	@SuppressWarnings("unchecked")
	public void errorShow(Message msg, Context context,
			String errStr) {
		Map<String, String> map = (Map<String, String>) msg.obj;
		if (map != null) {
			// 手动设置错误提示语
			if (errStr != null && errStr.trim().length() > 0)
				ToastShowTool.myToastShort(context, errStr);
			else {// 服务器返回提示语
				String msgStr = map.get(JsonParse.MSG);
				if (msgStr == null || msgStr.length() == 0)
					ToastShowTool.showNetError(context);
				else {
					ToastShowTool.myToastShort(context, msgStr);
				}
			}
		}
	}

	/**
	 * 网络错误提示
	 * 
	 * @param msg
	 * @param context
	 */
	public void networkErrorShow(Message msg, Context context) {
			int errorStatus = msg.arg2;
			if (errorStatus == AppConstantError.WEBSEVICE_SOAP_FAULT||errorStatus == AppConstantError.WEBSEVICE_WEB_ERROR)
			// /请求失败
			{
				
				ToastShowTool.showNetError(context);
				isNetworkError = true;
			} else if (errorStatus == AppConstantError.NOT_NETWORK)
			// /无网络
			{
				ToastShowTool.myToastNotNetwork(context);
				isNetworkError = true;
				BaseApp.getInstance().setNetworkDisconnected(true);
			} else if (errorStatus == AppConstantError.WEB_TIMEOUT)
			// 连接超时
			{
				ToastShowTool.myToastTimeout(context);
				isNetworkError = true;
			}else if (errorStatus == AppConstantError.LOAD_NULL)
			// 加载为空
			{
				ToastShowTool.showEmptyPrompt(context);
				isNetworkError = false;
			}

			else
				isNetworkError=false;
	}
	/**
	 * 获取msg中obj里的某一个值
	 * @param key
	 */
	public String getDataParam(Message msg,String key)
	{
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) msg.obj;
		// holder.praiseBtn.setBackgroundResource(R.drawable.zambia1_3x_33);
		String value = null;
		try {
			JSONObject jsonObj = new JSONObject(
					map.get(JsonParse.CONTEXT));
			value = jsonObj.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
		
		}
		return value;
	}

	public class MessageInfo {
		private Boolean isSuccess = false;
		private Boolean isHashValue = false;
		private Boolean isEnd = false;
		private Map<String ,String> retMap =new  HashMap();
		@SuppressWarnings("rawtypes")
		private List list;
		@SuppressWarnings("rawtypes")
		public List getList() {
			return list;
		}
		@SuppressWarnings("rawtypes")
		public void setList(List list) {
			this.list = list;
		}
		public Boolean getIsSuccess() {
			return isSuccess;
		}

		public void setIsSuccess(Boolean isSuccess) {
			this.isSuccess = isSuccess;
		}

		public Boolean getIsHashValue() {
			return isHashValue;
		}

		public void setIsHashValue(Boolean isHashValue) {
			this.isHashValue = isHashValue;
		}

		public Boolean getIsEnd() {
			return isEnd;
		}

		public void setIsEnd(Boolean isEnd) {
			this.isEnd = isEnd;
		}
		public Map<String, String> getRetMap() {
			return retMap;
		}
		public void setRetMap(Map<String, String> retMap) {
			this.retMap = retMap;
		}
	}
}
