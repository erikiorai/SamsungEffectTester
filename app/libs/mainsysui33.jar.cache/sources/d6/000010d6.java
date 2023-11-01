package com.android.systemui.animation;

import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/LaunchableViewDelegate.class */
public final class LaunchableViewDelegate implements LaunchableView {
    public boolean blockVisibilityChanges;
    public int lastVisibility;
    public final Function1<Integer, Unit> superSetVisibility;
    public final View view;

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public LaunchableViewDelegate(View view, Function1<? super Integer, Unit> function1) {
        this.view = view;
        this.superSetVisibility = function1;
        this.lastVisibility = view.getVisibility();
    }

    @Override // com.android.systemui.animation.LaunchableView
    public void setShouldBlockVisibilityChanges(boolean z) {
        if (z == this.blockVisibilityChanges) {
            return;
        }
        this.blockVisibilityChanges = z;
        if (z) {
            this.lastVisibility = this.view.getVisibility();
        } else if (this.lastVisibility == 0) {
            this.superSetVisibility.invoke(4);
            this.superSetVisibility.invoke(0);
        } else {
            this.superSetVisibility.invoke(0);
            this.superSetVisibility.invoke(Integer.valueOf(this.lastVisibility));
        }
    }

    public final void setVisibility(int i) {
        if (this.blockVisibilityChanges) {
            this.lastVisibility = i;
        } else {
            this.superSetVisibility.invoke(Integer.valueOf(i));
        }
    }
}