package com.android.systemui.dagger;

import android.os.HandlerThread;
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
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/WMComponent.class */
public interface WMComponent {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/WMComponent$Builder.class */
    public interface Builder {
        WMComponent build();

        Builder setShellMainThread(HandlerThread handlerThread);
    }

    Optional<BackAnimation> getBackAnimation();

    Optional<Bubbles> getBubbles();

    Optional<DesktopMode> getDesktopMode();

    Optional<DisplayAreaHelper> getDisplayAreaHelper();

    Optional<OneHanded> getOneHanded();

    Optional<Pip> getPip();

    Optional<RecentTasks> getRecentTasks();

    ShellInterface getShell();

    Optional<SplitScreen> getSplitScreen();

    Optional<StartingSurface> getStartingSurface();

    Optional<TaskViewFactory> getTaskViewFactory();

    ShellTransitions getTransitions();

    default void init() {
        getShell().onInit();
    }
}