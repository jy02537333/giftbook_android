package pri.zxw.library.async;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pri.zxw.library.tool.ImgLoad.ImageUtil;


import pri.zxw.library.tool.NetUtil;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class UploadImageTask extends AsyncTask<String, String, String> {
	public interface UploadImageTaskCallback {
		public void success(Bitmap bitmap, String result);
	}

	File file;
	UploadImageTaskCallback callback;
	Bitmap bitmap;

	public UploadImageTask(Bitmap bitmap, File file, UploadImageTaskCallback callback) {
		// TODO Auto-generated constructor stub
		this.file = file;
		this.callback = callback;
		this.bitmap = bitmap;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result = null;
		if (file == null) {
			return "";
		}
		bitmap = ImageUtil.comp(bitmap);
		if (bitmap != null) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file.getAbsolutePath());
				if (bitmap != null) {
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 把数据写入文件
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.flush();
						fos.close();
					}
					String end = "\r\n";
					String twoHyphens = "--";
					String boundary = "*****";
					try {
						URL url = new URL(NetUtil.getBaseUrl()+"phoneWayBill/uploadPic");
						HttpURLConnection con = (HttpURLConnection) url.openConnection();
						/* 允许Input、Output，不使用Cache */
						con.setDoInput(true);
						con.setDoOutput(true);
						con.setUseCaches(false);
						/* 设置传送的method=POST */
						con.setRequestMethod("POST");
						/* setRequestProperty */
						con.setRequestProperty("Connection", "Keep-Alive");
						con.setRequestProperty("Charset", "UTF-8");
						con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
						/* 设置DataOutputStream */
						DataOutputStream ds = new DataOutputStream(con.getOutputStream());
						ds.writeBytes(twoHyphens + boundary + end);
						ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\";filename=\"" + file.getName() + "\"" + end);
						ds.writeBytes(end);
						/* 取得文件的FileInputStream */
						FileInputStream fStream = new FileInputStream(file);
						/* 设置每次写入1024bytes */
						int bufferSize = 1024;
						byte[] buffer = new byte[bufferSize];
						int length = -1;

						int fileLength = 0;
						/* 从文件读取数据至缓冲区 */
						while ((length = fStream.read(buffer)) != -1) {
							/* 将资料写入DataOutputStream中 */
							fileLength += length;
							ds.write(buffer, 0, length);
						}
						ds.writeBytes(end);
						ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
						/* close streams */
						fStream.close();
						ds.flush();
						/* 取得Response内容 */
						InputStream is = con.getInputStream();
						int ch;
						StringBuffer sb = new StringBuffer();
						while ((ch = is.read()) != -1) {
							sb.append((char) ch);
						}
						if (!sb.toString().equals("error")) {
							// 上传成功,返回服务器端存储的文件的文件名
							result = sb.toString();
						} else {
						}
						/* 关闭DataOutputStream */
						ds.close();
					} catch (Exception e) {
						Log.i("lynx", e.toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		callback.success(bitmap, result);
	}
}
