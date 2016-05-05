package com.chujunxiong.superhornet;

/**
 * 规范跨组件/跨线程通信参数的名字
 * Created by looper on 2016/4/13.
 */
public interface HandlerParameter {

    //规范进程间通信的目标名字
    String Target = "Target";
    //规范进程间通信的数据名字
    String Data   = "Data";
    //规范进程间通信的监听名字
    String FUNCTION = "Function";
}
