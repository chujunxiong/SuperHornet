package com.chujunxiong.superhornet;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by looper on 2016/4/18.
 */
public class Communicate {

    private Class mTarget = null;
    private Object mData = null;
    private String mFunction = null;
    private final TaskQueue queue;

    private Communicate(Context context) {
        queue = Go.newTaskQueue(context);
    }

    public static  Communicate init (Context context) {
        return  new Communicate(context);
    }

    public Communicate Target(Class target) {
        this.mTarget = target;
        return  this;
    }

    public Communicate Data(Object data) {
        this.mData = data;
        return  this;
    }

    public Communicate Function(String function) {
        this.mFunction = function;
        return  this;
    }

    public void finish() {
        queue.add(new Task(Task.TaskType.HANDLER,new Task.Entry(getMap())));
    }

    private HashMap<String,Object> getMap() {
        HashMap<String,Object> map = new HashMap<>();
        map.put(HandlerParameter.Target,mTarget);
        map.put(HandlerParameter.Data,mData);
        map.put(HandlerParameter.FUNCTION,mFunction);
        return  map;
    }


}
