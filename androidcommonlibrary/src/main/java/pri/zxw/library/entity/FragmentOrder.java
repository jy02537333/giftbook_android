package pri.zxw.library.entity;

import pri.zxw.library.base.BaseFragment;



/**
 * @className 包含fragment的排序与角色
 * @author 张相伟
 * @function 类功能
 * @createDate 2015-1-16
 * @version 1
 * @upadteMemter 2015-1-16
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class FragmentOrder implements  Comparable<FragmentOrder>{
	private  int mOrderKey;
	private  String mRoleStr;
	/**图片id*/
	private int mDrawableId;
	/**操作图片id*/
	private int mDrawableOperateId;
	private Class mFragmentValue;
	private CharSequence mName;
	
	
	public FragmentOrder(int mOrderKey, String mRoleStr, int mDrawableId,
			int drawableOperateId,
			Class mFragmentValue, String mName) {
		super();
		this.mOrderKey = mOrderKey;
		this.mRoleStr = mRoleStr;
		this.mDrawableId = mDrawableId;
		this.mFragmentValue = mFragmentValue;
		mDrawableOperateId=drawableOperateId;
		this.mName = mName;
	}
	/**
	 * @return 名称
	 */
	public CharSequence getName() {
		return mName;
	}
	/**
	 * 名称
	 * @param 名称
	 */
	public void setName(CharSequence mName) {
		this.mName = mName;
	}
	/**
	 * @return 图片id
	 */
	public int getDrawableId() {
		return mDrawableId;
	}
	/**
	 * @param 图片id
	 */
	public void setDrawableId(int mDrawableId) {
		this.mDrawableId = mDrawableId;
	}
	/**
	 * @param 操作图片id
	 */
	public void setDrawableOperateId(int drawableOperateId) {
		this.mDrawableOperateId = drawableOperateId;
	}
	/**
	 * @return 操作图片id
	 */
	public int getDrawableOperateId() {
		return mDrawableOperateId;
	}

	
	
	/**
	 * fragment所属角色
	 * @return
	 */
	public String getRoleStr() {
		return mRoleStr;
	}
	/**
	 * fragment所属角色
	 * @return
	 */
	public void setRoleStr(String roleStr) {
		this.mRoleStr = roleStr;
	}
	/**
	 * 排序
	 * @param orderKey
	 */
	public void setOrderKey(int orderKey)
	{
		mOrderKey=orderKey;
	}
	/**
	 * 排序
	 * @param orderKey
	 */
	public int getOrderKey()
	{
		return mOrderKey;
	}
	/**
	 * Fragment
	 * @param fragmentValue
	 */
	public void setFragmentValue(Class<BaseFragment> fragmentValue)
	{
		mFragmentValue=fragmentValue;
	}
	/**
	 * Fragment
	 * @param fragmentValue
	 */
	public Class<BaseFragment> getFragmentValue()
	{
		return mFragmentValue;
	}
	@Override
	public int compareTo(FragmentOrder o) {
	    if (this.mOrderKey > o.getOrderKey()) {
            return (this.mOrderKey - o.getOrderKey());
        }
        if (this.mOrderKey < o.getOrderKey()) {
            return (this.mOrderKey - o.getOrderKey());
        }
		return 0;
	}
}