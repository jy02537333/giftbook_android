package pri.zxw.library.tool;

import java.io.Serializable;
import java.util.regex.Pattern;

import android.text.TextUtils;


/**
 * 解析json数据，json数组，如:listMap,MapListMap,解析带分页功能的Json对象的数据、字符串工具类,
 * @版本 v1.0
 * @创建时间 2015-3-5 上午12:49:19
 * @QQ号码 444141300
 * @官网 http://www.yinlz.com
 */
public final class ToolsString implements Serializable {

	private static final long serialVersionUID = 1L;

	public static boolean isEmptyStr(String str)
	{
		if(str==null||str.trim().length()==0)
		{
			return true;
		}
		return false;
	}

    
    /**
     * 判断是否是空值或是否没有值,null不算，如果是空值则返回true否则返回false
     * @param str 欲要判断的值
     * @return  默认值
     * @返回值类型 boolean
     * @创建时间 2015-2-25 下午9:25:14 
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
     */
    public final static boolean isEmptyForStr(String str) {
        if (str == null || str.length() == 0 || "".equals(str) ||str.trim().equals("") || str.toLowerCase().equals("null") || TextUtils.isEmpty(str))
            return true;
        else
            return false;
    }
    
    /**
     * 判断Object是否是空值或是否没有值,null不算，如果是空值则返回true否则返回false
     * @param obj
     * @return
     * @返回值类型 boolean
     * @创建时间 2015年3月25日 12:07:19 
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
     */
    public final static boolean isEmptyForObj(final Object obj) {
    	if (obj == null){
            return true;
    	}else{
    		String value = obj.toString();
    		boolean b = isEmptyForStr(value);
    		if (b) {
				return true ;
			}
            return false;
    	}
    }
    
    /**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public final static boolean isEmpty(final String input) {
		if (input == null || "".equals(input))
			return true;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}


}