package com.zxw.giftbook.Activity.entitiy;


import pri.zxw.library.base.BaseEntity;

/**
 * @Title: Entity
 * @Description: 登陆记录
 * @author onlineGenerator
 * @date 2016-11-04 13:51:22
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class LoginlogEntity extends BaseEntity implements java.io.Serializable {
	public  Class<GifttypeEntity> getMyClass()
	{
		return GifttypeEntity.class;
	}
	/**Id*/
	private String id;
	/**用户id*/
	private String userid;
	/**登录时间*/
	private Long logindate;
	/**上次登录时间*/
	private Long lastlogindate;
	/**登录token*/
	private String logintoken;
	/**设备编号*/
	private String device;
	/**创建时间*/
	private Long createDate;
//	/**创建人编号*/
//	private String createBy;
//	/**创建人姓名*/
//	private String createName;
//	/**更新时间*/
//	private Long updateDate;
//	/**更新人编号*/
//	private String updateBy;
//	/**更信任姓名*/
//	private String updateName;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  Id
	 */
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  Id
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户id
	 */
	public String getUserid(){
		return this.userid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setUserid(String userid){
		this.userid = userid;
	}
	/**
	 *方法: 取得Long
	 *@return: Long  登录时间
	 */
	public Long getLogindate(){
		return this.logindate;
	}

	/**
	 *方法: 设置Long
	 *@param: Long  登录时间
	 */
	public void setLogindate(Long logindate){
		this.logindate = logindate;
	}
	/**
	 *方法: 取得Long
	 *@return: Long  上次登录时间
	 */
	public Long getLastlogindate(){
		return this.lastlogindate;
	}

	/**
	 *方法: 设置Long
	 *@param: Long  上次登录时间
	 */
	public void setLastlogindate(Long lastlogindate){
		this.lastlogindate = lastlogindate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  登录token
	 */
	public String getLogintoken(){
		return this.logintoken;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  登录token
	 */
	public void setLogintoken(String logintoken){
		this.logintoken = logintoken;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  设备编号
	 */
	public String getDevice(){
		return this.device;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  设备编号
	 */
	public void setDevice(String device){
		this.device = device;
	}
	/**
	 *方法: 取得Long
	 *@return: Long  创建时间
	 */
	public Long getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置Long
	 *@param: Long  创建时间
	 */
	public void setCreateDate(Long createDate){
		this.createDate = createDate;
	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  创建人编号
//	 */
//	public String getCreateBy(){
//		return this.createBy;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  创建人编号
//	 */
//	public void setCreateBy(String createBy){
//		this.createBy = createBy;
//	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  创建人姓名
//	 */
//	public String getCreateName(){
//		return this.createName;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  创建人姓名
//	 */
//	public void setCreateName(String createName){
//		this.createName = createName;
//	}
//	/**
//	 *方法: 取得Long
//	 *@return: Long  更新时间
//	 */
//	public Long getUpdateDate(){
//		return this.updateDate;
//	}
//
//	/**
//	 *方法: 设置Long
//	 *@param: Long  更新时间
//	 */
//	public void setUpdateDate(Long updateDate){
//		this.updateDate = updateDate;
//	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  更新人编号
//	 */
//	public String getUpdateBy(){
//		return this.updateBy;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  更新人编号
//	 */
//	public void setUpdateBy(String updateBy){
//		this.updateBy = updateBy;
//	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  更信任姓名
//	 */
//	public String getUpdateName(){
//		return this.updateName;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  更信任姓名
//	 */
//	public void setUpdateName(String updateName){
//		this.updateName = updateName;
//	}
}
