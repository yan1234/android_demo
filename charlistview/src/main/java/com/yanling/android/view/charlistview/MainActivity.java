package com.yanling.android.view.charlistview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页面
 * @author yanling
 * @date 2017-04-25
 */
public class MainActivity extends Activity{

    //定义RecycleView和适配器
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    //定义字符列表
    private ListView lv_charlist;
    //定义字符预览控件
    private TextView tv_preview;

    //存储↑、*、A-Z、#等29个字符
    private char[] charlist = new char[29];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initEvent();
    }

    private void initData(){
        //初始化字符列表
        charlist[0] = '↑';
        charlist[1] = '*';
        for (int i = 0; i < 26; i ++){
            charlist[i + 2] = (char)('A' + i);
        }
        charlist[28] = '#';
    }

    private void initView(){
        tv_preview = (TextView)this.findViewById(R.id.layout_charlist_preview);
        lv_charlist = (ListView)this.findViewById(R.id.layout_charlist_list);
        recyclerView = (RecyclerView)this.findViewById(R.id.layout_content);
        //封装数据
        List<Map<String, Object>> listitems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 29; i++){
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("name", charlist[i]);
            listitems.add(item);
        }
        //加载适配器
        lv_charlist.setAdapter(new SimpleAdapter(
                this,
                listitems,
                R.layout.item_charlist,
                new String[]{"name"},
                new int[]{R.id.item_charlist_char}
        ));
    }

    private void initEvent(){

        lv_charlist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                    case MotionEvent.ACTION_MOVE:
                        int position = (int)(event.getY() / (v.getHeight() / 29));
                        Log.d("haha", ">>>>>>>>"+ position);
                        tv_preview.setText(""+charlist[position]);
                        tv_preview.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        tv_preview.setVisibility(View.INVISIBLE);
                        break;
                }
                return true;
            }
        });

        //添加适配器
        recyclerView.setAdapter(adapter = new MyAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    /**
     * RecyclerView适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(
                    LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.item_charlist, parent, false)
            );
            return holder;
        }

        @Override
        public int getItemCount() {
            return charlist.length;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText("" + charlist[position]);
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            public TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView)itemView.findViewById(R.id.item_charlist_char);
            }
        }
    }
}
