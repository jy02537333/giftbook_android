package pri.zxw.library.tool.dialogTools;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pri.zxw.library.R;
import pri.zxw.library.tool.ToolsString;

/**
 * 消息提示
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年9月11日 10:01:28
 * @QQ号码 444141300
 * @官网 http://www.yinlz.com
*/
public final class CustomDialog {
	/**显示时间长度*/
	public static final int SHOW_TIME=Toast.LENGTH_SHORT;
	private Activity activity;
	public CustomDialog(Activity activity){
		this.activity = activity ;
	}
	
	/**
	 * 错误提示
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastError(Context context,String msg) {
		if (ToolsString.isEmptyForObj(context))return;
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.dialog_text);
        ImageView image = (ImageView)view.findViewById(R.id.dialog_image);
        image.setImageResource(R.mipmap.error);
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
      //  toast.setGravity(Gravity.CENTER, 0, 190);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 错误提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastError(Activity activity,String msg) {
		if (ToolsString.isEmptyForObj(activity))return;
		Toast toast = new Toast(activity);
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.error);
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
      //  toast.setGravity(Gravity.CENTER, 0, 190);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 提示，字体为白色
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastErrorWhite(Activity activity,String msg) {
		if (ToolsString.isEmptyForObj(activity))return;
		Toast toast = new Toast(activity);
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.error);
        text.setTextColor(Color.rgb(255,255,255));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_SHORT);
     //   toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 错误
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastError(Context context,Integer msg) {
		if (ToolsString.isEmptyForObj(context))return;
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.error);
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(String.valueOf(msg));
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
    //    toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 错误
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastError(Context context,Object msg) {
		if (ToolsString.isEmptyForObj(context))return;
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.error);
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(ToolsString.isEmptyForObj(msg)?"系统提示":String.valueOf(msg));
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
      //  toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * OK提示
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:44
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastOK(Context context,String msg) {
		if (ToolsString.isEmptyForObj(context))return;
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.ok);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
      //  toast.setGravity(Gravity.CENTER, 0, 190);
        toast.setView(view);
        toast.show();
	}
	/**
	 * OK提示提示为白色字体提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:44
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastOKWhite(Activity activity,String msg) {
		if (ToolsString.isEmptyForObj(activity))return;
		Toast toast = new Toast(activity);
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.ok);
        text.setTextColor(Color.rgb(255,255,255));//字体颜色
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
     //   toast.setGravity(Gravity.CENTER, 0, 190);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * OK提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:44
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastOK(Activity activity,String msg) {
		if (ToolsString.isEmptyForObj(activity))return;
		Toast toast = new Toast(activity);
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.ok);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
     //   toast.setGravity(Gravity.CENTER, 0, 190);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * OK提示
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:44
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastOK(Context context,Integer msg) {
		if (ToolsString.isEmptyForObj(context))return;
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.ok);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(String.valueOf(msg));
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
    //    toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * OK提示
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:44
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastOK(Context context,Object msg) {
		if (ToolsString.isEmptyForObj(context))return;
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.ok);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(ToolsString.isEmptyForObj(msg)?"系统提示":String.valueOf(msg));
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
    //    toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 提示
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastTips(Context context,String msg) {
		if (ToolsString.isEmptyForObj(context))return;
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.tips);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
     //   toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastTips(Activity activity,String msg) {
		ToastTips(activity, msg,SHOW_TIME);
	}
	/**
	 * 提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastTips(Activity activity,String msg,int duration) {
		if (ToolsString.isEmptyForObj(activity))return;
		Toast toast = new Toast(activity);
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.tips);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(duration);
    //    toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 提示，字体为白色
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastTipsWhite(Activity activity,String msg) {
		if (ToolsString.isEmptyForObj(activity))return;
		Toast toast = new Toast(activity);
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.tips);
        text.setTextColor(Color.rgb(255,255,255));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_SHORT);
     //   toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 提示 Object 不能为空
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastTips(Context context,Object msg) {
		if (ToolsString.isEmptyForObj(context))return;
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.tips);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(ToolsString.isEmptyForObj(msg)?"系统提示":String.valueOf(msg));
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
     //   toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 提示
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastTips(Context context,Integer msg) {
		if (ToolsString.isEmptyForObj(context))return;
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.mipmap.tips);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(String.valueOf(msg));
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
     //   toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 提示-没有图标,黑色字体
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2015年9月11日 09:52:37
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastNoIcon(Activity activity,String msg) {
		if (ToolsString.isEmptyForObj(activity))return;
		Toast toast = new Toast(activity);
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
		ImageView image = (ImageView)view.findViewById(R.id.image);
	    image.setVisibility(View.GONE);//隐藏图标
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        text.setText(ToolsString.isEmptyForStr(msg)?"系统提示":msg);
        text.setTextSize(16);//字体大小
        toast.setDuration(SHOW_TIME);
     //   toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 提示-没有图标,黑色字体
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2015年9月11日 09:52:42
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastNoIcon(Activity activity,Object msg) {
		ToastNoIcon(activity, msg,SHOW_TIME);
	}	
	/**
	 * 提示-没有图标,黑色字体
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2015年9月11日 09:52:42
	 * @QQ号码 444141300 
	 * @主页 www.yinlz.com
	 */
	public final static void ToastNoIcon(Activity activity,Object msg,int duration)
	{
		if (ToolsString.isEmptyForObj(activity))return;
		Toast toast = new Toast(activity);
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_custom, null);
		TextView text = (TextView)view.findViewById(R.id.text);
		ImageView image = (ImageView)view.findViewById(R.id.image);
	    image.setVisibility(View.GONE);//隐藏图标
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        text.setText(ToolsString.isEmptyForObj(msg)?"系统提示":String.valueOf(msg));
        text.setTextSize(16);//字体大小
        toast.setDuration(duration);
    //    toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
		
	}
	
	/**
	 * 注意在 new Threan(new Runnable(){}).start();子线程里,调用会报错!!
	 * @param context
	 * @param msg
	 * @return
	 * @作者 田应平
	 * @返回值类型 Dialog
	 * @创建时间 2015年4月3日 19:01:08
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final static Dialog createLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.dialog_img);
		TextView tipTextView = (TextView) v.findViewById(R.id.dialog_tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setTextColor(Color.rgb(00,00,00));
		tipTextView.setText(ToolsString.isEmptyForStr(msg)?"正在处理…":msg);// 设置加载信息
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}
	
	/**
	 * 查看大图原图的动画
	 * @param context
	 * @param msg
	 * @return
	 * @作者 田应平
	 * @返回值类型 Dialog
	 * @创建时间 2015-10-25 下午1:00:28 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public final static Dialog createViewImage(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading_view_image, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.dialog_img);
		TextView tipTextView = (TextView) v.findViewById(R.id.dialog_tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setTextColor(Color.rgb(00,00,00));
		tipTextView.setText(ToolsString.isEmptyForStr(msg)?"正在处理…":msg);// 设置加载信息
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}
	
	/**
	 * 注意在 new Thread(new Runnable(){}).start();子线程里,调用会报错!!
	 * @param context
	 * @param msg
	 * @用法 dialog = CustomToast.createLoadingDialog(ActivityLogin.this, "正在登录...",R.drawable.winnower);
	 * @param resId 自定义动画的图片
	 * @作者 田应平
	 * @返回值类型 Dialog
	 * @创建时间 2015年6月19日 00:36:55
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public final static Dialog createLoadingDialog(Context context, String msg,int resId) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		//setImageDrawable(drawable);
		//setImageBitmap(bm);
		//setImageResource(resId);
		spaceshipImage.setImageResource(R.mipmap.winnower);//加载动画的图片
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setTextColor(Color.rgb(00,00,00));
		tipTextView.setText(ToolsString.isEmptyForStr(msg)?"正在处理…":msg);// 设置加载信息
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}
	
	/**
	 * 完全自定义的Toast,同法 new CustomToast(Activity).ToastTips(titleStr,msg);
	 * @param titleStr
	 * @param msg
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015-2-5 下午12:41:09 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public final void ToastTips(String titleStr,String msg){
		LayoutInflater inflater = this.activity.getLayoutInflater();
		if (ToolsString.isEmptyForObj(activity))return;
		View layout = inflater.inflate(R.layout.dialog_custom_tips, (ViewGroup)this.activity.findViewById(R.id.dialog_rootToast));
		ImageView image = (ImageView) layout.findViewById(R.id.dialog_tvImageToast);
		image.setImageResource(R.mipmap.ok);
		TextView title = (TextView) layout.findViewById(R.id.dialog_tvTitleToast);
		title.setText(TextUtils.isEmpty(titleStr)?"系统提示":titleStr);
		TextView text = (TextView) layout.findViewById(R.id.dialog_tvTextToast);
		text.setText(TextUtils.isEmpty(msg)?"恭喜,完成":msg);
		Toast toast = new Toast(activity);
	//	toast.setGravity(Gravity.CENTER | Gravity.TOP, 12, 40);
		toast.setDuration(SHOW_TIME);
		toast.setView(layout);
		toast.show();
	}
}