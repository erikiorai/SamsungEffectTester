package com.android.systemui.clipboardoverlay.dagger;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R$layout;
import com.android.systemui.clipboardoverlay.ClipboardOverlayView;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/dagger/ClipboardOverlayModule.class */
public interface ClipboardOverlayModule {
    static ClipboardOverlayView provideClipboardOverlayView(Context context) {
        return (ClipboardOverlayView) LayoutInflater.from(context).inflate(R$layout.clipboard_overlay, (ViewGroup) null);
    }

    static Context provideWindowContext(DisplayManager displayManager, Context context) {
        return context.createWindowContext(displayManager.getDisplay(0), 2036, null);
    }
}