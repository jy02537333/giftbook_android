package pri.zxw.library.tool.ImgLoad;

import java.io.File;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import pri.zxw.library.constant.CachePathConstants;


public class ImageLoadTool {
	public static void imageLoadConfig(Context context)
	{
		File cacheDir = StorageUtils
				.getOwnCacheDirectory(context, CachePathConstants.IMG_CACHE_PATH);// 获取到缓存的目录地址
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				// .memoryCacheExtraOptions(480, 800) // max width, max
				// height，即保存的每个缓存文件的最大长宽
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null) // Can slow ImageLoader, use it carefully (Better don't
				// use it)设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				// .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 *
				// 1024)) // You can pass your own memory cache
				// implementation你可以通过自己的内存缓存实现
				// .memoryCacheSize(2 * 1024 * 1024)
				// /.discCacheSize(50 * 1024 * 1024)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5
																		// 加密
				// .discCacheFileNameGenerator(new
				// HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				 .diskCacheFileCount(500) //缓存的File数量
				.diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// .imageDownloader(new BaseImageDownloader(context, 5 * 1000,
				// 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}
	
	public static DisplayImageOptions userHeadImgOptionsInit(DisplayImageOptions  optionsUserHead
	, int defaultImgId)
	{
		optionsUserHead= new DisplayImageOptions.Builder()
		//.showImageOnLoading(defaultImgId) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(defaultImgId) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(defaultImgId) // 设置图片加载或解码过程中发生错误显示的图片
		.resetViewBeforeLoading(false) // default 设置图片在加载前是否重置、复位
		.considerExifParams(true) // default
		// .displayer(new SimpleBitmapDisplayer()) // default
		// 还可以设置圆角图片new RoundedBitmapDisplayer(20)
		.cacheInMemory(true)//设置下载的图片是否缓存在内存中  
		// .imageScaleType(ImageScaleType.EXACTLY)
		.cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中 
//		.displayer(new FadeInBitmapDisplayer(200))
		.build();
//		   .delayBeforeLoading(0)//int delayInMillis为你设置的下载前的延迟时间
//		 .displayer(new RoundedBitmapDisplayer(20))//不推荐用！！！！是否设置为圆角，弧度为多少  
     //是否图片加载好后渐入的动画时间，可能会出现闪动
		return optionsUserHead;
	}
	public static DisplayImageOptions imgOptionsInit(DisplayImageOptions  options,int drawableIntId)
	{
		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(drawableIntId) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(drawableIntId) // 设置图片Uri为空或是错误的时候显示的图片
//		.showImageOnFail(drawableIntId) // 设置图片加载或解码过程中发生错误显示的图片
		.resetViewBeforeLoading(false) // default 设置图片在加载前是否重置、复位
		.considerExifParams(true) // default
		// .displayer(new SimpleBitmapDisplayer()) // default
		// 还可以设置圆角图片new RoundedBitmapDisplayer(20)
		.cacheInMemory(true)//设置下载的图片是否缓存在内存中  
		// .imageScaleType(ImageScaleType.EXACTLY)
		.cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中 
//		   .delayBeforeLoading(0)//int delayInMillis为你设置的下载前的延迟时间
//		 .displayer(new RoundedBitmapDisplayer(20))//不推荐用！！！！是否设置为圆角，弧度为多少  
//        .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间，可能会出现闪动
		.build();
		return options;
	}
}
