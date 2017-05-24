package me.nereo.multi_image_selector.utils.choosepic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * 选择图片记录
 * 
 * @author 张相伟
 *
 */
public class PicsSelectBimp {
	
	public static int max = 0;

//	public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>(); // 选择的图片的临时列表
//	public static void remove(String id)
//	{
//		for (ImageItem item : tempSelectBitmap) {
//			if(item.getImageId().equals(id))
//			{
//				tempSelectBitmap.remove(item);
//				break;
//			}
//		}
//	}
	public static void remove(int index )
	{
//				tempSelectBitmap.remove(index);
	}
//	public static void add(ImageItem imgItem )
//	{
//		for (ImageItem item : tempSelectBitmap) {
//			if(item.getImageId().equals(imgItem.getImageId()))
//			{
//				return;
//			}
//		}
//		tempSelectBitmap.add(imgItem);
//	}
	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	/**
	 * 
	 * @param bitmap
	 * @param max
	 * @return
	 */
	 public static Bitmap resizeImage(Bitmap bitmap, int max) {
	        return resizeImage(bitmap, max, max, false);
	    }

	    public static Bitmap resizeImage(final Bitmap bitmap, int maxW, int maxH, boolean crop) {
	        if (bitmap == null)
	            return null;

	        int width = bitmap.getWidth();
	        int height = bitmap.getHeight();
	        if (!crop && width <= maxW && height <= maxH)
	            return bitmap;

	        int x = 0, y = 0, w = maxW, h = maxH;
	        float scaleW = w / (float)width;
	        float scaleH = h / (float)height;
	        float scale = Math.max(scaleW, scaleH);

	        if (crop) {
	            if (scaleW > scaleH) {
	                x = Math.round((width * scale - maxW) / 2);
	            } else {
	                y = Math.round((height * scale - maxH) / 2);
	            }
	            Matrix matrix = new Matrix();
	            matrix.postScale(scale, scale);
	            return Bitmap.createBitmap(bitmap, x, y, w, h, matrix, true);
	        } else {
	            float aspect = width / (float)height;
	            if (aspect <= 2.0f) {
	                scale = Math.min(scaleW, scaleH);
	                w = (int)(width * scale);
	                h = (int)(height * scale);
	                float nowAspect = w / (float)maxW;
	                if (nowAspect < 0.5f) {
	                    w = Math.round(maxW * 0.5f);
	                    h = Math.round(w / aspect);
	                }
	            } else if (width > maxW && height > maxH) {
	                w = (int)(width * scale);
	                h = (int)(height * scale);
	            } else {
	                return bitmap;
	            }
	            return Bitmap.createScaledBitmap(bitmap, w, h, true);
	        }
	    }

}