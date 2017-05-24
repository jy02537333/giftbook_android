package pri.zxw.library.tool.ImgLoad;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import pri.zxw.library.R;

import java.io.InputStream;


/**
 * 功能 imageview 加载mipmap工具
 * Createdy 张相伟
 * 2016/11/6.
 */

public class ImgLoadMipmapTool {

   public static final int resid=R.mipmap.winnower;
    /**
     *  给imageView 设置mitmap  ，这种方式可以减少一下，内存占用
     * @param mipmapId
     * @param img
     */
    public static void load(int mipmapId, ImageView img)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        try {
            // 获取资源图片
            if(mipmapId==0)
            mipmapId=resid;
            InputStream is = img.getContext().getResources().openRawResource(mipmapId);
           Bitmap bm= BitmapFactory.decodeStream(is, null, opt);
            img.setImageBitmap(bm);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *  给imageView 设置mitmap  ，这种方式可以减少一下，内存占用
     * @param mipmapId
     */
    public static void load(int mipmapId, View view)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        if(mipmapId==0)
            mipmapId=resid;
        try {
        	
        	Bitmap bm = BitmapFactory.decodeResource(view.getContext().getResources(),mipmapId);
        	BitmapDrawable bd = new BitmapDrawable(
        			view.getContext().getResources(), bm);
            view.setBackgroundDrawable(bd);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void load(String path,ImageView view){
        view.setImageBitmap(ImageUtil.getSmallBitmap(path,60));
    }
    /**
     *
     * @param mipmapId
     */
    public static Bitmap loadBitmap(int mipmapId, Context context)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        if(mipmapId==0)
            mipmapId=resid;
        try {
            // 获取资源图片
            InputStream is = context.getResources().openRawResource(mipmapId);
            Bitmap bm= BitmapFactory.decodeStream(is, null, opt);
            return bm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void recycleBg(View view){
    	BitmapDrawable bd = (BitmapDrawable)view.getBackground();
    	view.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
    	bd.setCallback(null);
    	bd.getBitmap().recycle();
    }
    public static void recycle(ImageView img){
    	BitmapDrawable bd = (BitmapDrawable)img.getDrawable();
    	//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
    	img.setImageResource(0);
    	img.setImageBitmap(null);
    	bd.setCallback(null);
    	bd.getBitmap().recycle();
    }
    
}
