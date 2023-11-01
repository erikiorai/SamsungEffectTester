package com.android.systemui.media.dagger;

import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.MediaHost;
import com.android.systemui.media.controls.ui.MediaHostStatesManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dagger/MediaModule_ProvidesQSMediaHostFactory.class */
public final class MediaModule_ProvidesQSMediaHostFactory implements Factory<MediaHost> {
    public final Provider<MediaDataManager> dataManagerProvider;
    public final Provider<MediaHierarchyManager> hierarchyManagerProvider;
    public final Provider<MediaHost.MediaHostStateHolder> stateHolderProvider;
    public final Provider<MediaHostStatesManager> statesManagerProvider;

    public MediaModule_ProvidesQSMediaHostFactory(Provider<MediaHost.MediaHostStateHolder> provider, Provider<MediaHierarchyManager> provider2, Provider<MediaDataManager> provider3, Provider<MediaHostStatesManager> provider4) {
        this.stateHolderProvider = provider;
        this.hierarchyManagerProvider = provider2;
        this.dataManagerProvider = provider3;
        this.statesManagerProvider = provider4;
    }

    public static MediaModule_ProvidesQSMediaHostFactory create(Provider<MediaHost.MediaHostStateHolder> provider, Provider<MediaHierarchyManager> provider2, Provider<MediaDataManager> provider3, Provider<MediaHostStatesManager> provider4) {
        return new MediaModule_ProvidesQSMediaHostFactory(provider, provider2, provider3, provider4);
    }

    public static MediaHost providesQSMediaHost(MediaHost.MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager) {
        return (MediaHost) Preconditions.checkNotNullFromProvides(MediaModule.providesQSMediaHost(mediaHostStateHolder, mediaHierarchyManager, mediaDataManager, mediaHostStatesManager));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaHost m3294get() {
        return providesQSMediaHost((MediaHost.MediaHostStateHolder) this.stateHolderProvider.get(), (MediaHierarchyManager) this.hierarchyManagerProvider.get(), (MediaDataManager) this.dataManagerProvider.get(), (MediaHostStatesManager) this.statesManagerProvider.get());
    }
}