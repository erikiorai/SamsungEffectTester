package com.android.systemui.log.table;

import com.android.systemui.Dumpable;
import com.android.systemui.plugins.util.RingBuffer;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/log/table/TableLogBuffer.class */
public final class TableLogBuffer implements Dumpable {
    public final RingBuffer<TableChange> buffer;
    public final String name;
    public final SystemClock systemClock;
    public final TableRowLoggerImpl tempRow;

    /* loaded from: mainsysui33.jar:com/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl.class */
    public static final class TableRowLoggerImpl implements TableRowLogger {
        public String columnPrefix;
        public final TableLogBuffer tableLogBuffer;
        public long timestamp;

        public TableRowLoggerImpl(long j, String str, TableLogBuffer tableLogBuffer) {
            this.timestamp = j;
            this.columnPrefix = str;
            this.tableLogBuffer = tableLogBuffer;
        }

        @Override // com.android.systemui.log.table.TableRowLogger
        public void logChange(String str, int i) {
            this.tableLogBuffer.logChange(this.timestamp, this.columnPrefix, str, i);
        }

        @Override // com.android.systemui.log.table.TableRowLogger
        public void logChange(String str, String str2) {
            this.tableLogBuffer.logChange(this.timestamp, this.columnPrefix, str, str2);
        }

        @Override // com.android.systemui.log.table.TableRowLogger
        public void logChange(String str, boolean z) {
            this.tableLogBuffer.logChange(this.timestamp, this.columnPrefix, str, z);
        }

        public final void setColumnPrefix(String str) {
            this.columnPrefix = str;
        }

        public final void setTimestamp(long j) {
            this.timestamp = j;
        }
    }

    public TableLogBuffer(int i, String str, SystemClock systemClock) {
        this.name = str;
        this.systemClock = systemClock;
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize must be > 0");
        }
        this.buffer = new RingBuffer<>(i, new Function0<TableChange>() { // from class: com.android.systemui.log.table.TableLogBuffer$buffer$1
            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final TableChange m3144invoke() {
                return new TableChange(0L, null, null, null, false, 0, null, 127, null);
            }
        });
        this.tempRow = new TableRowLoggerImpl(0L, "", this);
    }

    public final void dump(TableChange tableChange, PrintWriter printWriter) {
        if (tableChange.hasData()) {
            printWriter.print(TableLogBufferKt.getTABLE_LOG_DATE_FORMAT().format(Long.valueOf(tableChange.getTimestamp())));
            printWriter.print("|");
            printWriter.print(tableChange.getName());
            printWriter.print("|");
            printWriter.print(tableChange.getVal());
            printWriter.println();
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        synchronized (this) {
            printWriter.println("SystemUI StateChangeTableSection START: " + this.name);
            printWriter.println("version 1");
            int size = this.buffer.getSize();
            for (int i = 0; i < size; i++) {
                dump(this.buffer.get(i), printWriter);
            }
            printWriter.println("SystemUI StateChangeTableSection END: " + this.name);
        }
    }

    public final void logChange(long j, String str, String str2, int i) {
        obtain(j, str, str2).set(i);
    }

    public final void logChange(long j, String str, String str2, String str3) {
        obtain(j, str, str2).set(str3);
    }

    public final void logChange(long j, String str, String str2, boolean z) {
        obtain(j, str, str2).set(z);
    }

    public final void logChange(String str, String str2, int i) {
        logChange(this.systemClock.currentTimeMillis(), str, str2, i);
    }

    public final void logChange(String str, String str2, String str3) {
        logChange(this.systemClock.currentTimeMillis(), str, str2, str3);
    }

    public final void logChange(String str, String str2, boolean z) {
        logChange(this.systemClock.currentTimeMillis(), str, str2, z);
    }

    public final void logChange(String str, Function1<? super TableRowLogger, Unit> function1) {
        synchronized (this) {
            TableRowLoggerImpl tableRowLoggerImpl = this.tempRow;
            tableRowLoggerImpl.setTimestamp(this.systemClock.currentTimeMillis());
            tableRowLoggerImpl.setColumnPrefix(str);
            function1.invoke(tableRowLoggerImpl);
        }
    }

    public final <T extends Diffable<T>> void logDiffs(String str, T t, T t2) {
        synchronized (this) {
            TableRowLoggerImpl tableRowLoggerImpl = this.tempRow;
            tableRowLoggerImpl.setTimestamp(this.systemClock.currentTimeMillis());
            tableRowLoggerImpl.setColumnPrefix(str);
            t2.logDiffs(t, tableRowLoggerImpl);
        }
    }

    public final TableChange obtain(long j, String str, String str2) {
        TableChange advance;
        synchronized (this) {
            verifyValidName(str, str2);
            advance = this.buffer.advance();
            advance.reset(j, str, str2);
        }
        return advance;
    }

    public final void verifyValidName(String str, String str2) {
        if (StringsKt__StringsKt.contains$default(str, "|", false, 2, (Object) null)) {
            throw new IllegalArgumentException("columnPrefix cannot contain | but was " + str);
        } else if (StringsKt__StringsKt.contains$default(str2, "|", false, 2, (Object) null)) {
            throw new IllegalArgumentException("columnName cannot contain | but was " + str2);
        }
    }
}