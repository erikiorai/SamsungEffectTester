package com.android.systemui.log.table;

import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: mainsysui33.jar:com/android/systemui/log/table/TableLogBufferKt.class */
public final class TableLogBufferKt {
    public static final SimpleDateFormat TABLE_LOG_DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);

    public static final SimpleDateFormat getTABLE_LOG_DATE_FORMAT() {
        return TABLE_LOG_DATE_FORMAT;
    }
}