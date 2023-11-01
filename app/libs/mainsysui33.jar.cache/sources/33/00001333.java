package com.android.systemui.clipboardoverlay;

import com.android.internal.logging.UiEventLogger;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayEvent.class */
public enum ClipboardOverlayEvent implements UiEventLogger.UiEventEnum {
    CLIPBOARD_OVERLAY_ENTERED(949),
    CLIPBOARD_OVERLAY_UPDATED(950),
    CLIPBOARD_OVERLAY_EDIT_TAPPED(951),
    CLIPBOARD_OVERLAY_SHARE_TAPPED(1067),
    CLIPBOARD_OVERLAY_ACTION_SHOWN(1260),
    CLIPBOARD_OVERLAY_ACTION_TAPPED(952),
    CLIPBOARD_OVERLAY_REMOTE_COPY_TAPPED(953),
    CLIPBOARD_OVERLAY_TIMED_OUT(954),
    CLIPBOARD_OVERLAY_DISMISS_TAPPED(955),
    CLIPBOARD_OVERLAY_SWIPE_DISMISSED(956),
    CLIPBOARD_OVERLAY_TAP_OUTSIDE(1077),
    CLIPBOARD_OVERLAY_DISMISSED_OTHER(1078),
    CLIPBOARD_TOAST_SHOWN(1270);
    
    private final int mId;

    ClipboardOverlayEvent(int i) {
        this.mId = i;
    }

    public int getId() {
        return this.mId;
    }
}