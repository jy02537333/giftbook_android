package com.zxw.giftbook.Activity.entitiy;

import java.lang.String;
import java.lang.Integer;

import pri.zxw.library.base.BaseEntity;

/**   
 * @Title: 收礼明细
 * @Description: receiving_gifts_money
 * @author onlineGenerator
 * @date 2017-06-03 15:11:20
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class ReceivingGiftsMoneyEntity extends BaseEntity {
	/**Id*/
	private String id;
	/**组成员id*/
	private String gourpmemberid;
	/**组成员姓名*/
	private String groupmember;
	/**是否支出*/
	private Integer isexpenditure;
	/**金额*/
	private String money;
	/**支出类型编号*/
	private String expendituretype;
	/**支出类型名称*/
	private String expendituretypename;
	/**相关的请帖*/
	private String correlativeinvitation;
	/**支出时间*/
	private String expendituredate;
	/**备注*/
	private String remark;
	/**状态(0=删除，1正常)*/
	private Integer state;
	/**创建时间*/
	private String createDate;
	/**创建人编号*/
	private String createBy;
	/**创建人姓名*/
	private String createName;
	/**更新时间*/
	private String updateDate;
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
	public Integer getIsexpenditure(){
		return this.isexpenditure;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否支出
	 */
	public void setIsexpenditure(Integer isexpenditure){
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支出类型编号
	 */
	public String getExpendituretype(){
		return this.expendituretype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支出类型编号
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  相关的请帖
	 */
	public String getCorrelativeinvitation(){
		return this.correlativeinvitation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  相关的请帖
	 */
	public void setCorrelativeinvitation(String correlativeinvitation){
		this.correlativeinvitation = correlativeinvitation;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  支出时间
	 */
	public String getExpendituredate(){
		return this.expendituredate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  支出时间
	 */
	public void setExpendituredate(String expendituredate){
		this.expendituredate = expendituredate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
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
	public String getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(String createDate){
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新时间
	 */
	public String getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateDate(String updateDate){
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

	@Override
	public Class getMyClass() {
		return this.getClass();
	}
}
