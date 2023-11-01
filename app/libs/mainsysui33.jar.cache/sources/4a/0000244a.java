package com.android.systemui.screenshot;

import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScrollCaptureController;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/LongScreenshotData.class */
public class LongScreenshotData {
    public String mForegroundAppName;
    public boolean mNeedsMagnification;
    public final AtomicReference<ScrollCaptureController.LongScreenshot> mLongScreenshot = new AtomicReference<>();
    public final AtomicReference<ScreenshotController.TransitionDestination> mTransitionDestinationCallback = new AtomicReference<>();

    public String getForegroundAppName() {
        return this.mForegroundAppName;
    }

    public boolean getNeedsMagnification() {
        return this.mNeedsMagnification;
    }

    public void setForegroundAppName(String str) {
        this.mForegroundAppName = str;
    }

    public void setLongScreenshot(ScrollCaptureController.LongScreenshot longScreenshot) {
        this.mLongScreenshot.set(longScreenshot);
    }

    public void setNeedsMagnification(boolean z) {
        this.mNeedsMagnification = z;
    }

    public void setTransitionDestinationCallback(ScreenshotController.TransitionDestination transitionDestination) {
        this.mTransitionDestinationCallback.set(transitionDestination);
    }

    public ScrollCaptureController.LongScreenshot takeLongScreenshot() {
        return this.mLongScreenshot.getAndSet(null);
    }

    public ScreenshotController.TransitionDestination takeTransitionDestinationCallback() {
        return this.mTransitionDestinationCallback.getAndSet(null);
    }
}