package com.chujunxiong.superhornet;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by looper on 2016/5/4.
 */
public class HandlerProxy {

    private Handler handler;
    private SwitchCallBack callBack;


    private HandlerProxy(){}

    public static HandlerProxy getDefault() {
        return Holder.proxy;
    }

    private static class Holder {
        private static final HandlerProxy proxy = new HandlerProxy();
    }

    public HandlerProxy attach(SwitchCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public void DeliverData(Message message) {
        if(message != null) {
            Looper looper = Looper.getMainLooper();
            handler = new Handler(looper){
                @Override
                public void handleMessage(Message msg) {
                  if(callBack != null) {
                      callBack.UseData(msg);
                      handler = null;
                  }
                }
            };
            handler.sendMessage(message);
        }
    }
}
