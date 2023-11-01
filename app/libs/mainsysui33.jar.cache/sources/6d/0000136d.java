package com.android.systemui.common.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/common/ui/view/LaunchableLinearLayout.class */
public final class LaunchableLinearLayout extends LinearLayout implements LaunchableView {
    public final LaunchableViewDelegate delegate;

    public LaunchableLinearLayout(Context context) {
        super(context);
        this.delegate = new LaunchableViewDelegate(this, new Function1<Integer, Unit>() { // from class: com.android.systemui.common.ui.view.LaunchableLinearLayout$delegate$1
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                LaunchableLinearLayout.access$setVisibility$s1127291599(LaunchableLinearLayout.this, i);
            }
        });
    }

    public LaunchableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.delegate = new LaunchableViewDelegate(this, new Function1<Integer, Unit>() { // from class: com.android.systemui.common.ui.view.LaunchableLinearLayout$delegate$1
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                LaunchableLinearLayout.access$setVisibility$s1127291599(LaunchableLinearLayout.this, i);
            }
        });
    }

    public LaunchableLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.delegate = new LaunchableViewDelegate(this, new Function1<Integer, Unit>() { // from class: com.android.systemui.common.ui.view.LaunchableLinearLayout$delegate$1
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i2) {
                LaunchableLinearLayout.access$setVisibility$s1127291599(LaunchableLinearLayout.this, i2);
            }
        });
    }

    public LaunchableLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.delegate = new LaunchableViewDelegate(this, new Function1<Integer, Unit>() { // from class: com.android.systemui.common.ui.view.LaunchableLinearLayout$delegate$1
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i22) {
                LaunchableLinearLayout.access$setVisibility$s1127291599(LaunchableLinearLayout.this, i22);
            }
        });
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.common.ui.view.LaunchableLinearLayout$delegate$1.invoke(int):void] */
    public static final /* synthetic */ void access$setVisibility$s1127291599(LaunchableLinearLayout launchableLinearLayout, int i) {
        super.setVisibility(i);
    }

    @Override // com.android.systemui.animation.LaunchableView
    public void setShouldBlockVisibilityChanges(boolean z) {
        this.delegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        this.delegate.setVisibility(i);
    }
}