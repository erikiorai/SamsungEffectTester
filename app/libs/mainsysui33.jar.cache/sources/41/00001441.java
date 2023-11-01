package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.res.Resources;
import com.android.systemui.R$string;
import com.android.systemui.util.PluralMessageFormaterKt;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/FavoritesRenderer.class */
public final class FavoritesRenderer {
    public final Function1<ComponentName, Integer> favoriteFunction;
    public final Resources resources;

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlin.jvm.functions.Function1<? super android.content.ComponentName, java.lang.Integer> */
    /* JADX WARN: Multi-variable type inference failed */
    public FavoritesRenderer(Resources resources, Function1<? super ComponentName, Integer> function1) {
        this.resources = resources;
        this.favoriteFunction = function1;
    }

    public final String renderFavoritesForComponent(ComponentName componentName) {
        int intValue = ((Number) this.favoriteFunction.invoke(componentName)).intValue();
        return intValue != 0 ? PluralMessageFormaterKt.icuMessageFormat(this.resources, R$string.controls_number_of_favorites, intValue) : null;
    }
}