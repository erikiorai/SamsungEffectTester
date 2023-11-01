package com.android.systemui.media.controls.ui;

import com.android.systemui.monet.ColorScheme;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaColorSchemesKt.class */
public final class MediaColorSchemesKt {
    public static final int accentPrimaryFromScheme(ColorScheme colorScheme) {
        return colorScheme.getAccent1().get(2).intValue();
    }

    public static final int accentSecondaryFromScheme(ColorScheme colorScheme) {
        return colorScheme.getAccent1().get(3).intValue();
    }

    public static final int backgroundEndFromScheme(ColorScheme colorScheme) {
        return colorScheme.getAccent1().get(8).intValue();
    }

    public static final int backgroundStartFromScheme(ColorScheme colorScheme) {
        return colorScheme.getAccent2().get(8).intValue();
    }

    public static final int surfaceFromScheme(ColorScheme colorScheme) {
        return colorScheme.getAccent2().get(9).intValue();
    }

    public static final int textPrimaryFromScheme(ColorScheme colorScheme) {
        return colorScheme.getNeutral1().get(1).intValue();
    }

    public static final int textPrimaryInverseFromScheme(ColorScheme colorScheme) {
        return colorScheme.getNeutral1().get(10).intValue();
    }

    public static final int textSecondaryFromScheme(ColorScheme colorScheme) {
        return colorScheme.getNeutral2().get(3).intValue();
    }

    public static final int textTertiaryFromScheme(ColorScheme colorScheme) {
        return colorScheme.getNeutral2().get(5).intValue();
    }
}