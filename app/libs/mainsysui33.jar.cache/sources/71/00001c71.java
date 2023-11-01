package com.android.systemui.media.controls.ui;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/ColorSchemeTransition$textPrimaryInverse$1.class */
public final /* synthetic */ class ColorSchemeTransition$textPrimaryInverse$1 extends FunctionReferenceImpl implements Function1<ColorScheme, Integer> {
    public static final ColorSchemeTransition$textPrimaryInverse$1 INSTANCE = new ColorSchemeTransition$textPrimaryInverse$1();

    public ColorSchemeTransition$textPrimaryInverse$1() {
        super(1, MediaColorSchemesKt.class, "textPrimaryInverseFromScheme", "textPrimaryInverseFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Integer invoke(ColorScheme colorScheme) {
        return Integer.valueOf(MediaColorSchemesKt.textPrimaryInverseFromScheme(colorScheme));
    }
}