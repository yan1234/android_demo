package com.yanling.android.demo.serviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 远端的服务端的Service
 * @author yanling
 * @date 2017-04-07
 */
public class RemoteService extends Service{

    private static final String TAG = RemoteService.class.getSimpleName();

    private static final int CODE_RECEIVE = 0;

    private static final int CODE_SEND = 1;

    //定义服务端的Messenger,并指向本服务的handler对象
    private Messenger serviceMessenger = new Messenger(new ServiceHandler());

    //定义客户端的Messenger
    private Messenger clientMessenger;

    //定义服务端的handler对象，用于接收处理客户端传递过来的消息
    private class ServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CODE_RECEIVE:
                    //接收客户端传递过来的消息
                    //得到客户端的连接
                    clientMessenger = msg.replyTo;

                    //获取消息
                    Bundle bundle = msg.getData();
                    Log.d(TAG, ">>>Receive Message:" + bundle.getString("msg"));

                    //向客户端回传消息
                    Message clientMsg = Message.obtain();
                    clientMsg.what = CODE_SEND;
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("msg", "你好Client,我是RemoteService");
                    clientMsg.setData(bundle1);
                    try {
                        clientMessenger.send(clientMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, ">>>onCreate()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, ">>>onBind()");
        //返回Messenger的Bindler给客户端，用于客户端重新构造生成该Messenger对象
        return serviceMessenger.getBinder();

        //客户端
        //Messenger messenger = new Messenger(IBinder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, ">>>onDestroy()");
    }
}
