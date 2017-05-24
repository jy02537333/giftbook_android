package com.zxw.giftbook.Activity.entitiy;


import pri.zxw.library.base.BaseEntity;

/**
 * @Title: Entity
 * @Description: 礼金类型
 * @author onlineGenerator
 * @date 2016-11-03 09:52:10
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class GifttypeEntity  extends BaseEntity implements java.io.Serializable {
	public  Class<GifttypeEntity> getMyClass()
	{
		return GifttypeEntity.class;
	}

	/**Id*/
	private String id;
	/**类型id，逻辑id*/
	private String dtid;
	/**类型名称*/
	private String typename;
	/**父级id*/
	private String parentid;
	/**创建时间*/
	private Long createDate;
//	/**创建人编号*/
//	private String createBy;
//	/**创建人姓名*/
//	private String createName;
//	/**更新时间*/
//	private java.util.Date updateDate;
//	/**更新人编号*/
//	private String updateBy;
//	/**更新人姓名*/
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
	 *@return: java.lang.String  类型id，逻辑id
	 */
	public String getDtid(){
		return this.dtid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型id，逻辑id
	 */
	public void setDtid(String dtid){
		this.dtid = dtid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型名称
	 */
	public String getTypename(){
		return this.typename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型名称
	 */
	public void setTypename(String typename){
		this.typename = typename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父级id
	 */
	public String getParentid(){
		return this.parentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父级id
	 */
	public void setParentid(String parentid){
		this.parentid = parentid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	public Long getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
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
//	 *方法: 取得java.util.Date
//	 *@return: java.util.Date  更新时间
//	 */
//	public java.util.Date getUpdateDate(){
//		return this.updateDate;
//	}
//
//	/**
//	 *方法: 设置java.util.Date
//	 *@param: java.util.Date  更新时间
//	 */
//	public void setUpdateDate(java.util.Date updateDate){
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
//	 *@return: java.lang.String  更新人姓名
//	 */
//	public String getUpdateName(){
//		return this.updateName;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  更新人姓名
//	 */
//	public void setUpdateName(String updateName){
//		this.updateName = updateName;
//	}
}
