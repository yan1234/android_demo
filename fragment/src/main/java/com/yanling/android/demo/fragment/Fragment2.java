package com.yanling.android.demo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 内容fragment
 * @author yanling
 * @date 2017-04-06
 */
public class Fragment2 extends Fragment{
    private static final String TAG = Fragment2.class.getSimpleName();

    public Fragment2() {
        super();
        Log.d(TAG, ">>> Fragment2()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, ">>> onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, ">>> onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, ">>> onCreateView");
        return inflater.inflate(R.layout.fragment_2, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, ">>> onActivityCreate");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, ">>> onViewCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, ">>> onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, ">>> onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, ">>> onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, ">>> onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, ">>> onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, ">>> onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, ">>> onDetach");
    }
}
