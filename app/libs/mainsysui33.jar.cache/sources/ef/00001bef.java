package com.android.systemui.media.controls.models.player;

import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarViewModel_Factory.class */
public final class SeekBarViewModel_Factory implements Factory<SeekBarViewModel> {
    public final Provider<RepeatableExecutor> bgExecutorProvider;
    public final Provider<FalsingManager> falsingManagerProvider;

    public SeekBarViewModel_Factory(Provider<RepeatableExecutor> provider, Provider<FalsingManager> provider2) {
        this.bgExecutorProvider = provider;
        this.falsingManagerProvider = provider2;
    }

    public static SeekBarViewModel_Factory create(Provider<RepeatableExecutor> provider, Provider<FalsingManager> provider2) {
        return new SeekBarViewModel_Factory(provider, provider2);
    }

    public static SeekBarViewModel newInstance(RepeatableExecutor repeatableExecutor, FalsingManager falsingManager) {
        return new SeekBarViewModel(repeatableExecutor, falsingManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SeekBarViewModel m3178get() {
        return newInstance((RepeatableExecutor) this.bgExecutorProvider.get(), (FalsingManager) this.falsingManagerProvider.get());
    }
}