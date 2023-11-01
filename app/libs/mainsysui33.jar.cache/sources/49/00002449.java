package com.android.systemui.screenshot;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/LongScreenshotActivity_Factory.class */
public final class LongScreenshotActivity_Factory implements Factory<LongScreenshotActivity> {
    public final Provider<ActionIntentExecutor> actionExecutorProvider;
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<ImageExporter> imageExporterProvider;
    public final Provider<LongScreenshotData> longScreenshotHolderProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public LongScreenshotActivity_Factory(Provider<UiEventLogger> provider, Provider<ImageExporter> provider2, Provider<Executor> provider3, Provider<Executor> provider4, Provider<LongScreenshotData> provider5, Provider<ActionIntentExecutor> provider6, Provider<FeatureFlags> provider7) {
        this.uiEventLoggerProvider = provider;
        this.imageExporterProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.bgExecutorProvider = provider4;
        this.longScreenshotHolderProvider = provider5;
        this.actionExecutorProvider = provider6;
        this.featureFlagsProvider = provider7;
    }

    public static LongScreenshotActivity_Factory create(Provider<UiEventLogger> provider, Provider<ImageExporter> provider2, Provider<Executor> provider3, Provider<Executor> provider4, Provider<LongScreenshotData> provider5, Provider<ActionIntentExecutor> provider6, Provider<FeatureFlags> provider7) {
        return new LongScreenshotActivity_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static LongScreenshotActivity newInstance(UiEventLogger uiEventLogger, Object obj, Executor executor, Executor executor2, LongScreenshotData longScreenshotData, ActionIntentExecutor actionIntentExecutor, FeatureFlags featureFlags) {
        return new LongScreenshotActivity(uiEventLogger, (ImageExporter) obj, executor, executor2, longScreenshotData, actionIntentExecutor, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LongScreenshotActivity m4246get() {
        return newInstance((UiEventLogger) this.uiEventLoggerProvider.get(), this.imageExporterProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.bgExecutorProvider.get(), (LongScreenshotData) this.longScreenshotHolderProvider.get(), (ActionIntentExecutor) this.actionExecutorProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}