package com.zxw.giftbook.Activity.contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.GroupMemberAddAct;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.myinterface.IDataMapUtilCallback;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.DataMapUtil;
import com.zxw.giftbook.utils.contact.AddressBooktBean;
import com.zxw.giftbook.utils.contact.CharacterParser;
import com.zxw.giftbook.utils.contact.ClearEditText;
import com.zxw.giftbook.utils.contact.ContactListAdapter;
import com.zxw.giftbook.utils.contact.ContactListSelectAdapter;
import com.zxw.giftbook.utils.contact.QuickAlphabeticBar;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.CustomDialog;
import pri.zxw.library.view.TitleBar;

/**
 * 单位联系人选择框
 *
 * @作者 刘碧涛
 * @版本 v1.0
 * @创建时间 2016/10/10
 */
public class ContactListSelectActivity extends MyBaseActivity {

    private ContactListSelectAdapter adapter;
    private ListView contactList;
    public static List<Map.Entry<String, AddressBooktBean>> mContactList;
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
    private QuickAlphabeticBar alphabeticBar; // 快速索引条
    private TitleBar mTitleBar;
    private ClearEditText mClearEditText;// 只定义收缩edittext
    private CharacterParser characterParser;
    private Button confirmBtn;
    private LinearLayout imgsLay;
    private Dialog dialog;
    private String ids;
    private static final int GET_CODE=5341;
    AppServerTool servicesTool;
    boolean isGetData=false;
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_CODE) {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Object ret=messageHandlerTool.handlerObject(msg,
                        new TypeToken<List<GroupmemberEntity>>(){}.getType(),ContactListSelectActivity.this);
                if(ret!=null)
                {
                    List<GroupmemberEntity> list1=( List<GroupmemberEntity> )ret;
                    List<AddressBooktBean> listContact=new ArrayList<>();
                    for (GroupmemberEntity item:list1)
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
                    initAdapterVal();
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
        ids = getIntent().getStringExtra("ids");
        setContentView(R.layout.act_select_contact);
        mClearEditText = (ClearEditText) findViewById(R.id.act_mine_select_contact_filter_edit);
        contactList = (ListView) findViewById(R.id.act_mine_select_contact_list);
        mTitleBar = (TitleBar) findViewById(R.id.act_mine_select_contact_title_bar);
        alphabeticBar = (QuickAlphabeticBar) findViewById(R.id.act_mine_select_contact_fast_scroller);
        confirmBtn = (Button) findViewById(R.id.act_mine_select_contact_confirm_btn);
        imgsLay = (LinearLayout) findViewById(R.id.act_mine_select_contact_imgs_lay);
        TitleBar.initSystemBar(this);
        init();
        tableinit();
        if(mContactList!=null&&mContactList.size()>0)
        {
            initAdapterVal();
        }else
        department();
    }

    // 初始化标题栏
    private void tableinit() {
        mTitleBar.setTitle("通讯录");// 标题栏
        mTitleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 初始化数据库查询参数
     */
    private void init() {
        servicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 这个时候不需要挤压效果 就把他隐藏掉
                // slidingview.setVisibility(View.GONE);
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
        confirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                StringBuffer sb = new StringBuffer("[");
                StringBuffer ids = new StringBuffer();
                if (adapter.checkMap != null && adapter.checkMap.size() > 0)
                    for (Integer key : adapter.checkMap.keySet()) {
                        if (adapter.checkMap.get(key)) {
                            AddressBooktBean bean = (AddressBooktBean) adapter
                                    .getItem(key);
                            sb.append("{\"id\":\"").append(bean.getTfId())
                                    .append("\",\"name\":\"")
                                    .append(bean.getSortKey())
                                    .append("\",\"phone\":\"").append(bean.getTfPhone()).append("\"},");




                            ids.append(bean.getTfId()).append(",");
                        }
                    }
                sb = sb.deleteCharAt(sb.length() - 1);
                if (ids.length() < 0)
                    ids = ids.deleteCharAt(ids.length() - 1);
                Intent intent = new Intent();
                if (sb.length() > 5) {
                    sb.append("]");
                    intent.putExtra("entitys", sb.toString());
                    intent.putExtra("ids", ids.toString());
                }
                setResult(1, intent);
                finish();
            }
        });
    }
    private void initAdapterVal()
    {
        String[] idArray = null;
        if (ids != null && ids.trim().length() > 0)
            idArray = ids.split(",");
        adapter = new ContactListSelectAdapter(
                ContactListSelectActivity.this, mContactList,
                alphabeticBar, idArray);
        contactList.setAdapter(adapter);
        alphabeticBar.init(ContactListSelectActivity.this,
                R.id.act_mine_select_contact_fast_position);
        alphabeticBar.setListView(contactList);
        alphabeticBar.setHight(alphabeticBar.getHeight());
        alphabeticBar.setVisibility(View.VISIBLE);
    }

    private void department() {
        Map<String, SidekickergroupEntity> obj= DataMapUtil.getAllTypeData(this,true,false, new IDataMapUtilCallback() {
            @Override
            public void onSuccess(boolean isSuccess) {
                if(isSuccess)
                {
                    Map<String, SidekickergroupEntity> obj1= DataMapUtil.getAllTypeData(ContactListSelectActivity.this, null);
                    initData(obj1);
                }
            }

            @Override
            public void onFailure(boolean isFailure) {
                ToastShowTool.myToastShort(ContactListSelectActivity.this,"加载异常！");
            }
        });
        initData(obj);
    }
    void initData( Map<String, SidekickergroupEntity> obj){
        if(obj!=null)
        {
            List<AddressBooktBean> listContact=new ArrayList<>();
            for (Map.Entry<String, SidekickergroupEntity> item:obj.entrySet()                 ) {
                if(item.getValue().getGroupmemberList()==null)
                    continue;
                for (GroupmemberEntity entity :item.getValue().getGroupmemberList())
                {
                    AddressBooktBean addressBooktBean=new AddressBooktBean();
                    addressBooktBean.setTfId(entity.getId());
                    addressBooktBean.setTfName (item.getValue().getGroupname()+"  "+ entity.getGroupmember());
                    addressBooktBean.setSortKey (entity.getGroupmember());
                    addressBooktBean.setTfPhone (entity.getMemberphone());
                    addressBooktBean.setTfPortrait ("");
                    addressBooktBean.setAffiliatedperson(entity.getAffiliatedperson());
                    listContact.add(addressBooktBean);
                }
            }
            sortList(listContact);
            initAdapterVal();
        }
    }

    public void sortList(List<AddressBooktBean> _list1111) {
        HashMap<String, AddressBooktBean> infoMap = new HashMap<String, AddressBooktBean>();
        for (int i = 0; i < _list1111.size(); i++) {
            infoMap.put(_list1111.get(i).getTfName(), _list1111.get(i));
        }

        //通过ArrayList构造函数把map.entrySet()转换成list  
        mContactList = new ArrayList<Entry<String, AddressBooktBean>>(infoMap.entrySet());
        Collections.sort(mContactList, new Comparator<Entry<String, AddressBooktBean>>() {
            @Override
            public int compare(Entry<String, AddressBooktBean> o1,
                               Entry<String, AddressBooktBean> o2) {
                String name1 = ContactListAdapter.getAlpha(characterParser.getSelling(o1.getValue().getSortKey()));
                String name2 = ContactListAdapter.getAlpha(characterParser.getSelling(o2.getValue().getSortKey()));
                return name1.compareTo(name2);
            }
        });
    }

    private void filterData(String filterStr) {
        List<Entry<String, AddressBooktBean>> filterDateList = new ArrayList<Entry<String, AddressBooktBean>>();// 初始化实体类
        if (filterStr.toString().trim().equals("")) {// 如果为空 显示全部
            filterDateList = mContactList;
            // tvNofriends.setVisibility(View.GONE);
        } else {
            filterDateList.clear();// 如果不为空遍历后添加到filter date list中
            for (Entry<String, AddressBooktBean> sortModel : mContactList) {
                String name = sortModel.getValue().getSortKey();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith( // 汉子转拼音
                        filterStr.toString())) {
                    filterDateList.add(sortModel);// 添加到filterDateList中
                }
            }
        }
        adapter.updateListView(filterDateList);// 刷新适配器
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
    }

}
