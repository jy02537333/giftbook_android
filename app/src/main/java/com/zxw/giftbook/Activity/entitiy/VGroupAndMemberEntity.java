package com.zxw.giftbook.Activity.entitiy;

import java.util.Date;
import java.lang.String;
import java.lang.Integer;

import pri.zxw.library.base.BaseEntity;

/**   
 * @Title: 视图，组名称加组员信息
 * @Description: v_group_and_member
 * @author onlineGenerator
 * @date 2017-06-18 17:37:55
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class VGroupAndMemberEntity extends BaseEntity {
	private String userid;
	/**Id*/
	private String groupid;
	/**是否默认使用*/
	private String isdefault;
	/**人员数量*/
	private Integer groupmembersnum;
	/**组名称*/
	private String groupname;
	/**创建时间*/
	private Long createdate;
	/**Id*/
	private String id;
	/**组成员姓名*/
	private String groupmember;
	/**总金额*/
	private String totalmoney;
	/**memberphone*/
	private String memberphone;
	/**affiliatedperson*/
	private String affiliatedperson;
	/**affiliatedpersonid*/
	private String affiliatedpersonid;


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  userid
	 */
	public String getUserid(){
		return this.userid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  userid
	 */
	public void setUserid(String userid){
		this.userid = userid;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  Id
	 */
	public String getGroupid(){
		return this.groupid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  Id
	 */
	public void setGroupid(String groupid){
		this.groupid = groupid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否默认使用
	 */
	public String getIsdefault(){
		return this.isdefault;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否默认使用
	 */
	public void setIsdefault(String isdefault){
		this.isdefault = isdefault;
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
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	public Long getCreatedate(){
		return this.createdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreatedate(Long createdate){
		this.createdate = createdate;
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
	public String getTotalmoney(){
		return this.totalmoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  总金额
	 */
	public void setTotalmoney(String totalmoney){
		this.totalmoney = totalmoney;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  memberphone
	 */
	public String getMemberphone(){
		return this.memberphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  memberphone
	 */
	public void setMemberphone(String memberphone){
		this.memberphone = memberphone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  affiliatedperson
	 */
	public String getAffiliatedperson(){
		return this.affiliatedperson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  affiliatedperson
	 */
	public void setAffiliatedperson(String affiliatedperson){
		this.affiliatedperson = affiliatedperson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  affiliatedpersonid
	 */
	public String getAffiliatedpersonid(){
		return this.affiliatedpersonid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  affiliatedpersonid
	 */
	public void setAffiliatedpersonid(String affiliatedpersonid){
		this.affiliatedpersonid = affiliatedpersonid;
	}

	@Override
	public Class getMyClass() {
		return null;
	}
}
