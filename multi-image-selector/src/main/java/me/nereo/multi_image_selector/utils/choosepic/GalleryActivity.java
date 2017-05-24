package me.nereo.multi_image_selector.utils.choosepic;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.nereo.multi_image_selector.R;
import pri.zxw.library.tool.ImgLoad.ImageUtil;


/**
 * 这个是用于进行图片浏览时的界面
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:47:53
 */
public class GalleryActivity extends Activity {
	private Intent intent;
    // 返回按钮
    private Button back_bt;
	// 发送按钮
	private Button send_bt;
	//删除按钮
	private Button del_bt;
	//顶部显示预览图片位置的textview
	private TextView positionTextView;
	//获取前一个activity传过来的position
	private int position;
	//当前的位置
	private int location = 0;
	
	private ArrayList<View> listViews = null;
	private ViewPagerFixed pager;
	private MyPageAdapter adapter;

	private ImageView deldle_button;
	private Context mContext;

	RelativeLayout photo_relativeLayout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mis_plugin_camera_gallery);// 切屏到主界面
		PublicWay.activityList.add(this);
		mContext = this;
		deldle_button=(ImageView) findViewById(R.id.mis_plugin_camera_deldle_button);
		back_bt = (Button) findViewById(Res.getWidgetID("mis_plugin_camera_gallery_back"));
		send_bt = (Button) findViewById(Res.getWidgetID("mis_plugin_camera_send_button"));
		del_bt = (Button)findViewById(Res.getWidgetID("mis_plugin_camera_gallery_del"));
		deldle_button.setOnClickListener(new DelListener());
		back_bt.setOnClickListener(new BackListener());
		send_bt.setOnClickListener(new GallerySendListener());
		del_bt.setOnClickListener(new DelListener());
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		position = Integer.parseInt(intent.getStringExtra("position"));
		isShowOkBt();
		// 为发送按钮设置文字
		pager = (ViewPagerFixed) findViewById(R.id.mis_plugin_camera_gallery01);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < ImgScrollGridTool.mSelectPath.size(); i++) {
			initListViews(ImageUtil.getSmallBitmap( ImgScrollGridTool.mSelectPath.get(i),60) );
		}
		
		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setPageMargin((int)getResources().getDimensionPixelOffset(Res.getDimenID("mis_ui_10_dip")));
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
//		TitleBar.initSystemBar(this);
	}
	
	private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			location = arg0;
			send_bt.setText(Res.getString("mis_finish")+"(" + (++arg0) + "/"+ImgScrollGridTool.mSelectPath.size()+")");
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};
	
	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}
	
	// 返回按钮添加的监听器
	private class BackListener implements OnClickListener {

		public void onClick(View v) {
			finish();
		}
	}
	
	// 删除按钮添加的监听器
	private class DelListener implements OnClickListener {

		public void onClick(View v) {
			if (listViews.size() == 1) {
				ImgScrollGridTool.mSelectPath.clear();
				send_bt.setText(Res.getString("mis_finish")+"(" +listViews.size() + "/"+ImgScrollGridTool.mSelectPath.size()+")");
				Intent intent = new Intent("data.broadcast.action");  
                sendBroadcast(intent);  
				finish();
			} else {
				listViews.remove(location);
				ImgScrollGridTool.mSelectPath.remove(location);
				pager.removeAllViews();
				adapter.setListViews(listViews);
				send_bt.setText(Res.getString("mis_finish")+"(" + listViews.size() + "/"+ImgScrollGridTool.mSelectPath.size()+")");
				adapter.notifyDataSetChanged();
			}
		}
	}

	// 完成按钮的监听
	private class GallerySendListener implements OnClickListener {
		public void onClick(View v) {
			   setResult(1);
			finish();
		}

	}

	public void isShowOkBt() {
		int index=location+1;
		if (ImgScrollGridTool.mSelectPath.size() > 0) {
			send_bt.setText(Res.getString("mis_finish")+"(" + index + "/"+ImgScrollGridTool.mSelectPath.size()+")");
			send_bt.setPressed(true);
			send_bt.setClickable(true);
			send_bt.setTextColor(Color.WHITE);
		} else {
			send_bt.setPressed(false);
			send_bt.setClickable(false);
			send_bt.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	/**
	 * 监听返回按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(position==1){
//				ImgScrollGridTool.mSelectPath.clear();
//				PicsSelectBimp.max = 0;
//				send_bt.setText(Res.getString("finish")+"(" + ImgScrollGridTool.mSelectPath.size() + "/"+PicsSelectBimp.max+")");
//				Intent intent = new Intent("data.broadcast.action");  
//                sendBroadcast(intent);  
				finish();
//				intent.setClass(GalleryActivity.this, PublishWXAct.class);
//				startActivity(intent);
				
			}else if(position==2){
//				ImgScrollGridTool.mSelectPath.clear();
//				PicsSelectBimp.max = 0;
//				send_bt.setText(Res.getString("finish")+"(" + ImgScrollGridTool.mSelectPath.size() + "/"+PicsSelectBimp.max+")");
//				Intent intent = new Intent("data.broadcast.action");  
//                sendBroadcast(intent);  
				finish();
//				intent.setClass(GalleryActivity.this, ShowAllPhotoAct.class);
//				startActivity(intent); 	
			}
		}
		return true;
	}
	
	
	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;
		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
	@Override
	protected void onDestroy() {
		PublicWay.activityList.remove(this);
		mContext=null;
		super.onDestroy();
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
