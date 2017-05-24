package com.zxw.giftbook.my_enum;

/**
 * 登陆类型
 * Createdy 张相伟
 * 2016/10/30.
 */
public enum  LoginTypeEnum {
    QQ("腾讯",1),
    SINA("新浪",2),
   WEIXIN("微信",3),
    PLATFORM("平台",4);
    private String value;
    private int key;

    // 构造方法
    private LoginTypeEnum(String value, int key) {
        this.value = value;
        this.key = key;
    }

    // 覆盖方法
    @Override
    public String toString() {
        return this.key + "_" + this.value;
    }
}
