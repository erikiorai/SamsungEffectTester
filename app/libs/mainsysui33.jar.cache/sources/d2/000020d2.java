package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSEvents.class */
public final class QSEvents {
    public static final QSEvents INSTANCE = new QSEvents();
    public static UiEventLogger qsUiEventsLogger = new UiEventLoggerImpl();

    public final UiEventLogger getQsUiEventsLogger() {
        return qsUiEventsLogger;
    }
}