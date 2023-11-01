package com.android.systemui.plugins;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.WindowManager;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.io.PrintWriter;

@ProvidesInterface(action = NavigationEdgeBackPlugin.ACTION, version = 1)
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/NavigationEdgeBackPlugin.class */
public interface NavigationEdgeBackPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_NAVIGATION_EDGE_BACK_ACTION";
    public static final int VERSION = 1;

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/NavigationEdgeBackPlugin$BackCallback.class */
    public interface BackCallback {
        void cancelBack();

        void setTriggerBack(boolean z);

        void setTriggerLongSwipe(boolean z);

        void triggerBack(boolean z);
    }

    void dump(PrintWriter printWriter);

    void onMotionEvent(MotionEvent motionEvent);

    void setBackCallback(BackCallback backCallback);

    void setDisplaySize(Point point);

    void setInsets(int i, int i2);

    void setIsLeftPanel(boolean z);

    void setLayoutParams(WindowManager.LayoutParams layoutParams);

    default void setLongSwipeEnabled(boolean z) {
    }
}