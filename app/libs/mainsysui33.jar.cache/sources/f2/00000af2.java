package com.android.app.motiontool;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/UnknownTraceIdException.class */
public final class UnknownTraceIdException extends Exception {
    private final int traceId;

    public UnknownTraceIdException(int i) {
        this.traceId = i;
    }

    public final int getTraceId() {
        return this.traceId;
    }
}