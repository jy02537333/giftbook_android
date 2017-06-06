package com.zxw.giftbook.Activity.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxw.giftbook.Activity.AddReceivingGiftsAffair2Act;
import com.zxw.giftbook.Activity.ReceivingGiftItemMoneyListAct;
import com.zxw.giftbook.Activity.entitiy.ReceivingGiftEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.ReceivesGiftAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.myinterface.IServicesCallback;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.view.TitleBar;

/**
 *  收到礼金
 * Created by Administrator on 2017/3/1.
 */

public class ReceivingGIftFragment extends MyPullToRefreshBaseFragment {

    TitleBar titleBar;
    View view;
    AppServerTool mServicesTool;
    TextView numTv,moneyTv;
    ReceivesGiftAdapter adapter;
    PullToRefreshListView listView;
    public static final String GET_DATA_URL="apiVReceivingMoneyController.do?datagrid";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<List<ReceivingGiftEntity>>(){}.getType();
                MessageHandlerTool.MessageInfo msgInfo = messageHandlerTool.handler(msg,ReceivingGIftFragment.this,adapter,listView,type);
                int num=0;
                double money=0.0d;
                if(msgInfo.getIsHashValue())
                for (ReceivingGiftEntity entity:(List<ReceivingGiftEntity>)msgInfo.getList())
                {
                    num+=entity.getNum();
                   if(entity.getSumMoney()!=null&&!entity.getSumMoney().equals(""))
                    money+=Double.parseDouble(entity.getSumMoney());
                }
                numTv.setText("收礼次数："+num+"次");
                moneyTv.setText(money+"元");
            }
            else if(msg.what==LOAD_CODE)
            {
                listView.setRefreshing(true);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.f_receiving_gift, container, false);
        initView();
        initTool();
        initListener();
        listLoad(mHandler);
        return view;
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public boolean getIsSpecial() {
        return false;
    }

    public void initView()
    {
        titleBar=(TitleBar) view.findViewById(R.id.f_receiving_gift_title_bar);
        listView=(PullToRefreshListView)view.findViewById(R.id.f_receiving_gift_lv);
        numTv=(TextView) view.findViewById(R.id.f_receiving_gift_num_tv);
        moneyTv=(TextView) view.findViewById(R.id.f_receiving_gift_money_tv);
    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,getActivity(),mHandler);
        adapter=new ReceivesGiftAdapter(getActivity());
        listView.setAdapter(adapter);
        setRows(1000);
        this.initListener(listView,adapter,PullToRefreshBase.Mode.PULL_FROM_START);
    }
    public void initListener()
    {
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AddReceivingGiftsAffair2Act.class);
                startActivityForResult(intent,GET_ADD_CODE);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReceivingGiftEntity entity=adapter.getItem(--position);
                Intent intent=new Intent(getActivity(), ReceivingGiftItemMoneyListAct.class);
                intent.putExtra("id",entity.getId());
                intent.putExtra("typeId",entity.getReceivestypeId());
                intent.putExtra("typeName",entity.getReceivestype());
                intent.putExtra("title",entity.getTitle());
                startActivityForResult(intent,ADD_CHILD_CODE);
            }
        });
    }


    @Override
    public void getWebData() {
        if(isSub)
            return ;
        Map<String,String> params= ComParamsAddTool.getPageParam(this);
        params.put("createby", FtpApplication.getInstance().getUser().getId());
        mServicesTool.doPostAndalysisDataCall(GET_DATA_URL, params, GET_DATA_CODE, new IServicesCallback() {
            @Override
            public void onStart() {
                isSub=true;
            }

            @Override
            public void onEnd() {
                isSub=false;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode==GET_ADD_CODE||requestCode==ADD_CHILD_CODE)&&resultCode==1)
        {
            listView.setRefreshing(true);
        }
    }
}
