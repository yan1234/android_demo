package com.yanling.android.demo.clientapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by yanling on 17-4-7.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button bind, unbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, ">>>onCreate()");
        bind = (Button)findViewById(R.id.btn_bind);
        unbind = (Button)findViewById(R.id.btn_unbind);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
    }


    private static final int CODE_SEND = 0;

    private static final int CODE_RECEIVE = 1;

    //定义服务端的Messenger对象
    private Messenger serviceMessenger = null;

    //定义客户端的Messenger对象,并且和当前的Handler对象进行绑定
    private Messenger clientNessenger = new Messenger(new ClientHandler());

    private class ClientHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CODE_RECEIVE:
                    //打印输出消息
                    Bundle bundle = msg.getData();
                    Log.d(TAG, ">>>ReceiveMessage"+bundle.getString("msg"));
                    break;
            }
        }
    }

    //定义ServiceConnection连接对象
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, ">>>onServiceConnected");
            //连接成功后获取服务端的Messenger
            serviceMessenger = new Messenger(service);

            //构造消息对象
            Message msg = Message.obtain();
            msg.what = CODE_SEND;

            //此处跨进程Message通信必须将msg.obj设置为Parcelable对象，所以用bundle
            Bundle data = new Bundle();
            data.putString("msg", "你好Service, 我是客户端");
            msg.setData(data);

            //将msg消息的replyTo设置为客户端的clientMessenger
            msg.replyTo = clientNessenger;

            //发送消息
            try {
                Log.d(TAG, ">>>sendMessage");
                serviceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, ">>>onServiceDisconnected()");
            serviceMessenger = null;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bind:
                //绑定启动服务
                Intent intent = new Intent();
                intent.setAction("com.yanling.android.demo.serviceapp.RemoteService");
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                //启动服务
                PackageManager pm = MainActivity.this.getPackageManager();
                //先通过隐式启动获取待启动的Service信息
                ResolveInfo ri = pm.resolveService(intent, PackageManager.MATCH_ALL);

                if (ri != null){
                    //获取待启动的包名和类名
                    String packageName = ri.serviceInfo.packageName;
                    String className = ri.serviceInfo.name;
                    //构造成显示启动
                    ComponentName componentName = new ComponentName(packageName, className);
                    intent.setComponent(componentName);
                    //绑定启动
                    bindService(intent, connection, BIND_AUTO_CREATE);
                    Log.d(TAG, ">>>bindService");
                }
                break;
            case R.id.btn_unbind:
                unbindService(connection);
                Log.d(TAG, ">>>unbindService");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, ">>>onDestroy()");
    }
}
