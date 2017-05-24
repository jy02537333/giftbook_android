package pri.zxw.library.tool;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import Decoder.BASE64Encoder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;

public class BitmapUtil {
	/**
	 * base64字符串转化成音频
	 *
	 * @return
	 */
//	public static String GenerateVoic(ImageCache imageCache, String path, String imgStr, String url) { // 对字节数组字符串进行Base64解码并生成图片
//		if (imgStr == null) // 图像数据为空
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			// Base64解码
//			byte[] b = decoder.decodeBuffer(imgStr);
//			for (int i = 0; i < b.length; ++i) {
//				if (b[i] < 0) {// 调整异常数据
//					b[i] += 256;
//				}
//			}
//			imageCache.mDiskCache.put(url, b);
//			return path + url;
//		} catch (Exception e) {
//			return null;
//		}
//	}

	public static Bitmap bytes2Bimap(byte[] b) {
		if (b != null && b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	public static String GetImageStr(String imagDir) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		// String imgFile =
		// "d://test.jpg";//待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imagDir);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * base64字符串转化成图片
	 * 
	 * @param imageCache
	 * @param path
	 * @param imgStr
	 * @param str
	 * @return
	 */
//	public static String GenerateImage(ImageCache imageCache, String path, String imgStr, String str) { // 对字节数组字符串进行Base64解码并生成图片
//		if (imgStr == null) // 图像数据为空
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			// Base64解码
//			byte[] b = decoder.decodeBuffer(imgStr);
//			for (int i = 0; i < b.length; ++i) {
//				if (b[i] < 0) {// 调整异常数据
//					b[i] += 256;
//				}
//			}
//			BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inJustDecodeBounds = true;// 如果值设为true，那么将不返回实际的bitmap，也不给其分配内存空间，这样就避免了内存溢出。
//			Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, options);
//			int realwidth = options.outWidth;
//			int realheight = options.outHeight;
//			// 计算缩放。
//			int scal = (int) ((realheight > realwidth ? realwidth : realheight) / 200);
//			if (scal <= 0) {
//				scal = 1;
//			}
//			options.inSampleSize = scal;
//			options.inJustDecodeBounds = false;
//			bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, options);
//			imageCache.mDiskCache.put(str, bitmap);
//			return path + str;
//		} catch (Exception e) {
//			return null;
//		}
//	}

	/**
	 * 旋转图片
	 * 
	 * @param bm
	 *            原始图片
	 * @param orientationDegree
	 *            角度
	 * @return 旋转后的图片
	 */
	public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {

		Matrix m = new Matrix();
		m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
		float targetX, targetY;
		if (orientationDegree == 90) {
			targetX = bm.getHeight();
			targetY = 0;
		} else {
			targetX = bm.getHeight();
			targetY = bm.getWidth();
		}

		final float[] values = new float[9];
		m.getValues(values);

		float x1 = values[Matrix.MTRANS_X];
		float y1 = values[Matrix.MTRANS_Y];

		m.postTranslate(targetX - x1, targetY - y1);

		Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
		Paint paint = new Paint();
		Canvas canvas = new Canvas(bm1);
		canvas.drawBitmap(bm, m, paint);

		return bm1;
	}

	/**
	 * 读取照片exif信息中的旋转角度
	 * 
	 * @param path
	 *            照片路径
	 * @return角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	private Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap compression(String path,int size) {
		Bitmap bitmap = null;
		// 获取图片旋转角度
		int angle = readPictureDegree(path);

		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 如果值设为true，那么将不返回实际的bitmap，也不给其分配内存空间，这样就避免了内存溢出。
		bitmap = BitmapFactory.decodeFile(path, options);
		int realwidth = options.outWidth;
		int realheight = options.outHeight;
		// 计算缩放。
		int scal = (int) ((realheight > realwidth ? realwidth : realheight) / size);
		if (scal <= 0) {
			scal = 1;
		}
		options.inSampleSize = scal;
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, options);
		if (angle != 0) {
			// 旋转图片
			bitmap = adjustPhotoRotation(bitmap, angle);
		}
		return bitmap;
	}
	private static byte[] getFileBytes(File file) throws IOException {    
        BufferedInputStream bis = null;    
        try {    
            bis = new BufferedInputStream(new FileInputStream(file));    
            int bytes = (int) file.length();    
            byte[] buffer = new byte[bytes];    
            int readBytes = bis.read(buffer);    
            if (readBytes != buffer.length) {    
                throw new IOException("Entire file not read");    
            }    
            return buffer;    
        } finally {    
            if (bis != null) {    
                bis.close();    
            }    
        }    
	}
	public static String bitmapToString(Bitmap bitmap)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] b = stream.toByteArray();
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(b);
	}
	
	/**
	 * 压缩图片
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	
	
	
}
