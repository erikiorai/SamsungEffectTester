package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.UdfpsAnimationView;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsAnimationViewController.class */
public abstract class UdfpsAnimationViewController<T extends UdfpsAnimationView> extends ViewController<T> implements Dumpable {
    public ValueAnimator dialogAlphaAnimator;
    public final SystemUIDialogManager.Listener dialogListener;
    public final SystemUIDialogManager dialogManager;
    public final DumpManager dumpManager;
    public final String dumpTag;
    public boolean notificationShadeVisible;
    public final int paddingX;
    public final int paddingY;
    public final ShadeExpansionListener shadeExpansionListener;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final StatusBarStateController statusBarStateController;
    public final PointF touchTranslation;

    public UdfpsAnimationViewController(final T t, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager) {
        super(t);
        this.statusBarStateController = statusBarStateController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.dialogManager = systemUIDialogManager;
        this.dumpManager = dumpManager;
        this.dialogListener = new SystemUIDialogManager.Listener(this) { // from class: com.android.systemui.biometrics.UdfpsAnimationViewController$dialogListener$1
            public final /* synthetic */ UdfpsAnimationViewController<T> this$0;

            {
                this.this$0 = this;
            }

            public final void shouldHideAffordances(boolean z) {
                this.this$0.runDialogAlphaAnimator();
            }
        };
        this.shadeExpansionListener = new ShadeExpansionListener() { // from class: com.android.systemui.biometrics.UdfpsAnimationViewController$shadeExpansionListener$1
            public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                UdfpsAnimationViewController.this.setNotificationShadeVisible(shadeExpansionChangeEvent.getExpanded() && shadeExpansionChangeEvent.getFraction() > ActionBarShadowController.ELEVATION_LOW);
                t.onExpansionChanged(shadeExpansionChangeEvent.getFraction());
                UdfpsAnimationViewController.this.updatePauseAuth();
            }
        };
        this.touchTranslation = new PointF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
        String tag = getTag();
        this.dumpTag = tag + " (" + this + ")";
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsAnimationViewController$runDialogAlphaAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ UdfpsAnimationView access$getView(UdfpsAnimationViewController udfpsAnimationViewController) {
        return udfpsAnimationViewController.getView();
    }

    public void doAnnounceForAccessibility(String str) {
    }

    public final void dozeTimeTick() {
        if (getView().dozeTimeTick()) {
            getView().postInvalidate();
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        boolean z = this.notificationShadeVisible;
        printWriter.println("mNotificationShadeVisible=" + z);
        boolean shouldPauseAuth = shouldPauseAuth();
        printWriter.println("shouldPauseAuth()=" + shouldPauseAuth);
        boolean isPauseAuth = getView().isPauseAuth();
        printWriter.println("isPauseAuth=" + isPauseAuth);
        float dialogSuggestedAlpha = getView().getDialogSuggestedAlpha();
        printWriter.println("dialogSuggestedAlpha=" + dialogSuggestedAlpha);
    }

    public final boolean getNotificationShadeVisible() {
        return this.notificationShadeVisible;
    }

    public int getPaddingX() {
        return this.paddingX;
    }

    public int getPaddingY() {
        return this.paddingY;
    }

    public final ShadeExpansionStateManager getShadeExpansionStateManager() {
        return this.shadeExpansionStateManager;
    }

    public final StatusBarStateController getStatusBarStateController() {
        return this.statusBarStateController;
    }

    public abstract String getTag();

    public PointF getTouchTranslation() {
        return this.touchTranslation;
    }

    public final T getView() {
        View view = ((ViewController) this).mView;
        Intrinsics.checkNotNull(view);
        return (T) view;
    }

    public boolean listenForTouchesOutsideView() {
        return false;
    }

    public final void onDisplayConfiguring() {
        getView().onDisplayConfiguring();
        getView().postInvalidate();
    }

    public final void onDisplayUnconfigured() {
        getView().onDisplayUnconfigured();
        getView().postInvalidate();
    }

    public final void onSensorRectUpdated(RectF rectF) {
        getView().onSensorRectUpdated(rectF);
    }

    public void onTouchOutsideView() {
    }

    public void onViewAttached() {
        this.shadeExpansionStateManager.addExpansionListener(this.shadeExpansionListener);
        this.dialogManager.registerListener(this.dialogListener);
        DumpManager.registerDumpable$default(this.dumpManager, this.dumpTag, this, null, 4, null);
    }

    public void onViewDetached() {
        this.shadeExpansionStateManager.removeExpansionListener(this.shadeExpansionListener);
        this.dialogManager.unregisterListener(this.dialogListener);
        this.dumpManager.unregisterDumpable(this.dumpTag);
    }

    public final void runDialogAlphaAnimator() {
        boolean shouldHideAffordance = this.dialogManager.shouldHideAffordance();
        ValueAnimator valueAnimator = this.dialogAlphaAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(getView().calculateAlpha() / 255.0f, shouldHideAffordance ? 0.0f : 1.0f);
        ofFloat.setDuration(shouldHideAffordance ? 83L : 200L);
        ofFloat.setInterpolator(shouldHideAffordance ? Interpolators.LINEAR : Interpolators.ALPHA_IN);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.biometrics.UdfpsAnimationViewController$runDialogAlphaAnimator$1$1
            public final /* synthetic */ UdfpsAnimationViewController<T> this$0;

            {
                this.this$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                UdfpsAnimationViewController.access$getView(this.this$0).setDialogSuggestedAlpha(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                this.this$0.updateAlpha();
                this.this$0.updatePauseAuth();
            }
        });
        ofFloat.start();
        this.dialogAlphaAnimator = ofFloat;
    }

    public final void setNotificationShadeVisible(boolean z) {
        this.notificationShadeVisible = z;
    }

    public boolean shouldPauseAuth() {
        return this.notificationShadeVisible || this.dialogManager.shouldHideAffordance();
    }

    public void updateAlpha() {
        getView().updateAlpha();
    }

    public final void updatePauseAuth() {
        if (getView().setPauseAuth(shouldPauseAuth())) {
            getView().postInvalidate();
        }
    }
}