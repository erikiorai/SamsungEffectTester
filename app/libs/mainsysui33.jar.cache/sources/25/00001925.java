package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import java.util.Collection;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1", f = "HomeControlsKeyguardQuickAffordanceConfig.kt", l = {141}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1.class */
public final class HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1 extends SuspendLambda implements Function2<ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ ControlsListingController $listingController;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ HomeControlsKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1(ControlsListingController controlsListingController, HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig, Continuation<? super HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1> continuation) {
        super(2, continuation);
        this.$listingController = controlsListingController;
        this.this$0 = homeControlsKeyguardQuickAffordanceConfig;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1 homeControlsKeyguardQuickAffordanceConfig$stateInternal$1 = new HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1(this.$listingController, this.this$0, continuation);
        homeControlsKeyguardQuickAffordanceConfig$stateInternal$1.L$0 = obj;
        return homeControlsKeyguardQuickAffordanceConfig$stateInternal$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1$callback$1, java.lang.Object] */
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig = this.this$0;
            final ?? r0 = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1$callback$1
                @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
                public void onServicesUpdated(List<ControlsServiceInfo> list) {
                    ControlsComponent controlsComponent;
                    ControlsComponent controlsComponent2;
                    boolean z;
                    ControlsComponent controlsComponent3;
                    ControlsComponent controlsComponent4;
                    KeyguardQuickAffordanceConfig.LockScreenState state;
                    controlsComponent = HomeControlsKeyguardQuickAffordanceConfig.this.component;
                    ControlsController orElse = controlsComponent.getControlsController().orElse(null);
                    List<StructureInfo> favorites = orElse != null ? orElse.getFavorites() : null;
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    SendChannel sendChannel = producerScope;
                    controlsComponent2 = HomeControlsKeyguardQuickAffordanceConfig.this.component;
                    boolean isEnabled = controlsComponent2.isEnabled();
                    boolean z2 = favorites != null && (favorites.isEmpty() ^ true);
                    List<ControlsServiceInfo> list2 = list;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        for (ControlsServiceInfo controlsServiceInfo : list2) {
                            if (controlsServiceInfo.getPanelActivity() != null) {
                                z = true;
                                break;
                            }
                        }
                    }
                    z = false;
                    boolean isEmpty = list.isEmpty();
                    controlsComponent3 = HomeControlsKeyguardQuickAffordanceConfig.this.component;
                    int tileImageId = controlsComponent3.getTileImageId();
                    controlsComponent4 = HomeControlsKeyguardQuickAffordanceConfig.this.component;
                    state = HomeControlsKeyguardQuickAffordanceConfig.this.state(isEnabled, z2, z, !isEmpty, controlsComponent4.getVisibility(), Integer.valueOf(tileImageId));
                    ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, state, "HomeControlsKeyguardQuickAffordanceConfig", null, 4, null);
                }
            };
            this.$listingController.addCallback((Object) r0);
            final ControlsListingController controlsListingController = this.$listingController;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2931invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2931invoke() {
                    ControlsListingController.this.removeCallback(r0);
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