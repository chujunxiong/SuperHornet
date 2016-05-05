package com.chujunxiong.superhornet;

import android.content.Context;
import android.os.Process;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * 异步网络请求任务分发线程
 * Created by looper on 2016/4/13.
 */
public class RequestDispatcher extends  Thread{

    private BlockingQueue<Task> mQueue;
    private HashMap<String,Object> map;
    private RequestQueue queue;
    private volatile boolean mQuit = false;

    public RequestDispatcher(BlockingQueue<Task> mQueue, Context context) {
        this.mQueue = mQueue;
        queue = Volley.newRequestQueue(context);
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
            try {
                task = mQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                if(mQuit) {
                    return;
                }
                continue;
            }
            Task.Entry entry = task.getEntry();
            map = entry.getMap();
            Request request = GetRequestObject.getDefault().getRequestObject(map);
            queue.add(request);
        }
    }
}
