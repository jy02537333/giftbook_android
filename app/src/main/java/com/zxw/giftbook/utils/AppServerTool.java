package com.zxw.giftbook.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.zxw.giftbook.Activity.login.LoginAct;

import pri.zxw.library.tool.ServicesTool;

/**
 * Created by Administrator on 2017/3/2.
 */

public class AppServerTool extends ServicesTool {

    public AppServerTool(String base_Url, Context context, Handler handler) {
        super(base_Url, context, handler);
    }

    public void responseHandler(String responseBody, final int requestCode)
    {
     super.responseHandler( responseBody,requestCode);
    }

    @Override
    public void tokenVerifyCallback(Context context) {
        context.startActivity(new Intent(context, LoginAct.class));
    }

}
