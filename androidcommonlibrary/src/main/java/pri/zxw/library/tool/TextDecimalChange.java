package pri.zxw.library.tool;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @className 小数文本输入，限制最后一位是“.”。  限制没有小数的时候，低于输入的长度一位
 * @author 张相伟
 * @function 类功能
 * @createDate 2015-3-14
 * @version 1
 * @upadteMemter 2015-3-14
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class TextDecimalChange implements TextWatcher {
	/**
	 * 整数长度
	 */
	public int mLength=0;
	/**
	 * 小数长度
	 */
	private int mFractionalPartLength;
	public EditText mEditText;
	public TextDecimalChange(int length,int fractionalPartLength,EditText editText)
	{
		mLength=length;
		mEditText=editText;
		mFractionalPartLength=fractionalPartLength;
	}
	@Override
	public void afterTextChanged(Editable arg0) {
		String str=arg0.toString();
		if(str.length()>2)
		{
			str=DecimalsTool.decimalsLengthLimit(str, mLength, mFractionalPartLength);
			mEditText.removeTextChangedListener(this);
			mEditText.setText(str);
			mEditText.addTextChangedListener(this);
		}
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		
	}

}
