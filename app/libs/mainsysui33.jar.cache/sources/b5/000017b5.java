package com.android.systemui.flags;

import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FeatureFlagsDebug$$ExternalSyntheticLambda2.class */
public final /* synthetic */ class FeatureFlagsDebug$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ FeatureFlagsDebug f$0;

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.flags.FeatureFlagsDebug.eraseFlag(com.android.systemui.flags.Flag<T>):void, com.android.systemui.flags.FeatureFlagsDebug.setBooleanFlagInternal(com.android.systemui.flags.Flag<?>, boolean):void] */
    public /* synthetic */ FeatureFlagsDebug$$ExternalSyntheticLambda2(FeatureFlagsDebug featureFlagsDebug) {
        this.f$0 = featureFlagsDebug;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        FeatureFlagsDebug.$r8$lambda$oRJv8HsU_SAZHPiMp9rhpgVroro(this.f$0, ((Boolean) obj).booleanValue());
    }
}