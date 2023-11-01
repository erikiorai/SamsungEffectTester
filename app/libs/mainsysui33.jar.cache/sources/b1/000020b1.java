package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.ScrollView;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/NonInterceptingScrollView.class */
public class NonInterceptingScrollView extends ScrollView {
    public float mDownY;
    public boolean mPreventingIntercept;
    public boolean mScrollEnabled;
    public final int mTouchSlop;

    public NonInterceptingScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScrollEnabled = true;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        return this.mScrollEnabled && super.canScrollHorizontally(i);
    }

    @Override // android.view.View
    public boolean canScrollVertically(int i) {
        return this.mScrollEnabled && super.canScrollVertically(i);
    }

    public boolean isPreventingIntercept() {
        return this.mPreventingIntercept;
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mPreventingIntercept = false;
            if (canScrollVertically(1)) {
                this.mPreventingIntercept = true;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
            this.mDownY = motionEvent.getY();
        } else if (actionMasked == 2 && ((int) motionEvent.getY()) - this.mDownY < (-this.mTouchSlop) && !canScrollVertically(1)) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.ScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mPreventingIntercept = false;
            if (canScrollVertically(1)) {
                this.mPreventingIntercept = true;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            } else if (!canScrollVertically(-1)) {
                return false;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setScrollingEnabled(boolean z) {
        this.mScrollEnabled = z;
    }
}