package com.android.systemui.animation;

import android.view.GhostView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewDialogLaunchAnimatorController.class */
public final class ViewDialogLaunchAnimatorController implements DialogLaunchAnimator.Controller {
    public final DialogCuj cuj;
    public final View source;
    public final Object sourceIdentity;

    public ViewDialogLaunchAnimatorController(View view, DialogCuj dialogCuj) {
        this.source = view;
        this.cuj = dialogCuj;
        this.sourceIdentity = view;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ViewDialogLaunchAnimatorController$createLaunchController$1.onLaunchAnimationEnd(boolean):void, com.android.systemui.animation.ViewDialogLaunchAnimatorController$createLaunchController$1.onLaunchAnimationStart(boolean):void] */
    public static final /* synthetic */ View access$getSource$p(ViewDialogLaunchAnimatorController viewDialogLaunchAnimatorController) {
        return viewDialogLaunchAnimatorController.source;
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public LaunchAnimator.Controller createExitController() {
        return new GhostedViewLaunchAnimatorController(this.source, null, null, 6, null);
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public LaunchAnimator.Controller createLaunchController() {
        return new LaunchAnimator.Controller(this) { // from class: com.android.systemui.animation.ViewDialogLaunchAnimatorController$createLaunchController$1
            public final /* synthetic */ GhostedViewLaunchAnimatorController $$delegate_0;
            public final /* synthetic */ ViewDialogLaunchAnimatorController this$0;

            {
                this.this$0 = this;
                this.$$delegate_0 = GhostedViewLaunchAnimatorController.this;
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public LaunchAnimator.State createAnimatorState() {
                return this.$$delegate_0.createAnimatorState();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public ViewGroup getLaunchContainer() {
                return this.$$delegate_0.getLaunchContainer();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public View getOpeningWindowSyncView() {
                return this.$$delegate_0.getOpeningWindowSyncView();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void onLaunchAnimationEnd(boolean z) {
                GhostedViewLaunchAnimatorController.this.onLaunchAnimationEnd(z);
                if (!(ViewDialogLaunchAnimatorController.access$getSource$p(this.this$0) instanceof LaunchableView)) {
                    ViewDialogLaunchAnimatorController.access$getSource$p(this.this$0).setVisibility(4);
                    return;
                }
                ((LaunchableView) ViewDialogLaunchAnimatorController.access$getSource$p(this.this$0)).setShouldBlockVisibilityChanges(true);
                ViewDialogLaunchAnimatorController.access$getSource$p(this.this$0).setTransitionVisibility(4);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
                this.$$delegate_0.onLaunchAnimationProgress(state, f, f2);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void onLaunchAnimationStart(boolean z) {
                GhostView.removeGhost(ViewDialogLaunchAnimatorController.access$getSource$p(this.this$0));
                GhostedViewLaunchAnimatorController.this.onLaunchAnimationStart(z);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void setLaunchContainer(ViewGroup viewGroup) {
                this.$$delegate_0.setLaunchContainer(viewGroup);
            }
        };
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public DialogCuj getCuj() {
        return this.cuj;
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public Object getSourceIdentity() {
        return this.sourceIdentity;
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public ViewRootImpl getViewRoot() {
        return this.source.getViewRootImpl();
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public InteractionJankMonitor.Configuration.Builder jankConfigurationBuilder() {
        DialogCuj cuj = getCuj();
        if (cuj != null) {
            return InteractionJankMonitor.Configuration.Builder.withView(cuj.getCujType(), this.source);
        }
        return null;
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public void onExitAnimationCancelled() {
        View view = this.source;
        if (view instanceof LaunchableView) {
            ((LaunchableView) view).setShouldBlockVisibilityChanges(false);
        } else if (view.getVisibility() == 4) {
            this.source.setVisibility(0);
        }
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public boolean shouldAnimateExit() {
        if (this.source.getVisibility() != 4) {
            return false;
        }
        boolean z = false;
        if (this.source.isAttachedToWindow()) {
            ViewParent parent = this.source.getParent();
            View view = parent instanceof View ? (View) parent : null;
            z = false;
            if (view != null ? view.isShown() : true) {
                z = true;
            }
        }
        return z;
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public void startDrawingInOverlayOf(ViewGroup viewGroup) {
        View view = this.source;
        LaunchableView launchableView = view instanceof LaunchableView ? (LaunchableView) view : null;
        if (launchableView != null) {
            launchableView.setShouldBlockVisibilityChanges(true);
        }
        GhostView.addGhost(this.source, viewGroup);
    }

    @Override // com.android.systemui.animation.DialogLaunchAnimator.Controller
    public void stopDrawingInOverlay() {
        View view = this.source;
        if (view instanceof LaunchableView) {
            ((LaunchableView) view).setShouldBlockVisibilityChanges(false);
        } else {
            view.setVisibility(0);
        }
    }
}