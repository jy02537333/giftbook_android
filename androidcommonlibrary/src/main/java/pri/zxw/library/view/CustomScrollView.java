package pri.zxw.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ScrollView;

/**
 * @author 龙流平(LongLiuPing)
 * @version 创建时间：2015年2月28日 上午9:23:18 package pri.zxw.library.view;
 */
public class CustomScrollView extends ScrollView {

	private GestureDetector mGestureDetector;

	public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CustomScrollView(Context context) {
		super(context);
		init();
	}

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mGestureDetector = new GestureDetector(getContext(), new YScrollDetector());
		setFadingEdgeLength(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
	}

	private class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
	}
}