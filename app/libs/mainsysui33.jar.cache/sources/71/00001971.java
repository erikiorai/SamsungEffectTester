package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/QrCodeScannerKeyguardQuickAffordanceConfig_Factory.class */
public final class QrCodeScannerKeyguardQuickAffordanceConfig_Factory implements Factory<QrCodeScannerKeyguardQuickAffordanceConfig> {
    public final Provider<Context> contextProvider;
    public final Provider<QRCodeScannerController> controllerProvider;

    public QrCodeScannerKeyguardQuickAffordanceConfig_Factory(Provider<Context> provider, Provider<QRCodeScannerController> provider2) {
        this.contextProvider = provider;
        this.controllerProvider = provider2;
    }

    public static QrCodeScannerKeyguardQuickAffordanceConfig_Factory create(Provider<Context> provider, Provider<QRCodeScannerController> provider2) {
        return new QrCodeScannerKeyguardQuickAffordanceConfig_Factory(provider, provider2);
    }

    public static QrCodeScannerKeyguardQuickAffordanceConfig newInstance(Context context, QRCodeScannerController qRCodeScannerController) {
        return new QrCodeScannerKeyguardQuickAffordanceConfig(context, qRCodeScannerController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QrCodeScannerKeyguardQuickAffordanceConfig m2958get() {
        return newInstance((Context) this.contextProvider.get(), (QRCodeScannerController) this.controllerProvider.get());
    }
}