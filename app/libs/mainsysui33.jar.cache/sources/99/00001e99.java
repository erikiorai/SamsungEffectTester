package com.android.systemui.navigationbar.buttons;

import android.view.View;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/NearestTouchFrame$$ExternalSyntheticLambda0.class */
public final /* synthetic */ class NearestTouchFrame$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((View) obj).isAttachedToWindow();
    }
}