package pri.zxw.library.tool.ImgLoad;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import pri.zxw.library.R;
import pri.zxw.library.constant.BaseNetConfig;

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
    public static void pauseTag(Context context) {

        Glide.with(context).pauseRequests();
    }
    public static void resumeTag(Context context) {
        Glide.with(context).resumeRequests();
    }
}
