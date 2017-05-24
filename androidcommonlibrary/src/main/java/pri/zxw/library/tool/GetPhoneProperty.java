package pri.zxw.library.tool;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * @className 获取手机属性
 * @author 张相伟
 * @function 类功能
 * @createDate 2015-3-1
 * @version 1
 * @upadteMemter 2015-3-1
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class GetPhoneProperty {

	public PhoneProperty getProperty(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		PhoneProperty phoneProperty=new PhoneProperty();
		
		phoneProperty.setWidth( dm.widthPixels);
		phoneProperty.setHeight(dm.heightPixels);
		return phoneProperty;
	}

/**
 * @className 获取手机属性
 * @author 张相伟
 * @function 类功能
 * @createDate 2015-3-1
 * @version 1
 * @upadteMemter 2015-3-1
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
	public class PhoneProperty
	{
		private int width;
		private int height;
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		
	}
}
