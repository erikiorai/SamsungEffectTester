package com.android.systemui.broadcast.logging;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.text.StringsKt__IndentKt;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/logging/BroadcastDispatcherLogger.class */
public final class BroadcastDispatcherLogger {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;

    /* loaded from: mainsysui33.jar:com/android/systemui/broadcast/logging/BroadcastDispatcherLogger$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String flagToString(int i) {
            StringBuilder sb = new StringBuilder("");
            if ((i & 1) != 0) {
                sb.append("instant_apps,");
            }
            if ((i & 4) != 0) {
                sb.append("not_exported,");
            }
            if ((i & 2) != 0) {
                sb.append("exported");
            }
            if (sb.length() == 0) {
                sb.append(i);
            }
            return sb.toString();
        }
    }

    public BroadcastDispatcherLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logBroadcastDispatched(int i, String str, BroadcastReceiver broadcastReceiver) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        BroadcastDispatcherLogger$logBroadcastDispatched$2 broadcastDispatcherLogger$logBroadcastDispatched$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logBroadcastDispatched$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                return "Broadcast " + int1 + " (" + str1 + ") dispatched to " + str2;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logBroadcastDispatched$2, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        obtain.setStr2(broadcastReceiver2);
        logBuffer.commit(obtain);
    }

    public final void logBroadcastReceived(int i, int i2, Intent intent) {
        String intent2 = intent.toString();
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logBroadcastReceived$2 broadcastDispatcherLogger$logBroadcastReceived$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logBroadcastReceived$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                return "[" + int1 + "] Broadcast received for user " + int2 + ": " + str1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logBroadcastReceived$2, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        obtain.setStr1(intent2);
        logBuffer.commit(obtain);
    }

    public final void logClearedAfterRemoval(int i, BroadcastReceiver broadcastReceiver) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        BroadcastDispatcherLogger$logClearedAfterRemoval$2 broadcastDispatcherLogger$logClearedAfterRemoval$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logClearedAfterRemoval$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "Receiver " + str1 + " has been completely removed for user " + int1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logClearedAfterRemoval$2, null);
        obtain.setInt1(i);
        obtain.setStr1(broadcastReceiver2);
        logBuffer.commit(obtain);
    }

    public final void logContextReceiverRegistered(int i, int i2, IntentFilter intentFilter) {
        String joinToString$default = SequencesKt___SequencesKt.joinToString$default(SequencesKt__SequencesKt.asSequence(intentFilter.actionsIterator()), ",", "Actions(", ")", 0, (CharSequence) null, (Function1) null, 56, (Object) null);
        String joinToString$default2 = intentFilter.countCategories() != 0 ? SequencesKt___SequencesKt.joinToString$default(SequencesKt__SequencesKt.asSequence(intentFilter.categoriesIterator()), ",", "Categories(", ")", 0, (CharSequence) null, (Function1) null, 56, (Object) null) : "";
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logContextReceiverRegistered$2 broadcastDispatcherLogger$logContextReceiverRegistered$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logContextReceiverRegistered$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str2 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                return StringsKt__IndentKt.trimIndent("\n                Receiver registered with Context for user " + int1 + ". Flags=" + str2 + "\n                " + str1 + "\n            ");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logContextReceiverRegistered$2, null);
        obtain.setInt1(i);
        String str = joinToString$default;
        if (!Intrinsics.areEqual(joinToString$default2, "")) {
            str = joinToString$default + "\n" + joinToString$default2;
        }
        obtain.setStr1(str);
        obtain.setStr2(Companion.flagToString(i2));
        logBuffer.commit(obtain);
    }

    public final void logContextReceiverUnregistered(int i, String str) {
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logContextReceiverUnregistered$2 broadcastDispatcherLogger$logContextReceiverUnregistered$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logContextReceiverUnregistered$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                return "Receiver unregistered with Context for user " + int1 + ", action " + str1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logContextReceiverUnregistered$2, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logReceiverRegistered(int i, BroadcastReceiver broadcastReceiver, int i2) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        String flagToString = Companion.flagToString(i2);
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logReceiverRegistered$2 broadcastDispatcherLogger$logReceiverRegistered$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logReceiverRegistered$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                return "Receiver " + str1 + " (" + str2 + ") registered for user " + int1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logReceiverRegistered$2, null);
        obtain.setInt1(i);
        obtain.setStr1(broadcastReceiver2);
        obtain.setStr2(flagToString);
        logBuffer.commit(obtain);
    }

    public final void logReceiverUnregistered(int i, BroadcastReceiver broadcastReceiver) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logReceiverUnregistered$2 broadcastDispatcherLogger$logReceiverUnregistered$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logReceiverUnregistered$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "Receiver " + str1 + " unregistered for user " + int1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logReceiverUnregistered$2, null);
        obtain.setInt1(i);
        obtain.setStr1(broadcastReceiver2);
        logBuffer.commit(obtain);
    }

    public final void logTagForRemoval(int i, BroadcastReceiver broadcastReceiver) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        BroadcastDispatcherLogger$logTagForRemoval$2 broadcastDispatcherLogger$logTagForRemoval$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logTagForRemoval$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "Receiver " + str1 + " tagged for removal from user " + int1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logTagForRemoval$2, null);
        obtain.setInt1(i);
        obtain.setStr1(broadcastReceiver2);
        logBuffer.commit(obtain);
    }
}