package com.android.systemui.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/UserBroadcastDispatcher.class */
public class UserBroadcastDispatcher implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final AtomicInteger index = new AtomicInteger(0);
    public final Context context;
    public final BroadcastDispatcherLogger logger;
    public final PendingRemovalStore removalPendingStore;
    public final int userId;
    public final Executor workerExecutor;
    public final Handler workerHandler;
    public final Looper workerLooper;
    public final String wrongThreadErrorMsg = "This method should only be called from the worker thread (which is expected to be the BroadcastRunning thread)";
    public final ArrayMap<ReceiverProperties, ActionReceiver> actionsToActionsReceivers = new ArrayMap<>();
    public final ArrayMap<BroadcastReceiver, Set<String>> receiverToActions = new ArrayMap<>();

    /* loaded from: mainsysui33.jar:com/android/systemui/broadcast/UserBroadcastDispatcher$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/broadcast/UserBroadcastDispatcher$ReceiverProperties.class */
    public static final class ReceiverProperties {
        public final String action;
        public final int flags;
        public final String permission;

        public ReceiverProperties(String str, int i, String str2) {
            this.action = str;
            this.flags = i;
            this.permission = str2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ReceiverProperties) {
                ReceiverProperties receiverProperties = (ReceiverProperties) obj;
                return Intrinsics.areEqual(this.action, receiverProperties.action) && this.flags == receiverProperties.flags && Intrinsics.areEqual(this.permission, receiverProperties.permission);
            }
            return false;
        }

        public final String getAction() {
            return this.action;
        }

        public final int getFlags() {
            return this.flags;
        }

        public final String getPermission() {
            return this.permission;
        }

        public int hashCode() {
            int hashCode = this.action.hashCode();
            int hashCode2 = Integer.hashCode(this.flags);
            String str = this.permission;
            return (((hashCode * 31) + hashCode2) * 31) + (str == null ? 0 : str.hashCode());
        }

        public String toString() {
            String str = this.action;
            int i = this.flags;
            String str2 = this.permission;
            return "ReceiverProperties(action=" + str + ", flags=" + i + ", permission=" + str2 + ")";
        }
    }

    public UserBroadcastDispatcher(Context context, int i, Looper looper, Executor executor, BroadcastDispatcherLogger broadcastDispatcherLogger, PendingRemovalStore pendingRemovalStore) {
        this.context = context;
        this.userId = i;
        this.workerLooper = looper;
        this.workerExecutor = executor;
        this.logger = broadcastDispatcherLogger;
        this.removalPendingStore = pendingRemovalStore;
        this.workerHandler = new Handler(looper);
    }

    public static /* synthetic */ void getActionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    @SuppressLint({"RegisterReceiverViaContextDetector"})
    public ActionReceiver createActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core(final String str, final String str2, final int i) {
        return new ActionReceiver(str, this.userId, new Function2<BroadcastReceiver, IntentFilter, Unit>() { // from class: com.android.systemui.broadcast.UserBroadcastDispatcher$createActionReceiver$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                invoke((BroadcastReceiver) obj, (IntentFilter) obj2);
                return Unit.INSTANCE;
            }

            public final void invoke(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
                Context context;
                int i2;
                Handler handler;
                BroadcastDispatcherLogger broadcastDispatcherLogger;
                int i3;
                int i4;
                if (Trace.isEnabled()) {
                    String str3 = str;
                    i4 = this.userId;
                    Trace.traceBegin(4096L, "registerReceiver act=" + str3 + " user=" + i4);
                }
                context = this.context;
                i2 = this.userId;
                UserHandle of = UserHandle.of(i2);
                String str4 = str2;
                handler = this.workerHandler;
                context.registerReceiverAsUser(broadcastReceiver, of, intentFilter, str4, handler, i);
                Trace.endSection();
                broadcastDispatcherLogger = this.logger;
                i3 = this.userId;
                broadcastDispatcherLogger.logContextReceiverRegistered(i3, i, intentFilter);
            }
        }, new Function1<BroadcastReceiver, Unit>() { // from class: com.android.systemui.broadcast.UserBroadcastDispatcher$createActionReceiver$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((BroadcastReceiver) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(BroadcastReceiver broadcastReceiver) {
                int i2;
                Context context;
                BroadcastDispatcherLogger broadcastDispatcherLogger;
                int i3;
                int i4;
                try {
                    if (Trace.isEnabled()) {
                        String str3 = str;
                        i4 = this.userId;
                        Trace.traceBegin(4096L, "unregisterReceiver act=" + str3 + " user=" + i4);
                    }
                    context = this.context;
                    context.unregisterReceiver(broadcastReceiver);
                    Trace.endSection();
                    broadcastDispatcherLogger = this.logger;
                    i3 = this.userId;
                    broadcastDispatcherLogger.logContextReceiverUnregistered(i3, str);
                } catch (IllegalArgumentException e) {
                    i2 = this.userId;
                    String str4 = str;
                    Log.e("UserBroadcastDispatcher", "Trying to unregister unregistered receiver for user " + i2 + ", action " + str4, new IllegalStateException(e));
                }
            }
        }, this.workerExecutor, this.logger, new UserBroadcastDispatcher$createActionReceiver$3(this.removalPendingStore));
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        boolean z = printWriter instanceof IndentingPrintWriter;
        if (z) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        for (Map.Entry<ReceiverProperties, ActionReceiver> entry : this.actionsToActionsReceivers.entrySet()) {
            ReceiverProperties key = entry.getKey();
            ActionReceiver value = entry.getValue();
            String action = key.getAction();
            String flagToString = BroadcastDispatcherLogger.Companion.flagToString(key.getFlags());
            String str = "):";
            if (key.getPermission() != null) {
                str = ":" + key.getPermission() + "):";
            }
            printWriter.println("(" + action + ": " + flagToString + str);
            value.dump(printWriter, strArr);
        }
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0061, code lost:
        if (r0 == null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleRegisterReceiver(ReceiverData receiverData, int i) {
        Sequence emptySequence;
        Preconditions.checkState(this.workerLooper.isCurrentThread(), this.wrongThreadErrorMsg);
        ArrayMap<BroadcastReceiver, Set<String>> arrayMap = this.receiverToActions;
        BroadcastReceiver receiver = receiverData.getReceiver();
        Set<String> set = arrayMap.get(receiver);
        ArraySet arraySet = set;
        if (set == null) {
            arraySet = new ArraySet();
            arrayMap.put(receiver, arraySet);
        }
        Set<String> set2 = arraySet;
        Iterator<String> actionsIterator = receiverData.getFilter().actionsIterator();
        if (actionsIterator != null) {
            Sequence asSequence = SequencesKt__SequencesKt.asSequence(actionsIterator);
            emptySequence = asSequence;
        }
        emptySequence = SequencesKt__SequencesKt.emptySequence();
        CollectionsKt__MutableCollectionsKt.addAll(set2, emptySequence);
        Iterator<String> actionsIterator2 = receiverData.getFilter().actionsIterator();
        while (actionsIterator2.hasNext()) {
            String next = actionsIterator2.next();
            ArrayMap<ReceiverProperties, ActionReceiver> arrayMap2 = this.actionsToActionsReceivers;
            ReceiverProperties receiverProperties = new ReceiverProperties(next, i, receiverData.getPermission());
            ActionReceiver actionReceiver = arrayMap2.get(receiverProperties);
            ActionReceiver actionReceiver2 = actionReceiver;
            if (actionReceiver == null) {
                actionReceiver2 = createActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core(next, receiverData.getPermission(), i);
                arrayMap2.put(receiverProperties, actionReceiver2);
            }
            actionReceiver2.addReceiverData(receiverData);
        }
        this.logger.logReceiverRegistered(this.userId, receiverData.getReceiver(), i);
    }

    public final void handleUnregisterReceiver(BroadcastReceiver broadcastReceiver) {
        Preconditions.checkState(this.workerLooper.isCurrentThread(), this.wrongThreadErrorMsg);
        for (String str : this.receiverToActions.getOrDefault(broadcastReceiver, new LinkedHashSet())) {
            for (Map.Entry<ReceiverProperties, ActionReceiver> entry : this.actionsToActionsReceivers.entrySet()) {
                ReceiverProperties key = entry.getKey();
                ActionReceiver value = entry.getValue();
                if (Intrinsics.areEqual(key.getAction(), str)) {
                    value.removeReceiver(broadcastReceiver);
                }
            }
        }
        this.receiverToActions.remove(broadcastReceiver);
        this.logger.logReceiverUnregistered(this.userId, broadcastReceiver);
    }

    public final boolean isReceiverReferenceHeld$frameworks__base__packages__SystemUI__android_common__SystemUI_core(BroadcastReceiver broadcastReceiver) {
        boolean z;
        Collection<ActionReceiver> values = this.actionsToActionsReceivers.values();
        if (!values.isEmpty()) {
            for (ActionReceiver actionReceiver : values) {
                if (actionReceiver.hasReceiver(broadcastReceiver)) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        return z ? true : this.receiverToActions.containsKey(broadcastReceiver);
    }

    public final void registerReceiver(ReceiverData receiverData, int i) {
        handleRegisterReceiver(receiverData, i);
    }

    public final void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        handleUnregisterReceiver(broadcastReceiver);
    }
}