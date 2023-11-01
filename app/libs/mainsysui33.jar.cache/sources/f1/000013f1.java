package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlInfoWrapper$customIconGetter$1.class */
public final /* synthetic */ class ControlInfoWrapper$customIconGetter$1 extends FunctionReferenceImpl implements Function2<ComponentName, String, Icon> {
    public static final ControlInfoWrapper$customIconGetter$1 INSTANCE = new ControlInfoWrapper$customIconGetter$1();

    public ControlInfoWrapper$customIconGetter$1() {
        super(2, ControlsModelKt.class, "nullIconGetter", "nullIconGetter(Landroid/content/ComponentName;Ljava/lang/String;)Landroid/graphics/drawable/Icon;", 1);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Icon invoke(ComponentName componentName, String str) {
        Icon nullIconGetter;
        nullIconGetter = ControlsModelKt.nullIconGetter(componentName, str);
        return nullIconGetter;
    }
}