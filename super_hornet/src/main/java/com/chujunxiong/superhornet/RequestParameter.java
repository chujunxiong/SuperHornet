package com.chujunxiong.superhornet;

/**
 * 规范请求参数的名字
 * Created by looper on 2016/4/13.
 */
public interface RequestParameter {
    //规范请求方法的名字
    String Method = "Method";
    //规范请求URL的名字
    String URL    = "URL";
    //规范响应回调的名字
    String LISTENER = "Listener";
    //规范请求类型的名字
    String REQUEST_TYPE = "RequestType";
  }
