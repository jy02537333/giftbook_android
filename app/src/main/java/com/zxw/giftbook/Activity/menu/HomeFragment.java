package com.zxw.giftbook.Activity.menu;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import pri.zxw.library.refresh_tool.SwipeRecyclerView;
import com.zxw.giftbook.Activity.GiftMoneyAddAct;
import com.zxw.giftbook.Activity.GiftMoneyAddNewAct;
import com.zxw.giftbook.Activity.MemberSearchAct;
import com.zxw.giftbook.Activity.entitiy.MembergiftmoneyEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.HomeJournalAccountAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.DateMapUtil;


import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.refresh_tool.footerView.SimpleFooterView;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.DropDownBoxTool;
import pri.zxw.library.view.TitleBar;

/**
 * @作者 送礼记录
 * @类 首页
 * Created by lenovo on 2016-07-15.
 */
public class HomeFragment  extends MyPullToRefreshBaseFragment {
    TitleBar titleBar;
    View view;
    AppServerTool mServicesTool;
    TextView yearTv;
    TextView monthTv;
    TextView sumTv;
    String defaultYear="0";
    String defaultMonth="0";
    EditText f_home_journal_account_search_name_edit;
    HomeJournalAccountAdapter adapter;
    SwipeRecyclerView listView;
    Double sumAmount=0d;
    public static final String ADD_URL="apiMembergiftmoneyCtrl.do?doAdd";
    public static final String GET_DATA_URL="apiMembergiftmoneyCtrl.do?getList";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {
//                if(!HomeFragment.super.getUpfalg())
//                {
//                    listView.stopLoadingMore();
//                    adapter.notifyDataSetChanged();
//                    ToastShowTool.myToastShort(getActivity(),"停止加载！");
//                    return ;
//                }
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<List<MembergiftmoneyEntity>>(){}.getType();
                MessageHandlerTool.MessageInfo msgInfo = messageHandlerTool.handler(msg,HomeFragment.this,adapter,type);
               String sum=  msgInfo.getRetMap().get("sumCount");
                try{
//                    if(getUpfalg())
//                    {
                        sumAmount=Double.parseDouble(sum);
//                    }else
//                        sumAmount=sumAmount+Double.parseDouble(sum);
                }catch (Exception e){

                }
//                if(sum!=null)
//                {
//                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("合计："+sum+" 元");
//                  int color=  getResources().getColor(R.color.com_font_money_red);
//                    spannableStringBuilder.setSpan(new ForegroundColorSpan(color), 3, 3+sum.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    sumTv.setText(spannableStringBuilder);
//                }else if(getUpfalg())
//                {
//                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("合计："+0+" 元");
//                    int color=  getResources().getColor(R.color.com_font_money_red);
//                    spannableStringBuilder.setSpan(new ForegroundColorSpan(color), 3, 3+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    sumTv.setText(spannableStringBuilder);
//                }
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("合计："+sumAmount+" 元");
                int color=  getResources().getColor(R.color.com_font_money_red);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color), 3, 3+sumAmount.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sumTv.setText(spannableStringBuilder);
                isSub=false;
            }
            else if(msg.what==LOAD_CODE)
            {
                listView.setRefreshing(true);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.f_home_journal_account, container, false);
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
        f_home_journal_account_search_name_edit
                =(EditText) view.findViewById(R.id.f_home_journal_account_search_name_edit);
        f_home_journal_account_search_name_edit.setInputType(InputType.TYPE_NULL);
        titleBar=(TitleBar) view.findViewById(R.id.f_home_journal_account_title_bar);
        listView=(SwipeRecyclerView)view.findViewById(R.id.f_home_journal_account_lv);
        Drawable top_edit=getResources().getDrawable(R.mipmap.top_edit);
        top_edit.setBounds(0, 0, top_edit.getMinimumWidth(), top_edit.getMinimumHeight());
        titleBar.setRightDrawable(top_edit,null,null,null);
        yearTv=(TextView) view.findViewById(R.id.f_home_journal_account_year_tv);
         monthTv=(TextView) view.findViewById(R.id.f_home_journal_account_month_tv);
         sumTv=(TextView) view.findViewById(R.id.f_home_journal_account_sum_tv);
        yearTv.setText(Calendar.getInstance().get(Calendar.YEAR)+"年");
        yearTv.setTag(Calendar.getInstance().get(Calendar.YEAR));
        defaultYear= Calendar.getInstance().get(Calendar.YEAR)  +"";
        monthTv.setText("1-12月");
        monthTv.setTag(defaultMonth);

    }
    void initTool()
    {
        mServicesTool=new AppServerTool(NetworkConfig.api_url,getActivity(),mHandler);
        adapter=new HomeJournalAccountAdapter(this);
        listView.setAdapter(adapter);
        this.initListener(listView,adapter);
        listView.setFooterView(new SimpleFooterView(getActivity()));

    }
    public void initListener()
    {
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), GiftMoneyAddAct.class);
                startActivityForResult(intent,GET_ADD_CODE);
            }
        });
        yearTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DropDownBoxTool tool = new DropDownBoxTool();
                tool.showDialog("选择年", DateMapUtil.getYearMap(), 1,
                        getActivity(), yearTv, new DropDownBoxTool.Callback() {
                            @Override
                            public void complate(String key, String value) {
                                if(defaultYear.equals(key))
                                    return ;
                                defaultYear=key;
                                listView.setRefreshing(true);
                            }
                        });
            }
        });
        monthTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DropDownBoxTool tool = new DropDownBoxTool();
                tool.showDialog("选择月", DateMapUtil.getMonthMap(), 1,
                        getActivity(), monthTv, new DropDownBoxTool.Callback() {
                            @Override
                            public void complate(String key, String value) {
                                if(defaultMonth.equals(key))
                                    return ;
                                defaultMonth=key;
                                listView.setRefreshing(true);
                            }
                        });
            }
        });
        f_home_journal_account_search_name_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MemberSearchAct.class);
                startActivityForResult(intent,1111);
            }
        });
    }


    @Override
    public void getWebData() {
        Map<String,String> params= ComParamsAddTool.getPageParam(this);
        params.put("isexpenditure","1");
//        if(!yearTv.getTag().toString().equals("0"))
//            params.put("year",yearTv.getTag().toString());
//        else
//            params.put("year","0");
//        if(!monthTv.getTag().toString().equals("0"))
//            params.put("month",monthTv.getTag().toString());
//        else
//            params.put("month","0");
        params.put("name",f_home_journal_account_search_name_edit.getText().toString());
        params.put("createBy", FtpApplication.getInstance().getUser().getId());
        params.put("getCount","10");
        if(isSub)
            return ;
        isSub=true;
        mServicesTool.doPostAndalysisData(GET_DATA_URL,params,GET_DATA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==GET_ADD_CODE&&resultCode==1)
        {
            listView.setRefreshing(true);
        }
        if(requestCode==1111&&resultCode==1)
        {
           // memberId=data.getStringExtra("giftMoneyUserId");
            f_home_journal_account_search_name_edit.setText(data.getStringExtra("giftMoneyUserName"));
            listLoad(mHandler);
        }
    }
}
