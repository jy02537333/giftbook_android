package com.zxw.giftbook.Activity.entitiy;

import java.util.Date;
import java.util.List;

import pri.zxw.library.base.BaseEntity;

/**
 *  受邀请的请帖
 * Createdy 张相伟
 * 2017/5/20.
 */

public class ReceivesInvitationEntity extends BaseEntity implements java.io.Serializable {
    public  Class<ReceivesInvitationEntity> getMyClass()
    {
        return ReceivesInvitationEntity.class;
    }
    /**Id*/
    private java.lang.String id;
    /**邀请人*/
    private java.lang.String inviterid;
    /**宴席地址*/
    private java.lang.String feastaddress;
    /**宴席时间*/
    private String feastdate;
    /**宴席类型*/
    private java.lang.String feasttype;
    /**邀请人*/
    private java.lang.String invitername;
    /**邀请人电话*/
    private java.lang.String inviterphone;
    /**封面图片*/
    private java.lang.String coverimg;
    /**相册图片*/
    private java.lang.String photoalbum;
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

    private List<InvitationlistEntity> invitationlistEntityList;
    public List<InvitationlistEntity> getInvitationlistEntityList() {
        return invitationlistEntityList;
    }
    public void setInvitationlistEntityList(List<InvitationlistEntity> invitationlistEntityList) {
        this.invitationlistEntityList = invitationlistEntityList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInviterid() {
        return inviterid;
    }

    public String getInviterphone() {
        return inviterphone;
    }

    public void setInviterphone(String inviterphone) {
        this.inviterphone = inviterphone;
    }

    public void setInviterid(String inviterid) {
        this.inviterid = inviterid;
    }

    public String getFeastaddress() {
        return feastaddress;
    }

    public void setFeastaddress(String feastaddress) {
        this.feastaddress = feastaddress;
    }

    public String getFeastdate() {
        return feastdate;
    }

    public void setFeastdate(String feastdate) {
        this.feastdate = feastdate;
    }

    public String getFeasttype() {
        return feasttype;
    }

    public void setFeasttype(String feasttype) {
        this.feasttype = feasttype;
    }

    public String getInvitername() {
        return invitername;
    }

    public void setInvitername(String invitername) {
        this.invitername = invitername;
    }

    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
    }

    public String getPhotoalbum() {
        return photoalbum;
    }

    public void setPhotoalbum(String photoalbum) {
        this.photoalbum = photoalbum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
}
