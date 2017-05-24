package com.zxw.giftbook.utils;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zxw.giftbook.FtpApplication;

import java.util.HashMap;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseInterface;

/**
 * 功能 公共参数添加
 * Createdy 张相伟
 * 2016/11/6.
 */

public class ComParamsAddTool {
    /**
     *  获取公共分页参数
     * @return
     */
    public static Map<String,String> getPageParam(MyPullToRefreshBaseInterface base)
    {
        Map<String,String > paramMap=getParam();
        paramMap.put("page",base.getCur_page()+"");
        return paramMap;
    }
    /**
     *  获取公共参数
     * @return
     */
    public static Map<String,String> getParam()
    {
        Map<String,String > paramMap=new HashMap<>();
        paramMap.put("token", FtpApplication.getInstance().getUser().getToken());

        return paramMap;
    }
 }
