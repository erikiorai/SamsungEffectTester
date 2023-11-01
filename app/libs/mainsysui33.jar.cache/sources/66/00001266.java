package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/BroadcastDispatcher.class */
public class BroadcastDispatcher implements Dumpable {
    public final Executor broadcastExecutor;
    public final Looper broadcastLooper;
    public final Context context;
    public final DumpManager dumpManager;
    public final BroadcastDispatcher$handler$1 handler;
    public final BroadcastDispatcherLogger logger;
    public final Executor mainExecutor;
    public final SparseArray<UserBroadcastDispatcher> receiversByUser = new SparseArray<>(20);
    public final PendingRemovalStore removalPendingStore;
    public final UserTracker userTracker;

    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.broadcast.BroadcastDispatcher$handler$1] */
    public BroadcastDispatcher(Context context, Executor executor, Looper looper, Executor executor2, DumpManager dumpManager, BroadcastDispatcherLogger broadcastDispatcherLogger, UserTracker userTracker, PendingRemovalStore pendingRemovalStore) {
        this.context = context;
        this.mainExecutor = executor;
        this.broadcastLooper = looper;
        this.broadcastExecutor = executor2;
        this.dumpManager = dumpManager;
        this.logger = broadcastDispatcherLogger;
        this.userTracker = userTracker;
        this.removalPendingStore = pendingRemovalStore;
        this.handler = new Handler(looper) { // from class: com.android.systemui.broadcast.BroadcastDispatcher$handler$1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int identifier;
                SparseArray sparseArray;
                SparseArray sparseArray2;
                UserTracker userTracker2;
                SparseArray sparseArray3;
                PendingRemovalStore pendingRemovalStore2;
                SparseArray sparseArray4;
                SparseArray sparseArray5;
                PendingRemovalStore pendingRemovalStore3;
                UserTracker userTracker3;
                int i = message.what;
                if (i == 0) {
                    ReceiverData receiverData = (ReceiverData) message.obj;
                    int i2 = message.arg1;
                    if (receiverData.getUser().getIdentifier() == -2) {
                        userTracker2 = BroadcastDispatcher.this.userTracker;
                        identifier = userTracker2.getUserId();
                    } else {
                        identifier = receiverData.getUser().getIdentifier();
                    }
                    if (identifier < -1) {
                        throw new IllegalStateException("Attempting to register receiver for invalid user {" + identifier + "}");
                    }
                    sparseArray = BroadcastDispatcher.this.receiversByUser;
                    UserBroadcastDispatcher userBroadcastDispatcher = (UserBroadcastDispatcher) sparseArray.get(identifier, BroadcastDispatcher.this.createUBRForUser(identifier));
                    sparseArray2 = BroadcastDispatcher.this.receiversByUser;
                    sparseArray2.put(identifier, userBroadcastDispatcher);
                    userBroadcastDispatcher.registerReceiver(receiverData, i2);
                } else if (i == 1) {
                    sparseArray3 = BroadcastDispatcher.this.receiversByUser;
                    int size = sparseArray3.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        sparseArray4 = BroadcastDispatcher.this.receiversByUser;
                        ((UserBroadcastDispatcher) sparseArray4.valueAt(i3)).unregisterReceiver((BroadcastReceiver) message.obj);
                    }
                    pendingRemovalStore2 = BroadcastDispatcher.this.removalPendingStore;
                    pendingRemovalStore2.clearPendingRemoval((BroadcastReceiver) message.obj, -1);
                } else if (i != 2) {
                    super.handleMessage(message);
                } else {
                    int i4 = message.arg1;
                    int i5 = i4;
                    if (i4 == -2) {
                        userTracker3 = BroadcastDispatcher.this.userTracker;
                        i5 = userTracker3.getUserId();
                    }
                    sparseArray5 = BroadcastDispatcher.this.receiversByUser;
                    UserBroadcastDispatcher userBroadcastDispatcher2 = (UserBroadcastDispatcher) sparseArray5.get(i5);
                    if (userBroadcastDispatcher2 != null) {
                        userBroadcastDispatcher2.unregisterReceiver((BroadcastReceiver) message.obj);
                    }
                    pendingRemovalStore3 = BroadcastDispatcher.this.removalPendingStore;
                    pendingRemovalStore3.clearPendingRemoval((BroadcastReceiver) message.obj, i5);
                }
            }
        };
    }

    public static /* synthetic */ Flow broadcastFlow$default(BroadcastDispatcher broadcastDispatcher, IntentFilter intentFilter, UserHandle userHandle, int i, String str, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                userHandle = null;
            }
            if ((i2 & 4) != 0) {
                i = 2;
            }
            if ((i2 & 8) != 0) {
                str = null;
            }
            return broadcastDispatcher.broadcastFlow(intentFilter, userHandle, i, str);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: broadcastFlow");
    }

    public static /* synthetic */ Flow broadcastFlow$default(BroadcastDispatcher broadcastDispatcher, IntentFilter intentFilter, UserHandle userHandle, int i, String str, Function2 function2, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                userHandle = null;
            }
            if ((i2 & 4) != 0) {
                i = 2;
            }
            if ((i2 & 8) != 0) {
                str = null;
            }
            return broadcastDispatcher.broadcastFlow(intentFilter, userHandle, i, str, function2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: broadcastFlow");
    }

    public static /* synthetic */ void registerReceiver$default(BroadcastDispatcher broadcastDispatcher, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, int i, String str, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerReceiver");
        }
        if ((i2 & 4) != 0) {
            executor = null;
        }
        if ((i2 & 8) != 0) {
            userHandle = null;
        }
        if ((i2 & 16) != 0) {
            i = 2;
        }
        if ((i2 & 32) != 0) {
            str = null;
        }
        broadcastDispatcher.registerReceiver(broadcastReceiver, intentFilter, executor, userHandle, i, str);
    }

    public static /* synthetic */ void registerReceiverWithHandler$default(BroadcastDispatcher broadcastDispatcher, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler, UserHandle userHandle, int i, String str, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerReceiverWithHandler");
        }
        if ((i2 & 8) != 0) {
            userHandle = broadcastDispatcher.context.getUser();
        }
        if ((i2 & 16) != 0) {
            i = 2;
        }
        if ((i2 & 32) != 0) {
            str = null;
        }
        broadcastDispatcher.registerReceiverWithHandler(broadcastReceiver, intentFilter, handler, userHandle, i, str);
    }

    public final Flow<Unit> broadcastFlow(IntentFilter intentFilter, UserHandle userHandle, int i, String str) {
        return broadcastFlow(intentFilter, userHandle, i, str, new Function2<Intent, BroadcastReceiver, Unit>() { // from class: com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                invoke((Intent) obj, (BroadcastReceiver) obj2);
                return Unit.INSTANCE;
            }

            public final void invoke(Intent intent, BroadcastReceiver broadcastReceiver) {
            }
        });
    }

    public final <T> Flow<T> broadcastFlow(IntentFilter intentFilter, UserHandle userHandle, int i, String str, Function2<? super Intent, ? super BroadcastReceiver, ? extends T> function2) {
        return ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new BroadcastDispatcher$broadcastFlow$1(this, intentFilter, userHandle, i, str, function2, null));
    }

    public final void checkFilter(IntentFilter intentFilter) {
        StringBuilder sb = new StringBuilder();
        if (intentFilter.countActions() == 0) {
            sb.append("Filter must contain at least one action. ");
        }
        if (intentFilter.countDataAuthorities() != 0) {
            sb.append("Filter cannot contain DataAuthorities. ");
        }
        if (intentFilter.countDataPaths() != 0) {
            sb.append("Filter cannot contain DataPaths. ");
        }
        if (intentFilter.countDataSchemes() != 0) {
            sb.append("Filter cannot contain DataSchemes. ");
        }
        if (intentFilter.countDataTypes() != 0) {
            sb.append("Filter cannot contain DataTypes. ");
        }
        if (intentFilter.getPriority() != 0) {
            sb.append("Filter cannot modify priority. ");
        }
        if (!TextUtils.isEmpty(sb)) {
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @VisibleForTesting
    public UserBroadcastDispatcher createUBRForUser(int i) {
        return new UserBroadcastDispatcher(this.context, i, this.broadcastLooper, this.broadcastExecutor, this.logger, this.removalPendingStore);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Broadcast dispatcher:");
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        int size = this.receiversByUser.size();
        for (int i = 0; i < size; i++) {
            indentingPrintWriter.println("User " + this.receiversByUser.keyAt(i));
            this.receiversByUser.valueAt(i).dump(indentingPrintWriter, strArr);
        }
        indentingPrintWriter.println("Pending removal:");
        this.removalPendingStore.dump(indentingPrintWriter, strArr);
        indentingPrintWriter.decreaseIndent();
    }

    public final void initialize() {
        DumpManager.registerDumpable$default(this.dumpManager, getClass().getName(), this, null, 4, null);
    }

    public final void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        registerReceiver$default(this, broadcastReceiver, intentFilter, null, null, 0, null, 60, null);
    }

    public final void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor) {
        registerReceiver$default(this, broadcastReceiver, intentFilter, executor, null, 0, null, 56, null);
    }

    public final void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle) {
        registerReceiver$default(this, broadcastReceiver, intentFilter, executor, userHandle, 0, null, 48, null);
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, int i, String str) {
        checkFilter(intentFilter);
        Executor executor2 = executor;
        if (executor == null) {
            executor2 = this.mainExecutor;
        }
        UserHandle userHandle2 = userHandle;
        if (userHandle == null) {
            userHandle2 = this.context.getUser();
        }
        obtainMessage(0, i, 0, new ReceiverData(broadcastReceiver, intentFilter, executor2, userHandle2, str)).sendToTarget();
    }

    public final void registerReceiverWithHandler(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler) {
        registerReceiverWithHandler$default(this, broadcastReceiver, intentFilter, handler, null, 0, null, 56, null);
    }

    public final void registerReceiverWithHandler(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler, UserHandle userHandle) {
        registerReceiverWithHandler$default(this, broadcastReceiver, intentFilter, handler, userHandle, 0, null, 48, null);
    }

    public void registerReceiverWithHandler(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler, UserHandle userHandle, int i, String str) {
        registerReceiver(broadcastReceiver, intentFilter, new HandlerExecutor(handler), userHandle, i, str);
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        this.removalPendingStore.tagForRemoval(broadcastReceiver, -1);
        obtainMessage(1, broadcastReceiver).sendToTarget();
    }
}