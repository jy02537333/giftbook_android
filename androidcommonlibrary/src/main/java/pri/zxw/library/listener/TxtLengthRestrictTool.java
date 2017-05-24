package pri.zxw.library.listener;


import pri.zxw.library.tool.StatisticalCharsTool;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @className 限制昵称的输入
 * @author 张相伟
 * @function 类功能
 * @createDate 2015-1-5
 * @version 1
 * @upadteMemter 2015-1-5
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class TxtLengthRestrictTool implements TextWatcher {
	 private CharSequence temp;
     private int editStart ;
     private int editEnd ;
     private EditText mEditText;
     private int mRestrictLength;
     private TxtLengthRestrictTool thisObject;
     /**
      * 
      * @param editText
      * @param restrictLength 要限制的长度
      */
     public TxtLengthRestrictTool(EditText editText,int restrictLength)
     {
    	 thisObject=this;
    	    mEditText=editText;
    	     mRestrictLength=restrictLength; 
     }
	@Override
	public void afterTextChanged(Editable edit) {
		String txtStr=temp.toString();
		int strLength=StatisticalCharsTool.getSemiangleCount(txtStr);
		if(strLength>mRestrictLength)
		{
			mEditText.removeTextChangedListener(this);
			int length=StatisticalCharsTool.subStringChar(txtStr, mRestrictLength);
			edit.delete(length, txtStr.length());
            mEditText.setText(edit);
            mEditText.setSelection(edit.length());
            mEditText.addTextChangedListener(thisObject);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence str, int arg1, int arg2,
			int arg3) {
		
		temp=str;
	}
	@Override
	public void onTextChanged(CharSequence str, int arg1, int arg2, int arg3) {
		
	}

}
