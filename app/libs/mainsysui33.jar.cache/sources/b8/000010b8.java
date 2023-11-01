package com.android.systemui.animation;

import android.view.View;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/Expandable.class */
public interface Expandable {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/Expandable$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public final Expandable fromView(final View view) {
            return new Expandable() { // from class: com.android.systemui.animation.Expandable$Companion$fromView$1
                @Override // com.android.systemui.animation.Expandable
                public ActivityLaunchAnimator.Controller activityLaunchController(Integer num) {
                    return ActivityLaunchAnimator.Controller.Companion.fromView(view, num);
                }

                @Override // com.android.systemui.animation.Expandable
                public DialogLaunchAnimator.Controller dialogLaunchController(DialogCuj dialogCuj) {
                    return DialogLaunchAnimator.Controller.Companion.fromView(view, dialogCuj);
                }
            };
        }
    }

    static /* synthetic */ ActivityLaunchAnimator.Controller activityLaunchController$default(Expandable expandable, Integer num, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                num = null;
            }
            return expandable.activityLaunchController(num);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: activityLaunchController");
    }

    static /* synthetic */ DialogLaunchAnimator.Controller dialogLaunchController$default(Expandable expandable, DialogCuj dialogCuj, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                dialogCuj = null;
            }
            return expandable.dialogLaunchController(dialogCuj);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: dialogLaunchController");
    }

    static Expandable fromView(View view) {
        return Companion.fromView(view);
    }

    ActivityLaunchAnimator.Controller activityLaunchController(Integer num);

    DialogLaunchAnimator.Controller dialogLaunchController(DialogCuj dialogCuj);
}