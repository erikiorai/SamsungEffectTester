package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.common.coroutine.ChannelExt;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: Add missing generic type declarations: [T] */
@DebugMetadata(c = "com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1", f = "BroadcastDispatcher.kt", l = {192}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/BroadcastDispatcher$broadcastFlow$1.class */
public final class BroadcastDispatcher$broadcastFlow$1<T> extends SuspendLambda implements Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ IntentFilter $filter;
    public final /* synthetic */ int $flags;
    public final /* synthetic */ Function2<Intent, BroadcastReceiver, T> $map;
    public final /* synthetic */ String $permission;
    public final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ BroadcastDispatcher this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r10v0, resolved type: kotlin.jvm.functions.Function2<? super android.content.Intent, ? super android.content.BroadcastReceiver, ? extends T> */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BroadcastDispatcher$broadcastFlow$1(BroadcastDispatcher broadcastDispatcher, IntentFilter intentFilter, UserHandle userHandle, int i, String str, Function2<? super Intent, ? super BroadcastReceiver, ? extends T> function2, Continuation<? super BroadcastDispatcher$broadcastFlow$1> continuation) {
        super(2, continuation);
        this.this$0 = broadcastDispatcher;
        this.$filter = intentFilter;
        this.$user = userHandle;
        this.$flags = i;
        this.$permission = str;
        this.$map = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        BroadcastDispatcher$broadcastFlow$1 broadcastDispatcher$broadcastFlow$1 = new BroadcastDispatcher$broadcastFlow$1(this.this$0, this.$filter, this.$user, this.$flags, this.$permission, this.$map, continuation);
        broadcastDispatcher$broadcastFlow$1.L$0 = obj;
        return broadcastDispatcher$broadcastFlow$1;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke((ProducerScope) ((ProducerScope) obj), (Continuation) obj2);
    }

    public final Object invoke(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v10, resolved type: com.android.systemui.broadcast.BroadcastDispatcher */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1$receiver$1, android.content.BroadcastReceiver] */
    public final Object invokeSuspend(Object obj) {
        Executor executor;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Function2<Intent, BroadcastReceiver, T> function2 = this.$map;
            final ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, function2.invoke(intent, this), "BroadcastDispatcher", null, 4, null);
                }
            };
            BroadcastDispatcher broadcastDispatcher = this.this$0;
            IntentFilter intentFilter = this.$filter;
            executor = broadcastDispatcher.broadcastExecutor;
            broadcastDispatcher.registerReceiver(r0, intentFilter, executor, this.$user, this.$flags, this.$permission);
            final BroadcastDispatcher broadcastDispatcher2 = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m1633invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1633invoke() {
                    BroadcastDispatcher.this.unregisterReceiver(r0);
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}