package com.zxw.giftbook.Activity.entitiy;


import pri.zxw.library.base.BaseEntity;

/**
 * @Title: Entity
 * @Description: 成员礼金记录
 * @author onlineGenerator
 * @date 2016-11-03 17:23:01
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class MembergiftmoneyEntity extends BaseEntity implements java.io.Serializable {
	public  Class<GifttypeEntity> getMyClass()
	{
		return GifttypeEntity.class;
	}
	/**Id*/
	private String id;
	/**组成员id*/
	private String gourpmemberid;
	/**组成员姓名*/
	private String groupmember;
	/**是否支出*/
	private String isexpenditure;
	/**金额*/
	private String money;
	/**支出类型编号*/
	private String expendituretype;
	/**支出类型名称*/
	private String expendituretypename;
	/**状态(0=删除，1正常)*/
	private Integer state;
	/**创建时间*/
	private Long createDate;
	/**创建人编号*/
	private String createBy;
	/**创建人姓名*/
	private String createName;
	/**更新时间*/
	private Long updateDate;
	/**更新人编号*/
	private String updateBy;
	/**更信任姓名*/
	private String updateName;
	
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
	 *@return: java.lang.String  组成员id
	 */
	public String getGourpmemberid(){
		return this.gourpmemberid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组成员id
	 */
	public void setGourpmemberid(String gourpmemberid){
		this.gourpmemberid = gourpmemberid;
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
	 *@return: java.lang.String  是否支出
	 */
	public String getIsexpenditure(){
		return this.isexpenditure;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否支出
	 */
	public void setIsexpenditure(String isexpenditure){
		this.isexpenditure = isexpenditure;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  金额
	 */
	public String getMoney(){
		return this.money;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  金额
	 */
	public void setMoney(String money){
		this.money = money;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  支出类型编号
	 */
	public String getExpendituretype(){
		return this.expendituretype;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  支出类型编号
	 */
	public void setExpendituretype(String expendituretype){
		this.expendituretype = expendituretype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支出类型名称
	 */
	public String getExpendituretypename(){
		return this.expendituretypename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支出类型名称
	 */
	public void setExpendituretypename(String expendituretypename){
		this.expendituretypename = expendituretypename;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人编号
	 */
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人编号
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人姓名
	 */
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人姓名
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得Long
	 *@return: Long  更新时间
	 */
	public Long getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置Long
	 *@param: Long  更新时间
	 */
	public void setUpdateDate(Long updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人编号
	 */
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人编号
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更信任姓名
	 */
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更信任姓名
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
}
