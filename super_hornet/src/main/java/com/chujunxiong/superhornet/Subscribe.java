package com.chujunxiong.superhornet;

import android.content.Context;

import java.util.LinkedHashMap;

/**
 * 跨组件/跨线程通信注册绑定类
 * Created by looper on 2016/4/14.
 */
public class Subscribe {

    private  LinkedHashMap<String,Context> map  = new LinkedHashMap<>();

    public static Subscribe getDefault() {
        return SubscribeHolder.instance;
    }

    private Subscribe() {}

    private static class SubscribeHolder {
        private static final Subscribe instance = new Subscribe();
    }

    public void register(String name,Context context){
        if(name != null && context != null) {
            map.put(name,context);
        }
    }


    public  LinkedHashMap<String,Context> getMap(){
        return  map;
    }


    public void unregister(String name) {
        if(name != null) {
            map.remove(name);
        }
    }


}
