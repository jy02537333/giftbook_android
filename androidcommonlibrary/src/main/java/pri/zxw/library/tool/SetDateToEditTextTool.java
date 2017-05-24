/**  
* @Title: SetDateToEditTextTool.java
* @Package pri.zxw.library.tool
* @Description: TODO(用一句话描述该文件做什么)
* @author A18ccms Lix 
* @date 2015-1-15 上午11:20:49
* @version V1.0  
*/ 
package pri.zxw.library.tool;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * @ClassName: SetDateToEditTextTool
 * @Description: 使用DatePickerDialog为Editext添加日期的类
 * @author Lix
 * @date 2015-1-15 上午11:20:49
 *
 */
public class SetDateToEditTextTool {
  
	private static SetDateToEditTextTool dateToEditTextTool = null;
	
	private SetDateToEditTextTool(){
	}
	
	public static SetDateToEditTextTool getInstance(){
		if(dateToEditTextTool == null)
			dateToEditTextTool = new SetDateToEditTextTool();
		return dateToEditTextTool;
	}

	
	/**
	 * 
	* @Title: setDateToEdittext
	* @Description: 为EditText设置日期
	* @param editText  设定文件
	* @return void    返回类型
	* @throws
	 */
	public void setDateToEdittext(Activity activity,final EditText editText){
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog dateDialog = new DatePickerDialog(activity, new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(arg1+"-");
				if(arg2 > 9)
					stringBuilder.append((arg2+1)+"-");
				else
					stringBuilder.append("0"+(arg2+1)+"-");
				stringBuilder.append(arg3);
				editText.setText(stringBuilder.toString());
				editText.clearFocus();
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		dateDialog.setButton2("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				editText.clearFocus();
			}
		});
		dateDialog.setTitle("请选择日期");
		dateDialog.show();
	}
}
