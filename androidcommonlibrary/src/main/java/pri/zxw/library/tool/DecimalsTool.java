package pri.zxw.library.tool;

/**
 * 小数处理工具
 * @author Zxw
 *
 */
public class DecimalsTool {
	
	/**
	 * 限制小数的长度
	 * @param str
	 * @param 整数部分长度
	 * @param 小数部分长度
	 * @return
	 */
	public static String decimalsLengthLimit(String str, int intLength, int fractionalPartLength) {
		if (str.length() > 2) {
			try {
				int decimalPointPosition = str.indexOf(".");
				if(str.length()==decimalPointPosition+1)
				{
					str=str.substring(0,decimalPointPosition-1);
				}
				if (decimalPointPosition != -1) {
					// 整数字符
					String intPartStr = null;
					if (decimalPointPosition >= intLength)
						intPartStr = str.substring(0, intLength);
					else {
						intPartStr = str.substring(0, decimalPointPosition);
					}
					// 小数部分字符
					String fractionalPartStr = str.substring(decimalPointPosition+1);
					int fractionalPartStrLength=fractionalPartStr.length();
					if(fractionalPartStrLength>fractionalPartLength)
					fractionalPartStr = fractionalPartStr.substring(
							0,fractionalPartLength);
					str=intPartStr+"."+fractionalPartStr;
				} else {
					if (str.length() > intLength)
						str = str.substring(0, intLength);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "0";
			}
		}

		return str;
	}
	
	
}
