package com.android.keyguard.mediator;

import android.os.Handler;
import android.os.Trace;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation;
import com.android.systemui.util.concurrency.PendingTasksContainer;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: mainsysui33.jar:com/android/keyguard/mediator/ScreenOnCoordinator.class */
public final class ScreenOnCoordinator {
    public final FoldAodAnimationController foldAodAnimationController;
    public final Handler mainHandler;
    public final PendingTasksContainer pendingTasks = new PendingTasksContainer();
    public final UnfoldLightRevealOverlayAnimation unfoldLightRevealAnimation;

    public ScreenOnCoordinator(Optional<SysUIUnfoldComponent> optional, Handler handler) {
        this.mainHandler = handler;
        this.unfoldLightRevealAnimation = (UnfoldLightRevealOverlayAnimation) optional.map(new Function() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$unfoldLightRevealAnimation$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.Function
            public final UnfoldLightRevealOverlayAnimation apply(SysUIUnfoldComponent sysUIUnfoldComponent) {
                return sysUIUnfoldComponent.getUnfoldLightRevealOverlayAnimation();
            }
        }).orElse(null);
        this.foldAodAnimationController = (FoldAodAnimationController) optional.map(new Function() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$foldAodAnimationController$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.Function
            public final FoldAodAnimationController apply(SysUIUnfoldComponent sysUIUnfoldComponent) {
                return sysUIUnfoldComponent.getFoldAodAnimationController();
            }
        }).orElse(null);
    }

    public final void onScreenTurnedOn() {
        FoldAodAnimationController foldAodAnimationController = this.foldAodAnimationController;
        if (foldAodAnimationController != null) {
            foldAodAnimationController.onScreenTurnedOn();
        }
        this.pendingTasks.reset();
    }

    public final void onScreenTurningOn(final Runnable runnable) {
        Trace.beginSection("ScreenOnCoordinator#onScreenTurningOn");
        this.pendingTasks.reset();
        UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = this.unfoldLightRevealAnimation;
        if (unfoldLightRevealOverlayAnimation != null) {
            unfoldLightRevealOverlayAnimation.onScreenTurningOn(this.pendingTasks.registerTask("unfold-reveal"));
        }
        FoldAodAnimationController foldAodAnimationController = this.foldAodAnimationController;
        if (foldAodAnimationController != null) {
            foldAodAnimationController.onScreenTurningOn(this.pendingTasks.registerTask("fold-to-aod"));
        }
        this.pendingTasks.onTasksComplete(new Runnable() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$onScreenTurningOn$1
            @Override // java.lang.Runnable
            public final void run() {
                Handler handler;
                handler = ScreenOnCoordinator.this.mainHandler;
                final Runnable runnable2 = runnable;
                handler.post(new Runnable() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$onScreenTurningOn$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        runnable2.run();
                    }
                });
            }
        });
        Trace.endSection();
    }
}