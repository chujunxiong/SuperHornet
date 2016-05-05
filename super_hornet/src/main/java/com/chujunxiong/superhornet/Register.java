package com.chujunxiong.superhornet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by looper on 2016/5/4.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Register {
    //标记线程类型(默认在主线程中运行),暂时只支持background和mainThread
    int ThreadType() default ThreadType.MainThread;
}
