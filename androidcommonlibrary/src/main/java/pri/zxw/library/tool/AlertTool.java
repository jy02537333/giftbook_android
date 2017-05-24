package pri.zxw.library.tool;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class AlertTool  {
	Builder mDialog;
	public AlertTool(Context context) {
		mDialog = new Builder(context);
	}

	public  void yesOrNo(String title,final AlertToolCallBack callBack)
	{
		mDialog.setTitle(title);
	
		mDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
		mDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				callBack.complete();
			}
		});
		mDialog.create().show();
	}
	public interface AlertToolCallBack
	{
		public void complete();
	}
}
