package com.zxw.giftbook.Activity.contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.LogRecord;
import java.util.regex.Pattern;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zxw.giftbook.Activity.GiftMoneyAddAct;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.contact.AddressBooktBean;
import com.zxw.giftbook.utils.contact.CharacterParser;
import com.zxw.giftbook.utils.contact.ClearEditText;
import com.zxw.giftbook.utils.contact.ContactBean;
import com.zxw.giftbook.utils.contact.ContactListAdapter;
import com.zxw.giftbook.utils.contact.QuickAlphabeticBar;

import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ServicesTool;
import pri.zxw.library.tool.ToolsString;
import pri.zxw.library.tool.dialogTools.CustomDialog;
import pri.zxw.library.view.TitleBar;

/**
 * 部门通讯录联系人
 *
 * @作者 张相伟
 * @版本 v1.0
 * @创建时间 2016/10/10
 */
public class ContactListActivity extends MyBaseActivity {

    private ContactListAdapter adapter;
    private ListView contactList;
    List<Entry<String, AddressBooktBean>> list1 = null;
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
    private QuickAlphabeticBar alphabeticBar; // 快速索引条
    private TitleBar mTitleBar;
    private Map<Integer, ContactBean> contactIdMap = null;
    private ClearEditText mClearEditText;//只定义收缩edittext
    private CharacterParser characterParser;
    private Dialog dialog;
    private static final int GET_CODE=5341;
    AppServerTool servicesTool;
    boolean isGetData=false;
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Object ret=messageHandlerTool.handlerObject(msg,
                        new TypeToken<List<GroupmemberEntity>>(){}.getType(),ContactListActivity.this);
                if(ret!=null)
                {
                    List<GroupmemberEntity> list=( List<GroupmemberEntity> )ret;
                    List<AddressBooktBean> listContact=new ArrayList<>();
                    for (GroupmemberEntity item:list)
                    {
                        AddressBooktBean addressBooktBean=new AddressBooktBean();
                        addressBooktBean.setTfId(item.getId());
                        addressBooktBean.setTfName (item.getGourpName()+"  "+ item.getGroupmember());
                        addressBooktBean.setSortKey (item.getGroupmember());
                        addressBooktBean.setTfPhone (item.getMemberphone());
                        addressBooktBean.setTfPortrait ("");
                        addressBooktBean.setAffiliatedperson(item.getAffiliatedperson());
                        listContact.add(addressBooktBean);
                    }
                    sortList(listContact);
                    adapter = new ContactListAdapter(ContactListActivity.this, list1, alphabeticBar);
                    contactList.setAdapter(adapter);
                    alphabeticBar.init(ContactListActivity.this);
                    alphabeticBar.setListView(contactList);
                    alphabeticBar.setHight(alphabeticBar.getHeight());
                    alphabeticBar.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_contact);
        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        contactList = (ListView) findViewById(R.id.act_contact_list);
        mTitleBar = (TitleBar) findViewById(R.id.contact_title_bar);
        alphabeticBar = (QuickAlphabeticBar) findViewById(R.id.fast_scroller);
        TitleBar.initSystemBar(this);

        init();
        initTool();
        tableinit();
        department();
    }

    //初始化标题栏
    private void tableinit() {
        mTitleBar.setTitle("部门通讯录");// 标题栏
        mTitleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void initTool(){
        servicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
    }

    /**
     * 初始化数据库查询参数
     */
    private void init() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 这个时候不需要挤压效果 就把他隐藏掉
                //slidingview.setVisibility(View.GONE);
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * @author Administrator
     */
    private void department() {
        if(isGetData)
            return ;

         Map<String,String> params = ComParamsAddTool.getParam();
        params.put("userid", FtpApplication.getInstance().getUser().getId());
        servicesTool.doPostAndalysisDataCall("apiSidekickergroupCtrl.do?getFull", params, GET_CODE, new IServicesCallback() {
            @Override
            public void onStart() {
                dialog = CustomDialog.createLoadingDialog(ContactListActivity.this, "加载数据…");
                dialog.setCancelable(false);
                dialog.show();
                isGetData=true;
            }
            @Override
            public void onEnd() {
                isGetData=false;
                dialog.dismiss();
            }
        });
    }

    public void sortList(List<AddressBooktBean> list) {
        HashMap<String, AddressBooktBean> infoMap = new HashMap<String, AddressBooktBean>();
        for (int i = 0; i < list.size(); i++) {
            infoMap.put(list.get(i).getTfName(), list.get(i));
        }

        //通过ArrayList构造函数把map.entrySet()转换成list  
        list1 = new ArrayList<Entry<String, AddressBooktBean>>(infoMap.entrySet());
        Collections.sort(list1, new Comparator<Entry<String, AddressBooktBean>>() {
            @Override
            public int compare(Entry<String, AddressBooktBean> o1,
                               Entry<String, AddressBooktBean> o2) {
                String name1 = getAlpha(characterParser.getSelling(o1.getValue().getSortKey()));
                String name2 = getAlpha(characterParser.getSelling(o2.getValue().getSortKey()));
                return name1.compareTo(name2);
            }
        });
    }

    /**
     * 获取首字母
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
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


    private void filterData(String filterStr) {
        List<Entry<String, AddressBooktBean>> filterDateList = new ArrayList<Entry<String, AddressBooktBean>>();//初始化实体类
        if (filterStr.toString().trim().equals("")) {//如果为空 显示全部
            filterDateList = list1;
            //	tvNofriends.setVisibility(View.GONE);
        } else {
            filterDateList.clear();//如果不为空遍历后添加到filter date list中
            for (Entry<String, AddressBooktBean> sortModel : list1) {
                String name = sortModel.getValue().getSortKey();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith( //汉子转拼音
                        filterStr.toString())) {
                    filterDateList.add(sortModel);//添加到filterDateList中
                }
            }
        }
        adapter.updateListView(filterDateList);// 刷新适配器
    }
}