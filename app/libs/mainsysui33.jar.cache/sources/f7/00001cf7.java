package com.android.systemui.media.controls.ui;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Gefingerpoken;
import com.android.wm.shell.animation.PhysicsAnimatorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaScrollView.class */
public final class MediaScrollView extends HorizontalScrollView {
    public float animationTargetX;
    public ViewGroup contentContainer;
    public Gefingerpoken touchListener;

    public MediaScrollView(Context context) {
        this(context, null, 0, 6, null);
    }

    public MediaScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public MediaScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public /* synthetic */ MediaScrollView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public final void cancelCurrentScroll() {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 0);
        obtain.setSource(4098);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    public final ViewGroup getContentContainer() {
        ViewGroup viewGroup = this.contentContainer;
        if (viewGroup != null) {
            return viewGroup;
        }
        return null;
    }

    public final float getContentTranslation() {
        return PhysicsAnimatorKt.getPhysicsAnimator(getContentContainer()).isRunning() ? this.animationTargetX : getContentContainer().getTranslationX();
    }

    public final int getRelativeScrollX() {
        return transformScrollX(getScrollX());
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.contentContainer = (ViewGroup) getChildAt(0);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.touchListener;
        boolean z = false;
        boolean onInterceptTouchEvent = gefingerpoken != null ? gefingerpoken.onInterceptTouchEvent(motionEvent) : false;
        if (super.onInterceptTouchEvent(motionEvent) || onInterceptTouchEvent) {
            z = true;
        }
        return z;
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.touchListener;
        boolean z = false;
        boolean onTouchEvent = gefingerpoken != null ? gefingerpoken.onTouchEvent(motionEvent) : false;
        if (super.onTouchEvent(motionEvent) || onTouchEvent) {
            z = true;
        }
        return z;
    }

    @Override // android.view.View
    public boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        if (getContentTranslation() == ActionBarShadowController.ELEVATION_LOW) {
            return super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
        }
        return false;
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public void scrollTo(int i, int i2) {
        int i3 = ((HorizontalScrollView) this).mScrollX;
        if (i3 == i && ((HorizontalScrollView) this).mScrollY == i2) {
            return;
        }
        int i4 = ((HorizontalScrollView) this).mScrollY;
        ((HorizontalScrollView) this).mScrollX = i;
        ((HorizontalScrollView) this).mScrollY = i2;
        invalidateParentCaches();
        onScrollChanged(((HorizontalScrollView) this).mScrollX, ((HorizontalScrollView) this).mScrollY, i3, i4);
        if (awakenScrollBars()) {
            return;
        }
        postInvalidateOnAnimation();
    }

    public final void setAnimationTargetX(float f) {
        this.animationTargetX = f;
    }

    public final void setRelativeScrollX(int i) {
        setScrollX(transformScrollX(i));
    }

    public final void setTouchListener(Gefingerpoken gefingerpoken) {
        this.touchListener = gefingerpoken;
    }

    public final int transformScrollX(int i) {
        int i2 = i;
        if (isLayoutRtl()) {
            i2 = (getContentContainer().getWidth() - getWidth()) - i;
        }
        return i2;
    }
}