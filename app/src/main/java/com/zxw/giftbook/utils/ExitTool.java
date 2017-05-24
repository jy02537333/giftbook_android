package com.zxw.giftbook.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

import com.zxw.giftbook.FtpApplication;

public class ExitTool {
//	public static void Exit(final Activity act,final UMMessageAndStatisticsTool umTool) {
//		Builder builder = new Builder(act);
//		builder.setMessage("确定要退出吗?");
//		builder.setTitle("提示");
//		builder.setPositiveButton("确认",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						if (null != AppApplication.getInstance().getUser()) {
//							JsonStrHistoryDao dao=new JsonStrHistoryDao(act);
//							SharedSaveUserTool.saveUserJson(act,AppApplication.getInstance().getUser(),dao);
//						}
//						AppApplication.getInstance().exit();
//						umTool.onDestroy(act);
//						dialog.dismiss();
//						act.finish();
//					}
//				});
//		builder.setNegativeButton("取消",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				});
//		builder.create().show();
//	}
//
	public static void Exit(final Activity act) {
		Builder builder = new Builder(act);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (null != FtpApplication.getInstance().getUser()) {

						}
						FtpApplication.getInstance().exit();
						dialog.dismiss();
						act.finish();
					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
}






