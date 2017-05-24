package pri.zxw.library.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pri.zxw.library.R;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.SystemBarTintManager;


/**
 * 自定义标题栏
 *
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年9月9日 10:52:57
 * @QQ号码 444141300
 * @官网 http://www.yinlz.com
 */
public final class TitleBar extends LinearLayout {

    private TitleOnClickListener mRightListener;
    private TitleOnClickListener mLeftListener;
    private TitleOnClickListener mTitleListener;
    TextView titleTv;
    TextView rightTv;
    ImageView mImg;
    ImageView mRightImg;
    RelativeLayout mLeftLay;
    Context mContext;
    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView(attrs);
        if(mLeftListener==null)
        mLeftLay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)mContext).onBackPressed();
            }
        });
    }
    public void setText(String text)
    {
        setTitle(text);
    }
    public void setText(int stringId)
    {

        setTitle( getResources().getString(stringId));
    }
    public void setText(CharSequence text)
    {
        if (text!=null)
        titleTv.setText(text);
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_title_bar, this);
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.titleBarStyle);

        String text = ta.getString(R.styleable.titleBarStyle_text);
        String rightText = ta.getString(R.styleable.titleBarStyle_right_text);
        Drawable backgroupDrawable= ta.getDrawable(R.styleable.titleBarStyle_right_backgroup);
         int leftVisibilityVal  =ta.getInt(R.styleable.titleBarStyle_left_visibility,View.GONE);
        int leftVisibility=View.GONE;
        ta.recycle();
        titleTv=(TextView) findViewById(R.id.title_bar_tv);
        rightTv=(TextView) findViewById(R.id.title_bar_right_tv);
         mImg=(ImageView) findViewById(R.id.title_bar_iv);
         mRightImg=(ImageView) findViewById(R.id.title_bar_right_iv);
        mLeftLay=(RelativeLayout) findViewById(R.id.title_left_lay);
        titleTv.setText(text);
        rightTv.setText(rightText);
        rightTv.setBackgroundDrawable(backgroupDrawable);
        if(leftVisibilityVal==View.VISIBLE)
            leftVisibilityVal=View.VISIBLE;
        else if(leftVisibilityVal==View.INVISIBLE)
            leftVisibilityVal=View.INVISIBLE;
        else
            leftVisibilityVal=View.GONE;
        mLeftLay.setVisibility(leftVisibilityVal);
    }

    public void setRightVisibility(int visibility) {
        rightTv.setVisibility(visibility);
    }
    public void setRightDrawable(Drawable left,Drawable top,Drawable right,Drawable bottom)
    {
        rightTv.setCompoundDrawables(left,top,right,bottom);
    }

    public void setLeftVisibility(int visibility) {
        findViewById(R.id.title_left_lay).setVisibility(visibility);
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) title = "";
        titleTv.setText(title);
    }

    public void setRightText(String str) {
        if (TextUtils.isEmpty(str)) return;
        rightTv.setText(str);
        rightTv.setVisibility(View.VISIBLE);
        mRightImg.setVisibility(View.GONE);
    }

    public void setRightBackgrounpDrawable(int drawableId) {
        if (drawableId == 0) return;
        rightTv.setBackgroundResource(drawableId);
        rightTv.setVisibility(View.VISIBLE);
        mRightImg.setVisibility(View.GONE);
    }

    public void setRightPadding(int left, int top, int right, int bottom) {
        ((TextView) findViewById(R.id.title_bar_right_tv)).setPadding(left, top, right, bottom);
    }

    public void setRightMargins(int left, int top, int right, int bottom) {
        RelativeLayout lay = (RelativeLayout) findViewById(R.id.title_bar_right_lay);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) lay.getLayoutParams();
        lp.setMargins(left, top, right, bottom);
        lay.setLayoutParams(lp);
    }

    public void setRightIcon(int id) {
        mRightImg.setBackgroundResource(id);
        mRightImg.setVisibility(View.GONE);
        mRightImg.setVisibility(View.VISIBLE);
    }

    public void setLeftIcon(int id) {
        findViewById(R.id.title_left_iv).setBackgroundResource(id);
    }

    public void setTitleBackground(int id) {
        findViewById(R.id.title_bar_box_lay).setBackgroundResource(id);
    }

    public void setTitleRightImg(int resId) {
        findViewById(R.id.title_bar_right_iv).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.title_bar_right_iv)).setImageResource(resId);
    }

    public void setLeftClickListener(TitleOnClickListener l) {
        if (l == null) return;
        findViewById(R.id.title_left_lay).setOnClickListener(listener);
        mLeftListener = l;
    }

    public void setRightClickListener(TitleOnClickListener l) {
        if (l == null) return;
        findViewById(R.id.title_bar_right_lay).setVisibility(View.VISIBLE);
        findViewById(R.id.title_bar_right_lay).setOnClickListener(listener);
        mRightListener = l;
    }

    public void setTitleClickListener(TitleOnClickListener l) {
        if (l == null) return;
        findViewById(R.id.title_bar_box_lay).setOnClickListener(listener);
        mTitleListener = l;
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
               if(v.getId()== R.id.title_left_lay) {
                    if (mLeftListener != null) mLeftListener.onClick(v);
                }
            if(v.getId()==R.id.title_bar_right_lay) {
                    if (mRightListener != null) mRightListener.onClick(v);
                }
            if(v.getId()== R.id.title_bar_box_lay){
                    if (mRightListener != null) mTitleListener.onClick(v);
                }
        }
    };

    /**
     * 移除标题栏左边的view,注意先后顺序,即先初始化后天调用
     *
     * @作者 田应平
     * @返回值类型 void
     * @创建时间 2016年3月31日 17:18:51
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
     */
    public final void removeTitleLeftView() {
        ((RelativeLayout) findViewById(R.id.item_title_bar_root)).removeView(findViewById(R.id.title_left_lay));
    }

    /**
     * 移除标题栏右边的view,注意先后顺序,即先初始化后天调用
     *
     * @作者 田应平
     * @返回值类型 void
     * @创建时间 2016年3月31日 17:18:36
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
     */
    public final void removeTitleRightView() {
        ((RelativeLayout) findViewById(R.id.item_title_bar_root)).removeView(findViewById(R.id.title_left_lay));
    }

    /**
     * 改变状态栏的颜色使其与APP风格一体化
     *
     * @param activity
     * @作者 田应平
     * @返回值类型 void
     * @创建时间 2015年11月16日 09:07:26
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
     */
    public final static void initSystemBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        // 使用颜色资源
        tintManager.setStatusBarTintResource(R.color.view_top_layout_bg);
    }

    @TargetApi(19)
    private final static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 默认样式
     *
     * @param activity
     * @作者 田应平
     * @返回值类型 void
     * @创建时间 2015年11月20日 10:23:48
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
     */
    public final static void defaultStyle(Activity activity) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
    }

    /**
     * 选择颜色作为状态栏的颜色
     *
     * @param activity
     * @param color
     * @作者 田应平
     * @返回值类型 void
     * @创建时间 2015年11月20日 10:26:33
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
     */
    public final static void colorStyle(Activity activity, int color) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(color);
    }
}