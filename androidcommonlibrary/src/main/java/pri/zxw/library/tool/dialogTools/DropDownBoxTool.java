package pri.zxw.library.tool.dialogTools;

import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.widget.TextView;

import pri.zxw.library.constant.HandlerStatus;


/**
 * 
 * @description 弹出框列表工具
 * @author 张相伟
 * @date 2016-10-18
 */
public class DropDownBoxTool {

	public final void showDialog(final String title,
								 final Map<String, String> map, final int type, Activity act,
								 final TextView view, final Callback callback) {
		showDialog(title,map, type,  act, view, callback, null);
	}
	/**
	 * 
	 * @param title
	 *            标题
	 *            集合
	 * @param type
	 *            key 值 作为判断 当一个页面有多个下拉需要处理时方便使用
	 */
	public final void showDialog(final String title,
								 final Map<String, String> map, final int type, Activity act,
								 final TextView view, final Callback callback, DialogSheetzAction.CanelCallback canelCallback) {
		DialogSheetzAction dialog1 = new DialogSheetzAction(act,canelCallback).builder();
		dialog1.setTitle(title);
		dialog1.setCancelable(false);
		dialog1.setCanceledOnTouchOutside(false);
		for (final String key : map.keySet()) {
			String split = "";
			final String name = map.get(key);
			if (name.length() > 15) { // 如果菜单名很长。
				split = name.substring(0, 15) + "…";
			} else {
				split = name;
			}
			dialog1.addSheetItem(split, DialogSheetzAction.SheetItemColor.Blue,
					new DialogSheetzAction.OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							switch (type) {
							case HandlerStatus.KEY1:
								if(view!=null) {
									view.setText(name);
									view.setTag(key);
								}
									if (callback != null)
										callback.complate(key, name);
								break;
							default:
								break;
							}
						}
					});
		}
		dialog1.show();
	}

	public interface Callback {
		public void complate(String key, String value);
	}
}
