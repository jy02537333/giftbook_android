package pri.zxw.library.tool;

import android.widget.EditText;

/**
 * @author 龙流平(LongLiuPing)
 * @version 创建时间：2015年2月13日 下午3:07:16
 * package com.yunzhi.e_commerce.util;
 */
public class StringUtil {
	public static String nullTrim(String content)
	{
		if(content==null || content.endsWith("null"))
		{
			return "";
		}else{
			return content;
		}
	}
	/**
	 * 判断EditText 内容是否空
	* @Description: TODO
	* @author LongLiuPing
	* @param  
	* @return boolean ture 内容空,true 不为空
	* @throws
	 */
	public static boolean isNull(EditText editText)
	{
		if(editText==null)
		{
			return true;
		}
		if(editText.getText().toString() == null)
		{
			return true;
		}
		if(editText.getText().toString().trim().length()<=0)
		{
			return true;
		}
		return false;
	}
	public static boolean isNull(String editText)
	{
		if(editText==null)
		{
			return true;
		}
		if(editText.toString() == null)
		{
			return true;
		}
		if(editText.toString().trim().length()<=0)
		{
			return true;
		}
		return false;
	}
	public static boolean isNotEmpty(String str,boolean is)
	{
		if(str!=null&&str.trim().length()>0)
		{
			return true;
		}
		else
			return  false;
	}
}
