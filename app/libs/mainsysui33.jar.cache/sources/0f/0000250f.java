package com.android.systemui.security.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.security.data.model.SecurityModel;
import com.android.systemui.statusbar.policy.SecurityController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

@DebugMetadata(c = "com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1", f = "SecurityRepository.kt", l = {51, 52}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/security/data/repository/SecurityRepositoryImpl$security$1.class */
public final class SecurityRepositoryImpl$security$1 extends SuspendLambda implements Function2<ProducerScope<? super SecurityModel>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public Object L$1;
    public int label;
    public final /* synthetic */ SecurityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecurityRepositoryImpl$security$1(SecurityRepositoryImpl securityRepositoryImpl, Continuation<? super SecurityRepositoryImpl$security$1> continuation) {
        super(2, continuation);
        this.this$0 = securityRepositoryImpl;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1.1.invokeSuspend(java.lang.Object):java.lang.Object] */
    public static final /* synthetic */ Object access$invokeSuspend$updateState(ProducerScope producerScope, SecurityRepositoryImpl securityRepositoryImpl, Continuation continuation) {
        return invokeSuspend$updateState(producerScope, securityRepositoryImpl, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object invokeSuspend$updateState(ProducerScope<? super SecurityModel> producerScope, SecurityRepositoryImpl securityRepositoryImpl, Continuation<? super Unit> continuation) {
        SecurityRepositoryImpl$security$1$updateState$1 securityRepositoryImpl$security$1$updateState$1;
        int i;
        SendChannel sendChannel;
        SecurityController securityController;
        CoroutineDispatcher coroutineDispatcher;
        Object create;
        ChannelExt channelExt;
        if (continuation instanceof SecurityRepositoryImpl$security$1$updateState$1) {
            SecurityRepositoryImpl$security$1$updateState$1 securityRepositoryImpl$security$1$updateState$12 = (SecurityRepositoryImpl$security$1$updateState$1) continuation;
            int i2 = securityRepositoryImpl$security$1$updateState$12.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                securityRepositoryImpl$security$1$updateState$12.label = i2 - 2147483648;
                securityRepositoryImpl$security$1$updateState$1 = securityRepositoryImpl$security$1$updateState$12;
                Object obj = securityRepositoryImpl$security$1$updateState$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = securityRepositoryImpl$security$1$updateState$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelExt channelExt2 = ChannelExt.INSTANCE;
                    sendChannel = (SendChannel) producerScope;
                    SecurityModel.Companion companion = SecurityModel.Companion;
                    securityController = securityRepositoryImpl.securityController;
                    coroutineDispatcher = securityRepositoryImpl.bgDispatcher;
                    securityRepositoryImpl$security$1$updateState$1.L$0 = channelExt2;
                    securityRepositoryImpl$security$1$updateState$1.L$1 = sendChannel;
                    securityRepositoryImpl$security$1$updateState$1.label = 1;
                    create = companion.create(securityController, coroutineDispatcher, securityRepositoryImpl$security$1$updateState$1);
                    if (create == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    channelExt = channelExt2;
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    sendChannel = (SendChannel) securityRepositoryImpl$security$1$updateState$1.L$1;
                    channelExt = (ChannelExt) securityRepositoryImpl$security$1$updateState$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    create = obj;
                }
                ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, create, "SecurityRepositoryImpl", null, 4, null);
                return Unit.INSTANCE;
            }
        }
        securityRepositoryImpl$security$1$updateState$1 = new SecurityRepositoryImpl$security$1$updateState$1(continuation);
        Object obj2 = securityRepositoryImpl$security$1$updateState$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = securityRepositoryImpl$security$1$updateState$1.label;
        if (i != 0) {
        }
        ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, create, "SecurityRepositoryImpl", null, 4, null);
        return Unit.INSTANCE;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        SecurityRepositoryImpl$security$1 securityRepositoryImpl$security$1 = new SecurityRepositoryImpl$security$1(this.this$0, continuation);
        securityRepositoryImpl$security$1.L$0 = obj;
        return securityRepositoryImpl$security$1;
    }

    public final Object invoke(ProducerScope<? super SecurityModel> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        final ProducerScope producerScope;
        SecurityController.SecurityControllerCallback securityControllerCallback;
        SecurityController securityController;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
            final SecurityRepositoryImpl securityRepositoryImpl = this.this$0;
            securityControllerCallback = new SecurityController.SecurityControllerCallback() { // from class: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1

                @DebugMetadata(c = "com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1$1", f = "SecurityRepository.kt", l = {48}, m = "invokeSuspend")
                /* renamed from: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/security/data/repository/SecurityRepositoryImpl$security$1$callback$1$1.class */
                public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    public final /* synthetic */ ProducerScope<SecurityModel> $$this$conflatedCallbackFlow;
                    public int label;
                    public final /* synthetic */ SecurityRepositoryImpl this$0;

                    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlinx.coroutines.channels.ProducerScope<? super com.android.systemui.security.data.model.SecurityModel> */
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    public AnonymousClass1(ProducerScope<? super SecurityModel> producerScope, SecurityRepositoryImpl securityRepositoryImpl, Continuation<? super AnonymousClass1> continuation) {
                        super(2, continuation);
                        this.$$this$conflatedCallbackFlow = producerScope;
                        this.this$0 = securityRepositoryImpl;
                    }

                    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                        return new AnonymousClass1(this.$$this$conflatedCallbackFlow, this.this$0, continuation);
                    }

                    /* JADX DEBUG: Method merged with bridge method */
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
                    }

                    public final Object invokeSuspend(Object obj) {
                        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            ProducerScope<SecurityModel> producerScope = this.$$this$conflatedCallbackFlow;
                            SecurityRepositoryImpl securityRepositoryImpl = this.this$0;
                            this.label = 1;
                            if (SecurityRepositoryImpl$security$1.access$invokeSuspend$updateState(producerScope, securityRepositoryImpl, this) == coroutine_suspended) {
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

                public final void onStateChanged() {
                    CoroutineScope coroutineScope = producerScope;
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass1(coroutineScope, securityRepositoryImpl, null), 3, (Object) null);
                }
            };
            securityController = this.this$0.securityController;
            securityController.addCallback(securityControllerCallback);
            SecurityRepositoryImpl securityRepositoryImpl2 = this.this$0;
            this.L$0 = producerScope;
            this.L$1 = securityControllerCallback;
            this.label = 1;
            if (invokeSuspend$updateState(producerScope, securityRepositoryImpl2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            if (i == 2) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            securityControllerCallback = (SecurityController.SecurityControllerCallback) this.L$1;
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
        }
        final SecurityRepositoryImpl securityRepositoryImpl3 = this.this$0;
        final SecurityController.SecurityControllerCallback securityControllerCallback2 = securityControllerCallback;
        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m4350invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke */
            public final void m4350invoke() {
                SecurityController securityController2;
                securityController2 = securityRepositoryImpl3.securityController;
                securityController2.removeCallback(securityControllerCallback2);
            }
        };
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
            return coroutine_suspended;
        }
        return Unit.INSTANCE;
    }
}