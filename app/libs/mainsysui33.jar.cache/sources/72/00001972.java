package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.WalletCard;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/QuickAccessWalletKeyguardQuickAffordanceConfig.class */
public final class QuickAccessWalletKeyguardQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final Companion Companion = new Companion(null);
    public final ActivityStarter activityStarter;
    public final Context context;
    public final String pickerName;
    public final QuickAccessWalletController walletController;
    public final String key = "wallet";
    public final int pickerIconResourceId = R$drawable.ic_wallet_lockscreen;
    public final Flow<KeyguardQuickAffordanceConfig.LockScreenState> lockScreenState = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1(this, null));

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/QuickAccessWalletKeyguardQuickAffordanceConfig$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public QuickAccessWalletKeyguardQuickAffordanceConfig(Context context, QuickAccessWalletController quickAccessWalletController, ActivityStarter activityStarter) {
        this.context = context;
        this.walletController = quickAccessWalletController;
        this.activityStarter = activityStarter;
        this.pickerName = context.getString(R$string.accessibility_wallet_button);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public Flow<KeyguardQuickAffordanceConfig.LockScreenState> getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public int getPickerIconResourceId() {
        return this.pickerIconResourceId;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public String getPickerName() {
        return this.pickerName;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00f0  */
    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object getPickerScreenState(Continuation<? super KeyguardQuickAffordanceConfig.PickerScreenState> continuation) {
        Continuation<? super List<WalletCard>> quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1;
        int i;
        QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig;
        Object obj;
        KeyguardQuickAffordanceConfig.PickerScreenState pickerScreenState;
        if (continuation instanceof QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1) {
            quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1 = (QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1) continuation;
            int i2 = quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.label = i2 - 2147483648;
                Object obj2 = quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    if (!this.walletController.isWalletEnabled()) {
                        pickerScreenState = KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
                        return pickerScreenState;
                    }
                    quickAccessWalletKeyguardQuickAffordanceConfig = this;
                    if (this.walletController.getWalletClient().getTileIcon() != null) {
                        quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.L$0 = this;
                        quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.label = 1;
                        Object queryCards = queryCards(quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1);
                        quickAccessWalletKeyguardQuickAffordanceConfig = this;
                        obj = queryCards;
                        if (queryCards == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                    String componentName = quickAccessWalletKeyguardQuickAffordanceConfig.toComponentName(quickAccessWalletKeyguardQuickAffordanceConfig.walletController.getWalletClient().createWalletSettingsIntent());
                    pickerScreenState = new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(CollectionsKt__CollectionsKt.listOf(new String[]{quickAccessWalletKeyguardQuickAffordanceConfig.context.getString(R$string.keyguard_affordance_enablement_dialog_wallet_instruction_1), quickAccessWalletKeyguardQuickAffordanceConfig.context.getString(R$string.keyguard_affordance_enablement_dialog_wallet_instruction_2)}), componentName != null ? quickAccessWalletKeyguardQuickAffordanceConfig.context.getString(R$string.keyguard_affordance_enablement_dialog_action_template, quickAccessWalletKeyguardQuickAffordanceConfig.getPickerName()) : null, componentName);
                    return pickerScreenState;
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    quickAccessWalletKeyguardQuickAffordanceConfig = (QuickAccessWalletKeyguardQuickAffordanceConfig) quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    obj = obj2;
                }
                if (!((List) obj).isEmpty()) {
                    pickerScreenState = KeyguardQuickAffordanceConfig.PickerScreenState.Default.INSTANCE;
                    return pickerScreenState;
                }
                String componentName2 = quickAccessWalletKeyguardQuickAffordanceConfig.toComponentName(quickAccessWalletKeyguardQuickAffordanceConfig.walletController.getWalletClient().createWalletSettingsIntent());
                pickerScreenState = new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(CollectionsKt__CollectionsKt.listOf(new String[]{quickAccessWalletKeyguardQuickAffordanceConfig.context.getString(R$string.keyguard_affordance_enablement_dialog_wallet_instruction_1), quickAccessWalletKeyguardQuickAffordanceConfig.context.getString(R$string.keyguard_affordance_enablement_dialog_wallet_instruction_2)}), componentName2 != null ? quickAccessWalletKeyguardQuickAffordanceConfig.context.getString(R$string.keyguard_affordance_enablement_dialog_action_template, quickAccessWalletKeyguardQuickAffordanceConfig.getPickerName()) : null, componentName2);
                return pickerScreenState;
            }
        }
        quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1 = new QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1(this, continuation);
        Object obj22 = quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = quickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.label;
        if (i != 0) {
        }
        if (!((List) obj).isEmpty()) {
        }
        String componentName22 = quickAccessWalletKeyguardQuickAffordanceConfig.toComponentName(quickAccessWalletKeyguardQuickAffordanceConfig.walletController.getWalletClient().createWalletSettingsIntent());
        pickerScreenState = new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(CollectionsKt__CollectionsKt.listOf(new String[]{quickAccessWalletKeyguardQuickAffordanceConfig.context.getString(R$string.keyguard_affordance_enablement_dialog_wallet_instruction_1), quickAccessWalletKeyguardQuickAffordanceConfig.context.getString(R$string.keyguard_affordance_enablement_dialog_wallet_instruction_2)}), componentName22 != null ? quickAccessWalletKeyguardQuickAffordanceConfig.context.getString(R$string.keyguard_affordance_enablement_dialog_action_template, quickAccessWalletKeyguardQuickAffordanceConfig.getPickerName()) : null, componentName22);
        return pickerScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        QuickAccessWalletController quickAccessWalletController = this.walletController;
        ActivityStarter activityStarter = this.activityStarter;
        ActivityLaunchAnimator.Controller controller = null;
        if (expandable != null) {
            controller = Expandable.activityLaunchController$default(expandable, null, 1, null);
        }
        quickAccessWalletController.startQuickAccessUiIntent(activityStarter, controller, true);
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    public final Object queryCards(Continuation<? super List<WalletCard>> continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        this.walletController.queryWalletCards(new QuickAccessWalletClient.OnWalletCardsRetrievedCallback() { // from class: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$queryCards$2$callback$1
            public void onWalletCardRetrievalError(GetWalletCardsError getWalletCardsError) {
                CancellableContinuation<List<WalletCard>> cancellableContinuation = cancellableContinuationImpl;
                Result.Companion companion = Result.Companion;
                cancellableContinuation.resumeWith(Result.constructor-impl(CollectionsKt__CollectionsKt.emptyList()));
            }

            public void onWalletCardsRetrieved(GetWalletCardsResponse getWalletCardsResponse) {
                CancellableContinuation<List<WalletCard>> cancellableContinuation = cancellableContinuationImpl;
                Result.Companion companion = Result.Companion;
                List<WalletCard> walletCards = getWalletCardsResponse != null ? getWalletCardsResponse.getWalletCards() : null;
                List<WalletCard> list = walletCards;
                if (walletCards == null) {
                    list = CollectionsKt__CollectionsKt.emptyList();
                }
                cancellableContinuation.resumeWith(Result.constructor-impl(list));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    public final KeyguardQuickAffordanceConfig.LockScreenState state(boolean z, boolean z2, Drawable drawable) {
        return (z && z2 && drawable != null) ? new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawable, new ContentDescription.Resource(R$string.accessibility_wallet_button)), null, 2, null) : KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
    }

    public final String toComponentName(Intent intent) {
        if (intent == null) {
            return null;
        }
        return KeyguardQuickAffordanceConfig.Companion.componentName(intent.getPackage(), intent.getAction());
    }
}