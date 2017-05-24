package pri.zxw.library.tool;

import android.app.Activity;
import android.content.Intent;


/**
 * @className 处理AsyncTask返回数据
 * @author 张相伟
 * @function 类功能
 * @createDate 2014-12-30
 * @version 1
 * @upadteMemter 2014-12-30
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class WebAsyncTaskTool {  
	private WebGetDataTool mWebGetDataTool;
	private Activity mAct;
	public WebAsyncTaskTool(Activity act)
	{
		mAct=act; 
		mWebGetDataTool=new WebGetDataTool(mAct);
	}
	
	
	/**
	 * 处理添加，修改，删除返回�?
	 * @param msg
	 */
	public Integer taskAddResult(Integer errorCode,Object resultObj) {
		return taskAddResult(errorCode, resultObj, null,WebGetDataTool.BLANK_SPACE_OPERATE_CODE, null, true, 0);
	}
	/**
	 * 处理添加，修改，删除返回�?
	 * @param msg
	 * @param operateCode 由外部定义的操作编码所以需要传入 
	 */
	public Integer taskAddResult(Integer errorCode,Object resultObj,Integer operateCode) {
		return taskAddResult(errorCode, resultObj, null,operateCode, null, true, 0);
	}
	/**
	 * 处理添加，修改，删除返回�?
	 * @param msg
	 * @param operateCode 由外部定义的操作编码所以需要传入 
	 * @param msgContent 提示内容
	 */
	public Integer taskAddResult(Integer errorCode,Object resultObj,int operateCode,String msgContent) {
		return taskAddResult(errorCode, resultObj, null,operateCode, msgContent, true, 0);
	}
	/**
	 * 处理添加返回�?
	 * 
	 * @param msg
	 * @param Intent 需要跳转的页面
	 */
	public Integer taskAddResult(Integer errorCode,Object resultObj, Intent responseIntent) {
		return taskAddResult(errorCode, resultObj, responseIntent,WebGetDataTool.BLANK_SPACE_OPERATE_CODE, null, true, 0);
	}
	/**
	 * 处理添加返回�?
	 * 
	 * @param msg
	 * @param Intent 需要跳转的页面
	 * @param isShowMsg
	 *            是否显示提示�?
	 */
	public Integer taskAddResult(Integer errorCode,Object resultObj, Intent responseIntent,
			Boolean isShowMsg) {
		return taskAddResult(errorCode, resultObj, responseIntent,WebGetDataTool.BLANK_SPACE_OPERATE_CODE, 
				null, isShowMsg, 0);
	}
	/**
	 * 处理添加返回�?
	 * 
	 * @param msg
	 * @param Intent 需要跳转的页面
	 * @param magContent
	 *            提示内容
	 */
	public Integer taskAddResult(Integer errorCode,Object resultObj, Intent responseIntent,
			String magContent) {
		return taskAddResult(errorCode, resultObj, responseIntent,WebGetDataTool.BLANK_SPACE_OPERATE_CODE,
				magContent, true, 0);
	}
	/**
	 * 处理添加返回�?
	 * 
	 * @param msg
	 * @param Intent 需要跳转的页面
	 * @param magContent
	 *            提示内容
	 * @param isShowMsg
	 *            是否显示提示�?
	 * @param okCode
	 *            返回成功值编�?
	 */
	public Integer taskAddResult(Integer errorCode,Object resultObj, Intent responseIntent,
			Integer okCode) {
		return taskAddResult(errorCode, resultObj, responseIntent,WebGetDataTool.BLANK_SPACE_OPERATE_CODE,
				null, true, okCode);
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
	public Integer taskAddResult(Integer errorCode,Object resultObj, Intent responseIntent,Integer operateCode,
			String magContent, Boolean isShowMsg, Integer okCode) {
		Integer ret = 0;
			if(errorCode==null)
				errorCode=0;
			if(!mWebGetDataTool.errorPrompt(errorCode))
			 {
				Integer webCode =null;
				try {
					if(resultObj!=null)
						webCode=Integer.parseInt( resultObj.toString() );
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				if (!mWebGetDataTool.PKErrorPrompt(webCode)) 
					if (webCode == 0) {
						ToastShowTool.showNetFail(mAct);
					} else if (webCode == 1 || (okCode > 0 && webCode > okCode)) {
						if (isShowMsg)
						{
							if (magContent == null) {
								ToastShowTool.showNetSuccess(mAct);
							} else
								ToastShowTool.myToastShort(mAct, magContent);
						}
						if (responseIntent != null) {
							mAct.startActivity(responseIntent);
							mAct.finish();
						}
						return 1;
					}
			}
		return ret;
	}
	
}
