package com.android.keyguard;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$id;
import java.util.Collections;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardStatusView.class */
public class KeyguardStatusView extends GridLayout {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public KeyguardClockSwitch mClockView;
    public float mDarkAmount;
    public KeyguardSliceView mKeyguardSlice;
    public View mMediaHostContainer;
    public ViewGroup mStatusViewContainer;

    public KeyguardStatusView(Context context) {
        this(context, null, 0);
    }

    public KeyguardStatusView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardStatusView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDarkAmount = ActionBarShadowController.ELEVATION_LOW;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mStatusViewContainer = (ViewGroup) findViewById(R$id.status_view_container);
        this.mClockView = (KeyguardClockSwitch) findViewById(R$id.keyguard_clock_container);
        if (KeyguardClockAccessibilityDelegate.isNeeded(((GridLayout) this).mContext)) {
            this.mClockView.setAccessibilityDelegate(new KeyguardClockAccessibilityDelegate(((GridLayout) this).mContext));
        }
        this.mKeyguardSlice = (KeyguardSliceView) findViewById(R$id.keyguard_slice_view);
        this.mMediaHostContainer = findViewById(R$id.status_view_media_container);
        updateDark();
    }

    @Override // android.widget.GridLayout, android.view.View
    public void onMeasure(int i, int i2) {
        Trace.beginSection("KeyguardStatusView#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    public void setChildrenTranslationY(float f, boolean z) {
        setChildrenTranslationYExcluding(f, z ? Set.of(this.mMediaHostContainer) : Collections.emptySet());
    }

    public final void setChildrenTranslationYExcluding(float f, Set<View> set) {
        for (int i = 0; i < this.mStatusViewContainer.getChildCount(); i++) {
            View childAt = this.mStatusViewContainer.getChildAt(i);
            if (!set.contains(childAt)) {
                childAt.setTranslationY(f);
            }
        }
    }

    public void updateDark() {
        this.mKeyguardSlice.setDarkAmount(this.mDarkAmount);
    }
}