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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.zxw.giftbook.Activity.GroupMemberAct;
import com.zxw.giftbook.Activity.GroupMemberAddAct;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.SidekickerGroup2Adapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.PopShowDelAndEditOperateTool;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.db.JsonStrHistoryDao;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.listener.TxtLengthRestrictTool;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.MyAlertDialog;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;
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
    int mPosition=-1;
    public static final String GET_DATA_URL="apiSidekickergroupCtrl.do?datagrid";
    public static final String GET_DEL_URL="apiSidekickergroupCtrl.do?doDel";
    public static final String GET_EDIT_URL="apiSidekickergroupCtrl.do?doUpdate";
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
            }else if(msg.what==EDIT_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<SidekickergroupEntity>(){}.getType();
                SidekickergroupEntity retEntity=(SidekickergroupEntity)messageHandlerTool.handlerObject(msg,type,getActivity());
                if(retEntity!=null)
                {
                    SidekickergroupEntity entity=   adapter.getItem(mPosition);
                    entity.setGroupname(retEntity.getGroupname());
                    adapter.notifyDataSetChanged();
                }
            }else if(msg.what==DEL_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                int ret=messageHandlerTool.handler(msg,getActivity());
                if(ret==1)
                {
                    adapter.removeItem(mPosition);
                    adapter.notifyDataSetChanged();
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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SidekickergroupEntity entity=adapter.getItem(--position);
                Intent intent=new Intent(getActivity(), GroupMemberAct.class);
                intent.putExtra("id",entity.getId());
                intent.putExtra("groupName",entity.getGroupname());
                startActivity(intent);
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
    public void operate( String id,  String name,int poistion)
    {
        mPosition=poistion;
        PopShowDelAndEditOperateTool.show(getActivity(),id,name,  view,
        new PopShowDelAndEditOperateTool.DelAndEditCallback(){
            @Override
            public void onDelComplate(String id, String name) {
                del(id,name);
            }
            @Override
            public void onEditComplate(String id, String name) {
                showEditView(id,name);
            }
        });
    }
    void del(final String id,String name)
    {

        final MyAlertDialog.MyBuilder dialog1 = new MyAlertDialog.MyBuilder(getActivity());
        dialog1.setTitle("删除分类："+name);
        dialog1.setAutoDismiss(false, true);
        LayoutInflater mLayoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View dialog_view = mLayoutInflater.inflate(
                R.layout.tool_alert_edit_text, null);
        dialog1.setContentView(dialog_view);
        final EditText editText = (EditText) dialog_view
                .findViewById(R.id.nickname_edit_aty_editor);
        editText.setVisibility(View.GONE);
        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog1.dismiss();
                if(isData)
                    return;
                isData=true;
                Map<String,String> params= ComParamsAddTool.getParam();
                params.put("id", id);
                mServicesTool.doPostAndalysisDataCall(GET_DEL_URL, params, DEL_CODE, new IServicesCallback() {
                    @Override
                    public void onStart() {
                        ProgressDialogTool.getInstance(getActivity()).showDialog("删除....");
                        isData=false;
                    }

                    @Override
                    public void onEnd() {
                        ProgressDialogTool.getInstance(getActivity()).dismissDialog();
                        isData=false;
                    }
                });

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
    void editSubmit(String id,String updateNameStr)
    {
        if(updateNameStr.trim().length()==0)
        {
            ToastShowTool.myToastShort(getActivity(),"");
            return ;
        }
        if(isData)
            return;
        isData=true;
        Map<String,String> params= ComParamsAddTool.getParam();
        params.put("id", id);
        params.put("groupname",updateNameStr);
        mServicesTool.doPostAndalysisDataCall(GET_EDIT_URL, params, EDIT_CODE, new IServicesCallback() {
            @Override
            public void onStart() {
                ProgressDialogTool.getInstance(getActivity()).showDialog("修改....");
                isData=false;
            }

            @Override
            public void onEnd() {
                ProgressDialogTool.getInstance(getActivity()).dismissDialog();
                isData=false;
            }
        });
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
        mServicesTool.doPostAndalysisData(GET_DATA_URL,params,GET_DATA_CODE);
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


    /**
     * 编辑分类
     */
    private void showEditView(final String id, final String oldName) {
        final MyAlertDialog.MyBuilder dialog1 = new MyAlertDialog.MyBuilder(getActivity());
        dialog1.setTitle("编辑名称");
        dialog1.setAutoDismiss(false, true);
        LayoutInflater mLayoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View dialog_view = mLayoutInflater.inflate(
                R.layout.tool_alert_edit_text, null);
        dialog1.setContentView(dialog_view);
        final EditText editText = (EditText) dialog_view
                .findViewById(R.id.nickname_edit_aty_editor);
        editText.setTextColor(getResources().getColor(R.color.font_com_content_black_color));
        editText.setHintTextColor(getResources().getColor(R.color.com_hint_font_gray_color));
        editText.setHint( oldName);
        editText.addTextChangedListener(new TxtLengthRestrictTool(editText, 20));
        editText.setSelection(editText.getText().length());
        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog1.dismiss();
                String str = editText.getText().toString().trim();
                if(!str.trim().equals(oldName))
                      editSubmit(id,str);
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
       if(resultCode==1)
       {
           listLoad(mHandler);
       }
    }
}
