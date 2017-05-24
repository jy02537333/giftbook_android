package com.zxw.giftbook.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.zxw.giftbook.Activity.SettingAct;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;

import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.view.TitleBar;

/**
 * 功能  三个主界面的设置按钮初始化
 * Createdy 张相伟
 * 2016/11/6.
 */

public class MenuSettingViewInit {
    public static void init(TitleBar titleBar,final Activity act)
    {
        if(FtpApplication.getInstance().getUser()!=null) {
            String name = FtpApplication.getInstance().getUser().getUsername();
            if (name != null)
                titleBar.setText(name);
        }
        titleBar.setRightIcon(R.mipmap.setting);
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(act,SettingAct.class);
                act. startActivity(intent);
            }
        });
    }
}
