package com.zxw.giftbook.Activity.contact;

import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
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
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.DataMapUtil;
import com.zxw.giftbook.utils.contact.AddressBooktBean;
import com.zxw.giftbook.utils.contact.CharacterParser;
import com.zxw.giftbook.utils.contact.ClearEditText;
import com.zxw.giftbook.utils.contact.ContactListAdapter;
import com.zxw.giftbook.utils.contact.ContactListSelectAdapter;
import com.zxw.giftbook.utils.contact.QuickAlphabeticBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.CustomDialog;
import pri.zxw.library.view.TitleBar;

/**
 * 手机联系人选择框
 *
 * @作者 刘碧涛
 * @版本 v1.0
 * @创建时间 2016/10/10
 */
public class PhoneContactListSelectActivity extends MyBaseActivity {

    private ContactListSelectAdapter adapter;
    private ListView contactList;
    public static List<Entry<String, AddressBooktBean>> mContactList;
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
    private QuickAlphabeticBar alphabeticBar; // 快速索引条
    private TitleBar mTitleBar;
    private ClearEditText mClearEditText;// 只定义收缩edittext
    private CharacterParser characterParser;
    private Button confirmBtn;
    private LinearLayout imgsLay;
    private Dialog dialog;
    private String ids;
    private boolean isImport=false;
    private static final int GET_CODE=5341;
     static final String ADD_URL="apiGroupmemberCtrl.do?importMember";
    AppServerTool mServicesTool;
    boolean isGetData=false;
    /**当前选择的组*/
    String typeId,typeName;
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_CODE) {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Object ret=messageHandlerTool.handlerObject(msg,
                        new TypeToken<List<GroupmemberEntity>>(){}.getType(),PhoneContactListSelectActivity.this);
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
            }else if(msg.what==GET_ADD_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                int ret=messageHandlerTool.handler(msg,PhoneContactListSelectActivity.this);
                if(ret==1)
                {
                    ToastShowTool.myToastShort(PhoneContactListSelectActivity.this,"导入成功！");
                    DataMapUtil.getAllTypeData(PhoneContactListSelectActivity.this,null);
                    setResult(1);
                    finish();
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
        typeId=getIntent().getStringExtra("typeid");
        typeName=getIntent().getStringExtra("typename");
        isImport=getIntent().getBooleanExtra("isImport",false);
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
//        if(mContactList!=null&&mContactList.size()>0)
//        {
//            initAdapterVal();
//        }else
//        {
            List<AddressBooktBean> listContact=getPhoneNumberFromMobile();
            sortList(listContact);
            initAdapterVal();
//        }
    }

    // 初始化标题栏
    private void tableinit() {
        if(isImport){
            mTitleBar.setTitle("手机通讯录");// 标题栏
        }else
        mTitleBar.setTitle("手机通讯录-导入"+typeName);// 标题栏
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
        mServicesTool=new AppServerTool(NetworkConfig.api_url,this,mHandler);
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
                            sb.append("{")
                                    .append("\"groupmember\":\"")
                                    .append(bean.getSortKey())
                                    .append("\",\"memberphone\":\"")

                                    .append(bean.getTfPhone())
                                    .append("\",\"gourpid\":\"")

                                    .append(typeId)
                                    .append("\",\"gourpName\":\"")

                                    .append(typeName).append("\"},");
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
                Map<String,String > params= ComParamsAddTool.getParam();
                params.put("importJson", sb.toString());
                params.put("gourpid", typeId);
                if(isSub)
                    return;
                isSub=true;
                mServicesTool.doPostAndalysisDataCall(ADD_URL, params, GET_ADD_CODE, new IServicesCallback() {
                    @Override
                    public void onStart() {
                        ProgressDialogTool.getInstance(PhoneContactListSelectActivity.this).showDialog("提交中...");
                    }

                    @Override
                    public void onEnd() {
                        ProgressDialogTool.getInstance(PhoneContactListSelectActivity.this).dismissDialog();
                        isSub=false;
                    }
                });
            }
        });
    }
    private void initAdapterVal()
    {
        String[] idArray = null;
        if (ids != null && ids.trim().length() > 0)
            idArray = ids.split(",");
        adapter = new ContactListSelectAdapter(
                PhoneContactListSelectActivity.this, mContactList,
                alphabeticBar, idArray);
        contactList.setAdapter(adapter);
        alphabeticBar.init(PhoneContactListSelectActivity.this,
                R.id.act_mine_select_contact_fast_position);
        alphabeticBar.setListView(contactList);
        alphabeticBar.setHight(alphabeticBar.getHeight());
        alphabeticBar.setVisibility(View.VISIBLE);
    }

    private void department() {
        if(isGetData)
            return ;
        Map<String,String> params = ComParamsAddTool.getParam();
        params.put("userid", FtpApplication.getInstance().getUser().getId());
        mServicesTool.doPostAndalysisDataCall("apiSidekickergroupCtrl.do?getFull", params, GET_CODE, new IServicesCallback() {
            @Override
            public void onStart() {
                dialog = CustomDialog.createLoadingDialog(PhoneContactListSelectActivity.this, "加载数据…");
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

    /**
     *  读取手机联系人信息
     * @return
     */
    public List<AddressBooktBean> getPhoneNumberFromMobile() {
        // TODO Auto-generated constructor stub
        List<AddressBooktBean> list = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            AddressBooktBean phoneInfo = new AddressBooktBean(null,name, name,number,null,null);
            list.add(phoneInfo);
        }
        return list;
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
