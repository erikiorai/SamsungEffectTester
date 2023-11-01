package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.Intent;
import com.android.systemui.R$string;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/HomeControlsKeyguardQuickAffordanceConfig.class */
public final class HomeControlsKeyguardQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final Companion Companion = new Companion(null);
    public final Context appContext;
    public final ControlsComponent component;
    public final Context context;
    public final Flow<KeyguardQuickAffordanceConfig.LockScreenState> lockScreenState;
    public final String key = BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN;
    public final Lazy pickerName$delegate = LazyKt__LazyJVMKt.lazy(new Function0<String>() { // from class: com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$pickerName$2
        {
            super(0);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final String invoke() {
            return HomeControlsKeyguardQuickAffordanceConfig.access$getContext$p(HomeControlsKeyguardQuickAffordanceConfig.this).getString(HomeControlsKeyguardQuickAffordanceConfig.access$getComponent$p(HomeControlsKeyguardQuickAffordanceConfig.this).getTileTitleId());
        }
    });
    public final Lazy pickerIconResourceId$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Integer>() { // from class: com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$pickerIconResourceId$2
        {
            super(0);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* renamed from: invoke */
        public final Integer m2929invoke() {
            return Integer.valueOf(HomeControlsKeyguardQuickAffordanceConfig.access$getComponent$p(HomeControlsKeyguardQuickAffordanceConfig.this).getTileImageId());
        }
    });

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/HomeControlsKeyguardQuickAffordanceConfig$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public HomeControlsKeyguardQuickAffordanceConfig(Context context, ControlsComponent controlsComponent) {
        this.context = context;
        this.component = controlsComponent;
        this.appContext = context.getApplicationContext();
        this.lockScreenState = FlowKt.transformLatest(controlsComponent.getCanShowWhileLockedSetting(), new HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1(null, this));
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$pickerIconResourceId$2.invoke():java.lang.Integer, com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$pickerName$2.invoke():java.lang.String] */
    public static final /* synthetic */ ControlsComponent access$getComponent$p(HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig) {
        return homeControlsKeyguardQuickAffordanceConfig.component;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$pickerName$2.invoke():java.lang.String] */
    public static final /* synthetic */ Context access$getContext$p(HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig) {
        return homeControlsKeyguardQuickAffordanceConfig.context;
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
        return ((Number) this.pickerIconResourceId$delegate.getValue()).intValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public String getPickerName() {
        return (String) this.pickerName$delegate.getValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public Object getPickerScreenState(Continuation<? super KeyguardQuickAffordanceConfig.PickerScreenState> continuation) {
        List<StructureInfo> favorites;
        if (this.component.isEnabled()) {
            ControlsListingController orElse = this.component.getControlsListingController().orElse(null);
            List<ControlsServiceInfo> currentServices = orElse != null ? orElse.getCurrentServices() : null;
            ControlsController orElse2 = this.component.getControlsController().orElse(null);
            boolean z = (orElse2 == null || (favorites = orElse2.getFavorites()) == null || !(favorites.isEmpty() ^ true)) ? false : true;
            List<ControlsServiceInfo> list = currentServices;
            return ((list == null || list.isEmpty()) || !z) ? new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(CollectionsKt__CollectionsKt.listOf(new String[]{this.context.getString(R$string.keyguard_affordance_enablement_dialog_home_instruction_1), this.context.getString(R$string.keyguard_affordance_enablement_dialog_home_instruction_2)}), null, null, 6, null) : KeyguardQuickAffordanceConfig.PickerScreenState.Default.INSTANCE;
        }
        return KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(new Intent(this.appContext, ControlsActivity.class).addFlags(335544320).putExtra("extra_animate", true), ((Boolean) this.component.getCanShowWhileLockedSetting().getValue()).booleanValue());
    }

    public final KeyguardQuickAffordanceConfig.LockScreenState state(boolean z, boolean z2, boolean z3, boolean z4, ControlsComponent.Visibility visibility, Integer num) {
        return (z && (z2 || z3) && z4 && num != null && visibility == ControlsComponent.Visibility.AVAILABLE) ? new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(num.intValue(), new ContentDescription.Resource(this.component.getTileTitleId())), null, 2, null) : KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
    }

    public final Flow<KeyguardQuickAffordanceConfig.LockScreenState> stateInternal(ControlsListingController controlsListingController) {
        return controlsListingController == null ? FlowKt.flowOf(KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE) : ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1(controlsListingController, this, null));
    }
}