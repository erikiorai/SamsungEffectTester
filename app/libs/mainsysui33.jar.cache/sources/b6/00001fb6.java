package com.android.systemui.plugins.log;

import android.os.Trace;
import android.util.Log;
import com.android.systemui.plugins.util.RingBuffer;
import com.google.errorprone.annotations.CompileTimeConstant;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogBuffer.class */
public final class LogBuffer {
    private final RingBuffer<LogMessageImpl> buffer;
    private final BlockingQueue<LogMessage> echoMessageQueue;
    private boolean frozen;
    private final LogcatEchoTracker logcatEchoTracker;
    private final int maxSize;
    private final String name;
    private final boolean systrace;

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogBuffer$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LogLevel.values().length];
            try {
                iArr[LogLevel.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[LogLevel.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[LogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[LogLevel.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[LogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[LogLevel.WTF.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker) {
        this(str, i, logcatEchoTracker, false, 8, null);
    }

    public LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker, boolean z) {
        this.name = str;
        this.maxSize = i;
        this.logcatEchoTracker = logcatEchoTracker;
        this.systrace = z;
        this.buffer = new RingBuffer<>(i, new Function0<LogMessageImpl>() { // from class: com.android.systemui.plugins.log.LogBuffer$buffer$1
            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final LogMessageImpl m3593invoke() {
                return LogMessageImpl.Factory.create();
            }
        });
        ArrayBlockingQueue arrayBlockingQueue = logcatEchoTracker.getLogInBackgroundThread() ? new ArrayBlockingQueue(10) : null;
        this.echoMessageQueue = arrayBlockingQueue;
        if (!logcatEchoTracker.getLogInBackgroundThread() || arrayBlockingQueue == null) {
            return;
        }
        ThreadsKt.thread$default(true, false, (ClassLoader) null, "LogBuffer-" + str, 5, new Function0<Unit>() { // from class: com.android.systemui.plugins.log.LogBuffer.1
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m3590invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m3590invoke() {
                while (true) {
                    try {
                        LogBuffer logBuffer = LogBuffer.this;
                        logBuffer.echoToDesiredEndpoints((LogMessage) logBuffer.echoMessageQueue.take());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }, 6, (Object) null);
    }

    public /* synthetic */ LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, logcatEchoTracker, (i2 & 8) != 0 ? true : z);
    }

    private final void echo(LogMessage logMessage, boolean z, boolean z2) {
        if (z || z2) {
            String str = (String) logMessage.getMessagePrinter().invoke(logMessage);
            if (z2) {
                echoToSystrace(logMessage, str);
            }
            if (z) {
                echoToLogcat(logMessage, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void echoToDesiredEndpoints(LogMessage logMessage) {
        echo(logMessage, this.logcatEchoTracker.isBufferLoggable(this.name, logMessage.getLevel()) || this.logcatEchoTracker.isTagLoggable(logMessage.getTag(), logMessage.getLevel()), this.systrace);
    }

    private final void echoToLogcat(LogMessage logMessage, String str) {
        switch (WhenMappings.$EnumSwitchMapping$0[logMessage.getLevel().ordinal()]) {
            case 1:
                Log.v(logMessage.getTag(), str, logMessage.getException());
                return;
            case 2:
                Log.d(logMessage.getTag(), str, logMessage.getException());
                return;
            case 3:
                Log.i(logMessage.getTag(), str, logMessage.getException());
                return;
            case 4:
                Log.w(logMessage.getTag(), str, logMessage.getException());
                return;
            case 5:
                Log.e(logMessage.getTag(), str, logMessage.getException());
                return;
            case 6:
                Log.wtf(logMessage.getTag(), str, logMessage.getException());
                return;
            default:
                return;
        }
    }

    private final void echoToSystrace(LogMessage logMessage, String str) {
        String str2 = this.name;
        String shortString = logMessage.getLevel().getShortString();
        String tag = logMessage.getTag();
        Trace.instantForTrack(4096L, "UI Events", str2 + " - " + shortString + " " + tag + ": " + str);
    }

    private final boolean getMutable() {
        return !this.frozen && this.maxSize > 0;
    }

    public static /* synthetic */ void log$default(LogBuffer logBuffer, String str, LogLevel logLevel, Function1 function1, Function1 function12, Throwable th, int i, Object obj) {
        if ((i & 16) != 0) {
            th = null;
        }
        LogMessage obtain = logBuffer.obtain(str, logLevel, function12, th);
        function1.invoke(obtain);
        logBuffer.commit(obtain);
    }

    public static /* synthetic */ LogMessage obtain$default(LogBuffer logBuffer, String str, LogLevel logLevel, Function1 function1, Throwable th, int i, Object obj) {
        if ((i & 8) != 0) {
            th = null;
        }
        return logBuffer.obtain(str, logLevel, function1, th);
    }

    public final void commit(LogMessage logMessage) {
        synchronized (this) {
            if (getMutable()) {
                BlockingQueue<LogMessage> blockingQueue = this.echoMessageQueue;
                if (blockingQueue == null || blockingQueue.remainingCapacity() <= 0) {
                    echoToDesiredEndpoints(logMessage);
                } else {
                    try {
                        this.echoMessageQueue.put(logMessage);
                    } catch (InterruptedException e) {
                        echoToDesiredEndpoints(logMessage);
                    }
                }
            }
        }
    }

    public final void dump(PrintWriter printWriter, int i) {
        synchronized (this) {
            int size = this.buffer.getSize();
            for (int max = i <= 0 ? 0 : Math.max(0, this.buffer.getSize() - i); max < size; max++) {
                this.buffer.get(max).dump(printWriter);
            }
        }
    }

    public final void freeze() {
        synchronized (this) {
            if (!this.frozen) {
                LogMessage obtain = obtain("LogBuffer", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.plugins.log.LogBuffer$freeze$2
                    /* JADX DEBUG: Method merged with bridge method */
                    public final String invoke(LogMessage logMessage) {
                        String str1 = logMessage.getStr1();
                        return str1 + " frozen";
                    }
                }, null);
                obtain.setStr1(this.name);
                commit(obtain);
                this.frozen = true;
            }
        }
    }

    public final boolean getFrozen() {
        return this.frozen;
    }

    public final void log(String str, LogLevel logLevel, @CompileTimeConstant String str2) {
        LogMessage obtain = obtain(str, logLevel, new Function1<LogMessage, String>() { // from class: com.android.systemui.plugins.log.LogBuffer$log$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                Intrinsics.checkNotNull(str1);
                return str1;
            }
        }, null);
        obtain.setStr1(str2);
        commit(obtain);
    }

    public final void log(String str, LogLevel logLevel, Function1<? super LogMessage, Unit> function1, Function1<? super LogMessage, String> function12) {
        LogMessage obtain = obtain(str, logLevel, function12, null);
        function1.invoke(obtain);
        commit(obtain);
    }

    public final void log(String str, LogLevel logLevel, Function1<? super LogMessage, Unit> function1, Function1<? super LogMessage, String> function12, Throwable th) {
        LogMessage obtain = obtain(str, logLevel, function12, th);
        function1.invoke(obtain);
        commit(obtain);
    }

    public final LogMessage obtain(String str, LogLevel logLevel, Function1<? super LogMessage, String> function1, Throwable th) {
        LogMessageImpl logMessageImpl;
        synchronized (this) {
            if (!getMutable()) {
                logMessageImpl = LogBufferKt.FROZEN_MESSAGE;
                return logMessageImpl;
            }
            LogMessageImpl advance = this.buffer.advance();
            advance.reset(str, logLevel, System.currentTimeMillis(), function1, th);
            return advance;
        }
    }

    public final void unfreeze() {
        synchronized (this) {
            if (this.frozen) {
                LogMessage obtain = obtain("LogBuffer", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.plugins.log.LogBuffer$unfreeze$2
                    /* JADX DEBUG: Method merged with bridge method */
                    public final String invoke(LogMessage logMessage) {
                        String str1 = logMessage.getStr1();
                        return str1 + " unfrozen";
                    }
                }, null);
                obtain.setStr1(this.name);
                commit(obtain);
                this.frozen = false;
            }
        }
    }
}