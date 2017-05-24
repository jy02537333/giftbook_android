package com.zxw.giftbook.Activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.MainAct;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.my_enum.LoginTypeEnum;
import com.zxw.giftbook.utils.AppServerTool;
import com.zxw.giftbook.utils.KeyboardTools;
import com.zxw.giftbook.utils.LoginUserInfoHandlerTool;

import java.util.HashMap;
import java.util.Map;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.entity.User;
import pri.zxw.library.tool.EncodeTool;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ToastShowTool;

/**
 * 登陆页面
 *
 * @Author RA
 * @Blog http://blog.csdn.net/vipzjyno1
 */
public class LoginAct extends MyBaseActivity {
    private Button canelBtn;
    private ImageView closeImg, qqImg, weiboImg, weixinImg, showPwdImg;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button loginBtn;
    private TextView registerTv;
    private TextView findPwdTv;
    private AppServerTool mServicesTool;
    private int oauthType = 0;
    private String access_token = null;
    private String uid = null;
    boolean isLoging = false;
    /**
     * 登录后用户信息
     */
    private User loginUser;
    private static final String OAUTH_SPLIT_STR = "-";
    public final static int LOGIN_CODE = 5341;
    public final static int REGISTER_CODE = 5441;
    public final static int FIND_CODE = 5446;
    private LoginUserInfoHandlerTool loginUserInfoHandlerTool;
    // 整个平台的Controller, 负责管理整个SDK的配置、操作等处理
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case LOGIN_CODE:
                    try {
                        if (msg.arg1 == 1) {
                            @SuppressWarnings("unchecked")
                            Map<String, String> map = (Map<String, String>) msg.obj;
                            String status = map.get(JsonParse.STATUS);
                            if (status.equals("1")) {
                                loginUser = loginUserInfoHandlerTool.loginedHandler(msg,passwordEdit.getText().toString());
                                ToastShowTool.myToastShort(LoginAct.this, "登录成功！");
                                Intent intent = new Intent(LoginAct.this, MainAct.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            MessageHandlerTool handlerTool = new MessageHandlerTool();
                            handlerTool.requestResultPrompt(msg, LoginAct.this, "用户名或者密码不正确！");
                            ProgressDialogTool.getInstance(LoginAct.this).dismissDialog();
                        }
                    } catch (Exception e) {
                        ProgressDialogTool.getInstance(LoginAct.this).dismissDialog();
                        e.printStackTrace();
                    }
                    isLoging = false;
                    break;
                default:
                    loginUserInfoHandlerTool.messageHandler(msg);
                    break;
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        initView();
        initTool();
        initListener();
    }

    /**
     * 初始化数据
     */
    private void initTool() {
        mServicesTool = new AppServerTool(NetworkConfig.api_url, this, mHandler);
        loginUserInfoHandlerTool = new LoginUserInfoHandlerTool
                (LoginAct.this, mServicesTool, true);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        canelBtn = (Button) findViewById(R.id.a_login_canel);
        closeImg = (ImageView) findViewById(R.id.login_close_img);
        accountEdit = (EditText) findViewById(R.id.a_login_username_edit);
        passwordEdit = (EditText) findViewById(R.id.a_login_password_edit);
        loginBtn = (Button) findViewById(R.id.a_login_btn);
        registerTv = (TextView) findViewById(R.id.a_login_register);
        findPwdTv = (TextView) findViewById(R.id.a_login_find_pwd);
        weixinImg = (ImageView) findViewById(R.id.a_login_weixin_img);
        qqImg = (ImageView) findViewById(R.id.a_login_qq_img);
        weiboImg = (ImageView) findViewById(R.id.a_login_weibo_img);
        showPwdImg = (ImageView) findViewById(R.id.a_login_show_pwd_img);
    }

    private void initListener() {
        canelBtn.setOnClickListener(new MOnClickListener());
        closeImg.setOnClickListener(new MOnClickListener());
        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                login();
            }
        });
        registerTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginAct.this,
                        RegisterAct.class), REGISTER_CODE);
            }
        });
        findPwdTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginAct.this,
                        FindPwdAct.class), FIND_CODE);
            }
        });
        weixinImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				login(SHARE_MEDIA.WEIXIN);
            }
        });
        qqImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				login(SHARE_MEDIA.QQ);
            }
        });
        weiboImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				login(SHARE_MEDIA.SINA);
            }
        });
        showPwdImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passwordEdit.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ;
                } else {
                    passwordEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ;
                }
            }
        });
    }

    private void login() {

        KeyboardTools.anastole(this);
        if (accountEdit.getText().toString().trim().length() == 0) {
            ToastShowTool.myToastShort(LoginAct.this, "请输入帐号");
            return;
        } else if (passwordEdit.getText().toString().trim().length() == 0) {
            ToastShowTool.myToastShort(LoginAct.this, "请输入密码");
            return;
        }
        User user = new User();
        user.setLoginname(accountEdit.getText().toString().trim());
        user.setLoginpassword(passwordEdit.getText().toString());
        Map<String, String> param = new HashMap<>();
        param.put("info", user.toSignString(this));
        param.put("loginname", accountEdit.getText().toString().trim());
        oauthType = 0;
        // /UserInfo/Validate
        // /UserInfo/Login
        if (isLoging)
            return;
        isLoging = true;
        ProgressDialogTool.getInstance(LoginAct.this).showDialog("登陆中...");
        mServicesTool.doPostAndalysisData("apiSysUserCtrl.do?login", param, LOGIN_CODE);
    }

    /**
     * 授权。如果授权成功，则获取用户信息</br>
     */
    private void login(final LoginTypeEnum platform) {
        ProgressDialogTool.getInstance(LoginAct.this).showDialog("开始授权...");
        if (platform.equals(LoginTypeEnum.WEIXIN)) {
            oauthType = 3;
        } else if (platform.equals(LoginTypeEnum.QQ)) {
            oauthType = 1;
        } else if (platform.equals(LoginTypeEnum.SINA)) {
            oauthType = 2;
        }
//		umLoginTool.doOauthVerify(platform, new OAuthLoginCallback() {
//			@Override
//			public void onStart(SHARE_MEDIA platform) {
////				Toast.makeText(LoginAct.this, "onStart",
////								Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onError(SocializeException e, SHARE_MEDIA platform) {
////						Toast.makeText(LoginAct.this, "onError",
////								Toast.LENGTH_SHORT).show();
//				ProgressDialogTool.getInstance(LoginAct.this)
//						.dismissDialog();
//			}
//
//			@Override
//			public void onComplete(int status, Map<String, Object> info) {
//				// String showText = "";
//				// if (status == StatusCode.ST_CODE_SUCCESSED) {
//				// showText = "用户名：" +
//				// info.get("screen_name").toString();
//				// Log.d("#########", "##########" + info.toString());
//				// } else {
//				// showText = "获取用户信息失败";
//				// }
//				// {uid=2413517143, favourites_count=0, location=贵州 贵阳,
//				// description=, verified=false,
//				// friends_count=7, gender=1, screen_name=冰_QQ,
//				// statuses_count=3, followers_count=0,
//				// profile_image_url=http://tp4.sinaimg.cn/2413517143/180/0/1,
//				// access_token=2.00rDs1dC0XzJJF2d412e107fkpoRoC}
//				//{is_yellow_year_vip=0, vip=0, level=0, province=贵州, yellow_vip_level=0, is_yellow_vip=0, gender=男, screen_name=RILWZAISI, msg=, profile_image_url=http://q.qlogo.cn/qqapp/100424468/8C8200DE558AE2261E2252C664C04CD8/100, city=贵阳}
//				if (info != null) {
//					uid = umLoginTool.uid;
//					access_token = umLoginTool.access_token;
//					Log.e("com.dxcm.news", info.toString());
//					User user = OAuthCallbackTool.getUser(info.toString(), oauthType, uid);
//					oauthLogin(user);
//				} else {
//					ProgressDialogTool.getInstance(LoginAct.this)
//							.dismissDialog();
//				}
//			}
//
//			@Override
//			public void onCancel(SHARE_MEDIA platform) {
//				ProgressDialogTool.getInstance(LoginAct.this)
//						.dismissDialog();
//			}
//		});
    }

    private void oauthLogin(User user) {
        ProgressDialogTool.getInstance(LoginAct.this).showDialog("正在登陆...");
        Map<String, String> param = new HashMap<>();
        param.put("LoginSource", oauthType + "");
        param.put("Username", user.getUsername());
        param.put("photo", user.getUserphone());
        param.put("gender", user.getSex() + "");
        if (oauthType == 1)
            param.put("QqOpenId", user.getQqopenid());
        else if (oauthType == 2)
            param.put("SinaOpenId", user.getSinaopenid());
        else if (oauthType == 3)
            param.put("WxOpenId", user.getWxopenid());
        // /UserInfo/Validate
        // /UserInfo/Login
        mServicesTool.doPostAndalysisData("UserInfo/ThirdLogin", param,
                LOGIN_CODE);

    }

    private class MOnClickListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            LoginAct.this.finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_CODE && resultCode == 1) {
            setResult(10);
            finish();
        } else {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
