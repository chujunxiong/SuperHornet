package com.chujunxiong.super_hornet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.chujunxiong.superhornet.HandlerProxy;
import com.chujunxiong.superhornet.R;
import com.chujunxiong.superhornet.Register;
import com.chujunxiong.superhornet.Subscribe;
import com.chujunxiong.superhornet.SwitchCallBack;
import com.chujunxiong.superhornet.ThreadType;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        Subscribe.getDefault().register(TAG,this);
        this.startService(new Intent(this,MyService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Register(ThreadType = ThreadType.MainThread)
    public void getNewMessage(Object var) {
        tv.setText(var.toString());
    }

    @Register(ThreadType = ThreadType.Background)
    public void getMessage(Object var) {
        Message msg = new Message();
        msg.obj = var;
        HandlerProxy.getDefault().attach(new SwitchCallBack() {
            @Override
            public void UseData(Message msg) {
                tv.setText(msg.obj.toString());
            }
        }).DeliverData(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Subscribe.getDefault().unregister(TAG);
    }
}
