package com.chujunxiong.superhornet;

import android.os.Process;

import java.util.concurrent.BlockingQueue;

/**
 * 任务分发线程
 * Created by looper on 2016/4/13.
 */
public class TaskDispatcher extends  Thread {

    private BlockingQueue<Task> mRequestQueue;
    private BlockingQueue<Task> mHandlerQueue;
    private BlockingQueue<Task> mQueue;
    private volatile boolean mQuit = false;

    public TaskDispatcher(BlockingQueue<Task> mQueue,BlockingQueue<Task> mRequestQueue,BlockingQueue<Task> mHandlerQueue){
       this.mQueue = mQueue;
       this.mRequestQueue = mRequestQueue;
       this.mHandlerQueue = mHandlerQueue;
    }

    public void quit() {
        mQuit = true;
        interrupt();
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Task task;
        while (true) {
            task = null;
            try {
                task = mQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                if(mQuit){
                    return;
                }
                continue;
            }

            int Type = task.getMtaskType();
            if(Type == 0) {
                try {
                    mRequestQueue.put(task);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if(Type == 2) {
                try {
                    mHandlerQueue.put(task);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
