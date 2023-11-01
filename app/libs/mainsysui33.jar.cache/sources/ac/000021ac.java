package com.android.systemui.qs.dagger;

import com.android.systemui.qs.QuickStatusBarHeader;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvidesStatusIconContainerFactory.class */
public final class QSFragmentModule_ProvidesStatusIconContainerFactory implements Factory<StatusIconContainer> {
    public final Provider<QuickStatusBarHeader> qsHeaderProvider;

    public QSFragmentModule_ProvidesStatusIconContainerFactory(Provider<QuickStatusBarHeader> provider) {
        this.qsHeaderProvider = provider;
    }

    public static QSFragmentModule_ProvidesStatusIconContainerFactory create(Provider<QuickStatusBarHeader> provider) {
        return new QSFragmentModule_ProvidesStatusIconContainerFactory(provider);
    }

    public static StatusIconContainer providesStatusIconContainer(QuickStatusBarHeader quickStatusBarHeader) {
        return (StatusIconContainer) Preconditions.checkNotNullFromProvides(QSFragmentModule.providesStatusIconContainer(quickStatusBarHeader));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public StatusIconContainer m3888get() {
        return providesStatusIconContainer((QuickStatusBarHeader) this.qsHeaderProvider.get());
    }
}