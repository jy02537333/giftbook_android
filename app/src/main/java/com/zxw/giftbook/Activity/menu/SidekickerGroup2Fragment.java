package com.zxw.giftbook.Activity.menu;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxw.giftbook.Activity.GroupMemberAddAct;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.SidekickerGroup2Adapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.db.JsonStrHistoryDao;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.listener.TxtLengthRestrictTool;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.MyAlertDialog;
import pri.zxw.library.tool.dialogTools.DialogSelectBtn;
import pri.zxw.library.view.TitleBar;

/**
 * 亲友团
 * Created by lenovo on 2016-07-15.
 */
public class SidekickerGroup2Fragment extends MyPullToRefreshBaseFragment {
    boolean isData=false;
    boolean isSubmit=false;
    View view;
    TitleBar titleBar;
    PullToRefreshListView lv;
    SidekickerGroup2Adapter adapter;
    AppServerTool mServicesTool;
    public static final String GET_DATA_URL="apiSidekickergroupCtrl.do?list";
    public static final int GET_DATA_CODE=1111;
    public static final int GET_ADD_CODE=222;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {

                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<List<SidekickergroupEntity>>(){}.getType();
                MessageHandlerTool.MessageInfo messageInfo =messageHandlerTool.
                        handler(msg,SidekickerGroup2Fragment.this,adapter,lv,type);
               if(messageInfo.getIsHashValue())
                {
                    JsonStrHistoryDao dao=new JsonStrHistoryDao();
                    dao.addCache("sidekickerGroup",messageHandlerTool.jsonStr);
                }
                isData=false;
            }else if(msg.what==GET_ADD_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                String id=messageHandlerTool.handlerData(msg,getActivity());
                if(id!=null)
                {
                    adapter.remove();
                    isSubmit=false;
                    getWebData();
                }
            } else if(msg.what==LOAD_CODE)
            {
                lv.setRefreshing(true);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.f_sidekicker_group2, container, false);
        initView();
        initTool();
        initListener();
        Gson gson=new Gson();
        JsonStrHistoryDao dao=new JsonStrHistoryDao();
        String str=dao.getCache("sidekickerGroup");
        if(str!=null&&str.trim().length()>0) {
            Type type = new TypeToken<List<SidekickergroupEntity>>() {            }.getType();
            List<SidekickergroupEntity> list=  gson.fromJson(str, type);
        }
        listLoad(mHandler);
        return view;
    }

    private void  initView()
    {
     titleBar=(TitleBar) view.findViewById(R.id.f_sidekicker_group2_title_bar);
        lv=(PullToRefreshListView) view.findViewById(R.id.f_sidekicker_group2_lv);
    }
    void initTool()
    {
//        MenuSettingViewInit.init(titleBar,getActivity());
        mServicesTool=new AppServerTool(NetworkConfig.api_url,getActivity(),mHandler);
        adapter=new SidekickerGroup2Adapter(this);
        lv.setAdapter(adapter);
        this.initListener(lv,adapter);
    }
    private void  initListener()
    {
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                showAddView();
            }
        });
    }
    public void addGroup(String name)
    {
        if(isSubmit)
            return ;
        isSubmit=true;
        Map<String,String> params= ComParamsAddTool.getParam();
        params.put("groupmembersnum","0");
        params.put("groupname",name);
        params.put("userid", FtpApplication.getInstance().getUser().getId());
        mServicesTool.doPostAndalysisData("apiSidekickergroupCtrl.do?doAdd",params,GET_ADD_CODE);
    }
    public void operate(final String id, final String name)
    {
        int width=320;
        int height= 180;
        List<View> btns=new ArrayList<>();
        TextView btn1= new TextView(getActivity());
         LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width,height);
        lp.setMargins(0,10,0,0);
        btn1.setLayoutParams(lp);
        btn1.setCompoundDrawablePadding(10);
        Drawable drawable=getResources().getDrawable(R.mipmap.operate_del);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        btn1.setCompoundDrawables(drawable,null,null,null);
        btn1.setText("删除");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del(id,name);
            }
        });
        btns.add(btn1);
        TextView lineTv=new TextView(getActivity());
        LinearLayout.LayoutParams lpLine=new LinearLayout.LayoutParams(width,1);
        lineTv.setLayoutParams(lp);
        btns.add(lineTv);

        TextView btn2= new TextView(getActivity());
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(width,height);
        lp2.setMargins(0,10,0,10);
        btn2.setLayoutParams(lp2);
        btn1.setCompoundDrawablePadding(10);
        Drawable drawable2=getResources().getDrawable(R.mipmap.operate_edit);
        drawable2.setBounds(0,0,drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
        btn1.setCompoundDrawables(drawable,null,null,null);
        btn2.setText("编辑");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(id,name);
            }
        });
        btns.add(btn2);
        DialogSelectBtn.show(getActivity(),"操作分类："+name,btns,view);
