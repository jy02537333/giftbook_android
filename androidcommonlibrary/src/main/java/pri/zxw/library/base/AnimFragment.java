/**  
* @Title: AnimFragment.java
* @Package com.yunzhi.e_commerce.fragment
* @Description: TODO(用一句话描述该文件做什么)
* @author A18ccms Lix 
* @date 2015-1-13 下午4:41:03
* @version V1.0  
*/ 
package pri.zxw.library.base;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import pri.zxw.library.R;

/**
 * @ClassName: AnimFragment
 * @Description: 动画Fragment
 * @author Lix
 * @date 2015-1-13 下午4:41:03
 *
 */
@SuppressLint("ValidFragment")
public class AnimFragment extends Fragment implements OnClickListener{

	private Activity mActivity;
	private View mWholeView;
	private ImageView mCancelImg;
	
	private OnFragmentDismissListener mListener;
	public interface OnFragmentDismissListener {
		public void onFragmentDismiss(); 
	}

	@SuppressLint("ValidFragment")
	public AnimFragment(Fragment listener) {
		this.mListener = (OnFragmentDismissListener) listener;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.anim_fragment_layout, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initEvents();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	private void initViews(View view) {
		mWholeView = view.findViewById(R.id.mark_layout);
		mCancelImg = (ImageView) view.findViewById(R.id.cancel_img);
	}
	
	private void initEvents() {
		mWholeView.setOnClickListener(this);
		mCancelImg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.mark_layout||v.getId()==R.id.cancel_img)
			dismissFragment();
	}
	
	private void dismissFragment() {
		if (null != mListener) {
			mListener.onFragmentDismiss();
		}
	}
}
