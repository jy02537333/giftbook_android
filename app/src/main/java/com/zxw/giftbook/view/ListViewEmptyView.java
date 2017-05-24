package com.zxw.giftbook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 
 * @description listview或者grid 空数据时显示
 * @author 张相伟
 * @date 2016-10-12
 */
public class ListViewEmptyView extends TextView {

	private Context mContext;
	 
	
	public ListViewEmptyView(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public ListViewEmptyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public ListViewEmptyView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}
	public void init()
	{
		this.setText("这里还是空空如也");
		this.setGravity(Gravity.CENTER);
	}

}
