package pri.zxw.library.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * @ClassName: MyHorizontalScrollView  
 * @author Lix
 * @date 2015-3-12 10:53:38
 *
 */
public class MyHorizontalScrollView extends HorizontalScrollView {

	public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	
	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}

	public MyHorizontalScrollView(Context context) {
		super(context);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
		
		return 0;
	}

}
