package pri.zxw.library.base;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


import android.content.res.Resources;
import android.widget.ListView;


public class MyPullToRefreshBaseMethod {
	

	
	/**
	 * 修改pullListView的高和宽
	 * @param resources
	 * @param listView
	 * @param adapter
	 */
	public static void setPullListViewParam(Resources resources,PullToRefreshListView listView,
			MyBaseAdapter adapter)
	{
		/**标题栏高度*/
//		int headHeight=(int)resources.getDimension(dimen.title_bar_height);
//		int adapterTitleHeight=(int)resources.getDimension(dimen.adapter_title_height);
		if(listView==null||adapter==null )
		{
		}
		else {
	    	int width=adapter.adapterWidth;
		}
	}
	/**
	 * 修改pullListView的高和宽
	 * @param resources
	 * @param listView
	 * @param adapter
	 */
	public static void setPullListViewParam(Resources resources,ListView listView,
			MyBaseAdapter adapter)
	{
		/**标题栏高度*/
//		int headHeight=(int)resources.getDimension(dimen.title_bar_height);
//		int adapterTitleHeight=(int)resources.getDimension(dimen.adapter_title_height);
		if(listView==null||adapter==null )
		{
		}
		else {
		}
	}
	/**
	 * 返回 cur_page,修改pullToRefreshBase的上拉加载开关
	 * @param totale
	 * @param size
	 * @param pullToRefreshBase
	 * @return
	 */
	public static int closePullDownToRefresh(int cur_page,
			int totale,int size,PullToRefreshBase pullToRefreshBase)
	{
//		if(totale==0&&cur_page>1)
//			cur_page=1;
//		if(cur_page*size>=totale)
//			pullToRefreshBase.setMode(Mode.PULL_FROM_START);
//		else if(totale>0){
//			pullToRefreshBase.setMode(Mode.BOTH);	
//		}
		return cur_page;
	}
	
}
