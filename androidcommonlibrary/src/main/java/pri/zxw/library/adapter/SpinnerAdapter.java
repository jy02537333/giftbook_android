package pri.zxw.library.adapter;

import java.util.List;

import pri.zxw.library.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author 龙流平(LongLiuPing)
 * @version 创建时间：2015年2月1日 下午2:08:57 package com.yunzhi.e_commerce.adapter;
 */
public class SpinnerAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	List<String> listInfo;

	public SpinnerAdapter(Context context, List<String> listInfo) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listInfo = listInfo;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_account, null);
			holder.item_username = (TextView) convertView.findViewById(R.id.item_username);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String info = listInfo.get(position);
		if (info != null) {
			holder.item_username.setText(info);
		}
		return convertView;
	}

	class ViewHolder {
		TextView item_username;
	}
}
