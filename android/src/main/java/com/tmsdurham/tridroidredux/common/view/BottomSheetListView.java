package com.tmsdurham.tridroidredux.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by billyparrish on 3/9/17 for Shortlist.
 */


public class BottomSheetListView extends ListView {
    public BottomSheetListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (canScrollVertically(this)) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }

    public boolean canScrollVertically(AbsListView view) {
        boolean canScroll = false;

        if (view != null && view.getChildCount() > 0) {
            boolean isOnTop = view.getFirstVisiblePosition() != 0 || view.getChildAt(0).getTop() != 0;
            boolean isAllItemsVisible = isOnTop && view.getLastVisiblePosition() == view.getChildCount();

            if (isOnTop || isAllItemsVisible) {
                canScroll = true;
            }
        }

        return canScroll;
    }
}