package pri.zxw.library.base;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import pri.zxw.library.entity.AbstractStartDateEntity;
import pri.zxw.library.refresh_tool.SwipeRecyclerView;
import pri.zxw.library.tool.DateCommon;
import pri.zxw.library.tool.MessageHandlerTool;


@SuppressLint("NewApi")
public abstract class MyPullToRefreshBaseActivity<T  extends AbstractStartDateEntity>
extends MyBaseActivity implements MyPullToRefreshBaseInterface {
	public static final int GET_DATA_CODE=9011;
	public static final int GET_ADD_CODE=9022;
	/**添加子级**/
	public static final int ADD_CHILD_CODE=9055;
	public static final int LOAD_CODE=9003;
	public static final int DEL_CODE=9004;
	public static final int EDIT_CODE=9006;
	protected   int rows = 10; // 页面大小
	protected String startDate = DateCommon.getCurrentDateStr(); // 开始查找的时间节
	/**是否下拉刷新*/
	protected Boolean mUpfalg = true;
	public boolean isSub=false;
	public int cur_page = 1; // 当前页的索引
	protected SwipeRecyclerView mPullToRefreshBase;
	protected MyBaseAdapter mAdapter;
	private MessageHandlerTool.MessageInfo mMessageInfo;

	public Boolean getUpfalg() {
		return mUpfalg;
	}
	public void setUpfalg(Boolean mUpfalg) {
		this.mUpfalg = mUpfalg;
	}
	public int getCur_page() {
		return cur_page;
	}
	public void setCur_page(int cur_page) {
		this.cur_page = cur_page;
	}
	public void setSwipeRecyclerView(SwipeRecyclerView swipeRecyclerView)
	{
		this.mPullToRefreshBase=swipeRecyclerView;
	}
	public SwipeRecyclerView getSwipeRecyclerView()
	{
		return mPullToRefreshBase;
	}
	public void setAdapter(MyBaseAdapter adapter)
	{
		this.mAdapter=adapter;
	}
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}

	public MyPullToRefreshBaseActivity()
	{}


	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * 需要将三个参数传入，交给MyPullToRefreshBaseFragment
	 * @param pullToRefreshBase
	 * @param wgdTool
	 */
