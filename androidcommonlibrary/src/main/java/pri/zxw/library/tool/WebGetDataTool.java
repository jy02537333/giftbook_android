package pri.zxw.library.tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;

import java.util.List;

import pri.zxw.library.entity.AbstractStartDateEntity;
    

  
public class WebGetDataTool {
	/**
	 * 图片上传操作
	 */
	public static final int PHOTO_UPLOAD = 4443;
	/**
	 * 获取最新数据操�?
	 */
	public static final int GET_NEWEST_INFO = 3334;
	public static final int ADD = 1111;
	public static final int DEL = 2222;
	public static final int UPDATE = 5555;
	public static final int INT_OPERATE=105;
	/**
	 * 空白的操作编码，使用这个编码说明不希望和这个编码匹配上
	 */
	public static final int BLANK_SPACE_OPERATE_CODE=98989898;
	/**
	 * 获取实体
	 */
	public static final int Get_MODE = 6666;
	/**
	 * 获取列表操作
	 */
	public static final int Get_LIST = 3333;

	/**
	 * 访问网络返回的编�?
	 */
	public Integer webResultCode;
	/**
	 * 访问网络返回的错误码
	 */
	public Integer webResultError;

	
	/**
	 * 判断是否刷新
	 */
	public boolean mUpflag = false;
	/**
	 * 当前页码
	 */
	public int cur_page=1;
	/**
	 * 一次获取几条数�?
	 */
	public  int pageSize = 10;
	/**
	 * 记录开始查询时�?
	 */
	public String startDate = null;

	/**
	 * 上传图片路径
	 */
	private String xmlImgPath = null;
	
	/**
	 * 是否显示最后一页提示内�?
	 */
	public boolean isShowLast=true;
	/**
	 * 最后一页提示的显示内容
	 */
	public  String lastContent=null;
	/**
	 * 没有查询到数据的提示内容
	 */
	public String  searchSpaceDataContent=null;
	
	private Activity mActivity;
	public <T> WebGetDataTool(Activity act) {
		mActivity = act;
	}
	

	

	
	
