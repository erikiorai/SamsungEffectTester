package com.android.systemui.animation;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/DelegateLaunchAnimatorController.class */
public class DelegateLaunchAnimatorController implements ActivityLaunchAnimator.Controller {
    public final ActivityLaunchAnimator.Controller delegate;

    public DelegateLaunchAnimatorController(ActivityLaunchAnimator.Controller controller) {
        this.delegate = controller;
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public LaunchAnimator.State createAnimatorState() {
        return this.delegate.createAnimatorState();
    }

    public final ActivityLaunchAnimator.Controller getDelegate() {
        return this.delegate;
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public ViewGroup getLaunchContainer() {
        return this.delegate.getLaunchContainer();
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public View getOpeningWindowSyncView() {
        return this.delegate.getOpeningWindowSyncView();
    }

    @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
    public boolean isBelowAnimatingWindow() {
        return this.delegate.isBelowAnimatingWindow();
    }

    @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
    public boolean isDialogLaunch() {
        return this.delegate.isDialogLaunch();
    }

    @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
    public void onIntentStarted(boolean z) {
        this.delegate.onIntentStarted(z);
    }

    @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
    public void onLaunchAnimationCancelled(Boolean bool) {
        this.delegate.onLaunchAnimationCancelled(bool);
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
        this.delegate.onLaunchAnimationProgress(state, f, f2);
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public void onLaunchAnimationStart(boolean z) {
        this.delegate.onLaunchAnimationStart(z);
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public void setLaunchContainer(ViewGroup viewGroup) {
        this.delegate.setLaunchContainer(viewGroup);
    }
}