package pri.zxw.library.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import pri.zxw.library.refresh_tool.SwipeRecyclerView;

public abstract class MyBaseAdapter<T extends BaseEntity> extends RecyclerView.Adapter< RecyclerView.ViewHolder> {
	Activity mActivity;
	public int adapterWidth=0;
	protected MyBaseAdapter()
	{
		this.notifyDataSetChanged();
	}
	public MyBaseAdapter(Activity activity)
	{
		mActivity=activity;
	}
	public abstract Object getItem(int position) ;
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	} 
	/**
	 * 获取adapter item的宽度
	 */
	public int getItemWidth()
	{
		return adapterWidth;
	}
	public abstract void addData(T info);
	/**
	 * 批量添加对象方法
	 * @param infos
	 */
	public abstract void addDataAll(List<? extends BaseEntity> infos);
	public abstract void removeItem(int postion) ;
	/**
	 * 批量清空对象方法
	 */
	public abstract void remove();
	public interface  OnItemClickListener{
		public void onItemClick(View rootView, int position, long id);
	}
}
