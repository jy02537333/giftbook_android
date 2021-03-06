package com.zxw.giftbook.Activity.menu;


import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxw.giftbook.Activity.login.LoginAct;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.PersonalCenterAdapter;
import com.zxw.giftbook.config.ResConstants;


import pri.zxw.library.base.BaseFragment;
import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.entity.User;
import pri.zxw.library.refresh_tool.SwipeRecyclerView;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;

/**
 * 个人信息
 */
public class PersonalCenterFragment extends MyPullToRefreshBaseFragment implements
        AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = PersonalCenterFragment.class.getSimpleName();

    private Activity mActivity;
    /**
     * 是否登录过
     */
    private boolean isLogined = false;
    private View mView;
    private ImageView  titleRecentImg;
    private ImageView userImg;
    private TextView userNameTv;
    private SwipeRecyclerView gv;
    private LinearLayout  headLay;
    private int currentVersion;
    private PersonalCenterAdapter adapter;

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
        mView = inflater.inflate(R.layout.f_personal_center, container, false);

        initView(mView);
        initTool();
        setUserView();
        addListener();
        initData();

        return mView;
    }

    private void initView(View view) {
//        headLay = (LinearLayout) view.findViewById(R.id.f_personal_center_img);
        userImg=(ImageView) view.findViewById(R.id.f_personal_center_img);
        gv=(SwipeRecyclerView) view.findViewById(R.id.f_personal_center_gv);
        userNameTv = (TextView) view.findViewById(R.id.f_personal_center_name);
        createMysetting();
//       if( FtpApplication.getInstance().getUser().isLogin(getActivity()))
//       {
//           MyImgLoadTool.loadNetHeadImg(getActivity(),FtpApplication.getInstance().getUser().getPortrait(),userImg,100,100,"");
//           if(FtpApplication.getInstance().getUser().getUsername().trim().length()==0)
//           {
//               userNameTv.setText(FtpApplication.getInstance().getUser().getUserphone());
//           }else
//           userNameTv.setText(FtpApplication.getInstance().getUser().getUsername());
//       }

    }
    private void createMysetting()
    {
        adapter=new PersonalCenterAdapter(this);
        gv.setAdapter(adapter);
    }

    private void initTool() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(FragmentMyBroadcast.FRAGMENTMYBROADCAST_UPDATE);
        getActivity().registerReceiver(new FragmentMyBroadcast(), myIntentFilter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        initListener(gv,adapter, layoutManager, SwipeRecyclerView.Mode.NOT_REFRESH);
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
            MyImgLoadTool.loadNetHeadThumbnailImg(getActivity(),user.getPortrait(),userImg,80,80,"");
            if(user.getUsername()==null||user.getUsername().length()==0)
            {
                userNameTv.setText(user.getLoginname());
            }else
                userNameTv.setText(user.getUsername());
            isLogined = true;
        }else {
            userNameTv.setText("登陆更多精彩");
            userImg.setImageResource( ResConstants.HEAD_DEFAULT_ID);
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

    @Override
    public String getFragmentName() {
        return "PersonalCenterFragment";
    }

    @Override
    public boolean getIsSpecial() {
        return false;
    }

    @Override
    public void getWebData() {

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
