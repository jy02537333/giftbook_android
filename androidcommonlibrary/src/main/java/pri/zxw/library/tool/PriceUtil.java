package pri.zxw.library.tool;

import java.text.NumberFormat;

/**
 * @author 龙流平(LongLiuPing)
 * @version 创建时间：2015年4月10日 上午11:27:30
 * package pri.zxw.library.tool;
 */
public class PriceUtil {
	public static String getDoublePrice(Object object)
	{
		if(object==null || object.toString().trim() == null || object.toString().length()<=0)
		{
			return "";
		}
//		NumberFormat ddf1=NumberFormat.getNumberInstance() ; 
//		ddf1.setMaximumFractionDigits(2); 
//		return ddf1.format(object) ; 
		return String.format("%.2f", Double.valueOf(object.toString()));
	}
}
