package pri.zxw.library.tool;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ProgressDialogTool {
	private static ProgressDialogTool mDialogTool = null;
	private ProgressDialog mDialog;
	private static List<ProgressDialog> mDialogs=new ArrayList<ProgressDialog>();
	private static Activity mContext;
	private static boolean mIsBackKey;
	private static boolean mIsOkKey=false;
	/**
	 * 是否显示状态
	 */
	private static boolean isShow=false;
	@SuppressWarnings("deprecation")
	private ProgressDialogTool(Activity context) {
		mDialog = new ProgressDialog(context);
		mDialogs.add(  mDialog );
		mContext=context;
		// 是否可以按回退键取消
		if(mIsBackKey)
		mDialog.setCancelable(true);
		// 设置mProgressDialog的一个Button
		if(mIsOkKey)
		mDialog.setButton("确定", new DialogInterface.OnClickListener()
		{
		  @Override
		  public void onClick(DialogInterface dialog, int which)
		  {
		    dialog.cancel();
		   }
		});
	}
	public static ProgressDialogTool getInstance(Activity context,boolean isBackKey,boolean isOkKey) {
		mIsBackKey=isBackKey;
		mIsOkKey=isOkKey;
		if (mDialogTool == null) {
			mDialogTool = new ProgressDialogTool(context);  
		}else if(mContext!=context)
		{
			mDialogTool.destroyDialog();
			mDialogTool = new ProgressDialogTool(context);  
		}
		
		return mDialogTool;  
	}

	public static ProgressDialogTool getInstance(Activity context) {
		return getInstance(context,true, false);
	}

	@SuppressWarnings("null")
	public void showDialog(String message) {
		if (message != null || !message.equals("")) {
			mDialog.setMessage(message);
		} else {
			mDialog.setMessage("");
		}
		isShow=true;
		mDialog.show();
	}
	public void canelClick(final OnCanelClick canelClick)
	{
		mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				canelClick.onComplate();
			}
		});
	}

	/**
	 * 是否显示
	 * @return
     */
	public static boolean isShow()
	{
		return isShow;
	}
	public  void dismissDialog() {
		isShow=false;
		for (ProgressDialog item : mDialogs) {
			if(item!=null)
			{
				if(item.getContext().isRestricted())
				{

				}
				item.dismiss();

			}

		}
		if(mDialogs.size()==0)
		{
			mDialog.dismiss();
			
		}
		if(mDialogs!=null)
		mDialogs.clear();
		mContext=null;
		mDialogTool = null;
	}
	public static void dismissDialog(Activity context) {
     if(isShow())
		 getInstance(context).dismissDialog();
	}


	public boolean getIsShow()
	{
		return isShow;
	}
	public void destroyDialog() {
		dismissDialog();
		
	}

	public interface OnCanelClick{
		public void onComplate();
	}
}
