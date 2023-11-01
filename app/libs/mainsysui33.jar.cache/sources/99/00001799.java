package com.android.systemui.dump;

import android.content.Context;
import android.util.Log;
import com.android.systemui.util.io.Files;
import com.android.systemui.util.time.SystemClock;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jdk7.AutoCloseableKt;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/LogBufferEulogizer.class */
public final class LogBufferEulogizer {
    public final DumpManager dumpManager;
    public final Files files;
    public final Path logPath;
    public final long maxLogAgeToDump;
    public final long minWriteGap;
    public final SystemClock systemClock;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public LogBufferEulogizer(Context context, DumpManager dumpManager, SystemClock systemClock, Files files) {
        this(dumpManager, systemClock, files, r4, r5, r6);
        long j;
        long j2;
        Path path = Paths.get(context.getFilesDir().toPath().toString(), "log_buffers.txt");
        j = LogBufferEulogizerKt.MIN_WRITE_GAP;
        j2 = LogBufferEulogizerKt.MAX_AGE_TO_DUMP;
    }

    public LogBufferEulogizer(DumpManager dumpManager, SystemClock systemClock, Files files, Path path, long j, long j2) {
        this.dumpManager = dumpManager;
        this.systemClock = systemClock;
        this.files = files;
        this.logPath = path;
        this.minWriteGap = j;
        this.maxLogAgeToDump = j2;
    }

    public final long getMillisSinceLastWrite(Path path) {
        BasicFileAttributes basicFileAttributes;
        FileTime lastModifiedTime;
        try {
            basicFileAttributes = this.files.readAttributes(path, BasicFileAttributes.class, new LinkOption[0]);
        } catch (IOException e) {
            basicFileAttributes = null;
        }
        return this.systemClock.currentTimeMillis() - ((basicFileAttributes == null || (lastModifiedTime = basicFileAttributes.lastModifiedTime()) == null) ? 0L : lastModifiedTime.toMillis());
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public final void readEulogyIfPresent(final PrintWriter printWriter) {
        try {
            long millisSinceLastWrite = getMillisSinceLastWrite(this.logPath);
            if (millisSinceLastWrite > this.maxLogAgeToDump) {
                long convert = TimeUnit.HOURS.convert(millisSinceLastWrite, TimeUnit.MILLISECONDS);
                Log.i("BufferEulogizer", "Not eulogizing buffers; they are " + convert + " hours old");
                return;
            }
            Stream lines = this.files.lines(this.logPath);
            try {
                printWriter.println();
                printWriter.println();
                printWriter.println("=============== BUFFERS FROM MOST RECENT CRASH ===============");
                lines.forEach(new Consumer() { // from class: com.android.systemui.dump.LogBufferEulogizer$readEulogyIfPresent$1$1
                    /* JADX DEBUG: Method merged with bridge method */
                    @Override // java.util.function.Consumer
                    public final void accept(String str) {
                        printWriter.println(str);
                    }
                });
                Unit unit = Unit.INSTANCE;
                AutoCloseableKt.closeFinally(lines, (Throwable) null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    AutoCloseableKt.closeFinally(lines, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
        } catch (UncheckedIOException e2) {
            Log.e("BufferEulogizer", "UncheckedIOException while dumping the core", e2);
        }
    }

    public final <T extends Exception> T record(T t) {
        SimpleDateFormat simpleDateFormat;
        long uptimeMillis = this.systemClock.uptimeMillis();
        Log.i("BufferEulogizer", "Performing emergency dump of log buffers");
        long millisSinceLastWrite = getMillisSinceLastWrite(this.logPath);
        if (millisSinceLastWrite < this.minWriteGap) {
            Log.w("BufferEulogizer", "Cannot dump logs, last write was only " + millisSinceLastWrite + " ms ago");
            return t;
        }
        long j = 0;
        try {
            BufferedWriter newBufferedWriter = this.files.newBufferedWriter(this.logPath, new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING});
            PrintWriter printWriter = new PrintWriter(newBufferedWriter);
            simpleDateFormat = LogBufferEulogizerKt.DATE_FORMAT;
            printWriter.println(simpleDateFormat.format(Long.valueOf(this.systemClock.currentTimeMillis())));
            printWriter.println();
            printWriter.println("Dump triggered by exception:");
            t.printStackTrace(printWriter);
            this.dumpManager.dumpBuffers(printWriter, 0);
            long uptimeMillis2 = this.systemClock.uptimeMillis() - uptimeMillis;
            printWriter.println();
            StringBuilder sb = new StringBuilder();
            sb.append("Buffer eulogy took ");
            sb.append(uptimeMillis2);
            sb.append("ms");
            printWriter.println(sb.toString());
            Unit unit = Unit.INSTANCE;
            j = uptimeMillis2;
            CloseableKt.closeFinally(newBufferedWriter, (Throwable) null);
            j = uptimeMillis2;
        } catch (Exception e) {
            Log.e("BufferEulogizer", "Exception while attempting to dump buffers, bailing", e);
        }
        Log.i("BufferEulogizer", "Buffer eulogy took " + j + "ms");
        return t;
    }
}