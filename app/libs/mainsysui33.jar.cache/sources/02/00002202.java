package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.policy.UserSwitcherController;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserName$1", f = "UserSwitcherRepository.kt", l = {104, 105}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$currentUserName$1.class */
public final class UserSwitcherRepositoryImpl$currentUserName$1 extends SuspendLambda implements Function2<ProducerScope<? super String>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public Object L$1;
    public int label;
    public final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$currentUserName$1(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super UserSwitcherRepositoryImpl$currentUserName$1> continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserName$1$callback$1.1.invokeSuspend(java.lang.Object):java.lang.Object] */
    public static final /* synthetic */ Object access$invokeSuspend$updateState(ProducerScope producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
        return invokeSuspend$updateState(producerScope, userSwitcherRepositoryImpl, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object invokeSuspend$updateState(ProducerScope<? super String> producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super Unit> continuation) {
        UserSwitcherRepositoryImpl$currentUserName$1$updateState$1 userSwitcherRepositoryImpl$currentUserName$1$updateState$1;
        int i;
        SendChannel sendChannel;
        Object currentUser;
        Object obj;
        ChannelExt channelExt;
        if (continuation instanceof UserSwitcherRepositoryImpl$currentUserName$1$updateState$1) {
            UserSwitcherRepositoryImpl$currentUserName$1$updateState$1 userSwitcherRepositoryImpl$currentUserName$1$updateState$12 = (UserSwitcherRepositoryImpl$currentUserName$1$updateState$1) continuation;
            int i2 = userSwitcherRepositoryImpl$currentUserName$1$updateState$12.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                userSwitcherRepositoryImpl$currentUserName$1$updateState$12.label = i2 - 2147483648;
                userSwitcherRepositoryImpl$currentUserName$1$updateState$1 = userSwitcherRepositoryImpl$currentUserName$1$updateState$12;
                Object obj2 = userSwitcherRepositoryImpl$currentUserName$1$updateState$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = userSwitcherRepositoryImpl$currentUserName$1$updateState$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    ChannelExt channelExt2 = ChannelExt.INSTANCE;
                    sendChannel = (SendChannel) producerScope;
                    userSwitcherRepositoryImpl$currentUserName$1$updateState$1.L$0 = channelExt2;
                    userSwitcherRepositoryImpl$currentUserName$1$updateState$1.L$1 = sendChannel;
                    userSwitcherRepositoryImpl$currentUserName$1$updateState$1.label = 1;
                    currentUser = userSwitcherRepositoryImpl.getCurrentUser(userSwitcherRepositoryImpl$currentUserName$1$updateState$1);
                    obj = currentUser;
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    channelExt = channelExt2;
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    sendChannel = (SendChannel) userSwitcherRepositoryImpl$currentUserName$1$updateState$1.L$1;
                    channelExt = (ChannelExt) userSwitcherRepositoryImpl$currentUserName$1$updateState$1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    obj = obj2;
                }
                ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, obj, "UserSwitcherRepositoryImpl", null, 4, null);
                return Unit.INSTANCE;
            }
        }
        userSwitcherRepositoryImpl$currentUserName$1$updateState$1 = new UserSwitcherRepositoryImpl$currentUserName$1$updateState$1(continuation);
        Object obj22 = userSwitcherRepositoryImpl$currentUserName$1$updateState$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = userSwitcherRepositoryImpl$currentUserName$1$updateState$1.label;
        if (i != 0) {
        }
        ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, obj, "UserSwitcherRepositoryImpl", null, 4, null);
        return Unit.INSTANCE;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        UserSwitcherRepositoryImpl$currentUserName$1 userSwitcherRepositoryImpl$currentUserName$1 = new UserSwitcherRepositoryImpl$currentUserName$1(this.this$0, continuation);
        userSwitcherRepositoryImpl$currentUserName$1.L$0 = obj;
        return userSwitcherRepositoryImpl$currentUserName$1;
    }

    public final Object invoke(ProducerScope<? super String> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        final ProducerScope producerScope;
        UserSwitcherController.UserSwitchCallback userSwitchCallback;
        UserSwitcherController userSwitcherController;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            userSwitchCallback = new UserSwitcherController.UserSwitchCallback() { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserName$1$callback$1

                @DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserName$1$callback$1$1", f = "UserSwitcherRepository.kt", l = {101}, m = "invokeSuspend")
                /* renamed from: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserName$1$callback$1$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$currentUserName$1$callback$1$1.class */
                public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    public final /* synthetic */ ProducerScope<String> $$this$conflatedCallbackFlow;
                    public int label;
                    public final /* synthetic */ UserSwitcherRepositoryImpl this$0;

                    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlinx.coroutines.channels.ProducerScope<? super java.lang.String> */
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    public AnonymousClass1(ProducerScope<? super String> producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super AnonymousClass1> continuation) {
                        super(2, continuation);
                        this.$$this$conflatedCallbackFlow = producerScope;
                        this.this$0 = userSwitcherRepositoryImpl;
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
                            ProducerScope<String> producerScope = this.$$this$conflatedCallbackFlow;
                            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
                            this.label = 1;
                            if (UserSwitcherRepositoryImpl$currentUserName$1.access$invokeSuspend$updateState(producerScope, userSwitcherRepositoryImpl, this) == coroutine_suspended) {
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

                public final void onUserSwitched() {
                    CoroutineScope coroutineScope = producerScope;
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass1(coroutineScope, userSwitcherRepositoryImpl, null), 3, (Object) null);
                }
            };
            userSwitcherController = this.this$0.userSwitcherController;
            userSwitcherController.addUserSwitchCallback(userSwitchCallback);
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl2 = this.this$0;
            this.L$0 = producerScope;
            this.L$1 = userSwitchCallback;
            this.label = 1;
            if (invokeSuspend$updateState(producerScope, userSwitcherRepositoryImpl2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            if (i == 2) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            userSwitchCallback = (UserSwitcherController.UserSwitchCallback) this.L$1;
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
        }
        final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl3 = this.this$0;
        final UserSwitcherController.UserSwitchCallback userSwitchCallback2 = userSwitchCallback;
        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserName$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m3926invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke */
            public final void m3926invoke() {
                UserSwitcherController userSwitcherController2;
                userSwitcherController2 = userSwitcherRepositoryImpl3.userSwitcherController;
                userSwitcherController2.removeUserSwitchCallback(userSwitchCallback2);
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