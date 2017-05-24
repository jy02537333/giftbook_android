package com.zxw.giftbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zxw.giftbook.R;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.entity.ComInfo;

public class KeywordAdapter extends BaseAdapter  {
	private Context mContext;
	private List<ComInfo> comLists;
	private LayoutInflater inflater = null;
	
	public KeywordAdapter(Context context) {
		this.mContext = context;
		inflater= LayoutInflater.from(context);
		comLists=new ArrayList<ComInfo>();
	}

	@Override
	public int getCount() {
		if(comLists!=null)
			return comLists.size();
		return 0;
	}
	public void addData(List<ComInfo> infos) {
		comLists.addAll(infos);
		this.notifyDataSetChanged();
	}
	@Override
	public ComInfo getItem(int position) {
		return comLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item_gv_keyword, null);
			mHolder = new ViewHolder();
			mHolder.keyword_tv = (TextView) view.findViewById(R.id.item_keyword_tv);
			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}
		ComInfo comInfo =comLists.get(position);
		mHolder.keyword_tv.setText(comInfo.getName());
		return view;
	}

	class ViewHolder {
		TextView keyword_tv;
	}


	
}
