package com.android.systemui.animation;

import android.view.View;
import android.window.SurfaceSyncer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewRootSync.class */
public final class ViewRootSync {
    public static final ViewRootSync INSTANCE = new ViewRootSync();
    public static SurfaceSyncer surfaceSyncer;

    public final void synchronizeNextDraw(View view, View view2, final Function0<Unit> function0) {
        if (!view.isAttachedToWindow() || view.getViewRootImpl() == null || !view2.isAttachedToWindow() || view2.getViewRootImpl() == null || Intrinsics.areEqual(view.getViewRootImpl(), view2.getViewRootImpl())) {
            function0.invoke();
            return;
        }
        SurfaceSyncer surfaceSyncer2 = new SurfaceSyncer();
        int i = surfaceSyncer2.setupSync(new Runnable() { // from class: com.android.systemui.animation.ViewRootSync$synchronizeNextDraw$1$syncId$1
            @Override // java.lang.Runnable
            public final void run() {
                function0.invoke();
            }
        });
        surfaceSyncer2.addToSync(i, view);
        surfaceSyncer2.addToSync(i, view2);
        surfaceSyncer2.markSyncReady(i);
        surfaceSyncer = surfaceSyncer2;
    }
}