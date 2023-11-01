package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.settings.UserTracker;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1", f = "KeyguardQuickAffordanceRemoteUserSelectionManager.kt", l = {69}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1.class */
public final class KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1 extends SuspendLambda implements Function2<ProducerScope<? super Integer>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ KeyguardQuickAffordanceRemoteUserSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1(KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager, Continuation<? super KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceRemoteUserSelectionManager;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1 keyguardQuickAffordanceRemoteUserSelectionManager$userId$1 = new KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1(this.this$0, continuation);
        keyguardQuickAffordanceRemoteUserSelectionManager$userId$1.L$0 = obj;
        return keyguardQuickAffordanceRemoteUserSelectionManager$userId$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super Integer> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1$callback$1, com.android.systemui.settings.UserTracker$Callback] */
    public final Object invokeSuspend(Object obj) {
        UserTracker userTracker;
        UserTracker userTracker2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SendChannel sendChannel = (ProducerScope) this.L$0;
            final ?? r0 = new UserTracker.Callback() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1$callback$1
                public void onUserChanged(int i2, Context context) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, sendChannel, Integer.valueOf(i2), "KeyguardQuickAffordanceMultiUserSelectionManager", null, 4, null);
                }
            };
            userTracker = this.this$0.userTracker;
            userTracker.addCallback((UserTracker.Callback) r0, new Executor() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1.1
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    runnable.run();
                }
            });
            userTracker2 = this.this$0.userTracker;
            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, sendChannel, Boxing.boxInt(userTracker2.getUserId()), "KeyguardQuickAffordanceMultiUserSelectionManager", null, 4, null);
            final KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2954invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2954invoke() {
                    UserTracker userTracker3;
                    userTracker3 = KeyguardQuickAffordanceRemoteUserSelectionManager.this.userTracker;
                    userTracker3.removeCallback(r0);
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(sendChannel, function0, this) == coroutine_suspended) {
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