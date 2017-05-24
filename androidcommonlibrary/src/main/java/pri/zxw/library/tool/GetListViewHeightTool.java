package pri.zxw.library.tool;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @className 计算listview的高度
 * @author 张相伟
 * @function 计算listview的高度
 * @createDate 2014-12-16
 * @version 1
 * @upadteMemter 2014-12-16
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class GetListViewHeightTool {
	/**
	 * 获取高度
	 * @param listView
	 * @return
	 */
	public static int GetListViewHeight(ListView listView) {
		  int totalHeight=0;
		try {
			ListAdapter listAdapter = listView.getAdapter();
			  if (listAdapter == null) {
			   return 0;
			  }
			  for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
			   View listItem = listAdapter.getView(i, null, listView);
			   listItem.measure(0, 0); // 计算子项View 的宽高
			   totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return totalHeight;
	}


}
