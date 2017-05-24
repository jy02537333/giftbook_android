package pri.zxw.library.tool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
   
public class BitmapToRoundCorner {  
	//将bitmap修改为圆角图片
	 
	 public static Bitmap toRoundCorner(Bitmap bitmap) {
			int pixels = 0;
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

		}
 
}
