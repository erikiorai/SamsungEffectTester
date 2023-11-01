package com.android.systemui.keyguard.data.quickaffordance;

import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.WalletCard;
import android.util.Log;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import java.util.List;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1", f = "QuickAccessWalletKeyguardQuickAffordanceConfig.kt", l = {93}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1.class */
public final class QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2<ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1(QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, Continuation<? super QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1> continuation) {
        super(2, continuation);
        this.this$0 = quickAccessWalletKeyguardQuickAffordanceConfig;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 = new QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig = this.this$0;
            QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback = new QuickAccessWalletClient.OnWalletCardsRetrievedCallback() { // from class: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1
                public void onWalletCardRetrievalError(GetWalletCardsError getWalletCardsError) {
                    CharSequence message = getWalletCardsError != null ? getWalletCardsError.getMessage() : null;
                    Log.e("QuickAccessWalletKeyguardQuickAffordanceConfig", "Wallet card retrieval error, message: \"" + ((Object) message) + "\"");
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE, "QuickAccessWalletKeyguardQuickAffordanceConfig", null, 4, null);
                }

                public void onWalletCardsRetrieved(GetWalletCardsResponse getWalletCardsResponse) {
                    KeyguardQuickAffordanceConfig.LockScreenState state;
                    List<WalletCard> walletCards;
                    boolean z = false;
                    if (getWalletCardsResponse != null) {
                        z = false;
                        if (getWalletCardsResponse.getWalletCards() != null) {
                            z = false;
                            if (!walletCards.isEmpty()) {
                                z = true;
                            }
                        }
                    }
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    SendChannel sendChannel = producerScope;
                    QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig2 = quickAccessWalletKeyguardQuickAffordanceConfig;
                    state = quickAccessWalletKeyguardQuickAffordanceConfig2.state(quickAccessWalletKeyguardQuickAffordanceConfig2.walletController.isWalletEnabled(), z, quickAccessWalletKeyguardQuickAffordanceConfig.walletController.getWalletClient().getTileIcon());
                    ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, state, "QuickAccessWalletKeyguardQuickAffordanceConfig", null, 4, null);
                }
            };
            this.this$0.walletController.setupWalletChangeObservers(onWalletCardsRetrievedCallback, new QuickAccessWalletController.WalletChangeEvent[]{QuickAccessWalletController.WalletChangeEvent.WALLET_PREFERENCE_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE});
            this.this$0.walletController.updateWalletPreference();
            this.this$0.walletController.queryWalletCards(onWalletCardsRetrievedCallback);
            final QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig2 = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1.1
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2960invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2960invoke() {
                    QuickAccessWalletKeyguardQuickAffordanceConfig.this.walletController.unregisterWalletChangeObservers(new QuickAccessWalletController.WalletChangeEvent[]{QuickAccessWalletController.WalletChangeEvent.WALLET_PREFERENCE_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE});
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