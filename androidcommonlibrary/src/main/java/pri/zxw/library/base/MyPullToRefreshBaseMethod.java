package pri.zxw.library.base;



import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import pri.zxw.library.R;
import pri.zxw.library.refresh_tool.SwipeRecyclerView;


public class MyPullToRefreshBaseMethod {


	/**
	 * 绑定事件
	 */
	public  static void initListener(MyPullToRefreshBaseInterface base,SwipeRecyclerView swipeRecyclerView,MyBaseAdapter myBaseAdapter)
	{
		initListener( base,swipeRecyclerView , myBaseAdapter,null, SwipeRecyclerView.Mode.NORMAL);

	}
	/**
	 * 绑定事件
	 */
	public  static void initListener(MyPullToRefreshBaseInterface base,SwipeRecyclerView swipeRecyclerView,MyBaseAdapter myBaseAdapter,
									  SwipeRecyclerView.Mode mode)
	{
		initListener( base,swipeRecyclerView , myBaseAdapter,null, mode);
	}
	/**
	 * 绑定事件
	 */
	public  static void initListener(MyPullToRefreshBaseInterface base,SwipeRecyclerView swipeRecyclerView,MyBaseAdapter myBaseAdapter,
									 LinearLayoutManager linearLayoutManager, SwipeRecyclerView.Mode mode)
	{
		base.setSwipeRecyclerView(swipeRecyclerView);
		base.setAdapter(myBaseAdapter);
		//set color
		MyPullToRefreshBaseMethod.setRefreshColor(base);
		if(linearLayoutManager==null)
			base.getSwipeRecyclerView().getRecyclerView().setLayoutManager(new LinearLayoutManager(base.getContext()));
		else
			base.getSwipeRecyclerView().getRecyclerView().setLayoutManager(linearLayoutManager);
		MyPullToRefreshBaseMethod.onLoad(base);
		if(mode==SwipeRecyclerView.Mode.CLOSE_END)
			base.closePullUpToRefresh();
		else if(mode==SwipeRecyclerView.Mode.CLOSE_START)
			base.closePullDownToRefresh();
		else if(mode==SwipeRecyclerView.Mode.NOT_REFRESH)
		{
			base.closePullUpToRefresh();
			base.closePullDownToRefresh();
		}

	}


	/**
	 * 修改pullListView的高和宽
	 * @param resources
	 * @param listView
	 * @param adapter
	 */
	public static void setPullListViewParam(Resources resources,SwipeRecyclerView listView,
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
//	public static int closePullDownToRefresh(int cur_page,
//			int totale,int size,SwipeRecyclerView pullToRefreshBase)
//	{
//        pullToRefreshBase.setRefreshEnable(false);
//		return cur_page;
//	}

    /**
     * 去除底部分割线
     */
    public static void delFootLine(SwipeRecyclerView pullToRefreshBase) {
        //设置去除footerView 的分割线
        pullToRefreshBase.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(0xFFEECCCC);

                Rect rect = new Rect();
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                final int childCount = parent.getChildCount() - 1;
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);

                    //获得child的布局信息
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int top = child.getBottom() + params.bottomMargin;
                    final int itemDividerHeight = 1;//px
                    rect.set(left + 50, top, right - 50, top + itemDividerHeight);
                    c.drawRect(rect, paint);
                }
            }
        });
    }
	public  static void onLoad(final MyPullToRefreshBaseInterface base)
	{
		base.getSwipeRecyclerView().setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
			@Override
			public void onRefresh() {

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						base.onPullDownToRefresh();
					}
				}, 1000);

			}

			@Override
			public void onLoadMore() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						base.onPullUpToRefresh();
					}
				}, 1000);
			}
		});
	}
	public static void setRefreshColor(MyPullToRefreshBaseInterface base){
		base.getSwipeRecyclerView().getSwipeRefreshLayout().setColorSchemeResources(R.color.refresh_colorPrimary,
				R.color.refresh_colorAccent, R.color.refresh_colorPrimaryDark, R.color.refresh_colorAccent);
	}
}
