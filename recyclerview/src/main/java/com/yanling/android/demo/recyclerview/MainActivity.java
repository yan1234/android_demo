package com.yanling.android.demo.recyclerview;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView测试demo
 * @author yanling
 * @date 2017-04-24
 */
public class MainActivity extends Activity{

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    //定义图片列表
    private List<String> images = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolverImages();
        recyclerView = (RecyclerView)this.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter = new MyAdapter());
        recyclerView.bringToFront();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //构造ViewHolder对象
            MyViewHolder viewHolder = new MyViewHolder(
                    LayoutInflater.from(MainActivity.this)
                            .inflate(R.layout.item_recyclerview, parent, false)
            );
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            //绑定数据
            //利用Glide加载图片数据
            Glide.with(MainActivity.this).load(images.get(position))
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            public ImageView imageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.item_image);
            }
        }
    }

    private void resolverImages(){
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()){
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            images.add(path);
        }

        cursor.close();
    }
}
