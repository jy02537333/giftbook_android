package com.zxw.giftbook.utils.contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zxw.giftbook.R;

import pri.zxw.library.tool.ImgLoad.ImgUrlUtil;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;


public class ContactListAdapter extends BaseAdapter implements SectionIndexer {
	private LayoutInflater inflater;
	private HashMap<String, Integer> alphaIndexer; // 字母索引

	List<Entry<String, AddressBooktBean>> list1=null;
	private String[] sections; // 存储每个章节
	private Context ctx; // 上下文
	private CharacterParser characterParser = new CharacterParser();
//	private ImageLoader loader= ImageLoader.getInstance();//图片下载
	private Dialog dialog;
	public ContactListAdapter(Context context, List<Entry<String, AddressBooktBean>> list,
			QuickAlphabeticBar alpha) {
		list1=list;
		this.ctx = context;
		this.inflater = LayoutInflater.from(context);
		this.alphaIndexer = new HashMap<String, Integer>();
		this.sections = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			// 得到字母
			String name = ContactListAdapter.getAlpha(characterParser.getSelling(list.get(i).getValue().getSortKey()));
			if (!alphaIndexer.containsKey(name)) {
				alphaIndexer.put(name, i);
			}
		}
		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);

		alpha.setAlphaIndexer(alphaIndexer);


	}

	@Override
	public int getCount() {
		return list1.size();
	}

	@Override
	public Object getItem(int position) {
		return list1.get(position);
	}


	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 *
	 * @param list
	 */
	public void updateListView(List<Entry<String, AddressBooktBean>> list) {
		list1 =list;
		notifyDataSetChanged();
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	public void remove(int position) {
		list1.remove(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.plugin_contact_list_item, null);
			holder = new ViewHolder();
			holder.act_contact_call_img=(ImageView) convertView.findViewById(R.id.p_contact_list_item_call_img);
			holder.act_contact_head_img=(ImageView) convertView.findViewById(R.id.p_contact_list_item_head_img);
			holder.alpha = (TextView) convertView.findViewById(R.id.p_contact_list_item_alpha);
			holder.name = (TextView) convertView.findViewById(R.id.p_contact_list_item_name);
			holder.number = (TextView) convertView.findViewById(R.id.p_contact_list_item_number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AddressBooktBean contact =list1.get(position).getValue();

			String name = contact.getTfName();

			final String number = contact.getTfPhone();
			holder.name.setText(name);
			holder.number.setText(number);
		String path= ImgUrlUtil.getFullImgUrl(contact.getTfPortrait());
		MyImgLoadTool.loadNetImg(ctx,path, R.mipmap.user_default,holder.act_contact_head_img,400,400,null);
			holder.act_contact_call_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// 构造对话框
					AlertDialog.Builder builder = new AlertDialog.Builder(ctx,AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("提示");
					builder.setMessage("是否与其通话");
					// 更新
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent phoneIntent = new Intent("android.intent.action.CALL",
									Uri.parse("tel:" + number));

							ctx.startActivity(phoneIntent);
						}
					});
					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					Dialog noticeDialog = builder.create();
					Window window = noticeDialog.getWindow();
					WindowManager.LayoutParams lp = window.getAttributes();
					lp.alpha = 0.9f;
					window.setAttributes(lp);
					noticeDialog.show();

				}
			});




		// 当前字母

		String currentStr = getAlpha( characterParser.getSelling(contact.getSortKey()));
		// 前面的字母
		String previewStr = (position - 1) >= 0 ? getAlpha( characterParser.getSelling(list1.get(position - 1).getValue().getSortKey())) : " ";

		if (!previewStr.equals(currentStr)) {
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(currentStr);
		} else {
			holder.alpha.setVisibility(View.GONE);
		}
		return convertView;
	}

	private static class ViewHolder {
		TextView alpha;
		TextView name;
		TextView number;
		ImageView act_contact_head_img;
		ImageView act_contact_call_img;
	}

	/**
	 * 获取首字母
	 *
	 * @param str
	 * @return
	 */
	public static String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式匹配
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(); // 将小写字母转换为大写
		} else {
			return "#";
		}
	}

	@Override
	public int getPositionForSection(int position) {
		// TODO Auto-generated method stub
		return list1.get(position).getValue().getSortKey().charAt(0);
	}

	@Override
	public int getSectionForPosition(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr =list1.get(i).getValue().getSortKey();
			char firsc=sortStr.toUpperCase().charAt(0);
			if (firsc == section) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}


}
