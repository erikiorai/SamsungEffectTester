package com.android.systemui.screenshot;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory.class */
public final class ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory implements Factory<ScreenshotNotificationSmartActionsProvider> {

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory INSTANCE = new ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory();
    }

    public static ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ScreenshotNotificationSmartActionsProvider providesScrnshtNotifSmartActionsProvider() {
        return (ScreenshotNotificationSmartActionsProvider) Preconditions.checkNotNullFromProvides(ReferenceScreenshotModule.providesScrnshtNotifSmartActionsProvider());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ScreenshotNotificationSmartActionsProvider m4251get() {
        return providesScrnshtNotifSmartActionsProvider();
    }
}