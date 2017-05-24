package pri.zxw.library.async;
import java.io.ByteArrayOutputStream;
import java.io.File;

import pri.zxw.library.tool.BitmapUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
/**
 * 类名  
 * @author LongLiuPing
 * 调用相机 异步返回。 
 * 创建日期 2014-11-17
 */
public class CameraTask extends AsyncTask<String, Bitmap, Bitmap>{
	File file = null;
	Context context = null;
	CameraTaskCallBack callBack = null;
	public CameraTask(Context contex,File file,CameraTaskCallBack callBack) {
		// TODO Auto-generated constructor stub
		this.context = contex;
		this.file = file;
		this.callBack = callBack;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		Bitmap bitmap = null;
		if(file!=null && file.isFile())
		{
			bitmap = BitmapUtil.compression(file.getAbsolutePath(),150);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		}
		return bitmap;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		callBack.complete(result);
	}
	public interface CameraTaskCallBack
	{
		public void complete(Bitmap bitmap);
	}
}
