package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.internal.util.Preconditions;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$integer;
import com.android.systemui.R$layout;
import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.dreams.DreamOverlayStatusBarView;
import com.android.systemui.touch.TouchInsetManager;
import dagger.Lazy;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule.class */
public abstract class DreamOverlayModule {
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.dagger.DreamOverlayModule$$ExternalSyntheticLambda0.getLifecycle():androidx.lifecycle.Lifecycle] */
    public static /* synthetic */ Lifecycle $r8$lambda$np9T_0OzQjEHpaAbTNqPwrPfasU(Lazy lazy) {
        return lambda$providesLifecycleOwner$0(lazy);
    }

    public static /* synthetic */ Lifecycle lambda$providesLifecycleOwner$0(Lazy lazy) {
        return (Lifecycle) lazy.get();
    }

    public static long providesBurnInProtectionUpdateInterval(Resources resources) {
        return resources.getInteger(R$integer.config_dreamOverlayBurnInProtectionUpdateIntervalMillis);
    }

    public static int providesDreamBlurRadius(Resources resources) {
        return resources.getDimensionPixelSize(R$dimen.dream_overlay_anim_blur_radius);
    }

    public static long providesDreamInBlurAnimationDuration(Resources resources) {
        return resources.getInteger(R$integer.config_dreamOverlayInBlurDurationMs);
    }

    public static long providesDreamInComplicationsAnimationDuration(Resources resources) {
        return resources.getInteger(R$integer.config_dreamOverlayInComplicationsDurationMs);
    }

    public static int providesDreamInComplicationsTranslationY(Resources resources) {
        return resources.getDimensionPixelSize(R$dimen.dream_overlay_entry_y_offset);
    }

    public static long providesDreamInComplicationsTranslationYDuration(Resources resources) {
        return resources.getInteger(R$integer.config_dreamOverlayInTranslationYDurationMs);
    }

    public static DreamOverlayContainerView providesDreamOverlayContainerView(LayoutInflater layoutInflater) {
        return (DreamOverlayContainerView) Preconditions.checkNotNull((DreamOverlayContainerView) layoutInflater.inflate(R$layout.dream_overlay_container, (ViewGroup) null), "R.layout.dream_layout_container could not be properly inflated");
    }

    public static ViewGroup providesDreamOverlayContentView(DreamOverlayContainerView dreamOverlayContainerView) {
        return (ViewGroup) Preconditions.checkNotNull((ViewGroup) dreamOverlayContainerView.findViewById(R$id.dream_overlay_content), "R.id.dream_overlay_content must not be null");
    }

    public static DreamOverlayStatusBarView providesDreamOverlayStatusBarView(DreamOverlayContainerView dreamOverlayContainerView) {
        return (DreamOverlayStatusBarView) Preconditions.checkNotNull((DreamOverlayStatusBarView) dreamOverlayContainerView.findViewById(R$id.dream_overlay_status_bar), "R.id.status_bar must not be null");
    }

    public static Lifecycle providesLifecycle(LifecycleOwner lifecycleOwner) {
        return lifecycleOwner.getLifecycle();
    }

    public static LifecycleOwner providesLifecycleOwner(final Lazy<LifecycleRegistry> lazy) {
        return new LifecycleOwner() { // from class: com.android.systemui.dreams.dagger.DreamOverlayModule$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.LifecycleOwner
            public final Lifecycle getLifecycle() {
                return DreamOverlayModule.$r8$lambda$np9T_0OzQjEHpaAbTNqPwrPfasU(lazy);
            }
        };
    }

    public static LifecycleRegistry providesLifecycleRegistry(LifecycleOwner lifecycleOwner) {
        return new LifecycleRegistry(lifecycleOwner);
    }

    public static int providesMaxBurnInOffset(Resources resources) {
        return resources.getDimensionPixelSize(R$dimen.default_burn_in_prevention_offset);
    }

    public static long providesMillisUntilFullJitter(Resources resources) {
        return resources.getInteger(R$integer.config_dreamOverlayMillisUntilFullJitter);
    }

    public static TouchInsetManager providesTouchInsetManager(Executor executor, DreamOverlayContainerView dreamOverlayContainerView) {
        return new TouchInsetManager(executor, dreamOverlayContainerView);
    }

    public static TouchInsetManager.TouchInsetSession providesTouchInsetSession(TouchInsetManager touchInsetManager) {
        return touchInsetManager.createSession();
    }
}