	/**
	 * 网络操作获取list的返回值，不包含count
	 * @param msg
	 * @param operateCode 自定义操作码
	 * @return
	 */
	public List<AbstractStartDateEntity> handleListNotCountResult(Message msg,Integer operateCode)
	{
		if (msg.what==Get_LIST||msg.what ==operateCode) {
			if(!errorPrompt((Integer)msg.arg2))
			if (msg.obj != null) {
				try {
					return (List<AbstractStartDateEntity>)msg.obj;
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

//	public Integer handleListResultInsertMode(Message msg, BaseListView xListView,
//			MyColBaseAdapter<T> myAapter,int what,int insertRow)
//	{
//		return handleListResult(msg,xListView,myAapter,what);
//	}
	/**
	 * 处理添加，修改，删除返回�?
	 * @param msg
	 */
	public Integer handleAddResult(Message msg) {
		return handleAddResult(msg, null,BLANK_SPACE_OPERATE_CODE, null, true, 0);
	}
	/**
	 * 网络返回是int的处理
	 * @param msg
	 * @param isShowPrompt 是否需要提示
	 */
	public Integer handleIntResult(Message msg,boolean isShowPrompt) {
		return handleAddResult(msg, null,isShowPrompt);
	}
	/**
	 * 网络返回是int的处理
	 * @param msg
	 * @param isShowPrompt 是否需要提示
	 */
	public Integer handleIntResult(Message msg,int operateCode,boolean isShowPrompt) {
		return handleAddResult(msg, null,operateCode,null,isShowPrompt,0);
	}
	/**
	 * 处理添加，修改，删除返回�?
	 * @param msg
	 * @param operateCode 由外部定义的操作编码所以需要传入 
	 */
	public Integer handleAddResult(Message msg,int operateCode) {
		return handleAddResult(msg, null,operateCode, null, true, 0);
	}
	/**
	 * 处理添加，修改，删除返回�?
	 * @param msg
	 * @param operateCode 由外部定义的操作编码所以需要传入 
	 * @param msgContent 提示内容
	 */
	public Integer handleAddResult(Message msg,int operateCode,String msgContent) {
		return handleAddResult(msg, null,operateCode, msgContent, true, 0);
	}
	/**
	 * 处理添加返回�?
	 * 
	 * @param msg
	 * @param responseIntent 需要跳转的页面
	 */
	public Integer handleAddResult(Message msg, Intent responseIntent) {
		return handleAddResult(msg, responseIntent,BLANK_SPACE_OPERATE_CODE, null, true, 0);
	}
	/**
	 * 处理添加返回�?
	 * 
	 * @param msg
	 * @param responseIntent 需要跳转的页面
	 * @param isShowMsg
	 *            是否显示提示�?
	 */
	public Integer handleAddResult(Message msg, Intent responseIntent,
			Boolean isShowMsg) {
		return handleAddResult(msg, responseIntent,BLANK_SPACE_OPERATE_CODE, null, isShowMsg, 0);
	}
	/**
	 * 处理添加返回�?
	 * 
	 * @param msg
	 * @param responseIntent 需要跳转的页面
	 * @param magContent
	 *            提示内容
	 */
	public Integer handleAddResult(Message msg, Intent responseIntent,
			String magContent) {
		return handleAddResult(msg, responseIntent,BLANK_SPACE_OPERATE_CODE, magContent, true, 0);
	}
	/**
	 * 处理添加返回�?
	 * 
	 * @param msg
	 * @param responseIntent 需要跳转的页面
	 * @param okCode
	 *            返回成功值编�?
	 */
	public Integer handleAddResult(Message msg, Intent responseIntent,
			Integer okCode) {
		return handleAddResult(msg, responseIntent,BLANK_SPACE_OPERATE_CODE, null, true, okCode);
	}

	/**
	 * 
	 * @param msg
	 * @param responseIntent
	 *            成功跳转的页面，和发送�?
	 * @param operateCode 操作类型的编码
	 * @param magContent
	 *            提示内容
	 * @param isShowMsg
	 *            是否显示提示�?
	 * @param okCode
	 *            返回成功值编�?
	 * @return
	 */
	public Integer handleAddResult(Message msg, Intent responseIntent,Integer operateCode,
			String magContent, Boolean isShowMsg, Integer okCode) {
		Integer ret = 0;
		if (msg.what == ADD || msg.what == DEL || msg.what == UPDATE||msg.what ==operateCode) {
			WebAsyncTaskTool webAsyncTaskTool=new WebAsyncTaskTool(mActivity);
			ret=webAsyncTaskTool.taskAddResult(msg.arg2, msg.obj,
					responseIntent, operateCode, magContent, isShowMsg, okCode);
		}
		return ret;
	}
	/**
	 * 主键重复错误
	 * @param errorCode
	 * @return
	 */
	public boolean PKErrorPrompt(Integer errorCode)
	{
		if (errorCode.equals(  AppConstantError.REGIST_PK_ERROR  )) {
			ToastShowTool.showPKError(mActivity);
			return true;
		} 
		return false;
	}

	/**
	 * 处理上传图片返回�?
	 * 
	 * @param msg
	 * @return 上传成功的图片地址
	 */
	public String handlePhotoResult(Message msg) {
		xmlImgPath = null;
		if (msg.what == this.PHOTO_UPLOAD) {
			if(!errorPrompt( msg.arg2) )
			if (msg != null&&msg.arg2==0) {
				xmlImgPath = msg.obj.toString();
			}
		}
		return xmlImgPath;
	}

	public Object handleGetModeResult(Message msg,String showContent,String failShowContent) {
		if (msg.what == Get_MODE ) {
			errorPrompt( msg.arg2);
			if (msg != null&& null!=msg.obj&&msg.arg2==0 ) {
				if(showContent!=null)
					ToastShowTool.myToastShort(mActivity, showContent);
				return  msg.obj;
			}else {
				if(failShowContent!=null)
				ToastShowTool.myToastShort(mActivity, failShowContent);
			}
		}
		return null;
	}
	public Object handleGetModeResult(Message msg) {
		return handleGetModeResult(msg,null,null);
	}
	
	

	/**
	 * 错误提示
	 * @param errorCode 错误码
	 * @return 是否错误 true是错误，false是没有错误
	 */
	public boolean errorPrompt(Integer errorCode)
	{
		if (errorCode == AppConstantError.WEB_TIMEOUT) {
			ToastShowTool.myToastTimeout(mActivity);
			return true;
		}else if (errorCode == AppConstantError.NOT_NETWORK) {
			ToastShowTool.myToastNotNetwork(mActivity);
			return true;
		} 
		else if (PKErrorPrompt( errorCode)) {
			return true;
		} 
		else if (errorCode > 0) {
			ToastShowTool.showNetError(mActivity);
			return true;
		}
		return false;
	}
	


	
	/**
	 * I,D,U 操作，结果处理，错误提示
	 * @param errorCode 错误码
	 */
	public void errorIDUPrompt(Integer errorCode)
	{
		if (errorCode == AppConstantError.WEB_TIMEOUT) {
			ToastShowTool.myToastTimeout(mActivity);
		} else if (errorCode == AppConstantError.NOT_NETWORK) {
			ToastShowTool.myToastNotNetwork(mActivity);
		} else if (errorCode > 0) {
			ToastShowTool.showNetError(mActivity);
		}
	}
	

	



	
}
