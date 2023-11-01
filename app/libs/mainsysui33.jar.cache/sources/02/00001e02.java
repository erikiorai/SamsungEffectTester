package com.android.systemui.monet;

import com.android.internal.graphics.cam.Cam;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.List;
import kotlin.Pair;

/* loaded from: mainsysui33.jar:com/android/systemui/monet/Hue.class */
public interface Hue {

    /* loaded from: mainsysui33.jar:com/android/systemui/monet/Hue$DefaultImpls.class */
    public static final class DefaultImpls {
        public static double getHueRotation(Hue hue, float f, List<Pair<Integer, Integer>> list) {
            int i = 0;
            float floatValue = ((f < ActionBarShadowController.ELEVATION_LOW || f >= 360.0f) ? 0 : Float.valueOf(f)).floatValue();
            int size = list.size() - 2;
            if (size >= 0) {
                while (true) {
                    float intValue = ((Number) list.get(i).getFirst()).intValue();
                    int i2 = i + 1;
                    float intValue2 = ((Number) list.get(i2).getFirst()).intValue();
                    if (intValue <= floatValue && floatValue < intValue2) {
                        return ColorScheme.Companion.wrapDegreesDouble(floatValue + ((Number) list.get(i).getSecond()).doubleValue());
                    }
                    if (i == size) {
                        break;
                    }
                    i = i2;
                }
            }
            return f;
        }
    }

    double get(Cam cam);
}