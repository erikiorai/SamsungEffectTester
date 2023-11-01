package com.android.systemui.power.data.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ChannelExt;
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
import kotlinx.coroutines.channels.SendChannel;

@DebugMetadata(c = "com.android.systemui.power.data.repository.PowerRepositoryImpl$isInteractive$1", f = "PowerRepository.kt", l = {68}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/power/data/repository/PowerRepositoryImpl$isInteractive$1.class */
public final class PowerRepositoryImpl$isInteractive$1 extends SuspendLambda implements Function2<ProducerScope<? super Boolean>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ BroadcastDispatcher $dispatcher;
    public final /* synthetic */ PowerManager $manager;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PowerRepositoryImpl$isInteractive$1(BroadcastDispatcher broadcastDispatcher, PowerManager powerManager, Continuation<? super PowerRepositoryImpl$isInteractive$1> continuation) {
        super(2, continuation);
        this.$dispatcher = broadcastDispatcher;
        this.$manager = powerManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.data.repository.PowerRepositoryImpl$isInteractive$1$receiver$1.onReceive(android.content.Context, android.content.Intent):void] */
    public static final /* synthetic */ void access$invokeSuspend$send(ProducerScope producerScope, PowerManager powerManager) {
        invokeSuspend$send(producerScope, powerManager);
    }

    public static final void invokeSuspend$send(ProducerScope<? super Boolean> producerScope, PowerManager powerManager) {
        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, (SendChannel) producerScope, Boolean.valueOf(powerManager.isInteractive()), "PowerRepository", null, 4, null);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        PowerRepositoryImpl$isInteractive$1 powerRepositoryImpl$isInteractive$1 = new PowerRepositoryImpl$isInteractive$1(this.$dispatcher, this.$manager, continuation);
        powerRepositoryImpl$isInteractive$1.L$0 = obj;
        return powerRepositoryImpl$isInteractive$1;
    }

    public final Object invoke(ProducerScope<? super Boolean> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.power.data.repository.PowerRepositoryImpl$isInteractive$1$receiver$1, android.content.BroadcastReceiver] */
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final PowerManager powerManager = this.$manager;
            final ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.power.data.repository.PowerRepositoryImpl$isInteractive$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    PowerRepositoryImpl$isInteractive$1.access$invokeSuspend$send(producerScope, powerManager);
                }
            };
            BroadcastDispatcher broadcastDispatcher = this.$dispatcher;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            Unit unit = Unit.INSTANCE;
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r0, intentFilter, null, null, 0, null, 60, null);
            invokeSuspend$send(producerScope, this.$manager);
            final BroadcastDispatcher broadcastDispatcher2 = this.$dispatcher;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.power.data.repository.PowerRepositoryImpl$isInteractive$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m3659invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke */
                public final void m3659invoke() {
                    broadcastDispatcher2.unregisterReceiver(r0);
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