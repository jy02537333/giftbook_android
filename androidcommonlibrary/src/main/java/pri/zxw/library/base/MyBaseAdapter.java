package pri.zxw.library.base;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.entity.AbstractStartDateEntity;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T extends BaseEntity> extends BaseAdapter {
	Activity mActivity;
	private List<T> mList=new ArrayList<>();
	public int adapterWidth=0;
	protected MyBaseAdapter()
	{
		
	}
	public MyBaseAdapter(Activity activity)
	{
		mList=new ArrayList<T>();
		mActivity=activity;
	}
	 @Override  
	 public View getView(final int position, View convertView, ViewGroup parent) { 
	 
	 	 return convertView;  
	 }  
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}
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
	public void addData(T info) { 
		if(mList!=null) 
	   	mList.add(info); 
	} 
	/**
	 * 批量添加对象方法
	 * @param infos
	 */
	public abstract void addDataAll(List<T> infos);
	public void removeItem(int postion) { 
	     if(mList!=null) 
		        mList.remove(postion); 
	}
	/**
	 * 批量清空对象方法
	 */
	public abstract void remove();
}
