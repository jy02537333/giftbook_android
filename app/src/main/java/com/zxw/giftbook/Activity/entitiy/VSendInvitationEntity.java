package com.zxw.giftbook.Activity.entitiy;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: v_send_invitation
 * @author onlineGenerator
 * @date 2017-06-07 15:34:59
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_send_invitation", schema = "")
@SuppressWarnings("serial")
public class VSendInvitationEntity implements java.io.Serializable {
	/**Id*/
	private String id;
	/**男士姓名*/
	@Excel(name="男士姓名")
	private String manname;
	/**女士姓名*/
	@Excel(name="女士姓名")
	private String womanname;
	/**状态(0=删除，1正常)*/
	@Excel(name="状态(0=删除，1正常)")
	private Integer state;
	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd")
	private Date create_date;
	/**创建人编号*/
	@Excel(name="创建人编号")
	private String create_by;
	/**创建人姓名*/
	@Excel(name="创建人姓名")
	private String create_name;
	/**邀请人编号*/
	@Excel(name="邀请人编号")
	private String inviterid;
	/**邀请人电话*/
	@Excel(name="邀请人电话")
	private String inviterphone;
	/**宴席地址*/
	@Excel(name="宴席地址")
	private String feastaddress;
	/**宴席时间*/
	@Excel(name="宴席时间",format = "yyyy-MM-dd")
	private Date feastdate;
	/**封面图片*/
	@Excel(name="封面图片")
	private String coverimg;
	/**相册图片*/
	@Excel(name="相册图片")
	private String photoalbum;
	/**feasttype*/
	@Excel(name="feasttype")
	private String feasttype;
	/**num*/
	@Excel(name="num")
	private Integer num;

	@Column(name ="ID",nullable=false,length=32)
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
	@Column(name ="MANNAME",nullable=true,length=100)
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
	@Column(name ="WOMANNAME",nullable=true,length=100)
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
	@Column(name ="STATE",nullable=true,length=10)
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
	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.create_date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(Date createDate){
		this.create_date = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人编号
	 */
	@Column(name ="CREATE_BY",nullable=true,length=100)
	public String getCreateBy(){
		return this.create_by;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人编号
	 */
	public void setCreateBy(String createBy){
		this.create_by = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人姓名
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=100)
	public String getCreateName(){
		return this.create_name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人姓名
	 */
	public void setCreateName(String createName){
		this.create_name = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邀请人编号
	 */
	@Column(name ="INVITERID",nullable=true,length=32)
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
	@Column(name ="INVITERPHONE",nullable=true,length=32)
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
	@Column(name ="FEASTADDRESS",nullable=true,length=150)
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
	@Column(name ="FEASTDATE",nullable=true)
	public Date getFeastdate(){
		return this.feastdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  宴席时间
	 */
	public void setFeastdate(Date feastdate){
		this.feastdate = feastdate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  封面图片
	 */
	@Column(name ="COVERIMG",nullable=true,length=255)
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
	@Column(name ="PHOTOALBUM",nullable=true,length=2000)
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
	@Column(name ="FEASTTYPE",nullable=true,length=3)
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
	@Column(name ="NUM",nullable=false,length=19)
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
}