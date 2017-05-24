package pri.zxw.library.base;



import com.handmark.pulltorefresh.library.DateCommon;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import pri.zxw.library.entity.AbstractStartDateEntity;
import pri.zxw.library.tool.WebGetDataTool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;



@SuppressLint("NewApi")
public abstract class MyPullToRefreshBaseFragment<T extends AbstractStartDateEntity>
extends BaseFragment implements PullToRefreshBase.OnRefreshListener2
,MyPullToRefreshBaseInterface{
	public static final int GET_DATA_CODE=1111;
	public static final int GET_ADD_CODE=2222;
	/**添加子级**/
	public static final int ADD_CHILD_CODE=9494;
	public static final int LOAD_CODE=3333;
	public static final int DEL_CODE=4444;
	protected static final int PAGE_SIZE = 10; // 页面大小
	protected String startDate = DateCommon.getCurrentDateStr(); // 开始查找的时间节
	/**是否下拉刷新*/
	protected Boolean mUpfalg = true; 
	public int cur_page = 1; // 当前页的索引
	protected PullToRefreshBase mPullToRefreshBase;   
	protected MyBaseAdapter mAdapter;

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
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return super.getActivity();
	}

	public MyPullToRefreshBaseFragment()
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
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					Message msg=new Message();
					msg.what=LOAD_CODE;
					handler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	/**
	 * 绑定事件
	 */
	protected void initListener(PullToRefreshBase pullToRefreshBase,MyBaseAdapter myBaseAdapter)
	{
		mPullToRefreshBase=pullToRefreshBase;
		mPullToRefreshBase.setMode(Mode.BOTH);
		mPullToRefreshBase.setOnRefreshListener(this);
		mAdapter=myBaseAdapter;
	}
	/**
	 * 绑定事件
	 */
	protected void initListener(PullToRefreshBase pullToRefreshBase,MyBaseAdapter myBaseAdapter,Mode mode)
	{
		mPullToRefreshBase=pullToRefreshBase;
		mPullToRefreshBase.setMode(mode);
		mPullToRefreshBase.setOnRefreshListener(this);
		mAdapter=myBaseAdapter;
	}
	/**
	 * 修改pullListView的高和宽
	 * @param listView
	 * @param adapter
	 */
	protected void setPullListViewParam(PullToRefreshListView listView,MyBaseAdapter adapter)
	{
		mPullToRefreshBase=listView; 
		MyPullToRefreshBaseMethod.setPullListViewParam(getResources(), listView, adapter);
	}
	/**关闭下拉刷新*/
	protected void closePullDownToRefresh(int totale,int size)
	{
	 cur_page= MyPullToRefreshBaseMethod.closePullDownToRefresh(cur_page, totale, size, mPullToRefreshBase);
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
	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		initParameter();
		getWebData();
	}
//	protected void isRefreshOrLoad() {
//		if(mUpfalg)
//			mAdapter.remove();
//	}
	/**
	 * @Title: onLoadMore
	 * @Description: 上拉加载的操作
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		mUpfalg = false;
		cur_page ++;
		getWebData();
	}
	@Override
	public void enableUpRefresh()
	{
		mPullToRefreshBase.setMode(Mode.BOTH);
	}
	
	@Override
	public int CurrPageMinus() {
		cur_page--;
		return cur_page;
	}
}
