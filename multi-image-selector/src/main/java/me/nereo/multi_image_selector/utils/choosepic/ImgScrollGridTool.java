package me.nereo.multi_image_selector.utils.choosepic;
 
 import java.util.ArrayList;
 import java.util.List;

 import android.Manifest;
 import android.annotation.SuppressLint;
 import android.app.Activity;
 import android.content.BroadcastReceiver;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.graphics.BitmapFactory;
 import android.graphics.drawable.ColorDrawable;
 import android.net.Uri;
 import android.os.Build;
 import android.os.Environment;
 import android.support.v4.app.ActivityCompat;
 import android.support.v7.app.AlertDialog;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.view.ViewGroup.LayoutParams;
 import android.view.inputmethod.InputMethodManager;
 import android.widget.AdapterView;
 import android.widget.BaseAdapter;
 import android.widget.EditText;
 import android.widget.GridView;
 import android.widget.ImageView;
 import android.graphics.Color;
 import android.widget.AdapterView.OnItemClickListener;

 import me.nereo.multi_image_selector.MultiImageSelector;
 import me.nereo.multi_image_selector.R;
 import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;

 import static android.app.Activity.RESULT_OK;

/**
  * 选择图片
  * @description 图片选择工具初始化
  * @author 张相伟
  * @date 2016-10-14
  */
 public class ImgScrollGridTool {
	public static final int REQUEST_ONE_IMAGE = 202;
	public static final int REQUEST_MULTI_IMAGE = 201;
	public static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
	public static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
	public static int maxNumber=9;
	/**
	 * 去选择图片
	 */
	public static final int OPEN_PICS_CODE=4519;
	/**
	 * 预览图片
	 */
	public static final int REVIEW_PICS_CODE=4511;
	/**执行，选择图片*/
	public static final int TAKE_PICTURE = 0x007001;
	public static ArrayList<String> mSelectPath=new ArrayList<>(9);
 	Activity mAct;
 	GridView mGridView;
 	private GridAdapter adapter;
 	EditText mContentEdit;
 	public List<ImageView> imgList;
 	View parentView;
 	public static Uri imgUri;
 	public static String savePath;
 	/**必须为static静态的!否则三星手机报错的*/
 	public static String mTakePhotoFile;
 	public static String CLEAR_PIC="clear_pic";
 	int i;
 	public ImgScrollGridTool(Activity act) {
 		mAct = act;
 		Res.init(mAct);
 	}
 
 	public void init(GridView grid, EditText contentEdit, View rootView) {
 		mSelectPath.clear();
 	      Intent intent = new Intent();  // Itent就是我们要发送的内容
           intent.setAction(CLEAR_PIC);   // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
           mAct.sendBroadcast(intent);   // 发送广播
 
 		mGridView = grid;
 		mContentEdit = contentEdit;
 		grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
 		adapter = new GridAdapter(mAct);
 		adapter.update();
 		grid.setAdapter(adapter);
 		grid.setOnItemClickListener(new OnItemClickListener() {
 
 			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
 				InputMethodManager inputMethodManager = (InputMethodManager) mAct.getSystemService(Context.INPUT_METHOD_SERVICE);
 				if (mContentEdit!=null&&inputMethodManager.isActive(mContentEdit)) {
// 					  因为是在fragment下，所以用了getView()获取view，也可以用findViewById（）来获取父控件
 					mContentEdit.requestFocus(); // 强制获取焦点，不然getActivity().getCurrentFocus().getWindowToken()会报错
 					inputMethodManager.hideSoftInputFromWindow(mAct.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
 					inputMethodManager.restartInput(mContentEdit);
 				}
 				if (arg2 == mSelectPath.size()) {
				pickImage(false);

 				} else {
 					Intent intent = new Intent(mAct, GalleryActivity.class);
 					intent.putExtra("position", "1");
 					intent.putExtra("ID", arg2);
 					mAct.startActivityForResult(intent, REVIEW_PICS_CODE);
 				}
 			}
 		});
 		LayoutParams layoutParams = grid.getLayoutParams();
 
 		float height = mAct.getResources().getDimension(R.dimen.mis_pics_select_item_width);
 		int line = maxNumber / 4 + 1;
 		layoutParams.height = (int) (line * height) + line * 5;
 		grid.setLayoutParams(layoutParams);
 	}
 

 	public void update() {
 		adapter.update();
 	}
 
 	public void notifyDataSetChanged() {
 		adapter.notifyDataSetChanged();
 	}
 
 	@SuppressLint("HandlerLeak")
 	public class GridAdapter extends BaseAdapter {
 		private LayoutInflater inflater;
 		private int selectedPosition = -1;
 		private boolean shape;
 
 
 		public boolean isShape() {
 			return shape;
 		}
 
 		public void setShape(boolean shape) {
 			this.shape = shape;
 		}
 
 		public GridAdapter(Context context) {
 			inflater = LayoutInflater.from(context);
 			imgList = new ArrayList<ImageView>();
 		}
 
 		public void update() {
// 			  loading();
 		}
 		public int getCount() {
 			if (mSelectPath.size() == maxNumber) {
 				return maxNumber;
 			}
 			return (mSelectPath.size() + 1);
 		}
 
 		public Object getItem(int arg0) {
 			return null;
 		}
 
 		public long getItemId(int arg0) {
 			return 0;
 		}
 
 		public void setSelectedPosition(int position) {
 			selectedPosition = position;
 		}
 
 		public int getSelectedPosition() {
 			return selectedPosition;
 		}
 
 		public View getView(int position, View convertView, ViewGroup parent) {
 
 			ViewHolder holder = null;
 			if (convertView == null) {
 				convertView = inflater.inflate(R.layout.mis_plugin_camera_item_published_grida, parent, false);
 				holder = new ViewHolder();
 				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
 				convertView.setTag(holder);
 			} else {
 				holder = (ViewHolder) convertView.getTag();
 			}
 			if (position == ImgScrollGridTool.mSelectPath.size()) {
 				 holder.image.setImageBitmap(BitmapFactory.decodeResource(mAct.getResources(), R.mipmap.icon_addpic_unfocused));
 				holder.image.setTag(111);
 				 holder.image.setImageDrawable( mAct.getResources().getDrawable(    R.mipmap.icon_addpic_unfocused));
 				ImgLoadMipmapTool.load(R.mipmap.icon_addpic_unfocused,holder.image);
 				if (position == maxNumber) {
 					holder.image.setVisibility(View.GONE);
 				}
 			} else {
 				try {
					ImgLoadMipmapTool.load(mSelectPath.get(position),holder.image);
					//holder.image.setImageBitmap(ImageUtil.getSmallBitmap(mSelectPath.get(position),60));
 					//holder.image.setImageBitmap(ImgScrollGridTool.mSelectPath.get(position).getBitmap());
 				} catch (OutOfMemoryError e) {
 					e.printStackTrace();
 				}
 			}
 			if(!imgList.contains(holder.image))
 				imgList.add(holder.image);
 			return convertView;
 		}
 
 		public class ViewHolder {
 			public ImageView image;
 		}
 	}
 
 	public String getString(String s) {
 		String path = null;
 		if (s == null)
 			return "";
 		for (int i = s.length() - 1; i > 0; i++) {
 			s.charAt(i);
 		}
 		return path;
 	}
 
 	public String path = "";
 
// 	public void photo() {
//
// 		String state = Environment.getExternalStorageState();
// 		if (state.equals(Environment.MEDIA_MOUNTED)) {
// 			Intent intentImg =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// 			SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
// 			String fileName=format.format(new Date(System.currentTimeMillis()));
// 			savePath=Environment.getExternalStorageDirectory()+"/"+fileName+"image.jpg";
// 			File photoFile=new File(Environment.getExternalStorageDirectory(),fileName+"image.jpg");
// 			imgUri=Uri.fromFile(photoFile);
// 			intentImg.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
// 			mAct.startActivityForResult(intentImg, TAKE_PICTURE);
// 		} else {
// 			Toast.makeText(mAct.getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
// 		}
// 	}
 
 	public static boolean hasSDcard() {
 		String status = Environment.getExternalStorageState();
 		if (status.equals(Environment.MEDIA_MOUNTED)) {
 			return true;
 		} else {
 			return false;
 		}
 	}
 	public  void clear()
 	{
  		for (ImageView img : imgList) {
  			ImgLoadMipmapTool.recycle(img);
  		}
 	}
 	public  void clearNoAddImg()
 	{
  		for (ImageView img : imgList) {
  			if(img.getTag()!=null&&(Integer)img.getTag()==111)
  				continue;
  			ImgLoadMipmapTool.recycle(img);
  		}
 	}
 
 	public void destroy() {
 		mAct = null;
 		mGridView = null;
 		clear();
		mSelectPath.clear();
 		PublicWay.activityList.clear();
 		System.gc();
 	}
 	public class ReceiveBroadCast extends BroadcastReceiver
 	{
 	        @Override
 	        public void onReceive(Context context, Intent intent)
 	        {
 	        	if(intent.getAction().equals(CLEAR_PIC))
 	        	{
 	        		clearNoAddImg();
 	        	}
 	        }
 	}
	public void pickImage(boolean isOne) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
				&& ActivityCompat.checkSelfPermission(mAct, Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
					mAct.getString(R.string.mis_permission_rationale),
					REQUEST_STORAGE_READ_ACCESS_PERMISSION);
		}else {
			boolean showCamera = true;
			int maxNum = 9;
			MultiImageSelector selector = MultiImageSelector.create(mAct);
			selector.showCamera(showCamera);
			selector.count(maxNum);
			int request_code=0;
			if (isOne) {
				request_code=REQUEST_ONE_IMAGE;
				selector.single();
			} else {
				request_code=REQUEST_MULTI_IMAGE;
				selector.multi();
			}
			selector.origin(mSelectPath);
			selector.start(mAct, request_code);
		}
	}
	private void requestPermission(final String permission, String rationale, final int requestCode){
		if(ActivityCompat.shouldShowRequestPermissionRationale(mAct, permission)){
			new AlertDialog.Builder(mAct)
					.setTitle(R.string.mis_permission_dialog_title)
					.setMessage(rationale)
					.setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ActivityCompat.requestPermissions(mAct, new String[]{permission}, requestCode);
						}
					})
					.setNegativeButton(R.string.mis_permission_dialog_cancel, null)
					.create().show();
		}else{
			ActivityCompat.requestPermissions(mAct, new String[]{permission}, requestCode);
		}
	}
	public void imgActivityResult(int resultCode,int requestCode,Intent data)
	{
		if(requestCode == ImgScrollGridTool.REQUEST_MULTI_IMAGE){
			if(resultCode == RESULT_OK){
				ImgScrollGridTool.mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
			}
		}
		this.notifyDataSetChanged();
	}
 }