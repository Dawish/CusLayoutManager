package com.xlg.cuslayoutmanager;

import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by xlg on 2017/5/17.
 */

public class HomeLayoutManager extends RecyclerView.LayoutManager {
    private LayoutState mLayoutState;
    private ArrayList<RectF> mItemRectFList;

    //用来判断是否到边界了
    private int mTotalW;

    //主要用来标志item是否已在当前界面上
    private final HiveBucket mBooleanMap = new HiveBucket();

    public HomeLayoutManager(ArrayList<RectF> itemRectFList) {
        mItemRectFList = itemRectFList;
        init();
    }

    private void init() {
        mLayoutState = new LayoutState();

        mBooleanMap.reset();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        super.onLayoutChildren(recycler, state);
        int itemCount = state.getItemCount();
        if (itemCount <= 0) {
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        //回收当前所有的view到Scrap里
        detachAndScrapAttachedViews(recycler);
        //初始化标志位
        mBooleanMap.reset();
        // 遍历所有的item
        fill(recycler, state);

    }


    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }
        //更新recycleview的控件位置
        updateLayoutState();
        mTotalW = 0;
        int itemCount = state.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            // 得到当前position下的视图显示区域
            RectF bounds = new RectF(mItemRectFList.get(i));
            //需要根据你的实际情况来设置边界值
            if (bounds.right > mTotalW) {
                mTotalW = (int) bounds.right;
            }

            //注：这边事先获取item的位置来判断是否要显示在屏幕上后再来获取itemView的对象
            if (!mBooleanMap.get(i) && RectF.intersects(bounds, mLayoutState.containerRect)) {
                // 通过recycler得到该位置上的View，Recycler负责是否使用旧的还是生成新的View。
                View view = recycler.getViewForPosition(i);

                bounds.offset(mLayoutState.offsetX, mLayoutState.offsetY);
                // 然后我们将得到的View添加到Recycler中
                addView(view);
                //标志当前item已在界面上
                mBooleanMap.set(i);
                // 然后测量View带Margin的的尺寸
                measureChildWithMargins(view, 0, 0);
                // 然后layout带Margin的View，将View放置到对应的位置
                layoutDecoratedWithMargins(view, (int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom);
            }
        }
        //预留50的边界
        mTotalW += 50;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //水平滑动开关
    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
//

        //实际要滑动的距离
        int travel = dx;

        //判断是否到边界
        //如果滑动到最左边
        if (-mLayoutState.offsetX + dx < 0) {
            travel = mLayoutState.offsetX;
        } else if (-mLayoutState.offsetX + dx + getWidth() > mTotalW) {//如果滑动到最右部
            travel = mTotalW + mLayoutState.offsetX - getWidth();
        }


        //移动
        offsetChildrenHorizontal(-travel);
        //记录当前移动距离，绘制界面时需要用到
        mLayoutState.offsetX -= travel;
        //回收view的Recycle
        scrapOutSetViews(recycler);
        //重新绘制界面：在滑动过程中需要将新进入屏幕的view设置出来
        fill(recycler, state);
        return travel;
    }

    private void scrapOutSetViews(RecyclerView.Recycler recycler) {
        int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View view = getChildAt(i);
            if (!RectF.intersects(new RectF(0, 0, getWidth(), getHeight()), new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()))) {
                int position = getPosition(view);
                mBooleanMap.clear(position);
                removeAndRecycleViewAt(i, recycler);
            }
        }
    }

    //更新父控件窗口位置
    private void updateLayoutState() {
        mLayoutState.containerRect.set(0, 0, getWidth(), getHeight());
        mLayoutState.containerRect.offset(-mLayoutState.offsetX, -mLayoutState.offsetY);
    }

    private class LayoutState {

        int offsetX;
        int offsetY;

        final RectF containerRect = new RectF();
    }
}
