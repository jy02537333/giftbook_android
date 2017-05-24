package pri.zxw.library.view;

import java.text.DecimalFormat;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author 龙流平(LongLiuPing)
 * @version 创建时间：2015年3月25日 上午10:47:19 package pri.zxw.library.view;
 */
public class DecimalEditText extends EditText implements TextWatcher {


	public DecimalEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		DecimalFormat format = new DecimalFormat("##.##");
		String formatted = format.format(s.toString());
		this.setText(formatted);
	}
}
