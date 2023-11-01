package com.android.systemui.animation;

import android.app.Dialog;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/DialogLaunchAnimator.class */
public final class DialogLaunchAnimator {
    @Deprecated
    public static final LaunchAnimator.Interpolators INTERPOLATORS;
    public final Callback callback;
    public final InteractionJankMonitor interactionJankMonitor;
    public final boolean isForTesting;
    public final LaunchAnimator launchAnimator;
    public final HashSet<AnimatedDialog> openedDialogs;
    public static final Companion Companion = new Companion(null);
    @Deprecated
    public static final LaunchAnimator.Timings TIMINGS = ActivityLaunchAnimator.TIMINGS;

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/DialogLaunchAnimator$Callback.class */
    public interface Callback {
        boolean isDreaming();

        boolean isShowingAlternateAuthOnUnlock();

        boolean isUnlocked();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/DialogLaunchAnimator$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/DialogLaunchAnimator$Controller.class */
    public interface Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* loaded from: mainsysui33.jar:com/android/systemui/animation/DialogLaunchAnimator$Controller$Companion.class */
        public static final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            public final Controller fromView(View view, DialogCuj dialogCuj) {
                if (view.getParent() instanceof ViewGroup) {
                    return new ViewDialogLaunchAnimatorController(view, dialogCuj);
                }
                Log.e("DialogLaunchAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
                return null;
            }
        }

        LaunchAnimator.Controller createExitController();

        LaunchAnimator.Controller createLaunchController();

        DialogCuj getCuj();

        Object getSourceIdentity();

        ViewRootImpl getViewRoot();

        InteractionJankMonitor.Configuration.Builder jankConfigurationBuilder();

        void onExitAnimationCancelled();

        boolean shouldAnimateExit();

        void startDrawingInOverlayOf(ViewGroup viewGroup);

        void stopDrawingInOverlay();
    }

    static {
        ActivityLaunchAnimator.Companion companion = ActivityLaunchAnimator.Companion;
        INTERPOLATORS = LaunchAnimator.Interpolators.copy$default(companion.getINTERPOLATORS(), null, companion.getINTERPOLATORS().getPositionInterpolator(), null, null, 13, null);
    }

    public DialogLaunchAnimator(Callback callback, InteractionJankMonitor interactionJankMonitor) {
        this(callback, interactionJankMonitor, null, false, 12, null);
    }

    public DialogLaunchAnimator(Callback callback, InteractionJankMonitor interactionJankMonitor, LaunchAnimator launchAnimator, boolean z) {
        this.callback = callback;
        this.interactionJankMonitor = interactionJankMonitor;
        this.launchAnimator = launchAnimator;
        this.isForTesting = z;
        this.openedDialogs = new HashSet<>();
    }

    public /* synthetic */ DialogLaunchAnimator(Callback callback, InteractionJankMonitor interactionJankMonitor, LaunchAnimator launchAnimator, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(callback, interactionJankMonitor, (i & 4) != 0 ? new LaunchAnimator(TIMINGS, INTERPOLATORS) : launchAnimator, (i & 8) != 0 ? false : z);
    }

    public static /* synthetic */ ActivityLaunchAnimator.Controller createActivityLaunchController$default(DialogLaunchAnimator dialogLaunchAnimator, View view, Integer num, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        return dialogLaunchAnimator.createActivityLaunchController(view, num);
    }

    public static /* synthetic */ void show$default(DialogLaunchAnimator dialogLaunchAnimator, Dialog dialog, Controller controller, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        dialogLaunchAnimator.show(dialog, controller, z);
    }

    public static /* synthetic */ void showFromDialog$default(DialogLaunchAnimator dialogLaunchAnimator, Dialog dialog, Dialog dialog2, DialogCuj dialogCuj, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            dialogCuj = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        dialogLaunchAnimator.showFromDialog(dialog, dialog2, dialogCuj, z);
    }

    public static /* synthetic */ void showFromView$default(DialogLaunchAnimator dialogLaunchAnimator, Dialog dialog, View view, DialogCuj dialogCuj, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            dialogCuj = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        dialogLaunchAnimator.showFromView(dialog, view, dialogCuj, z);
    }

    public final ActivityLaunchAnimator.Controller createActivityLaunchController(View view) {
        return createActivityLaunchController$default(this, view, null, 2, null);
    }

