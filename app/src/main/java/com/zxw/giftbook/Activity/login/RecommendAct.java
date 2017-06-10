package com.zxw.giftbook.Activity.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.ZxingQRImgTool;

import me.nereo.multi_image_selector.utils.choosepic.ImgScrollGridTool;
import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;
import pri.zxw.library.view.TitleBar;

/**
 * Created by Administrator on 2017/5/31.
 */

public class RecommendAct extends MyBaseActivity {
    TitleBar titleBar;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_recommend);
        titleBar=(TitleBar) findViewById(R.id.a_recommend_title_bar);
         img=(ImageView)findViewById(R.id.a_recommend_img);
        String url= NetworkConfig.api_url+""+ FtpApplication.getInstance().getUser().getLoginname();
        initListener();
        Bitmap bitmap= ImgLoadMipmapTool.loadBitmap(R.mipmap.logo,this);
        ZxingQRImgTool.createQRImage(this,url,bitmap);
    }
    void initListener()
    {
        titleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
