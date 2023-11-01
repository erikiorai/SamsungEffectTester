package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/QrCodeScannerKeyguardQuickAffordanceConfig.class */
public final class QrCodeScannerKeyguardQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final QRCodeScannerController controller;
    public final String pickerName;
    public final String key = "qr_code_scanner";
    public final int pickerIconResourceId = R$drawable.ic_qr_code_scanner;
    public final Flow<KeyguardQuickAffordanceConfig.LockScreenState> lockScreenState = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1(this, null));

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/QrCodeScannerKeyguardQuickAffordanceConfig$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public QrCodeScannerKeyguardQuickAffordanceConfig(Context context, QRCodeScannerController qRCodeScannerController) {
        this.context = context;
        this.controller = qRCodeScannerController;
        this.pickerName = context.getString(R$string.qr_code_scanner_title);
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

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public Object getPickerScreenState(Continuation<? super KeyguardQuickAffordanceConfig.PickerScreenState> continuation) {
        return !this.controller.isAvailableOnDevice() ? KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE : !this.controller.isAbleToOpenCameraApp() ? new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(CollectionsKt__CollectionsJVMKt.listOf(this.context.getString(R$string.keyguard_affordance_enablement_dialog_qr_scanner_instruction)), null, null, 6, null) : KeyguardQuickAffordanceConfig.PickerScreenState.Default.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(this.controller.getIntent(), true);
    }

    public final KeyguardQuickAffordanceConfig.LockScreenState state() {
        return this.controller.isEnabledForLockScreenButton() ? new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R$drawable.ic_qr_code_scanner, new ContentDescription.Resource(R$string.accessibility_qr_code_scanner_button)), null, 2, null) : KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
    }
}