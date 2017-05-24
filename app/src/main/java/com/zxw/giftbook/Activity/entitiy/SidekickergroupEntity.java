package com.zxw.giftbook.Activity.entitiy;


import pri.zxw.library.base.BaseEntity;

/**   
 * @Title: Entity
 * @Description: 亲友团
 * @author onlineGenerator
 * @date 2016-11-04 13:54:16
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class SidekickergroupEntity extends BaseEntity implements java.io.Serializable {
	public  Class<GifttypeEntity> getMyClass()
	{
		return GifttypeEntity.class;
	}
	/**Id*/
	private String id;
	/**用户id*/
	private String userid;
	/**人员数量*/
	private Integer groupmembersnum;
	/**组名称*/
	private String groupname;
	/**亲友团，适配器添加按钮，分类使用*/
	private int addType;
//	/**状态(0=删除，1正常)*/
//	private Integer state;
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
	/**亲友团，适配器添加按钮，分类使用*/
	public int getAddType() {
		return addType;
	}
	/**亲友团，适配器添加按钮，分类使用*/
	public void setAddType(int addType) {
		this.addType = addType;
	}

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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  人员数量
	 */
	public Integer getGroupmembersnum(){
		return this.groupmembersnum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  人员数量
	 */
	public void setGroupmembersnum(Integer groupmembersnum){
		this.groupmembersnum = groupmembersnum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组名称
	 */
	public String getGroupname(){
		return this.groupname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组名称
	 */
	public void setGroupname(String groupname){
		this.groupname = groupname;
	}
//	/**
//	 *方法: 取得java.lang.Integer
//	 *@return: java.lang.Integer  状态(0=删除，1正常)
//	 */
//	public Integer getState(){
//		return this.state;
//	}
//
//	/**
//	 *方法: 设置java.lang.Integer
//	 *@param: java.lang.Integer  状态(0=删除，1正常)
//	 */
//	public void setState(Integer state){
//		this.state = state;
//	}
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
