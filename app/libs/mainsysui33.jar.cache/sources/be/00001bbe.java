package com.android.systemui.log.table;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBufferHelper;
import com.android.systemui.util.time.SystemClock;

/* loaded from: mainsysui33.jar:com/android/systemui/log/table/TableLogBufferFactory.class */
public final class TableLogBufferFactory {
    public final DumpManager dumpManager;
    public final SystemClock systemClock;

    public TableLogBufferFactory(DumpManager dumpManager, SystemClock systemClock) {
        this.dumpManager = dumpManager;
        this.systemClock = systemClock;
    }

    public final TableLogBuffer create(String str, int i) {
        TableLogBuffer tableLogBuffer = new TableLogBuffer(LogBufferHelper.Companion.adjustMaxSize(i), str, this.systemClock);
        this.dumpManager.registerNormalDumpable(str, tableLogBuffer);
        return tableLogBuffer;
    }
}