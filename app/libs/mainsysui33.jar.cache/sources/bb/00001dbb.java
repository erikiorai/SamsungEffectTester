package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.media.taptotransfer.common.MediaTttLogger;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.temporarydisplay.chipbar.ChipbarInfo;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/MediaTttSenderCoordinator_Factory.class */
public final class MediaTttSenderCoordinator_Factory implements Factory<MediaTttSenderCoordinator> {
    public final Provider<ChipbarCoordinator> chipbarCoordinatorProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<MediaTttLogger<ChipbarInfo>> loggerProvider;
    public final Provider<MediaTttFlags> mediaTttFlagsProvider;
    public final Provider<MediaTttSenderUiEventLogger> uiEventLoggerProvider;

    public MediaTttSenderCoordinator_Factory(Provider<ChipbarCoordinator> provider, Provider<CommandQueue> provider2, Provider<Context> provider3, Provider<MediaTttLogger<ChipbarInfo>> provider4, Provider<MediaTttFlags> provider5, Provider<MediaTttSenderUiEventLogger> provider6) {
        this.chipbarCoordinatorProvider = provider;
        this.commandQueueProvider = provider2;
        this.contextProvider = provider3;
        this.loggerProvider = provider4;
        this.mediaTttFlagsProvider = provider5;
        this.uiEventLoggerProvider = provider6;
    }

    public static MediaTttSenderCoordinator_Factory create(Provider<ChipbarCoordinator> provider, Provider<CommandQueue> provider2, Provider<Context> provider3, Provider<MediaTttLogger<ChipbarInfo>> provider4, Provider<MediaTttFlags> provider5, Provider<MediaTttSenderUiEventLogger> provider6) {
        return new MediaTttSenderCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static MediaTttSenderCoordinator newInstance(ChipbarCoordinator chipbarCoordinator, CommandQueue commandQueue, Context context, MediaTttLogger<ChipbarInfo> mediaTttLogger, MediaTttFlags mediaTttFlags, MediaTttSenderUiEventLogger mediaTttSenderUiEventLogger) {
        return new MediaTttSenderCoordinator(chipbarCoordinator, commandQueue, context, mediaTttLogger, mediaTttFlags, mediaTttSenderUiEventLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaTttSenderCoordinator m3360get() {
        return newInstance((ChipbarCoordinator) this.chipbarCoordinatorProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (Context) this.contextProvider.get(), (MediaTttLogger) this.loggerProvider.get(), (MediaTttFlags) this.mediaTttFlagsProvider.get(), (MediaTttSenderUiEventLogger) this.uiEventLoggerProvider.get());
    }
}