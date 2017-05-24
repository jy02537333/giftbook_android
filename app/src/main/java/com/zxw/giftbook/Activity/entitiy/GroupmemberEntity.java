package com.zxw.giftbook.Activity.entitiy;


import pri.zxw.library.base.BaseEntity;

/**
 * @Title: Entity
 * @Description: 组成员
 * @author onlineGenerator
 * @date 2016-11-04 13:55:00
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class GroupmemberEntity extends BaseEntity implements java.io.Serializable {
	public  Class<GifttypeEntity> getMyClass()
	{
		return GifttypeEntity.class;
	}

	/**Id*/
	private String id;
	/**组id*/
	private String gourpid;
	/**组名称*/
	private String gourpName;
	/**组成员姓名*/
	private String groupmember;
	/**总金额*/
	private Double totalmoney;
	private String memberphone;
	private String affiliatedperson;
	/**关联人id*/
	private String affiliatedpersonid;
	/**总金额*/
	private Double affiliatedpersonidAmount;
	/**关联人id*/
	private  GroupmemberEntity[] affiliatedpersonList;
	/**状态(0=删除，1正常)*/
	private Integer state;
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
//	/**更信任姓名*/
//	private String updateName;

	/**
	 *  成员电话
	 * @return
     */
	public String getMemberphone() {
		return memberphone;
	}
	/**
	 *  成员电话
	 * @return
	 */
	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}

	/**
	 * 关联人
	 * @return
     */
	public String getAffiliatedperson() {
		return affiliatedperson;
	}
	/**
	 * 关联人
	 * @return
	 */
	public void setAffiliatedperson(String affiliatedperson) {
		this.affiliatedperson = affiliatedperson;
	}
	/***关联人id*/
	public String getAffiliatedpersonid() {
		return affiliatedpersonid;
	}
	/***关联人id*/
	public void setAffiliatedpersonid(String affiliatedpersonid) {
		this.affiliatedpersonid = affiliatedpersonid;
	}
	/***关联人集合*/
	public GroupmemberEntity[] getAffiliatedpersonList() {
		return affiliatedpersonList;
	}
	/***关联人集合*/
	public void setAffiliatedpersonList(GroupmemberEntity[] affiliatedpersonList) {
		this.affiliatedpersonList = affiliatedpersonList;
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
	 *@return: java.lang.String  组id
	 */
	public String getGourpid(){
		return this.gourpid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组id
	 */
	public void setGourpid(String gourpid){
		this.gourpid = gourpid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组id
	 */
	public String getGourpName(){
		return this.gourpName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组id
	 */
	public void setGourpName(String gourpName){
		this.gourpName = gourpName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组成员姓名
	 */
	public String getGroupmember(){
		return this.groupmember;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组成员姓名
	 */
	public void setGroupmember(String groupmember){
		this.groupmember = groupmember;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  总金额
	 */
	public Double getTotalmoney(){
		return this.totalmoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  总金额
	 */
	public void setTotalmoney(Double totalmoney){
		this.totalmoney = totalmoney;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态(0=删除，1正常)
	 */
	public Integer getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态(0=删除，1正常)
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	public  Long getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(Long createDate){
		this.createDate = createDate;
	}
	/**关联人总金额*/
	public Double getAffiliatedpersonidAmount() {
		return affiliatedpersonidAmount;
	}
	/**关联人总金额*/
	public void setAffiliatedpersonidAmount(Double affiliatedpersonidAmount) {
		this.affiliatedpersonidAmount = affiliatedpersonidAmount;
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
