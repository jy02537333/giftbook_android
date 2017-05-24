package pri.zxw.library.constant;

import android.os.Environment;

/**
 * 保存路径
 * @author 张相伟
 *
 */
public class CachePathConstants {
	public static final String APP_PATH_NAME="giftbook";
	public static final String SAVE_APP_PATH = 
			Environment.getExternalStorageDirectory().getPath()+"/"+APP_PATH_NAME+"/"; 
	public static final String FULL_APP_PATH_NAME = SAVE_APP_PATH+"/Cache"; 
	public static final String IMG_CACHE_PATH = APP_PATH_NAME+"/Cache"; 
	public static final String UPDATE_CACHE_PATH = SAVE_APP_PATH+"app";
}
