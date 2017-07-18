package pri.zxw.library.base;

import android.content.Context;

import pri.zxw.library.refresh_tool.SwipeRecyclerView;
import pri.zxw.library.tool.MessageHandlerTool;

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
	/**关闭下拉刷新*/
	public void closePullDownToRefresh();
	/**关闭上拉加载*/
	public void closePullUpToRefresh();
	public void onComplete();
	public void onPullDownToRefresh();
	public void onPullUpToRefresh();
	public  SwipeRecyclerView getSwipeRecyclerView();
	public void setSwipeRecyclerView( SwipeRecyclerView swipeRecyclerView);
	public  void setAdapter(MyBaseAdapter myBaseAdapter);
	public int getRows();
	public void setRows(int rows) ;

	public void setFootText(CharSequence errorStr) ;
	public void onStopLoadingMore();
	/**无网络错误*/
	public void onNetChange() ;
	//设置错误信息
	public void onError(CharSequence msgStr);
	//获取数据情况
	public MessageHandlerTool.MessageInfo getMessageInfo();
	//设置数据情况
	public void setMessageInfo(MessageHandlerTool.MessageInfo info);
}
