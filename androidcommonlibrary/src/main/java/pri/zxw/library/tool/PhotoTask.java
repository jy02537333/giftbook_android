package pri.zxw.library.tool;
import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
/**
 * 类名  
 * @author LongLiuPing
 * 选择相册  异步。 
 * 创建日期 2014-11-17
 */
public class PhotoTask extends AsyncTask<String, Bitmap, Bitmap>{
	Context context = null;
	PhotoTaskCallBack callBack = null;
	Intent data = null;
	File file;
	public PhotoTask(Context contex,Intent data,PhotoTaskCallBack callBack) {
		// TODO Auto-generated constructor stub
		this.context = contex;
		this.data = data;
		this.callBack = callBack;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		Bitmap bitmap = null;
		if(data!=null){
			Uri selectedImage = data.getData();
			if (selectedImage != null) {
				Cursor cursor = context.getContentResolver().query(selectedImage, null, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex("_data");
				String localSelectPath = cursor.getString(columnIndex);
				cursor.close();
				if (localSelectPath != null || !localSelectPath.equals("null")) {
					bitmap = BitmapUtil.compression(localSelectPath,400);
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					file = new File(localSelectPath);
				}
			}
		}
		return bitmap;
	}
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		// TODO Auto-generated method stub
		super.onPostExecute(bitmap);
		callBack.complete(bitmap,file);
	}
	public interface PhotoTaskCallBack
	{
		public void complete(Bitmap bitmap, File file);
	}
}
