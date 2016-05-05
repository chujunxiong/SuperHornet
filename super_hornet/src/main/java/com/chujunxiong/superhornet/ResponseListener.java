package com.chujunxiong.superhornet;

/**
 * Created by looper on 2016/4/13.
 */
public interface ResponseListener {

    //响应成功
    public void sucess(Object var);
    //响应失败
    public void error(Object var);
}
