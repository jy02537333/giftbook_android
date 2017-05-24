package pri.zxw.library.tool;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MultipartRequest2 {

//    //参数类型
//    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//    //创建OkHttpClient实例
//    private final OkHttpClient client = new OkHttpClient();
//
//    MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
//
////遍历map中所有参数到builder
//    for (String key : map.keySet()) {
//        builder.addFormDataPart(key, map.get(key));
//    }
//
//
//    //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
//    for (String path : paths) {
//        builder.addFormDataPart("upload", null, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
//    }
//
//
//    //构建请求体
//    RequestBody requestBody = builder.build();
//
//    //构建请求
//    Request request = new Request.Builder()
//            .url(url)//地址
//            .post(requestBody)//添加请求体
//            .build();
//
////发送异步请求，同步会报错，Android4.0以后禁止在主线程中进行耗时操作
//    client.newCall(request).enqueue(new Callback() {
//        @Override
//        public void onFailure(Request request, IOException e) {
//            System.out.println("request = " + request.urlString());
//            System.out.println("e.getLocalizedMessage() = " + e.getLocalizedMessage())；
//        }
//
//        @Override
//        public void onResponse(Response response) throws IOException {
//            //看清楚是response.body().string()不是response.body().toString()
//            System.out.println("response = " + response.body().string());
//        }
//    });
}