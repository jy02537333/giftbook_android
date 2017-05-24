package com.zxw.giftbook.Activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.GroupMemberAddAct;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.SidekickerGroupAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.MenuSettingViewInit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.db.JsonStrHistoryDao;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.view.TitleBar;

/**
 * 亲友团
 * Created by lenovo on 2016-07-15.
 */
public class SidekickerGroupFragment extends MyPullToRefreshBaseFragment {
    boolean isData=false;
    boolean isSubmit=false;
    View view;
    TitleBar titleBar;
    GridView gridView;
    SidekickerGroupAdapter adapter;
    AppServerTool mServicesTool;
    SwipeRefreshLayout swipeRefreshLayout;
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
                List<SidekickergroupEntity> list=( List<SidekickergroupEntity>)messageHandlerTool.handlerObject(msg,type,getActivity());
                if(list==null||list.size()==0) {
                    list=new ArrayList<>();
                }else if(list.size()>0)
                {
                    JsonStrHistoryDao dao=new JsonStrHistoryDao();
                    dao.addCache("sidekickerGroup",messageHandlerTool.jsonStr);
                }
                handlerData( list);
//                SidekickergroupEntity entity=new SidekickergroupEntity();
//                entity.setId("");
//                entity.setGroupname("添加分组");
//                entity.setAddType(1);
//                list.add(entity);
//                SidekickergroupEntity entity2=new SidekickergroupEntity();
//                entity2.setId("");
//                entity2.setAddType(2);
//                entity2.setGroupname("添加亲友");
//                list.add(entity2);
//                addEmpty(list);
//                adapter.addDataAll(list);
                isData=false;
            }else if(msg.what==GET_ADD_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
             //   Type type=new TypeToken<SidekickergroupEntity>(){}.getType();
                String id=messageHandlerTool.handlerData(msg,getActivity());
                if(id!=null)
                {
                    mAdapter.remove();
                    isSubmit=false;
                    getWebData();
//                    adapter.addData(entity);
//                    adapter.notifyDataSetChanged();
                }
            }
        }
    };
    void addEmpty(List<SidekickergroupEntity> list)
    {
        if(list.size()%SidekickerGroupAdapter.COL_NUM!=0)
        {
            SidekickergroupEntity entity3=new SidekickergroupEntity();
            entity3.setId("");
            entity3.setAddType(0);
            entity3.setGroupname("0");
            list.add(entity3);
            addEmpty(list);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.f_sidekicker_group, container, false);
        initView();
        initTool();
        initListener();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                adapter.remove();
                getWebData();
            }
        }, 1000);
        Gson gson=new Gson();
        JsonStrHistoryDao dao=new JsonStrHistoryDao();
        String str=dao.getCache("sidekickerGroup");
        if(str!=null&&str.trim().length()>0) {
            Type type = new TypeToken<List<SidekickergroupEntity>>() {            }.getType();
            List<SidekickergroupEntity> list=  gson.fromJson(str, type);
            handlerData(list);
        }
       // listLoad(mHandler);
        return view;
    }

    private void  initView()
    {
     titleBar=(TitleBar) view.findViewById(R.id.f_sidekicker_group_title_bar);
        gridView=(GridView) view.findViewById(R.id.f_sidekicker_group_gv);
        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.f_sidekicker_group_SwipeRefreshLayout);
    }
    void initTool()
    {
        MenuSettingViewInit.init(titleBar,getActivity());
        mServicesTool=new AppServerTool(NetworkConfig.api_url,getActivity(),mHandler);
        adapter=new SidekickerGroupAdapter(this);
        gridView.setAdapter(adapter);
    }
    private void  initListener()
    {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                           @Override
                    public void onRefresh() {
                           // TODO Auto-generated method stub
                           new Handler().postDelayed(new Runnable() {
                                            @Override
                                     public void run() {
                                            swipeRefreshLayout.setRefreshing(false);
                                                adapter.remove();
                                                getWebData();
                                        }
                                 }, 1000);
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
    private void handlerData(List<SidekickergroupEntity> list)
    {
                        SidekickergroupEntity entity=new SidekickergroupEntity();
                entity.setId("");
                entity.setGroupname("添加分组");
                entity.setAddType(1);
                list.add(entity);
                SidekickergroupEntity entity2=new SidekickergroupEntity();
                entity2.setId("");
                entity2.setAddType(2);
                entity2.setGroupname("添加亲友");
                list.add(entity2);
                addEmpty(list);
                adapter.addDataAll(list);
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
