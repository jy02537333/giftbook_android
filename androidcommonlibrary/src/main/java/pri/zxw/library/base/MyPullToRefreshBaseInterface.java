package pri.zxw.library.base;

import android.content.Context;

public interface MyPullToRefreshBaseInterface {
	/**
	 * 上拉加载更多网络数据失败，减去1
	 * @return
	 */
	public int CurrPageMinus();
	/**
	 * 开启上拉加载
	 */
	public void enableUpRefresh();
	/**是否下拉刷新*/
	public Boolean getUpfalg();
	/**是否下拉刷新*/
	public void setUpfalg(Boolean mUpfalg);
	public int getCur_page();
	public void setCur_page(int cur_page);
	public Context getContext();
	public String getStartDate();
	public void setStartDate(String startDate);
}
