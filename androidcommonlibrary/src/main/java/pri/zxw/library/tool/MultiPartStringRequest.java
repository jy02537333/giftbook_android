///**
// * Copyright 2013 Mani Selvaraj
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package pri.zxw.library.tool;
//
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.Map;
//
//import pri.zxw.library.entity.FileByteEntity;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.Response.ErrorListener;
//import com.android.volley.Response.Listener;
//import com.android.volley.RetryPolicy;
//import com.android.volley.toolbox.HttpHeaderParser;
//
///**
// * MultipartRequest - To handle the large file uploads.
// * Extended from JSONRequest. You might want to change to StringRequest based on your response type.
// * @author Mani Selvaraj
// *
// */
//public class MultiPartStringRequest extends Request<String> implements MultiPartRequest{
//
//	/**
//	 * 上传文件，name相同时，使用该属性
//	 */
//	private String uploadFileName;
//
//
//	private final Listener<String> mListener;
//	/* To hold the parameter name and the File to upload */
//	private Map<String,File> fileUploads = new HashMap<String,File>();
//
//	private Map<String,FileByteEntity> fileByteUploads = new HashMap<String,FileByteEntity>();
//	/* To hold the parameter name and the string content to upload */
//	private Map<String,String> stringUploads = new HashMap<String,String>();
//	private int timeout_time=20000;
//    /**
//     * Creates a new request with the given method.
//     *
//     * @param method the request {@link Method} to use
//     * @param url URL to fetch the string at
//     * @param listener Listener to receive the String response
//     * @param errorListener Error listener, or null to ignore errors
//     */
//    public MultiPartStringRequest(int method, String url, Listener<String> listener,
//            ErrorListener errorListener) {
//        super(method, url, errorListener);
//        mListener = listener;
//
//    }
//    /**
//     * 上传文件，name相同时，使用该属性
//     * @param uploadFileName
//     */
//    public void setUploadFileName(String uploadFileName) {
//		this.uploadFileName = uploadFileName;
//	}
//    public void addFileUpload(Map<String,File> files) {
//    	fileUploads=files;
//    }
//
//    public void addStringUpload(Map<String,String> params) {
//    	stringUploads=params;
//    }
//    public void addFileByteUpload(Map<String,FileByteEntity> params) {
//    	fileByteUploads=params;
//    }
//
//    public void addFileUpload(String param,File file) {
//    	fileUploads.put(param,file);
//    }
//
//    public void addStringUpload(String param,String content) {
//    	stringUploads.put(param,content);
//    }
//
//    /**
//     * 要上传的文件
//     */
//    public Map<String,File> getFileUploads() {
//    	return fileUploads;
//    }
//
//    /**
//     * 要上传的文件byte[]
//     */
//    public Map<String,FileByteEntity> getFileByteUploads() {
//    	return fileByteUploads;
//    }
//
//    /**
//     * 要上传的参数
//     */
//    public Map<String,String> getStringUploads() {
//    	return stringUploads;
//    }
//
//
//    @Override
//    protected Response<String> parseNetworkResponse(NetworkResponse response) {
//        String parsed;
//        try {
//            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//        } catch (UnsupportedEncodingException e) {
//            parsed = new String(response.data);
//        }
//        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
//    }
//
//	@Override
//	protected void deliverResponse(String response) {
//		if(mListener != null) {
//			mListener.onResponse(response);
//		}
//	}
//
//	@Override
//    public RetryPolicy getRetryPolicy()
//    {
//	RetryPolicy retryPolicy = new DefaultRetryPolicy(
//			timeout_time, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//			DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//	return retryPolicy;
//    }
//
//	/**
//	 * 空表示不上传
//	 */
//    public String getBodyContentType() {
//        return null;
//    }
//
//    /**
//     * 上传文件，name相同时，使用该属性
//     */
//	@Override
//	public String getFileName() {
//		// TODO Auto-generated method stub
//		return uploadFileName;
//	}
//}