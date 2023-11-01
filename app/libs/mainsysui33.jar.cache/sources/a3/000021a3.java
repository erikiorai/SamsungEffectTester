package com.android.systemui.qs.dagger;

import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.qs.QuickStatusBarHeader;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvidesPrivacyChipFactory.class */
public final class QSFragmentModule_ProvidesPrivacyChipFactory implements Factory<OngoingPrivacyChip> {
    public final Provider<QuickStatusBarHeader> qsHeaderProvider;

    public QSFragmentModule_ProvidesPrivacyChipFactory(Provider<QuickStatusBarHeader> provider) {
        this.qsHeaderProvider = provider;
    }

    public static QSFragmentModule_ProvidesPrivacyChipFactory create(Provider<QuickStatusBarHeader> provider) {
        return new QSFragmentModule_ProvidesPrivacyChipFactory(provider);
    }

    public static OngoingPrivacyChip providesPrivacyChip(QuickStatusBarHeader quickStatusBarHeader) {
        return (OngoingPrivacyChip) Preconditions.checkNotNullFromProvides(QSFragmentModule.providesPrivacyChip(quickStatusBarHeader));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public OngoingPrivacyChip m3879get() {
        return providesPrivacyChip((QuickStatusBarHeader) this.qsHeaderProvider.get());
    }
}