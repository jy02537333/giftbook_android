/**  
* @Title: CateListAdapter.java
* @Package com.yunzhi.e_commerce.adapter
* @Description: TODO(用一句话描述该文件做什么)
* @author A18ccms Lix 
* @date 2015-1-13 下午4:24:23
* @version V1.0  
*/ 
package pri.zxw.library.adapter;

import java.util.List;

import pri.zxw.library.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

/**
 * @ClassName: CateListAdapter
 * @Description: 更多选项的分类列表的适配器
 * @author Lix
 * @date 2015-1-13 下午4:24:23
 *
 */
public class CateListAdapter extends BaseAdapter {

	
	private Context mContext;
	private List<CharSequence> mCategories;
	private LayoutInflater mInflater;
	private int mSelectedPos = 0;

	public CateListAdapter(Context context, List<CharSequence> categories) {
		super();
		this.mContext = context;
		this.mCategories = categories;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return (null == mCategories) ? 0 : mCategories.size();
	}

	@Override
	public CharSequence getItem(int position) {
		return (null == mCategories) ? null : mCategories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (null == mContext) {
			return null;
		}

		if (null == mCategories || mCategories.size() == 0
				|| mCategories.size() <= position) {
			return null;
		}

		final ViewHolder viewHolder;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.more_fragment_cate_list_item, parent,
					false);

			viewHolder.cateCheckedTextView = (CheckedTextView) convertView
					.findViewById(R.id.cate_tv);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.cateCheckedTextView.setText(mCategories.get(position));

		if (mSelectedPos == position) {
			viewHolder.cateCheckedTextView
					.setTextColor(Color.rgb(247, 88, 123));
		} else {
			viewHolder.cateCheckedTextView.setTextColor(Color
					.rgb(19, 12, 14));
		}
		return convertView;
	}

	class ViewHolder {
		CheckedTextView cateCheckedTextView;
	}

	public void setSelectedPos(int position) {
		this.mSelectedPos = position;
		notifyDataSetChanged();
	}

}
