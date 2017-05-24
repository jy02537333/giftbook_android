package com.zxw.giftbook.Activity.menu;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zxw.giftbook.R;

import pri.zxw.library.base.BaseFragment;

/***
 * 金融超市
 * Created by Administrator on 2017/2/14.
 */

public class FinancialFragment extends BaseFragment {
View view;
WebView webView;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.f_financial, container, false);
        initView();
        initTool();
        initListener();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                wvLoad();
            }
        }, 1000);
        return view;
    }
   void initView(){
       webView=(WebView)view.findViewById(R.id.f_financial_wv);
       swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.f_financial_SwipeRefreshLayout);

    }
    void  initTool(){

    }
    void wvLoad()
    {
        swipeRefreshLayout.setRefreshing(false);
        webView.loadUrl("https://www.libugj.cn/apiFinancialSupermarketController.do?list");
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new webViewClient ());
//        webView.setWebViewClient(new WebViewClient());
    }

    void initListener(){
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wvLoad();
                    }
                }, 1000);
            }
        });
    }
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            // super.onPageFinished(view, url);
//            Toast.makeText(getActivity(), "同步成功，请稍候再试", Toast.LENGTH_SHORT)
//                    .show();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
//            Toast.makeText(getActivity(), "同步失败，请稍候再试", Toast.LENGTH_SHORT)
//                    .show();
        }
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public boolean getIsSpecial() {
        return false;
    }
}
