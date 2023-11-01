package com.android.systemui;

import android.content.Context;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/* loaded from: mainsysui33.jar:com/android/systemui/RegionInterceptingFrameLayout.class */
public class RegionInterceptingFrameLayout extends FrameLayout {
    public final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsListener;

    /* loaded from: mainsysui33.jar:com/android/systemui/RegionInterceptingFrameLayout$RegionInterceptableView.class */
    public interface RegionInterceptableView {
        Region getInterceptRegion();

        default boolean shouldInterceptTouch() {
            return false;
        }
    }

    public RegionInterceptingFrameLayout(Context context) {
        super(context);
        this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.RegionInterceptingFrameLayout$$ExternalSyntheticLambda0
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                RegionInterceptingFrameLayout.this.lambda$new$0(internalInsetsInfo);
            }
        };
    }

    public RegionInterceptingFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.RegionInterceptingFrameLayout$$ExternalSyntheticLambda0
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                RegionInterceptingFrameLayout.this.lambda$new$0(internalInsetsInfo);
            }
        };
    }

    public RegionInterceptingFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.RegionInterceptingFrameLayout$$ExternalSyntheticLambda0
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                RegionInterceptingFrameLayout.this.lambda$new$0(internalInsetsInfo);
            }
        };
    }

    public RegionInterceptingFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.RegionInterceptingFrameLayout$$ExternalSyntheticLambda0
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                RegionInterceptingFrameLayout.this.lambda$new$0(internalInsetsInfo);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        Region interceptRegion;
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.setEmpty();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof RegionInterceptableView) {
                RegionInterceptableView regionInterceptableView = (RegionInterceptableView) childAt;
                if (regionInterceptableView.shouldInterceptTouch() && (interceptRegion = regionInterceptableView.getInterceptRegion()) != null) {
                    internalInsetsInfo.touchableRegion.op(interceptRegion, Region.Op.UNION);
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsListener);
    }
}