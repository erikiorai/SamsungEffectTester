package com.android.systemui.dagger;

import com.android.systemui.BootCompleteCacheImpl;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.InitController;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.unfold.FoldStateLogger;
import com.android.systemui.unfold.FoldStateLoggingProvider;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.UnfoldLatencyTracker;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.wm.shell.TaskViewFactory;
import com.android.wm.shell.back.BackAnimation;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.displayareahelper.DisplayAreaHelper;
import com.android.wm.shell.onehanded.OneHanded;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.recents.RecentTasks;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.startingsurface.StartingSurface;
import com.android.wm.shell.sysui.ShellInterface;
import com.android.wm.shell.transition.ShellTransitions;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/SysUIComponent.class */
public interface SysUIComponent {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/SysUIComponent$Builder.class */
    public interface Builder {
        SysUIComponent build();

        Builder setBackAnimation(Optional<BackAnimation> optional);

        Builder setBubbles(Optional<Bubbles> optional);

        Builder setDesktopMode(Optional<DesktopMode> optional);

        Builder setDisplayAreaHelper(Optional<DisplayAreaHelper> optional);

        Builder setOneHanded(Optional<OneHanded> optional);

        Builder setPip(Optional<Pip> optional);

        Builder setRecentTasks(Optional<RecentTasks> optional);

        Builder setShell(ShellInterface shellInterface);

        Builder setSplitScreen(Optional<SplitScreen> optional);

        Builder setStartingSurface(Optional<StartingSurface> optional);

        Builder setTaskViewFactory(Optional<TaskViewFactory> optional);

        Builder setTransitions(ShellTransitions shellTransitions);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    static /* synthetic */ void $r8$lambda$7fysl9LIOFjab_eqkovVpB7LI_0(NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        naturalRotationUnfoldProgressProvider.init();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    static /* synthetic */ void $r8$lambda$B_0g7GNwCBKx72HamzYsVOe4rRg(SysUIUnfoldComponent sysUIUnfoldComponent) {
        lambda$init$0(sysUIUnfoldComponent);
    }

    static /* synthetic */ void lambda$init$0(SysUIUnfoldComponent sysUIUnfoldComponent) {
        sysUIUnfoldComponent.getUnfoldLightRevealOverlayAnimation().init();
        sysUIUnfoldComponent.getUnfoldTransitionWallpaperController().init();
        sysUIUnfoldComponent.getUnfoldHapticsPlayer();
    }

    Dependency createDependency();

    DumpManager createDumpManager();

    ConfigurationController getConfigurationController();

    Optional<FoldStateLogger> getFoldStateLogger();

    Optional<FoldStateLoggingProvider> getFoldStateLoggingProvider();

    InitController getInitController();

    Optional<MediaMuteAwaitConnectionCli> getMediaMuteAwaitConnectionCli();

    Optional<NaturalRotationUnfoldProgressProvider> getNaturalRotationUnfoldProgressProvider();

    Optional<NearbyMediaDevicesManager> getNearbyMediaDevicesManager();

    Map<Class<?>, Provider<CoreStartable>> getPerUserStartables();

    Map<Class<?>, Provider<CoreStartable>> getStartables();

    Optional<SysUIUnfoldComponent> getSysUIUnfoldComponent();

    UnfoldLatencyTracker getUnfoldLatencyTracker();

    default void init() {
        getSysUIUnfoldComponent().ifPresent(new Consumer() { // from class: com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SysUIComponent.$r8$lambda$B_0g7GNwCBKx72HamzYsVOe4rRg((SysUIUnfoldComponent) obj);
            }
        });
        getNaturalRotationUnfoldProgressProvider().ifPresent(new Consumer() { // from class: com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SysUIComponent.$r8$lambda$7fysl9LIOFjab_eqkovVpB7LI_0((NaturalRotationUnfoldProgressProvider) obj);
            }
        });
        getMediaMuteAwaitConnectionCli();
        getNearbyMediaDevicesManager();
        getUnfoldLatencyTracker().init();
        getFoldStateLoggingProvider().ifPresent(new Consumer() { // from class: com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FoldStateLoggingProvider) obj).init();
            }
        });
        getFoldStateLogger().ifPresent(new Consumer() { // from class: com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FoldStateLogger) obj).init();
            }
        });
    }

    void inject(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase);

    BootCompleteCacheImpl provideBootCacheImpl();
}