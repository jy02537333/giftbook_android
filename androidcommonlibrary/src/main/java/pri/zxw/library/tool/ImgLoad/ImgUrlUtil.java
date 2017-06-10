package pri.zxw.library.tool.ImgLoad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pri.zxw.library.constant.BaseNetConfig;

/**
 * 功能
 * Createdy 张相伟
 * 2017/4/15.
 */

public class ImgUrlUtil {
    public static final String IMG_BASE_URL= BaseNetConfig.img_url;
    /**
     * 返回完整的图片路径
     * @param imgUrl
     * @return
     */
    public static String  getFullImgUrl(String imgUrl)
    {
        if(imgUrl==null||imgUrl.length()==0)
            return IMG_BASE_URL+"uploadImgs/user_default.png";
        if(imgUrl.indexOf(IMG_BASE_URL)==-1)
            imgUrl=IMG_BASE_URL+imgUrl;
        if(imgUrl.indexOf("http://")==-1)
            imgUrl="http://"+imgUrl;
        return imgUrl;
    }




    /**
     * 返回我的任务方头像图片
     * @param imgUrl
     * @return
     */
    public static String  getMyTaskFullImgUrl(String imgUrl)
    {
        if(imgUrl.indexOf("http://")!=-1)
            return imgUrl;
        if(imgUrl==null||imgUrl.length()==0)
            return IMG_BASE_URL+"uploadImgs/myTaskuserimg.jpg";
        if(imgUrl.indexOf(IMG_BASE_URL)==-1)
            imgUrl=IMG_BASE_URL+imgUrl;
        if(imgUrl.indexOf("http://")==-1)
            imgUrl="http://"+imgUrl;
        return imgUrl;
    }
    /**
     * 头像调用
     * @param imgUrl
     * @return
     */
    public static String  getFullHeadImgUrl(String imgUrl)
    {
        if(imgUrl==null)
        {
            return IMG_BASE_URL+"uploadImgs/icon_user.png";
        }
        if(imgUrl.indexOf("http://")!=-1)
            return imgUrl;
        if(imgUrl==null||imgUrl.length()==0)
            return IMG_BASE_URL+"uploadImgs/icon_user.png";
        if(imgUrl.indexOf(IMG_BASE_URL)==-1)
            imgUrl=IMG_BASE_URL+imgUrl;
        if(imgUrl.indexOf("http://")==-1)
            imgUrl="http://"+imgUrl;
        return imgUrl;
    }

    /**
     * 单位头像调用
     * @param imgUrl
     * @return
     */
    public static String  getFullUnitImgUrl(String imgUrl)
    {
        if(imgUrl.indexOf("http://")!=-1)
            return imgUrl;
        if(imgUrl==null||imgUrl.length()==0)
            return IMG_BASE_URL+"uploadImgs/img_square.jpg";
        if(imgUrl.indexOf(IMG_BASE_URL)==-1)
            imgUrl=IMG_BASE_URL+imgUrl;
        if(imgUrl.indexOf("http://")==-1)
            imgUrl="http://"+imgUrl;
        return imgUrl;
    }


    public static String getSmall(String imageUrl,int type)
    {

        //String imageUrl = "uploadImgs/20161205/temp/b8788db87ad1417b8412de7acc2b0fcd_480_320.jpg";
        //	String imageUrl = "uploadImgs/20161205/b8788db87ad1417b8412de7acc2b0fcd.jpg";
        Pattern p = Pattern.compile("uploadImgs\\/\\d{8}\\/");
        Matcher m = p.matcher(imageUrl);
        if (m.find()) {
            String prefix = m.group();
            String tempStr = imageUrl.replace(prefix, prefix + "temp/");
            String suffix = imageUrl.substring(imageUrl.lastIndexOf("."));

            if(type==1)
            {
                imageUrl=tempStr.replace(suffix, "_320_240" + suffix);
            }else if(type==2)
            {
                imageUrl=tempStr.replace(suffix, "_480_320" + suffix);
            }else if(type==3)
            {
                imageUrl=tempStr.replace(suffix, "_720_480" + suffix);
            }

        }
        return imageUrl;
    }
}
