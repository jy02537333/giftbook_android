package pri.zxw.library.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 获取手持设备屏幕分辨率
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年3月18日 12:59:27
 * @QQ号码 444141300
 * @Email 444141300@qq.com
 * @官网 http://www.yinlz.com
 */
public final class ScreenDisplay implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取当前手持设备的屏幕分辨率
	 * @param activity
	 * @return HashMap《String,Integer》,key=dpi密度,width宽度,height高度
	 * @作者 田应平
	 * @返回值类型 HashMap<String,Integer>
	 * @创建时间 2015-3-18 下午1:37:33 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public final static HashMap<String, Integer> getScreenDisplay(Activity activity){
		
		HashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		DisplayMetrics metric = new DisplayMetrics();
		
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		
		int width = metric.widthPixels;  // 屏幕宽度（像素）
		int height = metric.heightPixels;  // 屏幕高度（像素）
	    
	    float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
	    int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
		int dpi = 0 ;
	    
	    if((density >= 0.75 && density < 1.0 ) || (densityDpi >= 120 && densityDpi < 160)){//小分辨率
	    	dpi = 1 ;
	    }else if ((density >= 1.0 && density < 1.5 ) || (densityDpi >= 160 && densityDpi < 240)) {//中分辨率
	    	dpi = 2 ;
		}else if (density >= 1.5 || densityDpi >= 240) {//高分辨率
			dpi = 3 ;
		}else { //未知分辨率
			dpi = 0 ;
		}
	    
	    map.put("dpi",dpi);
	    map.put("width", width);
    	map.put("height", height);
    	
    	return map;
	}
	
	/**
	 * 获得屏幕宽度
	 * @return
	 */
	public static int getScreenWidth(Activity activity) {
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕高度
	 * @return
	 */
	public static int getScreenHeight(Activity activity) {
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
	
	/**
	 * px转dp--得到的是密码
	 * @param activity
	 * @return
	 * @作者 田应平
	 * @返回值类型 float
	 * @创建时间 2015-6-30 下午4:15:18 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public final static float pxTodp(Activity activity) {
		final float scale = activity.getResources().getDisplayMetrics().density;
		return scale;
	}

	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * @param activity
	 * @return
	*/
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * @param activity
	 * @return
	*/
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}
	
	/**
	 * 不改变控件位置，修改控件大小
	 * @param v
	 * @param W
	 * @param H
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015-5-26 上午11:09:51 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public static void changeWH(View v, int W, int H) {
		LayoutParams params = (LayoutParams) v.getLayoutParams();
		params.width = W;
		params.height = H;
		v.setLayoutParams(params);
	}

	/**
	 * 修改控件的高
	 * @param v
	 * @param H
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015-5-26 上午11:09:56 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public static void changeH(View v, int H) {
		LayoutParams params = (LayoutParams) v.getLayoutParams();
		params.height = H;
		v.setLayoutParams(params);
	}
	
	/**
	 * 修改整个界面所有控件的字体
	 * @param root
	 * @param path
	 * @param act
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015年5月26日 11:11:44
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public static void changeFonts(ViewGroup root, String path, Activity act) {
		// path是字体路径
		Typeface tf = Typeface.createFromAsset(act.getAssets(), path);
		for (int i = 0; i < root.getChildCount(); i++) {
			View v = root.getChildAt(i);
			if (v instanceof TextView) {
				((TextView) v).setTypeface(tf);
			} else if (v instanceof Button) {
				((Button) v).setTypeface(tf);
			} else if (v instanceof EditText) {
				((EditText) v).setTypeface(tf);
			} else if (v instanceof ViewGroup) {
				changeFonts((ViewGroup) v, path, act);
			}
		}
	}

	/**
	 * 修改整个界面所有控件的字体大小
	 * @param root
	 * @param size
	 * @param act
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015年5月26日 11:11:49 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public static void changeTextSize(ViewGroup root, int size, Activity act) {
		for (int i = 0; i < root.getChildCount(); i++) {
			View v = root.getChildAt(i);
			if (v instanceof TextView) {
				((TextView) v).setTextSize(size);
			} else if (v instanceof Button) {
				((Button) v).setTextSize(size);
			} else if (v instanceof EditText) {
				((EditText) v).setTextSize(size);
			} else if (v instanceof ViewGroup) {
				changeTextSize((ViewGroup) v, size, act);
			}
		}
	}
	
	/**
	 * 根据view来生成bitmap图片，可用于截图功能
	 * @param view
	 * @作者 田应平
	 * @返回值类型 Bitmap
	 * @创建时间 2016-2-18 下午5:10:06 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final static Bitmap getViewBitmap(View view) {
		view.clearFocus();
		view.setPressed(false);
		// 能画缓存就返回false
		boolean willNotCache = view.willNotCacheDrawing();
		view.setWillNotCacheDrawing(false);
		int color = view.getDrawingCacheBackgroundColor();
		view.setDrawingCacheBackgroundColor(0);
		if (color != 0) {
			view.destroyDrawingCache();
		}
		view.buildDrawingCache();
		Bitmap cacheBitmap = view.getDrawingCache();
		if (cacheBitmap == null) {
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
		view.destroyDrawingCache();
		view.setWillNotCacheDrawing(willNotCache);
		view.setDrawingCacheBackgroundColor(color);
		return bitmap;
	}
	
	/**
	 * 截取webView可视区域的截图
	 * @注意 webView 前提：WebView要设置webView.setDrawingCacheEnabled(true);
	 * @param webView
	 * @作者 田应平
	 * @返回值类型 Bitmap
	 * @创建时间 2016-2-18 下午5:19:38 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public final static Bitmap captureWebViewVisibleSize(WebView webView) {
		return webView.getDrawingCache();
	}
	
	/**
	 * 截取webView快照(webView加载的整个内容的大小)
	 * @param webView
	 * @作者 田应平
	 * @返回值类型 Bitmap
	 * @创建时间 2016-2-18 下午5:21:06 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final static Bitmap captureWebView(WebView webView) {
		@SuppressWarnings("deprecation")
		Picture snapShot = webView.capturePicture();
		Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),
		snapShot.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		snapShot.draw(canvas);
		return bmp;
	}
	
	/**
	 * 手机屏幕的快照
	 * @作者 田应平
	 * @返回值类型 Bitmap
	 * @创建时间 2016-2-18 下午5:29:47 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final static Bitmap captureScreen(Activity activity){
	     View cv = activity.getWindow().getDecorView();
	     Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(),Config.ARGB_8888);
	     Canvas canvas = new Canvas(bmp);
	     cv.draw(canvas);
	     return bmp;
	}
	


	/**
	 * 创建指定路径的文件夹，并返回执行情况 ture or false
	 * @param filePath
	 * @作者 田应平
	 * @返回值类型 boolean
	 * @创建时间 2016-2-18 下午5:48:35 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final static boolean createDir(final String filePath){
		File fileDir = new File(filePath); // 生成文件流对象
		boolean bRet = true;
		// 如果文件不存在，创建文件
		if (!fileDir.exists()) {
			// 获得文件或文件夹名称
			String[] aDirs = filePath.split("/");
			StringBuffer strDir = new StringBuffer();
			for (int i = 0; i < aDirs.length; i++) {
				// 获得文件上一级文件夹
				fileDir = new File(strDir.append("/").append(aDirs[i]).toString());
				// 是否存在
				if (!fileDir.exists()) {
					// 不存在创建文件失败返回FALSE
					if (!fileDir.mkdir()) {
						bRet = false;
						break;
					}
				}
			}
		}
		return bRet;
	}
}