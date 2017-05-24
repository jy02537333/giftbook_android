//package com.zxw.giftbook.Activity;
//
//
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.regex.Pattern;
//
//import net.tsz.afinal.FinalHttp;
//import net.tsz.afinal.http.AjaxCallBack;
//import net.tsz.afinal.http.AjaxParams;
//
//
//
//import com.alibaba.fastjson.JSON;
//import com.hxyc.activity.BaseActivity;
//import com.hxyc.app.ConstantsUser;
//import com.hxyc.app.R;
//import com.hxyc.bean.AddressBooktBean;
//import com.hxyc.bean.ContactBean;
//import com.hxyc.bean.PolicyModel;
//import com.hxyc.config.Config;
//import com.hxyc.interfaces.TitleOnClickListener;
//import com.hxyc.ui.adapter.ContactListAdapter;
//import com.hxyc.ui.adapter.PolicyAdapter;
//import com.hxyc.util.ToolsString;
//import com.hxyc.views.AppRestCuer;
//import com.hxyc.views.CustomToast;
//import com.hxyc.views.contacts.CharacterParser;
//import com.hxyc.views.contacts.ClearEditText;
//import com.hxyc.views.contacts.QuickAlphabeticBar;
//import com.hxyc.widget.TitleBar;
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.AsyncQueryHandler;
//import android.content.ContentResolver;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.ListView;
//
//import pri.zxw.library.base.MyBaseActivity;
//
///**
// * 联系人
// * @作者 刘碧涛
// * @版本 v1.0
// * @创建时间  2016/10/10
// *
// */
//public class ContactListActivity extends MyBaseActivity {
//
//	private ContactListAdapter adapter;
//	private ListView contactList;
//	List<Entry<String, AddressBooktBean>> list1=null;
//	private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
//	private QuickAlphabeticBar alphabeticBar; // 快速索引条
//	private TitleBar mTitleBar;
//	private Map<Integer, ContactBean> contactIdMap = null;
//	private ClearEditText mClearEditText;//只定义收缩edittext
//	private CharacterParser characterParser;
//	private Dialog dialog;
//
//	/**
//	 * 根据拼音来排列ListView里面的数据类
//	 */
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.act_mine_contact);
//		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
//		contactList = (ListView) findViewById(R.id.act_contact_list);
//		mTitleBar=(TitleBar) findViewById(R.id.contact_title_bar);
//		alphabeticBar = (QuickAlphabeticBar) findViewById(R.id.fast_scroller);
//		TitleBar.initSystemBar(this);
//
//		init();
//		tableinit();
//		department();
//	}
//	//初始化标题栏
//	private void tableinit() {
//		mTitleBar.setTitle("单位通讯录");// 标题栏
//		mTitleBar.setLeftClickListener(new TitleOnClickListener() {
//			@Override
//			public void onClick(View view) {
//				onBackPressed();
//			}
//		});
//	}
//
//	/**
//	 * 初始化数据库查询参数
//	 */
//	private void init() {
//		// 实例化汉字转拼音类
//		characterParser = CharacterParser.getInstance();
//
//		// 根据输入框输入值的改变来过滤搜索
//		mClearEditText.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				// 这个时候不需要挤压效果 就把他隐藏掉
//				//slidingview.setVisibility(View.GONE);
//				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//				filterData(s.toString());
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//
//			}
//			@Override
//			public void afterTextChanged(Editable s) {
//			}
//		});
//	}
//
//	/**
//	 *
//	 * @author Administrator
//	 *
//	 */
//	private void department() {
//		// TODO Auto-generated method stub
//		// TODO Auto-generated method stub
//		final FinalHttp http = new FinalHttp();
//		final AjaxParams params = new AjaxParams();
//		params.put("fkey",Config.fkey);
//		params.put("userId",ConstantsUser.getUsers().getTfId());
//		params.put("unitId",ConstantsUser.getUsers().getTfSysUnitId());
//		http.post(Config.AddressBook, params, new AjaxCallBack<Object>(){
//			@Override
//			public void onStart() {
//				dialog = CustomToast.createLoadingDialog(ContactListActivity.this,"加载数据…");
//				dialog.setCancelable(false);
//				dialog.show();
//			}
//			@Override
//			public void onSuccess(Object t) {
//				dialog.dismiss();
//				if (!ToolsString.isEmptyForObj(t)) {
//					final HashMap<String, Object> map = ToolsString.readAjaxApi(t);
//					final String result = map.get("result").toString();
//
//					if (result.equals("1")){
//						final String varList=map.get("varlist").toString();
//					List<AddressBooktBean>	list = JSON.parseArray(varList, AddressBooktBean.class);
//					sortList(list);
//						adapter = new ContactListAdapter(ContactListActivity.this, list1, alphabeticBar);
//						contactList.setAdapter(adapter);
//						alphabeticBar.init(ContactListActivity.this);
//						alphabeticBar.setListView(contactList);
//						alphabeticBar.setHight(alphabeticBar.getHeight());
//						alphabeticBar.setVisibility(View.VISIBLE);
//					}else {
//						CustomToast.ToastError(ContactListActivity.this, map.get("msg").toString());
//					}
//				}
//			}
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				dialog.dismiss();
//				CustomToast.ToastError(ContactListActivity.this,"网络连接失败");
//			}
//		});
//	}
//	public void sortList(List<AddressBooktBean> list)
//	{
//		HashMap<String, AddressBooktBean> infoMap=new HashMap<String, AddressBooktBean>();
//		for (int i = 0; i < list.size(); i++) {
//			infoMap.put( list.get(i).getTfName(), list.get(i));
//		}
//
//		//通过ArrayList构造函数把map.entrySet()转换成list  
//		list1=new ArrayList<Entry<String,AddressBooktBean>>(infoMap.entrySet());
//		Collections.sort(list1,new Comparator<Entry<String,AddressBooktBean>>() {
//			@Override
//			public int compare(Entry<String, AddressBooktBean> o1,
//					Entry<String, AddressBooktBean> o2) {
//				String name1 = getAlpha(characterParser.getSelling(o1.getValue().getSortKey()));
//				String name2 = getAlpha(characterParser.getSelling(o2.getValue().getSortKey()));
//				return name1.compareTo(name2);
//			}
//		});
//	}
//	/**
//	 * 获取首字母
//	 *
//	 * @param str
//	 * @return
//	 */
//	private String getAlpha(String str) {
//		if (str == null) {
//			return "#";
//		}
//		if (str.trim().length() == 0) {
//			return "#";
//		}
//		char c = str.trim().substring(0, 1).charAt(0);
//		// 正则表达式匹配
//		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
//		if (pattern.matcher(c + "").matches()) {
//			return (c + "").toUpperCase(); // 将小写字母转换为大写
//		} else {
//			return "#";
//		}
//	}
//
//
//	private void filterData(String filterStr) {
//		List<Entry<String, AddressBooktBean>> filterDateList = new ArrayList<Entry<String, AddressBooktBean>>();//初始化实体类
//		if (filterStr.toString().trim().equals("")) {//如果为空 显示全部
//			filterDateList = list1;
//			//	tvNofriends.setVisibility(View.GONE);
//		} else {
//			filterDateList.clear();//如果不为空遍历后添加到filter date list中
//			for (Entry<String, AddressBooktBean> sortModel : list1) {
//				String name = sortModel.getValue().getSortKey();
//				if (name.indexOf(filterStr.toString()) != -1
//						|| characterParser.getSelling(name).startsWith( //汉子转拼音
//								filterStr.toString())) {
//					filterDateList.add(sortModel);//添加到filterDateList中
//				}
//			}
//		}
//		adapter.updateListView(filterDateList);// 刷新适配器
//	}
//	}