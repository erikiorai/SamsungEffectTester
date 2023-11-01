package com.android.systemui.media.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MetadataAnimationHandler.class */
public class MetadataAnimationHandler extends AnimatorListenerAdapter {
    public final Animator enterAnimator;
    public final Animator exitAnimator;
    public Function0<Unit> postEnterUpdate;
    public Function0<Unit> postExitUpdate;
    public Object targetData;

    public MetadataAnimationHandler(Animator animator, Animator animator2) {
        this.exitAnimator = animator;
        this.enterAnimator = animator2;
        animator.addListener(this);
        animator2.addListener(this);
    }

    public final boolean isRunning() {
        return this.enterAnimator.isRunning() || this.exitAnimator.isRunning();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        if (animator == this.exitAnimator) {
            Function0<Unit> function0 = this.postExitUpdate;
            if (function0 != null) {
                function0.invoke();
            }
            this.postExitUpdate = null;
            this.enterAnimator.start();
        }
        if (animator == this.enterAnimator) {
            if (this.postExitUpdate != null) {
                this.exitAnimator.start();
                return;
            }
            Function0<Unit> function02 = this.postEnterUpdate;
            if (function02 != null) {
                function02.invoke();
            }
            this.postEnterUpdate = null;
        }
    }

    public final boolean setNext(Object obj, Function0<Unit> function0, Function0<Unit> function02) {
        if (Intrinsics.areEqual(obj, this.targetData)) {
            return false;
        }
        this.targetData = obj;
        this.postExitUpdate = function0;
        this.postEnterUpdate = function02;
        if (isRunning()) {
            return true;
        }
        this.exitAnimator.start();
        return true;
    }
}