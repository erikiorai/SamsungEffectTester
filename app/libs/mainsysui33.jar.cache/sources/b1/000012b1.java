package com.android.systemui.classifier;

import android.view.accessibility.AccessibilityManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import java.util.Set;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/BrightLineFalsingManager_Factory.class */
public final class BrightLineFalsingManager_Factory implements Factory<BrightLineFalsingManager> {
    public final Provider<AccessibilityManager> accessibilityManagerProvider;
    public final Provider<Set<FalsingClassifier>> classifiersProvider;
    public final Provider<DoubleTapClassifier> doubleTapClassifierProvider;
    public final Provider<FalsingDataProvider> falsingDataProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<HistoryTracker> historyTrackerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<LongTapClassifier> longTapClassifierProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<SingleTapClassifier> singleTapClassifierProvider;
    public final Provider<Boolean> testHarnessProvider;

    public BrightLineFalsingManager_Factory(Provider<FalsingDataProvider> provider, Provider<MetricsLogger> provider2, Provider<Set<FalsingClassifier>> provider3, Provider<SingleTapClassifier> provider4, Provider<LongTapClassifier> provider5, Provider<DoubleTapClassifier> provider6, Provider<HistoryTracker> provider7, Provider<KeyguardStateController> provider8, Provider<AccessibilityManager> provider9, Provider<Boolean> provider10, Provider<FeatureFlags> provider11) {
        this.falsingDataProvider = provider;
        this.metricsLoggerProvider = provider2;
        this.classifiersProvider = provider3;
        this.singleTapClassifierProvider = provider4;
        this.longTapClassifierProvider = provider5;
        this.doubleTapClassifierProvider = provider6;
        this.historyTrackerProvider = provider7;
        this.keyguardStateControllerProvider = provider8;
        this.accessibilityManagerProvider = provider9;
        this.testHarnessProvider = provider10;
        this.featureFlagsProvider = provider11;
    }

    public static BrightLineFalsingManager_Factory create(Provider<FalsingDataProvider> provider, Provider<MetricsLogger> provider2, Provider<Set<FalsingClassifier>> provider3, Provider<SingleTapClassifier> provider4, Provider<LongTapClassifier> provider5, Provider<DoubleTapClassifier> provider6, Provider<HistoryTracker> provider7, Provider<KeyguardStateController> provider8, Provider<AccessibilityManager> provider9, Provider<Boolean> provider10, Provider<FeatureFlags> provider11) {
        return new BrightLineFalsingManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static BrightLineFalsingManager newInstance(FalsingDataProvider falsingDataProvider, MetricsLogger metricsLogger, Set<FalsingClassifier> set, SingleTapClassifier singleTapClassifier, LongTapClassifier longTapClassifier, DoubleTapClassifier doubleTapClassifier, HistoryTracker historyTracker, KeyguardStateController keyguardStateController, AccessibilityManager accessibilityManager, boolean z, FeatureFlags featureFlags) {
        return new BrightLineFalsingManager(falsingDataProvider, metricsLogger, set, singleTapClassifier, longTapClassifier, doubleTapClassifier, historyTracker, keyguardStateController, accessibilityManager, z, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BrightLineFalsingManager m1682get() {
        return newInstance((FalsingDataProvider) this.falsingDataProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (Set) this.classifiersProvider.get(), (SingleTapClassifier) this.singleTapClassifierProvider.get(), (LongTapClassifier) this.longTapClassifierProvider.get(), (DoubleTapClassifier) this.doubleTapClassifierProvider.get(), (HistoryTracker) this.historyTrackerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (AccessibilityManager) this.accessibilityManagerProvider.get(), ((Boolean) this.testHarnessProvider.get()).booleanValue(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}