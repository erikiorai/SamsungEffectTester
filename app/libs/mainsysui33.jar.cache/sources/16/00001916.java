package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.statusbar.policy.FlashlightController;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/FlashlightQuickAffordanceConfig.class */
public final class FlashlightQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final FlashlightController flashlightController;
    public final Flow<KeyguardQuickAffordanceConfig.LockScreenState> lockScreenState = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new FlashlightQuickAffordanceConfig$lockScreenState$1(this, null));

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/FlashlightQuickAffordanceConfig$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/FlashlightQuickAffordanceConfig$FlashlightState.class */
    public static abstract class FlashlightState {

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/FlashlightQuickAffordanceConfig$FlashlightState$OffAvailable.class */
        public static final class OffAvailable extends FlashlightState {
            public static final OffAvailable INSTANCE = new OffAvailable();

            public OffAvailable() {
                super(null);
            }

            public KeyguardQuickAffordanceConfig.LockScreenState toLockScreenState() {
                return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R$drawable.qs_flashlight_icon_off, new ContentDescription.Resource(R$string.quick_settings_flashlight_label)), ActivationState.Inactive.INSTANCE);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/FlashlightQuickAffordanceConfig$FlashlightState$On.class */
        public static final class On extends FlashlightState {
            public static final On INSTANCE = new On();

            public On() {
                super(null);
            }

            public KeyguardQuickAffordanceConfig.LockScreenState toLockScreenState() {
                return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R$drawable.qs_flashlight_icon_on, new ContentDescription.Resource(R$string.quick_settings_flashlight_label)), ActivationState.Active.INSTANCE);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/FlashlightQuickAffordanceConfig$FlashlightState$Unavailable.class */
        public static final class Unavailable extends FlashlightState {
            public static final Unavailable INSTANCE = new Unavailable();

            public Unavailable() {
                super(null);
            }

            public KeyguardQuickAffordanceConfig.LockScreenState toLockScreenState() {
                return KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
            }
        }

        public FlashlightState() {
        }

        public /* synthetic */ FlashlightState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public FlashlightQuickAffordanceConfig(Context context, FlashlightController flashlightController) {
        this.context = context;
        this.flashlightController = flashlightController;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public String getKey() {
        return "flashlight";
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public Flow<KeyguardQuickAffordanceConfig.LockScreenState> getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public int getPickerIconResourceId() {
        return R$drawable.ic_flashlight_off;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public String getPickerName() {
        return this.context.getString(R$string.quick_settings_flashlight_label);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public Object getPickerScreenState(Continuation<? super KeyguardQuickAffordanceConfig.PickerScreenState> continuation) {
        return this.flashlightController.isAvailable() ? KeyguardQuickAffordanceConfig.PickerScreenState.Default.INSTANCE : KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        FlashlightController flashlightController = this.flashlightController;
        flashlightController.setFlashlight(flashlightController.isAvailable() && !this.flashlightController.isEnabled());
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }
}