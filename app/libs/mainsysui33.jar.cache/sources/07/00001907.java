package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.R$string;
import com.android.systemui.animation.Expandable;
import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import dagger.Lazy;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/CameraQuickAffordanceConfig.class */
public final class CameraQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final Lazy<CameraGestureHelper> cameraGestureHelper;
    public final Context context;

    public CameraQuickAffordanceConfig(Context context, Lazy<CameraGestureHelper> lazy) {
        this.context = context;
        this.cameraGestureHelper = lazy;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public String getKey() {
        return "camera";
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public Flow<KeyguardQuickAffordanceConfig.LockScreenState> getLockScreenState() {
        return FlowKt.flowOf(new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(17303161, new ContentDescription.Resource(R$string.accessibility_camera_button)), null, 2, null));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public int getPickerIconResourceId() {
        return 17303161;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public String getPickerName() {
        return this.context.getString(R$string.accessibility_camera_button);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public Object getPickerScreenState(Continuation<? super KeyguardQuickAffordanceConfig.PickerScreenState> continuation) {
        return KeyguardQuickAffordanceConfig.DefaultImpls.getPickerScreenState(this, continuation);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        ((CameraGestureHelper) this.cameraGestureHelper.get()).launchCamera(3);
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }
}