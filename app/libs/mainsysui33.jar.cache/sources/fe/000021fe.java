package com.android.systemui.qs.footer.data.repository;

import android.graphics.drawable.Drawable;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.policy.UserInfoController;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
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

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1", f = "UserSwitcherRepository.kt", l = {120}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$currentUserInfo$1.class */
public final class UserSwitcherRepositoryImpl$currentUserInfo$1 extends SuspendLambda implements Function2<ProducerScope<? super Pair<? extends Drawable, ? extends Boolean>>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$currentUserInfo$1(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super UserSwitcherRepositoryImpl$currentUserInfo$1> continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        UserSwitcherRepositoryImpl$currentUserInfo$1 userSwitcherRepositoryImpl$currentUserInfo$1 = new UserSwitcherRepositoryImpl$currentUserInfo$1(this.this$0, continuation);
        userSwitcherRepositoryImpl$currentUserInfo$1.L$0 = obj;
        return userSwitcherRepositoryImpl$currentUserInfo$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super Pair<? extends Drawable, Boolean>> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        UserInfoController userInfoController;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            final UserInfoController.OnUserInfoChangedListener onUserInfoChangedListener = new UserInfoController.OnUserInfoChangedListener() { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1

                @DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1$1", f = "UserSwitcherRepository.kt", l = {114}, m = "invokeSuspend")
                /* renamed from: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1$1.class */
                public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    public final /* synthetic */ ProducerScope<Pair<? extends Drawable, Boolean>> $$this$conflatedCallbackFlow;
                    public final /* synthetic */ Drawable $picture;
                    public Object L$0;
                    public Object L$1;
                    public Object L$2;
                    public int label;
                    public final /* synthetic */ UserSwitcherRepositoryImpl this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(ProducerScope<? super Pair<? extends Drawable, Boolean>> producerScope, Drawable drawable, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super AnonymousClass1> continuation) {
                        super(2, continuation);
                        this.$$this$conflatedCallbackFlow = producerScope;
                        this.$picture = drawable;
                        this.this$0 = userSwitcherRepositoryImpl;
                    }

                    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                        return new AnonymousClass1(this.$$this$conflatedCallbackFlow, this.$picture, this.this$0, continuation);
                    }

                    /* JADX DEBUG: Method merged with bridge method */
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
                    }

                    public final Object invokeSuspend(Object obj) {
                        SendChannel sendChannel;
                        Drawable drawable;
                        Object isGuestUser;
                        Object obj2;
                        ChannelExt channelExt;
                        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            ChannelExt channelExt2 = ChannelExt.INSTANCE;
                            sendChannel = this.$$this$conflatedCallbackFlow;
                            drawable = this.$picture;
                            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
                            this.L$0 = channelExt2;
                            this.L$1 = sendChannel;
                            this.L$2 = drawable;
                            this.label = 1;
                            isGuestUser = userSwitcherRepositoryImpl.isGuestUser(this);
                            obj2 = isGuestUser;
                            if (obj2 == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            channelExt = channelExt2;
                        } else if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        } else {
                            drawable = (Drawable) this.L$2;
                            sendChannel = (SendChannel) this.L$1;
                            channelExt = (ChannelExt) this.L$0;
                            ResultKt.throwOnFailure(obj);
                            obj2 = obj;
                        }
                        ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, TuplesKt.to(drawable, obj2), "UserSwitcherRepositoryImpl", null, 4, null);
                        return Unit.INSTANCE;
                    }
                }

                public final void onUserInfoChanged(String str, Drawable drawable, String str2) {
                    CoroutineScope coroutineScope = producerScope;
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass1(coroutineScope, drawable, userSwitcherRepositoryImpl, null), 3, (Object) null);
                }
            };
            userInfoController = this.this$0.userInfoController;
            userInfoController.addCallback(onUserInfoChangedListener);
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl2 = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m3925invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m3925invoke() {
                    UserInfoController userInfoController2;
                    userInfoController2 = UserSwitcherRepositoryImpl.this.userInfoController;
                    userInfoController2.removeCallback(onUserInfoChangedListener);
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