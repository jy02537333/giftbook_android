package me.nereo.multi_image_selector.utils.choosepic;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 这个是显示一个文件夹里面的所有图片时用的适配器
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:49:35
 */
public class AlbumGridViewAdapter extends BaseAdapter{
	final String TAG = getClass().getSimpleName();
	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private ArrayList<String> keyList=new ArrayList<String>();
	private ArrayList<ImageItem> selectedData;
	private DisplayMetrics dm;
	BitmapCache cache;
	public AlbumGridViewAdapter(Context c, ArrayList<ImageItem> dataList,
			ArrayList<String> selectedDataList) {
		mContext = c;
		cache = new BitmapCache();
		this.dataList = dataList;
		for (String item : selectedDataList) {
			keyList.add(item);
		}
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
	}

	public int getCount() {
		return dataList.size();
	}

	public Object getItem(int position) {
		return dataList.get(position);
	}
	public void initKey(ArrayList<ImageItem> selectedDataList)
	{
		keyList.clear();
		for (ImageItem item : selectedDataList) {
			keyList.add(item.imageId);
		}
	}

	public long getItemId(int position) {
		return 0;
	}
	public void add(ImageItem imgItem)
	{
		keyList.add(imgItem.getImageId());
	}
	public void remove(ImageItem imgItem)
	{
		keyList.remove(imgItem.getImageId());
	}

	BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
				}
			} else {
			}
		}
	};

	/**
	 * 存放列表项控件句柄
	 */
	private class ViewHolder {
		public ImageView imageView;
		public ToggleButton toggleButton;
		public Button choosetoggle;
		public TextView textView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					Res.getLayoutID("plugin_camera_select_imageview"), parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(Res.getWidgetID("image_view"));
			viewHolder.toggleButton = (ToggleButton) convertView
					.findViewById(Res.getWidgetID("toggle_button"));
			viewHolder.choosetoggle = (Button) convertView
					.findViewById(Res.getWidgetID("choosedbt"));
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,dipToPx(65));
			lp.setMargins(50, 0, 50,0);
			viewHolder.imageView.setLayoutParams(lp);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String path;
		if (dataList != null && dataList.size() > position)
			path = dataList.get(position).imagePath;
		else
			path = "camera_default";
		if (path.contains("camera_default")) {
			viewHolder.imageView.setImageResource(Res.getDrawableID("plugin_camera_no_pictures"));
		} else {
			final ImageItem item = dataList.get(position);
			viewHolder.imageView.setTag(item.imagePath);
			cache.displayBmp(viewHolder.imageView, item.thumbnailPath, item.imagePath,
					callback);
		}
		viewHolder.toggleButton.setTag(position);
		viewHolder.choosetoggle.setTag(position);
		viewHolder.toggleButton.setOnClickListener(new ToggleClickListener(viewHolder.toggleButton,viewHolder.choosetoggle));
		if (keyList.contains(dataList.get(position).imageId)) {
			viewHolder.toggleButton.setChecked(true);
			viewHolder.choosetoggle.setVisibility(View.VISIBLE);
		} else {
			viewHolder.toggleButton.setChecked(false);
			viewHolder.choosetoggle.setVisibility(View.GONE);
		}
		viewHolder.choosetoggle.setOnClickListener(new ToggleClickListener(viewHolder.toggleButton,viewHolder.choosetoggle));
		if (keyList.contains(dataList.get(position).imageId)) {
			viewHolder.toggleButton.setChecked(true);
			viewHolder.choosetoggle.setVisibility(View.VISIBLE);
		} else {
			viewHolder.toggleButton.setChecked(false);
			viewHolder.choosetoggle.setVisibility(View.GONE);
		}
		return convertView;
	}

	public int dipToPx(int dip) {
		return (int) (dip * dm.density + 0.5f);
	}
	private class ToggleClickListener implements OnClickListener{
		Button chooseBt;
		ToggleButton mToggleButton;
		public ToggleClickListener(ToggleButton toggleButton,Button choosebt){
			this.chooseBt = choosebt;
			mToggleButton=toggleButton;
		}

		@Override
		public void onClick(View view) {
			if (view instanceof ToggleButton) {
				ToggleButton toggleButton = (ToggleButton) view;
				int position = (Integer) toggleButton.getTag();
				if (dataList != null && mOnItemClickListener != null
						&& position < dataList.size()) {
					mOnItemClickListener.onItemClick(toggleButton, position, toggleButton.isChecked(),chooseBt);
				}
			}else if (view instanceof Button) {
				int position = (Integer) chooseBt.getTag();
				if (dataList != null && mOnItemClickListener != null
						&& position < dataList.size()) {
					mOnItemClickListener.onItemClick(mToggleButton, position, !mToggleButton.isChecked(),chooseBt);
					mToggleButton.setChecked(!mToggleButton.isChecked());
				}
			}
		}
	}


	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	public interface OnItemClickListener {
		public void onItemClick(ToggleButton view, int position,
								boolean isChecked, Button chooseBt);
	}

}