    public final ActivityLaunchAnimator.Controller createActivityLaunchController(View view, Integer num) {
        Object obj;
        Iterator<T> it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            if (Intrinsics.areEqual(((AnimatedDialog) next).getDialog().getWindow().getDecorView().getViewRootImpl(), view.getViewRootImpl())) {
                obj = next;
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        if (animatedDialog == null) {
            return null;
        }
        return createActivityLaunchController(animatedDialog, num);
    }

    public final ActivityLaunchAnimator.Controller createActivityLaunchController(final AnimatedDialog animatedDialog, Integer num) {
        ViewGroup dialogContentWithBackground;
        final ActivityLaunchAnimator.Controller fromView;
        animatedDialog.setExitAnimationDisabled(true);
        final Dialog dialog = animatedDialog.getDialog();
        if (dialog.isShowing()) {
            if ((!this.callback.isUnlocked() && !this.callback.isShowingAlternateAuthOnUnlock()) || (dialogContentWithBackground = animatedDialog.getDialogContentWithBackground()) == null || (fromView = ActivityLaunchAnimator.Controller.Companion.fromView(dialogContentWithBackground, num)) == null) {
                return null;
            }
            return new ActivityLaunchAnimator.Controller(dialog, animatedDialog) { // from class: com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1
                public final /* synthetic */ ActivityLaunchAnimator.Controller $$delegate_0;
                public final /* synthetic */ AnimatedDialog $animatedDialog;
                public final /* synthetic */ Dialog $dialog;
                public final boolean isDialogLaunch = true;

                {
                    this.$dialog = dialog;
                    this.$animatedDialog = animatedDialog;
                    this.$$delegate_0 = ActivityLaunchAnimator.Controller.this;
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public LaunchAnimator.State createAnimatorState() {
                    return this.$$delegate_0.createAnimatorState();
                }

                public final void disableDialogDismiss() {
                    this.$dialog.setDismissOverride(new Runnable() { // from class: com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1$disableDialogDismiss$1
                        @Override // java.lang.Runnable
                        public final void run() {
                        }
                    });
                }

                public final void enableDialogDismiss() {
                    Dialog dialog2 = this.$dialog;
                    final AnimatedDialog animatedDialog2 = this.$animatedDialog;
                    dialog2.setDismissOverride(new Runnable() { // from class: com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1$enableDialogDismiss$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AnimatedDialog.this.onDialogDismissed();
                        }
                    });
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public ViewGroup getLaunchContainer() {
                    return this.$$delegate_0.getLaunchContainer();
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public View getOpeningWindowSyncView() {
                    return this.$$delegate_0.getOpeningWindowSyncView();
                }

                @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                public boolean isBelowAnimatingWindow() {
                    return this.$$delegate_0.isBelowAnimatingWindow();
                }

                @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                public boolean isDialogLaunch() {
                    return this.isDialogLaunch;
                }

                @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                public void onIntentStarted(boolean z) {
                    ActivityLaunchAnimator.Controller.this.onIntentStarted(z);
                    if (z) {
                        return;
                    }
                    this.$dialog.dismiss();
                }

                @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                public void onLaunchAnimationCancelled(Boolean bool) {
                    ActivityLaunchAnimator.Controller.onLaunchAnimationCancelled$default(ActivityLaunchAnimator.Controller.this, null, 1, null);
                    enableDialogDismiss();
                    this.$dialog.dismiss();
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public void onLaunchAnimationEnd(boolean z) {
                    ActivityLaunchAnimator.Controller.this.onLaunchAnimationEnd(z);
                    this.$dialog.hide();
                    enableDialogDismiss();
                    this.$dialog.dismiss();
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
                    this.$$delegate_0.onLaunchAnimationProgress(state, f, f2);
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public void onLaunchAnimationStart(boolean z) {
                    ActivityLaunchAnimator.Controller.this.onLaunchAnimationStart(z);
                    disableDialogDismiss();
                    this.$animatedDialog.prepareForStackDismiss();
                    this.$dialog.getWindow().clearFlags(2);
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public void setLaunchContainer(ViewGroup viewGroup) {
                    this.$$delegate_0.setLaunchContainer(viewGroup);
                }
            };
        }
        return null;
    }

    public final void disableAllCurrentDialogsExitAnimations() {
        for (AnimatedDialog animatedDialog : this.openedDialogs) {
            animatedDialog.setExitAnimationDisabled(true);
        }
    }

    public final void dismissStack(Dialog dialog) {
        Object obj;
        Iterator<T> it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((AnimatedDialog) obj).getDialog(), dialog)) {
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        if (animatedDialog != null) {
            animatedDialog.prepareForStackDismiss();
        }
        dialog.dismiss();
    }

    public final void show(Dialog dialog, Controller controller) {
        show$default(this, dialog, controller, false, 4, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0077, code lost:
        if (r17 == null) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show(Dialog dialog, Controller controller, boolean z) {
        Object obj;
        Controller controller2;
        boolean z2;
        ViewGroup dialogContentWithBackground;
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            throw new IllegalStateException("showFromView must be called from the main thread and dialog must be created in the main thread");
        }
        Iterator<T> it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((AnimatedDialog) obj).getDialog().getWindow().getDecorView().getViewRootImpl(), controller.getViewRoot())) {
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        if (animatedDialog != null && (dialogContentWithBackground = animatedDialog.getDialogContentWithBackground()) != null) {
            controller2 = Controller.Companion.fromView(dialogContentWithBackground, controller.getCuj());
        }
        controller2 = controller;
        if (animatedDialog == null && !(controller2 instanceof LaunchableView)) {
            Log.w("DialogLaunchAnimator", "A dialog was launched from a View that does not implement LaunchableView. This can lead to subtle bugs where the visibility of the View we are launching from is not what we expected.");
        }
        HashSet<AnimatedDialog> hashSet = this.openedDialogs;
        if (!(hashSet instanceof Collection) || !hashSet.isEmpty()) {
            Iterator<T> it2 = hashSet.iterator();
            while (true) {
                z2 = false;
                if (!it2.hasNext()) {
                    break;
                } else if (Intrinsics.areEqual(((AnimatedDialog) it2.next()).getController().getSourceIdentity(), controller.getSourceIdentity())) {
                    z2 = true;
                    break;
                }
            }
        } else {
            z2 = false;
        }
        if (z2) {
            Log.e("DialogLaunchAnimator", "Not running dialog launch animation from source as it is already expanded into a dialog");
            dialog.show();
            return;
        }
        AnimatedDialog animatedDialog2 = new AnimatedDialog(this.launchAnimator, this.callback, this.interactionJankMonitor, controller2, new Function1<AnimatedDialog, Unit>() { // from class: com.android.systemui.animation.DialogLaunchAnimator$show$animatedDialog$1
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                invoke((AnimatedDialog) obj2);
                return Unit.INSTANCE;
            }

            public final void invoke(AnimatedDialog animatedDialog3) {
                HashSet hashSet2;
                hashSet2 = DialogLaunchAnimator.this.openedDialogs;
                hashSet2.remove(animatedDialog3);
            }
        }, dialog, z, animatedDialog, this.isForTesting);
        this.openedDialogs.add(animatedDialog2);
        animatedDialog2.start();
    }

    public final void showFromDialog(Dialog dialog, Dialog dialog2, DialogCuj dialogCuj, boolean z) {
        Object obj;
        Iterator<T> it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            if (Intrinsics.areEqual(((AnimatedDialog) next).getDialog(), dialog2)) {
                obj = next;
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        ViewGroup viewGroup = null;
        if (animatedDialog != null) {
            viewGroup = animatedDialog.getDialogContentWithBackground();
        }
        if (viewGroup != null) {
            showFromView(dialog, viewGroup, dialogCuj, z);
            return;
        }
        Log.w("DialogLaunchAnimator", "Showing dialog " + dialog + " normally as the dialog it is shown from was not shown using DialogLaunchAnimator");
        dialog.show();
    }

    public final void showFromView(Dialog dialog, View view) {
        showFromView$default(this, dialog, view, null, false, 12, null);
    }

    public final void showFromView(Dialog dialog, View view, DialogCuj dialogCuj) {
        showFromView$default(this, dialog, view, dialogCuj, false, 8, null);
    }

    public final void showFromView(Dialog dialog, View view, DialogCuj dialogCuj, boolean z) {
        Controller fromView = Controller.Companion.fromView(view, dialogCuj);
        if (fromView == null) {
            dialog.show();
        } else {
            show(dialog, fromView, z);
        }
    }
}