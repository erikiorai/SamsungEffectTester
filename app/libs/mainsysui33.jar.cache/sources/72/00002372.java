package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogFactory.class */
public final class InternetDialogFactory {
    public static final Companion Companion = new Companion(null);
    public static InternetDialog internetDialog;
    public final Context context;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final Executor executor;
    public final Handler handler;
    public final InternetDialogController internetDialogController;
    public final KeyguardStateController keyguardStateController;
    public final UiEventLogger uiEventLogger;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogFactory$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public InternetDialogFactory(Handler handler, Executor executor, InternetDialogController internetDialogController, Context context, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, KeyguardStateController keyguardStateController) {
        this.handler = handler;
        this.executor = executor;
        this.internetDialogController = internetDialogController;
        this.context = context;
        this.uiEventLogger = uiEventLogger;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.keyguardStateController = keyguardStateController;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v6, resolved type: com.android.systemui.animation.DialogLaunchAnimator */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.app.AlertDialog, com.android.systemui.qs.tiles.dialog.InternetDialog, java.lang.Object, android.app.Dialog] */
    public final void create(boolean z, boolean z2, boolean z3, View view) {
        boolean z4;
        if (internetDialog != null) {
            z4 = InternetDialogFactoryKt.DEBUG;
            if (z4) {
                Log.d("InternetDialogFactory", "InternetDialog is showing, do not create it twice.");
                return;
            }
            return;
        }
        ?? internetDialog2 = new InternetDialog(this.context, this, this.internetDialogController, z2, z3, z, this.uiEventLogger, this.dialogLaunchAnimator, this.handler, this.executor, this.keyguardStateController);
        internetDialog = internetDialog2;
        if (view == null) {
            internetDialog2.show();
            return;
        }
        DialogLaunchAnimator dialogLaunchAnimator = this.dialogLaunchAnimator;
        Intrinsics.checkNotNull((Object) internetDialog2);
        dialogLaunchAnimator.showFromView(internetDialog2, view, new DialogCuj(58, "internet"), true);
    }

    public final void destroyDialog() {
        boolean z;
        z = InternetDialogFactoryKt.DEBUG;
        if (z) {
            Log.d("InternetDialogFactory", "destroyDialog");
        }
        internetDialog = null;
    }
}