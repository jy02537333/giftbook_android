package com.zxw.giftbook.utils.contact;

import android.widget.SectionIndexer;

public class AddressBooktBean  {
public String tfId;
public String tfName;//姓名
public String sortKey;//索引

/**
 * @return the tfId
 */
public String getTfId() {
	return tfId;
}
/**
 * @param tfId the tfId to set
 */
public void setTfId(String tfId) {
	this.tfId = tfId;
}
public String getSortKey() {
	return sortKey;
}
public void setSortKey(String sortKey) {
	this.sortKey = sortKey;
}
public String getTfName() {
	return tfName;
}
public void setTfName(String tfName) {
	this.tfName = tfName;
}
public String getTfPhone() {
	return tfPhone;
}
public void setTfPhone(String tfPhone) {
	this.tfPhone = tfPhone;
}
	/***头像*/
public String getTfPortrait() {
	return tfPortrait;
}

	/**
	 * 头像
	 * @param tfPortrait
     */
    public void setTfPortrait(String tfPortrait) {
	this.tfPortrait = tfPortrait;
}
	/**关联人*/
public String getAffiliatedperson() {
	return affiliatedperson;
}
	/**关联人*/
public void setAffiliatedperson(String affiliatedperson) {
	this.affiliatedperson = affiliatedperson;
}
public String tfPhone;//电话
public String tfPortrait;//头像
public String affiliatedperson;//职位



}