//
//        final MyAlertDialog.MyBuilder dialog1 = new MyAlertDialog.MyBuilder(getActivity());
//        dialog1.setTitle("操作分类："+name);
//        dialog1.setAutoDismiss(false, true);
//        LayoutInflater mLayoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
//        View dialog_view = mLayoutInflater.inflate(
//                R.layout.tool_alert_edit_text, null);
//        dialog1.setContentView(dialog_view);
//        final EditText editText = (EditText) dialog_view
//                .findViewById(R.id.nickname_edit_aty_editor);
//        editText.setTextColor(getResources().getColor(R.color.font_com_content_black_color));
//        editText.setHintTextColor(getResources().getColor(R.color.com_hint_font_gray_color));
//        editText.setHint("输入分类");
//        editText.addTextChangedListener(new TxtLengthRestrictTool(editText, 20));
//        editText.setSelection(editText.getText().length());
//        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                String str = editText.getText().toString().trim();
//                addGroup(str);
//                dialog1.dismiss();
//            }
//        });
//        dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                dialog1.dismiss();
//            }
//        });
//        dialog1.create().show();
    }
    void del(String id,String name)
    {

    }
    void edit(String id,String name)
    {

    }

    public void addMember(String id,String groupName)
    {
        Intent intent=new Intent(getActivity(), GroupMemberAddAct.class);
        intent.putExtra("id",id);
        intent.putExtra("groupName",groupName);
        startActivityForResult(intent,GET_ADD_CODE);
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public boolean getIsSpecial() {
        return false;
    }


    @Override
    public void getWebData() {
        if(isData)
            return;
        isData=true;
        Map<String,String> params= ComParamsAddTool.getParam();
        params.put("page","1");
        params.put("rows","100");
        params.put("userid", FtpApplication.getInstance().getUser().getId());
        mServicesTool.doPostAndalysisData("apiSidekickergroupCtrl.do?datagrid",params,GET_DATA_CODE);
    }


    /**
     * 添加分类
     */
    private void showAddView() {
        final MyAlertDialog.MyBuilder dialog1 = new MyAlertDialog.MyBuilder(getActivity());
        dialog1.setTitle("添加分类");
        dialog1.setAutoDismiss(false, true);
        LayoutInflater mLayoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View dialog_view = mLayoutInflater.inflate(
                R.layout.tool_alert_edit_text, null);
        dialog1.setContentView(dialog_view);
        final EditText editText = (EditText) dialog_view
                .findViewById(R.id.nickname_edit_aty_editor);
        editText.setTextColor(getResources().getColor(R.color.font_com_content_black_color));
        editText.setHintTextColor(getResources().getColor(R.color.com_hint_font_gray_color));
        editText.setHint("输入分类");
        editText.addTextChangedListener(new TxtLengthRestrictTool(editText, 20));
        editText.setSelection(editText.getText().length());
        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String str = editText.getText().toString().trim();
                addGroup(str);
                dialog1.dismiss();
            }
        });
        dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog1.dismiss();
            }
        });
        dialog1.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode==GET_ADD_CODE&&resultCode==1)
       {
           adapter.remove();
           getWebData();
       }
    }
}
