package com.android.systemui.controls.management;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.qs.PageIndicator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ManagementPageIndicator.class */
public final class ManagementPageIndicator extends PageIndicator {
    public Function1<? super Integer, Unit> visibilityListener;

    public ManagementPageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.visibilityListener = new Function1<Integer, Unit>() { // from class: com.android.systemui.controls.management.ManagementPageIndicator$visibilityListener$1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
            }
        };
    }

    @Override // android.view.View
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (Intrinsics.areEqual(view, this)) {
            this.visibilityListener.invoke(Integer.valueOf(i));
        }
    }

    @Override // com.android.systemui.qs.PageIndicator
    public void setLocation(float f) {
        if (getLayoutDirection() == 1) {
            super.setLocation((getChildCount() - 1) - f);
        } else {
            super.setLocation(f);
        }
    }

    public final void setVisibilityListener(Function1<? super Integer, Unit> function1) {
        this.visibilityListener = function1;
    }
}