package com.zxw.giftbook.Activity.entitiy;

import java.lang.String;
import java.lang.Integer;

import pri.zxw.library.base.BaseEntity;

/**   
 * @Title: 我发送的请帖
 * @Description: v_send_invitation
 * @author onlineGenerator
 * @date 2017-06-07 15:34:59
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class VSendInvitationEntity extends BaseEntity {
	/**Id*/
	private String id;
	/**男士姓名*/
	private String manname;
	/**女士姓名*/
	private String womanname;
	/**状态(0=删除，1正常)*/
	private Integer state;
	/**创建时间*/
	private String createDate;
	/**创建人编号*/
	private String createBy;
	/**创建人姓名*/
	private String createName;
	/**邀请人编号*/
	private String inviterid;
	/**邀请人电话*/
	private String inviterphone;
	/**宴席地址*/
	private String feastaddress;
	/**宴席时间*/
	private String feastdate;
	/**封面图片*/
	private String coverimg;
	/**相册图片*/
	private String photoalbum;
	/**feasttype*/
	private String feasttype;
	/**num*/
	private Integer num;
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
	 *@return: java.lang.String  男士姓名
	 */
	public String getManname(){
		return this.manname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  男士姓名
	 */
	public void setManname(String manname){
		this.manname = manname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  女士姓名
	 */
	public String getWomanname(){
		return this.womanname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  女士姓名
	 */
	public void setWomanname(String womanname){
		this.womanname = womanname;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邀请人编号
	 */
	public String getInviterid(){
		return this.inviterid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邀请人编号
	 */
	public void setInviterid(String inviterid){
		this.inviterid = inviterid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邀请人电话
	 */
	public String getInviterphone(){
		return this.inviterphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邀请人电话
	 */
	public void setInviterphone(String inviterphone){
		this.inviterphone = inviterphone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  宴席地址
	 */
	public String getFeastaddress(){
		return this.feastaddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  宴席地址
	 */
	public void setFeastaddress(String feastaddress){
		this.feastaddress = feastaddress;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  宴席时间
	 */
	public String getFeastdate(){
		return this.feastdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  宴席时间
	 */
	public void setFeastdate(String feastdate){
		this.feastdate = feastdate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  封面图片
	 */
	public String getCoverimg(){
		return this.coverimg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  封面图片
	 */
	public void setCoverimg(String coverimg){
		this.coverimg = coverimg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  相册图片
	 */
	public String getPhotoalbum(){
		return this.photoalbum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  相册图片
	 */
	public void setPhotoalbum(String photoalbum){
		this.photoalbum = photoalbum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  feasttype
	 */
	public String getFeasttype(){
		return this.feasttype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  feasttype
	 */
	public void setFeasttype(String feasttype){
		this.feasttype = feasttype;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  num
	 */
	public Integer getNum(){
		return this.num;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  num
	 */
	public void setNum(Integer num){
		this.num = num;
	}

	@Override
	public Class getMyClass() {
		return this.getClass();
	}
}
