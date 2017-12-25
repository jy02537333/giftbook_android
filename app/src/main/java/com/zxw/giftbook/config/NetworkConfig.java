package com.zxw.giftbook.config;

import pri.zxw.library.constant.BaseNetConfig;

/**
 * Created by sun on 2016/7/12.
 */
public class NetworkConfig extends BaseNetConfig {
    static {
        BaseNetConfig.img_url="http://images.libugj.cn/";
    }
    public static final String IP="www.libugj.cn";
 //   public static final String IP="http://192.168.255.108:8080/";
 //   public static final String IP="http://192.168.3.102:8087/";
    public static final String PORT="";
 //   public static final String PORT=":8080";
    public static final String PROJECT_NAME="";
 //   public static final String PROJECT_NAME="/jeecg";
  public static  String api_url="https://"+IP+PORT+PROJECT_NAME+"/";

//  public static  String api_url="http://192.168.3.114:8087/";


}
