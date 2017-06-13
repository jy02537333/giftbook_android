package pri.zxw.library.tool.ImgLoad;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import pri.zxw.library.R;

/**
 * 加载图片
 * Createdy 张相伟
 * 2017/4/13.
 */

public class MyImgLoadTool {
    /**
     * 加载本地文件图片
     * @param context
     * @param errorId
     * @param file
     * @param img
     * @param width
     * @param height
     */
    public static void loadLocalImg(Context context, File file,  int errorId, ImageView img,int width,int height,String tag)
    {
        Glide
                .with(context)
                .load(file)
                .placeholder(errorId)
//                .error(errorId)
                .override(width, height)
                .centerCrop()
                .into(img);
    }
    /**
     * 加载本地文件图片,修改成圆形
     * @param context
     * @param errorId
     * @param file
     * @param img
     * @param width
     * @param height
     */
    public static void loadLocalRoundImg(Context context, File file,  int errorId, ImageView img,int width,int height,String tag)
    {
//        Glide
//                .with(context)
//                .load(file)
//                .override(width, height)
//                .bitmapTransform(
//                        new CropCircleTransformation(context))
//                .crossFade(1000)
//                .centerCrop()
//                .into(img);
        Glide.with(context)
                .load(file)
                .error(R.mipmap.big_user_default)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(img);
    }

    /**
     * 加载网络图片
     * @param context
     * @param errorId
     * @param img
     * @param width
     * @param height
     */
    public static void loadNetImg(Context context, String path, int errorId, ImageView img,int width,int height,String tag)
    {
        Glide
                .with(context)
                .load(path)
                .placeholder(errorId)
                .error(errorId)
                .override(width, height)
                .centerCrop()
                .into(img);
    }
    /**
     * 加载网络头像图片
     * @param context
     * @param img
     * @param width
     * @param height
     */
    public static void loadNetHeadImg(Context context, String path, ImageView img,int width,int height,String tag)
    {
        path=ImgUrlUtil.getFullHeadImgUrl(path);
        Glide
                .with(context)
                .load(path)
                .placeholder(R.mipmap.big_user_default)
                .error(R.mipmap.big_user_default)
                .override(width, height)
                .centerCrop()
                .into(img);

    }
    public static void loadNetHeadThumbnailImg(Context context, String path, ImageView img,int width,int height,String tag)
    {
        path=ImgUrlUtil.getFullHeadImgUrl(path)+"?imageView2/1/w/240/h/240/q/75|imageslim";
//        Glide
//                .with(context)
//                .load(path)
//                .placeholder(R.mipmap.big_user_default)
//                .error(R.mipmap.big_user_default)
//                .bitmapTransform(new GlideCircleTransform(context))
//                .crossFade(1000)
//                .bitmapTransform(
//                        new CropCircleTransformation(context))
//                .override(width, height)
//                .centerCrop()
//                .into(img);
        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.big_user_default)
                .error(R.mipmap.big_user_default)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(img);
    }


    public static void pauseTag(Context context) {

        Glide.with(context).pauseRequests();
    }
    public static void resumeTag(Context context) {
        Glide.with(context).resumeRequests();
    }
}
