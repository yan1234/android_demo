package com.yanling.android.demo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * 主界面
 * @author yanling
 * @date 2017-04-06
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btn_bottom_1;
    private Button btn_bottom_2;
    private Button btn_bottom_3;
    private Button btn_bottom_4;

    private Fragment1 fragment1;
    private Fragment2 fragment2;

    //初始化Fragment管理器
    FragmentManager fragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.d(TAG, ">>> onCreate");

        //初始化布局
        btn_bottom_1 = (Button)findViewById(R.id.bottom_bar_1);
        btn_bottom_2 = (Button)findViewById(R.id.bottom_bar_2);
        btn_bottom_3 = (Button)findViewById(R.id.bottom_bar_3);
        btn_bottom_4 = (Button)findViewById(R.id.bottom_bar_4);
        btn_bottom_1.setOnClickListener(this);
        btn_bottom_2.setOnClickListener(this);
        btn_bottom_3.setOnClickListener(this);
        btn_bottom_4.setOnClickListener(this);

        //设置默认的fragment
        //避免由于屏幕切换导致的多个fragment实例创建
        if (savedInstanceState == null){
            setDefaultContent();
        }



    }

    private void setDefaultContent(){
        fragmentManager = getFragmentManager();
        //开启fragment操作事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment2 = new Fragment2();
        fragment2.setArguments(null);
        //将第2个fragment显示出来
        transaction.replace(R.id.fragment_content, fragment2);
        //提交事务
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.bottom_bar_1:
                if (fragment1 == null){
                    fragment1 = new Fragment1();
                }
                //将１替换进来
                transaction.replace(R.id.fragment_content, fragment1);
                break;
            case R.id.bottom_bar_2:
                if (fragment2 == null){
                    fragment2 = new Fragment2();
                }
                //将2替换进来
                transaction.replace(R.id.fragment_content, fragment2);
                break;
            case R.id.bottom_bar_3:
                if (fragment1 == null){
                    fragment1 = new Fragment1();
                }
                transaction.replace(R.id.fragment_content, fragment1);
                //添加回退栈
                transaction.addToBackStack(null);
                break;
            case R.id.bottom_bar_4:
                if (fragment2 == null){
                    fragment2 = new Fragment2();
                }
                transaction.replace(R.id.fragment_content, fragment2);
                //添加到回退栈
                transaction.addToBackStack(null);
                break;
        }
        //提交事务
        transaction.commit();
    }

    public MainActivity() {
        super();
        Log.d(TAG, ">>> MainActivity()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, ">>> onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, ">>> onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, ">>> onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, ">>> onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, ">>> onDestroy");
    }
}
