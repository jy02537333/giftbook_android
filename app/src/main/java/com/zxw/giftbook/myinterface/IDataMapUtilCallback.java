package com.zxw.giftbook.myinterface;

/**
 * 缓存数据结果返回接口
 * Created by 张相伟 on 2017/6/28.
 */

public interface IDataMapUtilCallback {
    public void onSuccess(boolean isSuccess) ;
    public void onFailure(boolean isFailure) ;
}
