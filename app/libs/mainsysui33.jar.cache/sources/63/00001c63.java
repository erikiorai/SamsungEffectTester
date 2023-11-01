package com.android.systemui.media.controls.ui;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/AnimationBindHandler.class */
public final class AnimationBindHandler extends Animatable2.AnimationCallback {
    public Integer rebindId;
    public final List<Function0<Unit>> onAnimationsComplete = new ArrayList();
    public final List<Animatable2> registrations = new ArrayList();

    public final boolean isAnimationRunning() {
        boolean z;
        List<Animatable2> list = this.registrations;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                z = false;
                if (!it.hasNext()) {
                    break;
                } else if (((Animatable2) it.next()).isRunning()) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
        }
        return z;
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public void onAnimationEnd(Drawable drawable) {
        super.onAnimationEnd(drawable);
        if (isAnimationRunning()) {
            return;
        }
        Iterator<T> it = this.onAnimationsComplete.iterator();
        while (it.hasNext()) {
            ((Function0) it.next()).invoke();
        }
        this.onAnimationsComplete.clear();
    }

    public final void tryExecute(Function0<Unit> function0) {
        if (isAnimationRunning()) {
            this.onAnimationsComplete.add(function0);
        } else {
            function0.invoke();
        }
    }

    public final void tryRegister(Drawable drawable) {
        if (drawable instanceof Animatable2) {
            Animatable2 animatable2 = (Animatable2) drawable;
            animatable2.registerAnimationCallback(this);
            this.registrations.add(animatable2);
        }
    }

    public final void unregisterAll() {
        for (Animatable2 animatable2 : this.registrations) {
            animatable2.unregisterAnimationCallback(this);
        }
        this.registrations.clear();
    }

    public final boolean updateRebindId(Integer num) {
        Integer num2 = this.rebindId;
        if (num2 == null || num == null || !Intrinsics.areEqual(num2, num)) {
            this.rebindId = num;
            return true;
        }
        return false;
    }
}