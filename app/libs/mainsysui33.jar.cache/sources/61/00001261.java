package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/ActionReceiver.class */
public final class ActionReceiver extends BroadcastReceiver implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final AtomicInteger index = new AtomicInteger(0);
    public final String action;
    public final BroadcastDispatcherLogger logger;
    public final Function2<BroadcastReceiver, IntentFilter, Unit> registerAction;
    public boolean registered;
    public final Function2<BroadcastReceiver, Integer, Boolean> testPendingRemovalAction;
    public final Function1<BroadcastReceiver, Unit> unregisterAction;
    public final int userId;
    public final Executor workerExecutor;
    public final ArraySet<ReceiverData> receiverDatas = new ArraySet<>();
    public final ArraySet<String> activeCategories = new ArraySet<>();

    /* loaded from: mainsysui33.jar:com/android/systemui/broadcast/ActionReceiver$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r11v0, resolved type: kotlin.jvm.functions.Function2<? super android.content.BroadcastReceiver, ? super java.lang.Integer, java.lang.Boolean> */
    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: kotlin.jvm.functions.Function2<? super android.content.BroadcastReceiver, ? super android.content.IntentFilter, kotlin.Unit> */
    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: kotlin.jvm.functions.Function1<? super android.content.BroadcastReceiver, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public ActionReceiver(String str, int i, Function2<? super BroadcastReceiver, ? super IntentFilter, Unit> function2, Function1<? super BroadcastReceiver, Unit> function1, Executor executor, BroadcastDispatcherLogger broadcastDispatcherLogger, Function2<? super BroadcastReceiver, ? super Integer, Boolean> function22) {
        this.action = str;
        this.userId = i;
        this.registerAction = function2;
        this.unregisterAction = function1;
        this.workerExecutor = executor;
        this.logger = broadcastDispatcherLogger;
        this.testPendingRemovalAction = function22;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.broadcast.ActionReceiver$onReceive$1$1$1.run():void] */
    public static final /* synthetic */ String access$getAction$p(ActionReceiver actionReceiver) {
        return actionReceiver.action;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.broadcast.ActionReceiver$onReceive$1$1$1.run():void] */
    public static final /* synthetic */ BroadcastDispatcherLogger access$getLogger$p(ActionReceiver actionReceiver) {
        return actionReceiver.logger;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.broadcast.ActionReceiver$onReceive$1.run():void] */
    public static final /* synthetic */ ArraySet access$getReceiverDatas$p(ActionReceiver actionReceiver) {
        return actionReceiver.receiverDatas;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.broadcast.ActionReceiver$onReceive$1.run():void] */
    public static final /* synthetic */ Function2 access$getTestPendingRemovalAction$p(ActionReceiver actionReceiver) {
        return actionReceiver.testPendingRemovalAction;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.broadcast.ActionReceiver$onReceive$1.run():void] */
    public static final /* synthetic */ int access$getUserId$p(ActionReceiver actionReceiver) {
        return actionReceiver.userId;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x002a, code lost:
        if (r0 == null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addReceiverData(ReceiverData receiverData) throws IllegalArgumentException {
        Sequence emptySequence;
        if (!receiverData.getFilter().hasAction(this.action)) {
            String str = this.action;
            BroadcastReceiver receiver = receiverData.getReceiver();
            throw new IllegalArgumentException("Trying to attach to " + str + " without correct action,receiver: " + receiver);
        }
        ArraySet<String> arraySet = this.activeCategories;
        Iterator<String> categoriesIterator = receiverData.getFilter().categoriesIterator();
        if (categoriesIterator != null) {
            Sequence asSequence = SequencesKt__SequencesKt.asSequence(categoriesIterator);
            emptySequence = asSequence;
        }
        emptySequence = SequencesKt__SequencesKt.emptySequence();
        boolean addAll = CollectionsKt__MutableCollectionsKt.addAll(arraySet, emptySequence);
        if (this.receiverDatas.add(receiverData) && this.receiverDatas.size() == 1) {
            this.registerAction.invoke(this, createFilter());
            this.registered = true;
        } else if (addAll) {
            this.unregisterAction.invoke(this);
            this.registerAction.invoke(this, createFilter());
        }
    }

    public final IntentFilter createFilter() {
        IntentFilter intentFilter = new IntentFilter(this.action);
        for (String str : this.activeCategories) {
            intentFilter.addCategory(str);
        }
        return intentFilter;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        if (printWriter instanceof IndentingPrintWriter) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        boolean z = this.registered;
        printWriter.println("Registered: " + z);
        printWriter.println("Receivers:");
        boolean z2 = printWriter instanceof IndentingPrintWriter;
        if (z2) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        for (ReceiverData receiverData : this.receiverDatas) {
            printWriter.println(receiverData.getReceiver());
        }
        if (z2) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
        String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(this.activeCategories, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
        printWriter.println("Categories: " + joinToString$default);
        if (z2) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    public final boolean hasReceiver(BroadcastReceiver broadcastReceiver) {
        boolean z;
        ArraySet<ReceiverData> arraySet = this.receiverDatas;
        if (!(arraySet instanceof Collection) || !arraySet.isEmpty()) {
            Iterator<T> it = arraySet.iterator();
            while (true) {
                z = false;
                if (!it.hasNext()) {
                    break;
                } else if (Intrinsics.areEqual(((ReceiverData) it.next()).getReceiver(), broadcastReceiver)) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
        }
        return z;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, final Intent intent) throws IllegalStateException {
        if (Intrinsics.areEqual(intent.getAction(), this.action)) {
            final int andIncrement = index.getAndIncrement();
            this.logger.logBroadcastReceived(andIncrement, this.userId, intent);
            this.workerExecutor.execute(new Runnable() { // from class: com.android.systemui.broadcast.ActionReceiver$onReceive$1
                @Override // java.lang.Runnable
                public final void run() {
                    ArraySet<ReceiverData> access$getReceiverDatas$p = ActionReceiver.access$getReceiverDatas$p(ActionReceiver.this);
                    final Intent intent2 = intent;
                    final ActionReceiver actionReceiver = ActionReceiver.this;
                    final Context context2 = context;
                    final int i = andIncrement;
                    for (final ReceiverData receiverData : access$getReceiverDatas$p) {
                        if (receiverData.getFilter().matchCategories(intent2.getCategories()) == null && !((Boolean) ActionReceiver.access$getTestPendingRemovalAction$p(actionReceiver).invoke(receiverData.getReceiver(), Integer.valueOf(ActionReceiver.access$getUserId$p(actionReceiver)))).booleanValue()) {
                            receiverData.getExecutor().execute(new Runnable() { // from class: com.android.systemui.broadcast.ActionReceiver$onReceive$1$1$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ReceiverData.this.getReceiver().setPendingResult(actionReceiver.getPendingResult());
                                    ReceiverData.this.getReceiver().onReceive(context2, intent2);
                                    ActionReceiver.access$getLogger$p(actionReceiver).logBroadcastDispatched(i, ActionReceiver.access$getAction$p(actionReceiver), ReceiverData.this.getReceiver());
                                }
                            });
                        }
                    }
                }
            });
            return;
        }
        String action = intent.getAction();
        String str = this.action;
        throw new IllegalStateException("Received intent for " + action + " in receiver for " + str + "}");
    }

    public final void removeReceiver(final BroadcastReceiver broadcastReceiver) {
        if (CollectionsKt__MutableCollectionsKt.removeAll(this.receiverDatas, new Function1<ReceiverData, Boolean>() { // from class: com.android.systemui.broadcast.ActionReceiver$removeReceiver$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final Boolean invoke(ReceiverData receiverData) {
                return Boolean.valueOf(Intrinsics.areEqual(receiverData.getReceiver(), broadcastReceiver));
            }
        }) && this.receiverDatas.isEmpty() && this.registered) {
            this.unregisterAction.invoke(this);
            this.registered = false;
            this.activeCategories.clear();
        }
    }
}