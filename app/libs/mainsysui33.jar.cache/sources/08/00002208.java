package com.android.systemui.qs.footer.data.repository;

import android.os.Handler;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SettingsProxy;
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

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1", f = "UserSwitcherRepository.kt", l = {91, 92}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$isEnabled$1.class */
public final class UserSwitcherRepositoryImpl$isEnabled$1 extends SuspendLambda implements Function2<ProducerScope<? super Boolean>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public Object L$1;
    public int label;
    public final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$isEnabled$1(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super UserSwitcherRepositoryImpl$isEnabled$1> continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object invokeSuspend$updateState(ProducerScope<? super Boolean> producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super Unit> continuation) {
        UserSwitcherRepositoryImpl$isEnabled$1$updateState$1 userSwitcherRepositoryImpl$isEnabled$1$updateState$1;
        int i;
        SendChannel sendChannel;
        Object isUserSwitcherEnabled;
        Object obj;
        ChannelExt channelExt;
        if (continuation instanceof UserSwitcherRepositoryImpl$isEnabled$1$updateState$1) {
            UserSwitcherRepositoryImpl$isEnabled$1$updateState$1 userSwitcherRepositoryImpl$isEnabled$1$updateState$12 = (UserSwitcherRepositoryImpl$isEnabled$1$updateState$1) continuation;
            int i2 = userSwitcherRepositoryImpl$isEnabled$1$updateState$12.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                userSwitcherRepositoryImpl$isEnabled$1$updateState$12.label = i2 - 2147483648;
                userSwitcherRepositoryImpl$isEnabled$1$updateState$1 = userSwitcherRepositoryImpl$isEnabled$1$updateState$12;
                Object obj2 = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    ChannelExt channelExt2 = ChannelExt.INSTANCE;
                    sendChannel = (SendChannel) producerScope;
                    userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$0 = channelExt2;
                    userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$1 = sendChannel;
                    userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label = 1;
                    isUserSwitcherEnabled = userSwitcherRepositoryImpl.isUserSwitcherEnabled(userSwitcherRepositoryImpl$isEnabled$1$updateState$1);
                    obj = isUserSwitcherEnabled;
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    channelExt = channelExt2;
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    sendChannel = (SendChannel) userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$1;
                    channelExt = (ChannelExt) userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    obj = obj2;
                }
                ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, obj, "UserSwitcherRepositoryImpl", null, 4, null);
                return Unit.INSTANCE;
            }
        }
        userSwitcherRepositoryImpl$isEnabled$1$updateState$1 = new UserSwitcherRepositoryImpl$isEnabled$1$updateState$1(continuation);
        Object obj22 = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label;
        if (i != 0) {
        }
        ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, obj, "UserSwitcherRepositoryImpl", null, 4, null);
        return Unit.INSTANCE;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        UserSwitcherRepositoryImpl$isEnabled$1 userSwitcherRepositoryImpl$isEnabled$1 = new UserSwitcherRepositoryImpl$isEnabled$1(this.this$0, continuation);
        userSwitcherRepositoryImpl$isEnabled$1.L$0 = obj;
        return userSwitcherRepositoryImpl$isEnabled$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super Boolean> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v2, resolved type: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1 */
    /* JADX WARN: Multi-variable type inference failed */
    public final Object invokeSuspend(Object obj) {
        final ProducerScope producerScope;
        final GlobalSettings globalSettings;
        final Handler handler;
        UserTracker userTracker;
        UserSwitcherRepositoryImpl$isEnabled$1$observer$1 userSwitcherRepositoryImpl$isEnabled$1$observer$1;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
            globalSettings = this.this$0.globalSetting;
            handler = this.this$0.bgHandler;
            userTracker = this.this$0.userTracker;
            final int userId = userTracker.getUserId();
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            SettingObserver settingObserver = new SettingObserver(producerScope, userSwitcherRepositoryImpl, globalSettings, handler, userId) { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1
                public final /* synthetic */ ProducerScope<Boolean> $$this$conflatedCallbackFlow;
                public final /* synthetic */ UserSwitcherRepositoryImpl this$0;

                /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: kotlinx.coroutines.channels.ProducerScope<? super java.lang.Boolean> */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super((SettingsProxy) globalSettings, handler, "user_switcher_enabled", userId);
                }

                @Override // com.android.systemui.qs.SettingObserver
                public void handleValueChanged(int i2, boolean z) {
                    if (z) {
                        CoroutineScope coroutineScope = this.$$this$conflatedCallbackFlow;
                        BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1(coroutineScope, this.this$0, null), 3, (Object) null);
                    }
                }
            };
            settingObserver.setListening(true);
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl2 = this.this$0;
            this.L$0 = producerScope;
            this.L$1 = settingObserver;
            this.label = 1;
            userSwitcherRepositoryImpl$isEnabled$1$observer$1 = settingObserver;
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
            userSwitcherRepositoryImpl$isEnabled$1$observer$1 = (UserSwitcherRepositoryImpl$isEnabled$1$observer$1) this.L$1;
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
        }
        final UserSwitcherRepositoryImpl$isEnabled$1$observer$1 userSwitcherRepositoryImpl$isEnabled$1$observer$12 = userSwitcherRepositoryImpl$isEnabled$1$observer$1;
        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1.1
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m3927invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m3927invoke() {
                setListening(false);
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