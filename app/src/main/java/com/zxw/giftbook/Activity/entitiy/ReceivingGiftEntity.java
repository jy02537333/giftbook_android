package com.zxw.giftbook.Activity.entitiy;

import java.util.Date;

import pri.zxw.library.base.BaseEntity;

/**
 *  收到礼金
 * Createdy 张相伟
 * 2017/6/3.
 */

public class ReceivingGiftEntity extends BaseEntity {
    /**标题*/
    private String title;
    /**创建时间*/
    private String createdate;
    private String createby;
    /**Id*/
    private String id;
    /**receivestype*/
    private String receivestype;
    private String typeid;
    /**sumMoney*/
    private String summoney;
    /**num*/
    private Integer num;

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  标题
     */
    public String getTitle(){
        return this.title;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  标题
     */
    public void setTitle(String title){
        this.title = title;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  创建时间
     */
    public String getCreateDate(){
        return this.createdate;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  创建时间
     */
    public void setCreateDate(String createDate){
        this.createdate = createDate;
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
     *@return: java.lang.String  receivestype
     */
    public String getReceivestype(){
        return this.receivestype;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  receivestype
     */
    public void setReceivestype(String receivestype){
        this.receivestype = receivestype;
    }

    /**
     * 送礼类型
     * @return
     */
    public String getReceivestypeId() {
        return typeid;
    }
    /**
     * 送礼类型
     * @return
     */
    public void setReceivestypeId(String typeid) {
        this.typeid = typeid;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  sumMoney
     */
    public String getSumMoney(){
        return this.summoney;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  sumMoney
     */
    public void setSumMoney(String sumMoney){
        this.summoney = sumMoney;
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

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    @Override
    public Class getMyClass() {
        return this.getClass();
    }
}
