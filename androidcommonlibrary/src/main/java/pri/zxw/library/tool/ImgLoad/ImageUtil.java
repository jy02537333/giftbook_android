package pri.zxw.library.tool.ImgLoad;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextPaint;

public class ImageUtil {
	/**
	 * 限制图片大小
	 */
	public static final double ASTRICTIMG_SIZE = 300;

	// 加水卿也可以加文字
	public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark,
			String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		// 霿Ɓ处理图片太大造成的内存超过的问题,这里我的图片很小承ۥ不写相应代码亿
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建丿ت新的和SRC长度宽度丿7的位囿
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// 圿0＿坐标弿ǋ画入src
		Paint paint = new Paint();
		// 加入图片
		if (watermark != null) {

			int ww = watermark.getWidth();
			int wh = watermark.getHeight();
			paint.setAlpha(50);
			cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);// 在src的右下角画入水印
		}
		// 加入文字
		if (title != null) {
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(22);
			cv.drawText(title, 0, 40, paint);
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return newb;
	}

	// 图片按比例大小压缩方泿
	public static Bitmap comp(Bitmap image) {
		if (image == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos丿
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 弿ǋ读入图片，此时把options.inJustDecodeBounds 设回true亿
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，承ۥ高和宽我们设置为
		float hh = 800f;// 这里设置高度丿00f
		float ww = 480f;// 这里设置宽度丿80f
		// 缩放比䀧Ա于是固定比例缩放，只用高或者宽其中丿ت数据进行计算即可
		int be = 1;// be=1表示不缩政
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩政
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩政
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false亿
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缿
	}

	/**
	 * 质量压缩
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这釿00表示不压缩，把压缩后的数据存放到baos丿
		int options = 100;
		while (baos.toByteArray().length / 1024 > ASTRICTIMG_SIZE) { // 循环判断如果压缩后图片是否大于图片限制大小kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos丿
			options -= 10;// 每次都减尿0
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream丿
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 将彩色图转换为灰度图
	 * 
	 * @param img
	 *            位图
	 * @return 返回转换好的位图
	 */
	public static Bitmap convertGreyImg(Bitmap img) {
		int width = img.getWidth(); // 获取位图的宽
		int height = img.getHeight(); // 获取位图的高

		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}

	// 图片Url保存为位图并进行缩放操作
	// 通过传入图片url获取位图方法
	public Bitmap returnBitMap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	// 通过传入位图,新的宿高比进行位图的缩放操使
	public static Drawable resizeImage(Bitmap bitmap, int w, int h) {

		// load the origial Bitmap
		Bitmap BitmapOrg = bitmap;

		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;

		// calculate the scale
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the Bitmap
		matrix.postScale(scaleWidth, scaleHeight);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);

		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return new BitmapDrawable(resizedBitmap);

	}

	/**
	 * 
	 * 获得圆角图片
	 * 
	 * 
	 * 
	 * @param bitmap
	 * 
	 * @param roundPx
	 * 
	 * @return
	 */

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		if (bitmap == null) {

			return null;

		}

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),

		bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;

		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;

	}

	/**
	 * 
	 * 获得带急ݱ的图片
	 */

	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {

		if (bitmap == null) {

			return null;

		}

		final int reflectionGap = 4;

		int width = bitmap.getWidth();

		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();

		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,

		width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,

		(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);

		canvas.drawBitmap(bitmap, 0, 0, null);

		Paint deafalutPaint = new Paint();

		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();

		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,

		bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,

		0x00ffffff, TileMode.CLAMP);

		paint.setShader(shader);

		// Set the Transfer mode to be porter duff and destination in

		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		// Draw a rectangle using the paint with our linear gradient

		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()

		+ reflectionGap, paint);

		return bitmapWithReflection;

	}

	// 添加文字
	public static Bitmap drawTextToBitmap(Bitmap bitmap, String gText) {

		if (bitmap == null) {
			return null;
		}
		Config bitmapConfig = bitmap.getConfig();
		if (bitmapConfig == null) {
			bitmapConfig = Config.ARGB_8888;
		}
		// resource bitmaps are imutable,
		// so we need to convert it to mutable one
		bitmap = bitmap.copy(bitmapConfig, true);
		Canvas canvas = new Canvas(bitmap);
		// new antialised Paint
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// text color - #3D3D3D
		paint.setColor(Color.rgb(61, 61, 61));
		// text size in pixels
		paint.setTextSize(150);
		// text shadow
		paint.setShadowLayer(1f, 0f, 1f, Color.BLACK);
		// draw text to the Canvas center
		Rect bounds = new Rect();
		paint.getTextBounds(gText, 0, gText.length(), bounds);
		// int x = (bitmap.getWidth() - bounds.width()) / 2;
		// int y = (bitmap.getHeight() + bounds.height()) / 2;
		// draw text to the bottom
		int x = (bitmap.getWidth() - bounds.width()) / 10 * 9;
		int y = (bitmap.getHeight() + bounds.height()) / 10 * 9;
		canvas.drawText(gText, x, y, paint);

		return bitmap;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight, int inSampleSize) {
		final int height = options.outHeight;
		final int width = options.outWidth;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 根据路径获得图片并压缩，返回bitmap用于显示
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, int inSampleSize) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800,
				inSampleSize);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}
	/**
	 * 图片缩放到指定大小
	 * @param bit
	 * @param maxSize
	 * @return
	 */
	public static Bitmap imageZoom(Bitmap bit,Double maxSize) {
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bit = zoomImage(bit, bit.getWidth() / Math.sqrt(i), bit.getHeight()
					/ Math.sqrt(i));
		}
		return bit;
	}
	/**
	 * 图片缩放到指定大小
	 * @param bit
	 * @return
	 */
	public static Bitmap imageZoom(Bitmap bit) {
		// 图片允许最大空间 单位：KB
				double maxSize = ASTRICTIMG_SIZE;
		return imageZoom(bit,maxSize);
	}

	/***
	 * 图片的缩放方法
	 * 
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	private static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	/**拍照后保存的路径*/
	public final static Intent getStartCameraIntent(File save_file) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(save_file));
		return intent;
	}
	/**浏览相册图片*/
	public final static Intent getStartGalleryIntent() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
		return intent;
	}

}
