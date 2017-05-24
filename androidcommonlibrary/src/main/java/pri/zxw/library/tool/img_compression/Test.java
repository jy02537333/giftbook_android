package pri.zxw.library.tool.img_compression;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能
 * Createdy 张相伟
 * 2017/4/30.
 */

public class Test {
    private List<String> mImagePathes = new ArrayList<>();
    final Map<String, File> compressedFiles = new HashMap<>();
    //线程不安全,判断是否上传完毕;压缩后文件-上传KEY
    final Map<File, String> uploadedKeys = new HashMap<>();
    public void fgg(Context context,String imgPath){
        ImageCompressUtils.from(context)
                .load(imgPath)
                .execute(new ImageCompressUtils.OnCompressListener() {
                    @Override
                    public void onSuccess(File file) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    public void iii(Context context,String imgPath)
    {
//        ImageCompressUtils.from(context)
//                .load(imgPath)
//                .execute(new ImageCompressUtils.OnCompressListener() {
//                    @Override
//                    public void onSuccess(File file) {
//                        //添加压缩结果
//                        compressedFiles.put(imgPath, file);
//                        //判断图片压缩是否已操作结束
//                        if (compressedFiles.size() == mImagePathes.size()) {
//                            for (final File resFile : compressedFiles.values()) {
//                                //并行上传图片
//                                QiniuUtils.from(MainActivity.this).queue(resFile, new QiniuUtils.UploadListener() {
//                                    @Override
//                                    public void onSuccess(File compressedFile, String key) {
//                                        //添加压缩文件对应上传key
//                                        uploadedKeys.put(compressedFile, key);
//                                        //判断图片上传是否已结束
//                                        if (uploadedKeys.size() == compressedFiles.size()) {
//                                            //图片地址参数
//                                            String img_urls = "";
//                                            for (final String imgPath : mImagePathes) {
//                                                //按照原始图片顺序排序
//                                                File temp = compressedFiles.get(imgPath);
//                                                img_urls += uploadedKeys.get(temp) + ",";
//                                            }
//                                            //TODO 完成图片上传后，你的业务逻辑
//
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(int code) {
//                                        Log.d(TAG, String.format("code is %d", code));
//                                    }
//                                });
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "图片上传失败");
//                    }
//                });
    }
}
