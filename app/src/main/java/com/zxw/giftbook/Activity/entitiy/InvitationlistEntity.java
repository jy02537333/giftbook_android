package com.zxw.giftbook.Activity.entitiy;

import pri.zxw.library.base.BaseEntity;

/**
 *  邀请人列表
 * Createdy 张相伟
 * 2017/5/23.
 */

public class InvitationlistEntity  extends BaseEntity {
    /**Id*/
    private java.lang.String id;
    /**请帖编号*/
    private java.lang.String invitationid;
    /**邀请人信息*/
    private java.lang.String invitationName;
    /**被邀请人*/
    private java.lang.String inviteeid;
    /**被邀请人*/
    private java.lang.String inviteename;
    /**被邀请人电话*/
    private java.lang.String inviteephone;
    /**状态(0=删除，1正常)*/
    private java.lang.Integer state;
    /**创建时间*/
    private String createDate;
    /**创建人编号*/
    private java.lang.String createBy;
    /**创建人姓名*/
    private java.lang.String createName;
    /**更新时间*/
    private String updateDate;
    /**更新人编号*/
    private java.lang.String updateBy;
    /**更信任姓名*/
    private java.lang.String updateName;
    /**是否已经发送短信(0未发送，1已发送)*/
    private java.lang.Integer issend;
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  Id
     */
    public java.lang.String getId(){
        return this.id;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  Id
     */
    public void setId(java.lang.String id){
        this.id = id;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  请帖编号
     */
    public java.lang.String getInvitationid(){
        return this.invitationid;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  请帖编号
     */
    public void setInvitationid(java.lang.String invitationid){
        this.invitationid = invitationid;
    }


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  邀请人信息
     */
    public java.lang.String getInvitationName(){
        return this.invitationName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  邀请人信息
     */
    public void setInvitationName(java.lang.String invitationName){
        this.invitationName = invitationName;
    }


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  被邀请人
     */
    public java.lang.String getInviteeid(){
        return this.inviteeid;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  被邀请人
     */
    public void setInviteeid(java.lang.String inviteeid){
        this.inviteeid = inviteeid;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  被邀请人
     */
    public java.lang.String getInviteename(){
        return this.inviteename;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  被邀请人
     */
    public void setInviteename(java.lang.String inviteename){
        this.inviteename = inviteename;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  被邀请人电话
     */
    public java.lang.String getInviteephone(){
        return this.inviteephone;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  被邀请人电话
     */
    public void setInviteephone(java.lang.String inviteephone){
        this.inviteephone = inviteephone;
    }
    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  状态(0=删除，1正常)
     */
    public java.lang.Integer getState(){
        return this.state;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  状态(0=删除，1正常)
     */
    public void setState(java.lang.Integer state){
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
    public java.lang.String getCreateBy(){
        return this.createBy;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  创建人编号
     */
    public void setCreateBy(java.lang.String createBy){
        this.createBy = createBy;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  创建人姓名
     */
    public java.lang.String getCreateName(){
        return this.createName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  创建人姓名
     */
    public void setCreateName(java.lang.String createName){
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
    public java.lang.String getUpdateBy(){
        return this.updateBy;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  更新人编号
     */
    public void setUpdateBy(java.lang.String updateBy){
        this.updateBy = updateBy;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  更信任姓名
     */
    public java.lang.String getUpdateName(){
        return this.updateName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  更信任姓名
     */
    public void setUpdateName(java.lang.String updateName){
        this.updateName = updateName;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  是否已经发送短信(0未发送，1已发送)
     */
    public java.lang.Integer getIssend(){
        return this.issend;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  是否已经发送短信(0未发送，1已发送)
     */
    public void setIssend(java.lang.Integer issend){
        this.issend = issend;
    }

    @Override
    public Class getMyClass() {
        return null;
    }
}
