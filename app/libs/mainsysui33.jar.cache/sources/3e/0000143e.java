package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import com.android.systemui.controls.CustomIconCache;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/FavoritesModel$elements$1$1.class */
public final /* synthetic */ class FavoritesModel$elements$1$1 extends FunctionReferenceImpl implements Function2<ComponentName, String, Icon> {
    public FavoritesModel$elements$1$1(Object obj) {
        super(2, obj, CustomIconCache.class, "retrieve", "retrieve(Landroid/content/ComponentName;Ljava/lang/String;)Landroid/graphics/drawable/Icon;", 0);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Icon invoke(ComponentName componentName, String str) {
        return ((CustomIconCache) ((CallableReference) this).receiver).retrieve(componentName, str);
    }
}