package com.android.systemui.screenshot;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/SmartActionsReceiver_Factory.class */
public final class SmartActionsReceiver_Factory implements Factory<SmartActionsReceiver> {
    public final Provider<ScreenshotSmartActions> screenshotSmartActionsProvider;

    public SmartActionsReceiver_Factory(Provider<ScreenshotSmartActions> provider) {
        this.screenshotSmartActionsProvider = provider;
    }

    public static SmartActionsReceiver_Factory create(Provider<ScreenshotSmartActions> provider) {
        return new SmartActionsReceiver_Factory(provider);
    }

    public static SmartActionsReceiver newInstance(ScreenshotSmartActions screenshotSmartActions) {
        return new SmartActionsReceiver(screenshotSmartActions);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SmartActionsReceiver m4329get() {
        return newInstance((ScreenshotSmartActions) this.screenshotSmartActionsProvider.get());
    }
}