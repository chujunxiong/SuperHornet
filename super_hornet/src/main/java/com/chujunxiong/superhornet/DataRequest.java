package com.chujunxiong.superhornet;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by looper on 2016/4/18.
 */
public class DataRequest {

    private int mRequstType = 0;
    private int mMethod = 0;
    private String mURL = null;
    private ResponseListener mListener = null;
    private final TaskQueue queue;

    private DataRequest(Context context) {
        queue = Go.newTaskQueue(context);
    }

    public static DataRequest init(Context context) {
        return  new DataRequest(context);
    }

    public DataRequest RequestType(int type) {
        this.mRequstType = type;
        return this;
    }

    public DataRequest Method(int method) {
        this.mMethod = method;
        return  this;
    }

    public DataRequest URL(String url) {
        this.mURL = url;
        return  this;
    }


    public DataRequest Listener(ResponseListener listener) {
        this.mListener = listener;
        return  this;
    }


    public void finish() {
        queue.add(new Task(Task.TaskType.REQUEST,new Task.Entry(getMap())));
    }


    private HashMap<String,Object> getMap() {
        HashMap<String,Object> map = new HashMap<>();
        map.put(RequestParameter.REQUEST_TYPE,mRequstType);
        map.put(RequestParameter.Method,mMethod);
        map.put(RequestParameter.URL,mURL);
        map.put(RequestParameter.LISTENER,mListener);
        return  map;
    }

}
