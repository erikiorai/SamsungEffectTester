package com.android.systemui.screenshot;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotSmartActions_Factory.class */
public final class ScreenshotSmartActions_Factory implements Factory<ScreenshotSmartActions> {
    public final Provider<ScreenshotNotificationSmartActionsProvider> screenshotNotificationSmartActionsProvider;

    public ScreenshotSmartActions_Factory(Provider<ScreenshotNotificationSmartActionsProvider> provider) {
        this.screenshotNotificationSmartActionsProvider = provider;
    }

    public static ScreenshotSmartActions_Factory create(Provider<ScreenshotNotificationSmartActionsProvider> provider) {
        return new ScreenshotSmartActions_Factory(provider);
    }

    public static ScreenshotSmartActions newInstance(Provider<ScreenshotNotificationSmartActionsProvider> provider) {
        return new ScreenshotSmartActions(provider);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ScreenshotSmartActions m4298get() {
        return newInstance(this.screenshotNotificationSmartActionsProvider);
    }
}