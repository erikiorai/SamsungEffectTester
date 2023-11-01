package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsController;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsProviderSelectorActivity$onStart$2.class */
public final /* synthetic */ class ControlsProviderSelectorActivity$onStart$2 extends FunctionReferenceImpl implements Function1<ComponentName, Integer> {
    public ControlsProviderSelectorActivity$onStart$2(Object obj) {
        super(1, obj, ControlsController.class, "countFavoritesForComponent", "countFavoritesForComponent(Landroid/content/ComponentName;)I", 0);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Integer invoke(ComponentName componentName) {
        return Integer.valueOf(((ControlsController) ((CallableReference) this).receiver).countFavoritesForComponent(componentName));
    }
}