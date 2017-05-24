package com.zxw.giftbook.Activity.menu;


import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxw.giftbook.Activity.login.LoginAct;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.ResConstants;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.entity.User;
import pri.zxw.library.tool.ImgLoad.ImageLoadTool;
import pri.zxw.library.view.CircleImageView;
import pri.zxw.library.view.TitleBar;
import pri.zxw.mysetting.MySettingInfo;
import pri.zxw.mysetting.MysettingAdapter;

public class MyFragment extends Fragment implements
        AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = MyFragment.class.getSimpleName();

    private Activity mActivity;
    /**
     * 是否登录过
     */
    private boolean isLogined = false;
    private View mView;
    private ImageView  titleRecentImg;
    private CircleImageView userImg;
            ImageView genderImg;
    TitleBar titleBar;
    private TextView userNameTv;
    private LinearLayout  headLay;
    private ListView lv;
    private int currentVersion;
    private MysettingAdapter mysettingAdapter;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    /**
     * 登录
     */
    public static final int LOGIN_CHECK_CODE = 9441;
    /**
     * 完善资料
     */
    public static final int USER_OPTIMIZE_CODE = 5372;
    /**
     * 修改信息
     */
    public static final int USER_EDIT_CODE = 1647;

    // private Button changeBtn;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_tab_my, container, false);
        initTool();
        initView(mView);
        setUserView();
        addListener();
        initData();

        return mView;
    }

    private void initView(View view) {
        titleBar = (TitleBar) view.findViewById(R.id.fragment_my_title_bar);
        titleBar.setText(R.string.my_setting_login_title);
        titleBar.setLeftVisibility(View.GONE);
        headLay = (LinearLayout) view.findViewById(R.id.f_my_head_lay);
        userImg=(CircleImageView) view.findViewById(R.id.f_my_user_head_img);
        genderImg=(ImageView) view.findViewById(R.id.f_my_user_gender_img);
        userNameTv = (TextView) view.findViewById(R.id.f_my_user_name);
        lv= (ListView) view.findViewById(R.id.f_my_lv);
        createMysetting();

    }
    private void createMysetting()
    {
        List<MySettingInfo> infos=new ArrayList<>();
//        infos.add(createStore());
//        infos.add(createInfocation());

        infos.add(createSetting());
        infos.add(createLogout());
        mysettingAdapter=new MysettingAdapter(getActivity(),infos);
        lv.setAdapter(mysettingAdapter);
    }

    /**
     * 创建设置
     * @return
     */
    public MySettingInfo createSetting()
    {
        MySettingInfo info=new MySettingInfo();
        info.setItemIcon(R.mipmap.mysetting);
        info.setItemName("设置");
        info.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return info;
    }
    /**
     * 创建退出程序
     * @return
     */
    public MySettingInfo createLogout()
    {
        MySettingInfo info=new MySettingInfo();
        info.setItemIcon(R.mipmap.mysetting);
        info.setItemName("退出");
        info.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LoginAct.class);
                FtpApplication.getInstance().getUser().clearUser();
                startActivity(intent);
                getActivity().finish();
            }
        });
        return info;
    }

    private void initTool() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(FragmentMyBroadcast.FRAGMENTMYBROADCAST_UPDATE);
        getActivity().registerReceiver(new FragmentMyBroadcast(), myIntentFilter);
        imageLoader=ImageLoader.getInstance();
        options=ImageLoadTool.userHeadImgOptionsInit(options, ResConstants.HEAD_DEFAULT_ID);
    }

    private void initData() {

    }

    private void addListener() {

    }


    private void getData() {

    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

    }

    private void doResult(String result) {
        if (result == null || result.equals(""))
            return;
        else {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_OPTIMIZE_CODE && resultCode == 1) {
            setUserView();
        }else if (requestCode == LOGIN_CHECK_CODE && resultCode == 10) {
            isLogined=true;
            setUserView();
//			Intent intent = new Intent(mActivity, OptimizeUserAct.class);
//			startActivityForResult(intent, USER_OPTIMIZE_CODE);
        }

        else if (requestCode == LOGIN_CHECK_CODE && resultCode == 1) {
            isLogined=true;
            setUserView();
        }
        else if (requestCode == USER_EDIT_CODE && resultCode == 1) {
            isLogined=true;
            setUserView();
        }

    }

    public void setUserView() {
        if(mActivity==null)
            return ;
        User user = FtpApplication.getInstance().getUser();
        if (user!=null) {
            imageLoader.displayImage(user.getPortraitThumbnail(),userImg,options);

            isLogined = true;
        }else {
            userNameTv.setText("登陆更多精彩");
            userImg.setImageResource( ResConstants.HEAD_DEFAULT_ID);
            genderImg.setVisibility(View.GONE);
            isLogined = false;
        }
    }
    private void setHeadImg() {
        try {
            User user= FtpApplication.getInstance().getUser();
            Bitmap localPath =  user.getLocalHeadImgBitmap();
            if(localPath!=null)
            {
                userImg.setImageBitmap(localPath);
            }else
            {
                String imgUrl = user.getPortrait();
                if(imgUrl.equals(""))
                {
                    userImg.setImageResource(R.mipmap.not_headimg);
                }else
                {

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void clearUserView() {
//        title.setText(R.string.my_setting_login_title);
        userNameTv.setText(R.string.my_setting_login_alert_str);
        userImg.setImageResource(R.mipmap.not_headimg);
        isLogined=false;
    }
    @Override
    public void onClick(View arg0) {

    }

    @Override
    public void onDestroy() {
        // mActivity.unregisterReceiver(siteSalesCollectionReceiver);
        super.onDestroy();
    }

    public class FragmentMyBroadcast extends BroadcastReceiver
    {
        public static final String FRAGMENTMYBROADCAST_UPDATE="FRAGMENTMYBROADCAST_UPDATE";
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(FRAGMENTMYBROADCAST_UPDATE))
            {
                setUserView();
            }

        }
    }


}
