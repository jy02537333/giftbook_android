package me.nereo.multi_image_selector.utils.choosepic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import me.nereo.multi_image_selector.R;


/**
 * @description 单图片选择工具初始化
 * @author 张相伟
 * @date 2016-10-14
 */
public class ImageSelectTool {
	Activity mAct;
	View mImg;
	LinearLayout ll_popup;
	PopupWindow pop;
	View parentView;
	public static final  int IMAGESELECTTOOL_TASK=9015;
	public static final  int IMAGESELECTTOOL_OPEN_PICS_CODE=2015;
	public static final  int TAKE_PICTURE=8514;
	public ImageSelectTool(Activity act)
	{
		mAct=act;
		Res.init(mAct);
		ImgScrollGridTool.maxNumber=1;
	}
	public void init(View img,View rootView)
	{
		
		mImg=img;
//		 pop = new PopupWindow(mAct);
//		 parentView=rootView;
//		 View view = mAct.getLayoutInflater().inflate(R.layout.mis_plugin_camera_item_popupwindows, null);
//			ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//			pop.setWidth(LayoutParams.MATCH_PARENT);
//			pop.setHeight(LayoutParams.WRAP_CONTENT);
//			pop.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//			pop.setFocusable(true);
//			pop.setOutsideTouchable(true);
//			pop.setContentView(view);
//
//			RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.item_popupwindows_parent_lay);
//			Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
//			Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
//			Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
//			parent.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// pop隐藏
//					pop.dismiss();
//					ll_popup.clearAnimation();
//				}
//			});
//			bt1.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					photo();
//					pop.dismiss();
//					ll_popup.clearAnimation();
//				}
//			});
//			bt2.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					//进入相册
//					Intent intent = new Intent(mAct,
//							AlbumActivity.class);
//					mAct.startActivityForResult( intent,IMAGESELECTTOOL_OPEN_PICS_CODE);
//					mAct.overridePendingTransition(R.anim.mis_a_publish_translate_in,
//							R.anim.mis_a_publish_translate_out);
//					pop.dismiss();
//					ll_popup.clearAnimation();
//				}
//			});
//			bt3.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					pop.dismiss();
//					ll_popup.clearAnimation();
//				}
//			});
			mImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
				pop.showAtLocation(parentView , Gravity.BOTTOM, 0, 0);
				}
			});
					
	}
	
	
	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	

	public  String path = "";
	 public void photo() {
		  String state = Environment.getExternalStorageState();  
          if (state.equals(Environment.MEDIA_MOUNTED)) {  
              Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");     
              mAct.startActivityForResult(getImageByCamera,TAKE_PICTURE);  
          }  
          else {  
              Toast.makeText(mAct.getApplicationContext(), "请确认已经插入SD卡", 
            		  Toast.LENGTH_LONG).show();  
          }  
	    }

	    public static boolean hasSDcard() {
	        String status = Environment.getExternalStorageState();
	        if (status.equals(Environment.MEDIA_MOUNTED)) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	
	 public void destroy()
	 {
		 mAct=null;
		 ImgScrollGridTool.mSelectPath.clear();
		 PublicWay.activityList.clear();
		 System.gc();
	 }
}