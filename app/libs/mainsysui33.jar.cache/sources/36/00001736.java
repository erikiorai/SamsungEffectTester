package com.android.systemui.dreams.dagger;

import com.android.systemui.dreams.DreamOverlayNotificationCountProvider;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamModule_ProvidesDreamOverlayNotificationCountProviderFactory.class */
public final class DreamModule_ProvidesDreamOverlayNotificationCountProviderFactory implements Factory<Optional<DreamOverlayNotificationCountProvider>> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamModule_ProvidesDreamOverlayNotificationCountProviderFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final DreamModule_ProvidesDreamOverlayNotificationCountProviderFactory INSTANCE = new DreamModule_ProvidesDreamOverlayNotificationCountProviderFactory();
    }

    public static DreamModule_ProvidesDreamOverlayNotificationCountProviderFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Optional<DreamOverlayNotificationCountProvider> providesDreamOverlayNotificationCountProvider() {
        return (Optional) Preconditions.checkNotNullFromProvides(DreamModule.providesDreamOverlayNotificationCountProvider());
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<DreamOverlayNotificationCountProvider> get() {
        return providesDreamOverlayNotificationCountProvider();
    }
}