package com.chujunxiong.superhornet;

import android.content.Context;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 任务队列
 * Created by looper on 2016/4/13.
 */
public class TaskQueue {

    private final BlockingQueue<Task> mTaskQueue = new PriorityBlockingQueue<Task>();
    private final BlockingQueue<Task> mRequestQueue = new PriorityBlockingQueue<Task>();
    private final BlockingQueue<Task> mHandlerQueue = new PriorityBlockingQueue<Task>();
    private TaskDispatcher mTaskDispatcher;
    private RequestDispatcher mRequestDispatcher;
    private HandlerDispatcher mHandlerDispatcher;
    private Context context;

    public TaskQueue(Context context) {
        this.context = context;
    }

    public void start(){
        stop();
        mTaskDispatcher = new TaskDispatcher(mTaskQueue,mRequestQueue,mHandlerQueue);
        mTaskDispatcher.start();
        mRequestDispatcher = new RequestDispatcher(mRequestQueue,context);
        mRequestDispatcher.start();
        mHandlerDispatcher = new HandlerDispatcher(mHandlerQueue);
        mHandlerDispatcher.start();
    }

    public void stop() {
        if(mTaskDispatcher != null && mRequestDispatcher != null && mHandlerDispatcher != null){
            mTaskDispatcher.quit();
            mRequestDispatcher.quit();
            mHandlerDispatcher.quit();
        }
    }

    public void add(Task task){
        if(task == null) {
            throw new RuntimeException("Task 不能为空!");
        }else {
            mTaskQueue.add(task);
        }
    }
}
