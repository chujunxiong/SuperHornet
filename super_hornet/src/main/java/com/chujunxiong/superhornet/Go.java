package com.chujunxiong.superhornet;

import android.content.Context;

/**
 * 任务队列获取类
 * Created by looper on 2016/4/13.
 */
public class Go {

    public static TaskQueue newTaskQueue(Context context) {
        TaskQueue queue =  new TaskQueue(context);
        queue.start();
        return queue;
    }

}
