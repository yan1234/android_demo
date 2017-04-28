package com.yanling.android.demo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 标题fragment
 * @author yanling
 * @date 2017-04-06
 */
public class TitleFragment extends Fragment{

    private ImageButton leftBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载布局
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        leftBtn = (ImageButton)view.findViewById(R.id.fragment_title_left_btn);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "I am a ImageButton in TitleFragment",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
