package pri.zxw.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形图片
 *
 */
public class CircleImageView extends ImageView {
	private Paint paint;

	public CircleImageView(Context context) {
		this(context, null);
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();

	}

	/**
	 * * 绘制圆形图片
	 * 
	 * @author caizhiming
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		try {
			Drawable drawable = getDrawable(); 
			if (null != drawable) {
				Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
				Bitmap b = getCircleBitmap(bitmap, 14);
				final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
				final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
				paint.reset();
				canvas.drawBitmap(b, rectSrc, rectDest, paint);

			} else {
				super.onDraw(canvas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * * 获取圆形图片方法
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return Bitmap
	 * @author caizhiming
	 */
	private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
		
		 try {
			pixels = 0;
			if (bitmap.getWidth() >= bitmap.getHeight()) {
				pixels = bitmap.getHeight();
			} else {
				pixels = bitmap.getWidth();
			}
			Bitmap output = Bitmap.createBitmap(pixels, pixels, Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, pixels, pixels);
			final RectF rectF = new RectF(rect);
			
			final float roundPx = pixels;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return output;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
