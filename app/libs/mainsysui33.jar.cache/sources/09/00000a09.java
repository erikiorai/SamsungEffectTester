package com.airbnb.lottie;

import androidx.core.os.TraceCompat;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/L.class */
public class L {
    public static boolean DBG = false;
    public static int depthPastMaxDepth = 0;
    public static String[] sections;
    public static long[] startTimeNs;
    public static int traceDepth = 0;
    public static boolean traceEnabled = false;

    public static void beginSection(String str) {
        if (traceEnabled) {
            int i = traceDepth;
            if (i == 20) {
                depthPastMaxDepth++;
                return;
            }
            sections[i] = str;
            startTimeNs[i] = System.nanoTime();
            TraceCompat.beginSection(str);
            traceDepth++;
        }
    }

    public static float endSection(String str) {
        int i = depthPastMaxDepth;
        if (i > 0) {
            depthPastMaxDepth = i - 1;
            return ActionBarShadowController.ELEVATION_LOW;
        } else if (traceEnabled) {
            int i2 = traceDepth - 1;
            traceDepth = i2;
            if (i2 != -1) {
                if (str.equals(sections[i2])) {
                    TraceCompat.endSection();
                    return ((float) (System.nanoTime() - startTimeNs[traceDepth])) / 1000000.0f;
                }
                throw new IllegalStateException("Unbalanced trace call " + str + ". Expected " + sections[traceDepth] + ".");
            }
            throw new IllegalStateException("Can't end trace section. There are none.");
        } else {
            return ActionBarShadowController.ELEVATION_LOW;
        }
    }
}