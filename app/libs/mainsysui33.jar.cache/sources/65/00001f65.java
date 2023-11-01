package com.android.systemui.plugins;

import android.content.res.Resources;
import com.android.systemui.plugins.log.LogBuffer;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockController.class */
public interface ClockController {

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockController$DefaultImpls.class */
    public static final class DefaultImpls {
        public static void dump(ClockController clockController, PrintWriter printWriter) {
        }

        public static void initialize(ClockController clockController, Resources resources, float f, float f2) {
            clockController.getEvents().onColorPaletteChanged(resources);
            clockController.getAnimations().doze(f);
            clockController.getAnimations().fold(f2);
            clockController.getEvents().onTimeTick();
        }

        public static void setLogBuffer(ClockController clockController, LogBuffer logBuffer) {
        }
    }

    void dump(PrintWriter printWriter);

    ClockAnimations getAnimations();

    ClockEvents getEvents();

    ClockFaceController getLargeClock();

    ClockFaceController getSmallClock();

    void initialize(Resources resources, float f, float f2);

    void setLogBuffer(LogBuffer logBuffer);
}