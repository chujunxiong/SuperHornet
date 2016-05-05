package com.chujunxiong.superhornet;

import android.app.Activity;
import android.content.Context;
import android.os.Process;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;

/**
 *跨组件/跨线程通信任务分发线程
 * Created by looper on 2016/4/13.
 */
public class HandlerDispatcher extends  Thread{

    private BlockingQueue<Task> mQueue;
    private volatile boolean mQuit = false;
    private HashMap<String,Object> map;
    Class target;
    Object message;
    String function;
    LinkedHashMap<String, Context> lmap;
    Activity activity;

    public HandlerDispatcher(BlockingQueue<Task> mQueue) {
        this.mQueue = mQueue;
    }

    public void quit(){
        mQuit = true;
        interrupt();
    }

    private int getThreadType(Method m) {
        Register register = m.getAnnotation(Register.class);
        if(register != null){
            return register.ThreadType();
        }
        return 1;
    }


    private Runnable getRunnable(final Activity activity, final Method method, final Object message) {
        return  new Runnable() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                try {
                    method.invoke(activity,message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Task task;
        while (true) {
            try {
                task = mQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (mQuit) {
                    return;
                }
                continue;
            }
            Task.Entry entry = task.getEntry();
            if (entry != null) {
                map = entry.getMap();
                target = (Class) map.get(HandlerParameter.Target);
                message =  map.get(HandlerParameter.Data);
                function = (String) map.get(HandlerParameter.FUNCTION);
                lmap = Subscribe.getDefault().getMap();
                if (lmap.size() > 0) {
                    String SimpleName = target.getSimpleName();
                    if (SimpleName == null) {    //在某些时候getSimpleName可能会返回空
                        int position = target.getName().lastIndexOf('.');
                        if (position != -1) {
                            SimpleName = target.getName().substring(position + 1);
                        }
                    }
                    try {
                        activity = (Activity) lmap.get(SimpleName);
                        if (activity == null) {
                            throw new RuntimeException("确定在被通信组件中注册了么？");
                        } else {
                            Method method = null;
                            if(target != null && function != null) {
                                Method[] methods = target.getDeclaredMethods();
                                for(Method m:methods) {
                                    if(function.equals(m.getName())) {
                                        method = m;
                                    }
                                }
                            }
                            int type = getThreadType(method);
                            if(type == 1) {
                                activity.runOnUiThread(getRunnable(activity,method,message));
                            }else if(type == 0) {
                                new Thread(getRunnable(activity,method,message)).start();
                            }
                        }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                }
            }
        }
    }
}
