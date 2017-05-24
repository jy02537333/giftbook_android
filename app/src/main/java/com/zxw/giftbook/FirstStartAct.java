package com.zxw.giftbook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zxw.giftbook.adapter.ViewPagerAdapter;
import com.zxw.giftbook.utils.ExitTool;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;
import pri.zxw.library.tool.ProgressDialogTool;

public class FirstStartAct extends MyBaseActivity  implements OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;

	// 底部小点图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
					case Welcome.GET_DEF_CATEGORY_CODE:
						if (msg.arg1 == 1) {

						}
						break;

				}
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialogTool.getInstance(FirstStartAct.this).dismissDialog();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_first_start);
		initData();
		// 初始化页面
		initViews();

		// 初始化底部小点
		initDots();
	}
	private void initData() {
	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		// 初始化引导图片列表
//		views.add(inflater.inflate(R.layout.a_first_what_new_one, null));
//		views.add(inflater.inflate(R.layout.a_first_what_new_two, null));
//		views.add(inflater.inflate(R.layout.a_first_what_new_three, null));
//		views.add(inflater.inflate(R.layout.a_first_what_new_four, null));

		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views, this);

		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);
		for (int i = 0; i < views.size(); i++) {
			ImageView img=(ImageView)views.get(i).findViewById(R.id.a_first_new_img);
			if(i==0)
			{
//				img.setBackgroundDrawable(getResources().getDrawable(R.mipmap.yingdao1));
			}
			if(i==1)
			{
//				img.setBackgroundDrawable(getResources().getDrawable(R.mipmap.yingdao2));
			}
			if(i==2)
			{
//				img.setBackgroundDrawable(getResources().getDrawable(R.mipmap.yingdao3));
			}
			if(i==3)
			{
//				img.setBackgroundDrawable(getResources().getDrawable(R.mipmap.yingdao4));
			}
		}
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[views.size()];

		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurrentDot(arg0);
	}
	@Override
	public void onBackPressed() {
		ExitTool.Exit(this);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ExitTool.Exit(this);
			return false;
		}
		return false;
	}
	@Override
	protected void onDestroy() {
		vp=null;
		vpAdapter=null;
		for (int i = 0; i < views.size(); i++) {
			ImageView img=(ImageView)views.get(i).findViewById(R.id.a_first_new_img);
			img.setBackgroundDrawable(null);
			img.destroyDrawingCache();
			views.get(i).destroyDrawingCache();
		}
		views.clear();

		for (int i = 0; i < dots.length; i++) {
			ImgLoadMipmapTool.recycle(dots[i]);
		}
		dots=null;
		super.onDestroy();
		System.gc();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
