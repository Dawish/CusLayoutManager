package com.xlg.cuslayoutmanager;

import android.graphics.Color;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by xlg on 2017/5/17.
 */

public class HomeAdapter extends RecyclerView.Adapter {

    private ArrayList<RectF> mItemRectFList;
    private int[] colors = new int[]{
            Color.BLACK,
            Color.BLUE,
            Color.parseColor("#ff8040"),
            Color.YELLOW,
            Color.parseColor("#949449"),
            Color.RED,
            Color.GREEN,
            Color.GRAY,
            Color.parseColor("#8600ff"),
            Color.parseColor("#ff8040"),
            Color.GREEN,
            Color.parseColor("#5151a2"),
            Color.BLUE,
            Color.RED,
            Color.parseColor("#977c00"),
            Color.GREEN,
            Color.parseColor("#949449"),
            Color.parseColor("#804040")
    };


    public HomeAdapter(ArrayList<RectF> itemRectFList) {
        mItemRectFList = itemRectFList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(new ImageView(parent.getContext()));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ImageView) holder.itemView).setImageResource(R.mipmap.ic_launcher);
        ((ImageView) holder.itemView).setBackgroundColor(colors[position]);

    }

    @Override
    public int getItemCount() {
        return mItemRectFList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