//	protected  abstract void initTool(PullToRefreshBase pullToRefreshBase,
//			WebGetDataTool wgdTool);
	/**
	 * 获取数据的操作
	 */
	public abstract void getWebData();
	public void listLoad(final Handler handler)
	{
		mPullToRefreshBase.setRefreshing(true);
	}

	/**
	 * 绑定事件
	 */
	protected void initListener(SwipeRecyclerView pullToRefreshBase,MyBaseAdapter myBaseAdapter)
	{
		MyPullToRefreshBaseMethod.initListener(this,pullToRefreshBase,myBaseAdapter);
	}
	/**
	 * 绑定事件
	 */
	protected void initListener(SwipeRecyclerView pullToRefreshBase,MyBaseAdapter myBaseAdapter, SwipeRecyclerView.Mode mode)
	{
		MyPullToRefreshBaseMethod.initListener(this,pullToRefreshBase,myBaseAdapter,mode);
	}
	/**
	 * 绑定事件
	 */
	protected void initListener(SwipeRecyclerView pullToRefreshBase, MyBaseAdapter myBaseAdapter, LinearLayoutManager linearLayoutManager, SwipeRecyclerView.Mode mode)
	{
		MyPullToRefreshBaseMethod.initListener(this,pullToRefreshBase,myBaseAdapter,linearLayoutManager,mode);
	}
	/**
	 * 修改pullListView的高和宽
	 * @param listView
	 * @param adapter
	 */
	protected void setPullListViewParam(SwipeRecyclerView listView,MyBaseAdapter adapter)
	{
		mPullToRefreshBase=listView;
		MyPullToRefreshBaseMethod.setPullListViewParam(getResources(), listView, adapter);
	}

	/**
	 * 属性初始值
	 */
	public void  initParameter()
	{
		cur_page = 1;
		mUpfalg=true;
		startDate=DateCommon.getCurrentDateStr();
//		mAdapter.removeData();
	}
	/**
	 * @Title: onRefrsh
	 * @Description: 下拉刷新的操作
	 * @return void 返回类型
	 * @throws
	 */
	public void onPullDownToRefresh() {
		initParameter();
		getWebData();
	}
	/**
	 * @Title: onLoadMore
	 * @Description: 上拉加载的操作
	 * @return void 返回类型
	 * @throws
	 */
	public void onPullUpToRefresh() {
		mUpfalg = false;
		cur_page ++;
		getWebData();
	}
	/**关闭下拉刷新*/
	@Override
	public void closePullDownToRefresh()
	{
		mPullToRefreshBase.setRefreshEnable(false);
	}
	/**关闭上拉加载*/
	@Override
	public void closePullUpToRefresh() {
		mPullToRefreshBase.setLoadMoreEnable(false);
	}
	@Override
	public void enableUpRefresh()
	{
		//禁止加载更多
		mPullToRefreshBase.setLoadMoreEnable(true);
	}
	@Override
	public int CurrPageMinus() {
		cur_page--;
		return cur_page;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public void setRows(int rows) {
		this.rows=rows;
	}

	@Override
	public void onComplete() {
		mPullToRefreshBase.complete();
	}

	@Override
	public void onStopLoadingMore() {
		mPullToRefreshBase.stopLoadingMore();
	}
	@Override
	public void setFootText(CharSequence footStr){
		mPullToRefreshBase.onNoMore(footStr);
	}
	@Override
	public void onNetChange() {
		mPullToRefreshBase.onNetChange(true);
	}
	//设置错误信息
	@Override
	public void onError(CharSequence msgStr) {
		mPullToRefreshBase.onError(msgStr);
	}

	@Override
	public MessageHandlerTool.MessageInfo getMessageInfo() {
			return mMessageInfo;
	}

	@Override
	public void setMessageInfo(MessageHandlerTool.MessageInfo info) {
		mMessageInfo=info;
	}


//	protected  int rows = 10; // 页面大小
//	protected String startDate = DateCommon.getCurrentDateStr(); // 开始查找的时间节
//	protected Boolean mUpfalg = true;
//	public int cur_page = 1; // 当前页的索引
//	protected SwipeRecyclerView mPullToRefreshBase;
//	protected MyBaseAdapter mAdapter;
//
//	@Override
//	public Boolean getUpfalg() {
//		return mUpfalg;
//	}
//	@Override
//	public void setUpfalg(Boolean mUpfalg) {
//		this.mUpfalg = mUpfalg;
//	}
//	@Override
//	public int getCur_page() {
//		return cur_page;
//	}
//	@Override
//	public void setCur_page(int cur_page) {
//		this.cur_page = cur_page;
//	}
//	@Override
//	public String getStartDate() {
//		return startDate;
//	}
//	@Override
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}
//	@Override
//	public Context getContext() {
//		// TODO Auto-generated method stub
//		return this;
//	}
//	public void setSwipeRecyclerView(SwipeRecyclerView swipeRecyclerView)
//	{
//		this.mPullToRefreshBase=swipeRecyclerView;
//	}
//	public SwipeRecyclerView getSwipeRecyclerView()
//	{
//		return mPullToRefreshBase;
//	}
//	public void setAdapter(MyBaseAdapter adapter)
//	{
//		this.mAdapter=adapter;
//	}
//	/**
//	 * 获取数据的操作
//	 */
//	public abstract void getWebData();
//
//	/**
//	 * 绑定事件
//	 */
//	protected void initListener(SwipeRecyclerView pullToRefreshBase,MyBaseAdapter myBaseAdapter)
//	{
//		MyPullToRefreshBaseMethod.initListener(this,pullToRefreshBase,myBaseAdapter);
//	}
//	/**
//	 * 绑定事件
//	 */
//	protected void initListener(SwipeRecyclerView pullToRefreshBase,MyBaseAdapter myBaseAdapter, SwipeRecyclerView.Mode mode)
//	{
//		MyPullToRefreshBaseMethod.initListener(this,pullToRefreshBase,myBaseAdapter,mode);
//	}
//	/**
//	 * 绑定事件
//	 */
//	protected void initListener(SwipeRecyclerView pullToRefreshBase, MyBaseAdapter myBaseAdapter, LinearLayoutManager linearLayoutManager, SwipeRecyclerView.Mode mode)
//	{
//		MyPullToRefreshBaseMethod.initListener(this,pullToRefreshBase,myBaseAdapter,linearLayoutManager,mode);
//	}
//	public void listLoad(final Handler handler)
//	{
//		mPullToRefreshBase.setRefreshing(true);
//	}
//
//	/**
//	 * 修改pullListView的高和宽
//	 * @param listView
//	 * @param adapter
//	 */
//	public void setPullListViewParam(SwipeRecyclerView listView, MyBaseAdapter adapter)
//	{
//		mPullToRefreshBase=listView;
////		MyPullToRefreshBaseMethod.setPullListViewParam(getResources(), listView, adapter);
//	}
//	/**关闭下拉刷新*/
//	@Override
//	public void closePullDownToRefresh()
//	{
//		mPullToRefreshBase.setRefreshEnable(false);
//	}
//	/**关闭上拉加载*/
//	@Override
//	public void closePullUpToRefresh() {
//		mPullToRefreshBase.setLoadMoreEnable(false);
//	}
//	@Override
//	public void enableUpRefresh()
//	{
//		//禁止加载更多
//		mPullToRefreshBase.setLoadMoreEnable(true);
//	}
//	/**
//	 * 属性初始值
//	 */
//	public void  initParameter()
//	{
//		cur_page = 1;
//		mUpfalg=true;
//		startDate= DateCommon.getCurrentDateStr();
//	}
//	/**
//	 * @Title: onRefrsh
//	 * @Description: 下拉刷新的操作
//	 * @return void 返回类型
//	 * @throws
//	 */
//	public void onPullDownToRefresh() {
//		initParameter();
//		getWebData();
//	}
//	@Override
//	public void onComplete() {
//		mPullToRefreshBase.complete();
//	}
//
//	@Override
//	public void onStopLoadingMore() {
//		mPullToRefreshBase.stopLoadingMore();
//	}
//	/**
//	 * @Title: onLoadMore
//	 * @Description: 上拉加载的操作
//	 * @return void 返回类型
//	 * @throws
//	 */
//	public void onPullUpToRefresh() {
//		mUpfalg = false;
//		cur_page  ++;
//		getWebData();
//	}
//	@Override
//	public int CurrPageMinus() {
//		cur_page--;
//		return cur_page;
//	}
//	@Override
//	public int getRows() {
//		return rows;
//	}
//
//	@Override
//	public void setRows(int rows) {
//		this.rows=rows;
//	}
//	@Override
//	public void setFootText(CharSequence footStr){
//		mPullToRefreshBase.onNoMore(footStr);
//	}
//	@Override
//	public void onNetChange() {
//		mPullToRefreshBase.onNetChange(true);
//	}
//	//设置错误信息
//	@Override
//	public void onError(CharSequence msgStr) {
//		mPullToRefreshBase.onError(msgStr);
//	}

}
