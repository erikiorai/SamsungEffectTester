package com.android.systemui.media.taptotransfer.sender;

import android.os.VibrationEffect;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/TransferStatus.class */
public enum TransferStatus {
    NOT_STARTED(VibrationEffect.startComposition().addPrimitive(1, 1.0f, 0).compose()),
    IN_PROGRESS(VibrationEffect.startComposition().addPrimitive(4, 1.0f, 0).addPrimitive(1, 0.7f, 70).compose()),
    SUCCEEDED(null, 1, null),
    FAILED(VibrationEffect.get(1)),
    TOO_FAR(null, 1, null);
    
    private final VibrationEffect vibrationEffect;

    TransferStatus(VibrationEffect vibrationEffect) {
        this.vibrationEffect = vibrationEffect;
    }

    /* synthetic */ TransferStatus(VibrationEffect vibrationEffect, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : vibrationEffect);
    }

    public final VibrationEffect getVibrationEffect() {
        return this.vibrationEffect;
    }
}