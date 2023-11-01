package com.android.systemui.clipboardoverlay;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.screenshot.TimeoutHandler;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayControllerLegacyFactory.class */
public class ClipboardOverlayControllerLegacyFactory {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final BroadcastSender mBroadcastSender;
    public final UiEventLogger mUiEventLogger;

    public ClipboardOverlayControllerLegacyFactory(BroadcastDispatcher broadcastDispatcher, BroadcastSender broadcastSender, UiEventLogger uiEventLogger) {
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mBroadcastSender = broadcastSender;
        this.mUiEventLogger = uiEventLogger;
    }

    public ClipboardOverlayControllerLegacy create(Context context) {
        return new ClipboardOverlayControllerLegacy(context, this.mBroadcastDispatcher, this.mBroadcastSender, new TimeoutHandler(context), this.mUiEventLogger);
    }
}