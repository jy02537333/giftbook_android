/**  
* @Title: BaseFragment.java
* @Package com.yunzhi.e_commerce.fragment
* @Description: TODO(用一句话描述该文件做什么)
* @author A18ccms Lix 
* @date 2015-1-13 下午3:53:49
* @version V1.0  
*/ 
package pri.zxw.library.base;

import java.util.List;

import pri.zxw.library.tool.GetPhoneProperty;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ClassName: BaseFragment
 * @Description: 抽象的Fragment
 * @author Lix
 * @date 2015-1-13 下午3:53:49
 *
 */
public abstract class BaseFragment extends Fragment {
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	/**
	 * fragment name
	 */
	public abstract String getFragmentName();
	/**
	 * 判断是否特殊项
	 */
	public abstract boolean getIsSpecial();
}
