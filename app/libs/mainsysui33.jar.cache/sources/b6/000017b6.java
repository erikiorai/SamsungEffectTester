package com.android.systemui.flags;

import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FeatureFlagsDebug$$ExternalSyntheticLambda3.class */
public final /* synthetic */ class FeatureFlagsDebug$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ FeatureFlagsDebug f$0;

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.flags.FeatureFlagsDebug.eraseFlag(int):void, com.android.systemui.flags.FeatureFlagsDebug.init():void, com.android.systemui.flags.FeatureFlagsDebug.setFlagValue(int, T, com.android.systemui.flags.FlagSerializer<T>):void] */
    public /* synthetic */ FeatureFlagsDebug$$ExternalSyntheticLambda3(FeatureFlagsDebug featureFlagsDebug) {
        this.f$0 = featureFlagsDebug;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.restartSystemUI(((Boolean) obj).booleanValue());
    }
}