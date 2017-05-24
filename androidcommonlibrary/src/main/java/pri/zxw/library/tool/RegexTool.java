package pri.zxw.library.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @className 正则表达式工具
 * @author 张相伟
 * @function 类功能
 * @createDate 2014-12-17
 * @version 1
 * @upadteMemter 2014-12-17
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class RegexTool {

	/**
	 * 手机格式，不完善
	 */
	public static final String phoneFormatStr="1[0-9]{10}";
	/**
	 * 验证密码格式
	 */
	public static final String pwdFormatStr="\\w{6,20}";
	/**
	 * 验证是否手机格式
	 * @param str
	 * @return
	 */
	public static boolean matchPhone(String str)
	{
		return match(str,phoneFormatStr);
	}
	/**
	 * 验证格式是否正确
	 * @param str
	 * @return
	 */
	public static boolean match(String str,String formatStr) {
			Pattern pattern=Pattern.compile(formatStr);
	        Matcher match=pattern.matcher(str);
	        if(match.matches()==false)
	        {
	             return false;
	        }
	        else
	        {
	             return true;
	        }
		}
	/**
	 * 验证密码格式
	 * @param str
	 * @param formatStr
	 * @return
	 */
	public static boolean matchPwd(String str) {
		return match(str, pwdFormatStr);
	}
}
