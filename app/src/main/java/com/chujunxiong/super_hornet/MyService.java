package com.chujunxiong.super_hornet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Request;
import com.chujunxiong.superhornet.Communicate;
import com.chujunxiong.superhornet.DataRequest;
import com.chujunxiong.superhornet.RequestType;
import com.chujunxiong.superhornet.ResponseListener;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DataRequest.init(this)
                .RequestType(RequestType.JsonArray)
                .Method(Request.Method.GET)
                .URL("xxxxx")
                .Listener(new ResponseListener() {
                    @Override
                    public void sucess(Object var) {
                        Communicate.init(MyService.this).Target(MainActivity.class).Data(var.toString()).Function("getNewMessage").finish();
                    }

                    @Override
                    public void error(Object var) {
                        Communicate.init(MyService.this).Target(MainActivity.class).Data("请求失败！").Function("getMessage").finish();
                    }
                })
                .finish();

        return super.onStartCommand(intent, flags, startId);
    }
}
