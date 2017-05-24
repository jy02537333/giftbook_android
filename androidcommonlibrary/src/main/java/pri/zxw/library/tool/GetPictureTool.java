package pri.zxw.library.tool;

import java.io.File;

import pri.zxw.library.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


/**
 * 选择图片工具
 * @author Zxw
 *
 */
public class GetPictureTool {
	public static final int PHOTO_PICK_FROM_ALBUM = 1111;
	public static final int PHOTO_PICK_FROM_CAMERA = 2222;
	public static final int PHOTO_RESULT_OK=1;
	public static final int PHOTO_RESULT_BACK=0;
	
	public static String previewImgPath = null;  //本地图片的路径
	private static Bitmap mBitmap=null; //需要上传的图片
	private static int mPhotoStyle = R.id.photo_pick_from_album;// 默认从相册中选择图片
	
	private  String img_fileName=null;  //图片文件名
	private Activity act;
	private LayoutInflater mLayoutInflater;
	private RadioGroup photo_pick_group;
	
	public GetPictureTool(Activity act)
	{
		this.act=act;
	}
	/**
	 * 启动显示图片框
	 */
	public void showPictureList()
	{
		showPictureList("图片选择方式");
	}
	/**
	 * 启动显示图片框
	 */
	public void showPictureList(String titleName)
	{

		try {
			Builder dialog1 = new Builder(act);
			dialog1.setTitle(titleName);
			mLayoutInflater = (LayoutInflater) act.getSystemService(act.LAYOUT_INFLATER_SERVICE);
			View dialog_view = mLayoutInflater.inflate(
					R.layout.photo_pick_dialog_view, null);
			dialog1.setView(dialog_view);
			photo_pick_group = (RadioGroup) dialog_view
					.findViewById(R.id.photo_pick_group);
			photo_pick_group
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup parent,
								int checkedId) {
							// TODO Auto-generated method stub
							if (checkedId == R.id.photo_pick_from_album) {
								mPhotoStyle = R.id.photo_pick_from_album;
							}
							if (checkedId == R.id.photo_pick_from_camera) {
								mPhotoStyle = R.id.photo_pick_from_camera;
							}
						}
					});
			
			dialog1.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
				}
			});
			dialog1.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					addPhoto(); 
				}
			});
			dialog1.create().show();
		} catch (Exception e) {
		}
	}
	
	
	private void setPhotoStyle()
	{
		mPhotoStyle=photo_pick_group.getCheckedRadioButtonId();
	}
	
	// 添加图片
	private void addPhoto() {
		try {
			setPhotoStyle();
			// 从相册中选择图片
			if (mPhotoStyle == R.id.photo_pick_from_album) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");
				act.startActivityForResult(intent, PHOTO_PICK_FROM_ALBUM);
			}
			// 从相机中拍摄图片
			else if (mPhotoStyle == R.id.photo_pick_from_camera) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				img_fileName = "xiaochun" + System.currentTimeMillis() + ".jpg";
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory().getAbsoluteFile(), img_fileName)));
				act.startActivityForResult(intent, PHOTO_PICK_FROM_CAMERA);
			}
		} catch (Exception e) {
		Log.v("addPhoto", "选择图片异常！");
		}
	}
	
	/**
	 * 处理返回值
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public Bitmap resultPath(Integer requestCode,Integer resultCode,Intent data)
	{
		try {
			if(resultCode==PHOTO_RESULT_BACK)//但操作是返回的时候
				return null;
			switch(requestCode ){
			case  PHOTO_PICK_FROM_ALBUM:
				if(data != null){
					previewImgPath=getAbsoluteImagePath(data.getData());
					System.out.println("previewImgPath="+previewImgPath);
					mBitmap=BitmapUtil.revitionImageSize(previewImgPath);
				}
				break;
			case  PHOTO_PICK_FROM_CAMERA:
				previewImgPath=Environment.getExternalStorageDirectory().getAbsolutePath();
				Log.v("tupian", "previewImgPath="+previewImgPath);
				Log.v("tupian", "img_fileName="+img_fileName);
				previewImgPath=previewImgPath+"/"+img_fileName;
				System.out.println(previewImgPath);
				mBitmap=BitmapUtil.revitionImageSize(previewImgPath);
				break;
				
			}
		} catch (Exception e) {
		}
		return mBitmap;
	}
	
	/**
	 * 处理返回值
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public String resultLocalPath(Integer requestCode,Integer resultCode,Intent data)
	{
		previewImgPath=null;
		try {
			if(resultCode==PHOTO_RESULT_BACK)//但操作是返回的时候
				return null;
			switch(requestCode ){
			case  PHOTO_PICK_FROM_ALBUM:
				if(data != null){
					previewImgPath=getAbsoluteImagePath(data.getData());
					System.out.println("previewImgPath="+previewImgPath);
				}
				break;
			case  PHOTO_PICK_FROM_CAMERA:
				previewImgPath=Environment.getExternalStorageDirectory().getAbsolutePath();
				Log.v("tupian", "previewImgPath="+previewImgPath);
				Log.v("tupian", "img_fileName="+img_fileName);
				previewImgPath=previewImgPath+"/"+img_fileName;
				System.out.println(previewImgPath);
				break;
				
			}
		} catch (Exception e) {
		}
		
		return previewImgPath;
	}
	
	
	
	/**
	 * 取绝对路径
	 * @param uri
	 * @return
	 */
	protected String getAbsoluteImagePath(Uri uri) {
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = act.managedQuery(uri, proj, // Which columns to return
					null, // WHERE clause; which rows to return (all rows)
					null, // WHERE clause selection arguments (none)
					null); // Order-by clause (ascending by name)

			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} catch (IllegalArgumentException e) {
		}
		return "";
	}
	
}
