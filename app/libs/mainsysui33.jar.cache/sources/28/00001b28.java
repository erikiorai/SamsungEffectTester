package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.viewmodel.LightRevealScrimViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.LightRevealScrim;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/LightRevealScrimViewBinder.class */
public final class LightRevealScrimViewBinder {
    public static final LightRevealScrimViewBinder INSTANCE = new LightRevealScrimViewBinder();

    public static final void bind(LightRevealScrim lightRevealScrim, LightRevealScrimViewModel lightRevealScrimViewModel) {
        RepeatWhenAttachedKt.repeatWhenAttached$default(lightRevealScrim, null, new LightRevealScrimViewBinder$bind$1(lightRevealScrimViewModel, lightRevealScrim, null), 1, null);
    }
}