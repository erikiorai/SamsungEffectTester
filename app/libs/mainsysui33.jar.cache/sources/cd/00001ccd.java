package com.android.systemui.media.controls.ui;

import android.content.Context;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.bluetooth.BroadcastDialogController;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.media.controls.models.player.SeekBarViewModel;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.time.SystemClock;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaControlPanel_Factory.class */
public final class MediaControlPanel_Factory implements Factory<MediaControlPanel> {
    public final Provider<ActivityIntentHelper> activityIntentHelperProvider;
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<BroadcastDialogController> broadcastDialogControllerProvider;
    public final Provider<BroadcastSender> broadcastSenderProvider;
    public final Provider<Context> contextProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<NotificationLockscreenUserManager> lockscreenUserManagerProvider;
    public final Provider<MediaUiEventLogger> loggerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<MediaCarouselController> mediaCarouselControllerProvider;
    public final Provider<MediaDataManager> mediaDataManagerProvider;
    public final Provider<MediaOutputDialogFactory> mediaOutputDialogFactoryProvider;
    public final Provider<MediaViewController> mediaViewControllerProvider;
    public final Provider<SeekBarViewModel> seekBarViewModelProvider;
    public final Provider<SystemClock> systemClockProvider;

    public MediaControlPanel_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<ActivityStarter> provider4, Provider<BroadcastSender> provider5, Provider<MediaViewController> provider6, Provider<SeekBarViewModel> provider7, Provider<MediaDataManager> provider8, Provider<MediaOutputDialogFactory> provider9, Provider<MediaCarouselController> provider10, Provider<FalsingManager> provider11, Provider<SystemClock> provider12, Provider<MediaUiEventLogger> provider13, Provider<KeyguardStateController> provider14, Provider<ActivityIntentHelper> provider15, Provider<NotificationLockscreenUserManager> provider16, Provider<BroadcastDialogController> provider17, Provider<FeatureFlags> provider18) {
        this.contextProvider = provider;
        this.backgroundExecutorProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.activityStarterProvider = provider4;
        this.broadcastSenderProvider = provider5;
        this.mediaViewControllerProvider = provider6;
        this.seekBarViewModelProvider = provider7;
        this.mediaDataManagerProvider = provider8;
        this.mediaOutputDialogFactoryProvider = provider9;
        this.mediaCarouselControllerProvider = provider10;
        this.falsingManagerProvider = provider11;
        this.systemClockProvider = provider12;
        this.loggerProvider = provider13;
        this.keyguardStateControllerProvider = provider14;
        this.activityIntentHelperProvider = provider15;
        this.lockscreenUserManagerProvider = provider16;
        this.broadcastDialogControllerProvider = provider17;
        this.featureFlagsProvider = provider18;
    }

    public static MediaControlPanel_Factory create(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<ActivityStarter> provider4, Provider<BroadcastSender> provider5, Provider<MediaViewController> provider6, Provider<SeekBarViewModel> provider7, Provider<MediaDataManager> provider8, Provider<MediaOutputDialogFactory> provider9, Provider<MediaCarouselController> provider10, Provider<FalsingManager> provider11, Provider<SystemClock> provider12, Provider<MediaUiEventLogger> provider13, Provider<KeyguardStateController> provider14, Provider<ActivityIntentHelper> provider15, Provider<NotificationLockscreenUserManager> provider16, Provider<BroadcastDialogController> provider17, Provider<FeatureFlags> provider18) {
        return new MediaControlPanel_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18);
    }

    public static MediaControlPanel newInstance(Context context, Executor executor, Executor executor2, ActivityStarter activityStarter, BroadcastSender broadcastSender, MediaViewController mediaViewController, SeekBarViewModel seekBarViewModel, Lazy<MediaDataManager> lazy, MediaOutputDialogFactory mediaOutputDialogFactory, MediaCarouselController mediaCarouselController, FalsingManager falsingManager, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, KeyguardStateController keyguardStateController, ActivityIntentHelper activityIntentHelper, NotificationLockscreenUserManager notificationLockscreenUserManager, BroadcastDialogController broadcastDialogController, FeatureFlags featureFlags) {
        return new MediaControlPanel(context, executor, executor2, activityStarter, broadcastSender, mediaViewController, seekBarViewModel, lazy, mediaOutputDialogFactory, mediaCarouselController, falsingManager, systemClock, mediaUiEventLogger, keyguardStateController, activityIntentHelper, notificationLockscreenUserManager, broadcastDialogController, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaControlPanel m3263get() {
        return newInstance((Context) this.contextProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (Executor) this.mainExecutorProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get(), (MediaViewController) this.mediaViewControllerProvider.get(), (SeekBarViewModel) this.seekBarViewModelProvider.get(), DoubleCheck.lazy(this.mediaDataManagerProvider), (MediaOutputDialogFactory) this.mediaOutputDialogFactoryProvider.get(), (MediaCarouselController) this.mediaCarouselControllerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (SystemClock) this.systemClockProvider.get(), (MediaUiEventLogger) this.loggerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (ActivityIntentHelper) this.activityIntentHelperProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (BroadcastDialogController) this.broadcastDialogControllerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}