package com.android.systemui.media.controls.ui;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHierarchyManagerKt.class */
public final class MediaHierarchyManagerKt {
    public static final String TAG = MediaHierarchyManager.class.getSimpleName();
    public static final Rect EMPTY_RECT = new Rect();

    public static final boolean isShownNotFaded(View view) {
        ViewParent parent;
        while (view.getVisibility() == 0) {
            if ((view.getAlpha() == ActionBarShadowController.ELEVATION_LOW) || (parent = view.getParent()) == null) {
                return false;
            }
            if (!(parent instanceof View)) {
                return true;
            }
            view = (View) parent;
        }
        return false;
    }
}