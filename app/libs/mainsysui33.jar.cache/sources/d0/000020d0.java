package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSEditEvent.class */
public enum QSEditEvent implements UiEventLogger.UiEventEnum {
    QS_EDIT_REMOVE(210),
    QS_EDIT_ADD(211),
    QS_EDIT_MOVE(212),
    QS_EDIT_OPEN(213),
    QS_EDIT_CLOSED(214),
    QS_EDIT_RESET(215);
    
    private final int _id;

    QSEditEvent(int i) {
        this._id = i;
    }

    public int getId() {
        return this._id;
    }
}