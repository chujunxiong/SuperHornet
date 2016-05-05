package com.chujunxiong.superhornet;

import java.util.HashMap;

/**
 * Task javabean
 * Created by looper on 2016/4/13.
 */
public class Task implements Comparable<Task>{

    @Override
    public int compareTo(Task another) {
        return 0;
    }

    /**
     * 规范任务类型
     */
    public interface TaskType{
        int REQUEST = 0;  //异步网络请求
        int HANDLER = 2;  //跨组件通信
    }

    //任务类型
    private int mtaskType;

    //任务实体内容
    private Entry entry;

    public Task(int mtaskType,Entry entry){
        this.mtaskType = mtaskType;
        this.entry  = entry;
    }

    public int getMtaskType(){
        return mtaskType;
    }

    public Entry getEntry() {
        if(entry != null) {
            return entry;
        }
        return null;
    }

    public static class Entry{

        private HashMap<String,Object> map = new HashMap();

        public  Entry(HashMap<String,Object> map) {
            this.map = map;
        }

        public HashMap<String,Object> getMap() {
            if(map != null){
                return map;
            }
            return null;
        }
    }
}
