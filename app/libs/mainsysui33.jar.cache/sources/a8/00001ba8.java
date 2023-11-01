package com.android.systemui.log.table;

/* loaded from: mainsysui33.jar:com/android/systemui/log/table/Diffable.class */
public interface Diffable<T> {

    /* loaded from: mainsysui33.jar:com/android/systemui/log/table/Diffable$DefaultImpls.class */
    public static final class DefaultImpls {
        public static <T> void logFull(Diffable<T> diffable, TableRowLogger tableRowLogger) {
        }
    }

    void logDiffs(T t, TableRowLogger tableRowLogger);

    void logFull(TableRowLogger tableRowLogger);
}